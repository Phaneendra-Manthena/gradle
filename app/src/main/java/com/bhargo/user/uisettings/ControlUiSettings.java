package com.bhargo.user.uisettings;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUDIO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_COMPLETION;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BAR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BUTTON;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CALENDAR_EVENT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CALENDER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CAMERA;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHART;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHECK_LIST;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_COUNT_DOWN_TIMER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_COUNT_UP_TIMER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CURRENCY;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CUSTOM_HEADER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CUSTOM_IMAGE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DATA_TABLE;
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
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PROGRESS;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_QR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_RATING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SIGNATURE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_TEXT_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_TIME;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_URL_LINK;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_USER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_RECORDING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIEWFILE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VOICE_RECORDING;
import static com.bhargo.user.utils.AppConstants.MATCH_PARENT;
import static com.bhargo.user.utils.ImproveHelper.pxToDPControlUI;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.controls.advanced.AutoCompletionControl;
import com.bhargo.user.controls.advanced.BarCode;
import com.bhargo.user.controls.advanced.ChartControl;
import com.bhargo.user.controls.advanced.DataTableControl;
import com.bhargo.user.controls.advanced.Gps_Control;
import com.bhargo.user.controls.advanced.PostControl;
import com.bhargo.user.controls.advanced.ProgressControl;
import com.bhargo.user.controls.advanced.QRCode;
import com.bhargo.user.controls.advanced.UserControl;
import com.bhargo.user.controls.standard.AudioPlayer;
import com.bhargo.user.controls.standard.Button;
import com.bhargo.user.controls.standard.Calendar;
import com.bhargo.user.controls.standard.CalendarEventControl;
import com.bhargo.user.controls.standard.Camera;
import com.bhargo.user.controls.standard.CheckList;
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
import com.bhargo.user.uisettings.pojos.ControlUIProperties;
import com.bhargo.user.uisettings.pojos.UILayoutProperties;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;

import java.util.LinkedHashMap;

public class ControlUiSettings {

    ControlObject controlObject;
    UILayoutProperties uiLayoutProperties;
    ControlUIProperties controlUIProperties;
    public LinkedHashMap<String, Object> List_ControlClassObjects;
    Context context;
    ImproveHelper improveHelper;


    public ControlUiSettings(ControlObject controlObject, UILayoutProperties uiLayoutProperties, ControlUIProperties controlUIProperties,LinkedHashMap<String, Object> List_ControlClassObjects,Context context) {
        this.controlObject = controlObject;
        this.uiLayoutProperties = uiLayoutProperties;
        this.controlUIProperties = controlUIProperties;
        this.List_ControlClassObjects = List_ControlClassObjects;
        this.context = context;
        improveHelper = new ImproveHelper();
    }


    public void setControlUiSettings() {

        try {
            if (controlUIProperties != null) {
                String strControlType = controlObject.getControlType();
                String strControlName = controlObject.getControlName();
                switch (strControlType) {
                    case CONTROL_TYPE_TEXT_INPUT:

                        TextInput textInput = (TextInput) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) textInput.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpLCtrl = null;
                            LinearLayout.LayoutParams lpEditText = null;
                            LinearLayout.LayoutParams lpTapText = null;
                            LinearLayout.LayoutParams lpLeftRightWeight = null;
                            LinearLayout.LayoutParams lpDisplayName = null;


                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("1")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) textInput.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) textInput.getLl_tap_text().getLayoutParams();

                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                        lpEditText = (LinearLayout.LayoutParams) textInput.getCustomEditText().getLayoutParams();
                                        lpEditText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpEditText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("2")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) textInput.getLayout_control().getLayoutParams();
                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("3")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) textInput.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) textInput.getLl_tap_text().getLayoutParams();
                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("5")) {
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpLCtrl = (LinearLayout.LayoutParams) textInput.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) textInput.getLl_tap_text().getLayoutParams();
                                        lpLeftRightWeight = (LinearLayout.LayoutParams) textInput.getLl_leftRightWeight().getLayoutParams();
                                        lpDisplayName = (LinearLayout.LayoutParams) textInput.getLl_displayName().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpLeftRightWeight.weight = 1f;

                                        lpDisplayName.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpDisplayName.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpDisplayName.weight = 0.4f;

                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpLCtrl.weight = 0.6f;


                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    }
                                } else {
                                    lpLCtrl = (LinearLayout.LayoutParams) textInput.getLayout_control().getLayoutParams();
                                    lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                }
                            }
                            if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("1")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) textInput.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) textInput.getLl_tap_text().getLayoutParams();

                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpEditText = (LinearLayout.LayoutParams) textInput.getCustomEditText().getLayoutParams();
                                        lpEditText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpEditText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("2")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) textInput.getLayout_control().getLayoutParams();
                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("3")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) textInput.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) textInput.getLl_tap_text().getLayoutParams();
                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("5")) {
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpLCtrl = (LinearLayout.LayoutParams) textInput.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) textInput.getLl_tap_text().getLayoutParams();
                                        lpLeftRightWeight = (LinearLayout.LayoutParams) textInput.getLl_leftRightWeight().getLayoutParams();
                                        lpDisplayName = (LinearLayout.LayoutParams) textInput.getLl_displayName().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.weight = 1f;

                                        lpDisplayName.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpDisplayName.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpDisplayName.weight = 0.4f;

                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.weight = 0.6f;


                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    }
                                } else {
                                    lpLCtrl = (LinearLayout.LayoutParams) textInput.getLayout_control().getLayoutParams();
                                    lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                }
                            }

                            textInput.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            if (lpLCtrl != null) {
                                textInput.getLayout_control().setLayoutParams(lpLCtrl);
                            }
                            if (lpLeftRightWeight != null) {
                                textInput.getLl_leftRightWeight().setLayoutParams(lpLeftRightWeight);
                            }
                            if (lpEditText != null) {
                                textInput.getCustomEditText().setLayoutParams(lpEditText);
                            }
                            if (lpTapText != null) {
                                textInput.getLl_tap_text().setLayoutParams(lpTapText);
                            }
                            if (lpDisplayName != null) {
                                textInput.getLl_displayName().setLayoutParams(lpDisplayName);
                            }


                        }
                        controlUIDisplaySettings(controlUIProperties, textInput.getTv_displayName());
                        controlAlignments(uiLayoutProperties, textInput.getLl_main_inside());

                        break;

                    case CONTROL_TYPE_NUMERIC_INPUT:
                        NumericInput numericInput = (NumericInput) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();
                            ViewGroup.LayoutParams lpCtrlUI = numericInput.getLl_control_ui().getLayoutParams();
                            ViewGroup.LayoutParams lpLCtrl = numericInput.getLayout_control().getLayoutParams();
                            ViewGroup.LayoutParams lpTapText = numericInput.getLl_tap_text().getLayoutParams();
                            ViewGroup.LayoutParams editText = numericInput.getNumericTextView().getLayoutParams();

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                lpCtrlUI.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                lpLCtrl.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                lpLCtrl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                editText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                editText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                lpLCtrl.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpLCtrl.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                int heightFontMinus = uiLayoutProperties.getLayoutHeightInPixel() - improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getFontSize()));
//                        int heightMatchParent = heightFontMinus-improveHelper.dpToPx(Integer.parseInt("20"));

                                lpTapText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpTapText.height = heightFontMinus;

                                editText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                editText.height = heightFontMinus;

                            }
                            numericInput.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            numericInput.getLayout_control().setLayoutParams(lpLCtrl);
                            numericInput.getLl_tap_text().setLayoutParams(lpTapText);
                            numericInput.getNumericTextView().setLayoutParams(editText);


                        }
                        controlUIDisplaySettings(controlUIProperties, numericInput.getTv_displayName());
                        controlAlignments(uiLayoutProperties, numericInput.getLl_main_inside());
//                    }

                        break;
                    case CONTROL_TYPE_PHONE:

//                    if (List_ControlClassObjects.get(strControlName) instanceof Phone) {
                        Phone phone = (Phone) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();
                            ViewGroup.LayoutParams lpCtrlUI = phone.getLl_control_ui().getLayoutParams();
                            ViewGroup.LayoutParams lpLCtrl = phone.getLayout_control().getLayoutParams();
                            ViewGroup.LayoutParams lpTapText = phone.getLl_tap_text().getLayoutParams();
                            ViewGroup.LayoutParams editText = phone.getCustomEditText().getLayoutParams();

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                lpCtrlUI.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                lpLCtrl.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                lpLCtrl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                editText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                editText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                lpLCtrl.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpLCtrl.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                int heightFontMinus = uiLayoutProperties.getLayoutHeightInPixel() - improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getFontSize()));
//                        int heightMatchParent = heightFontMinus-improveHelper.dpToPx(Integer.parseInt("20"));

                                lpTapText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpTapText.height = heightFontMinus;

                                editText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                editText.height = heightFontMinus;

                            }
                            phone.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            phone.getLayout_control().setLayoutParams(lpLCtrl);
                            phone.getLl_tap_text().setLayoutParams(lpTapText);
                            phone.getCustomEditText().setLayoutParams(editText);
                        }
                        controlUIDisplaySettings(controlUIProperties, phone.getTv_displayName());
                        controlAlignments(uiLayoutProperties, phone.getLl_main_inside());
//                    }

                        break;
                    case CONTROL_TYPE_EMAIL:
                        Email email = (Email) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();
                            ViewGroup.LayoutParams lpCtrlUI = email.getLl_control_ui().getLayoutParams();
                            ViewGroup.LayoutParams lpLCtrl = email.getLayout_control().getLayoutParams();
                            ViewGroup.LayoutParams lpTapText = email.getLl_tap_text().getLayoutParams();
                            ViewGroup.LayoutParams editText = email.getCustomEditText().getLayoutParams();

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                lpCtrlUI.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                lpLCtrl.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                lpLCtrl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                editText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                editText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                lpLCtrl.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpLCtrl.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                int heightFontMinus = uiLayoutProperties.getLayoutHeightInPixel() - improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getFontSize()));
//                        int heightMatchParent = heightFontMinus-improveHelper.dpToPx(Integer.parseInt("20"));

                                lpTapText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpTapText.height = heightFontMinus;

                                editText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                editText.height = heightFontMinus;

                            }
                            email.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            email.getLayout_control().setLayoutParams(lpLCtrl);
                            email.getLl_tap_text().setLayoutParams(lpTapText);
                            email.getCustomEditText().setLayoutParams(editText);

                        }
                        controlUIDisplaySettings(controlUIProperties, email.getTv_displayName());
                        controlAlignments(uiLayoutProperties, email.getLl_main_inside());

                        break;
                    case CONTROL_TYPE_LARGE_INPUT:
                        LargeInput largeInput = (LargeInput) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();
                            ViewGroup.LayoutParams lpCtrlUI = largeInput.getLl_control_ui().getLayoutParams();
                            ViewGroup.LayoutParams lpLCtrl = largeInput.getLayout_control().getLayoutParams();
                            ViewGroup.LayoutParams lpTapText = largeInput.getLl_tap_text().getLayoutParams();
                            ViewGroup.LayoutParams editText = largeInput.getCustomEditText().getLayoutParams();

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                lpCtrlUI.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                lpLCtrl.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                lpLCtrl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                editText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                editText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                lpLCtrl.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpLCtrl.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                int heightFontMinus = uiLayoutProperties.getLayoutHeightInPixel() - improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getFontSize()));
//                        int heightMatchParent = heightFontMinus-improveHelper.dpToPx(Integer.parseInt("20"));

                                lpTapText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpTapText.height = heightFontMinus;

                                editText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                editText.height = heightFontMinus;

                            }
                            largeInput.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            largeInput.getLayout_control().setLayoutParams(lpLCtrl);
                            largeInput.getLl_tap_text().setLayoutParams(lpTapText);
                            largeInput.getCustomEditText().setLayoutParams(editText);

                        }
                        controlUIDisplaySettings(controlUIProperties, largeInput.getTv_displayName());
                        controlAlignments(uiLayoutProperties, largeInput.getLl_main_inside());

                        break;
                    case CONTROL_TYPE_CAMERA:

                        if (List_ControlClassObjects.get(strControlName) instanceof Camera) {
                            Camera camera = (Camera) List_ControlClassObjects.get(strControlName);
                            if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                    && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                    && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                                String strWidthType = controlUIProperties.getTypeOfWidth();
                                String strHeightType = controlUIProperties.getTypeOfHeight();

                                ViewGroup.LayoutParams lpCtrlUI = camera.getLl_control_ui().getLayoutParams();
                                ViewGroup.LayoutParams lpLCtrl = camera.getLayout_control().getLayoutParams();
                                ViewGroup.LayoutParams lpTapText = camera.getLl_tap_text().getLayoutParams();
                                ViewGroup.LayoutParams lpCamDisplay = camera.getLlDisplayRequired().getLayoutParams();

                                if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                    lpCtrlUI.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpCtrlUI.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpLCtrl.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpLCtrl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                    lpCamDisplay.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpCamDisplay.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                    lpCtrlUI.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpCtrlUI.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                    lpLCtrl.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpLCtrl.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                    int heightFontMinus = uiLayoutProperties.getLayoutHeightInPixel() - improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getFontSize()));
