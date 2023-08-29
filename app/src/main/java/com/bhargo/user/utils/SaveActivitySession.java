package com.bhargo.user.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.bhargo.user.Java_Beans.AllControls;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.controls.advanced.Gps_Control;
import com.bhargo.user.controls.advanced.GridControl;
import com.bhargo.user.controls.advanced.SubformView;
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
import com.bhargo.user.controls.standard.VideoRecording;
import com.bhargo.user.controls.standard.VoiceRecording;
import com.google.android.gms.maps.model.LatLng;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUDIO_PLAYER;
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
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_GRID_CONTROL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_IMAGE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_LARGE_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_LiveTracking;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_NUMERIC_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PASSWORD;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PERCENTAGE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PHONE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_QR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_RADIO_BUTTON;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_RATING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SIGNATURE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SUBFORM;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_TEXT_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_URL_LINK;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_RECORDING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VOICE_RECORDING;

public class SaveActivitySession {

    //=======Load_Onchange_EventObjects================

    private static final String TAG = "ControlsValidation";
    public List<String> subFormMainAppendString = new ArrayList<>();
    Context context;
    LinkedHashMap<String, Object> List_ControlClassObjects;
    AllControls allControls;
    List<String> stringListSubmit = new ArrayList<>();
    List<HashMap<String, String>> stringListFiles = new ArrayList<>();
    private String emailPatternMain = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    List<String> list_TableColums;


    public SaveActivitySession(Context context,
                               LinkedHashMap<String, Object> List_ControlClassObjects, List<String> list_TableColums) {
        this.context = context;

        this.List_ControlClassObjects = List_ControlClassObjects;
        this.list_TableColums=list_TableColums;

    }


    public void save(ControlObject loadControlObject, boolean isSubForm, List<String> sfString, List<HashMap<String, String>> sfFilesString, String subFormId, int subFormRowId, int controlPos) {

//        boolean 
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
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())){
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strTextInputValue);
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                    }
                                }
