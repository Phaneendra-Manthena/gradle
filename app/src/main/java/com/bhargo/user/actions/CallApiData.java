package com.bhargo.user.actions;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUDIO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BAR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CALENDER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CAMERA;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHECKBOX;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHECK_LIST;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CURRENCY;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DECIMAL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DROP_DOWN;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DYNAMIC_LABEL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_EMAIL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_FILE_BROWSING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_IMAGE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_LARGE_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_MAP;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_NUMERIC_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PASSWORD;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PERCENTAGE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PHONE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_QR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_RADIO_BUTTON;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_RATING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SIGNATURE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_TEXT_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_URL_LINK;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIEWFILE;
import static com.bhargo.user.utils.ImproveHelper.showToastRunOnUI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.API_OutputParam_Bean;
import com.bhargo.user.Java_Beans.ActionWithoutCondition_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.GetAPIDetails_Bean;
import com.bhargo.user.Java_Beans.ImageAdvanced_Mapped_Item;
import com.bhargo.user.Java_Beans.Item;
import com.bhargo.user.Java_Beans.LanguageMapping;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.advanced.BarCode;
import com.bhargo.user.controls.advanced.ChartControl;
import com.bhargo.user.controls.advanced.DataTableControl;
import com.bhargo.user.controls.advanced.DataViewer;
import com.bhargo.user.controls.advanced.GridControl;
import com.bhargo.user.controls.advanced.QRCode;
import com.bhargo.user.controls.advanced.SectionControl;
import com.bhargo.user.controls.advanced.SubformView;
import com.bhargo.user.controls.standard.CalendarEventControl;
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
import com.bhargo.user.controls.standard.TextInput;
import com.bhargo.user.controls.standard.ViewFileControl;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.Callback;
import com.bhargo.user.pojos.DataViewerModelClass;
import com.bhargo.user.realm.JSONKeyValueType;
import com.bhargo.user.realm.JSONTableColsVals;
import com.bhargo.user.realm.RealmDBHelper;
import com.bhargo.user.realm.RealmTables;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ControlUtils;
import com.bhargo.user.utils.ImproveHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class CallApiData {

    String TAG = "CallApiData";
    Callback callbackListener;
    String ServiceType, ServiceResult, ServiceSource;
    GetAPIDetails_Bean.APIDetails APIDetails;
    boolean fromsms;
    ActionWithoutCondition_Bean ActionBean;
    MainActivity context;
    List<JSONTableColsVals> jsonTableColsVals = new ArrayList<>();
    ProgressDialog pd;
    Handler handCloseProgressBarSuccess = new Handler() {
        public void handleMessage(android.os.Message msg) {
            successCase();
        }
    };
    Handler handCloseProgressBarFailure = new Handler() {
        public void handleMessage(android.os.Message msg) {
            failureCase();
        }
    };
    Activity activity;
    LinkedHashMap<String, String> outputParamswithPaths;
    String hdOutputSaveFlow;
    List<String> outputParams;
    String strAppName;
    LinkedHashMap<String, Object> List_ControlClassObjects;
    DataCollectionObject dataCollectionObject;
    String statusCode = "", statusMsgSuccess = "", statusMsgFailure = "";
    Handler handCallAPIResultIntoRealm = new Handler() {
        public void handleMessage(android.os.Message msg) {
            setResultToControl();
        }
    };

    public CallApiData(MainActivity context, Activity activity, ActionWithoutCondition_Bean ActionBean, boolean fromsms,
                       GetAPIDetails_Bean.APIDetails APIDetails, String ServiceType, String ServiceResult,
                       String ServiceSource, String strAppName, LinkedHashMap<String, Object> List_ControlClassObjects, DataCollectionObject dataCollectionObject) {
        this.context = context;
        this.activity = activity;
        this.List_ControlClassObjects = List_ControlClassObjects;
        this.dataCollectionObject = dataCollectionObject;
        this.strAppName = strAppName;
        this.ActionBean = ActionBean;
        this.fromsms = fromsms;
        this.APIDetails = APIDetails;
        this.ServiceSource = ServiceSource;
        this.ServiceResult = ServiceResult;
        this.ServiceType = ServiceType;
        outputParamswithPaths = ImproveHelper.getOutputParamswithPaths(APIDetails.getSuccessCaseDetails());
        hdOutputSaveFlow = ImproveHelper.gethdOutputSaveFlow(APIDetails.getSuccessCaseDetails());
        outputParams = ImproveHelper.getOutputParams(APIDetails.getSuccessCaseDetails());
    }

    public void resultIntoRealm(String responcestr, String responceType, Callback callbackListener) {
        this.callbackListener = callbackListener;
        showProgressDialog("Please Wait! API Result Processing Into Realm...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (responceType != null && !responceType.trim().equalsIgnoreCase("") && responceType.equalsIgnoreCase("json")) {
                                //modifiedColNames.clear();
                                jsonTableColsVals.clear();
                                savOutputPathsInRealm();
                                createRealmObjFromJson(ActionBean.getActionId(), responcestr, ActionBean.getActionId(), "", new ArrayList<>(), true);
                                RealmDBHelper.createTableWithInsertFromCallApiActionAndAPIMappingInsert(context, jsonTableColsVals);
                                saveOfflineResponse();
                                handCallAPIResultIntoRealm.sendEmptyMessage(0);
                            } else if (responceType != null && !responceType.trim().equalsIgnoreCase("") && responceType.equalsIgnoreCase("xml")) {
                                //modifiedColNames.clear();
                                jsonTableColsVals.clear();
                                savOutputPathsInRealm();
                                createRealmObjFromJson(ActionBean.getActionId(), convertXmlToJson(responcestr), ActionBean.getActionId(), "", new ArrayList<>(), true);
                                RealmDBHelper.createTableWithInsertFromCallApiActionAndAPIMappingInsert(context, jsonTableColsVals);
                                saveOfflineResponse();
                                handCallAPIResultIntoRealm.sendEmptyMessage(0);
                            } else {
                                clearData();
                                statusCode = "100";
                                statusMsgFailure = "No Response Type(json,xml & raw)";
                                handCloseProgressBarFailure.sendEmptyMessage(0);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            clearData();
                            statusCode = "100";
                            statusMsgFailure = e.getMessage();
                            handCloseProgressBarFailure.sendEmptyMessage(0);
                        }
                    }
                });
            }
        }).start();
    }

    private void savOutputPathsInRealm() {
        String tableName = ActionBean.getActionId() + "_OutputPaths";
        if (RealmDBHelper.existTable(context, tableName)) {
            RealmDBHelper.deleteTable(context, tableName);
        }
        RealmDBHelper.createTableFromJSONArray(context, tableName, ImproveHelper.getOutputParamswithPathsInStr(APIDetails.getSuccessCaseDetails()));
        RealmDBHelper.insertFromJSONArray(context, tableName, ImproveHelper.getOutputParamswithPathsInStr(APIDetails.getSuccessCaseDetails()));

    }

    private void saveOfflineResponse() {
        if (ActionBean.getSaveOfflineType().equalsIgnoreCase("Response")) {
            RealmDBHelper.updateSaveOfflineTable(context, strAppName, ActionBean.getActionId(), "callApi", "Offline");
        } else {
            RealmDBHelper.updateSaveOfflineTable(context, strAppName, ActionBean.getActionId(), "callApi", "Online");
        }
        //setGetDataStatusInGlobal(responcestr, ActionBean);
    }

    private void setResultToControl() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setResult();
                    }
                });
            }
        }).start();
    }

    public void SetSingleValuetoControlForCallAPIorForm_(API_OutputParam_Bean API_OutputParam_Bean, LinkedHashMap<String, List<String>> OutputData) {
        try {

            String Value = "", ControlID = "";
            String MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_ID().trim();
            ControlID = API_OutputParam_Bean.getOutParam_Name();
            if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                if (MappedControlID.trim().length() > 0) {
                    List<String> MappedValues = OutputData.get(MappedControlID.toLowerCase());
                    Value = MappedValues.get(0);
                }
            } else {
                MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_Expression().trim();
                ExpressionMainHelper ehelper = new ExpressionMainHelper();
                Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
            }
            //using same methods like getData,manageData for data binding
            ControlUtils.setSingleOrMultiValueToControlWithOutCtrl(context, List_ControlClassObjects, Value, OutputData, ControlID, MappedControlID, API_OutputParam_Bean);
            //old process code
            /* boolean checkflag = false;
            for (int i = 0; i < context.dataCollectionObject.getControls_list().size(); i++) {
                ControlObject temp_controlObj = context.dataCollectionObject.getControls_list().get(i);
                checkflag = false;
                if (temp_controlObj.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_DROP_DOWN) ||
                        temp_controlObj.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_RADIO_BUTTON) ||
                        temp_controlObj.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CHECK_LIST)) {
                    if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ControlID.trim())) {
                        checkflag = true;
                    } else if ((temp_controlObj.getControlName().trim() + "_ID").equalsIgnoreCase(ControlID.trim())) {
                        checkflag = true;
                    }

                } else if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ControlID.trim())) {
                    checkflag = true;
                }


                if (checkflag) {
                    switch (temp_controlObj.getControlType()) {
                        case CONTROL_TYPE_TEXT_INPUT:
                            TextInput clearTextView = (TextInput) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            Value = Value.split("\\,")[0];
                            clearTextView.getCustomEditText().setText(Value);
                            CustomTextView tv_tapTextType = clearTextView.gettap_text();
                            tv_tapTextType.setVisibility(View.GONE);
                            clearTextView.getCustomEditText().setVisibility(View.VISIBLE);
                            break;
                        case CONTROL_TYPE_NUMERIC_INPUT:
                            NumericInput numverTextView = (NumericInput) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            Value = Value.split("\\,")[0];
                            numverTextView.getNumericTextView().setText(Value);
                            CustomTextView tv_numtapTextType = numverTextView.gettap_text();
                            tv_numtapTextType.setVisibility(View.GONE);
                            numverTextView.getNumericTextView().setVisibility(View.VISIBLE);
                            numverTextView.gettap_text().setVisibility(View.GONE);
                            break;
                        case CONTROL_TYPE_PHONE:
                            Phone PhoneView = (Phone) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            Value = Value.split("\\,")[0];
                            PhoneView.getCustomEditText().setText(Value);
                            CustomTextView tv_phonetapTextType = PhoneView.gettap_text();
                            tv_phonetapTextType.setVisibility(View.GONE);
                            PhoneView.getCustomEditText().setVisibility(View.VISIBLE);
                            break;
                        case CONTROL_TYPE_EMAIL:
                            Email EmailView = (Email) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            Value = Value.split("\\,")[0];
                            EmailView.getCustomEditText().setText(Value);
                            CustomTextView tv_emailtapTextType = EmailView.gettap_text();
                            tv_emailtapTextType.setVisibility(View.GONE);
                            EmailView.getCustomEditText().setVisibility(View.VISIBLE);
                            break;
                        case CONTROL_TYPE_CAMERA:
                            Value = Value.split("\\,")[0];
                            Camera camera = (Camera) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            if (Value.startsWith("http")) {
                                camera.setImageForEdit(Value);
                            }
                            break;
                        case CONTROL_TYPE_MAP:
                            MapControl mapControl = (MapControl) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            if (API_OutputParam_Bean.getOutParam_Mapped_ID() != null && API_OutputParam_Bean.getOutParam_Mapped_ID().length() > 0) {
                                List<String> MappedValues = RealmDBHelper.getColumnDataInList(context, tableName, colName);
                                Value = MappedValues.get(0);
                                List<String> MappedValues1 = new ArrayList<>();
                                MappedValues1.add(Value);
                                HashMap<String, List<String>> hash_popupdata = new LinkedHashMap<String, List<String>>();
                                context.subFormMapControls.add(mapControl);
                                context.subFormMappedValues.add(MappedValues1);
                                String DefultMarker = API_OutputParam_Bean.getOutParam_Marker_defultImage();
                                String RenderingType = API_OutputParam_Bean.getOutParam_Marker_RenderingType();
                                List<String> ConditionColumn = new ArrayList<>();
                                if (API_OutputParam_Bean.getOutParam_MarkerAdvanced_ConditionColumn() != null) {
                                    String outputTemp1 = outputParamswithPaths.get(API_OutputParam_Bean.getOutParam_MarkerAdvanced_ConditionColumn());
                                    String temp1 = outputTemp1.substring(0, outputTemp1.lastIndexOf("/")).replace("/", "_");
                                    String tableName1 = ActionBean.getActionId().substring(0, 9) + "_" + temp1;
                                    String colName1 = outputTemp1.substring(outputTemp1.lastIndexOf("/") + 1);
                                    List<String> outputData = RealmDBHelper.getColumnDataInList(context, tableName1, colName1);
                                    //ConditionColumn.add(OutputData.get(API_OutputParam_Bean.getOutParam_MarkerAdvanced_ConditionColumn().toLowerCase()).get(0));
                                    ConditionColumn.add(outputData.get(0));
                                }
                                String Operator = API_OutputParam_Bean.getOutParam_MarkerAdvanced_Operator();
                                if (API_OutputParam_Bean.getOutParam_Marker_popupData() != null && API_OutputParam_Bean.getOutParam_Marker_popupData().size() > 0) {
                                    for (int x = 0; x < API_OutputParam_Bean.getOutParam_Marker_popupData().size(); x++) {
                                        List<String> outDatapopupData = new ArrayList<>();
                                        String outputTemp1 = outputParamswithPaths.get(API_OutputParam_Bean.getOutParam_Marker_popupData().get(x).toLowerCase());
                                        String temp1 = outputTemp1.substring(0, outputTemp1.lastIndexOf("/")).replace("/", "_");
                                        String tableName1 = ActionBean.getActionId().substring(0, 9) + "_" + temp1;
                                        String colName1 = outputTemp1.substring(outputTemp1.lastIndexOf("/") + 1);
                                        List<String> outputData = RealmDBHelper.getColumnDataInList(context, tableName1, colName1);
                                        //outDatapopupData.add(OutputData.get(API_OutputParam_Bean.getOutParam_Marker_popupData().get(x).toLowerCase()).get(0));
                                        outDatapopupData.add(outputData.get(0));
                                        hash_popupdata.put(API_OutputParam_Bean.getOutParam_Marker_popupData().get(x), outDatapopupData);
                                    }
                                }
                                if (API_OutputParam_Bean.getList_OutParam_MarkerAdvanced_Items() != null && API_OutputParam_Bean.getList_OutParam_MarkerAdvanced_Items().size() > 0) {
                                    mapControl.setMapMarkersDynamically(RenderingType, DefultMarker, MappedValues1, ConditionColumn, Operator, API_OutputParam_Bean.getList_OutParam_MarkerAdvanced_Items(), API_OutputParam_Bean.getOutParam_Marker_popupData(), hash_popupdata);
                                } else {
                                    mapControl.setMapMarkersDynamically(RenderingType, DefultMarker, MappedValues1, null, null, null, API_OutputParam_Bean.getOutParam_Marker_popupData(), hash_popupdata);
                                }

//                                mapControl.setMapMarkersDynamically(RenderingType,DefultMarker,MappedValues1,null,null,null);

                            }
                            break;
                        case CONTROL_TYPE_IMAGE:
                            Image ImageView = (Image) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            String Advance_ImageSource = "", Advance_ConditionColumn = "";
                            if (API_OutputParam_Bean.getOutParam_Mapped_ID() != null && API_OutputParam_Bean.getOutParam_Mapped_ID().length() > 0) {
                                List<String> output = RealmDBHelper.getColumnDataInList(context, tableName, colName);
                                Value = output.get(0);
                                if (ImageView.controlObject.getImageDataType().equalsIgnoreCase("Base64")) {
                                    ImageView.mainImageView.setImageBitmap(ImproveHelper.Base64StringToBitmap(Value));
                                } else {
                                    ImproveHelper.loadImage_new(context, Value, ImageView.mainImageView, false, temp_controlObj.getImageData());
                                }
                            } else if (API_OutputParam_Bean.getList_OutParam_ImageAdvanced_Items() != null && API_OutputParam_Bean.getList_OutParam_ImageAdvanced_Items().size() > 0) {
                                if (API_OutputParam_Bean.getOutParam_ImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                    String outputTemp1 = outputParamswithPaths.get(API_OutputParam_Bean.getOutParam_ImageAdvanced_ImageSource().toLowerCase());
                                    String temp1 = outputTemp1.substring(0, outputTemp1.lastIndexOf("/")).replace("/", "_");
                                    String tableName1 = ActionBean.getActionId().substring(0, 9) + "_" + temp1;
                                    String colName1 = outputTemp1.substring(outputTemp1.lastIndexOf("/") + 1);
                                    List<String> outputData = RealmDBHelper.getColumnDataInList(context, tableName1, colName1);
                                    Advance_ImageSource = outputData.get(0);
                                }
                                String outputTemp1 = outputParamswithPaths.get(API_OutputParam_Bean.getOutParam_ImageAdvanced_ConditionColumn().toLowerCase());
                                String temp1 = outputTemp1.substring(0, outputTemp1.lastIndexOf("/")).replace("/", "_");
                                String tableName1 = ActionBean.getActionId().substring(0, 9) + "_" + temp1;
                                String colName1 = outputTemp1.substring(outputTemp1.lastIndexOf("/") + 1);
                                List<String> outputData = RealmDBHelper.getColumnDataInList(context, tableName1, colName1);
                                //Advance_ConditionColumn = OutputData.get(API_OutputParam_Bean.getOutParam_ImageAdvanced_ConditionColumn().toLowerCase()).get(0);
                                Advance_ConditionColumn = outputData.get(0);

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
                            LargeInput LargeInputView = (LargeInput) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
//
                            LargeInputView.setDefaultValue(Value);
                            break;
                        case CONTROL_TYPE_CHECKBOX:
                            Checkbox CheckBoxView = (Checkbox) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            CheckBoxView.setValueToCheckBoxItem(Value);


                            break;
                        case CONTROL_TYPE_FILE_BROWSING:
                            FileBrowsing fileBrowsing = (FileBrowsing) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            fileBrowsing.setFileBrowsing(Value);
                            break;
                      *//*  case CONTROL_TYPE_CALENDER:

                            break;*//*
                        case CONTROL_TYPE_AUDIO_PLAYER:

                            break;
                        case CONTROL_TYPE_VIDEO_PLAYER:

                            break;
                        case CONTROL_TYPE_PERCENTAGE:
                            Percentage PercentageView = (Percentage) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            PercentageView.getCustomEditText().setText(Value);
                            CustomTextView tv_pertapTextType = PercentageView.gettap_text();
                            tv_pertapTextType.setVisibility(View.GONE);
                            PercentageView.getCustomEditText().setVisibility(View.VISIBLE);
                            break;
                        case CONTROL_TYPE_RADIO_BUTTON:
                            RadioGroupView RGroup = (RadioGroupView) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            boolean foundflag = true;
                            List<Item> AppendValues_list_rg = new ArrayList<>();


                            if (ControlID.equalsIgnoreCase(temp_controlObj.getControlName())) {
//                                List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                                List<String> Values = new ArrayList<>();
                                if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                                    if (MappedControlID.trim().length() > 0) {
                                        Values = RealmDBHelper.getColumnDataInList(context, tableName, colName);
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
                                            Values = RealmDBHelper.getColumnDataInList(context, tableName, colName);
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
                                    List<String> Values = RealmDBHelper.getColumnDataInList(context, tableName, colName);
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
                            DropDown dropDown = (DropDown) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            boolean dropfoundflag = true;
                            List<Item> AppendValues_list = new ArrayList<>();


                            if (ControlID.equalsIgnoreCase(temp_controlObj.getControlName())) {
//                                List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                                List<String> Values = new ArrayList<>();
                                if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                                    if (MappedControlID.trim().length() > 0) {
                                        Values = RealmDBHelper.getColumnDataInList(context, tableName, colName);
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
                                            Values = RealmDBHelper.getColumnDataInList(context, tableName, colName);
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
                                    List<String> Values = RealmDBHelper.getColumnDataInList(context, tableName, colName);
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
                            CheckList checklist = (CheckList) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            boolean CheckListflag = true;

                            List<Item> AppendValues_check_list = new ArrayList<>();


                            if (ControlID.equalsIgnoreCase(temp_controlObj.getControlName())) {
//                                List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                                List<String> Values = new ArrayList<>();
                                if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                                    if (MappedControlID.trim().length() > 0) {
                                        Values = RealmDBHelper.getColumnDataInList(context, tableName, colName);
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
                                            Values = RealmDBHelper.getColumnDataInList(context, tableName, colName);
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
                                    List<String> Values = RealmDBHelper.getColumnDataInList(context, tableName, colName);
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

                            break;
                        case CONTROL_TYPE_URL_LINK:
                            UrlView urlView = (UrlView) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            urlView.setText(Value);

                            break;
                        case CONTROL_TYPE_DECIMAL:
                            DecimalView Decimalview = (DecimalView) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            Decimalview.getCustomEditText().setText(Value);
                            CustomTextView tv_DectapTextType = Decimalview.gettap_text();
                            tv_DectapTextType.setVisibility(View.GONE);
                            Decimalview.getCustomEditText().setVisibility(View.VISIBLE);
                            break;
                        case CONTROL_TYPE_PASSWORD:
                            Password Passwordview = (Password) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            Passwordview.getCustomEditText().setText(Value);
                            LinearLayout ll_tap_text = Passwordview.gettap_text();
                            ll_tap_text.setVisibility(View.GONE);
                            Passwordview.getCustomEditText().setVisibility(View.VISIBLE);

                            break;
                        case CONTROL_TYPE_CURRENCY:
                            Currency Currencyview = (Currency) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            Currencyview.getCustomEditText().setText(Value);
                            CustomTextView tv_CurtapTextType = Currencyview.gettap_text();
                            tv_CurtapTextType.setVisibility(View.GONE);
                            Currencyview.getCustomEditText().setVisibility(View.VISIBLE);
                            break;
                        case CONTROL_TYPE_RATING:
                            break;
                        case CONTROL_TYPE_DYNAMIC_LABEL:
                            DynamicLabel DynamicLabeliew = (DynamicLabel) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            CustomTextView tv_dynamicLabel = DynamicLabeliew.getValueView();
//                            tv_dynamicLabel.setText(Value);
                            DynamicLabeliew.setValue(Value);
                            break;
                        case CONTROL_TYPE_QR_CODE:
                            QRCode qrCode = (QRCode) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            qrCode.createQrCodeDynamically(Value);
                            break;
                        case CONTROL_TYPE_BAR_CODE:
                            BarCode barCode = (BarCode) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            barCode.createBarCodeDynamically(Value);
                            break;
                        case CONTROL_TYPE_CALENDER:
                            Calendar calendar = (Calendar) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            calendar.getCe_TextType().setVisibility(View.VISIBLE);
                            if (Value != null && !Value.isEmpty()) {
//                                if(Value.length()>9 &&Value.charAt(10) == 'T'){
//                                    String[] defaultValue = Value.split("T");
//                                    calendar.setCalendarDate(defaultValue[0]);
//                                }else{
                                calendar.setSelectedDate(Value);
//                                }
                            }
                            break;
                        case CONTROL_TYPE_AUTO_COMPLETION:
                            AutoCompletionControl autoCompletionControl = (AutoCompletionControl) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            List<String> MappedValues = RealmDBHelper.getColumnDataInList(context, tableName, colName);
                            autoCompletionControl.setList_ControlItems(MappedValues);

                            break;
                        case CONTROL_TYPE_VIEWFILE:
                            ViewFileControl viewFileControl = (ViewFileControl) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            viewFileControl.setFileLink(Value);

                            break;
                        case CONTROL_TYPE_VOICE_RECORDING:
                            Value = Value.split("\\,")[0];
                            VoiceRecording voiceRecording = (VoiceRecording) context.List_ControlClassObjects.get(temp_controlObj.getControlName());
                            if (!Value.contains("File not found")) {
                                voiceRecording.setVoiceRecordingPath(Value);
                            }
                            break;
                    }
                }
            }*/

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "SetSingleValuetoControlForCallAPIorForm", e);
        }
    }

    private LinkedHashMap<String, List<String>> getDataFormRealm(String actionID,List<API_OutputParam_Bean> List_API_OutParams) {

        LinkedHashMap<String, List<String>> OutputData = new LinkedHashMap<>();
        List<String> l_tableNames = new ArrayList<>();
        List<String> l_ColNames = new ArrayList<>();
        List<API_OutputParam_Bean> api_outputParam_beans_updated=new ArrayList<>();
        for (int i = 0; i < List_API_OutParams.size(); i++) {
            if (!List_API_OutParams.get(i).isOutParam_Delete()) {
                api_outputParam_beans_updated.add(List_API_OutParams.get(i));
                String outputMapped_ID = outputParamswithPaths.get(List_API_OutParams.get(i).getOutParam_Mapped_ID().trim());
                String outputName = outputParamswithPaths.get(List_API_OutParams.get(i).getOutParam_Name());
                String temp_TableName = actionID;
                if (outputMapped_ID.contains("/")) {
                    String checkingColName=outputMapped_ID.substring(outputMapped_ID.lastIndexOf("/") + 1);
                    String temp = outputMapped_ID.substring(0, outputMapped_ID.lastIndexOf("/")).replace("/", "_");
                    if(RealmDBHelper.isAPIJsonArrayWithoutKeyExist(context,actionID,checkingColName)){
                        temp=outputMapped_ID.replace("/", "_");
                        temp_TableName = actionID + "_" + temp;
                    }else{
                        temp_TableName = actionID + "_" + temp;
                    }
                }
                List<String> ldata = RealmDBHelper.getTableData(context, RealmTables.APIMapping.TABLE_NAME, RealmTables.APIMapping.MapppingID, RealmTables.APIMapping.ActionIDWithTableName, temp_TableName);
                if (ldata.size() > 0) {
                    String tableName = ldata.get(0);
                    // String tableName = ActionBean.getActionId().substring(0, 9) + "_" + temp;
                    l_tableNames.add(tableName);
                    if (RealmDBHelper.isModifyColNameExist(context,outputMapped_ID)) {
                        String colName = outputMapped_ID.substring(outputMapped_ID.lastIndexOf("/") + 1);
                        l_ColNames.add(tableName + "_" + colName);
                    } else {
                        String colName = outputMapped_ID.substring(outputMapped_ID.lastIndexOf("/") + 1);
                        l_ColNames.add(colName);
                    }

                }

                //List<String> MappedValues = RealmDBHelper.getColumnDataInList(context, tableName, colName);
                //OutputData.put(List_API_OutParams.get(i).getOutParam_Mapped_ID().trim(), MappedValues);
            }
        }
        if (l_tableNames.size() > 0) {
            OutputData = RealmDBHelper.getTableDataInLHM(context, getTableName(l_tableNames), l_ColNames, api_outputParam_beans_updated);
        }

        return OutputData;
    }

    private String getTableName(List<String> l_tableNames) {
        String tableName = l_tableNames.get(0);
        for (int i = 1; i < l_tableNames.size(); i++) {
            if (l_tableNames.get(i).contains(tableName)) {
                tableName = l_tableNames.get(i);
            }
        }
        return tableName;
    }

    private void failureCase() {
        closeProgressDialog();
        if (ActionBean.isMessage_FailNoRecordsIsEnable()) {
            ExpressionMainHelper ehelper = new ExpressionMainHelper();
            String Message = ehelper.ExpressionHelper(context, ActionBean.getMessage_Fail());
            if (ActionBean.getMessage_FailNoRecordsDisplayType().equalsIgnoreCase("2")) {
                if (MainActivity.getInstance() != null)
                    MainActivity.getInstance().ShowMessageDialogWithOk(context, Message, 2);
            } else {
                showToastRunOnUI(MainActivity.getInstance(), Message);
            }
        } else if (!ActionBean.isMessage_FailNoRecordsIsEnable()) {

        } else {
            showToastRunOnUI(MainActivity.getInstance(), "Your Transaction Failed  ...");
        }
        callbackListener.onFailure(new Throwable(statusMsgFailure));
    }

    private void successCase() {
        closeProgressDialog();
        if (ActionBean.isSuccessMessageIsEnable()) {
            ExpressionMainHelper ehelper = new ExpressionMainHelper();
            String Message = ehelper.ExpressionHelper(context, ActionBean.getMessage_Success());
            if (ActionBean.getMessage_SuccessDisplayType().equalsIgnoreCase("2")) {
                MainActivity.getInstance().ShowMessageDialogWithOk(context, Message, 2);
            } else {
                ImproveHelper.showToastRunOnUI(MainActivity.getInstance(), Message);
            }
        } else if (!ActionBean.isSuccessMessageIsEnable()) {

        } else {
            //ImproveHelper.showToast(getApplicationContext(), "Data Recevied...");
        }

        callbackListener.onSuccess(statusMsgSuccess);
    }

    private void setResult() {
        try {
            if (fromsms) {
                //ImproveHelper.showToast(context, "SMS Sent..");
                statusCode = "200";
                statusMsgSuccess = "SMS Sent...";
                handCloseProgressBarSuccess.sendEmptyMessage(0);
            } else {
                boolean FileData = false;
                FileData = context.getTypeofService(APIDetails.getSuccessCaseDetails());
                if (FileData) {
                    //nk OutputData = context.sk_filedata.OutputData;
                } else if (ServiceSource.equalsIgnoreCase("Service Based")) {
                    if (APIDetails.getServiceCallsAt().equalsIgnoreCase("Server")) {
                        //nk OutputData = context.sk_Rest_interpreter.OutputData;
                    } else {
                        if (ServiceType.trim().equalsIgnoreCase("Soap Web Service")) {
                            //nk OutputData = context.sk_soapobj.OutputData;
                        } else if (ServiceType.trim().equalsIgnoreCase("WCF Service")) {
                            //nk OutputData = context.sk_Restobj_WCF.OutputData;
                        } else {
                            //nk OutputData = context.sk_Restobj.OutputData;
                        }
                    }
                } else {
                    //nk OutputData = context.sk_Rest_interpreterQuery.OutputData;
                }

                statusCode = "200";
                statusMsgSuccess = "Success";
               // List<API_OutputParam_Bean> list_API_OutParams = ActionBean.getList_Form_OutParams().size()==0?ActionBean.getList_API_OutParams():ActionBean.getList_Form_OutParams();
               // LinkedHashMap<String, List<String>> OutputData = getDataFormRealm(ActionBean.getActionId(),List_API_OutParams);
                if (ServiceResult.equalsIgnoreCase("None")) {
                    handCloseProgressBarSuccess.sendEmptyMessage(0);
                } else if (ServiceResult.equalsIgnoreCase("Single")) {
                    List<API_OutputParam_Bean> List_API_OutParams = ActionBean.getList_Form_OutParams().size()==0?ActionBean.getList_API_OutParams():ActionBean.getList_Form_OutParams();
                    LinkedHashMap<String, List<String>> OutputData = getDataFormRealm(ActionBean.getActionId(),List_API_OutParams);
                    for (int i = 0; i < List_API_OutParams.size(); i++) {
                        if (!List_API_OutParams.get(i).isOutParam_Delete()) {
                            SetSingleValuetoControlForCallAPIorForm_(List_API_OutParams.get(i), OutputData);
                        }
                    }
                    handCloseProgressBarSuccess.sendEmptyMessage(0);
                } else {
                    List<API_OutputParam_Bean> list_API_OutParams = ActionBean.getList_Form_OutParams().size()==0?ActionBean.getList_API_OutParams():ActionBean.getList_Form_OutParams();
                    LinkedHashMap<String, List<String>> OutputData = getDataFormRealm(ActionBean.getActionId(),list_API_OutParams);

                    //Outcols
                    String outputcolumns = "";
                    for (int i = 0; i < list_API_OutParams.size(); i++) {
                        if (list_API_OutParams.get(i).getOutParam_Mapped_ID() != null && !list_API_OutParams.get(i).getOutParam_Mapped_ID().equals("")) {
                            outputcolumns = outputcolumns + "," + list_API_OutParams.get(i).getOutParam_Mapped_ID();
                        } else if (list_API_OutParams.get(i).getList_OutParam_Languages() != null && list_API_OutParams.get(i).getList_OutParam_Languages().size() > 0) {
                            List<LanguageMapping> languageMappings = list_API_OutParams.get(i).getList_OutParam_Languages();
                            for (int j = 0; j < languageMappings.size(); j++) {
                                outputcolumns = outputcolumns + "," + languageMappings.get(j).getOutParam_Lang_Mapped();
                            }
                        }
                    }
                /*if (gpsContainInInParams) {
                    outputcolumns = outputcolumns + "," + "DistanceInKM";
                }*/
                    outputcolumns = outputcolumns.substring(outputcolumns.indexOf(",") + 1);
                    Log.e("Result: ", "" + outputcolumns.split("\\,").toString());
                    String SelectedSubForm = ActionBean.getSelectedSubForm();
                    if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_SUBFORM)) {
                        SubformView subform = (SubformView) List_ControlClassObjects.get(SelectedSubForm);
                        subform.saveNewRowData(OutputData, list_API_OutParams);
                        subform.loadSubFormData(ActionBean, MainActivity.getInstance().subFormMapControls, MainActivity.getInstance().subFormMappedValues);
                        subform.setCallbackListener(new Callback() {
                            @Override
                            public void onSuccess(Object result) {
                                statusMsgSuccess= (String) result;
                                handCloseProgressBarSuccess.sendEmptyMessage(0);
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                statusMsgFailure=throwable.toString();
                                handCloseProgressBarFailure.sendEmptyMessage(0);
                            }
                        });

                    } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_GRIDFORM)) {
                        GridControl gridForm = (GridControl) List_ControlClassObjects.get(SelectedSubForm);
                        gridForm.saveNewRowData(OutputData, list_API_OutParams);
                        gridForm.loadGridControlData(ActionBean, MainActivity.getInstance().subFormMapControls, MainActivity.getInstance().subFormMappedValues);
                        gridForm.setCallbackListener(new Callback() {
                            @Override
                            public void onSuccess(Object result) {
                                statusMsgSuccess= (String) result;
                                handCloseProgressBarSuccess.sendEmptyMessage(0);
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                statusMsgFailure=throwable.toString();
                                handCloseProgressBarFailure.sendEmptyMessage(0);
                            }
                        });

                    } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_SECTION)) {
                        SectionControl sectionControl = (SectionControl) List_ControlClassObjects.get(SelectedSubForm);
                        ActionUitls.SetValuetoMultiControlInCallAPIFormUsedbyControlObject(context, 0, OutputData, list_API_OutParams, sectionControl.controlObject.getSubFormControlList(), List_ControlClassObjects);
                        handCloseProgressBarSuccess.sendEmptyMessage(0);
                    } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_DATAVIEWER)) {
                        DataViewer dataViewer = (DataViewer) List_ControlClassObjects.get(SelectedSubForm);
                        dataViewer.setOutputData(OutputData);
                        dataViewer.setActionBean(ActionBean);
                        dataViewer.setResultFromGetDataManageDataAPIData();
                        handCloseProgressBarSuccess.sendEmptyMessage(0);
                    } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_LISTVIEW)) {
                        String cName = ActionBean.getResult_ListView_FilterMappedControl();
                        String mapControl = ActionBean.getResult_ListView_FilterItem();
                        String mapControlID = ActionBean.getResult_ListView_FilterItemID();
                        List<String> MappedValues = getDataFromCallAPIResult(ActionBean.getActionId(),mapControl.toLowerCase());//OutputData.get(mapControl.toLowerCase());
                        List<String> MappedValuesIDS = getDataFromCallAPIResult(ActionBean.getActionId(),mapControlID.toLowerCase());//OutputData.get(mapControlID.toLowerCase());
                        Log.e("Result: ", "" + MappedValues.toString());
                        ActionUitls.SetMultipleValuesbyControlID(context, cName, MappedValues, MappedValuesIDS, dataCollectionObject, List_ControlClassObjects);
                        handCloseProgressBarSuccess.sendEmptyMessage(0);
                    } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_IMAGEVIEW)) {
                        String cName = ActionBean.getResult_ListView_FilterMappedControl();
                        String mapControl = ActionBean.getResult_ListView_FilterItem();
                        List<String> MappedValues = OutputData.get(mapControl);
                        ActionUitls.SetMultipleValuestoImageControl(context, cName, MappedValues, dataCollectionObject, List_ControlClassObjects);
                        handCloseProgressBarSuccess.sendEmptyMessage(0);
                    } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_MAPVIEW)) {
                        MapControl mapControl = (MapControl) List_ControlClassObjects.get(SelectedSubForm);
                        if (list_API_OutParams.get(0).getOutParam_Mapped_ID() != null && list_API_OutParams.get(0).getOutParam_Mapped_ID().length() > 0) {
                            API_OutputParam_Bean gpsBean = list_API_OutParams.get(0);
                            List<String> MappedValues1 = OutputData.get(gpsBean.getOutParam_Mapped_ID().toLowerCase());
                            List<String> ConditionColumn = new ArrayList<>();
                            HashMap<String, List<String>> hash_popupdata = new LinkedHashMap<String, List<String>>();
                            String Operator = "";
                            MainActivity.getInstance().subFormMapControls.add(mapControl);
                            MainActivity.getInstance().subFormMappedValues.add(MappedValues1);
                            String DefultMarker = gpsBean.getOutParam_Marker_defultImage();
                            String RenderingType = gpsBean.getOutParam_Marker_RenderingType();
                            if (gpsBean.getOutParam_MarkerAdvanced_ConditionColumn() != null) {
                                ConditionColumn = OutputData.get(gpsBean.getOutParam_MarkerAdvanced_ConditionColumn().toLowerCase());
                                Operator = gpsBean.getOutParam_MarkerAdvanced_Operator();
                            }
                            if (gpsBean.getOutParam_Marker_popupData() != null && gpsBean.getOutParam_Marker_popupData().size() > 0) {
                                for (int i = 0; i < gpsBean.getOutParam_Marker_popupData().size(); i++) {
                                    if (OutputData.containsKey(gpsBean.getOutParam_Marker_popupData().get(i).toLowerCase())) {
                                        hash_popupdata.put(gpsBean.getOutParam_Marker_popupData().get(i), OutputData.get(gpsBean.getOutParam_Marker_popupData().get(i).toLowerCase()));
                                    } else {
                                        hash_popupdata.put(gpsBean.getOutParam_Marker_popupData().get(i), RealmDBHelper.getColumnDataInList(context, ActionBean.getActionId(), gpsBean.getOutParam_Marker_popupData().get(i)));
                                    }
                                }
                            }

                            boolean replace = ActionBean.getSv_Multiple_multi_assignType().equalsIgnoreCase("Replace");

                            if (gpsBean.getList_OutParam_MarkerAdvanced_Items() != null && gpsBean.getList_OutParam_MarkerAdvanced_Items().size() > 0) {
                                List<ImageAdvanced_Mapped_Item> List_OutParam_MarkerAdvanced_Items = gpsBean.getList_OutParam_MarkerAdvanced_Items();

                                mapControl.setMapMarkersDynamically(RenderingType, DefultMarker, MappedValues1, ConditionColumn, Operator, List_OutParam_MarkerAdvanced_Items, gpsBean.getOutParam_Marker_popupData(), hash_popupdata, replace);
                            } else {
                                mapControl.setMapMarkersDynamically(RenderingType, DefultMarker, MappedValues1, null, null, null, gpsBean.getOutParam_Marker_popupData(), hash_popupdata, replace);
                            }

//                                        MapControl.setMapMarkersDynamically(RenderingType,DefultMarker,MappedValues1,null,null,null);

                        }

                        if (list_API_OutParams.size() > 1 && list_API_OutParams.get(1).getOutParam_Mapped_ID() != null) {
                            API_OutputParam_Bean transIDBean = list_API_OutParams.get(1);
                            List<String> transIdsList = OutputData.get(transIDBean.getOutParam_Mapped_ID().toLowerCase());
                            mapControl.setTransIdsList(transIdsList);
                        }
                        handCloseProgressBarSuccess.sendEmptyMessage(0);
                    } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_CALENDARVIEW)) {
                        CalendarEventControl CalendarEventControl = (CalendarEventControl) List_ControlClassObjects.get(SelectedSubForm);

                        String MappedValue_Date = list_API_OutParams.get(0).getOutParam_Mapped_ID();
                        String MappedValue_Message = list_API_OutParams.get(1).getOutParam_Mapped_ID();
                        List<String> MappedValues_Date = OutputData.get(MappedValue_Date.toLowerCase());
                        List<String> MappedValues_Message = OutputData.get(MappedValue_Message.toLowerCase());

                        for (int x = 0; x < MappedValues_Date.size(); x++) {
                            CalendarEventControl.AddDateDynamically("Single", MappedValues_Date.get(x), MappedValues_Message.get(x));
                        }
                        handCloseProgressBarSuccess.sendEmptyMessage(0);
                    } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_CHART)) {
                        ChartControl chartControl = (ChartControl) List_ControlClassObjects.get(SelectedSubForm);
                        chartControl.setChartData(ActionBean, OutputData);
                        handCloseProgressBarSuccess.sendEmptyMessage(0);
                    } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_DATA_TABLE)) {
                        DataTableControl dataTableControl = (DataTableControl) List_ControlClassObjects.get(SelectedSubForm);
                        List<String> outColumns = new ArrayList<>();
                        outColumns.addAll(Arrays.asList(outputcolumns.split("\\,")));
                        if (ActionBean.getMulti_DataType().equalsIgnoreCase("append")) {
                            LinkedHashMap<String, List<String>> datamap = dataTableControl.getExistingData(outColumns, OutputData);
                            dataTableControl.ClearData();
                            //nk realm :
                            //dataTableControl.setDataTableData(ActionBean, datamap, outColumns);
                            dataTableControl.setDataTableData(ActionBean, datamap);
                        } else if (ActionBean.getMulti_DataType().equalsIgnoreCase("Replace")) {
                            dataTableControl.ClearData();
                            //nk realm :
                            //dataTableControl.setDataTableData(ActionBean, datamap, outColumns);
                            dataTableControl.setDataTableData(ActionBean, OutputData);
                        }
                        handCloseProgressBarSuccess.sendEmptyMessage(0);
                    }
                }
            }
        } catch (Exception e) {
            statusCode = "100";
            statusMsgSuccess = e.getMessage();
            handCloseProgressBarFailure.sendEmptyMessage(0);
        }

    }

    private List<String> getDataFromCallAPIResult(String actionID,String outPutParameters_Key){
        List<String> vals=new ArrayList<>();
        String outPutParameters_Path = outputParamswithPaths.get(outPutParameters_Key);
        String temp_TableName = actionID;
        if (outPutParameters_Path.contains("/")) {
            String checkingColName=outPutParameters_Path.substring(outPutParameters_Path.lastIndexOf("/") + 1);
            String temp = outPutParameters_Path.substring(0, outPutParameters_Path.lastIndexOf("/")).replace("/", "_");
            if(RealmDBHelper.isAPIJsonArrayWithoutKeyExist(context,actionID,checkingColName)){
                temp=outPutParameters_Path.replace("/", "_");
                temp_TableName = actionID + "_" + temp;
            }else{
                temp_TableName = actionID + "_" + temp;
            }
        }
        List<String> ldata = RealmDBHelper.getTableData(context, RealmTables.APIMapping.TABLE_NAME, RealmTables.APIMapping.MapppingID, RealmTables.APIMapping.ActionIDWithTableName, temp_TableName);
        if (ldata.size() > 0) {
            String tableName = ldata.get(0);
            String colName=outPutParameters_Path.substring(outPutParameters_Path.lastIndexOf("/") + 1);
            if (RealmDBHelper.isModifyColNameExist(context,outPutParameters_Path)) {
                colName=tableName + "_" + colName;
            }
            //val = RealmDBHelper.getSingleColDataWithComma(context, tableName, colName);
            vals = RealmDBHelper.getSingleColDataInList(context, tableName, colName);
        }
        return vals;
    }

    private void clearData() {
        {
            LinkedHashMap<String, List<String>> OutputData = new LinkedHashMap<>();
            try {
                JSONObject jobj = new JSONObject();
                jobj.put("CallAPI_Execution", "Success");
                jobj.put("OutputData_Size", OutputData.size());

                ImproveHelper.Controlflow("CallAPI Execute", "Action", "CallAPI", jobj.toString());
            } catch (JSONException e) {
                ImproveHelper.improveException(context, TAG, "CallAPI Execute", e);
            }
            List<API_OutputParam_Bean> List_API_OutParams = ActionBean.getList_API_OutParams();
            if (ServiceResult.equalsIgnoreCase("Single")) {
                for (int i = 0; i < List_API_OutParams.size(); i++) {
                    if (!List_API_OutParams.get(i).isOutParam_Delete()) {
                        context.SetNoDatatoControlForCallAPIorForm(List_API_OutParams.get(i));
                    }
                }
            } else {
                String SelectedSubForm = ActionBean.getSelectedSubForm();
                if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_SUBFORM)) {
                    SubformView subform = (SubformView) context.List_ControlClassObjects.get(SelectedSubForm);
                    if (!ActionBean.getMulti_DataType().equalsIgnoreCase("append")) {
                        subform.setiMinRows(0);
                        subform.setiMaxRows(Integer.parseInt(subform.controlObject.getMaximumRows()));
                        View view = subform.getSubFormView();
                        LinearLayout ll_MainSubFormContainer = view.findViewById(R.id.ll_MainSubFormContainer);
                        ll_MainSubFormContainer.removeAllViews();
                        List<LinkedHashMap<String, Object>> subform_List_ControlClassObjects = subform.getList_ControlClassObjects();
                        subform_List_ControlClassObjects.removeAll(subform_List_ControlClassObjects);
                    }
                } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_GRIDFORM)) {
                    GridControl subform = (GridControl) context.List_ControlClassObjects.get(SelectedSubForm);

                    View view = subform.getSubFormView();

                    if (!ActionBean.getMulti_DataType().equalsIgnoreCase("append")) {
                        TableLayout ll_grid_view = view.findViewById(R.id.ll_grid_view);

                        subform.SubFormTAG = 0;
                        List<LinkedHashMap<String, Object>> subform_List_ControlClassObjects = subform.getList_ControlClassObjects();

                        subform.setiMinRows(0);
                        subform.setiMaxRows(Integer.parseInt(subform.controlObject.getMaximumRows()));
                        ll_grid_view.removeAllViews();
                        subform_List_ControlClassObjects.removeAll(subform_List_ControlClassObjects);
                    }
                } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_DATAVIEWER)) {
                    DataViewer DataViewer = (DataViewer) context.List_ControlClassObjects.get(SelectedSubForm);
                    DataViewer.setOutputData(OutputData);
                    List<DataViewerModelClass> dataViewerModelClassList = new ArrayList<>();
                    if (ActionBean.getMulti_DataType().equalsIgnoreCase("append")) {
                        DataViewerModelClass dmv = new DataViewerModelClass();
                        dataViewerModelClassList.add(dmv);
                    }
                    DataViewer.SetDataViewerData(dataViewerModelClassList);

                } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_LISTVIEW)) {
                    String cName = ActionBean.getResult_ListView_FilterMappedControl();
                    context.SetMultipleValuesbyControlID(cName, null, null);
                } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_MAPVIEW)) {
                    MapControl MapControl = (MapControl) context.List_ControlClassObjects.get(SelectedSubForm);
                    MapControl.getGoogleMap().clear();

                } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_CALENDARVIEW)) {

                } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_CHART)) {
                }
            }

            if (AppConstants.GlobalObjects.getAPIs_status_ListHash() == null) {
                LinkedHashMap<String, String> tempAPI = new LinkedHashMap<String, String>();
                AppConstants.GlobalObjects.setAPIs_status_ListHash(tempAPI);
            }

            AppConstants.GlobalObjects.getAPIs_status_ListHash().put(APIDetails.getServiceName().toLowerCase(), "100");

            if (ActionBean.isMessage_SuccessNoRecordsIsEnable()) {
                ExpressionMainHelper ehelper = new ExpressionMainHelper();
                String Message = ehelper.ExpressionHelper(context, ActionBean.getMessage_SuccessNoRecords());
                if (ActionBean.getMessage_SuccessNoRecordsDisplayType().equalsIgnoreCase("2")) {
                    context.ShowMessageDialogWithOk(context, Message, 2);
                } else {
                    Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
                }


            } else if (!ActionBean.isMessage_SuccessNoRecordsIsEnable()) {

            } else {
                ImproveHelper.showToast(context, "No Data Found...");
            }
        }
    }

    private String getUniqueTableName() {
        String unique_tableName = UUID.randomUUID().toString().replaceAll("[^a-zA-Z0-9]", "").substring(0, 3);

        return unique_tableName;
    }

    private void createRealmObjFromJson(String actionID, String jsonStr, String tableName, String uniqueIDTableName, List<JSONKeyValueType> parentCols, boolean isFirstTime) throws JSONException {
        List<String> colNames = new ArrayList<>();
        List<String> colTypes = new ArrayList<>();
        List<String> colVals = new ArrayList<>();
        List<String> parentColNames = new ArrayList<>();
        List<JSONKeyValueType> updatedParentCols=new ArrayList<>();

        List<JSONKeyValueType> currentCols = RealmDBHelper.getJsonKeyAndValues(jsonStr);
        if (currentCols.size() > 0) {
            if (isFirstTime) {
                //check actionID in APIMapping Table if exist removed all mapped tables and delete record in APIMapping Table relative actionID
                // then create new unique id
                RealmDBHelper.removeAPIMappingTableBasedOnActionID(context, ActionBean.getActionId());
                //List<String> l_mappedTableNames = RealmDBHelper.getDataInRealResults(context, RealmTables.APIMapping.TABLE_NAME, RealmTables.APIMapping.MapppingID, new String[]{RealmTables.APIMapping.ActionID}, new String[]{actionID});
                //delete mappedTableName
                //RealmDBHelper.deleteTables(context, l_mappedTableNames);
                //delete rowdata based on mappedTableName in APIMapping Table
                //RealmDBHelper.rowDataDeleteMultiWithSingleCol(context, RealmTables.APIMapping.TABLE_NAME, RealmTables.APIMapping.MapppingID, l_mappedTableNames);
                uniqueIDTableName = getUniqueTableName();
                if (RealmDBHelper.existTable(context, uniqueIDTableName)) {
                    uniqueIDTableName = getUniqueTableName();
                }
            }
            //parentColNames for checking
            for (int i = 0; i < parentCols.size(); i++) {
                JSONKeyValueType col = parentCols.get(i);
                parentColNames.add(col.getKey());
            }
            //Table cols
            for (int j = 0; j < currentCols.size(); j++) {
                JSONKeyValueType col = currentCols.get(j);
                //no need to add as col when col type is JSONObject or JSONArray
              //  if(!col.getType().equalsIgnoreCase("JSONObject") && !col.getType().equalsIgnoreCase("JSONArray")){
                    //check whether col exist in parent cols
                    if (parentColNames.contains(col.getKey())) {
                        colNames.add(uniqueIDTableName + "_" + col.getKey());//123-2_id,weather_id
                        String modifiedCol = tableName.substring(tableName.indexOf("_") + 1) + "/" + col.getKey();
                        //modify colname update in currentCols
                        currentCols.get(j).setKey(uniqueIDTableName + "_" + col.getKey());
                        RealmDBHelper.createTableWithInsertForAPIModifyCol(context,actionID,col.getKey(),modifiedCol);
                        updatedParentCols.add(currentCols.get(j));
                    } else {
                        colNames.add(col.getKey());
                        updatedParentCols.add(currentCols.get(j));
                    }
                    colTypes.add(col.getType());
                    colVals.add(col.getValue());
              //  }
            }
            //if no cols then no need to create table, otherwise create table
           // if(colNames.size()>0){
                //parent cols adding last in row
                for (int i = 0; i < parentCols.size(); i++) {
                    JSONKeyValueType col = parentCols.get(i);
                    colNames.add(col.getKey());
                    colTypes.add(col.getType());
                    colVals.add(col.getValue());
                    updatedParentCols.add(col);
                }
                JSONTableColsVals tableColsVals = new JSONTableColsVals();
                tableColsVals.setActionId(actionID);
                tableColsVals.setTableName(tableName);
                tableColsVals.setMappingTableName(uniqueIDTableName);
                tableColsVals.setColNames(colNames);
                tableColsVals.setColValues(colVals);
                tableColsVals.setColTypes(colTypes);
                jsonTableColsVals.add(tableColsVals);
            //}
        }
        nextTableCreation(currentCols,uniqueIDTableName,actionID,tableName,updatedParentCols);

    }

    private void nextTableCreation(List<JSONKeyValueType> currentCols,String uniqueIDTableName,String actionID,
                                   String tableName,List<JSONKeyValueType> parentCols) throws JSONException {
        //checking JSONObject or JSONArray
        for (int j = 0; j < currentCols.size(); j++) {
            int index = j + 1;
            JSONKeyValueType col = currentCols.get(j);
            String uniqueIDTableName_Update = uniqueIDTableName + "-" + index;
            String tableName_Update=tableName + "_" + col.getKey();
            if (col.getType().equalsIgnoreCase("JSONObject")) {
                List<JSONKeyValueType> upadtedParentCols=getParentCols(currentCols,parentCols);
                //List<JSONKeyValueType> upadtedParentCols=getUpdatedParentCols(tableName_Update);
                createRealmObjFromJson(actionID, col.getValue(), tableName_Update, uniqueIDTableName_Update,upadtedParentCols , false);
            } else if (col.getType().equalsIgnoreCase("JSONArray")) {
                JSONArray jsonArray = new JSONArray(col.getValue());
                if (col.isStrArray()) {
                    //insert colname into APIJsonArrayWithoutKey
                    RealmDBHelper.createTableWithInsertForAPIJsonArrayWithoutKey(context,actionID,col.getKey());
                    List<JSONKeyValueType> upadtedParentCols=getParentCols(currentCols,parentCols);
                    //List<JSONKeyValueType> upadtedParentCols=getUpdatedParentCols(tableName_Update);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //create jsonObj for unit
                        JSONObject tempObj = new JSONObject();
                        tempObj.put(col.getKey(), jsonArray.get(i).toString());
                        //uniqueIDTableName_Update=uniqueIDTableName_Update+"."+i;
                        createRealmObjFromJson(actionID, tempObj.toString(), tableName_Update, uniqueIDTableName_Update, upadtedParentCols, false);
                    }
                } else {
                    List<JSONKeyValueType> upadtedParentCols=getParentCols(currentCols,parentCols);
                    //List<JSONKeyValueType> upadtedParentCols=getUpdatedParentCols(tableName_Update);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //uniqueIDTableName_Update=uniqueIDTableName_Update+"."+i;
                        createRealmObjFromJson(actionID, jsonArray.getJSONObject(i).toString(), tableName_Update, uniqueIDTableName_Update,upadtedParentCols, false);
                    }
                }

            }
        }
    }

    private String convertXmlToJson(String xmlStr) {
        String jsonStr = "";
        try {
            JSONObject jsonObj = XML.toJSONObject(xmlStr);
            jsonStr = jsonObj.toString();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return jsonStr;
    }

    private List<String> getTableNames(String tableName){
        List<String> l_Tables=new ArrayList<>();
        String split_[]=tableName.split("_");
        String temp=tableName;
        for (int split = 0; split < split_.length-1; split++) {
            temp=temp.substring(0,temp.lastIndexOf("_"));
            l_Tables.add(temp);
        }
        return l_Tables;
    }

   /* private List<JSONKeyValueType> getUpdatedParentCols(String tableName) {
        List<JSONKeyValueType> ppcols = new ArrayList<>();
        List<String> l_tableName=getTableNames(tableName);
        for (int t = 0; t < l_tableName.size(); t++) {
            for (int i = 0; i < jsonTableColsOnly.size(); i++) {
                JSONTableColsVals jsonTableColsVals=jsonTableColsOnly.get(i);
                String _tableName=jsonTableColsVals.getTableName();
                if(l_tableName.contains(_tableName)){

                    for (int j = 0; j < jsonTableColsVals.getColNames().size(); j++) {
                        JSONKeyValueType jsonKeyValueType=new JSONKeyValueType();
                        jsonKeyValueType.setKey(jsonTableColsVals.getColNames().get(j));
                        jsonKeyValueType.setType(jsonTableColsVals.getColTypes().get(j));
                        jsonKeyValueType.setValue(jsonTableColsVals.getColValues().get(j));
                        ppcols.add(jsonKeyValueType);
                    }
                  break;
                }
            }
        }
        return ppcols;
    }*/

    private List<JSONKeyValueType> getParentCols(List<JSONKeyValueType> pcols,List<JSONKeyValueType> perviousParentCols) {
        List<JSONKeyValueType> ppcols = new ArrayList<>();//perviousParentCols;

        for (int j = 0; j < perviousParentCols.size(); j++) {
            JSONKeyValueType col = perviousParentCols.get(j);
            if (col.getType().equalsIgnoreCase("JSONObject")) {
            } else if (col.getType().equalsIgnoreCase("JSONArray")) {
            } else {
                ppcols.add(col);
            }
        }
        return ppcols;
    }

    private void showProgressDialog(String msg) {
        if (pd != null && pd.isShowing()) {
            pd.setMessage(msg);
        } else {
            pd = new ProgressDialog(context);
            pd.setMessage(msg);
            pd.setCancelable(false);
            pd.show();
        }
    }

    private void closeProgressDialog() {
        try {
            if (pd != null)
                pd.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void SetValuetoMultiControlInCallAPIFormUsedbyControlObject(int pos, LinkedHashMap<String, List<String>> OutputData,
                                                                       List<API_OutputParam_Bean> List_API_OutParams,
                                                                       List<ControlObject> ControlObj, LinkedHashMap<String, Object> List_ControlClassObjects) {
        try {

            for (int a = 0; a < List_API_OutParams.size(); a++) {
                if (!List_API_OutParams.get(a).isOutParam_Delete()) {

                    API_OutputParam_Bean API_OutputParam_Bean = List_API_OutParams.get(a);

                    String Value = "", ControlID = "";
                    String MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_ID().trim();
                    ControlID = API_OutputParam_Bean.getOutParam_Name();

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


                    for (int i = 0; i < ControlObj.size(); i++) {
                        ControlObject temp_controlObj = ControlObj.get(i);
                        boolean checkflag = false;
                        if (temp_controlObj.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_DROP_DOWN) ||
                                temp_controlObj.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_RADIO_BUTTON) ||
                                temp_controlObj.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CHECK_LIST)) {
                            if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ControlID.trim())) {
                                checkflag = true;
                            }
//                        else  if ((temp_controlObj.getControlName().trim()+"_ID").equalsIgnoreCase(ControlID.trim())) {
//                            checkflag=true;
//                        }

                        } else if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ControlID.trim())) {
                            checkflag = true;
                        }

                        if (checkflag) {
                            switch (temp_controlObj.getControlType()) {
                                case CONTROL_TYPE_TEXT_INPUT:
                                    TextInput clearTextView = (TextInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    Value = Value.split("\\,")[0];
                                    clearTextView.getCustomEditText().setText(Value);
                                    CustomTextView tv_tapTextType = clearTextView.gettap_text();
                                    tv_tapTextType.setVisibility(View.GONE);
                                    clearTextView.getCustomEditText().setVisibility(View.VISIBLE);
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

                                    break;
                                case CONTROL_TYPE_IMAGE:
                                    Image ImageView = (Image) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    String Advance_ImageSource = "", Advance_ConditionColumn = "";
                                    if (API_OutputParam_Bean.getOutParam_Mapped_ID() != null && API_OutputParam_Bean.getOutParam_Mapped_ID().length() > 0) {
                                        Value = OutputData.get(API_OutputParam_Bean.getOutParam_Mapped_ID().toLowerCase()).get(pos);
                                        ImproveHelper.loadImage_new(context, Value, ImageView.mainImageView, false, temp_controlObj.getImageData());
                                    } else if (API_OutputParam_Bean.getList_OutParam_ImageAdvanced_Items() != null && API_OutputParam_Bean.getList_OutParam_ImageAdvanced_Items().size() > 0) {
                                        if (API_OutputParam_Bean.getOutParam_ImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                            Advance_ImageSource = OutputData.get(API_OutputParam_Bean.getOutParam_ImageAdvanced_ImageSource().toLowerCase()).get(pos);
                                        }
                                        Advance_ConditionColumn = OutputData.get(API_OutputParam_Bean.getOutParam_ImageAdvanced_ConditionColumn().toLowerCase()).get(pos);

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
                                    Value = Value.split("\\,")[0];
                                    final String liValue = Value;

                                    LargeInputView.setDefaultValue(Value);
                                    /*CustomTextView tv_largetapTextType = LargeInputView.gettap_text();
                                    tv_largetapTextType.setVisibility(View.GONE);
                                    if(LargeInputView.isEditorModeEnabled()){
                                        LargeInputView.getCustomEditText().setVisibility(View.GONE);
                                        LargeInputView.getEditorLayout().setVisibility(View.VISIBLE);
                                        LargeInputView.getEditorToolBar().setVisibility(View.VISIBLE);
                                        RichTextEditor editor = LargeInputView.getTextEditor();
                                        editor.addLoadedListener(() -> {
                                            editor.setHtml(liValue.trim());
                                            return null;
                                        });
                                      *//*  LargeInputView.getTextEditor().setHtml(Value);*//*
                                    }else if(LargeInputView.isHTMLViewerEnabled()){
                                        LargeInputView.getCustomEditText().setVisibility(View.GONE);
                                        LargeInputView.getEditorLayout().setVisibility(View.VISIBLE);
                                        LargeInputView.getEditorToolBar().setVisibility(View.GONE);
                                        RichTextEditor editor = LargeInputView.getTextEditor();
                                        editor.addLoadedListener(() -> {
                                            editor.setHtml(liValue.trim());
                                            return null;
                                        });
//                                        LargeInputView.getTextEditor().setHtml(Value);
                                   }else {
                                        LargeInputView.getEditorLayout().setVisibility(View.GONE);
                                        LargeInputView.getCustomEditText().setVisibility(View.VISIBLE);
                                        LargeInputView.getCustomEditText().setText(Value);
                                    }*/
                                    break;
                                case CONTROL_TYPE_CHECKBOX:
                                    Checkbox CheckBoxView = (Checkbox) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    CheckBoxView.setValueToCheckBoxItem(Value);
                                    break;
                                case CONTROL_TYPE_FILE_BROWSING:
                                    FileBrowsing fileBrowsing = (FileBrowsing) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    fileBrowsing.setFileBrowsing(Value);
                                    break;
                                case CONTROL_TYPE_CALENDER:

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
                                    boolean foundflag = true;

                                    List<Item> AppendValues_list0 = new ArrayList<>();

                                    List<String> rg_MappedValues = OutputData.get(MappedControlID.toLowerCase());


                                    if (OutputData.containsKey(ControlID + "_id")) {
                                        List<String> MappedValueIDs = OutputData.get((MappedControlID + "_id").toLowerCase());
                                        for (int j = 0; j < rg_MappedValues.size(); j++) {
                                            Item item = new Item();
                                            item.setValue(rg_MappedValues.get(j).trim());
                                            item.setId(MappedValueIDs.get(j).trim());
                                            AppendValues_list0.add(item);
                                        }
                                    } else {
                                        for (int j = 0; j < rg_MappedValues.size(); j++) {
                                            Item item = new Item();
                                            item.setValue(rg_MappedValues.get(j).trim());
                                            item.setId(rg_MappedValues.get(j).trim());
                                            AppendValues_list0.add(item);
                                        }

                                    }


                                    RGroup.setnewItemsListDynamically(AppendValues_list0);
                                    break;
                                case CONTROL_TYPE_DROP_DOWN:
                                    DropDown dropDown = (DropDown) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    boolean dropfoundflag = true;
                                    List<Item> AppendValues_list = new ArrayList<>();
                                    List<String> MappedValuesIds = new ArrayList<>();

                                    List<String> MappedValues = OutputData.get(MappedControlID.toLowerCase());

                                    for (int j = 0; j < List_API_OutParams.size(); j++) {
                                        API_OutputParam_Bean outputParam_bean = List_API_OutParams.get(j);
                                        if (!outputParam_bean.isOutParam_Delete()) {
                                            if (outputParam_bean.getOutParam_Name().toLowerCase().contentEquals((ControlID + "_ID").toLowerCase()) || outputParam_bean.getOutParam_Name().toLowerCase().contentEquals((ControlID + "ID").toLowerCase())) {
                                                MappedValuesIds = OutputData.get(outputParam_bean.getOutParam_Mapped_ID().toLowerCase());
                                                break;
                                            }
                                        }

                                    }

                                    /*if (OutputData.containsKey(ControlID + "_id")) {
                                        List<String> MappedValueIDs = OutputData.get((MappedControlID + "_id").toLowerCase());
                                        for (int j = 0; j < MappedValues.size(); j++) {
                                            Item item = new Item();
                                            item.setValue(MappedValues.get(j).trim());
                                            item.setId(MappedValueIDs.get(j).trim());
                                            AppendValues_list.add(item);
                                        }
                                    } else {
                                        for (int j = 0; j < MappedValues.size(); j++) {
                                            Item item = new Item();
                                            item.setValue(MappedValues.get(j).trim());
                                            item.setId(MappedValues.get(j).trim());
                                            AppendValues_list.add(item);
                                        }

                                    } */
                                    if (MappedValuesIds != null && MappedValuesIds.size() > 0) {
                                        /*List<String> MappedValueIDs = OutputData.get((MappedControlID + "_id").toLowerCase());*/
                                        for (int j = 0; j < MappedValues.size(); j++) {
                                            Item item = new Item();
                                            item.setValue(MappedValues.get(j).trim());
                                            item.setId(MappedValuesIds.get(j).trim());
                                            AppendValues_list.add(item);
                                        }
                                    } else {
                                        for (int j = 0; j < MappedValues.size(); j++) {
                                            Item item = new Item();
                                            item.setValue(MappedValues.get(j).trim());
                                            item.setId(MappedValues.get(j).trim());
                                            AppendValues_list.add(item);
                                        }

                                    }


                                    if (dropfoundflag) {
                                        dropDown.setnewItemsListDynamically(AppendValues_list);
                                    }
                                    break;

                                case CONTROL_TYPE_CHECK_LIST:
                                    CheckList checklist = (CheckList) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    boolean CheckListflag = true;
                                    List<Item> AppendValues_list2 = new ArrayList<>();

                                    List<String> chk_MappedValues = OutputData.get(MappedControlID.toLowerCase());

                                    if (OutputData.containsKey(ControlID + "_id")) {
                                        List<String> MappedValueIDs = OutputData.get((MappedControlID + "_id").toLowerCase());
                                        for (int j = 0; j < chk_MappedValues.size(); j++) {
                                            Item item = new Item();
                                            item.setValue(chk_MappedValues.get(j).trim());
                                            item.setId(MappedValueIDs.get(j).trim());
                                            AppendValues_list2.add(item);
                                        }
                                    } else {
                                        for (int j = 0; j < chk_MappedValues.size(); j++) {
                                            Item item = new Item();
                                            item.setValue(chk_MappedValues.get(j).trim());
                                            item.setId(chk_MappedValues.get(j).trim());
                                            AppendValues_list2.add(item);
                                        }

                                    }

                                    if (CheckListflag) {
                                        checklist.setnewItemsListDynamically(AppendValues_list2);
                                    }

                                    break;
                                case CONTROL_TYPE_SIGNATURE:

                                    break;
                                case CONTROL_TYPE_URL_LINK:
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
                                    LinearLayout ll_tap_text = Passwordview.gettap_text();
                                    ll_tap_text.setVisibility(View.GONE);
                                    Passwordview.getCustomEditText().setVisibility(View.VISIBLE);

                                    break;
                                case CONTROL_TYPE_CURRENCY:
                                    Currency Currencyview = (Currency) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    Currencyview.getCustomEditText().setText(Value);
                                    CustomTextView tv_CurtapTextType = Currencyview.gettap_text();
                                    tv_CurtapTextType.setVisibility(View.GONE);
                                    Currencyview.getCustomEditText().setVisibility(View.VISIBLE);
                                    break;
                                case CONTROL_TYPE_RATING:
                                    Rating ratingControl = (Rating) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    ratingControl.setRating(Value);
                                    break;
                                case CONTROL_TYPE_DYNAMIC_LABEL:
                                    DynamicLabel DynamicLabeliew = (DynamicLabel) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    CustomTextView tv_dynamicLabel = DynamicLabeliew.getValueView();
//                                    tv_dynamicLabel.setText(Value);
                                    DynamicLabeliew.setValue(Value);
                                    break;
                                case CONTROL_TYPE_MAP:
                                    MapControl mapControl = (MapControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    if (List_API_OutParams.get(0).getOutParam_Mapped_ID() != null && List_API_OutParams.get(0).getOutParam_Mapped_ID().length() > 0) {
                                        String MappedValue = List_API_OutParams.get(0).getOutParam_Mapped_ID();
                                        List<String> MappedValues1 = OutputData.get(MappedValue.toLowerCase());
                                        List<String> finalMapValues = new ArrayList<>();
                                        finalMapValues.add(MappedValues1.get(pos));
                                        context.subFormMapControls.add(mapControl);
                                        context.subFormMappedValues.add(finalMapValues);
                                        String DefultMarker = List_API_OutParams.get(0).getOutParam_Marker_defultImage();

//                                        MapControl.setMapPonitsDynamically(AppConstants.map_Multiple_Marker, MappedValues);
                                        if (mapControl.getMapViewType() != null) {
//                                            mapControl.setMapPonitsDynamically(mapControl.getMapViewType(), MappedValues1);
                                            mapControl.setMapPonitsDynamically(mapControl.getMapViewType(), finalMapValues, DefultMarker);
                                        } else {
//                                            mapControl.setMapPonitsDynamically(AppConstants.map_Multiple_Marker, MappedValues1);
                                            mapControl.setMapPonitsDynamically(AppConstants.map_Multiple_Marker, finalMapValues, DefultMarker);
                                        }
                                    } else {

                                    }
                                    break;
                                case CONTROL_TYPE_QR_CODE:
                                    QRCode qrCode = (QRCode) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    qrCode.createQrCodeDynamically(Value);
                                    break;
                                case CONTROL_TYPE_BAR_CODE:
                                    BarCode barCode = (BarCode) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    barCode.createBarCodeDynamically(Value);
                                    break;
                                case CONTROL_TYPE_VIEWFILE:
                                    ViewFileControl viewFileControl = (ViewFileControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    viewFileControl.setFileLink(Value);

                                    break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "SetValuetoMultiControlInCallAPIFormUsedbyControlObject", e);
        }
    }


}