//                        int heightMatchParent = heightFontMinus-improveHelper.dpToPx(Integer.parseInt("20"));

                                    lpTapText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpTapText.height = heightFontMinus;

                                    lpCamDisplay.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpCamDisplay.height = heightFontMinus;

                                }
                                camera.getLl_control_ui().setLayoutParams(lpCtrlUI);
                                camera.getLayout_control().setLayoutParams(lpLCtrl);
                                camera.getLl_tap_text().setLayoutParams(lpTapText);
                                camera.getLlDisplayRequired().setLayoutParams(lpCamDisplay);

                            }
                            controlUIDisplaySettings(controlUIProperties, camera.getTv_displayName());
                            controlAlignments(uiLayoutProperties, camera.getLl_main_inside());
                        }

                        break;
                    case CONTROL_TYPE_FILE_BROWSING:

                        if (List_ControlClassObjects.get(strControlName) instanceof FileBrowsing) {
                            FileBrowsing fileBrowsing = (FileBrowsing) List_ControlClassObjects.get(strControlName);
                            if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                    && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                    && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                                String strWidthType = controlUIProperties.getTypeOfWidth();
                                String strHeightType = controlUIProperties.getTypeOfHeight();

                                ViewGroup.LayoutParams lpCtrlUI = fileBrowsing.getLl_control_ui().getLayoutParams();
                                ViewGroup.LayoutParams lpLCtrl = fileBrowsing.getLayout_control().getLayoutParams();
                                ViewGroup.LayoutParams lpTapText = fileBrowsing.getLl_tap_text().getLayoutParams();
                                ViewGroup.LayoutParams lpCamDisplay = fileBrowsing.getLlDisplayRequired().getLayoutParams();

                                if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                    lpCtrlUI.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpCtrlUI.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpLCtrl.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpLCtrl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                    lpCamDisplay.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpCamDisplay.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                    lpCtrlUI.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpCtrlUI.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                    lpLCtrl.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpLCtrl.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                    int heightFontMinus = uiLayoutProperties.getLayoutHeightInPixel() - improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getFontSize()));
//                        int heightMatchParent = heightFontMinus-improveHelper.dpToPx(Integer.parseInt("20"));

                                    lpTapText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpTapText.height = heightFontMinus;

                                    lpCamDisplay.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpCamDisplay.height = heightFontMinus;

                                }
                                fileBrowsing.getLl_control_ui().setLayoutParams(lpCtrlUI);
                                fileBrowsing.getLayout_control().setLayoutParams(lpLCtrl);
                                fileBrowsing.getLl_tap_text().setLayoutParams(lpTapText);
                                fileBrowsing.getLlDisplayRequired().setLayoutParams(lpCamDisplay);
                                if (controlUIProperties.getFontSize() != null && !controlUIProperties.getFontSize().isEmpty()) {
                                    fileBrowsing.getControlRealPath().setTextSize(Float.parseFloat(controlUIProperties.getFontSize()));
                                }
                                if (controlUIProperties.getFontColorHex() != null && !controlUIProperties.getFontColorHex().isEmpty()) {
                                    fileBrowsing.getControlRealPath().setTextColor(Color.parseColor(controlUIProperties.getFontColorHex()));
                                }
                                if (controlUIProperties.getFontStyle() != null && !controlUIProperties.getFontStyle().isEmpty()) {
                                    String style = controlUIProperties.getFontStyle();
                                    if (style != null && style.equalsIgnoreCase("Bold")) {
                                        Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_bold));
                                        fileBrowsing.getControlRealPath().setTypeface(typeface_bold);

                                    } else if (style != null && style.equalsIgnoreCase("Italic")) {
                                        Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_italic));
                                        fileBrowsing.getControlRealPath().setTypeface(typeface_italic);
                                    }
                                }

                            }
                            controlAlignments(uiLayoutProperties, fileBrowsing.getLl_main_inside());
                        }

                        break;
                    case CONTROL_TYPE_CALENDER:

                        if (List_ControlClassObjects.get(strControlName) instanceof Calendar) {
                            Calendar calendar = (Calendar) List_ControlClassObjects.get(strControlName);
                            if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                    && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                    && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                                String strWidthType = controlUIProperties.getTypeOfWidth();
                                String strHeightType = controlUIProperties.getTypeOfHeight();

                                ViewGroup.LayoutParams lpCtrlUI = calendar.getLl_control_ui().getLayoutParams();
                                ViewGroup.LayoutParams lpLCtrl = calendar.getLayout_control().getLayoutParams();
                                ViewGroup.LayoutParams lpTapText = calendar.getLl_tap_text().getLayoutParams();
                                ViewGroup.LayoutParams editText = calendar.getCe_TextType().getLayoutParams();

                                if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                    lpCtrlUI.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpCtrlUI.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpLCtrl.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpLCtrl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                    editText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    editText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                    lpCtrlUI.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpCtrlUI.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                    lpLCtrl.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpLCtrl.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                    int heightFontMinus = uiLayoutProperties.getLayoutHeightInPixel() - improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getFontSize()));
//                        int heightMatchParent = heightFontMinus-improveHelper.dpToPx(Integer.parseInt("20"));

                                    lpTapText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpTapText.height = heightFontMinus;

                                    editText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    editText.height = heightFontMinus;

                                }
                                calendar.getLl_control_ui().setLayoutParams(lpCtrlUI);
                                calendar.getLayout_control().setLayoutParams(lpLCtrl);
                                calendar.getLl_tap_text().setLayoutParams(lpTapText);
                                calendar.getCe_TextType().setLayoutParams(editText);
                                if (controlUIProperties.getFontSize() != null && !controlUIProperties.getFontSize().isEmpty()) {
                                    calendar.getTv_displayName().setTextSize(Float.parseFloat(controlUIProperties.getFontSize()));
                                }
                                if (controlUIProperties.getFontColorHex() != null && !controlUIProperties.getFontColorHex().isEmpty()) {
                                    calendar.getTv_displayName().setTextColor(Color.parseColor(controlUIProperties.getFontColorHex()));
                                }
                                if (controlUIProperties.getFontStyle() != null && !controlUIProperties.getFontStyle().isEmpty()) {
                                    String style = controlUIProperties.getFontStyle();
                                    if (style != null && style.equalsIgnoreCase("Bold")) {
                                        Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_bold));
                                        calendar.getTv_displayName().setTypeface(typeface_bold);

                                    } else if (style != null && style.equalsIgnoreCase("Italic")) {
                                        Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_italic));
                                        calendar.getTv_displayName().setTypeface(typeface_italic);
                                    }
                                }

                            }
                            controlAlignments(uiLayoutProperties, calendar.getLl_main_inside());
                        }

                        break;
                    case CONTROL_TYPE_DROP_DOWN:

                        DropDown dropDown = (DropDown) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) dropDown.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpLCtrl = (LinearLayout.LayoutParams) dropDown.getLayout_control().getLayoutParams();
                            LinearLayout.LayoutParams lpTapText = (LinearLayout.LayoutParams) dropDown.getLl_tap_text().getLayoutParams();
                            LinearLayout.LayoutParams lpSpinner = (LinearLayout.LayoutParams) dropDown.getSearchableSpinner().getLayoutParams();
                            LinearLayout.LayoutParams lpleftRIghtWeight = null;
                            LinearLayout.LayoutParams lpDisplayName = (LinearLayout.LayoutParams) dropDown.getLl_displayName().getLayoutParams();

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {
                                    lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpCtrlUI.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));


                                    lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpLCtrl.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                    lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpTapText.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                    lpleftRIghtWeight = (LinearLayout.LayoutParams) dropDown.getLl_leftRightWeight().getLayoutParams();
                                    lpleftRIghtWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpleftRIghtWeight.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    lpleftRIghtWeight.weight = 1f;

                                    lpDisplayName.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpDisplayName.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpDisplayName.weight = 0.4f;

                                    lpSpinner.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpSpinner.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    lpDisplayName.weight = 0.6f;
                                } else {
                                    lpCtrlUI.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpCtrlUI.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpLCtrl.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpLCtrl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    lpDisplayName.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpDisplayName.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    lpSpinner.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpSpinner.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                }

                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                lpLCtrl.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpLCtrl.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                int heightFontMinus = uiLayoutProperties.getLayoutHeightInPixel() - improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getFontSize()));
//                        int heightMatchParent = heightFontMinus-improveHelper.dpToPx(Integer.parseInt("20"));

                                lpTapText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpTapText.height = heightFontMinus;

                                lpSpinner.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpSpinner.height = heightFontMinus;

                            }
                            dropDown.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            dropDown.getLayout_control().setLayoutParams(lpLCtrl);
                            dropDown.getLl_tap_text().setLayoutParams(lpTapText);
//                            dropDown.getLl_leftRightWeight().setLayoutParams(lpleftRIghtWeight);
                            dropDown.getSearchableSpinner().setLayoutParams(lpSpinner);
//                            dropDown.getSearchableSpinner().setBackground(context.getDrawable(R.drawable.control_dropdown_background_icon_default));
                            if (lpDisplayName != null) {
                                dropDown.getLl_displayName().setLayoutParams(lpDisplayName);
                            }
                            if (lpleftRIghtWeight != null) {
                                dropDown.getLl_leftRightWeight().setLayoutParams(lpleftRIghtWeight);
                            }
//                            dropDown.getSearchableSpinner().setLayoutParams(layoutParams1);
                            if (controlUIProperties.getFontSize() != null && !controlUIProperties.getFontSize().isEmpty()) {
                                dropDown.getTv_displayName().setTextSize(Float.parseFloat(controlUIProperties.getFontSize()));
                            }
                            if (controlUIProperties.getFontColorHex() != null && !controlUIProperties.getFontColorHex().isEmpty()) {
                                dropDown.getTv_displayName().setTextColor(Color.parseColor(controlUIProperties.getFontColorHex()));
                            }
                            if (controlUIProperties.getFontStyle() != null && !controlUIProperties.getFontStyle().isEmpty()) {
                                String style = controlUIProperties.getFontStyle();
                                if (style != null && style.equalsIgnoreCase("Bold")) {
                                    Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_bold));
                                    dropDown.getTv_displayName().setTypeface(typeface_bold);

                                } else if (style != null && style.equalsIgnoreCase("Italic")) {
                                    Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_italic));
                                    dropDown.getTv_displayName().setTypeface(typeface_italic);
                                }
                            }

                        }
                        controlAlignments(uiLayoutProperties, dropDown.getLl_main_inside());

                        break;
                    case CONTROL_TYPE_CHECK_LIST:

                        CheckList checkList = (CheckList) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) checkList.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpLCtrl = null;
                            LinearLayout.LayoutParams lpTapText = (LinearLayout.LayoutParams) checkList.getLl_tap_text().getLayoutParams();
                            LinearLayout.LayoutParams lpSpinner = (LinearLayout.LayoutParams) checkList.getMultiSearchableSpinner().getLayoutParams();
                            LinearLayout.LayoutParams lpleftRIghtWeight = null;
                            LinearLayout.LayoutParams lpDisplayName = (LinearLayout.LayoutParams) checkList.getLl_displayName().getLayoutParams();

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {
                                    lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpCtrlUI.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    lpLCtrl = (LinearLayout.LayoutParams) checkList.getLayout_control().getLayoutParams();

                                    lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpLCtrl.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                    lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpTapText.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                    lpleftRIghtWeight = (LinearLayout.LayoutParams) checkList.getLl_leftRightWeight().getLayoutParams();
                                    lpleftRIghtWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpleftRIghtWeight.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    lpleftRIghtWeight.weight = 1f;

                                    lpDisplayName.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpDisplayName.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpDisplayName.weight = 0.4f;

                                    lpSpinner.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpSpinner.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    lpDisplayName.weight = 0.6f;
                                } else {


                                    lpCtrlUI.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpCtrlUI.height = ViewGroup.LayoutParams.WRAP_CONTENT;

//                                    lpLCtrl.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//                                    lpLCtrl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
//                                    lpDisplayName.width = LinearLayout.LayoutParams.MATCH_PARENT;
//                                    lpDisplayName.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    lpSpinner.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpSpinner.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                }

                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                lpLCtrl.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpLCtrl.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                int heightFontMinus = uiLayoutProperties.getLayoutHeightInPixel() - improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getFontSize()));
//                        int heightMatchParent = heightFontMinus-improveHelper.dpToPx(Integer.parseInt("20"));

                                lpTapText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpTapText.height = heightFontMinus;

                                lpSpinner.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpSpinner.height = heightFontMinus;

                            }
                            checkList.getLl_control_ui().setLayoutParams(lpCtrlUI);

                            checkList.getLl_tap_text().setLayoutParams(lpTapText);
//                            checkList.getLl_leftRightWeight().setLayoutParams(lpleftRIghtWeight);
                            checkList.getMultiSearchableSpinner().setLayoutParams(lpSpinner);
//                            dropDown.getSearchableSpinner().setBackground(context.getDrawable(R.drawable.control_dropdown_background_icon_default));
                            if (lpLCtrl != null) {
                                checkList.getLayout_control().setLayoutParams(lpLCtrl);
                            }
                            if (lpDisplayName != null) {
                                checkList.getLl_displayName().setLayoutParams(lpDisplayName);
                            }
                            if (lpleftRIghtWeight != null) {
                                checkList.getLl_leftRightWeight().setLayoutParams(lpleftRIghtWeight);
                            }
