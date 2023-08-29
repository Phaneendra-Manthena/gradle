package com.bhargo.user.actions;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUDIO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_COMPLETION;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BAR_CODE;
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
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIEWFILE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VOICE_RECORDING;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;

import android.content.Context;
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
import com.bhargo.user.controls.advanced.PostControl;
import com.bhargo.user.controls.advanced.QRCode;
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
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import nk.mobileapps.spinnerlib.SearchableSpinner;

public class ActionUitls {
    static String TAG="ActionUitls";

    public static LinkedHashMap<String, List<String>> Convert_JSONForSQL(Context context,String serviceResponse, String[] OutParam_Names) {
        LinkedHashMap<String, List<String>> OutputData = new LinkedHashMap<String, List<String>>();
        try {
            JSONObject jmainobj = new JSONObject(serviceResponse);
            if (jmainobj.getString("Status").equalsIgnoreCase("200")) {
                JSONArray Jarr = jmainobj.getJSONArray("Data");
                //String outParameters = jmainobj.getString("OutParameters");
                //OutParam_Names = outParameters.split("\\|");
                for (int i = 0; i < Jarr.length(); i++) {
                    JSONObject Jobj = Jarr.getJSONObject(i);
                    for (int j = 0; j < OutParam_Names.length; j++) {
                        String jobjvalue = Jobj.getString(OutParam_Names[j]);
                        if (OutputData.containsKey(OutParam_Names[j].toLowerCase())) {
                            List<String> value = OutputData.get(OutParam_Names[j].toLowerCase().trim());
                            value.add(jobjvalue);
                            OutputData.put(OutParam_Names[j].toLowerCase().trim(), value);
                        } else {
                            List<String> value = new ArrayList<String>();
                            value.add(jobjvalue);
                            OutputData.put(OutParam_Names[j].toLowerCase().trim(), value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "Convert_JSONForSQL", e);
        }

        return OutputData;
    }

    public static LinkedHashMap<String, List<String>> Convert_JSONForDML(Context context,String serviceResponse, String[] OutParam_Names) {
        LinkedHashMap<String, List<String>> OutputData = new LinkedHashMap<String, List<String>>();
        try {
            JSONObject jmainobj = new JSONObject(serviceResponse);
            if (jmainobj.getString("Status").equalsIgnoreCase("200")) {
                for (int j = 0; j < OutParam_Names.length; j++) {
                    String jobjvalue = jmainobj.getString(OutParam_Names[j]);
                    if (OutputData.containsKey(OutParam_Names[j].toLowerCase())) {
                        List<String> value = OutputData.get(OutParam_Names[j].toLowerCase());
                        value.add(jobjvalue);
                        OutputData.put(OutParam_Names[j].toLowerCase(), value);
                    } else {
                        List<String> value = new ArrayList<String>();
                        value.add(jobjvalue);
                        OutputData.put(OutParam_Names[j].toLowerCase(), value);
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "Convert_JSONForDML", e);
        }

        return OutputData;
    }

   public static void SetNoDatatoControlForCallAPIorForm(Context context,API_OutputParam_Bean API_OutputParam_Bean,
                                                          DataCollectionObject dataCollectionObject,
                                                          LinkedHashMap<String, Object> List_ControlClassObjects ) {
        try {
            String Value = "", ControlID = "";
            ControlID = API_OutputParam_Bean.getOutParam_Name();

            boolean checkflag = false;
            for (int i = 0; i < dataCollectionObject.getControls_list().size(); i++) {
                ControlObject temp_controlObj = dataCollectionObject.getControls_list().get(i);
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
                            TextInput clearTextView = (TextInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            clearTextView.Clear();
                            break;
                        case CONTROL_TYPE_NUMERIC_INPUT:
                            NumericInput numverTextView = (NumericInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            numverTextView.Clear();
                            break;
                        case CONTROL_TYPE_PHONE:
                            Phone PhoneView = (Phone) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            PhoneView.Clear();
                            break;
                        case CONTROL_TYPE_EMAIL:
                            Email EmailView = (Email) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            EmailView.Clear();
                            break;
                        case CONTROL_TYPE_CAMERA:

                            break;
                        case CONTROL_TYPE_MAP:
                            MapControl mapControl = (MapControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            mapControl.getGoogleMap().clear();

                            break;
                        case CONTROL_TYPE_IMAGE:
                            Image ImageView = (Image) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            ImproveHelper.loadImage_new(context, null, ImageView.mainImageView, false, "null");

                            break;
                        case CONTROL_TYPE_LARGE_INPUT:
                            LargeInput LargeInputView = (LargeInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
//
                            LargeInputView.Clear();
                            break;
                        case CONTROL_TYPE_CHECKBOX:
                            Checkbox CheckBoxView = (Checkbox) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            LinearLayout cbview = CheckBoxView.getCheckbox();
                            cbview.removeAllViews();

                            break;
                        case CONTROL_TYPE_FILE_BROWSING:

                            break;
                      /*  case CONTROL_TYPE_CALENDER:

                            break;*/
                        case CONTROL_TYPE_AUDIO_PLAYER:

                            break;
                        case CONTROL_TYPE_VIDEO_PLAYER:

                            break;
                        case CONTROL_TYPE_PERCENTAGE:
                            Percentage PercentageView = (Percentage) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            PercentageView.Clear();
                            break;
                        case CONTROL_TYPE_RADIO_BUTTON:
                            RadioGroupView RGroup = (RadioGroupView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            RGroup.Clear();

                            break;
                        case CONTROL_TYPE_DROP_DOWN:
                            DropDown dropDown = (DropDown) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            dropDown.Clear();
                            break;
                        case CONTROL_TYPE_CHECK_LIST:
                            CheckList checklist = (CheckList) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            checklist.Clear();
                            break;
                        case CONTROL_TYPE_SIGNATURE:

                            break;
                        case CONTROL_TYPE_URL_LINK:
                            break;
                        case CONTROL_TYPE_DECIMAL:
                            DecimalView Decimalview = (DecimalView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            Decimalview.Clear();
                            break;
                        case CONTROL_TYPE_PASSWORD:
                            Password Passwordview = (Password) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            Passwordview.Clear();
                            break;
                        case CONTROL_TYPE_CURRENCY:
                            Currency Currencyview = (Currency) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            Currencyview.Clear();
                            break;
                        case CONTROL_TYPE_RATING:
                            break;
                        case CONTROL_TYPE_DYNAMIC_LABEL:
                            DynamicLabel DynamicLabeliew = (DynamicLabel) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            CustomTextView tv_dynamicLabel = DynamicLabeliew.getValueView();
                            DynamicLabeliew.setValue("");
                            break;
                        case CONTROL_TYPE_QR_CODE:
                            QRCode qrCode = (QRCode) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            qrCode.createQrCodeDynamically("");
                            break;
                        case CONTROL_TYPE_BAR_CODE:
                            BarCode barCode = (BarCode) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            barCode.createBarCodeDynamically("");
                            break;
                        case CONTROL_TYPE_CALENDER:
                            Calendar calendar = (Calendar) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            calendar.Clear();
                            break;

                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "SetNoDatatoControlForCallAPIorForm", e);
        }
    }


    public static void SetMultipleValuestoImageControl(Context context,String ControlID, List<String> Value,
                                                       DataCollectionObject dataCollectionObject,
                                                       LinkedHashMap<String, Object> List_ControlClassObjects) {
        try {
            for (int i = 0; i < dataCollectionObject.getControls_list().size(); i++) {
                List<Item> AppendValues_list = new ArrayList<Item>();
                List<String> AppendValuesStr_list = new ArrayList<String>();
                if (Value != null) {
                    for (int j = 0; j < Value.size(); j++) {
                        Item item = new Item();
                        item.setValue(Value.get(j));
                        AppendValuesStr_list.add(Value.get(j));
                        AppendValues_list.add(item);
                    }


                }
                ControlObject temp_controlObj = dataCollectionObject.getControls_list().get(i);
                if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ControlID.trim())) {
                    switch (temp_controlObj.getControlType()) {
                        case CONTROL_TYPE_IMAGE:
                            Image Image = (Image) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            Image.SetImagesDynamically(AppendValuesStr_list);

                            break;

                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "SetMultipleValuesbyControlID", e);
        }
    }
    public static LinkedHashMap<String, List<String>> ConvertGetvaluestoHashMap(Context context,
                                                                         String finalValue, int index, String DataSource) {
        LinkedHashMap<String, List<String>> finalList = new LinkedHashMap<String, List<String>>();

        try {
            if (index == 0) {
                String[] ColumList = finalValue.split("\\#");
                for (int i = 0; i < ColumList.length; i++) {
                    String Namestr = ColumList[i].substring(0, ColumList[i].indexOf("|"));
                    String Namevalu = ColumList[i].substring(ColumList[i].indexOf("|") + 1);
                    List<String> List_val = new ArrayList<>();
                    List_val.addAll(Arrays.asList(Namevalu.split("\\,")));
                    finalList.put(Namestr, List_val);
                }
            } else {

                String expressionData = "";
                if (DataSource.toLowerCase().startsWith("getnearbyvalue") ||
                        DataSource.toLowerCase().startsWith("getnearbyvaluewithinrange")) {
                    String[] ColumList = finalValue.split("\\#");
                    for (int i = 0; i < ColumList.length; i++) {

                        expressionData = DataSource.split("\\,")[1];

                        String[] ExpressionVals = expressionData.substring(expressionData.indexOf(":") + 1).split("\\.");
                        String ColumnName = ExpressionVals[2].substring(0, ExpressionVals[2].lastIndexOf(")"));

                        String Namevalu = ColumList[i];
                        List<String> List_val = new ArrayList<>();
                        List_val.addAll(Arrays.asList(Namevalu.split("\\,")));
                        finalList.put(ColumnName.toLowerCase(), List_val);
                    }
                } else {

//                        String[] ColumList=(finalValue.split("\\@")[0]).split("\\#");
                    try {
                        if (finalValue.trim().length() > 0) {
                            String[] ColumList = finalValue.split("\\#");
                            for (int i = 0; i < ColumList.length; i++) {

                                String ColumnName = ColumList[i].substring(0, ColumList[i].indexOf("|"));

                                String Namevalu = ColumList[i].substring(ColumList[i].indexOf("|") + 1);
                                List<String> List_val = new ArrayList<>();
                                List_val.addAll(Arrays.asList(Namevalu.split("\\,")));
                                finalList.put(ColumnName.toLowerCase(), List_val);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "ConvertGetvaluestoHashMap", e);
        }
        return finalList;
    }

    public static void SetMultipleValuesbyControlID(Context context, String ControlID, List<String> Value,
                                                    List<String> Ids,
                                                    DataCollectionObject dataCollectionObject,
                                                    LinkedHashMap<String, Object> List_ControlClassObjects) {
        try {
            for (int i = 0; i < dataCollectionObject.getControls_list().size(); i++) {
                List<Item> AppendValues_list = new ArrayList<Item>();
                List<String> AppendValuesStr_list = new ArrayList<String>();
                if (Value != null) {
                    for (int j = 0; j < Value.size(); j++) {
                        Item item = new Item();
                        item.setValue(Value.get(j));
                        if (Ids != null) {
                            item.setId(Ids.get(j));
                        } else {
                            item.setId(Value.get(j));
                        }
                        AppendValuesStr_list.add(Value.get(j));
                        AppendValues_list.add(item);
                    }


                }
                ControlObject temp_controlObj = dataCollectionObject.getControls_list().get(i);
                if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ControlID.trim())) {
                    switch (temp_controlObj.getControlType()) {
                        case CONTROL_TYPE_DROP_DOWN:
                            DropDown dropDown = (DropDown) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            boolean dropfoundflag = true;

                            Log.e("Result: ", "" + AppendValues_list);
                            if (dropfoundflag) {
                                dropDown.setnewItemsListDynamically(AppendValues_list);
                            }
                            break;
                        case CONTROL_TYPE_RADIO_BUTTON:
                            RadioGroupView RGroup = (RadioGroupView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            RGroup.setnewItemsListDynamically(AppendValues_list);
                            break;
                        case CONTROL_TYPE_CHECK_LIST:
                            CheckList checklist = (CheckList) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            checklist.setnewItemsListDynamically(AppendValues_list);
                            break;
                        case CONTROL_TYPE_CHECKBOX:
                            Checkbox checkBox = (Checkbox) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            checkBox.setValueToCheckBoxItems(Value);

                            break;
                        case CONTROL_TYPE_AUTO_COMPLETION:
                            AutoCompletionControl autoCompletionControl = (AutoCompletionControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            autoCompletionControl.setList_ControlItems(AppendValuesStr_list);

                            break;

                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, "ActionUtils", "SetMultipleValuesbyControlID", e);
        }
    }


    public static void SetValuetoMultiControlInCallAPIFormUsedbyControlObject(Context context, int pos, LinkedHashMap<String, List<String>> OutputData,
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
                                        if(MainActivity.getInstance()!=null){
                                            MainActivity.getInstance().subFormMapControls.add(mapControl);
                                            MainActivity.getInstance().subFormMappedValues.add(finalMapValues);
                                        }

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
            ImproveHelper.improveException(context, "ActionUtils", "SetValuetoMultiControlInCallAPIFormUsedbyControlObject", e);
        }
    }


}
