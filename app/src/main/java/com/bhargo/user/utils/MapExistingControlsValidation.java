package com.bhargo.user.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.bhargo.user.Java_Beans.AllControls;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.controls.advanced.AutoCompletionControl;
import com.bhargo.user.controls.advanced.Gps_Control;
import com.bhargo.user.controls.advanced.GridControl;
import com.bhargo.user.controls.advanced.SubformView;
import com.bhargo.user.controls.data_controls.DataControl;
import com.bhargo.user.controls.standard.AudioPlayer;
import com.bhargo.user.controls.standard.Calendar;
import com.bhargo.user.controls.standard.Camera;
import com.bhargo.user.controls.standard.CheckList;
import com.bhargo.user.controls.standard.Checkbox;
import com.bhargo.user.controls.standard.CountDownTimerControl;
import com.bhargo.user.controls.standard.CountUpTimerControl;
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
import com.bhargo.user.controls.standard.VideoPlayer;
import com.bhargo.user.controls.standard.VideoRecording;
import com.bhargo.user.controls.standard.ViewFileControl;
import com.bhargo.user.controls.standard.VoiceRecording;
import com.google.android.gms.maps.model.LatLng;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUDIO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_COMPLETION;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BAR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BUTTON;
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
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_URL_LINK;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_USER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_RECORDING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIEWFILE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VOICE_RECORDING;

public class MapExistingControlsValidation {

    //=======Load_Onchange_EventObjects================

    private static final String TAG = "ControlsValidation";
    public List<String> subFormMainAppendString = new ArrayList<>();
    Context context;
    LinkedHashMap<String, Object> List_ControlClassObjects;
    AllControls allControls;
    List<String> stringListSubmit = new ArrayList<>();
    List<HashMap<String, String>> stringListFiles = new ArrayList<>();
    private String emailPatternMain = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    public MapExistingControlsValidation(Context context,
                                         LinkedHashMap<String, Object> List_ControlClassObjects) {
        this.context = context;

        this.List_ControlClassObjects = List_ControlClassObjects;


    }