//                            dropDown.getSearchableSpinner().setLayoutParams(layoutParams1);
                            if (controlUIProperties.getFontSize() != null && !controlUIProperties.getFontSize().isEmpty()) {
                                checkList.getTv_displayName().setTextSize(Float.parseFloat(controlUIProperties.getFontSize()));
                            }
                            if (controlUIProperties.getFontColorHex() != null && !controlUIProperties.getFontColorHex().isEmpty()) {
                                checkList.getTv_displayName().setTextColor(Color.parseColor(controlUIProperties.getFontColorHex()));
                            }
                            if (controlUIProperties.getFontStyle() != null && !controlUIProperties.getFontStyle().isEmpty()) {
                                String style = controlUIProperties.getFontStyle();
                                if (style != null && style.equalsIgnoreCase("Bold")) {
                                    Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_bold));
                                    checkList.getTv_displayName().setTypeface(typeface_bold);

                                } else if (style != null && style.equalsIgnoreCase("Italic")) {
                                    Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_italic));
                                    checkList.getTv_displayName().setTypeface(typeface_italic);
                                }
                            }

                        }
                        controlAlignments(uiLayoutProperties, checkList.getLl_main_inside());

                        break;
                    case CONTROL_TYPE_RATING:


                        Rating rating = (Rating) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) rating.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpTapText = null;
                            if (rating.getLl_tap_text() != null) {
                                lpTapText = (LinearLayout.LayoutParams) rating.getLl_tap_text().getLayoutParams();
                            }
                            LinearLayout.LayoutParams lpLlRating = null;
                            if (rating.getLl_rating() != null) {
                                lpLlRating = (LinearLayout.LayoutParams) rating.getLl_rating().getLayoutParams();
                            }
                            LinearLayout.LayoutParams lpLlLabel = null;
                            if (rating.getLl_label() != null) {
                                lpLlLabel = (LinearLayout.LayoutParams) rating.getLl_label().getLayoutParams();
                            }
                            LinearLayout.LayoutParams lpRatingBar = null;
                            if (rating.getRatingBar() != null) {
                                lpRatingBar = (LinearLayout.LayoutParams) rating.getRatingBar().getLayoutParams();
                            }

                            LinearLayout.LayoutParams lpSmileRatingBar = null;
                            if (rating.getSmileRating() != null) {
                                lpSmileRatingBar = (LinearLayout.LayoutParams) rating.getSmileRating().getLayoutParams();
                            }


                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                if (lpTapText != null) {
                                    lpTapText.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    lpTapText.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                }
                                lpLlRating.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpLlRating.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                if (controlObject.getDisplayNameAlignment() != null && lpLlLabel != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {
                                    lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    lpTapText.weight = 1f;
                                    lpLlLabel.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpLlLabel.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpLlLabel.weight = 0.4f;

                                    lpLlRating.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpLlRating.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    lpLlRating.weight = 0.6f;
                                }

                                if (rating.getRatingBar().getVisibility() == View.VISIBLE) {
                                    lpRatingBar.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    lpRatingBar.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                        rating.getRatingBar().setMaxWidth(pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP())));
                                        rating.getRatingBar().setMaxHeight(pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP())));
                                    }

                                    rating.getRatingBar().setScaleX(1);
                                    rating.getRatingBar().setScaleY(1);
                                    if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                                        lpRatingBar.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpRatingBar.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        rating.getRatingBar().setScaleX(0.8f);
                                        rating.getRatingBar().setScaleY(0.8f);
                                    }
                                } else if (rating.getSmileRating().getVisibility() == View.VISIBLE) {

                                    lpSmileRatingBar.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpSmileRatingBar.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                    rating.getSmileRating().setScaleX(1);
                                    rating.getSmileRating().setScaleY(1);
                                }
                            }

                            rating.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            if (lpTapText != null) {
                                rating.getLl_tap_text().setLayoutParams(lpTapText);
                            }
                            if (lpLlRating != null) {
                                rating.getLl_rating().setLayoutParams(lpLlRating);
                            }
                            if (lpLlRating != null) {
                                rating.getLl_label().setLayoutParams(lpLlRating);
                            }
                            if (lpSmileRatingBar != null) {
                                rating.getSmileRating().setLayoutParams(lpSmileRatingBar);
                            }
                            if (controlUIProperties.getFontSize() != null && !controlUIProperties.getFontSize().isEmpty()) {
                                rating.getTv_displayName().setTextSize(Float.parseFloat(controlUIProperties.getFontSize()));
                            }
                            if (controlUIProperties.getFontColorHex() != null && !controlUIProperties.getFontColorHex().isEmpty()) {
                                rating.getTv_displayName().setTextColor(Color.parseColor(controlUIProperties.getFontColorHex()));
                            }
                            if (controlUIProperties.getFontStyle() != null && !controlUIProperties.getFontStyle().isEmpty()) {
                                String style = controlUIProperties.getFontStyle();
                                if (style != null && style.equalsIgnoreCase("Bold")) {
                                    Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_bold));
                                    rating.getTv_displayName().setTypeface(typeface_bold);

                                } else if (style != null && style.equalsIgnoreCase("Italic")) {
                                    Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_italic));
                                    rating.getTv_displayName().setTypeface(typeface_italic);
                                }
                            }

                        }
                        controlAlignments(uiLayoutProperties, rating.getLl_main_inside());

                        break;

                    case CONTROL_TYPE_VOICE_RECORDING:

                        VoiceRecording voiceRecording = (VoiceRecording) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) voiceRecording.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpLCtrl = null;
                            LinearLayout.LayoutParams lpTapText = (LinearLayout.LayoutParams) voiceRecording.getLl_tap_text().getLayoutParams();
                            LinearLayout.LayoutParams lpLl_cg = null;
                            LinearLayout.LayoutParams lpLlRecorderUpload = null;


                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                                    lpLlRecorderUpload = (LinearLayout.LayoutParams) voiceRecording.getLl_recorder_upload().getLayoutParams();

                                    lpCtrlUI.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpCtrlUI.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpTapText.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpTapText.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpLlRecorderUpload.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpLlRecorderUpload.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                } else if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                                    lpLlRecorderUpload = (LinearLayout.LayoutParams) voiceRecording.getLl_recorder_upload().getLayoutParams();

                                    lpCtrlUI.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpCtrlUI.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpTapText.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpTapText.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpLlRecorderUpload.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpLlRecorderUpload.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                } else {
                                    lpLCtrl = (LinearLayout.LayoutParams) voiceRecording.getLayout_control().getLayoutParams();
                                    lpLl_cg = (LinearLayout.LayoutParams) voiceRecording.getLl_cg().getLayoutParams();

                                    lpCtrlUI.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpCtrlUI.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    lpLCtrl.weight = 1f;

                                    lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    lpTapText.weight = 0.8f;

                                    lpLl_cg.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpLl_cg.height = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpLl_cg.weight = 0.2f;

                                }

                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                                    lpLlRecorderUpload = (LinearLayout.LayoutParams) voiceRecording.getLl_recorder_upload().getLayoutParams();

                                    lpCtrlUI.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpCtrlUI.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                    lpTapText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpTapText.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                    lpLlRecorderUpload.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpLlRecorderUpload.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                } else if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                                    lpLlRecorderUpload = (LinearLayout.LayoutParams) voiceRecording.getLl_recorder_upload().getLayoutParams();

                                    lpCtrlUI.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpCtrlUI.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                    lpTapText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpTapText.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                    lpLlRecorderUpload.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpLlRecorderUpload.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                } else {
                                    lpLCtrl = (LinearLayout.LayoutParams) voiceRecording.getLayout_control().getLayoutParams();
                                    lpLl_cg = (LinearLayout.LayoutParams) voiceRecording.getLl_cg().getLayoutParams();

                                    lpCtrlUI.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpCtrlUI.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                    lpLCtrl.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpLCtrl.height = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpLCtrl.weight = 1f;
                                    lpTapText.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpTapText.height = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpTapText.weight = 0.8f;
                                    lpLl_cg.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpLl_cg.height = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpLl_cg.weight = 0.2f;
                                }
                            }

                            voiceRecording.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            voiceRecording.getLl_tap_text().setLayoutParams(lpTapText);

                            if (lpLCtrl != null) {
                                voiceRecording.getLayout_control().setLayoutParams(lpLCtrl);
                            }
                            if (lpLl_cg != null) {
                                voiceRecording.getLl_cg().setLayoutParams(lpLl_cg);
                            }
                            if (lpLlRecorderUpload != null) {
                                voiceRecording.getLl_recorder_upload().setLayoutParams(lpLlRecorderUpload);
                            }


                        }
                        controlUIDisplaySettings(controlUIProperties, voiceRecording.getTv_displayName());
                        controlAlignments(uiLayoutProperties, voiceRecording.getLl_main_inside());
                        break;
                    case CONTROL_TYPE_VIDEO_RECORDING:

                        VideoRecording videoRecording = (VideoRecording) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpLabel = (LinearLayout.LayoutParams) videoRecording.getLl_label().getLayoutParams();
                            LinearLayout.LayoutParams lpLCtrl = (LinearLayout.LayoutParams) videoRecording.getLayout_control().getLayoutParams();
                            LinearLayout.LayoutParams lpTapText = null;
                            LinearLayout.LayoutParams lpLlRecorderUpload = null;


                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                                    lpLlRecorderUpload = (LinearLayout.LayoutParams) videoRecording.getLl_recorder_upload().getLayoutParams();

                                    lpLabel.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpLabel.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpLlRecorderUpload.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpLlRecorderUpload.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                } else if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                                    lpLlRecorderUpload = (LinearLayout.LayoutParams) videoRecording.getLl_recorder_upload().getLayoutParams();

                                    lpLabel.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpLabel.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpLlRecorderUpload.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpLlRecorderUpload.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                } else {
                                    lpTapText = (LinearLayout.LayoutParams) videoRecording.getLl_tap_text().getLayoutParams();

                                    lpLabel.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                    lpLabel.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                    lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                }
                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                                    lpLlRecorderUpload = (LinearLayout.LayoutParams) videoRecording.getLl_recorder_upload().getLayoutParams();

                                    lpLabel.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpLabel.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                    lpLlRecorderUpload.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpLlRecorderUpload.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                } else if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                                    lpLlRecorderUpload = (LinearLayout.LayoutParams) videoRecording.getLl_recorder_upload().getLayoutParams();

                                    lpLabel.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpLabel.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                    lpLlRecorderUpload.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpLlRecorderUpload.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                } else {
                                    lpTapText = (LinearLayout.LayoutParams) videoRecording.getLl_tap_text().getLayoutParams();

                                    lpLabel.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpLabel.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                    lpTapText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                    lpTapText.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                }
                            }

                            videoRecording.getLl_label().setLayoutParams(lpLabel);
                            videoRecording.getLl_tap_text().setLayoutParams(lpTapText);

                            if (lpLCtrl != null) {
                                videoRecording.getLayout_control().setLayoutParams(lpLCtrl);
                            }
                            if (lpLlRecorderUpload != null) {
                                videoRecording.getLl_recorder_upload().setLayoutParams(lpLlRecorderUpload);
                            }
                        }
                        if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                            controlUIDisplaySettings(controlUIProperties, videoRecording.getTv_startVideoRecorder());
                        } else if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                            controlUIDisplaySettings(controlUIProperties, videoRecording.getTv_startVideoRecorder());
                        } else {
                            controlUIDisplaySettings(controlUIProperties, videoRecording.getTv_displayName());
                        }
                        controlAlignments(uiLayoutProperties, videoRecording.getLl_main_inside());
                        break;
                    case CONTROL_TYPE_AUDIO_PLAYER:

                        AudioPlayer audioPlayer = (AudioPlayer) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) audioPlayer.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpLAudioPlayer = (LinearLayout.LayoutParams) audioPlayer.getLayout_audio_player().getLayoutParams();


                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                lpCtrlUI.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                lpLAudioPlayer.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                lpLAudioPlayer.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {

                                lpCtrlUI.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                lpLAudioPlayer.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpLAudioPlayer.height = ViewGroup.LayoutParams.MATCH_PARENT;

                            }

                            audioPlayer.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            audioPlayer.getLayout_audio_player().setLayoutParams(lpLAudioPlayer);
                        }
                        controlUIDisplaySettings(controlUIProperties, audioPlayer.getTv_displayName());
                        controlAlignments(uiLayoutProperties, audioPlayer.getLl_main_inside());
                        break;
                    case CONTROL_TYPE_VIDEO_PLAYER:

                        VideoPlayer videoPlayer = (VideoPlayer) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) videoPlayer.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpLVideoPlayer = (LinearLayout.LayoutParams) videoPlayer.getLl_videoPlayer_main().getLayoutParams();


                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                lpCtrlUI.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                                lpLVideoPlayer.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                lpLVideoPlayer.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                            } else if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                lpCtrlUI.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = ViewGroup.LayoutParams.MATCH_PARENT;

                                lpLVideoPlayer.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                lpLVideoPlayer.height = ViewGroup.LayoutParams.MATCH_PARENT;

                            }
                            videoPlayer.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            videoPlayer.getLl_videoPlayer_main().setLayoutParams(lpLVideoPlayer);
                        }
                        controlUIDisplaySettings(controlUIProperties, videoPlayer.getTv_displayName());
                        controlAlignments(uiLayoutProperties, videoPlayer.getLl_main_inside());
                        break;
                    case CONTROL_TYPE_PERCENTAGE:

                        Percentage percentage = (Percentage) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) percentage.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpLCtrl = null;
                            LinearLayout.LayoutParams lpEditText = null;
                            LinearLayout.LayoutParams lpTapText = null;
                            LinearLayout.LayoutParams lpLeftRightWeight = null;
                            LinearLayout.LayoutParams lpDisplayName = null;

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("1")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) percentage.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) percentage.getLl_tap_text().getLayoutParams();

                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                        lpEditText = (LinearLayout.LayoutParams) percentage.getCustomEditText().getLayoutParams();
                                        lpEditText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpEditText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("2")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) percentage.getLayout_control().getLayoutParams();
                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("3")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) percentage.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) percentage.getLl_tap_text().getLayoutParams();
                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("5")) {
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpLCtrl = (LinearLayout.LayoutParams) percentage.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) percentage.getLl_tap_text().getLayoutParams();
                                        lpLeftRightWeight = (LinearLayout.LayoutParams) percentage.getLl_leftRightWeight().getLayoutParams();
//                                    lpDisplayName = (LinearLayout.LayoutParams) percentage.getLl_displayName().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpLeftRightWeight.weight = 1f;

                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpLeftRightWeight.weight = 0.6f;

                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    }
                                } else {
                                    lpLCtrl = (LinearLayout.LayoutParams) percentage.getLayout_control().getLayoutParams();
                                    lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                }
                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("1")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) percentage.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) percentage.getLl_tap_text().getLayoutParams();

                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpEditText = (LinearLayout.LayoutParams) percentage.getCustomEditText().getLayoutParams();
                                        lpEditText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpEditText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("2")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) percentage.getLayout_control().getLayoutParams();
                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("3")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) percentage.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) percentage.getLl_tap_text().getLayoutParams();
                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("5")) {
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpLCtrl = (LinearLayout.LayoutParams) percentage.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) percentage.getLl_tap_text().getLayoutParams();
                                        lpLeftRightWeight = (LinearLayout.LayoutParams) percentage.getLl_leftRightWeight().getLayoutParams();