//                                
                                Log.e(TAG, "controlSubmitValidation: TextInput - " + CONTROL_TYPE_TEXT_INPUT + " - " + strTextInputValue);
                            }
                        } else if (!textInput.gettap_text().getText().toString().isEmpty() && (loadControlObject.isCurrentLocation() || loadControlObject.isReadFromQRCode() || loadControlObject.isReadFromQRCode())) {
                        if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())){
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + textInput.gettap_text().getText().toString());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + textInput.gettap_text().getText().toString().trim());
                            }
                         }
                        }
                    } else {
                        if (textInput.getTextInputValue() != null && !textInput.getTextInputValue().isEmpty()) {
                            strTextInputValue = textInput.getTextInputValue();
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                }
                            }
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
                    }
                } else if (textInput.getTextInputView().getVisibility() == View.GONE) { // Text Inpput Invisible Case
                    if (textInput.getTextInputValue() != null && !textInput.getTextInputValue().isEmpty()) {
                        strTextInputValue = textInput.getTextInputValue();
                        if (strTextInputValue.length() > 0) {
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strTextInputValue);
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                }
                            }
                        }

                    }else{
                        if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            }
                        }
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


                                    
                                    Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");

                                } else {

                                    if (loadControlObject.isEnableLowerLimit() && (new BigDecimal(loadControlObject.getLowerLimit()).compareTo(bInput) == 1)) {

                                    } else {

                                        BigDecimal bstrlength = new BigDecimal(String.valueOf(strNumericValue.length()));

                                        if (loadControlObject.isEnableCappingDigits() && bstrlength.compareTo(new BigDecimal(loadControlObject.getCappingDigits())) == 1) {

                                            Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                        } else {
                                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                                }
                                            }
                                            
                                            Log.e(TAG, "controlSubmitValidation: Numeric - " + strNumericValue);
                                        }
                                    }
                                }


                            }
                        }
                    } else {
                        if (numericInput.getNumericValue() != null && !numericInput.getNumericValue().isEmpty()) {
                            strNumericValue = numericInput.getNumericValue();
                            Log.e(TAG, "controlSubmitValidation: Numeric - " + strNumericValue);
                            numericInput.setAlertNumeric().setVisibility(View.GONE);
                            BigDecimal bInput = new BigDecimal(strNumericValue);

                            if (loadControlObject.isEnableUpperLimit() && bInput.compareTo(new BigDecimal(loadControlObject.getUpperLimit())) == 1) {



                            } else {

                                if (loadControlObject.isEnableLowerLimit() && (new BigDecimal(loadControlObject.getLowerLimit()).compareTo(bInput) == 1)) {

                                    Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                } else {

                                    BigDecimal bstrlength = new BigDecimal(String.valueOf(strNumericValue.length()));

                                    if (loadControlObject.isEnableCappingDigits() && bstrlength.compareTo(new BigDecimal(loadControlObject.getCappingDigits())) == 1) {

                                        Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                    } else {
                                        if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                            }
                                        }
                                    }
                                }
                            }
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }

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

                                } else {
                                    if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strPhoneValue.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strPhoneValue);
                                        }
                                    }
                                    
                                    Log.e(TAG, "controlSubmitValidation: " + CONTROL_TYPE_PHONE + " - " + strPhoneValue);
                                }
                            }
                        }
                    } else {
                        if (phoneInput.getPhoneValue() != null && !phoneInput.getPhoneValue().isEmpty()) {
                            strPhoneValue = phoneInput.getPhoneValue();
                            BigDecimal bstrlength = new BigDecimal(String.valueOf(strPhoneValue.length()));

                            if (loadControlObject.isEnableCappingDigits() && bstrlength.compareTo(new BigDecimal(loadControlObject.getCappingDigits())) == 1) {

                            } else {
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strPhoneValue.trim());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strPhoneValue);
                                    }
                                }
                                Log.e(TAG, "controlSubmitValidation: " + CONTROL_TYPE_PHONE + " - " + strPhoneValue);
                            }
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
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
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());
                                    }
                                }
                                email.setAlertEmail().setVisibility(View.GONE);
                                
                            }
                        }
                    } else {
                        if (email.getEmailValue() != null && !email.getEmailValue().isEmpty()) {
                            strEmailValue = email.getEmailValue();
                            if (isSubForm) {
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());
                                }
                            } else {
                                if (strEmailValue.length() > 0 && strEmailValue.matches(emailPatternMain)) {
                                    if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());
                                    }
                                }
                            }
                            Log.e(TAG, "controlSubmitValidationEmail: " + CONTROL_TYPE_EMAIL + " - " + strEmailValue);
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
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



                            } else {

                                if (loadControlObject.isEnableMinCharacters() && (new BigDecimal(loadControlObject.getMinCharacters())).compareTo(bInput) == 1) {

                                } else {
                                    if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                        }
                                    }
                                    
                                }
                            }
                            Log.e(TAG, "controlSubmitValidation: LargeInput - " + strLargeInput);
                        }
                    } else {
                        if (largeInput.getCustomEditText().getText().toString().length() > 0) {
                            strLargeInput = largeInput.getCustomEditText().getText().toString();
                            BigDecimal bInput = new BigDecimal(strLargeInput.length());

                            if (loadControlObject.isEnableMaxCharacters() && bInput.compareTo(new BigDecimal(loadControlObject.getMaxCharacters())) == 1) {


                            } else {

                                if (loadControlObject.isEnableMinCharacters() && (new BigDecimal(loadControlObject.getMinCharacters())).compareTo(bInput) == 1) {

                                } else {
                                    if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                        }
                                    }
                                    Log.e(TAG, "controlSubmitValidation:" + CONTROL_TYPE_LARGE_INPUT + " - " + strLargeInput);
                                }
                            }
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
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
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
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
                            }

                            
                        }
                    } else {
                        if (camera.getBitmapSetValues() != null) {
                            Log.e(TAG, "controlSubmitValidation: " + loadControlObject.getControlName() + "|" + "NA");
//                            stringListSubmit.add(loadControlObject.getControlName() + " | " + "NA");
//                            stringListFiles.add(camera.getControlRealPath().getTag().toString());
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
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
                            }

                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
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
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
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

                            

                        }
                    } else {
                        if (!fileBrowsing.getTapTextView().getText().toString().equalsIgnoreCase(context.getString(R.string.tap_here_to_select_file))
                                && fileBrowsing.getTapTextView().getText().toString().length() > 0) {
                            strFilePath = fileBrowsing.getFileBrowsingPath();
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListFiles.add(fileBrowsing.getControlRealPath().getTag().toString());
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
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

                            Log.e(TAG, "controlSubmitValidation:" + CONTROL_TYPE_FILE_BROWSING + "-" + strFilePath);
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
                    }

                }
                break;
            case CONTROL_TYPE_CALENDER:
                Calendar calendar = (Calendar) List_ControlClassObjects.get(loadControlObject.getControlName());
                String strCalendarValue = null;
                if (calendar.getCalnderView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                    if (loadControlObject.isNullAllowed()) {
                        if (calendar.getCalendarValue() != null && !calendar.getCalendarValue().isEmpty()) {
                            strCalendarValue = calendar.getCalendarValue();
                            if (strCalendarValue.length() > 0) {
                                Log.e(TAG, "controlSubmitValidation: Calendar - " + strCalendarValue);

                                calendar.setAlertCalendar().setVisibility(View.GONE);
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strCalendarValue.trim());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strCalendarValue.trim());
                                    }
                                }
                                

                            }
                        }
                    } else {
                        if (calendar.getCalendarValue() != null) {
                            strCalendarValue = calendar.getCalendarValue();
                            if (strCalendarValue.length() > 0) {
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strCalendarValue.trim());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strCalendarValue);
                                    }
                                }
                                Log.e(TAG, "controlSubmitValidation: Calendar - " + strCalendarValue);
                            }
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
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
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
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
                                }
                                
                            }

                        }
                    } else {
                        if (checkBox.getSelectedNameCheckboxString() != null && !checkBox.getSelectedNameCheckboxString().isEmpty()) {
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
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
                            }
                            Log.e(TAG, "controlSubmitValidation: " + CONTROL_TYPE_CHECKBOX + "-" + checkBox.getSelectedNameCheckboxString());
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                }
                            }
                        }
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
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    if (radioGroupView.getSelectedRadioButtonText() != null && radioGroupView.getSelectedRadioButtonText().length() > 0) {
                                        sfString.add(loadControlObject.getControlName() + "|" + radioGroupView.getSelectedRadioButtonText());
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + ImproveHelper.getSelectedId(radioGroupView.getSelectedRadioButtonText(), loadControlObject));
                                    } else {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                    }
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "|" + radioGroupView.getSelectedRadioButtonText());
                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + ImproveHelper.getSelectedId(radioGroupView.getSelectedRadioButtonText(), loadControlObject));
                                }
                            }
                            
                        }
                    } else {
                        if (radioGroupView.isRadioGroupViewSelected()) {
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    if (radioGroupView.getSelectedRadioButtonText() != null && radioGroupView.getSelectedRadioButtonText().length() > 0) {
                                        sfString.add(loadControlObject.getControlName() + "|" + radioGroupView.getSelectedRadioButtonText());
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + ImproveHelper.getSelectedId(radioGroupView.getSelectedRadioButtonText(), loadControlObject));

                                    } else {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                    }
//                                sfString.add(loadControlObject.getControlName() + "|" + radioGroupView.getSelectedRadioButtonText());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "|" + radioGroupView.getSelectedRadioButtonText());
                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + ImproveHelper.getSelectedId(radioGroupView.getSelectedRadioButtonText(), loadControlObject));

                                }
                            }
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                }
                            }
                        }
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
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + ImproveHelper.getSelectedId(dropDown.getSelectedDropDownItem(), loadControlObject));
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + ImproveHelper.getSelectedId(dropDown.getSelectedDropDownItem(), loadControlObject));
                                }
                            }
                            

                        }
                    } else {
                        if (dropDown.dropdownItemIsSelected()) {
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + ImproveHelper.getSelectedId(dropDown.getSelectedDropDownItem(), loadControlObject));
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + ImproveHelper.getSelectedId(dropDown.getSelectedDropDownItem(), loadControlObject));
                                }
                            }
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                }
                            }
                        }
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
                            
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + ImproveHelper.getSelectedId(checkList.getSelectedNameCheckListString(), loadControlObject));
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + ImproveHelper.getSelectedId(checkList.getSelectedNameCheckListString(), loadControlObject));
                                }
                            }
                            Log.d(TAG, "controlSubmitValidation: " + checkList.getCheckList());
                        }
                    } else {
                        if (checkList.checkListItemIsSelected() && !checkList.getSelectedNameCheckListString().isEmpty()) {
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + ImproveHelper.getSelectedId(checkList.getSelectedNameCheckListString(), loadControlObject));
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + ImproveHelper.getSelectedId(checkList.getSelectedNameCheckListString(), loadControlObject));
                                }
                            }
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                }
                            }
                        }
                    }
                }
                break;
            case CONTROL_TYPE_RATING:
                Rating rating = (Rating) List_ControlClassObjects.get(loadControlObject.getControlName());
                if (rating.getRatingView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                    if (loadControlObject.isNullAllowed()) {
                        if (rating.getTotalRating().equalsIgnoreCase("0.0")
                                || rating.getTotalRating().equalsIgnoreCase("0")
                                || rating.getTotalRating().equalsIgnoreCase("")) {

                        } else {
                            Log.d(TAG, "CONTROL_TYPE_RATING: " + rating.getTotalRating());
                            rating.setAlertRating().setVisibility(View.GONE);
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
                                }
                            }
                            
                        }
                    } else {
                        if (rating.getTotalRating() != null && !rating.getTotalRating().isEmpty()) {
                            Log.d(TAG, "CONTROL_TYPE_RATING: " + rating.getTotalRating());
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
                                }
                            }
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
                    }

                }
                break;
            case CONTROL_TYPE_VOICE_RECORDING:
                VoiceRecording voiceRecording = (VoiceRecording) List_ControlClassObjects.get(loadControlObject.getControlName());
                if (voiceRecording.getVoiceRecordingView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                    if (loadControlObject.isNullAllowed()) {
                        if (!voiceRecording.setIsVoiceRecorded()) {


                        } else {
                            voiceRecording.setAlertVoiceRecording().setVisibility(View.GONE);
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListVideoAudio.add(voiceRecording.getControlRealPath().getTag().toString());
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
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

                            
                        }

                    } else {
                        if (voiceRecording.setIsVoiceRecorded()) {
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListVideoAudio.add(voiceRecording.getControlRealPath().getTag().toString());
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
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

                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
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

                        } else {
                            videoRecording.setAlertVideoRecording().setVisibility(View.GONE);
                            strVideobase64 = videoRecording.getVideoFileToBase64(videoRecording.getFromCameraOrGalleyURI());
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListVideoAudio.add(videoRecording.getControlRealPath().getTag().toString());
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
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
                            
                        }
                    } else {
                        if (videoRecording.isVideoRecorded()) {
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListVideoAudio.add(videoRecording.getControlRealPath().getTag().toString());
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
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

                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
                    }

                }

                break;

            case CONTROL_TYPE_AUDIO_PLAYER:

                break;
            case CONTROL_TYPE_VIDEO_PLAYER:

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
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                                }
                            }
                            
                            Log.d(TAG, "controlSubmitValidation" + " - " + strPercentage);
                        }
                    } else {
                        if (!percentage.getCustomEditText().getText().toString().equalsIgnoreCase("")) {
                            strPercentage = percentage.getCustomEditText().getText().toString();
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                                }
                            }
                            Log.d(TAG, "controlSubmitValidation" + " - " + strPercentage);
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
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

                        } else {
                            signatureView.setAlertSignature().setVisibility(View.GONE);
//                            bitmapSignature = signatureView.isSignatureBitMap();
//                            stringListFiles.add(signatureView.getControlRealPath().getTag().toString());
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
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
                            
                            Log.d(TAG, "controlSubmitValidation" + " - " + bitmapSignature);
                        }
                    } else {
                        if (!signatureView.isSignatureView()) {
                            if (signatureView.isSignatureBitMap() != null) {
                                bitmapSignature = signatureView.isSignatureBitMap();
//                                stringListFiles.add(signatureView.getControlRealPath().getTag().toString());
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
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
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
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

                        } else {
                            decimalView.setAlertDecimalView().setVisibility(View.GONE);
                            strDecimalValue = decimalView.getCustomEditText().getText().toString();
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                                }
                            }

                            
                            Log.d(TAG, "controlSubmitValidation" + " - " + strDecimalValue);
                        }
                    } else {
                        if (decimalView.getCustomEditText().getText().toString() != null && !decimalView.getCustomEditText().getText().toString().isEmpty()) {
                            strDecimalValue = decimalView.getCustomEditText().getText().toString();
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                                }
                            }
                            Log.d(TAG, "controlSubmitValidation" + " - " + strDecimalValue);
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
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

                        } else {
                            password.setAlertPasswordView().setVisibility(View.GONE);
                            strPasswordValue = password.getCustomEditText().getText().toString();
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                                }
                            }

                            
                            Log.d(TAG, "controlSubmitValidation" + " - " + strPasswordValue);
                        }
                    } else {
                        if (!password.getCustomEditText().getText().toString().equalsIgnoreCase("")) {
                            strPasswordValue = password.getCustomEditText().getText().toString();
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                                }
                            }
                            Log.d(TAG, "controlSubmitValidation" + " - " + strPasswordValue);
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
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



                                } else {

                                    if (loadControlObject.isEnableMinimumAmount() && (new BigDecimal(loadControlObject.getMinAmount()).compareTo(bInput) == 1)) {

                                    } else {
                                        if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                                            }
                                        }
                                        
                                        Log.e(TAG, "controlSubmitValidation: Currency - " + strCurrencyValue);
                                    }
                                }


                            }
                        }
                    } else {
                        if (currency.getCustomEditText().getText() != null
                                && !currency.getCustomEditText().getText().toString().isEmpty()) {
                            strCurrencyValue = currency.getCustomEditText().getText().toString();
                            Log.e(TAG, "controlSubmitValidation: Currency - " + strCurrencyValue);
                            currency.setAlertCurrency().setVisibility(View.GONE);
                            BigDecimal bInput = new BigDecimal(strCurrencyValue);

                            if (loadControlObject.isEnableMaximumAmount() && bInput.compareTo(new BigDecimal(loadControlObject.getMaxAmount())) == 1) {



                            } else {

                                if (loadControlObject.isEnableMinimumAmount() && (new BigDecimal(loadControlObject.getMinAmount()).compareTo(bInput) == 1)) {


                                } else {
                                    if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                                        }
                                    }

                                }
                            }
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }

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