    public boolean controlSubmitValidation(ControlObject loadControlObject, boolean isSubForm, List<String> sfString, List<HashMap<String, String>> sfFilesString, String subFormId, int subFormRowId, int controlPos, List<String> mandatoryColumns) {

        boolean validationFlag = true;
        Log.d(TAG, "XmlHelperControlObject: " + loadControlObject.getControlType());

        switch (loadControlObject.getControlType()) {

            case CONTROL_TYPE_TEXT_INPUT:
                TextInput textInput = (TextInput) List_ControlClassObjects.get(loadControlObject.getControlName());
                String strTextInputValue = null;
                if (textInput.getTextInputView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                    if (loadControlObject.isNullAllowed()) {
                        if (textInput.getTextInputValue() != null && !textInput.getTextInputValue().isEmpty()) {
                            strTextInputValue = textInput.getTextInputValue();
                            if (strTextInputValue.length() > 0) {
                                textInput.setAlertTextInput().setVisibility(View.GONE);
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strTextInputValue);
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                }

                                validationFlag = true;
                                Log.e(TAG, "controlSubmitValidation: TextInput - " + CONTROL_TYPE_TEXT_INPUT + " - " + strTextInputValue);
                            } else {
                                textInput.setAlertTextInput().setVisibility(View.VISIBLE);
                                textInput.setAlertTextInput().setText(loadControlObject.getMandatoryFieldError());
                                ImproveHelper.setFocus(textInput.getTextInputView());
                                validationFlag = false;
                            }
                        } else if (!textInput.gettap_text().getText().toString().isEmpty() && (loadControlObject.isCurrentLocation() || loadControlObject.isReadFromQRCode() || loadControlObject.isReadFromQRCode())) {

                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + textInput.gettap_text().getText().toString());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + textInput.gettap_text().getText().toString().trim());
                            }

                        } else {
                            textInput.setAlertTextInput().setVisibility(View.VISIBLE);
                            textInput.setAlertTextInput().setText(loadControlObject.getMandatoryFieldError());
                            ImproveHelper.setFocus(textInput.getTextInputView());
                            validationFlag = false;
                        }
                    } else {
                        if (textInput.getTextInputValue() != null && !textInput.getTextInputValue().isEmpty()) {
                            strTextInputValue = textInput.getTextInputValue();

                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                            }

                        } else {

                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }

                        }
                    }
                } else if (textInput.getTextInputView().getVisibility() == View.GONE) { // Text Inpput Invisible Case
                    if (textInput.getTextInputValue() != null && !textInput.getTextInputValue().isEmpty()) {
                        strTextInputValue = textInput.getTextInputValue();
                        if (strTextInputValue.length() > 0) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strTextInputValue);
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                            }

                        }

                    } else {
                        if (isSubForm) {
                            sfString.add(loadControlObject.getControlName() + "|" + "");
                        }else{
                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                        }

                    }
                }

                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (textInput.getTextInputValue() != null && !textInput.getTextInputValue().isEmpty() && !textInput.getTextInputValue().trim().matches("")) {
                        textInput.setAlertTextInput().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {
                        textInput.setAlertTextInput().setVisibility(View.VISIBLE);
                        String alertMessage = loadControlObject.getControlName() + " is required";
                        textInput.setAlertTextInput().setText(alertMessage);
                        ImproveHelper.setFocus(textInput.getTextInputView());
                        validationFlag = false;
                    }
                }


                break;

            case CONTROL_TYPE_NUMERIC_INPUT:
                NumericInput numericInput = (NumericInput) List_ControlClassObjects.get(loadControlObject.getControlName());
                String strNumericValue = null;
                if (numericInput.getNumericInputView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                    if (loadControlObject.isNullAllowed()) {

                        if (numericInput.getNumericValue() != null && !numericInput.getNumericValue().isEmpty()) {
                            strNumericValue = numericInput.getNumericValue();
                            if (strNumericValue.length() > 0) {
                                numericInput.setAlertNumeric().setVisibility(View.GONE);
                                BigDecimal bInput = new BigDecimal(strNumericValue);

                                if (loadControlObject.isEnableUpperLimit() && bInput.compareTo(new BigDecimal(loadControlObject.getUpperLimit())) == 1) {

                                    numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                    numericInput.setAlertNumeric().setText(loadControlObject.getUpperLimitErrorMesage());
                                    ImproveHelper.setFocus(numericInput.getNumericInputView());
                                    validationFlag = false;
                                    Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");

                                } else {

                                    if (loadControlObject.isEnableLowerLimit() && (new BigDecimal(loadControlObject.getLowerLimit()).compareTo(bInput) == 1)) {
                                        numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                        numericInput.setAlertNumeric().setText(loadControlObject.getLowerLimitErrorMesage());
                                        validationFlag = false;
                                        ImproveHelper.setFocus(numericInput.getNumericInputView());
                                        Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                    } else {

                                        BigDecimal bstrlength = new BigDecimal(String.valueOf(strNumericValue.length()));

//                                        if (loadControlObject.isEnableCappingDigits() && bstrlength.compareTo(new BigDecimal(loadControlObject.getCappingDigits())) == 1) {
                                        if (loadControlObject.isEnableCappingDigits() && strNumericValue.length()>Integer.parseInt(loadControlObject.getCappingDigits())/*&& bstrlength.compareTo(new BigDecimal(loadControlObject.getCappingDigits())) == 1*/) {
                                            numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                            numericInput.setAlertNumeric().setText(loadControlObject.getCappingError());
                                            validationFlag = false;
                                            ImproveHelper.setFocus(numericInput.getNumericInputView());
                                            Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                        } else {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                            }

                                            validationFlag = true;
                                            Log.e(TAG, "controlSubmitValidation: Numeric - " + strNumericValue);
                                        }
                                    }
                                }


                            } else {
                                numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                numericInput.setAlertNumeric().setText(loadControlObject.getMandatoryFieldError());
                                validationFlag = false;
                                ImproveHelper.setFocus(numericInput.getNumericInputView());
                                Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");

                            }
                        } else {
                            numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                            numericInput.setAlertNumeric().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(numericInput.getNumericInputView());

                        }
                    } else {
                        if (numericInput.getNumericValue() != null && !numericInput.getNumericValue().isEmpty()) {
                            strNumericValue = numericInput.getNumericValue();
                            Log.e(TAG, "controlSubmitValidation: Numeric - " + strNumericValue);
                            numericInput.setAlertNumeric().setVisibility(View.GONE);
                            BigDecimal bInput = new BigDecimal(strNumericValue);

                            if (loadControlObject.isEnableUpperLimit() && bInput.compareTo(new BigDecimal(loadControlObject.getUpperLimit())) == 1) {

                                numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                numericInput.setAlertNumeric().setText(loadControlObject.getUpperLimitErrorMesage());
                                validationFlag = false;
                                ImproveHelper.setFocus(numericInput.getNumericInputView());
                                Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");

                            } else {

                                if (loadControlObject.isEnableLowerLimit() && (new BigDecimal(loadControlObject.getLowerLimit()).compareTo(bInput) == 1)) {
                                    numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                    numericInput.setAlertNumeric().setText(loadControlObject.getLowerLimitErrorMesage());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(numericInput.getNumericInputView());
                                    Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                } else {

                                    BigDecimal bstrlength = new BigDecimal(String.valueOf(strNumericValue.length()));

//                                    if (loadControlObject.isEnableCappingDigits() && bstrlength.compareTo(new BigDecimal(loadControlObject.getCappingDigits())) == 1) {
                                    if (loadControlObject.isEnableCappingDigits() && strNumericValue.length()>Integer.parseInt(loadControlObject.getCappingDigits())/*&& bstrlength.compareTo(new BigDecimal(loadControlObject.getCappingDigits())) == 1*/) {
                                        numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                        numericInput.setAlertNumeric().setText(loadControlObject.getCappingError());
                                        validationFlag = false;
                                        ImproveHelper.setFocus(numericInput.getNumericInputView());
                                        Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                    } else {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                        }

                                    }
                                }
                            }
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }

                        }

                    }


                } else if (numericInput.getNumericInputView().getVisibility() == View.GONE) {
                    if (numericInput.getNumericValue() != null && !numericInput.getNumericValue().isEmpty()) {
                        strNumericValue = numericInput.getNumericValue();
                        if (strNumericValue.length() > 0) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                }
                        }
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                    }
                }
                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (numericInput.getNumericValue() != null && !numericInput.getNumericValue().isEmpty() && !numericInput.getNumericValue().trim().matches("")) {
                        numericInput.setAlertNumeric().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {
                        numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                        String alertMessage = loadControlObject.getControlName() + " is required";
                        numericInput.setAlertNumeric().setText(alertMessage);
                        ImproveHelper.setFocus(numericInput.getNumericInputView());
                        validationFlag = false;
                    }
                }
                break;

            case CONTROL_TYPE_PHONE:
                Phone phoneInput = (Phone) List_ControlClassObjects.get(loadControlObject.getControlName());
                String strPhoneValue = null;
                if (phoneInput.getPhoneView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                    if (loadControlObject.isNullAllowed()) {
                        if (phoneInput.getPhoneValue() != null && !phoneInput.getPhoneValue().isEmpty()) {
                            strPhoneValue = phoneInput.getPhoneValue();
                            if (strPhoneValue.length() > 0) {
                                phoneInput.setAlertPhone().setVisibility(View.GONE);
                                BigDecimal bstrlength = new BigDecimal(String.valueOf(strPhoneValue.length()));

                                if (loadControlObject.isEnableCappingDigits() && bstrlength.compareTo(new BigDecimal(loadControlObject.getCappingDigits())) == 1) {
                                    phoneInput.setAlertPhone().setVisibility(View.VISIBLE);
                                    phoneInput.setAlertPhone().setText(loadControlObject.getCappingError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(phoneInput.getPhoneView());
                                    Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                } else {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strPhoneValue.trim());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strPhoneValue);
                                    }

                                    validationFlag = true;
                                    Log.e(TAG, "controlSubmitValidation: " + CONTROL_TYPE_PHONE + " - " + strPhoneValue);
                                }
                            } else {
                                phoneInput.setAlertPhone().setVisibility(View.VISIBLE);
                                phoneInput.setAlertPhone().setText(loadControlObject.getMandatoryFieldError());
                                validationFlag = false;
                                ImproveHelper.setFocus(phoneInput.getPhoneView());
                                Log.e(TAG, "controlSubmitValidation: No phoneInput Value ");
                            }
                        } else {
                            phoneInput.setAlertPhone().setVisibility(View.VISIBLE);
                            phoneInput.setAlertPhone().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(phoneInput.getPhoneView());
                        }
                    } else {
                        if (phoneInput.getPhoneValue() != null && !phoneInput.getPhoneValue().isEmpty()) {
                            strPhoneValue = phoneInput.getPhoneValue();
                            BigDecimal bstrlength = new BigDecimal(String.valueOf(strPhoneValue.length()));

                            if (loadControlObject.isEnableCappingDigits() && bstrlength.compareTo(new BigDecimal(loadControlObject.getCappingDigits())) == 1) {
                                phoneInput.setAlertPhone().setVisibility(View.VISIBLE);
                                phoneInput.setAlertPhone().setText(loadControlObject.getCappingError());
                                validationFlag = false;
                                ImproveHelper.setFocus(phoneInput.getPhoneView());
                                Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                            } else {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strPhoneValue.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strPhoneValue);
                                }

                                Log.e(TAG, "controlSubmitValidation: " + CONTROL_TYPE_PHONE + " - " + strPhoneValue);
                            }
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }

                        }
                    }
                } else if (phoneInput.getPhoneView().getVisibility() == View.GONE) {
                    if (phoneInput.getPhoneValue() != null && !phoneInput.getPhoneValue().isEmpty()) {
                        strPhoneValue = phoneInput.getPhoneValue();
                        if (strPhoneValue.length() > 0) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strPhoneValue.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strPhoneValue.trim());
                                }
                        }
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                    }
                }
                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (phoneInput.getPhoneValue() != null && !phoneInput.getPhoneValue().isEmpty() && !phoneInput.getPhoneValue().trim().matches("")) {
                        phoneInput.setAlertPhone().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {
                        phoneInput.setAlertPhone().setVisibility(View.VISIBLE);
                        String alertMessage = loadControlObject.getControlName() + " is required";
                        phoneInput.setAlertPhone().setText(alertMessage);
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
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());
                                }

                                email.setAlertEmail().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                email.setAlertEmail().setVisibility(View.VISIBLE);
                                email.setAlertEmail().setText("Invalid email");
                                validationFlag = false;
                                ImproveHelper.setFocus(email.getEmailView());
                                Log.e(TAG, "controlSubmitValidationEmail: No EmailInput Value ");
                            }
                        } else {
                            email.setAlertEmail().setVisibility(View.VISIBLE);
                            email.setAlertEmail().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(email.getEmailView());
                        }
                    } else {
                        if (email.getEmailValue() != null && !email.getEmailValue().isEmpty()) {
                            strEmailValue = email.getEmailValue();
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());

                            } else {
                                if (strEmailValue.length() > 0 && strEmailValue.matches(emailPatternMain)) {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());

                                } else {
                                    email.setAlertEmail().setVisibility(View.VISIBLE);
                                    email.setAlertEmail().setText("Invalid email");
                                    validationFlag = false;
                                    ImproveHelper.setFocus(email.getEmailView());
                                    Log.e(TAG, "controlSubmitValidationEmail: No EmailInput Value ");
                                }
                            }
                            Log.e(TAG, "controlSubmitValidationEmail: " + CONTROL_TYPE_EMAIL + " - " + strEmailValue);
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                        }
                    }
                } else if (email.getEmailView().getVisibility() == View.GONE) {
                    if (email.getEmailValue() != null && !email.getEmailValue().isEmpty()) {
                        strEmailValue = email.getEmailValue();
                        if (strEmailValue.length() > 0) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());
                                }
                        }
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                    }
                }

                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (email.getEmailValue() != null && !email.getEmailValue().isEmpty() && !email.getEmailValue().trim().matches("")) {
                        email.setAlertEmail().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {
                        email.setAlertEmail().setVisibility(View.VISIBLE);
                        String alertMessage = loadControlObject.getControlName() + " is required";
                        email.setAlertEmail().setText(alertMessage);
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
                        if (largeInput.getCustomEditText().getText().toString().length() > 0) {
                            strLargeInput = largeInput.getCustomEditText().getText().toString();

                            largeInput.setAlertLargeInput().setVisibility(View.GONE);

                            BigDecimal bInput = new BigDecimal(strLargeInput.length());

                            if (loadControlObject.isEnableMaxCharacters() && bInput.compareTo(new BigDecimal(loadControlObject.getMaxCharacters())) == 1) {

                                largeInput.setAlertLargeInput().setVisibility(View.VISIBLE);
                                largeInput.setAlertLargeInput().setText(loadControlObject.getMaxCharError());
                                validationFlag = false;
                                ImproveHelper.setFocus(largeInput.getLargeInputView());
                                Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");

                            } else {

                                if (loadControlObject.isEnableMinCharacters() && (new BigDecimal(loadControlObject.getMinCharacters())).compareTo(bInput) == 1) {
                                    largeInput.setAlertLargeInput().setVisibility(View.VISIBLE);
                                    largeInput.setAlertLargeInput().setText(loadControlObject.getMinCharError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(largeInput.getLargeInputView());
                                    Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                } else {

                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                    }

                                    validationFlag = true;
                                }
                            }
                            Log.e(TAG, "controlSubmitValidation: LargeInput - " + strLargeInput);
                        } else {
                            Log.e(TAG, "controlSubmitValidation: LargeInput - is mandatory ");
                            largeInput.setAlertLargeInput().setVisibility(View.VISIBLE);
                            largeInput.setAlertLargeInput().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(largeInput.getLargeInputView());
                        }
                    } else {
                        if (largeInput.getCustomEditText().getText().toString().length() > 0) {
                            strLargeInput = largeInput.getCustomEditText().getText().toString();
                            BigDecimal bInput = new BigDecimal(strLargeInput.length());

                            if (loadControlObject.isEnableMaxCharacters() && bInput.compareTo(new BigDecimal(loadControlObject.getMaxCharacters())) == 1) {

                                largeInput.setAlertLargeInput().setVisibility(View.VISIBLE);
                                largeInput.setAlertLargeInput().setText(loadControlObject.getMaxCharError());
                                validationFlag = false;
                                ImproveHelper.setFocus(largeInput.getLargeInputView());
                                Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");

                            } else {

                                if (loadControlObject.isEnableMinCharacters() && (new BigDecimal(loadControlObject.getMinCharacters())).compareTo(bInput) == 1) {
                                    largeInput.setAlertLargeInput().setVisibility(View.VISIBLE);
                                    largeInput.setAlertLargeInput().setText(loadControlObject.getMinCharError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(largeInput.getLargeInputView());
                                    Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                } else {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());

                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                    }

                                    Log.e(TAG, "controlSubmitValidation:" + CONTROL_TYPE_LARGE_INPUT + " - " + strLargeInput);
                                }
                            }
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }

                        }
                    }
                } else if (largeInput.getLargeInputView().getVisibility() == View.GONE) {
                    if (largeInput.getCustomEditText().getText() != null && !largeInput.getCustomEditText().getText().toString().isEmpty()) {
                        strLargeInput = largeInput.getCustomEditText().getText().toString();
                        if (strLargeInput.length() > 0) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                }
                        }
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                    }
                }

                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (largeInput.getCustomEditText() != null && !largeInput.getCustomEditText().getText().toString().isEmpty() && !largeInput.getCustomEditText().getText().toString().trim().matches("")) {
                        largeInput.setAlertLargeInput().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {
                        largeInput.setAlertLargeInput().setVisibility(View.VISIBLE);
                        String alertMessage = loadControlObject.getControlName() + " is required";
                        largeInput.setAlertLargeInput().setText(alertMessage);
                        ImproveHelper.setFocus(largeInput.getLargeInputView());
                        validationFlag = false;
                    }
                }

                break;
            case CONTROL_TYPE_CAMERA:
                Camera camera = (Camera) List_ControlClassObjects.get(loadControlObject.getControlName());
                if (camera.getCameraView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                    if (loadControlObject.isNullAllowed()) {
                        if (!camera.isImageViewNull()) {
                            camera.setAlertCamera().setVisibility(View.GONE);
                            Log.e(TAG, "controlSubmitValidation:" + loadControlObject.getControlName() + " | " + "NA");
//                            stringListSubmit.add(loadControlObject.getControlName() + " | " + "NA");
//                            stringListFiles.add(camera.getControlRealPath().getTag().toString());
                            if (isSubForm) {
                                HashMap<String, String> cam_hash = new HashMap<>();
                                cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, camera.getControlRealPath().getTag().toString());

                                sfFilesString.add(cam_hash);
                            } else {

                                HashMap<String, String> cam_hash = new HashMap<>();
                                if (camera.getControlRealPath().getTag() != null) {
                                    cam_hash.put(loadControlObject.getControlName(), camera.getControlRealPath().getTag().toString());
                                    stringListFiles.add(cam_hash);
                                } else {
                                    cam_hash.put(loadControlObject.getControlName(), loadControlObject.getControlValue());
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getControlValue());
                                }


                            }

                            validationFlag = true;
                        } else {
                            camera.setAlertCamera().setVisibility(View.VISIBLE);
                            camera.setAlertCamera().setText(loadControlObject.getMandatoryFieldError());
                            Log.e(TAG, "controlSubmitValidation: No camera Value ");
                            validationFlag = false;
                            ImproveHelper.setFocus(camera.getCameraView());
                        }
                    } else {
                        if (camera.getBitmapSetValues() != null || loadControlObject.getControlValue()!=null || camera.getControlRealPath().getTag().toString()!=null) {
                            Log.e(TAG, "controlSubmitValidation: " + loadControlObject.getControlName() + "|" + "NA");
//                            stringListSubmit.add(loadControlObject.getControlName() + " | " + "NA");
//                            stringListFiles.add(camera.getControlRealPath().getTag().toString());
                            if (isSubForm) {
                                HashMap<String, String> cam_hash = new HashMap<>();
                                cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, camera.getControlRealPath().getTag().toString());

                                sfFilesString.add(cam_hash);
                            } else {
                                HashMap<String, String> cam_hash = new HashMap<>();
                                if (camera.getControlRealPath().getTag() != null) {
                                    cam_hash.put(loadControlObject.getControlName(), camera.getControlRealPath().getTag().toString());
                                    stringListFiles.add(cam_hash);
                                } else {
                                    cam_hash.put(loadControlObject.getControlName(), loadControlObject.getControlValue());
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getControlValue());
                                }

                            }


                        } else {
                            HashMap<String, String> cam_hash = new HashMap<>();
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                if (camera.getControlRealPath().getTag() != null) {
                                    cam_hash.put(loadControlObject.getControlName(), camera.getControlRealPath().getTag().toString());
                                    stringListFiles.add(cam_hash);
                                }else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                }