//                                    lpDisplayName = (LinearLayout.LayoutParams) percentage.getLl_displayName().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.weight = 1f;

                                        lpLCtrl.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.weight = 0.6f;

                                        lpTapText.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.weight = 0.4f;
                                    }
                                } else {
                                    lpLCtrl = (LinearLayout.LayoutParams) percentage.getLayout_control().getLayoutParams();
                                    lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                }
                            }

                            percentage.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            if (lpLCtrl != null) {
                                percentage.getLayout_control().setLayoutParams(lpLCtrl);
                            }
                            if (lpLeftRightWeight != null) {
                                percentage.getLl_leftRightWeight().setLayoutParams(lpLeftRightWeight);
                            }
                            if (lpEditText != null) {
                                percentage.getCustomEditText().setLayoutParams(lpEditText);
                            }
                            if (lpTapText != null) {
                                percentage.getLl_tap_text().setLayoutParams(lpTapText);
                            }


                        }
                        controlUIDisplaySettings(controlUIProperties, percentage.getTv_displayName());
                        controlAlignments(uiLayoutProperties, percentage.getLl_main_inside());
                        break;
                    case CONTROL_TYPE_SIGNATURE:

                        SignatureView signatureView = (SignatureView) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) signatureView.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpTapText = null;
                            LinearLayout.LayoutParams lpSignaturePad = null;

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("6")) {

                                        lpSignaturePad = (LinearLayout.LayoutParams) signatureView.getmSignaturePad().getLayoutParams();
                                        lpSignaturePad.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpSignaturePad.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                    }
                                } else {

                                    lpTapText = (LinearLayout.LayoutParams) signatureView.getLl_tap_text().getLayoutParams();
                                    lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                }
                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("6")) {

                                        lpSignaturePad = (LinearLayout.LayoutParams) signatureView.getmSignaturePad().getLayoutParams();
                                        lpSignaturePad.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpSignaturePad.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                    }
                                } else {

                                    lpTapText = (LinearLayout.LayoutParams) signatureView.getLl_tap_text().getLayoutParams();
                                    lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                }
                            }

                            signatureView.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            if (lpTapText != null) {
                                signatureView.getLl_tap_text().setLayoutParams(lpTapText);
                            }
                            if (lpSignaturePad != null) {
                                signatureView.getmSignaturePad().setLayoutParams(lpSignaturePad);
                            }


                        }
                        controlUIDisplaySettings(controlUIProperties, signatureView.getControlRealPath());
                        controlAlignments(uiLayoutProperties, signatureView.getLl_main_inside());
                        break;
                    case CONTROL_TYPE_URL_LINK:

                        UrlView urlView = (UrlView) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) urlView.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpLCtrl = (LinearLayout.LayoutParams) urlView.getLayout_control().getLayoutParams();
                            LinearLayout.LayoutParams lpTapText = (LinearLayout.LayoutParams) urlView.getLl_tap_text().getLayoutParams();
                            LinearLayout.LayoutParams lpTv_tapTextType = null;
                            LinearLayout.LayoutParams lpEditText = null;
                            LinearLayout.LayoutParams lpLeftRightWeight = null;
                            LinearLayout.LayoutParams lpDisplayName = null;

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("1")) {
                                        lpEditText = (LinearLayout.LayoutParams) urlView.getCe_TextType().getLayoutParams();
                                        lpEditText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpEditText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("2")) {

                                        lpTv_tapTextType = (LinearLayout.LayoutParams) urlView.getTv_tapTextType().getLayoutParams();
                                        lpTv_tapTextType.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTv_tapTextType.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                    } else if (strDisplayType.equalsIgnoreCase("3")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) urlView.getLayout_control().getLayoutParams();
                                        lpTv_tapTextType = (LinearLayout.LayoutParams) urlView.getTv_tapTextType().getLayoutParams();

                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpTv_tapTextType.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTv_tapTextType.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                    } else if (strDisplayType.equalsIgnoreCase("5")) {
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpLCtrl = (LinearLayout.LayoutParams) urlView.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) urlView.getLl_tap_text().getLayoutParams();
                                        lpTv_tapTextType = (LinearLayout.LayoutParams) urlView.getTv_tapTextType().getLayoutParams();
                                        lpLeftRightWeight = (LinearLayout.LayoutParams) urlView.getLl_leftRightWeight().getLayoutParams();
                                        lpDisplayName = (LinearLayout.LayoutParams) urlView.getLl_displayName().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpLeftRightWeight.weight = 1f;

                                        lpDisplayName.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpDisplayName.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpDisplayName.weight = 0.4f;

                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpLCtrl.weight = 0.6f;

                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                        lpTv_tapTextType.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTv_tapTextType.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    }
                                } else {
                                    lpTv_tapTextType = (LinearLayout.LayoutParams) urlView.getTv_tapTextType().getLayoutParams();

                                    lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    lpTv_tapTextType.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpTv_tapTextType.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                }
                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("1")) {
                                        lpEditText = (LinearLayout.LayoutParams) urlView.getCe_TextType().getLayoutParams();
                                        lpEditText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpEditText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("2")) {

                                        lpTv_tapTextType = (LinearLayout.LayoutParams) urlView.getTv_tapTextType().getLayoutParams();
                                        lpTv_tapTextType.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTv_tapTextType.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                    } else if (strDisplayType.equalsIgnoreCase("3")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) urlView.getLayout_control().getLayoutParams();
                                        lpTv_tapTextType = (LinearLayout.LayoutParams) urlView.getTv_tapTextType().getLayoutParams();

                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTv_tapTextType.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTv_tapTextType.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                    } else if (strDisplayType.equalsIgnoreCase("5")) {
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpLCtrl = (LinearLayout.LayoutParams) urlView.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) urlView.getLl_tap_text().getLayoutParams();
                                        lpTv_tapTextType = (LinearLayout.LayoutParams) urlView.getTv_tapTextType().getLayoutParams();
                                        lpLeftRightWeight = (LinearLayout.LayoutParams) urlView.getLl_leftRightWeight().getLayoutParams();
                                        lpDisplayName = (LinearLayout.LayoutParams) urlView.getLl_displayName().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.weight = 1f;

                                        lpDisplayName.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpDisplayName.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpDisplayName.weight = 0.4f;

                                        lpLCtrl.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.weight = 0.6f;

                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpTv_tapTextType.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTv_tapTextType.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    }
                                } else {
                                    lpTv_tapTextType = (LinearLayout.LayoutParams) urlView.getTv_tapTextType().getLayoutParams();

                                    lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpTv_tapTextType.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpTv_tapTextType.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                }
                            }
                            urlView.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            if (lpLCtrl != null) {
                                urlView.getLayout_control().setLayoutParams(lpLCtrl);
                            }
                            if (lpTapText != null) {
                                urlView.getLl_tap_text().setLayoutParams(lpTapText);
                            }
                            if (lpTv_tapTextType != null) {
                                urlView.getTv_tapTextType().setLayoutParams(lpTv_tapTextType);
                            }
                            if (lpLeftRightWeight != null) {
                                urlView.getLl_leftRightWeight().setLayoutParams(lpLeftRightWeight);
                            }
                            if (lpDisplayName != null) {
                                urlView.getLl_displayName().setLayoutParams(lpDisplayName);
                            }

                        }
                        controlUIDisplaySettings(controlUIProperties, urlView.getTv_displayName());
                        controlAlignments(uiLayoutProperties, urlView.getLl_main_inside());
                        break;
                    case CONTROL_TYPE_DECIMAL:

                        DecimalView decimalView = (DecimalView) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) decimalView.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpLCtrl = null;
                            LinearLayout.LayoutParams lpEditText = null;
                            LinearLayout.LayoutParams lpTapText = null;
                            LinearLayout.LayoutParams lpLeftRightWeight = null;
                            LinearLayout.LayoutParams lpDisplayName = null;


                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("1")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) decimalView.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) decimalView.getLl_tap_text().getLayoutParams();

                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                        lpEditText = (LinearLayout.LayoutParams) decimalView.getCustomEditText().getLayoutParams();
                                        lpEditText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpEditText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("2")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) decimalView.getLayout_control().getLayoutParams();
                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("3")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) decimalView.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) decimalView.getLl_tap_text().getLayoutParams();
                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("5")) {
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpLCtrl = (LinearLayout.LayoutParams) decimalView.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) decimalView.getLl_tap_text().getLayoutParams();
                                        lpLeftRightWeight = (LinearLayout.LayoutParams) decimalView.getLl_leftRightWeight().getLayoutParams();
                                        lpDisplayName = (LinearLayout.LayoutParams) decimalView.getLl_displayName().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpLeftRightWeight.weight = 1f;

                                        lpDisplayName.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpDisplayName.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpDisplayName.weight = 0.4f;

                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpLCtrl.weight = 0.6f;

                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    }
                                } else {
                                    lpLCtrl = (LinearLayout.LayoutParams) decimalView.getLayout_control().getLayoutParams();
                                    lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                }
                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("1")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) decimalView.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) decimalView.getLl_tap_text().getLayoutParams();

                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpEditText = (LinearLayout.LayoutParams) decimalView.getCustomEditText().getLayoutParams();
                                        lpEditText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpEditText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("2")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) decimalView.getLayout_control().getLayoutParams();
                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("3")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) decimalView.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) decimalView.getLl_tap_text().getLayoutParams();
                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("5")) {
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpLCtrl = (LinearLayout.LayoutParams) decimalView.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) decimalView.getLl_tap_text().getLayoutParams();
                                        lpLeftRightWeight = (LinearLayout.LayoutParams) decimalView.getLl_leftRightWeight().getLayoutParams();
                                        lpDisplayName = (LinearLayout.LayoutParams) decimalView.getLl_displayName().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.weight = 1f;

                                        lpDisplayName.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpDisplayName.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpDisplayName.weight = 0.4f;

                                        lpLCtrl.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.weight = 0.6f;

                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    }
                                } else {
                                    lpLCtrl = (LinearLayout.LayoutParams) decimalView.getLayout_control().getLayoutParams();
                                    lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                }
                            }
                            decimalView.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            if (lpLCtrl != null) {
                                decimalView.getLayout_control().setLayoutParams(lpLCtrl);
                            }
                            if (lpLeftRightWeight != null) {
                                decimalView.getLl_leftRightWeight().setLayoutParams(lpLeftRightWeight);
                            }
                            if (lpEditText != null) {
                                decimalView.getCustomEditText().setLayoutParams(lpEditText);
                            }
                            if (lpTapText != null) {
                                decimalView.getLl_tap_text().setLayoutParams(lpTapText);
                            }
                            if (lpDisplayName != null) {
                                decimalView.getLl_displayName().setLayoutParams(lpDisplayName);
                            }


                        }
                        controlUIDisplaySettings(controlUIProperties, decimalView.getTv_displayName());
                        controlAlignments(uiLayoutProperties, decimalView.getLl_main_inside());

                        break;
                    case CONTROL_TYPE_PASSWORD:

                        Password password = (Password) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) password.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpTapText = null;
                            FrameLayout.LayoutParams lpTiePassword = null;
                            LinearLayout.LayoutParams lpLCtrl = null;
                            LinearLayout.LayoutParams lpLeftRightWeight = null;
                            LinearLayout.LayoutParams lpDisplayName = null;


                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("1")) {

                                        lpTiePassword = (FrameLayout.LayoutParams) password.getTie_password().getLayoutParams();
                                        lpTiePassword.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTiePassword.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("2")) {


                                        lpTapText = (LinearLayout.LayoutParams) password.gettap_text().getLayoutParams();
                                        lpTiePassword = (FrameLayout.LayoutParams) password.getTie_password().getLayoutParams();

                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTiePassword.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTiePassword.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("3")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) password.getLayout_control().getLayoutParams();
                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("5")) {
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpLCtrl = (LinearLayout.LayoutParams) password.getLayout_control().getLayoutParams();
                                        lpTiePassword = (FrameLayout.LayoutParams) password.getTie_password().getLayoutParams();
                                        lpLeftRightWeight = (LinearLayout.LayoutParams) password.getLl_leftRightWeight().getLayoutParams();
                                        lpDisplayName = (LinearLayout.LayoutParams) password.getLl_displayName().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.weight = 1f;

                                        lpDisplayName.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpDisplayName.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpDisplayName.weight = 0.4f;

                                        lpLCtrl.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.weight = 0.6f;

                                        lpTiePassword.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTiePassword.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    }
                                } else {
                                    lpTapText = (LinearLayout.LayoutParams) password.gettap_text().getLayoutParams();
                                    lpTiePassword = (FrameLayout.LayoutParams) password.getTie_password().getLayoutParams();
                                    lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpTiePassword.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpTiePassword.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                }
                            }
                            password.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            if (lpLCtrl != null) {
                                password.getLayout_control().setLayoutParams(lpLCtrl);
                            }
                            if (lpLeftRightWeight != null) {
                                password.getLl_leftRightWeight().setLayoutParams(lpLeftRightWeight);
                            }
                            if (lpTapText != null) {
                                password.gettap_text().setLayoutParams(lpTapText);
                            }
                            if (lpDisplayName != null) {
                                password.getLl_displayName().setLayoutParams(lpDisplayName);
                            }
                            if (lpTiePassword != null) {
                                password.getTie_password().setLayoutParams(lpTiePassword);
                            }


                        }
                        controlUIDisplaySettings(controlUIProperties, password.getTv_displayName());
                        controlAlignments(uiLayoutProperties, password.getLl_main_inside());

                        break;
                    case CONTROL_TYPE_CURRENCY:

                        Currency currency = (Currency) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) currency.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpLCtrl = null;
                            LinearLayout.LayoutParams lpEditText = null;
                            LinearLayout.LayoutParams lpTapText = null;
                            LinearLayout.LayoutParams lpLeftRightWeight = null;
                            LinearLayout.LayoutParams lpDisplayName = null;


                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("1")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) currency.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) currency.getLl_tap_text().getLayoutParams();

                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                        lpEditText = (LinearLayout.LayoutParams) currency.getCustomEditText().getLayoutParams();
                                        lpEditText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpEditText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("2")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) currency.getLayout_control().getLayoutParams();
                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("3")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) currency.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) currency.getLl_tap_text().getLayoutParams();
                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("5")) {
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpLCtrl = (LinearLayout.LayoutParams) currency.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) currency.getLl_tap_text().getLayoutParams();
                                        lpLeftRightWeight = (LinearLayout.LayoutParams) currency.getLl_leftRightWeight().getLayoutParams();
                                        lpDisplayName = (LinearLayout.LayoutParams) currency.getLl_displayName().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpLeftRightWeight.weight = 1f;

                                        lpDisplayName.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpDisplayName.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpDisplayName.weight = 0.4f;

                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpLCtrl.weight = 0.6f;


                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    }
                                } else {
                                    lpLCtrl = (LinearLayout.LayoutParams) currency.getLayout_control().getLayoutParams();
                                    lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                }
                            } else if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("1")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) currency.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) currency.getLl_tap_text().getLayoutParams();

                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpEditText = (LinearLayout.LayoutParams) currency.getCustomEditText().getLayoutParams();
                                        lpEditText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpEditText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("2")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) currency.getLayout_control().getLayoutParams();
                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("3")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) currency.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) currency.getLl_tap_text().getLayoutParams();
                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("5")) {
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpLCtrl = (LinearLayout.LayoutParams) currency.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) currency.getLl_tap_text().getLayoutParams();
                                        lpLeftRightWeight = (LinearLayout.LayoutParams) currency.getLl_leftRightWeight().getLayoutParams();
                                        lpDisplayName = (LinearLayout.LayoutParams) currency.getLl_displayName().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.weight = 1f;

                                        lpDisplayName.width = 0;
                                        lpDisplayName.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpDisplayName.weight = 0.4f;

                                        lpLCtrl.width = 0;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.weight = 0.6f;


                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    }
                                } else {
                                    lpLCtrl = (LinearLayout.LayoutParams) currency.getLayout_control().getLayoutParams();
                                    lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                }
                            }
                            currency.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            if (lpLCtrl != null) {
                                currency.getLayout_control().setLayoutParams(lpLCtrl);
                            }
                            if (lpLeftRightWeight != null) {
                                currency.getLl_leftRightWeight().setLayoutParams(lpLeftRightWeight);
                            }
                            if (lpEditText != null) {
                                currency.getCustomEditText().setLayoutParams(lpEditText);
                            }
                            if (lpTapText != null) {
                                currency.getLl_tap_text().setLayoutParams(lpTapText);
                            }
                            if (lpDisplayName != null) {
                                currency.getLl_displayName().setLayoutParams(lpDisplayName);
                            }


                        }
                        controlUIDisplaySettings(controlUIProperties, currency.getTv_displayName());
                        controlAlignments(uiLayoutProperties, currency.getLl_main_inside());

                        break;
                    case CONTROL_TYPE_DYNAMIC_LABEL:

                        DynamicLabel dynamicLabel = (DynamicLabel) List_ControlClassObjects.get(strControlName);
                        LinearLayout.LayoutParams lpMainInside = (LinearLayout.LayoutParams) dynamicLabel.getLl_main_inside().getLayoutParams();
                        lpMainInside.weight = LinearLayout.LayoutParams.MATCH_PARENT;
                        lpMainInside.height = LinearLayout.LayoutParams.MATCH_PARENT;
                        LinearLayout.LayoutParams lpCtrlUID = (LinearLayout.LayoutParams) dynamicLabel.getLl_control_ui().getLayoutParams();
                        lpCtrlUID.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                        lpCtrlUID.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) dynamicLabel.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpCView = null;
                            LinearLayout.LayoutParams lpLCtrl = null;
                            LinearLayout.LayoutParams lpSCtrl = null;
                            LinearLayout.LayoutParams lpLeftRightWeight = null;
                            LinearLayout.LayoutParams lpDisplayName = null;

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("2") || strDisplayType.equalsIgnoreCase("3")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) dynamicLabel.getLayout_control().getLayoutParams();
                                        lpSCtrl = (LinearLayout.LayoutParams) dynamicLabel.getSectionLayout().getLayoutParams();

                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                        lpSCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpSCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                    } else if (strDisplayType.equalsIgnoreCase("5")) {
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpLCtrl = (LinearLayout.LayoutParams) dynamicLabel.getLayout_control().getLayoutParams();
                                        lpLeftRightWeight = (LinearLayout.LayoutParams) dynamicLabel.getLl_leftRightWeight().getLayoutParams();
                                        lpDisplayName = (LinearLayout.LayoutParams) dynamicLabel.getLl_displayName().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpLeftRightWeight.weight = 1f;

                                        lpDisplayName.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpDisplayName.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpDisplayName.weight = 0.4f;

                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpLCtrl.weight = 0.6f;

                                    } else if (strDisplayType.equalsIgnoreCase("6")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) dynamicLabel.getLayout_control().getLayoutParams();
                                        lpSCtrl = (LinearLayout.LayoutParams) dynamicLabel.getSectionLayout().getLayoutParams();
                                        lpCView = (LinearLayout.LayoutParams) dynamicLabel.getValueView().getLayoutParams();

                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpSCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpSCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpCView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpCView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                    } else if (strDisplayType.equalsIgnoreCase("7")) {
                                        lpCView = (LinearLayout.LayoutParams) dynamicLabel.getValueView().getLayoutParams();
                                        lpCView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpCView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    }
                                } else {
                                    lpCView = (LinearLayout.LayoutParams) dynamicLabel.getValueView().getLayoutParams();
                                    lpCView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpCView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                }
                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("2") || strDisplayType.equalsIgnoreCase("3")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) dynamicLabel.getLayout_control().getLayoutParams();
                                        lpSCtrl = (LinearLayout.LayoutParams) dynamicLabel.getSectionLayout().getLayoutParams();

                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpSCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpSCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                    } else if (strDisplayType.equalsIgnoreCase("5")) {
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpLCtrl = (LinearLayout.LayoutParams) dynamicLabel.getLayout_control().getLayoutParams();
                                        lpLeftRightWeight = (LinearLayout.LayoutParams) dynamicLabel.getLl_leftRightWeight().getLayoutParams();
                                        lpDisplayName = (LinearLayout.LayoutParams) dynamicLabel.getLl_displayName().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.weight = 1f;

                                        lpDisplayName.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpDisplayName.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpDisplayName.weight = 0.4f;

                                        lpLCtrl.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.weight = 0.6f;

                                    } else if (strDisplayType.equalsIgnoreCase("6")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) dynamicLabel.getLayout_control().getLayoutParams();
                                        lpSCtrl = (LinearLayout.LayoutParams) dynamicLabel.getSectionLayout().getLayoutParams();
                                        lpCView = (LinearLayout.LayoutParams) dynamicLabel.getValueView().getLayoutParams();

                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpSCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpSCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCView.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCView.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                    } else if (strDisplayType.equalsIgnoreCase("7")) {
                                        lpCView = (LinearLayout.LayoutParams) dynamicLabel.getValueView().getLayoutParams();
                                        lpCView.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCView.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    }
                                } else {
                                    lpCView = (LinearLayout.LayoutParams) dynamicLabel.getValueView().getLayoutParams();
                                    lpCView.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpCView.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                }
                            }
                            dynamicLabel.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            if (lpCView != null) {
                                dynamicLabel.getValueView().setLayoutParams(lpCView);
                            }
                            if (lpLCtrl != null) {
                                dynamicLabel.getLayout_control().setLayoutParams(lpLCtrl);
                            }
                            if (lpLeftRightWeight != null) {
                                dynamicLabel.getLl_leftRightWeight().setLayoutParams(lpLeftRightWeight);
                            }
                            if (lpDisplayName != null) {
                                dynamicLabel.getLl_displayName().setLayoutParams(lpDisplayName);
                            }
                        }

                        dynamicLabel.getLl_main_inside().setLayoutParams(lpMainInside);

                        dynamicLabel.getLl_control_ui().setLayoutParams(lpCtrlUID);

                        if (controlObject.getDisplayNameAlignment() != null) {
                            String strDisplayType = controlObject.getDisplayNameAlignment();
                            if (strDisplayType.equalsIgnoreCase("7")) {
                                controlUIDisplaySettings(controlUIProperties, dynamicLabel.getValueView());
                            } else {
                                controlUIDisplaySettings(controlUIProperties, dynamicLabel.getTv_displayName());
                            }
                        }

                        controlAlignments(uiLayoutProperties, dynamicLabel.getLl_main_inside());

                        break;
                    case CONTROL_TYPE_IMAGE:

                        Image image = (Image) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = null;
                            LinearLayout.LayoutParams lpLCtrl = null;
                            LinearLayout.LayoutParams lpCView = null;
                            LinearLayout.LayoutParams lpTapText = null;
                            LinearLayout.LayoutParams lpLeftRightWeight = null;
                            LinearLayout.LayoutParams lpDisplayName = null;


                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("6")) {
                                        lpCtrlUI = (LinearLayout.LayoutParams) image.getLl_control_ui().getLayoutParams();
                                        lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpCView = (LinearLayout.LayoutParams) image.getMainImageView().getLayoutParams();
                                        lpCView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpCView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("7")) {
                                        lpCtrlUI = (LinearLayout.LayoutParams) image.getLl_control_ui().getLayoutParams();
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpLeftRightWeight = (LinearLayout.LayoutParams) image.getLl_leftRightWeight().getLayoutParams();
                                        lpDisplayName = (LinearLayout.LayoutParams) image.getLl_displayName().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) image.getLl_tap_text().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpLeftRightWeight.weight = 1f;

                                        lpDisplayName.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpDisplayName.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpDisplayName.weight = 0.4f;

                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpTapText.weight = 0.6f;

                                        lpCView = (LinearLayout.LayoutParams) image.getMainImageView().getLayoutParams();
                                        lpCView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpCView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("8")) {
                                        lpCtrlUI = (LinearLayout.LayoutParams) image.getLl_control_ui().getLayoutParams();
                                        lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpCView = (LinearLayout.LayoutParams) image.getMainImageView().getLayoutParams();
                                        lpCView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpCView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("9")) {
                                        lpCtrlUI = (LinearLayout.LayoutParams) image.getLl_control_ui().getLayoutParams();
                                        lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpCView = (LinearLayout.LayoutParams) image.getMainImageView().getLayoutParams();

                                        if (controlUIProperties.getCustomWidthInDP().equalsIgnoreCase("-1")) {
                                            lpCView.width = controlUIProperties.getCustomWidthInDP().equalsIgnoreCase("-1") ? -1 : LinearLayout.LayoutParams.MATCH_PARENT;
                                        } else if (controlUIProperties.getCustomWidthInDP().equalsIgnoreCase("-2")) {
                                            lpCView.width = controlUIProperties.getCustomWidthInDP().equalsIgnoreCase("-2") ? -2 : LinearLayout.LayoutParams.WRAP_CONTENT;
                                        } else {
                                            lpCView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        }
                                        if (controlUIProperties.getCustomHeightInDP().equalsIgnoreCase("-1")) {
                                            lpCView.height = controlUIProperties.getCustomHeightInDP().equalsIgnoreCase("-1") ? -1 : LinearLayout.LayoutParams.MATCH_PARENT;
                                        } else if (controlUIProperties.getCustomHeightInDP().equalsIgnoreCase("-2")) {
                                            lpCView.height = controlUIProperties.getCustomHeightInDP().equalsIgnoreCase("-2") ? -2 : LinearLayout.LayoutParams.WRAP_CONTENT;
                                        } else {
                                            lpCView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        }
                                    } else if (strDisplayType.equalsIgnoreCase("10")) {
                                        lpCtrlUI = (LinearLayout.LayoutParams) image.getLl_control_ui().getLayoutParams();
                                        lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpCView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpCView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("11")) {
                                        lpCtrlUI = (LinearLayout.LayoutParams) image.getLl_control_ui().getLayoutParams();
                                        lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpCView = (LinearLayout.LayoutParams) image.getMainImageView().getLayoutParams();
                                        lpCView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpCView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    }

                                } else {
                                    lpCtrlUI = (LinearLayout.LayoutParams) image.getLl_control_ui().getLayoutParams();
                                    lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                    lpCView = (LinearLayout.LayoutParams) image.getMainImageView().getLayoutParams();
                                    lpCView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpCView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                }
                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("6")) {
                                        lpCtrlUI = (LinearLayout.LayoutParams) image.getLl_control_ui().getLayoutParams();
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpCView = (LinearLayout.LayoutParams) image.getMainImageView().getLayoutParams();
                                        lpCView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpCView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("7")) {
                                        lpCtrlUI = (LinearLayout.LayoutParams) image.getLl_control_ui().getLayoutParams();
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpLeftRightWeight = (LinearLayout.LayoutParams) image.getLl_leftRightWeight().getLayoutParams();
                                        lpDisplayName = (LinearLayout.LayoutParams) image.getLl_displayName().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) image.getLl_tap_text().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.weight = 1f;

                                        lpDisplayName.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpDisplayName.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpDisplayName.weight = 0.4f;

                                        lpTapText.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.weight = 0.6f;

                                        lpCView = (LinearLayout.LayoutParams) image.getMainImageView().getLayoutParams();
                                        lpCView.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCView.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("8")) {
                                        lpCtrlUI = (LinearLayout.LayoutParams) image.getLl_control_ui().getLayoutParams();
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpCView = (LinearLayout.LayoutParams) image.getMainImageView().getLayoutParams();
                                        lpCView.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCView.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("9")) {
                                        lpCtrlUI = (LinearLayout.LayoutParams) image.getLl_control_ui().getLayoutParams();
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpCView = (LinearLayout.LayoutParams) image.getMainImageView().getLayoutParams();

                                        lpCView.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCView.height = LinearLayout.LayoutParams.MATCH_PARENT;
//                                    lpCView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
//                                    lpCView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("10")) {
                                        lpCtrlUI = (LinearLayout.LayoutParams) image.getLl_control_ui().getLayoutParams();
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpCView.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCView.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("11")) {
                                        lpCtrlUI = (LinearLayout.LayoutParams) image.getLl_control_ui().getLayoutParams();
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpCView = (LinearLayout.LayoutParams) image.getMainImageView().getLayoutParams();
                                        lpCView.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCView.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    }

                                } else {
                                    lpCtrlUI = (LinearLayout.LayoutParams) image.getLl_control_ui().getLayoutParams();
                                    lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpCView = (LinearLayout.LayoutParams) image.getMainImageView().getLayoutParams();
                                    lpCView.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpCView.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                }
                            }

                            if (lpCtrlUI != null) {
                                image.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            }
                            if (lpCView != null) {
                                image.getMainImageView().setLayoutParams(lpCView);
                            }
                            if (lpTapText != null) {
                                image.getLl_tap_text().setLayoutParams(lpTapText);
                            }
                            if (lpLCtrl != null) {
                                image.getLayout_control().setLayoutParams(lpLCtrl);
                            }
                            if (lpLeftRightWeight != null) {
                                image.getLl_leftRightWeight().setLayoutParams(lpLeftRightWeight);
                            }
                            if (lpDisplayName != null) {
                                image.getLl_displayName().setLayoutParams(lpDisplayName);
                            }

                        }
                        controlUIDisplaySettings(controlUIProperties, image.getTv_displayName());

                        controlAlignments(uiLayoutProperties, image.getLLMainInside());

                        break;
                    case CONTROL_TYPE_BUTTON:

                        Button button = (Button) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpLButton = (LinearLayout.LayoutParams) button.getll_button().getLayoutParams();
                            LinearLayout.LayoutParams lpCView = (LinearLayout.LayoutParams) button.getbtn_main().getLayoutParams();
                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                lpLButton.weight = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpLButton.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                lpCView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                lpCView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                            } else {
                                lpLButton.weight = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpLButton.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCView.weight = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCView.height = LinearLayout.LayoutParams.MATCH_PARENT;

                            }
                            if (lpLButton != null) {
                                button.getll_button().setLayoutParams(lpLButton);
                            }
                            if (lpCView != null) {
                                button.getbtn_main().setLayoutParams(lpCView);
                            }
                            controlAlignments(uiLayoutProperties, button.getll_button());
                            try {
                                if (button.getbtn_main() != null) {
                                    if (controlUIProperties.getFontSize() != null && !controlUIProperties.getFontSize().isEmpty()) {
                                        button.getbtn_main().setTextSize(Float.parseFloat(controlUIProperties.getFontSize()));
                                    }
                                    if (controlUIProperties.getFontColorHex() != null && !controlUIProperties.getFontColorHex().isEmpty()) {
                                        button.getbtn_main().setTextColor(Color.parseColor(controlUIProperties.getFontColorHex()));
                                    }
                                    if (controlUIProperties.getFontStyle() != null && !controlUIProperties.getFontStyle().isEmpty()) {
                                        String style = controlUIProperties.getFontStyle();
                                        if (style != null && style.equalsIgnoreCase("Bold")) {
                                            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_bold));
                                            button.getbtn_main().setTypeface(typeface_bold);

                                        } else if (style != null && style.equalsIgnoreCase("Italic")) {
                                            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_italic));
                                            button.getbtn_main().setTypeface(typeface_italic);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Log.getStackTraceString(e);
                            }

                        }
                        break;
                    case CONTROL_TYPE_TIME:
                        Time time = (Time) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) time.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpLCtrl = null;
                            LinearLayout.LayoutParams lpEditText = null;
                            LinearLayout.LayoutParams lpTapText = null;
                            LinearLayout.LayoutParams lpLeftRightWeight = null;
                            LinearLayout.LayoutParams lpDisplayName = null;

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("1")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) time.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) time.getLl_tap_text().getLayoutParams();

                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                        lpEditText = (LinearLayout.LayoutParams) time.getCustomEditText().getLayoutParams();
                                        lpEditText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpEditText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("2")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) time.getLayout_control().getLayoutParams();
                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("3")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) time.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) time.getLl_tap_text().getLayoutParams();
                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    } else if (strDisplayType.equalsIgnoreCase("5")) {
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpLCtrl = (LinearLayout.LayoutParams) time.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) time.getLl_tap_text().getLayoutParams();
                                        lpLeftRightWeight = (LinearLayout.LayoutParams) time.getLl_leftRightWeight().getLayoutParams();
                                        lpDisplayName = (LinearLayout.LayoutParams) time.getLl_displayName().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpLeftRightWeight.weight = 1f;

                                        lpDisplayName.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpDisplayName.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpDisplayName.weight = 0.4f;

                                        lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        lpLCtrl.weight = 0.6f;


                                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    }
                                } else {
                                    lpLCtrl = (LinearLayout.LayoutParams) time.getLayout_control().getLayoutParams();
                                    lpLCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpLCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                }
                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("1")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) time.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) time.getLl_tap_text().getLayoutParams();

                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpEditText = (LinearLayout.LayoutParams) time.getCustomEditText().getLayoutParams();
                                        lpEditText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpEditText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("2")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) time.getLayout_control().getLayoutParams();
                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("3")) {
                                        lpLCtrl = (LinearLayout.LayoutParams) time.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) time.getLl_tap_text().getLayoutParams();
                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    } else if (strDisplayType.equalsIgnoreCase("5")) {
                                        lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpLCtrl = (LinearLayout.LayoutParams) time.getLayout_control().getLayoutParams();
                                        lpTapText = (LinearLayout.LayoutParams) time.getLl_tap_text().getLayoutParams();
                                        lpLeftRightWeight = (LinearLayout.LayoutParams) time.getLl_leftRightWeight().getLayoutParams();
                                        lpDisplayName = (LinearLayout.LayoutParams) time.getLl_displayName().getLayoutParams();

                                        lpLeftRightWeight.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLeftRightWeight.weight = 1f;

                                        lpDisplayName.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpDisplayName.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpDisplayName.weight = 0.4f;

                                        lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLCtrl.weight = 0.6f;


                                        lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    }
                                } else {
                                    lpLCtrl = (LinearLayout.LayoutParams) time.getLayout_control().getLayoutParams();
                                    lpLCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpLCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                }
                            }

                            time.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            if (lpLCtrl != null) {
                                time.getLayout_control().setLayoutParams(lpLCtrl);
                            }
                            if (lpLeftRightWeight != null) {
                                time.getLl_leftRightWeight().setLayoutParams(lpLeftRightWeight);
                            }
                            if (lpEditText != null) {
                                time.getCustomEditText().setLayoutParams(lpEditText);
                            }
                            if (lpTapText != null) {
                                time.getLl_tap_text().setLayoutParams(lpTapText);
                            }
                            if (lpDisplayName != null) {
                                time.getLl_displayName().setLayoutParams(lpDisplayName);
                            }


                        }
                        controlUIDisplaySettings(controlUIProperties, time.getTv_displayName());
                        controlAlignments(uiLayoutProperties, time.getLl_main_inside());

                        break;
                    case CONTROL_TYPE_COUNT_DOWN_TIMER:
                        CountDownTimerControl countDownTimerControl = (CountDownTimerControl) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) countDownTimerControl.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpTapText = (LinearLayout.LayoutParams) countDownTimerControl.getLl_tap_text().getLayoutParams();


                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;

                            }
                            countDownTimerControl.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            if (lpTapText != null) {
                                countDownTimerControl.getLl_tap_text().setLayoutParams(lpTapText);
                            }


                        }
                        controlUIDisplaySettings(controlUIProperties, countDownTimerControl.getTv_displayName());
                        controlAlignments(uiLayoutProperties, countDownTimerControl.getLl_main_inside());

                        break;
                    case CONTROL_TYPE_COUNT_UP_TIMER:
                        CountUpTimerControl countUpTimerControl = (CountUpTimerControl) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams) countUpTimerControl.getLl_control_ui().getLayoutParams();
                            LinearLayout.LayoutParams lpTapText = (LinearLayout.LayoutParams) countUpTimerControl.getLl_tap_text().getLayoutParams();


                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;

                            }
                            countUpTimerControl.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            if (lpTapText != null) {
                                countUpTimerControl.getLl_tap_text().setLayoutParams(lpTapText);
                            }


                        }
                        controlUIDisplaySettings(controlUIProperties, countUpTimerControl.getTv_displayName());
                        controlAlignments(uiLayoutProperties, countUpTimerControl.getLl_main_inside());

                        break;
                    case CONTROL_TYPE_VIEWFILE:
                        ViewFileControl viewFileControl = (ViewFileControl) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpCtrlUI = null;
                            LinearLayout.LayoutParams lpWebView = null;
                            LinearLayout.LayoutParams lpLTapText = null;
                            LinearLayout.LayoutParams lpCardView = null;
                            FrameLayout.LayoutParams lpFWebView = null;

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("6")) {
                                        lpCardView = (LinearLayout.LayoutParams) viewFileControl.getCv_all().getLayoutParams();

                                        lpCardView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpCardView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                    }
                                } else {
                                    lpCtrlUI = (LinearLayout.LayoutParams) viewFileControl.getLl_control_ui().getLayoutParams();
                                    lpWebView = (LinearLayout.LayoutParams) viewFileControl.getWebView().getLayoutParams();
                                    lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    lpWebView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpWebView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                }
                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("6")) {
                                        lpCardView = (LinearLayout.LayoutParams) viewFileControl.getCv_all().getLayoutParams();

                                        lpCardView.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpCardView.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                    }
                                } else {
                                    lpCtrlUI = (LinearLayout.LayoutParams) viewFileControl.getLl_control_ui().getLayoutParams();
                                    lpWebView = (LinearLayout.LayoutParams) viewFileControl.getWebView().getLayoutParams();
                                    lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpWebView.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpWebView.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                }
                            }
                            if (lpCtrlUI != null) {
                                viewFileControl.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            }
                            if (lpWebView != null) {
                                viewFileControl.getWebView().setLayoutParams(lpWebView);
                            }
                            if (lpFWebView != null) {
                                viewFileControl.getViewFileFrameLayout().setLayoutParams(lpFWebView);
                            }
                            if (lpLTapText != null) {
                                viewFileControl.getLl_tap_text().setLayoutParams(lpLTapText);
                            }
                            if (lpCardView != null) {
                                viewFileControl.getCv_all().setLayoutParams(lpCardView);
                            }


                        }
                        controlUIDisplaySettings(controlUIProperties, viewFileControl.getTv_displayName());
                        controlAlignments(uiLayoutProperties, viewFileControl.getViewFileMainLayout());

                        break;
                    case CONTROL_TYPE_GPS:

                        Gps_Control gps_control = (Gps_Control) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();