//                validationFlag = gridView.formValidated(gridView.getSubFormView(), gridView,true);
                gridView.getSubFormString();
                Log.d(TAG, "controlSubmitValidationSubForm: " + gridView.getSubFormString());


                break;
            case CONTROL_TYPE_SUBFORM:

                SubformView subformView = (SubformView) List_ControlClassObjects.get(loadControlObject.getControlName());

//                validationFlag = subformView.formValidated(subformView.getSubFormView(), subformView,false);
                subformView.getSubFormString();
                Log.d(TAG, "controlSubmitValidationSubForm: " + subformView.getSubFormString());


                break;

            case CONTROL_TYPE_GPS:

                Gps_Control controlGPS = (Gps_Control) List_ControlClassObjects.get(loadControlObject.getControlName());
                if (controlGPS.getControlGPSView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                    if (loadControlObject.isNullAllowed()) {
                        if (controlGPS.getLatLngList().size() > 0) {
                            controlGPS.setAlertGPS().setVisibility(View.GONE);
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    String GPSSTR = "";
                                    List<LatLng> latLngList = controlGPS.getLatLngList();
                                    for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                        LatLng latlang = latLngList.get(i);
                                        GPSSTR = GPSSTR + " ^" + latlang.latitude + "$" + latlang.longitude;
                                    }
                                    GPSSTR = GPSSTR.substring(GPSSTR.indexOf("^") + 1);
                                    sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                    sfString.add(loadControlObject.getControlName() + "_Type" + "| " + controlGPS.getGPS_Type());

                                } else {
                                    String GPSSTR = "";
                                    List<LatLng> latLngList = controlGPS.getLatLngList();
                                    for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                        LatLng latlang = latLngList.get(i);
                                        GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                    }
                                    GPSSTR = GPSSTR.substring(GPSSTR.indexOf("^") + 1);

                                    stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                    stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + controlGPS.getGPS_Type());
                                }
                            }

                            
                        }
                    } else {
                        if (controlGPS.getLatLngList().size() != 0) {
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    String GPSSTR = "";
                                    List<LatLng> latLngList = controlGPS.getLatLngList();
                                    for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                        LatLng latlang = latLngList.get(i);
                                        GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                    }
                                    GPSSTR = GPSSTR.substring(GPSSTR.indexOf("^") + 1);
                                    sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                    sfString.add(loadControlObject.getControlName() + "_Type" + "|" + controlGPS.getGPS_Type());

                                } else {
                                    String GPSSTR = "";
                                    List<LatLng> latLngList = controlGPS.getLatLngList();
                                    for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                        LatLng latlang = latLngList.get(i);
                                        GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                    }
                                    GPSSTR = GPSSTR.substring(GPSSTR.indexOf("^") + 1);

                                    stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                    stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + controlGPS.getGPS_Type());
                                }
                            }
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
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

                            

                        } else {
                            
                            dataControl.setAlertDataControl().setVisibility(View.GONE);
                            strDataControlId = dataControl.getSelectedDropDownItemId();
                            strDataControlName = dataControl.getSelectedDropDownItemName();
//                            Log.d(TAG, "controlSubmitValidation" + " - " + strDataControlValue);
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
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
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName().trim() + "|" + strDataControlName);
                                    sfString.add(loadControlObject.getControlName().trim() + "_id" + "|" + strDataControlId);

                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "|" + strDataControlName);
                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "_id" + "|" + strDataControlId);
                                }
                            }
                        }else{
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }
                        }
                    }
                }
                break;
            case CONTROL_TYPE_LiveTracking:
                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                    stringListSubmit.add(loadControlObject.getControlName() + "|NA");
                }
                break;
        }


    }

    public List<String> getDataCollectionString() {

        return stringListSubmit;
    }

    public List<HashMap<String, String>> getStringListFiles() {
        return stringListFiles;
    }
}