//                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }

                        }
                    }
                } else if (camera.getCameraView().getVisibility() == View.GONE) {
                    if (!camera.isImageViewNull()) {
                            if (isSubForm) {
                                HashMap<String, String> cam_hash = new HashMap<>();
                                cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, camera.getControlRealPath().getTag().toString());
                                sfFilesString.add(cam_hash);
                            } else {
                                HashMap<String, String> cam_hash = new HashMap<>();
                                if (camera.getControlRealPath().getTag() != null) {
                                    cam_hash.put(loadControlObject.getControlName(), camera.getControlRealPath().getTag().toString());
                                    stringListFiles.add(cam_hash);
                                } else {
                                    cam_hash.put(loadControlObject.getControlName(), loadControlObject.getControlValue());
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getControlValue());
                                }
                            }
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                    }
                }

                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (!camera.isImageViewNull()) {
                        camera.setAlertCamera().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {
                        camera.setAlertCamera().setVisibility(View.VISIBLE);
                        String alertMessage = loadControlObject.getControlName() + " is required";
                        camera.setAlertCamera().setText(alertMessage);
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
                        if (!fileBrowsing.getTapTextView().getText().toString().equalsIgnoreCase(context.getString(R.string.tap_here_to_select_file))
                                && fileBrowsing.getTapTextView().getText().toString().length() > 0) {
                            strFilePath = fileBrowsing.getFileBrowsingPath();
                            Log.e(TAG, "controlSubmitValidation:" + CONTROL_TYPE_FILE_BROWSING + "-" + strFilePath);

                            fileBrowsing.setAlertFileBrowser().setVisibility(View.GONE);
//                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                                stringListFiles.add(fileBrowsing.getControlRealPath().getTag().toString());
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


                            validationFlag = true;

                        } else {
                            fileBrowsing.setAlertFileBrowser().setVisibility(View.VISIBLE);
                            fileBrowsing.setAlertFileBrowser().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(fileBrowsing.getFileBrowsingView());
                        }
                    } else {
                        if (!fileBrowsing.getTapTextView().getText().toString().equalsIgnoreCase(context.getString(R.string.tap_here_to_select_file))
                                && fileBrowsing.getTapTextView().getText().toString().length() > 0) {
                            strFilePath = fileBrowsing.getFileBrowsingPath();
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListFiles.add(fileBrowsing.getControlRealPath().getTag().toString());
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


                            Log.e(TAG, "controlSubmitValidation:" + CONTROL_TYPE_FILE_BROWSING + "-" + strFilePath);
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }

                        }
                    }
                } else if (fileBrowsing.getFileBrowsingView().getVisibility() == View.GONE) { // Text Input Invisible Case
                    if (!fileBrowsing.getTapTextView().getText().toString().equalsIgnoreCase(context.getString(R.string.tap_here_to_select_file))
                            && fileBrowsing.getTapTextView().getText().toString().length() > 0) {
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
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                    }
                }

                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (!fileBrowsing.getTapTextView().getText().toString().equalsIgnoreCase(context.getString(R.string.tap_here_to_select_file))
                            && fileBrowsing.getTapTextView().getText().toString().length() > 0) {
                        fileBrowsing.setAlertFileBrowser().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {

                        String alertMessage = loadControlObject.getControlName() + " is required";
                        fileBrowsing.setAlertFileBrowser().setVisibility(View.VISIBLE);
                        fileBrowsing.setAlertFileBrowser().setText(alertMessage);
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
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strCalendarValue.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strCalendarValue.trim());
                                }

                                validationFlag = true;

                            } else {
                                calendar.setAlertCalendar().setVisibility(View.VISIBLE);
                                calendar.setAlertCalendar().setText(loadControlObject.getMandatoryFieldError());
                                Log.e(TAG, "controlSubmitValidation: No Calendar Value ");
                                validationFlag = false;
                                ImproveHelper.setFocus(calendar.getCalnderView());
                            }
                        } else {
                            calendar.setAlertCalendar().setVisibility(View.VISIBLE);
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
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strCalendarValue.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strCalendarValue);
                                }

                                Log.e(TAG, "controlSubmitValidation: Calendar - " + strCalendarValue);
                            }
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }

                        }
                    }
                }else  if (calendar.getCalnderView().getVisibility() == View.GONE) {
                    if (calendar.getFinalDateWithServerFormat() != null) {/*calendar.getCalendarValue() != null*/
//                        strCalendarValue = calendar.getCalendarValue();
                                                strCalendarValue = calendar.getFinalDateWithServerFormat();
                        if (strCalendarValue.length() > 0) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strCalendarValue.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strCalendarValue.trim());
                                }
                        }
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                    }
                }

                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (calendar.getCalendarValue() != null && !calendar.getCalendarValue().isEmpty()) {
                        calendar.setAlertCalendar().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {

                        String alertMessage = loadControlObject.getControlName() + " is required";
                        calendar.setAlertCalendar().setVisibility(View.VISIBLE);
                        calendar.setAlertCalendar().setText(alertMessage);
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
                            if (checkBox.getSelectedNameCheckbox() != null) {
                                Log.d(TAG, "CB_Values: " + CONTROL_TYPE_CHECKBOX + "-" + checkBox.getSelectedNameCheckboxString());
                                if (isSubForm) {
                                    if (checkBox.getSelectedNameCheckboxString() != null && checkBox.getSelectedNameCheckboxString().length() > 0) {
                                        sfString.add(loadControlObject.getControlName() + "|" + checkBox.getSelectedNameCheckboxString());
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + ImproveHelper.getSelectedId(checkBox.getSelectedNameCheckboxString(), loadControlObject));

                                    } else {
                                        sfString.add(loadControlObject.getControlName() + "|" + null);
                                    }
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + checkBox.getSelectedNameCheckboxString());
                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + ImproveHelper.getSelectedId(checkBox.getSelectedNameCheckboxString(), loadControlObject));
                                }

                                validationFlag = true;
                            }

                        } else {
                            checkBox.setAlertCheckbox().setVisibility(View.VISIBLE);
                            checkBox.setAlertCheckbox().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(checkBox.getCheckbox());
                        }
                    } else {
                        if (checkBox.getSelectedNameCheckboxString() != null && !checkBox.getSelectedNameCheckboxString().isEmpty()) {
                            if (isSubForm) {
                                if (checkBox.getSelectedNameCheckboxString() != null && checkBox.getSelectedNameCheckboxString().length() > 0) {
                                    sfString.add(loadControlObject.getControlName() + "|" + checkBox.getSelectedNameCheckboxString());
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + ImproveHelper.getSelectedId(checkBox.getSelectedNameCheckboxString(), loadControlObject));

                                } else {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + "");

                                }

                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + checkBox.getSelectedNameCheckboxString());
                                stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + ImproveHelper.getSelectedId(checkBox.getSelectedNameCheckboxString(), loadControlObject));

                            }

                            Log.e(TAG, "controlSubmitValidation: " + CONTROL_TYPE_CHECKBOX + "-" + checkBox.getSelectedNameCheckboxString());
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }

                        }
                    }
                } else  if (checkBox.getCheckbox().getVisibility() == View.GONE) {
                    if (checkBox.getSelectedNameCheckboxString() != null && !checkBox.getSelectedNameCheckboxString().isEmpty()) {
                            if (isSubForm) {
                                if (checkBox.getSelectedNameCheckboxString() != null && checkBox.getSelectedNameCheckboxString().length() > 0) {
                                    sfString.add(loadControlObject.getControlName() + "|" + checkBox.getSelectedNameCheckboxString());
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + ImproveHelper.getSelectedId(checkBox.getSelectedNameCheckboxString(), loadControlObject));
                                } else {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                }
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + checkBox.getSelectedNameCheckboxString());
                                stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + ImproveHelper.getSelectedId(checkBox.getSelectedNameCheckboxString(), loadControlObject));
                            }

                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }
                    }
                }
                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (!checkBox.isCheckBoxListEmpty()) {
                        checkBox.setAlertCheckbox().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {

                        String alertMessage = loadControlObject.getControlName() + " is required";
                        checkBox.setAlertCheckbox().setVisibility(View.VISIBLE);
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

                            validationFlag = true;
                        } else {
                            radioGroupView.setAlertRadioGroup().setVisibility(View.VISIBLE);
                            radioGroupView.setAlertRadioGroup().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(radioGroupView.getRadioGroupView());
                        }
                    } else {
                        if (radioGroupView.isRadioGroupViewSelected()) {
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

                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }

                        }
                    }
                } else if (radioGroupView.getRadioGroupView().getVisibility() == View.GONE) {
                    if (radioGroupView.isRadioGroupViewSelected()) {
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
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }
                    }
                }
                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (radioGroupView.isRadioGroupViewSelected()) {
                        radioGroupView.setAlertRadioGroup().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {

                        String alertMessage = loadControlObject.getControlName() + " is required";
                        radioGroupView.setAlertRadioGroup().setVisibility(View.VISIBLE);
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
                            if (isSubForm) {
//                                    sfString.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
//                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + dropDown.getSelectedDropDownItemID());
                            } else {
//                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
//                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + dropDown.getSelectedDropDownItemID());
                            }

                            validationFlag = true;
                            if (dropDown.isDropDownOthersEmpty()) {
                                validationFlag = false;
                                dropDown.setAlertDropDown().setVisibility(View.VISIBLE);
                                dropDown.setAlertDropDown().setText(loadControlObject.getMandatoryFieldError());
                                ImproveHelper.setFocus(dropDown.getDropdown());
                            } else {
                                Log.d(TAG, "dd_value: " + CONTROL_TYPE_DROP_DOWN + "-" + dropDown.getSelectedDropDownItem());
                            }
                        } else {
                            dropDown.setAlertDropDown().setVisibility(View.VISIBLE);
                            dropDown.setAlertDropDown().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(dropDown.getDropdown());
                        }
                    } else {
                        if (dropDown.dropdownItemIsSelected()) {
                            if (isSubForm) {
//                                    sfString.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
//                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + dropDown.getSelectedDropDownItemID());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
                                stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + dropDown.getSelectedDropDownItemID());
                            }

                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }

                        }
                    }
                }else if (dropDown.getDropdown().getVisibility() == View.GONE) {
                    if (dropDown.dropdownItemIsSelected()) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
                                sfString.add(loadControlObject.getControlName() + "id" + "|" + dropDown.getSelectedDropDownItemID());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
                                stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + dropDown.getSelectedDropDownItemID());
                            }
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }
                    }
                }
                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (dropDown.dropdownItemIsSelected()) {
                        dropDown.setAlertDropDown().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {
                        String alertMessage = loadControlObject.getControlName() + " is required";
                        dropDown.setAlertDropDown().setVisibility(View.VISIBLE);
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
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                sfString.add(loadControlObject.getControlName() + "id" + "|" + checkList.getSelectedIDCheckListString());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + checkList.getSelectedIDCheckListString());
                            }

                            Log.d(TAG, "controlSubmitValidation: " + checkList.getCheckList());
                        } else {
                            checkList.setAlertCheckList().setVisibility(View.VISIBLE);
                            checkList.setAlertCheckList().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(checkList.getCheckList());
                        }
                    } else {
                        if (checkList.checkListItemIsSelected() && !checkList.getSelectedNameCheckListString().isEmpty()) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                sfString.add(loadControlObject.getControlName() + "id" + "|" + checkList.getSelectedIDCheckListString());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + checkList.getSelectedIDCheckListString());
                            }

                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }

                        }
                    }
                }else if (checkList.getCheckList().getVisibility() == View.GONE) {
                    if (checkList.checkListItemIsSelected() && !checkList.getSelectedNameCheckListString().isEmpty()) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                sfString.add(loadControlObject.getControlName() + "id" + "|" + checkList.getSelectedIDCheckListString());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + checkList.getSelectedIDCheckListString());
                            }
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                            }
                    }
                }
                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (checkList.checkListItemIsSelected()) {
                        checkList.setAlertCheckList().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {

                        String alertMessage = loadControlObject.getControlName() + " is required";
                        checkList.setAlertCheckList().setVisibility(View.VISIBLE);
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
                            rating.setAlertRating().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(rating.getRatingView());
                        } else {
                            Log.d(TAG, "CONTROL_TYPE_RATING: " + rating.getTotalRating());
                            rating.setAlertRating().setVisibility(View.GONE);
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
                            }

                            validationFlag = true;
                        }
                    } else {
                        if (rating.getTotalRating() != null && !rating.getTotalRating().isEmpty()) {
                            Log.d(TAG, "CONTROL_TYPE_RATING: " + rating.getTotalRating());
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
                            }

                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }

                        }
                    }
                }else  if (rating.getRatingView().getVisibility() == View.GONE) {
                    if (rating.getTotalRating() != null && !rating.getTotalRating().isEmpty()) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
                            }
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                    }
                }
                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (rating.getTotalRating().equalsIgnoreCase("0.0")
                            || rating.getTotalRating().equalsIgnoreCase("0")
                            || rating.getTotalRating().equalsIgnoreCase("")) {

                        String alertMessage = loadControlObject.getControlName() + " is required";
                        rating.setAlertRating().setVisibility(View.VISIBLE);
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
                            voiceRecording.setAlertVoiceRecording().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(voiceRecording.getVoiceRecordingView());
                        } else {
                            voiceRecording.setAlertVoiceRecording().setVisibility(View.GONE);
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListVideoAudio.add(voiceRecording.getControlRealPath().getTag().toString());
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


                            validationFlag = true;
                        }

                    } else {
                        if (voiceRecording.setIsVoiceRecorded()) {
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListVideoAudio.add(voiceRecording.getControlRealPath().getTag().toString());
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


                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }

                        }
                    }
                }else  if (voiceRecording.getVoiceRecordingView().getVisibility() == View.GONE) {
                    if (voiceRecording.setIsVoiceRecorded()) {
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
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                    }
                }
                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (voiceRecording.setIsVoiceRecorded()) {
                        voiceRecording.setAlertVoiceRecording().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {

                        String alertMessage = loadControlObject.getControlName() + " is required";
                        voiceRecording.setAlertVoiceRecording().setVisibility(View.VISIBLE);
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
                            videoRecording.setAlertVideoRecording().setVisibility(View.VISIBLE);
                            videoRecording.setAlertVideoRecording().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(videoRecording.getVideoRecorderView());
                        } else {
                            videoRecording.setAlertVideoRecording().setVisibility(View.GONE);
                            strVideobase64 = videoRecording.getVideoFileToBase64(videoRecording.getFromCameraOrGalleyURI());
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListVideoAudio.add(videoRecording.getControlRealPath().getTag().toString());
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


                            Log.d(TAG, "controlSubmitValidation" + " - " + strVideobase64);
                            validationFlag = true;
                        }
                    } else {
                        if (videoRecording.isVideoRecorded()) {
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListVideoAudio.add(videoRecording.getControlRealPath().getTag().toString());
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


                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }

                        }
                    }
                }else  if (videoRecording.getVideoRecorderView().getVisibility() == View.GONE) {
                    if (videoRecording.isVideoRecorded()) {
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
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                    }
                }
                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (videoRecording.isVideoRecorded()) {
                        videoRecording.setAlertVideoRecording().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {
                        String alertMessage = loadControlObject.getControlName() + " is required";
                        videoRecording.setAlertVideoRecording().setVisibility(View.VISIBLE);
                        videoRecording.setAlertVideoRecording().setText(alertMessage);
                        validationFlag = false;
                        ImproveHelper.setFocus(videoRecording.getVideoRecorderView());
                    }
                }

                break;

            case CONTROL_TYPE_AUDIO_PLAYER:
                AudioPlayer audioPlayer = (AudioPlayer) List_ControlClassObjects.get(loadControlObject.getControlName());

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
                    }
                break;
            case CONTROL_TYPE_VIDEO_PLAYER:
                VideoPlayer videoPlayer = (VideoPlayer) List_ControlClassObjects.get(loadControlObject.getControlName());

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
                    }
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
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                            }

                            validationFlag = true;
                            Log.d(TAG, "controlSubmitValidation" + " - " + strPercentage);
                        } else {
                            percentage.setAlertPercentage().setVisibility(View.VISIBLE);
                            percentage.setAlertPercentage().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(percentage.getPercentageView());
                        }
                    } else {
                        if (!percentage.getCustomEditText().getText().toString().equalsIgnoreCase("")) {
                            strPercentage = percentage.getCustomEditText().getText().toString();
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                            }

                            Log.d(TAG, "controlSubmitValidation" + " - " + strPercentage);
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }

                        }
                    }
                }else if (percentage.getPercentageView().getVisibility() == View.GONE) {
                    if (!percentage.getCustomEditText().getText().toString().equalsIgnoreCase("")) {
                        strPercentage = percentage.getCustomEditText().getText().toString();
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                            }
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                    }
                }
                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (percentage.getCustomEditText().getText().toString() != null && !percentage.getCustomEditText().getText().toString().isEmpty()) {
                        percentage.setAlertPercentage().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {
                        String alertMessage = loadControlObject.getControlName() + " is required";
                        percentage.setAlertPercentage().setVisibility(View.VISIBLE);
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
                            signatureView.setAlertSignature().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(signatureView.getSignature());
                        } else {
                            signatureView.setAlertSignature().setVisibility(View.GONE);
//                            bitmapSignature = signatureView.isSignatureBitMap();
//                            stringListFiles.add(signatureView.getControlRealPath().getTag().toString());
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


//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
                            validationFlag = true;
                            Log.d(TAG, "controlSubmitValidation" + " - " + bitmapSignature);
                        }
                    } else {
                        if (!signatureView.isSignatureView()) {
                            if (signatureView.isSignatureBitMap() != null) {
                                bitmapSignature = signatureView.isSignatureBitMap();
//                                stringListFiles.add(signatureView.getControlRealPath().getTag().toString());
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

//                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
                                Log.d(TAG, "controlSubmitValidation" + " - " + bitmapSignature);
                            }
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }

                        }
                    }
                }else if (signatureView.getSignature().getVisibility() == View.GONE) {
                    if (!signatureView.isSignatureView()) {
                        if (signatureView.isSignatureBitMap() != null) {
                            bitmapSignature = signatureView.isSignatureBitMap();
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

                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                    }
                }
                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (signatureView.isSignatureView() && !signatureView.isSignatureImage()) {
                        String alertMessage = loadControlObject.getControlName() + " is required";
                        signatureView.setAlertSignature().setVisibility(View.VISIBLE);
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
                break;

            case CONTROL_TYPE_DECIMAL:

                DecimalView decimalView = (DecimalView) List_ControlClassObjects.get(loadControlObject.getControlName());
                String strDecimalValue = null;
                if (decimalView.getDecimal().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                    if (loadControlObject.isNullAllowed()) {
                        if (decimalView.getCustomEditText().getText().toString().isEmpty()) {
                            decimalView.setAlertDecimalView().setVisibility(View.VISIBLE);
                            decimalView.setAlertDecimalView().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(decimalView.getDecimal());
                        } else {
                            decimalView.setAlertDecimalView().setVisibility(View.GONE);
                            strDecimalValue = decimalView.getCustomEditText().getText().toString();
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                            }


                            validationFlag = true;
                            Log.d(TAG, "controlSubmitValidation" + " - " + strDecimalValue);
                        }
                    } else {
                        if (decimalView.getCustomEditText().getText().toString() != null && !decimalView.getCustomEditText().getText().toString().isEmpty()) {
                            strDecimalValue = decimalView.getCustomEditText().getText().toString();
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                            }

                            Log.d(TAG, "controlSubmitValidation" + " - " + strDecimalValue);
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                        }
                    }
                }else  if (decimalView.getDecimal().getVisibility() == View.GONE) {
                    if (decimalView.getCustomEditText().getText().toString() != null && !decimalView.getCustomEditText().getText().toString().isEmpty()) {
                        strDecimalValue = decimalView.getCustomEditText().getText().toString();
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                            }
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                    }
                }
                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (decimalView.getCustomEditText().getText().toString().isEmpty()) {
                        String alertMessage = loadControlObject.getControlName() + " is required";
                        decimalView.setAlertDecimalView().setVisibility(View.VISIBLE);
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
                        if (password.getCustomEditText().getText().toString().isEmpty()) {
                            password.setAlertPasswordView().setVisibility(View.VISIBLE);
                            password.setAlertPasswordView().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(password.getPassword());
                        } else {
                            password.setAlertPasswordView().setVisibility(View.GONE);
                            strPasswordValue = password.getCustomEditText().getText().toString();
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                            }


                            validationFlag = true;
                            Log.d(TAG, "controlSubmitValidation" + " - " + strPasswordValue);
                        }
                    } else {
                        if (!password.getCustomEditText().getText().toString().equalsIgnoreCase("")) {
                            strPasswordValue = password.getCustomEditText().getText().toString();
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                            }

                            Log.d(TAG, "controlSubmitValidation" + " - " + strPasswordValue);
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }

                        }
                    }
                }else if (password.getPassword().getVisibility() == View.GONE) {
                    if (!password.getCustomEditText().getText().toString().equalsIgnoreCase("")) {
                        strPasswordValue = password.getCustomEditText().getText().toString();
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                            }
                    }else{
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                    }
                }
                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (password.getCustomEditText().getText().toString().isEmpty()) {
                        String alertMessage = loadControlObject.getControlName() + " is required";
                        password.setAlertPasswordView().setVisibility(View.VISIBLE);
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
                                    currency.setAlertCurrency().setText(loadControlObject.getMaxAmountError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(currency.getCurrency());
                                    Log.e(TAG, "controlSubmitValidation: No Currency Value ");

                                } else {

                                    if (loadControlObject.isEnableMinimumAmount() && (new BigDecimal(loadControlObject.getMinAmount()).compareTo(bInput) == 1)) {
                                        currency.setAlertCurrency().setVisibility(View.VISIBLE);
                                        currency.setAlertCurrency().setText(loadControlObject.getMinAmountError());
                                        validationFlag = false;
                                        ImproveHelper.setFocus(currency.getCurrency());
                                        Log.e(TAG, "controlSubmitValidation: No Currency Value ");
                                    } else {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                                        }

                                        validationFlag = true;
                                        Log.e(TAG, "controlSubmitValidation: Currency - " + strCurrencyValue);
                                    }
                                }


                            } else {
                                currency.setAlertCurrency().setVisibility(View.VISIBLE);
                                currency.setAlertCurrency().setText(loadControlObject.getMandatoryFieldError());
                                validationFlag = false;
                                ImproveHelper.setFocus(currency.getCurrency());
                                Log.e(TAG, "controlSubmitValidation: No Currency Value ");

                            }
                        } else {
                            currency.setAlertCurrency().setVisibility(View.VISIBLE);
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
                                currency.setAlertCurrency().setText(loadControlObject.getMaxAmountError());
                                validationFlag = false;
                                ImproveHelper.setFocus(currency.getCurrency());
                                Log.e(TAG, "controlSubmitValidation: No Currency Value ");

                            } else {

                                if (loadControlObject.isEnableMinimumAmount() && (new BigDecimal(loadControlObject.getMinAmount()).compareTo(bInput) == 1)) {
                                    currency.setAlertCurrency().setVisibility(View.VISIBLE);
                                    currency.setAlertCurrency().setText(loadControlObject.getMinAmountError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(currency.getCurrency());
                                    Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");

                                } else {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                                    }


                                }
                            }
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }

                        }

                    }

                }else if (currency.getCurrency().getVisibility() == View.GONE) {
                    if (currency.getCustomEditText().getText() != null
                            && !currency.getCustomEditText().getText().toString().isEmpty()) {
                        strCurrencyValue = currency.getCustomEditText().getText().toString();
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                            }
                        }else{
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }else{
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                }
                        }
                    }

                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (currency.getCustomEditText().getText() != null
                            && !currency.getCustomEditText().getText().toString().isEmpty()) {
                        currency.setAlertCurrency().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {
                        String alertMessage = loadControlObject.getControlName() + " is required";
                        currency.setAlertCurrency().setVisibility(View.VISIBLE);
                        currency.setAlertCurrency().setText(alertMessage);
                        validationFlag = false;
                        ImproveHelper.setFocus(currency.getCurrency());

                    }
                }


                break;

            case CONTROL_TYPE_DYNAMIC_LABEL:

                break;
            case CONTROL_TYPE_IMAGE:

                break;
            case CONTROL_TYPE_BUTTON:
                break;
            case CONTROL_TYPE_GRID_CONTROL:
                GridControl gridView = (GridControl) List_ControlClassObjects.get(loadControlObject.getControlName());

                validationFlag = gridView.formValidatedGrid(gridView.getSubFormView(), gridView, true);
                gridView.getSubFormString();
                Log.d(TAG, "controlSubmitValidationSubForm: " + gridView.getSubFormString());


                break;
            case CONTROL_TYPE_SUBFORM:

                SubformView subformView = (SubformView) List_ControlClassObjects.get(loadControlObject.getControlName());

                validationFlag = subformView.formValidated(subformView.getSubFormView(), subformView, false);
                subformView.getSubFormString();
                Log.d(TAG, "controlSubmitValidationSubForm: " + subformView.getSubFormString());


                break;

            case CONTROL_TYPE_GPS:

                Gps_Control controlGPS = (Gps_Control) List_ControlClassObjects.get(loadControlObject.getControlName());
                if (controlGPS.getControlGPSView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) { nk gps
                    if (loadControlObject.isNullAllowed()) {
                        if (controlGPS.getLatLngList().size() > 0) {
                            controlGPS.setAlertGPS().setVisibility(View.GONE);
                            if (isSubForm) {
                                String GPSSTR = "";
                                List<LatLng> latLngList = controlGPS.getLatLngList();
                                if(loadControlObject.getGpsType().equals("Single point GPS")){
                                    LatLng latlang = latLngList.get(0);
                                    GPSSTR =latlang.latitude + "$" + latlang.longitude;
                                }else{
                                    for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                        LatLng latlang = latLngList.get(i);
                                        GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                    }
                                }
                                GPSSTR =GPSSTR.startsWith("^")? GPSSTR.substring(1):GPSSTR;
                                //GPSSTR = GPSSTR.substring(GPSSTR.indexOf("^") + 1);
                                sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                sfString.add(loadControlObject.getControlName() + "_Type" + "| " + controlGPS.getGPS_Type());

                            } else {
                                String GPSSTR = "";
                                List<LatLng> latLngList = controlGPS.getLatLngList();
                                if(loadControlObject.getGpsType().equals("Single point GPS")){
                                    LatLng latlang = latLngList.get(0);
                                    GPSSTR =latlang.latitude + "$" + latlang.longitude;
                                }else{
                                    for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                        LatLng latlang = latLngList.get(i);
                                        GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                    }
                                }
                                //GPSSTR = GPSSTR.substring(GPSSTR.indexOf("^") + 1);
                                GPSSTR =GPSSTR.startsWith("^")? GPSSTR.substring(1):GPSSTR;
                                stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + controlGPS.getGPS_Type());
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
                            if (isSubForm) {
                                String GPSSTR = "";

                                List<LatLng> latLngList = controlGPS.getLatLngList();
                                if(loadControlObject.getGpsType().equals("Single point GPS")){
                                    LatLng latlang = latLngList.get(0);
                                    GPSSTR =latlang.latitude + "$" + latlang.longitude;
                                }else{
                                    for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                        LatLng latlang = latLngList.get(i);
                                        GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                    }
                                }
                                GPSSTR =GPSSTR.startsWith("^")? GPSSTR.substring(1):GPSSTR;
                                //GPSSTR = GPSSTR.substring(GPSSTR.indexOf("^") + 1);
                                sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                sfString.add(loadControlObject.getControlName() + "_Type" + "|" + controlGPS.getGPS_Type());

                            } else {
                                String GPSSTR = "";
                                List<LatLng> latLngList = controlGPS.getLatLngList();
                                if(loadControlObject.getGpsType().equals("Single point GPS")){
                                    LatLng latlang = latLngList.get(0);
                                    GPSSTR =latlang.latitude + "$" + latlang.longitude;
                                }else{
                                    for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                        LatLng latlang = latLngList.get(i);
                                        GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                    }
                                }
                                GPSSTR =GPSSTR.startsWith("^")? GPSSTR.substring(1):GPSSTR;
                                //GPSSTR = GPSSTR.substring(GPSSTR.indexOf("^") + 1);

                                stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + controlGPS.getGPS_Type());
                            }

                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "_Coordinates" +  "|" + "");
                                sfString.add(loadControlObject.getControlName() + "_Type" + "|" + "");
                            }else{
                                stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" +  "|" + "");
                                stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + "");
                            }

                        }
                    }
                } else   if (controlGPS.getControlGPSView().getVisibility() == View.GONE) {
                    if (controlGPS.getLatLngList().size() != 0) {
                            if (isSubForm) {
                                String GPSSTR = "";
                                List<LatLng> latLngList = controlGPS.getLatLngList();
                                if(loadControlObject.getGpsType().equals("Single point GPS")){
                                    LatLng latlang = latLngList.get(0);
                                    GPSSTR =latlang.latitude + "$" + latlang.longitude;
                                }else{
                                    for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                        LatLng latlang = latLngList.get(i);
                                        GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                    }
                                }
                                GPSSTR =GPSSTR.startsWith("^")? GPSSTR.substring(1):GPSSTR;
                                //GPSSTR = GPSSTR.substring(GPSSTR.indexOf("^") + 1);
                                sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                sfString.add(loadControlObject.getControlName() + "_Type" + "|" + controlGPS.getGPS_Type());
                            } else {
                                String GPSSTR = "";
                                List<LatLng> latLngList = controlGPS.getLatLngList();
                                if(loadControlObject.getGpsType().equals("Single point GPS")){
                                    LatLng latlang = latLngList.get(0);
                                    GPSSTR =latlang.latitude + "$" + latlang.longitude;
                                }else{
                                    for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                        LatLng latlang = latLngList.get(i);
                                        GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                    }
                                }
                                GPSSTR =GPSSTR.startsWith("^")? GPSSTR.substring(1):GPSSTR;
                                //GPSSTR = GPSSTR.substring(GPSSTR.indexOf("^") + 1);
                                stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + controlGPS.getGPS_Type());
                            }
                    }else{
                        if (isSubForm) {
                            sfString.add(loadControlObject.getControlName() + "_Coordinates" +  "|" + "");
                            sfString.add(loadControlObject.getControlName() + "_Type" + "|" + "");
                        }else{
                            stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" +  "|" + "");
                            stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + "");
                        }
                    }
                }

                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
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

                break;
            case CONTROL_TYPE_QR_CODE:

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
                            dataControl.setAlertDataControl().setText(loadControlObject.getMandatoryFieldError());
                            validationFlag = false;
                            ImproveHelper.setFocus(dataControl.getDataControl());
                        } else {
                            validationFlag = true;
                            dataControl.setAlertDataControl().setVisibility(View.GONE);
                            strDataControlId = dataControl.getSelectedDropDownItemId();
                            strDataControlName = dataControl.getSelectedDropDownItemName();
//                            Log.d(TAG, "controlSubmitValidation" + " - " + strDataControlValue);
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName().trim() + "|" + strDataControlName);
                                sfString.add(loadControlObject.getControlName().trim() + "_id" + "|" + strDataControlId);

                            } else {
                                stringListSubmit.add(loadControlObject.getControlName().trim() + "|" + strDataControlName);
                                stringListSubmit.add(loadControlObject.getControlName().trim() + "_id" + "|" + strDataControlId);
                            }

                        }
                    } else {
                        if (dataControl.dropdownItemIsSelected()) {
                            strDataControlId = dataControl.getSelectedDropDownItemId();
                            strDataControlName = dataControl.getSelectedDropDownItemName();
//                            Log.d(TAG, "controlSubmitValidation" + " - " + strDataControlValue);
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName().trim() + "|" + strDataControlName);
                                sfString.add(loadControlObject.getControlName().trim() + "_id" + "|" + strDataControlId);

                            } else {
                                stringListSubmit.add(loadControlObject.getControlName().trim() + "|" + strDataControlName);
                                stringListSubmit.add(loadControlObject.getControlName().trim() + "_id" + "|" + strDataControlId);
                            }

                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                sfString.add(loadControlObject.getControlName() + "_id" + "|" + "");
                            }else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                stringListSubmit.add(loadControlObject.getControlName() + "_id" + "|" + "");
                            }

                        }
                    }
                } else  if (dataControl.getDataControl().getVisibility() == View.GONE || dataControl.getDataControl().getVisibility() == View.INVISIBLE) {
                    if (dataControl.dropdownItemIsSelected()) {
                        strDataControlId = dataControl.getSelectedDropDownItemId();
                        strDataControlName = dataControl.getSelectedDropDownItemName();
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName().trim() + "|" + strDataControlName);
                                sfString.add(loadControlObject.getControlName().trim() + "_id" + "|" + strDataControlId);
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName().trim() + "|" + strDataControlName);
                                stringListSubmit.add(loadControlObject.getControlName().trim() + "_id" + "|" + strDataControlId);
                            }
                    }else{
                        if (isSubForm) {
                            sfString.add(loadControlObject.getControlName() + "|" + "");
                            sfString.add(loadControlObject.getControlName() + "_id" + "|" + "");
                        }else {
                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            stringListSubmit.add(loadControlObject.getControlName() + "_id" + "|" + "");
                        }
                    }
                }

                if (mandatoryColumns.contains(loadControlObject.getControlName())) {
                    if (dataControl.dropdownItemIsSelected()) {
                        dataControl.setAlertDataControl().setVisibility(View.GONE);
                        validationFlag = true;
                    } else {
                        String alertMessage = loadControlObject.getControlName() + " is required";
                        dataControl.setAlertDataControl().setVisibility(View.VISIBLE);
                        dataControl.setAlertDataControl().setText(alertMessage);
                        validationFlag = false;
                        ImproveHelper.setFocus(dataControl.getDataControl());

                    }
                }
                break;
            case CONTROL_TYPE_LiveTracking:
                stringListSubmit.add(loadControlObject.getControlName() + "|NA");

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

                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strACValue);
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strACValue.trim());
                                    }

                                    validationFlag = true;
                                    Log.e(TAG, "controlSubmitValidation: AutoCompleteText - " + CONTROL_TYPE_AUTO_COMPLETION + " - " + strACValue);
                                } else {
                                    autoCompletionControl.setAlertAutoCompleteText().setVisibility(View.VISIBLE);
                                    autoCompletionControl.setAlertAutoCompleteText().setText(loadControlObject.getMandatoryFieldError());
                                    ImproveHelper.setFocus(autoCompletionControl.getAutoCompletionControlView());
                                    validationFlag = false;
                                }
                            }/* else if (!autoCompletionControl.gettap_text().getText().toString().isEmpty() && ((loadControlObject.isCurrentLocation() || loadControlObject.isReadFromQRCode() || loadControlObject.isReadFromQRCode())
                                    && !autoCompletionControl.gettap_text().getText().toString().contentEquals(context.getResources().getString(R.string.tap_here_to_scan_qr_code))
                                    && !autoCompletionControl.gettap_text().getText().toString().contentEquals(context.getResources().getString(R.string.tap_here_to_scan_bar_code)))) {
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + autoCompletionControl.gettap_text().getText().toString());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + autoCompletionControl.gettap_text().getText().toString().trim());
                                    }
                                }
                            } else {
                                autoCompletionControl.setAlertAutoCompleteText().setVisibility(View.VISIBLE);
                                autoCompletionControl.setAlertAutoCompleteText().setText(loadControlObject.getMandatoryFieldError());
                                ImproveHelper.setFocus(autoCompletionControl.getAutoCompletionControlView());
                                validationFlag = false;
                            }
                        } */else{
                                if (autoCompletionControl.getAutoCompleteInputValue() != null && !autoCompletionControl.getAutoCompleteInputValue().isEmpty()) {
                                    strACValue = autoCompletionControl.getAutoCompleteInputValue();

                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strACValue.trim());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strACValue.trim());
                                    }

                                } else {

                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                    }

                                }
                            }
                        } else if (autoCompletionControl.getAutoCompletionControlView().getVisibility() == View.GONE) { // AutoComplete Input Invisible Case
                            if (autoCompletionControl.getAutoCompleteInputValue() != null && !autoCompletionControl.getAutoCompleteInputValue().isEmpty()) {
                                strACValue = autoCompletionControl.getAutoCompleteInputValue();
                                if (strACValue.length() > 0) {

                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strACValue);
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strACValue.trim());
                                    }

                                }

                            } else {

                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                }

                            }
                        }
                    }


                }
                break;
            case CONTROL_TYPE_COUNT_DOWN_TIMER:
                CountDownTimerControl countDownTimerControl = (CountDownTimerControl) List_ControlClassObjects.get(loadControlObject.getControlName());

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


                break;
            case CONTROL_TYPE_COUNT_UP_TIMER:
                CountUpTimerControl countUpTimerControl = (CountUpTimerControl) List_ControlClassObjects.get(loadControlObject.getControlName());

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
                    }

                break;
            case CONTROL_TYPE_VIEWFILE:
                ViewFileControl viewFileControl = (ViewFileControl) List_ControlClassObjects.get(loadControlObject.getControlName());

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
                    }

                break;
        }
        return validationFlag;

    }

    public List<String> getDataCollectionString() {

        return stringListSubmit;
    }

    public List<HashMap<String, String>> getStringListFiles() {
        return stringListFiles;
    }
}