//                        LinearLayout.LayoutParams lpLlTapText = null;
                            LinearLayout.LayoutParams lpCV_all = null;
                            LinearLayout.LayoutParams lplMapView = null;
                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
//                            lpLlTapText = (LinearLayout.LayoutParams) gps_control.getLl_tap_text().getLayoutParams();
//                            lpLlTapText.width = LinearLayout.LayoutParams.WRAP_CONTENT;
//                            lpLlTapText.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//
                                lpCV_all = (LinearLayout.LayoutParams) gps_control.getCv_all().getLayoutParams();
                                lpCV_all.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpCV_all.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                lplMapView = (LinearLayout.LayoutParams) gps_control.getLayout_map().getLayoutParams();
                                lplMapView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                lplMapView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpCV_all = (LinearLayout.LayoutParams) gps_control.getCv_all().getLayoutParams();
                                lpCV_all.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCV_all.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                lplMapView = (LinearLayout.LayoutParams) gps_control.getLayout_map().getLayoutParams();
                                lplMapView.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lplMapView.height = LinearLayout.LayoutParams.MATCH_PARENT;

                            }
                            if (lpCV_all != null) {
                                gps_control.getCv_all().setLayoutParams(lpCV_all);
                            }
                            if (lplMapView != null) {
                                gps_control.getMapView().setLayoutParams(lplMapView);
                            }
                        }
                        controlUIDisplaySettings(controlUIProperties, gps_control.getTv_displayName());
                        controlAlignments(uiLayoutProperties, gps_control.getLl_gps_main());
                        break;
                    case CONTROL_TYPE_BAR_CODE:

                        BarCode barCode = (BarCode) List_ControlClassObjects.get(strControlName);
                        barCode.createBarCode(controlObject.getDefaultValue(), barCode.getMainImageView(), controlUIProperties);
                        controlUIDisplaySettings(controlUIProperties, barCode.getTv_displayName());
                        controlAlignments(uiLayoutProperties, barCode.getLl_main_inside());

                        break;
                    case CONTROL_TYPE_QR_CODE:

                        QRCode qrCode = (QRCode) List_ControlClassObjects.get(strControlName);
                        qrCode.createBarCode(controlObject.getDefaultValue(), qrCode.getMainImageView(), controlUIProperties);
                        controlUIDisplaySettings(controlUIProperties, qrCode.getTv_displayName());
                        controlAlignments(uiLayoutProperties, qrCode.getLl_main_inside());

                        break;
