package com.bhargo.user.screens;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.Control_EventObject;
import com.bhargo.user.Java_Beans.New_DataControl_Bean;
import com.bhargo.user.controls.advanced.BarCode;
import com.bhargo.user.controls.advanced.Gps_Control;
import com.bhargo.user.controls.advanced.QRCode;
import com.bhargo.user.controls.advanced.SubformView;
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
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.FilePicker;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.SessionManager;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUDIO_PLAYER;
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

public class LoadForm {

    public static final int AUTOCOMPLETE_REQUEST_CODE = 666;
    private static final String TAG = "";
    private static final int FILE_BROWSER_RESULT_CODE = 554;
    private final Activity context;
    private final LinearLayout linearLayout;
    private final List<ControlObject> list = new ArrayList<>();
    private final boolean fromQueryFilter;
    //=======Load_Onchange_EventObjects================
    LinkedHashMap<String, Control_EventObject> hash_Onchange_EventObjects = new LinkedHashMap<String, Control_EventObject>();
    //=======Load_Onfocus_EventObjects================
    LinkedHashMap<String, Control_EventObject> hash_Onfocus_EventObjects = new LinkedHashMap<String, Control_EventObject>();
    //=======Load_OnClick_EventObjects================
    LinkedHashMap<String, Control_EventObject> hash_Onclick_EventObjects = new LinkedHashMap<String, Control_EventObject>();
    LinkedHashMap<String, Object> List_ControlClassObjects = new LinkedHashMap<String, Object>();
    SessionManager sessionManager;
    ImproveHelper improveHelper;
    private String strUserLocationStructure;


    public LoadForm(Activity context, LinearLayout linearLayout) {

        this.context = context;


        this.linearLayout = linearLayout;

        fromQueryFilter = false;

        sessionManager = new SessionManager(context);
        improveHelper = new ImproveHelper(context);
    }

    public LoadForm(Activity context, LinearLayout linearLayout, boolean fromQueryFilter) {

        this.context = context;


        this.linearLayout = linearLayout;


        this.fromQueryFilter = fromQueryFilter;
        improveHelper = new ImproveHelper(context);
    }


    public void load(List<ControlObject> list) {
        try {
            for (int i = 0; i < list.size(); i++) {


                loadControl(list.get(i), list.get(i).getControlType());

            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "load", e);
        }
    }

    private void loadControl(final ControlObject loadControlObject, String controlType) {
        try {
            Log.d(TAG, "XmlHelperTextInput: " + controlType);
            switch (controlType) {
                case CONTROL_TYPE_TEXT_INPUT:
                    TextInput textInput = new TextInput(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), textInput);
                    linearLayout.addView(textInput.getTextInputView());
                    if (loadControlObject.isGoogleLocationSearch()) {
                        textInput.getLl_tap_text().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onSearchCalled();
                            }
                        });

                        textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onSearchCalled();
                            }
                        });
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
                    NumericInput numericInput = new NumericInput(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), numericInput);
                    linearLayout.addView(numericInput.getNumericInputView());

//
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
                    Phone phone = new Phone(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), phone);
                    linearLayout.addView(phone.getPhoneView());

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
                    Email email = new Email(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), email);
                    linearLayout.addView(email.getEmailView());
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
//                    Camera camera = new Camera(context, loadControlObject, false, 0, "");
                    Camera camera = new Camera(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), camera);
                    linearLayout.addView(camera.getCameraView());
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_LARGE_INPUT:
                    LargeInput largeInput = new LargeInput(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), largeInput);
                    linearLayout.addView(largeInput.getLargeInputView());

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
                    Checkbox checkbox = new Checkbox(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), checkbox);
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
                case CONTROL_TYPE_FILE_BROWSING:
//                    FileBrowsing fileBrowsing = new FileBrowsing(context, loadControlObject);
                    FileBrowsing fileBrowsing = new FileBrowsing(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), fileBrowsing);
                    linearLayout.addView(fileBrowsing.getFileBrowsingView());
                    fileBrowsing.getTapTextViewLayout().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();

                            context.startActivityForResult(new Intent(context, FilePicker.class), FILE_BROWSER_RESULT_CODE);

                            if (loadControlObject.isOnChangeEventExists()) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    //ChangeEvent(v);
                                }
                            }
                        }
                    });
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_CALENDER:
                    Calendar calendar = new Calendar(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), calendar);
                    linearLayout.addView(calendar.getCalnderView());
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
                    AudioPlayer audioPlayer = new AudioPlayer(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), audioPlayer);
                    linearLayout.addView(audioPlayer.getAudioPlayerView());
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
//                    VideoPlayer videoPlayer = new VideoPlayer(context, loadControlObject, false, 0, "");
                    VideoPlayer videoPlayer = new VideoPlayer(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), videoPlayer);
                    linearLayout.addView(videoPlayer.getVideoPlayerView());
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
                    Percentage percentage = new Percentage(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), percentage);
                    linearLayout.addView(percentage.getPercentageView());
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
                    RadioGroupView radioGroup = new RadioGroupView(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), radioGroup);
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
                    break;
                case CONTROL_TYPE_DROP_DOWN:
                    DropDown dropDown = new DropDown(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), dropDown);
                    linearLayout.addView(dropDown.getDropdown());
               /* if (loadControlObject.isInvisible()) {
                    dropDown.getDropdown().setVisibility(View.GONE);
                } else {*/
                    dropDown.getDropdown().setVisibility(View.VISIBLE);
                    //}
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_CHECK_LIST:
                    CheckList checkList = new CheckList(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), checkList);
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

                    break;
                case CONTROL_TYPE_SIGNATURE:
                    SignatureView signature = new SignatureView(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), signature);
                    linearLayout.addView(signature.getSignature());
                    if (loadControlObject.isInvisible()) {
                        signature.getSignature().setVisibility(View.GONE);
                    } else {
                        signature.getSignature().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
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
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
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
                    Password password = new Password(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), password);
                    linearLayout.addView(password.getPassword());
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
                    Currency currency = new Currency(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), currency);
                    linearLayout.addView(currency.getCurrency());
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
                case CONTROL_TYPE_DYNAMIC_LABEL:
                    DynamicLabel dynamicLabel = new DynamicLabel(context, loadControlObject,false,-1,null);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), dynamicLabel);
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

                    break;
                case CONTROL_TYPE_IMAGE:
                    Image imageView = new Image(context, loadControlObject, false, 0, "", "",null);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), imageView);
                    linearLayout.addView(imageView.getImageView());
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
                    Button button = new Button(context, loadControlObject, false, 0, "", "");
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
                case CONTROL_TYPE_AUTO_NUMBER:
                    AutoNumber autoNumber = new AutoNumber(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), autoNumber);
                    linearLayout.addView(autoNumber.getAutoNumber());
                    if (loadControlObject.isInvisible()) {
                        autoNumber.getAutoNumber().setVisibility(View.GONE);
                    } else {
                        autoNumber.getAutoNumber().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
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
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_GPS:
             /*   if(!fromQueryFilter) {
                    GPS_Control controlGPS = new GPS_Control(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), controlGPS);
                    linearLayout.addView(controlGPS.getControlGPSView());
                    if (loadControlObject.isInvisible()) {
                        controlGPS.getControlGPSView().setVisibility(View.GONE);
                    } else {
                        controlGPS.getControlGPSView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                }else{*/
                    Gps_Control controlGPS = new Gps_Control(context, loadControlObject, fromQueryFilter);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), controlGPS);
                    linearLayout.addView(controlGPS.getControlGPSView());
                    /*if (loadControlObject.isInvisible()) {
                        controlGPS.getControlGPSView().setVisibility(View.GONE);
                    } else {
                        controlGPS.getControlGPSView().setVisibility(View.VISIBLE);
                    }*/
                    controlGPS.getControlGPSView().setVisibility(View.GONE);
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    // }
                    break;
                case CONTROL_TYPE_VIDEO_RECORDING:
                    VideoRecording videoRecording = new VideoRecording(context, loadControlObject,false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), videoRecording);
                    linearLayout.addView(videoRecording.getVideoRecorderView());
                    if (loadControlObject.isInvisible()) {
                        videoRecording.getVideoRecorderView().setVisibility(View.GONE);
                    } else {
                        videoRecording.getVideoRecorderView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_VOICE_RECORDING:
                    VoiceRecording voiceRecording = new VoiceRecording(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), voiceRecording);
                    linearLayout.addView(voiceRecording.getVoiceRecordingView());
                    if (loadControlObject.isInvisible()) {
                        voiceRecording.getVoiceRecordingView().setVisibility(View.GONE);
                    } else {
                        voiceRecording.getVoiceRecordingView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_BAR_CODE:
                    BarCode barcode = new BarCode(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), barcode);
                    linearLayout.addView(barcode.getBarCode());
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_QR_CODE:
                    QRCode qrCode = new QRCode(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), qrCode);
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
                    DataControl dataControl = new DataControl(context, loadControlObject, strUserLocationStructure, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), dataControl);
                    linearLayout.addView(dataControl.getDataControl());

                    break;
                case CONTROL_TYPE_SUBFORM:

                    SubformView subformView = new SubformView(context, loadControlObject, strUserLocationStructure, List_ControlClassObjects, false, "", null,null,false,null,null,0,null,null,null,null,null,null);

                    List_ControlClassObjects.put(loadControlObject.getControlName(), subformView);
                    linearLayout.addView(subformView.getSubFormView());

                    //subformView.setTag(String.valueOf(subFormPos));

                    //subFormPos++;

                    break;
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "loadControl", e);
        }

    }


    private void loadItems(ControlObject controlObject) {

        sessionManager = new SessionManager(context);
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
            } catch (Exception e) {
                ImproveHelper.improveException(context, TAG, "loadItems", e);
            }
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


    private LinearLayout getLinearLayout() {

        return linearLayout;
    }

    public LinkedHashMap<String, Object> getList_ControlClassObjects() {

        return List_ControlClassObjects;
    }
}