//                case CONTROL_TYPE_SECTION:// Inside control
//                    break;
                    case CONTROL_TYPE_USER:

                        UserControl userControl = (UserControl) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();
//                        LinearLayout.LayoutParams lpLlTapText = null;
                            LinearLayout.LayoutParams lpLlTapText = null;
                            LinearLayout.LayoutParams lpLlLabel = null;
                            LinearLayout.LayoutParams lpLlIsEnable = null;
                            LinearLayout.LayoutParams lpLlUserSearch = null;
                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("6")) {
                                        lpLlIsEnable = (LinearLayout.LayoutParams) userControl.getLl_isEnable().getLayoutParams();
                                        lpLlIsEnable.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpLlIsEnable.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        if (userControl.getLl_tap_text() != null && userControl.getLl_tap_text().getVisibility() == View.VISIBLE) {
                                            lpLlTapText = (LinearLayout.LayoutParams) userControl.getLl_tap_text().getLayoutParams();
                                            lpLlTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                            lpLlTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        }
                                        if (userControl.getLl_user_search() != null && userControl.getLl_user_search().getVisibility() == View.VISIBLE) {
                                            lpLlUserSearch = (LinearLayout.LayoutParams) userControl.getLl_user_search().getLayoutParams();
                                            lpLlUserSearch.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                            lpLlUserSearch.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        }
                                    }
                                } else {
                                    lpLlLabel = (LinearLayout.LayoutParams) userControl.getLl_label().getLayoutParams();
                                    lpLlLabel.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    lpLlLabel.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                    if (userControl.getLl_tap_text() != null && userControl.getLl_tap_text().getVisibility() == View.VISIBLE) {
                                        lpLlTapText = (LinearLayout.LayoutParams) userControl.getLl_tap_text().getLayoutParams();
                                        lpLlTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLlTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    }
                                    if (userControl.getLl_user_search() != null && userControl.getLl_user_search().getVisibility() == View.VISIBLE) {
                                        lpLlUserSearch = (LinearLayout.LayoutParams) userControl.getLl_user_search().getLayoutParams();
                                        lpLlUserSearch.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLlUserSearch.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    }
                                }
                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("6")) {
                                        lpLlIsEnable = (LinearLayout.LayoutParams) userControl.getLl_isEnable().getLayoutParams();
                                        lpLlIsEnable.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLlIsEnable.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        if (userControl.getLl_tap_text() != null && userControl.getLl_tap_text().getVisibility() == View.VISIBLE) {
                                            lpLlTapText = (LinearLayout.LayoutParams) userControl.getLl_tap_text().getLayoutParams();
                                            lpLlTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                            lpLlTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        }
                                        if (userControl.getLl_user_search() != null && userControl.getLl_user_search().getVisibility() == View.VISIBLE) {
                                            lpLlUserSearch = (LinearLayout.LayoutParams) userControl.getLl_user_search().getLayoutParams();
                                            lpLlUserSearch.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                            lpLlUserSearch.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        }
                                    }
                                } else {
                                    lpLlLabel = (LinearLayout.LayoutParams) userControl.getLl_label().getLayoutParams();
                                    lpLlLabel.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpLlLabel.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                    if (userControl.getLl_tap_text() != null && userControl.getLl_tap_text().getVisibility() == View.VISIBLE) {
                                        lpLlTapText = (LinearLayout.LayoutParams) userControl.getLl_tap_text().getLayoutParams();
                                        lpLlTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLlTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    }
                                    if (userControl.getLl_user_search() != null && userControl.getLl_user_search().getVisibility() == View.VISIBLE) {
                                        lpLlUserSearch = (LinearLayout.LayoutParams) userControl.getLl_user_search().getLayoutParams();
                                        lpLlUserSearch.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLlUserSearch.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    }
                                }
                            }
//
                            if (lpLlLabel != null) {
                                userControl.getLl_label().setLayoutParams(lpLlLabel);
                            }
                            if (lpLlIsEnable != null) {
                                userControl.getLl_isEnable().setLayoutParams(lpLlIsEnable);
                            }
                            if (lpLlTapText != null) {
                                userControl.getLl_tap_text().setLayoutParams(lpLlTapText);
                            }
                            if (lpLlUserSearch != null) {
                                userControl.getLl_user_search().setLayoutParams(lpLlUserSearch);
                            }

                        }
                        controlUIDisplaySettings(controlUIProperties, userControl.getTv_displayName());
                        controlAlignments(uiLayoutProperties, userControl.getLl_main_inside());
                        break;
                    case CONTROL_TYPE_POST:

                        PostControl postControl = (PostControl) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();
//                        LinearLayout.LayoutParams lpLlTapText = null;
                            LinearLayout.LayoutParams lpLlTapText = null;
                            LinearLayout.LayoutParams lpLlLabel = null;
                            LinearLayout.LayoutParams lpLlIsEnable = null;
                            LinearLayout.LayoutParams lpLlUserSearch = null;
                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("6")) {
                                        lpLlIsEnable = (LinearLayout.LayoutParams) postControl.getLl_isEnable().getLayoutParams();
                                        lpLlIsEnable.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpLlIsEnable.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        if (postControl.getLl_tap_text() != null && postControl.getLl_tap_text().getVisibility() == View.VISIBLE) {
                                            lpLlTapText = (LinearLayout.LayoutParams) postControl.getLl_tap_text().getLayoutParams();
                                            lpLlTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                            lpLlTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        }
                                        if (postControl.getLl_user_search() != null && postControl.getLl_user_search().getVisibility() == View.VISIBLE) {
                                            lpLlUserSearch = (LinearLayout.LayoutParams) postControl.getLl_user_search().getLayoutParams();
                                            lpLlUserSearch.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                            lpLlUserSearch.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                        }
                                    }
                                } else {
                                    lpLlLabel = (LinearLayout.LayoutParams) postControl.getLl_label().getLayoutParams();
                                    lpLlLabel.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    lpLlLabel.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                    if (postControl.getLl_tap_text() != null && postControl.getLl_tap_text().getVisibility() == View.VISIBLE) {
                                        lpLlTapText = (LinearLayout.LayoutParams) postControl.getLl_tap_text().getLayoutParams();
                                        lpLlTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLlTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    }
                                    if (postControl.getLl_user_search() != null && postControl.getLl_user_search().getVisibility() == View.VISIBLE) {
                                        lpLlUserSearch = (LinearLayout.LayoutParams) postControl.getLl_user_search().getLayoutParams();
                                        lpLlUserSearch.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLlUserSearch.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    }
                                }
                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("6")) {
                                        lpLlIsEnable = (LinearLayout.LayoutParams) postControl.getLl_isEnable().getLayoutParams();
                                        lpLlIsEnable.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLlIsEnable.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        if (postControl.getLl_tap_text() != null && postControl.getLl_tap_text().getVisibility() == View.VISIBLE) {
                                            lpLlTapText = (LinearLayout.LayoutParams) postControl.getLl_tap_text().getLayoutParams();
                                            lpLlTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                            lpLlTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        }
                                        if (postControl.getLl_user_search() != null && postControl.getLl_user_search().getVisibility() == View.VISIBLE) {
                                            lpLlUserSearch = (LinearLayout.LayoutParams) postControl.getLl_user_search().getLayoutParams();
                                            lpLlUserSearch.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                            lpLlUserSearch.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        }
                                    }
                                } else {
                                    lpLlLabel = (LinearLayout.LayoutParams) postControl.getLl_label().getLayoutParams();
                                    lpLlLabel.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpLlLabel.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                    if (postControl.getLl_tap_text() != null && postControl.getLl_tap_text().getVisibility() == View.VISIBLE) {
                                        lpLlTapText = (LinearLayout.LayoutParams) postControl.getLl_tap_text().getLayoutParams();
                                        lpLlTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLlTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    }
                                    if (postControl.getLl_user_search() != null && postControl.getLl_user_search().getVisibility() == View.VISIBLE) {
                                        lpLlUserSearch = (LinearLayout.LayoutParams) postControl.getLl_user_search().getLayoutParams();
                                        lpLlUserSearch.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLlUserSearch.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    }
                                }
                            }
//
                            if (lpLlLabel != null) {
                                postControl.getLl_label().setLayoutParams(lpLlLabel);
                            }
                            if (lpLlIsEnable != null) {
                                postControl.getLl_isEnable().setLayoutParams(lpLlIsEnable);
                            }
                            if (lpLlTapText != null) {
                                postControl.getLl_tap_text().setLayoutParams(lpLlTapText);
                            }
                            if (lpLlUserSearch != null) {
                                postControl.getLl_user_search().setLayoutParams(lpLlUserSearch);
                            }

                        }
                        controlUIDisplaySettings(controlUIProperties, postControl.getTv_displayName());
                        controlAlignments(uiLayoutProperties, postControl.getLl_main_inside());
                        break;
                    case CONTROL_TYPE_AUTO_COMPLETION:

                        AutoCompletionControl autoCompletionControl = (AutoCompletionControl) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpLlCtrlUI = null;
                            LinearLayout.LayoutParams lpLlTapText = null;

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("6")) {
                                        lpLlCtrlUI = (LinearLayout.LayoutParams) autoCompletionControl.getLl_control_ui().getLayoutParams();
                                        lpLlCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpLlCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                        lpLlTapText = (LinearLayout.LayoutParams) autoCompletionControl.getLl_tap_text().getLayoutParams();
                                        lpLlTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLlTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    }
                                } else {
                                    lpLlCtrlUI = (LinearLayout.LayoutParams) autoCompletionControl.getLl_control_ui().getLayoutParams();
                                    lpLlCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    lpLlCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                    lpLlTapText = (LinearLayout.LayoutParams) autoCompletionControl.getLl_tap_text().getLayoutParams();
                                    lpLlTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpLlTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                                }
                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("6")) {
                                        lpLlCtrlUI = (LinearLayout.LayoutParams) autoCompletionControl.getLl_control_ui().getLayoutParams();
                                        lpLlCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLlCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                        lpLlTapText = (LinearLayout.LayoutParams) autoCompletionControl.getLl_tap_text().getLayoutParams();
                                        lpLlTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLlTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                    }
                                } else {
                                    lpLlCtrlUI = (LinearLayout.LayoutParams) autoCompletionControl.getLl_control_ui().getLayoutParams();
                                    lpLlCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpLlCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                    lpLlTapText = (LinearLayout.LayoutParams) autoCompletionControl.getLl_tap_text().getLayoutParams();
                                    lpLlTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    lpLlTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                }
                            }
//
                            if (lpLlCtrlUI != null) {
                                autoCompletionControl.getLl_control_ui().setLayoutParams(lpLlCtrlUI);
                            }
                            if (lpLlTapText != null) {
                                autoCompletionControl.getLl_tap_text().setLayoutParams(lpLlTapText);
                            }


                        }
                        controlUIDisplaySettings(controlUIProperties, autoCompletionControl.getTv_displayName());
                        controlAlignments(uiLayoutProperties, autoCompletionControl.getLl_main_inside());
                        break;

                    case CONTROL_TYPE_CHART:
                        ChartControl chartControl = (ChartControl) List_ControlClassObjects.get(strControlName);
                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            chartControlUISwitchCase(controlObject, chartControl, controlUIProperties);
                        }
                        controlUIDisplaySettings(controlUIProperties, chartControl.getTv_displayName());
                        controlAlignments(uiLayoutProperties, chartControl.getLl_main_inside());
                        break;
                    case CONTROL_TYPE_DATA_TABLE:

                        DataTableControl dataTableControl = (DataTableControl) List_ControlClassObjects.get(strControlName);

                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpLlCtrlUI = null;
                            LinearLayout.LayoutParams lpLlCtrl = null;

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                lpLlCtrlUI = (LinearLayout.LayoutParams) dataTableControl.getLl_control_ui().getLayoutParams();
                                lpLlCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpLlCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                lpLlCtrl = (LinearLayout.LayoutParams) dataTableControl.getLl_data_table_main().getLayoutParams();
                                lpLlCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                lpLlCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));


                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpLlCtrlUI = (LinearLayout.LayoutParams) dataTableControl.getLl_control_ui().getLayoutParams();
                                lpLlCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpLlCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                lpLlCtrl = (LinearLayout.LayoutParams) dataTableControl.getLl_data_table_main().getLayoutParams();
                                lpLlCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpLlCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;

                            }


                            if (lpLlCtrlUI != null) {
                                dataTableControl.getLl_control_ui().setLayoutParams(lpLlCtrlUI);
                            }
                            if (lpLlCtrl != null) {
                                lpLlCtrl.setMargins(pxToDPControlUI(uiLayoutProperties.getMarginLeft()), pxToDPControlUI(uiLayoutProperties.getMarginTop()), pxToDPControlUI(uiLayoutProperties.getMarginRight()),
                                        pxToDPControlUI(uiLayoutProperties.getMarginBottom()));
                                dataTableControl.getLl_data_table_main().setLayoutParams(lpLlCtrl);

                                dataTableControl.getLl_data_table_main().setPadding(pxToDPControlUI(uiLayoutProperties.getPaddingLeft()), pxToDPControlUI(uiLayoutProperties.getPaddingTop()), pxToDPControlUI(uiLayoutProperties.getPaddingRight()),
                                        pxToDPControlUI(uiLayoutProperties.getPaddingBottom()));
                            }
                        }
                        controlUIDisplaySettings(controlUIProperties, dataTableControl.getTv_displayName());
                        controlAlignments(uiLayoutProperties, dataTableControl.getLl_main_inside());
                        break;
                    case CONTROL_TYPE_CALENDAR_EVENT:

                        CalendarEventControl calendarEventControl = (CalendarEventControl) List_ControlClassObjects.get(strControlName);

                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpLlCtrlUI = null;
                            LinearLayout.LayoutParams lpLlTapText = null;
                            ConstraintLayout.LayoutParams lpConstraintLayout = null;


                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("6")) {
                                        lpLlCtrlUI = (LinearLayout.LayoutParams) calendarEventControl.getLl_control_ui().getLayoutParams();
                                        lpLlCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpLlCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        lpLlTapText = (LinearLayout.LayoutParams) calendarEventControl.getLl_tap_text().getLayoutParams();
                                        lpLlTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                        lpLlTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                    }
                                } else {
                                    lpConstraintLayout = (ConstraintLayout.LayoutParams) calendarEventControl.getCalendarView().getLayoutParams();
                                    lpConstraintLayout.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpConstraintLayout.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                }
                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                if (controlObject.getDisplayNameAlignment() != null) {
                                    String strDisplayType = controlObject.getDisplayNameAlignment();
                                    if (strDisplayType.equalsIgnoreCase("6")) {
                                        lpLlCtrlUI = (LinearLayout.LayoutParams) calendarEventControl.getLl_control_ui().getLayoutParams();
                                        lpLlCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLlCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLlTapText = (LinearLayout.LayoutParams) calendarEventControl.getLl_tap_text().getLayoutParams();
                                        lpLlTapText.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                        lpLlTapText.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    }
                                } else {
                                    lpConstraintLayout = (ConstraintLayout.LayoutParams) calendarEventControl.getCalendarView().getLayoutParams();
                                    lpConstraintLayout.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                    lpConstraintLayout.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                }
                            }
                            if (lpLlCtrlUI != null) {
                                calendarEventControl.getLl_control_ui().setLayoutParams(lpLlCtrlUI);
                            }
                            if (lpLlTapText != null) {
                                calendarEventControl.getLl_tap_text().setLayoutParams(lpLlTapText);
                            }
                            if (lpConstraintLayout != null) {
                                calendarEventControl.getCalendarView().setLayoutParams(lpConstraintLayout);
                            }
                        }
                        controlUIDisplaySettings(controlUIProperties, calendarEventControl.getTv_displayName());
                        controlAlignments(uiLayoutProperties, calendarEventControl.getLl_main_inside());
                        break;
                    case CONTROL_TYPE_MAP:

                        MapControl mapControl = (MapControl) List_ControlClassObjects.get(strControlName);

                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpLlCtrlUI = null;
                            LinearLayout.LayoutParams lpMapView = null;

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                lpLlCtrlUI = (LinearLayout.LayoutParams) mapControl.getLl_control_ui().getLayoutParams();
                                lpLlCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpLlCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpMapView = (LinearLayout.LayoutParams) mapControl.getMapView().getLayoutParams();
                                lpMapView.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                lpMapView.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {

                                lpLlCtrlUI = (LinearLayout.LayoutParams) mapControl.getLl_control_ui().getLayoutParams();
                                lpLlCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpLlCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpMapView = (LinearLayout.LayoutParams) mapControl.getMapView().getLayoutParams();
                                lpMapView.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpMapView.height = LinearLayout.LayoutParams.MATCH_PARENT;

                            }
                            if (lpLlCtrlUI != null) {
                                mapControl.getLl_control_ui().setLayoutParams(lpLlCtrlUI);
                            }
                            if (lpMapView != null) {
                                mapControl.getMapView().setLayoutParams(lpMapView);
                            }
                        }
                        controlUIDisplaySettings(controlUIProperties, mapControl.getTv_displayName());
                        controlAlignments(uiLayoutProperties, mapControl.getLl_main_inside());
                        break;
                    case CONTROL_TYPE_PROGRESS:

                        ProgressControl progressControl = (ProgressControl) List_ControlClassObjects.get(strControlName);

                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            LinearLayout.LayoutParams lpLlCardView = (LinearLayout.LayoutParams) progressControl.getCv_all().getLayoutParams();
                            LinearLayout.LayoutParams lpLlPm = (LinearLayout.LayoutParams) progressControl.getLl_progress_main().getLayoutParams();
                            LinearLayout.LayoutParams lpPb = (LinearLayout.LayoutParams) progressControl.getPb().getLayoutParams();

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                lpLlCardView.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpLlCardView.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                lpLlPm.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpLlPm.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                lpPb.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                lpPb.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {

                                lpLlCardView.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpLlCardView.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                lpLlPm.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpLlPm.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                lpPb.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpPb.height = LinearLayout.LayoutParams.MATCH_PARENT;

                            }
                            if (lpLlCardView != null) {
                                progressControl.getCv_all().setLayoutParams(lpLlCardView);
                            }
                            if (lpLlPm != null) {
                                progressControl.getLl_progress_main().setLayoutParams(lpLlPm);
                            }
                            if (lpPb != null) {
                                progressControl.getPb().setLayoutParams(lpPb);
                            }
                        }
                        controlUIDisplaySettings(controlUIProperties, progressControl.getTv_displayName());
                        controlAlignments(uiLayoutProperties, progressControl.getLl_main_inside());
                        break;
                    case CONTROL_TYPE_CUSTOM_HEADER:

                        UISettingsCustomHeader uiSettingsCustomHeader = (UISettingsCustomHeader) List_ControlClassObjects.get(strControlName);

                        LinearLayout.LayoutParams lpMainInsideH = null;
                        LinearLayout.LayoutParams lpLlCtrlUI = (LinearLayout.LayoutParams) uiSettingsCustomHeader.getLl_control_ui().getLayoutParams();
                        LinearLayout.LayoutParams lpLlCtrl = (LinearLayout.LayoutParams) uiSettingsCustomHeader.getTv_custom_header().getLayoutParams();

                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();

                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                                lpLlCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpLlCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                lpLlCtrl.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                lpLlCtrl.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {

                                lpMainInsideH = (LinearLayout.LayoutParams) uiSettingsCustomHeader.getLl_main_inside().getLayoutParams();
                                lpMainInsideH.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpMainInsideH.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                lpLlCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpLlCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                lpLlCtrl.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpLlCtrl.height = LinearLayout.LayoutParams.MATCH_PARENT;

                            }
                            if (lpLlCtrlUI != null) {
                                uiSettingsCustomHeader.getLl_control_ui().setLayoutParams(lpLlCtrlUI);
                            }
                            if (lpLlCtrl != null) {
                                uiSettingsCustomHeader.getCustomHeaderLayout().setLayoutParams(lpLlCtrl);
                            }
                            if (lpMainInsideH != null) {
                                uiSettingsCustomHeader.getLl_main_inside().setLayoutParams(lpMainInsideH);
                            }
                        }
                        controlUIDisplaySettings(controlUIProperties, uiSettingsCustomHeader.getTv_custom_header());
                        controlAlignments(uiLayoutProperties, uiSettingsCustomHeader.getLl_main_inside());
                        break;
                    case CONTROL_TYPE_CUSTOM_IMAGE:

                        UISettingsCustomImage uiSettingsCustomImage = (UISettingsCustomImage) List_ControlClassObjects.get(strControlName);

                        LinearLayout.LayoutParams lpMainInsideI = null;
                        LinearLayout.LayoutParams lpCtrlUI = null;
                        LinearLayout.LayoutParams lpIv = null;

                        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                            String strWidthType = controlUIProperties.getTypeOfWidth();
                            String strHeightType = controlUIProperties.getTypeOfHeight();


                            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                                lpCtrlUI = (LinearLayout.LayoutParams) uiSettingsCustomImage.getLl_control_ui().getLayoutParams();
                                lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                                lpIv = (LinearLayout.LayoutParams) uiSettingsCustomImage.getUiCustomImage().getLayoutParams();

                                if (controlUIProperties.getCustomWidthInDP().equalsIgnoreCase("-1")) {
                                    lpIv.width = controlUIProperties.getCustomWidthInDP().equalsIgnoreCase("-1") ? -1 : LinearLayout.LayoutParams.MATCH_PARENT;
                                } else if (controlUIProperties.getCustomWidthInDP().equalsIgnoreCase("-2")) {
                                    lpIv.width = controlUIProperties.getCustomWidthInDP().equalsIgnoreCase("-2") ? -2 : LinearLayout.LayoutParams.WRAP_CONTENT;
                                } else {
                                    lpIv.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                }
                                if (controlUIProperties.getCustomHeightInDP().equalsIgnoreCase("-1")) {
                                    lpIv.height = controlUIProperties.getCustomHeightInDP().equalsIgnoreCase("-1") ? -1 : LinearLayout.LayoutParams.MATCH_PARENT;
                                } else if (controlUIProperties.getCustomHeightInDP().equalsIgnoreCase("-2")) {
                                    lpIv.height = controlUIProperties.getCustomHeightInDP().equalsIgnoreCase("-2") ? -2 : LinearLayout.LayoutParams.WRAP_CONTENT;
                                } else {
                                    lpIv.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                }

                            } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(MATCH_PARENT)) {
                                lpMainInsideI = (LinearLayout.LayoutParams) uiSettingsCustomImage.getLl_main_inside().getLayoutParams();
                                lpMainInsideI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpMainInsideI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                lpIv.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                lpIv.height = LinearLayout.LayoutParams.MATCH_PARENT;

                            }
                            if (lpMainInsideI != null) {
                                uiSettingsCustomImage.getLl_main_inside().setLayoutParams(lpMainInsideI);
                            }
                            if (lpCtrlUI != null) {
                                uiSettingsCustomImage.getLl_control_ui().setLayoutParams(lpCtrlUI);
                            }
                            if (lpIv != null) {
                                uiSettingsCustomImage.getUiCustomImage().setLayoutParams(lpIv);
                            }
                        }
                        controlAlignments(uiLayoutProperties, uiSettingsCustomImage.getLl_main_inside());
                        break;

                }
            }
        }catch (Exception e){
            Log.getStackTraceString(e);
        }

    }
    private void controlUIDisplaySettings(ControlUIProperties controlUIProperties, CustomTextView ctv) {

        try {
            if(ctv != null  && controlUIProperties != null) {
                if (controlUIProperties.getFontSize() != null && !controlUIProperties.getFontSize().isEmpty()) {
                    ctv.setTextSize(Float.parseFloat(controlUIProperties.getFontSize()));
                }
                if (controlUIProperties.getFontColorHex() != null && !controlUIProperties.getFontColorHex().isEmpty()) {
                    ctv.setTextColor(Color.parseColor(controlUIProperties.getFontColorHex()));
                }
                if (controlUIProperties.getFontStyle() != null && !controlUIProperties.getFontStyle().isEmpty()) {
                    String style = controlUIProperties.getFontStyle();
                    if (style != null && style.equalsIgnoreCase("Bold")) {
                        Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_bold));
                        ctv.setTypeface(typeface_bold);

                    } else if (style != null && style.equalsIgnoreCase("Italic")) {
                        Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_italic));
                        ctv.setTypeface(typeface_italic);
                    }
                }
            }
        }catch ( Exception e){
            Log.getStackTraceString(e);
        }

    }

    public void chartControlUISwitchCase(ControlObject controlObject,ChartControl chartControl,ControlUIProperties controlUIProperties) {
        try {
            String strChartType = controlObject.getChartType();
            String strWidthType = controlUIProperties.getTypeOfWidth();
            String strHeightType = controlUIProperties.getTypeOfHeight();
            String strDisplayNameAlignment = "";
            if (controlObject.getDisplayNameAlignment() != null) {
                strDisplayNameAlignment = controlObject.getDisplayNameAlignment();
            }

            LinearLayout.LayoutParams lpCardView = null;
            LinearLayout.LayoutParams lpChartType = null;
            LinearLayout.LayoutParams lpLCtrlUi = null;

            switch (strChartType) {
                case AppConstants.CHART_TYPE_LINE:
                    lpChartType = (LinearLayout.LayoutParams) chartControl.getLineChart().getLayoutParams();
                case AppConstants.CHART_TYPE_BAR:
                    lpChartType = (LinearLayout.LayoutParams) chartControl.getBarChart().getLayoutParams();
                case AppConstants.CHART_TYPE_PIE:
                    lpChartType = (LinearLayout.LayoutParams) chartControl.getPieChart().getLayoutParams();
                case AppConstants.CHART_TYPE_ROW:
                    lpChartType = (LinearLayout.LayoutParams) chartControl.getRowChart().getLayoutParams();
                case AppConstants.CHART_TYPE_AREA:
                    lpChartType = (LinearLayout.LayoutParams) chartControl.getAreaChart().getLayoutParams();
                case AppConstants.CHART_TYPE_SCATTER:
                    lpChartType = (LinearLayout.LayoutParams) chartControl.getScatterChart().getLayoutParams();
                case AppConstants.CHART_TYPE_COMBO:
                    if(strChartType.equalsIgnoreCase(AppConstants.CHART_TYPE_COMBO)) {
                        lpChartType = (LinearLayout.LayoutParams) chartControl.getCombinedChart().getLayoutParams();
                    }

                    if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                        if (strDisplayNameAlignment.equalsIgnoreCase("6")) {
                            lpLCtrlUi = (LinearLayout.LayoutParams) chartControl.getLl_control_ui().getLayoutParams();
                            lpLCtrlUi.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                            lpLCtrlUi.height = LinearLayout.LayoutParams.WRAP_CONTENT;

                            lpChartType.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                            lpChartType.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                            if (lpLCtrlUi != null) {
                                chartControl.getLl_control_ui().setLayoutParams(lpLCtrlUi);
                            }
                            if (lpChartType != null) {
                                chartControl.getLineChart().setLayoutParams(lpChartType);
                            }
                        } else {
                            lpCardView = (LinearLayout.LayoutParams) chartControl.getCv_all().getLayoutParams();
                            lpCardView.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                            lpCardView.height = LinearLayout.LayoutParams.WRAP_CONTENT;


                            lpChartType.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                            lpChartType.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                            if (lpCardView != null) {
                                chartControl.getCv_all().setLayoutParams(lpCardView);
                            }
                            if (lpChartType != null) {
                                chartControl.getLineChart().setLayoutParams(lpChartType);
                            }

                        }
                    } else if (strWidthType.equalsIgnoreCase(AppConstants.MATCH_PARENT) && strHeightType.equalsIgnoreCase(AppConstants.MATCH_PARENT)) {
                        if (strDisplayNameAlignment.equalsIgnoreCase("6")) {
                            lpLCtrlUi = (LinearLayout.LayoutParams) chartControl.getLl_control_ui().getLayoutParams();
                            lpLCtrlUi.width = LinearLayout.LayoutParams.MATCH_PARENT;
                            lpLCtrlUi.height = LinearLayout.LayoutParams.MATCH_PARENT;

                            lpChartType.width = LinearLayout.LayoutParams.MATCH_PARENT;
                            lpChartType.height = LinearLayout.LayoutParams.MATCH_PARENT;
                            if (lpLCtrlUi != null) {
                                chartControl.getLl_control_ui().setLayoutParams(lpLCtrlUi);
                            }
                            if (lpChartType != null) {
                                chartControl.getLineChart().setLayoutParams(lpChartType);
                            }
                        } else {
                            lpCardView = (LinearLayout.LayoutParams) chartControl.getCv_all().getLayoutParams();
                            lpCardView.width = LinearLayout.LayoutParams.MATCH_PARENT;
                            lpCardView.height = LinearLayout.LayoutParams.MATCH_PARENT;


                            lpChartType.width = LinearLayout.LayoutParams.MATCH_PARENT;
                            lpChartType.height = LinearLayout.LayoutParams.MATCH_PARENT;
                            if (lpCardView != null) {
                                chartControl.getCv_all().setLayoutParams(lpCardView);
                            }
                            if (lpChartType != null) {
                                chartControl.getLineChart().setLayoutParams(lpChartType);
                            }

                        }
                    }

                    break;

            }
        }catch (Exception e){
            Log.getStackTraceString(e);
        }
    }
    private void controlAlignments(UILayoutProperties uiLayoutProperties, LinearLayout linearLayout) {
        try {
            if (uiLayoutProperties != null && linearLayout != null) {
                if (uiLayoutProperties.getControlVerticalAlignment() != null && !uiLayoutProperties.getControlVerticalAlignment().isEmpty()
                        && uiLayoutProperties.getControlHorizontalAlignment() != null && !uiLayoutProperties.getControlHorizontalAlignment().isEmpty()) {
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
        }catch(Exception e){
            Log.getStackTraceString(e);
        }
    }

}
