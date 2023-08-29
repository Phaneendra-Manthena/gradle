package com.bhargo.user.utils;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUDIO_PLAYER;
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
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_GRID_CONTROL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_IMAGE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_LARGE_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_NUMERIC_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PASSWORD;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PERCENTAGE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PHONE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_POST;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_QR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_RADIO_BUTTON;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_RATING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SECTION;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SIGNATURE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SUBFORM;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_TEXT_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_TIME;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_URL_LINK;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_USER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_RECORDING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VOICE_RECORDING;
import static com.bhargo.user.utils.ImproveHelper.getControlType;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.Item;
import com.bhargo.user.R;
import com.bhargo.user.controls.advanced.BarCode;
import com.bhargo.user.controls.advanced.Gps_Control;
import com.bhargo.user.controls.advanced.GridControl;
import com.bhargo.user.controls.advanced.PostControl;
import com.bhargo.user.controls.advanced.QRCode;
import com.bhargo.user.controls.advanced.SectionControl;
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
import com.bhargo.user.controls.standard.NumericInput;
import com.bhargo.user.controls.standard.Password;
import com.bhargo.user.controls.standard.Percentage;
import com.bhargo.user.controls.standard.Phone;
import com.bhargo.user.controls.standard.RadioGroupView;
import com.bhargo.user.controls.standard.Rating;
import com.bhargo.user.controls.standard.SignatureView;
import com.bhargo.user.controls.standard.TextInput;
import com.bhargo.user.controls.standard.Time;
import com.bhargo.user.controls.standard.VideoRecording;
import com.bhargo.user.controls.standard.VoiceRecording;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.Callback;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import nk.mobileapps.spinnerlib.SearchableSpinner;

public class SetEditDataToControlValues {
    Context context;
    Activity activity;
    LinkedHashMap<String, Object> List_ControlClassObjects_;
    List<ControlObject> list_Control_;
    JSONObject jsonObjChildForm;
    String TAG = "SetJsonDataToControlValues";
    Callback callbackListener;
    ProgressDialog pd;

    public SetEditDataToControlValues(Context context, Activity activity,
                                      LinkedHashMap<String, Object> List_ControlClassObjects_,
                                      List<ControlObject> list_Control_) {
        this.context = context;
        this.activity = activity;
        this.List_ControlClassObjects_ = List_ControlClassObjects_;
        this.list_Control_ = list_Control_;
        //this.jsonObjChildForm = jsonObjChildForm;

    }

    public SetEditDataToControlValues(Activity activity) {
        this.activity = activity;
    }

    public void jsonWiseExecute(JSONObject jsonObjChildForm, Callback callbackListener) {
        this.jsonObjChildForm = jsonObjChildForm;
        this.callbackListener = callbackListener;
        new SetEditDataToControlValuesStart().execute();
    }

    public void objectWiseExecute(JSONObject jsonObjChildForm, Callback callbackListener) {
        //this.jsonObjChildForm = jsonObjChildForm;
        this.callbackListener = callbackListener;
        new SetEditDataToControlValuesStart().execute();
    }

    private void setDataToControlValues() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (int j = 0; j < list_Control_.size(); j++) {
                                ControlObject controlObject = list_Control_.get(j);
                                String controlName = list_Control_.get(j).getControlName();
                                String controlType = list_Control_.get(j).getControlType();
                                Log.d(TAG, "Set JSONDATA ControlNamesAndControlType: " + controlName + ":" + controlType);
                                switch (controlType) {
                                    case CONTROL_TYPE_SUBFORM:
                                        JSONArray subFormObjArray = getSubFormDataFromJson(controlName);
                                        if (subFormObjArray.length() > 0) {
                                            SubformView subview = (SubformView) List_ControlClassObjects_.get(controlName);
                                            setSubFormValuesForEditAfterEvents(controlObject, subFormObjArray, subview, false);
                                        }

                                        break;
                                    case CONTROL_TYPE_GRID_CONTROL:
                                        JSONArray gridFormObjArray = getSubFormDataFromJson(controlName);
                                        if (gridFormObjArray.length() > 0) {
                                            GridControl gridview = (GridControl) List_ControlClassObjects_.get(controlName);
                                            setGridControlValuesForEditAfterEvents(controlObject, gridFormObjArray, gridview, false);
                                        }
                                        break;
                                    case CONTROL_TYPE_SECTION:
                                        SectionControl sectionControl = (SectionControl) List_ControlClassObjects_.get(controlName);
                                        LinearLayout linearLayoutSection = sectionControl.getLl_sectionContainer();
                                        List<ControlObject> list_Control_section = sectionControl.getSectionControlList();
                                        for (int i_section = 0; i_section < linearLayoutSection.getChildCount(); i_section++) {
                                            View childViewSection = linearLayoutSection.getChildAt(i_section);
                                            String controlTypeSection = getControlType(list_Control_section, childViewSection.getTag().toString());
                                            JSONArray subformJsonSection = new JSONArray();
                                            ControlObject loadControlObjectSection = getControlObject(list_Control_section, childViewSection.getTag().toString());
                                            if (controlTypeSection.equalsIgnoreCase(CONTROL_TYPE_SUBFORM)) {
                                                JSONArray subFormObjArray_section = getSubFormDataFromJson(loadControlObjectSection.getControlName());
                                                if (subFormObjArray_section.length() > 0) {
                                                    SubformView subview_section = (SubformView) List_ControlClassObjects_.get(childViewSection.getTag().toString());
                                                    setSubFormValuesForEditAfterEvents(loadControlObjectSection, subFormObjArray_section, subview_section, false);
                                                }
                                            } else if (controlTypeSection.equalsIgnoreCase(CONTROL_TYPE_GRID_CONTROL)) {
                                                JSONArray gridFormObjArray_section = getSubFormDataFromJson(loadControlObjectSection.getControlName());
                                                if (gridFormObjArray_section.length() > 0) {
                                                    GridControl gridview_section = (GridControl) List_ControlClassObjects_.get(childViewSection.getTag().toString());
                                                    setGridControlValuesForEditAfterEvents(loadControlObjectSection, gridFormObjArray_section, gridview_section, false);
                                                }
                                            }
                                        }
                                        break;
                                    default://MainForm Control
                                        HashMap<String, String> controlValue = getEditValuesFromJSONObject(jsonObjChildForm, controlType, controlName);
                                        setEditValuesToControls(controlObject, controlType, controlName, controlValue, List_ControlClassObjects_);
                                        break;
                                }
                                if (j == list_Control_.size() - 1) {
                                    success();
                                }
                            }
                        } catch (Exception e) {
                            failure(e);
                            ImproveHelper.improveException(context, TAG, "EditThread", e);
                        }
                    }
                });
            }
        }).start();

    }
    public void setDataToControlValues(List<ControlObject>list_Control_,LinkedHashMap<String,Object> List_ControlClassObjects_,JSONObject jsonObjChildForm) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            showProgressDialog(activity, activity.getResources().getString(
                                    R.string.please_wait));
                            for (int j = 0; j < list_Control_.size(); j++) {
                                ControlObject controlObject = list_Control_.get(j);
                                String controlName = list_Control_.get(j).getControlName();
                                String controlType = list_Control_.get(j).getControlType();
                                Log.d(TAG, "Set JSONDATA ControlNamesAndControlType: " + controlName + ":" + controlType);
                                switch (controlType) {
                                    case CONTROL_TYPE_SUBFORM:
                                        JSONArray subFormObjArray = getSubFormDataFromJson(controlName);
                                        if (subFormObjArray.length() > 0) {
                                            SubformView subview = (SubformView) List_ControlClassObjects_.get(controlName);
                                            setSubFormValuesForEditAfterEvents(controlObject, subFormObjArray, subview, false);
                                        }

                                        break;
                                    case CONTROL_TYPE_GRID_CONTROL:
                                        JSONArray gridFormObjArray = getSubFormDataFromJson(controlName);
                                        if (gridFormObjArray.length() > 0) {
                                            GridControl gridview = (GridControl) List_ControlClassObjects_.get(controlName);
                                            setGridControlValuesForEditAfterEvents(controlObject, gridFormObjArray, gridview, false);
                                        }
                                        break;
                                    case CONTROL_TYPE_SECTION:
                                        SectionControl sectionControl = (SectionControl) List_ControlClassObjects_.get(controlName);
                                        LinearLayout linearLayoutSection = sectionControl.getLl_sectionContainer();
                                        List<ControlObject> list_Control_section = sectionControl.getSectionControlList();
                                        for (int i_section = 0; i_section < linearLayoutSection.getChildCount(); i_section++) {
                                            View childViewSection = linearLayoutSection.getChildAt(i_section);
                                            String controlTypeSection = getControlType(list_Control_section, childViewSection.getTag().toString());
                                            JSONArray subformJsonSection = new JSONArray();
                                            ControlObject loadControlObjectSection = getControlObject(list_Control_section, childViewSection.getTag().toString());
                                            if (controlTypeSection.equalsIgnoreCase(CONTROL_TYPE_SUBFORM)) {
                                                JSONArray subFormObjArray_section = getSubFormDataFromJson(loadControlObjectSection.getControlName());
                                                if (subFormObjArray_section.length() > 0) {
                                                    SubformView subview_section = (SubformView) List_ControlClassObjects_.get(childViewSection.getTag().toString());
                                                    setSubFormValuesForEditAfterEvents(loadControlObjectSection, subFormObjArray_section, subview_section, false);
                                                }
                                            } else if (controlTypeSection.equalsIgnoreCase(CONTROL_TYPE_GRID_CONTROL)) {
                                                JSONArray gridFormObjArray_section = getSubFormDataFromJson(loadControlObjectSection.getControlName());
                                                if (gridFormObjArray_section.length() > 0) {
                                                    GridControl gridview_section = (GridControl) List_ControlClassObjects_.get(childViewSection.getTag().toString());
                                                    setGridControlValuesForEditAfterEvents(loadControlObjectSection, gridFormObjArray_section, gridview_section, false);
                                                }
                                            }
                                        }
                                        break;
                                    default://MainForm Control
                                        HashMap<String, String> controlValue = getEditValuesFromJSONObject(jsonObjChildForm, controlType, controlName);
                                        if(!controlValue.isEmpty()) {
                                            setEditValuesToControls(controlObject, controlType, controlName, controlValue, List_ControlClassObjects_);
                                        }
                                        break;
                                }
                                if (j == list_Control_.size() - 1) {
                                    success();
                                }
                            }
                        } catch (Exception e) {
                            failure(e);
                            ImproveHelper.improveException(context, TAG, "EditThread", e);
                        }
                    }
                });
            }
        }).start();

    }

    private void success() {
        closeProgressDialog();
        if(callbackListener!=null) {
            callbackListener.onSuccess("Success");
        }
    }

    private void failure(Throwable throwable) {
        closeProgressDialog();
        if(callbackListener!=null) {
            callbackListener.onFailure(throwable);
        }
    }

    private JSONArray getSubFormDataFromJson(String controlName) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        JSONArray subForms = jsonObjChildForm.getJSONArray("SubForm");

        for (int sf = 0; sf < subForms.length(); sf++) {
            if (subForms.getJSONObject(sf).has(controlName)) {
                jsonArray = subForms.getJSONObject(sf).getJSONArray(controlName);
                break;
            }
        }
        return jsonArray;
    }

    private void setGridControlValuesForEditAfterEvents(ControlObject loadControlObject, JSONArray subformJsonArray, GridControl gridControl, boolean deleteOption) {
        try {
            List<ControlObject> list_Controls = gridControl.controlObject.getSubFormControlList();
            List<Integer> matchedIndex = new ArrayList<>();
            //test
            int grid_pos = 0;
            for (int sr = 0; sr < subformJsonArray.length(); sr++) {
                JSONObject jsonObject = subformJsonArray.getJSONObject(sr);
                TableLayout tl_MainSubFormContainer = gridControl.getSubFormView().getChildAt(0).findViewById(R.id.ll_grid_view);
                LinkedHashMap<String, Object> ObjectsView = null;
                boolean success = false;
                LinearLayout ll_innerSubFormContainer = null;
                for (int grid_formRows = 0; grid_formRows < tl_MainSubFormContainer.getChildCount(); grid_formRows++) {
                    if (!matchedIndex.contains(grid_formRows)) {
                        ObjectsView = gridControl.gridControl_List_ControlClassObjects.get(grid_formRows);
                        View view = tl_MainSubFormContainer.getChildAt(grid_formRows);
                        ll_innerSubFormContainer = (LinearLayout) view;
                        //try to use same method for subform and grid
                        success = ControlUtils.getGridControlRowValuesToCheck(context, list_Controls, ObjectsView, jsonObject);
                        if (success) {
                            matchedIndex.add(grid_formRows);
                            break;
                        }
                    }
                }
                if (success) {
                    //Exist Row Data Set
                    TableRow tableRow = (TableRow) tl_MainSubFormContainer.getChildAt(sr);
                    tableRow.setTag(jsonObject.getString("Trans_ID"));
                    setValueToGridControlForEditAfterEvents(list_Controls, ObjectsView, jsonObject, sr);
                } else {
                    //Add Row And Data Set
                    gridControl.addNewRowsFromEdit(activity, 1, deleteOption);
                    if (tl_MainSubFormContainer.getChildCount() > 0) {
                        int pos = tl_MainSubFormContainer.getChildCount() - 1;
                        LinkedHashMap<String, Object> ObjectsView_newRow = gridControl.gridControl_List_ControlClassObjects.get(pos);
                        TableRow tableRow = (TableRow) tl_MainSubFormContainer.getChildAt(pos);
                        tableRow.setTag(jsonObject.getString("Trans_ID"));
                        setValueToGridControlForEditAfterEvents(list_Controls, ObjectsView_newRow, jsonObject, pos);
                    }
                }
                //  gridControl.deleteStripsUpdateAfterApiCall(tl_MainSubFormContainer);
            }
        } catch (Exception e) {
            Log.d(TAG, "setSubForm_e: " + e);
            ImproveHelper.improveException(context, TAG, "EventExecute", e);
            failure(e);
        }
    }

    private void setValueToGridControlForEditAfterEvents(List<ControlObject> list_Controls, LinkedHashMap<String, Object> ViewObj, JSONObject jsonObject, int subFormRow) {
        for (int sc = 0; sc < list_Controls.size(); sc++) {
            ControlObject temp_controlObj = list_Controls.get(sc);
            String controlName = temp_controlObj.getControlID();
            String controlType = temp_controlObj.getControlType();

            HashMap<String, String> controlValue = getEditValuesFromJSONObject(jsonObject, controlType, controlName);
            Log.d(TAG, "controlName: " + controlName);
            Log.d(TAG, "controlValue: " + controlValue.get("Value"));
            setEditValuesToControls(temp_controlObj, controlType, controlName, controlValue, ViewObj);
        }
    }


    private void setSubFormValuesForEditAfterEvents(ControlObject loadControlObject,
                                                    JSONArray subformJsonArray,
                                                    SubformView subformView, boolean deleteOption) {
        try {
            List<ControlObject> list_Controls = subformView.controlObject.getSubFormControlList();
            boolean rowsAdded = false;
            int subformRows = 0;
            int pos = 0;
            for (int sr = 0; sr < subformJsonArray.length(); sr++) {
                JSONObject jsonObject = subformJsonArray.getJSONObject(sr);
                LinearLayout ll_MainSubFormContainer = subformView.getSubFormView().findViewById(R.id.ll_MainSubFormContainer);//rows loop
                //For Grid
                if (ll_MainSubFormContainer == null) {
                    ll_MainSubFormContainer = subformView.getSubFormView().getChildAt(0).findViewById(R.id.ll_grid_view);
                }
                LinkedHashMap<String, Object> ObjectsView = null;
                boolean success = false;
                LinearLayout ll_innerSubFormContainer = null;
                for (; subformRows < ll_MainSubFormContainer.getChildCount(); ) {
                    ObjectsView = subformView.subform_List_ControlClassObjects.get(subformRows);
                    View view = ll_MainSubFormContainer.getChildAt(subformRows);
                    if (ll_MainSubFormContainer.getChildCount() > Integer.parseInt(loadControlObject.getMinimumRows()) && deleteOption) {
                        ImageView iv_deleteSubForm = view.findViewById(R.id.iv_deleteSubForm);
                        iv_deleteSubForm.setVisibility(View.VISIBLE);
                    }
                    if (ll_MainSubFormContainer.getChildCount() != subformJsonArray.length()) {
                        if (!rowsAdded) {
                            for (int i = ll_MainSubFormContainer.getChildCount(); i < subformJsonArray.length(); i++) {
                                subformView.addNewRowsFromEdit(activity, 1, deleteOption);
                            }
                            rowsAdded = true;
                        }
                    } else {
                        rowsAdded = true;
                    }
                    ll_innerSubFormContainer = view.findViewById(R.id.ll_innerSubFormContainer);
                    //For Grid
                    if (ll_innerSubFormContainer == null) {
                        ll_innerSubFormContainer = (LinearLayout) view;
                    }
                    //pending comman method for grid and subform
                    success = getSubformRowValuesToCheck(ll_innerSubFormContainer, list_Controls, ObjectsView, jsonObject);
                    subformRows++;
                    if (success)
                        break;

                }
                if (success) {
                    setValueToControlForEditAfterEvents(ll_innerSubFormContainer, list_Controls, ObjectsView, jsonObject, sr);
                } else {
                    if (!rowsAdded) {
                        subformView.addNewRowsFromEdit(activity, 1, deleteOption);
                    }
                    if (ll_MainSubFormContainer.getChildCount() > 0) {
                        LinkedHashMap<String, Object> ObjectsView_newRow = subformView.subform_List_ControlClassObjects.get(pos);
                        View view_newRow = ll_MainSubFormContainer.getChildAt(pos);
                        if (ll_MainSubFormContainer.getChildCount() > Integer.parseInt(loadControlObject.getMinimumRows()) && deleteOption) {
                            ImageView iv_deleteSubForm = view_newRow.findViewById(R.id.iv_deleteSubForm);
                            iv_deleteSubForm.setVisibility(View.VISIBLE);
                        }
                        LinearLayout ll_innerSubFormContainer_newRow = view_newRow.findViewById(R.id.ll_innerSubFormContainer);
                        //For Grid
                        if (ll_innerSubFormContainer_newRow == null) {
                            ll_innerSubFormContainer_newRow = (LinearLayout) view_newRow;
                        }
                        pos++;
                        setValueToControlForEditAfterEvents(ll_innerSubFormContainer_newRow, list_Controls, ObjectsView_newRow, jsonObject, sr);
                    }
                }
                subformView.deleteStripsUpdateAfterApiCall(ll_MainSubFormContainer);
            }
        } catch (Exception e) {
            Log.d(TAG, "setSubForm_e: " + e);
            ImproveHelper.improveException(context, TAG, "EventExecute", e);
            failure(e);
        }
    }

    private ControlObject getControlObject(List<ControlObject> controlObjectList, String controlName) {
        ControlObject controlObject = new ControlObject();
        for (int i = 0; i < controlObjectList.size(); i++) {

            if (controlName.contentEquals(controlObjectList.get(i).getControlName())) {
                controlObject = controlObjectList.get(i);
                return controlObject;
            }
        }
        return controlObject;
    }

    private void setValueToControlForEditAfterEvents(LinearLayout linearLayout, List<ControlObject> list_Controls,
                                                     LinkedHashMap<String, Object> ViewObj, JSONObject jsonObject, int subFormRow) {
        try {
            for (int sc = 0; sc < linearLayout.getChildCount(); sc++) {
                LinearLayout chldLayout = (LinearLayout) linearLayout.getChildAt(sc);
                String controlName = "";
                String controlType = "";
                if (chldLayout.getTag() != null) {
                    controlName = chldLayout.getTag().toString();
                } else {
                    LinearLayout controlsView = chldLayout.findViewById(R.id.ll_control_view);
                    for (int i = 0; i < controlsView.getChildCount(); i++) {
                        LinearLayout iLayout = (LinearLayout) controlsView.getChildAt(i);
                        controlName = iLayout.getTag().toString();
//                        controlType = getControlType(list_Controls, controlName);
                    }
                }
                controlType = getControlType(list_Controls, controlName);
                ControlObject subObj = getControlObject(list_Controls, controlName);
                linearLayout.setTag(jsonObject.getString("Trans_ID"));
                HashMap<String, String> controlValue = getEditValuesFromJSONObject(jsonObject, controlType, controlName);
                Log.d(TAG, "controlName: " + controlName);
                Log.d(TAG, "controlValue: " + controlValue.get("Value"));
                setEditValuesToControls(subObj, controlType, controlName, controlValue, ViewObj);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setValueToControlForEditAfterEvents", e);
            failure(e);
        }
    }

    private boolean getSubformRowValuesToCheck(LinearLayout linearLayout, List<ControlObject> list_Controls, LinkedHashMap<String, Object> ViewObj, JSONObject jsonObject) {
        boolean rowValuesMatch = false;
        List<Boolean> rowValuesMatchBooleans = new ArrayList<>();
        for (int j = 0; j < linearLayout.getChildCount(); j++) {
            try {
                rowValuesMatch = false;
                LinearLayout chldLayout = (LinearLayout) linearLayout.getChildAt(j);
                String controlName = "";
                String controlType = "";
                if (chldLayout.getTag() != null) {
                    controlName = chldLayout.getTag().toString();
                } else {
                    LinearLayout controlsView = chldLayout.findViewById(R.id.ll_control_view);
                    for (int i = 0; i < controlsView.getChildCount(); i++) {
                        LinearLayout iLayout = (LinearLayout) controlsView.getChildAt(i);
                        controlName = iLayout.getTag().toString();
//                        controlType = getControlType(list_Controls, controlName);
                    }
                }
                Log.d(TAG, "setSubForm_name: " + controlName);
                controlType = getControlType(list_Controls, controlName);

//            ControlObject subObj = improveHelper.getControlObject(list_Controls, controlName);

                switch (controlType) {
                    case CONTROL_TYPE_TEXT_INPUT:
                        TextInput clearTextView = (TextInput) ViewObj.get(controlName);
                        String strTextInputValue = clearTextView.getTextInputValue().trim();
                        if (strTextInputValue != null && !strTextInputValue.isEmpty()) {
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
                            if (jsonObject.getString(controlName).equalsIgnoreCase(strEmailValue)) {
                                rowValuesMatch = true;
                                rowValuesMatchBooleans.add(rowValuesMatch);
                            } else {
                                rowValuesMatch = false;
                                rowValuesMatchBooleans.add(rowValuesMatch);
                            }
                        }
                        break;

                    case CONTROL_TYPE_LARGE_INPUT:
                        LargeInput LargeInputView = (LargeInput) ViewObj.get(controlName);
                        String strLargeInput = LargeInputView.getCustomEditText().getText().toString();
                        if (strLargeInput != null && !strLargeInput.isEmpty()) {
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

                        String enteredValue = jsonObject.getString(controlName);
                        if (enteredValue.equalsIgnoreCase(strCalendarValue)) {
                            rowValuesMatch = true;
                            rowValuesMatchBooleans.add(rowValuesMatch);
                        } else {
                            rowValuesMatch = false;
                            rowValuesMatchBooleans.add(rowValuesMatch);
                        }
                        break;
                    case CONTROL_TYPE_CHECKBOX:
                        Checkbox CheckBoxView = (Checkbox) ViewObj.get(controlName);
                        String text = CheckBoxView.getSelectedId().trim();
                        rowValuesMatch = jsonObject.getString(controlName).trim().equalsIgnoreCase(text);
                        rowValuesMatchBooleans.add(rowValuesMatch);
                        break;
                    case CONTROL_TYPE_AUDIO_PLAYER:

                        break;
                    case CONTROL_TYPE_VIDEO_PLAYER:

                        break;
                    case CONTROL_TYPE_PERCENTAGE:
                        Percentage PercentageView = (Percentage) ViewObj.get(controlName);
                        String strPercentage = PercentageView.getCustomEditText().getText().toString();
                        if (strPercentage != null && !strPercentage.isEmpty()) {
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
                            if (jsonObject.getString(controlName).equalsIgnoreCase(strRatingValue)) {
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
                            if (jsonObject.getString(controlName + "_id").equalsIgnoreCase(strDataControlId)) {
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

                }
            } catch (Exception e) {
                Log.d(TAG, "setSubForm_e_get: " + rowValuesMatch);
            }
        }
        rowValuesMatch = !rowValuesMatchBooleans.contains(false);
        Log.d(TAG, "setSubForm_get: " + rowValuesMatch);

        return rowValuesMatch;
    }


    //typeOfForm : MainForm,SubFrom,GridFrom,SectionForm
    private void setEditValuesToControls(ControlObject controlObject,
                                         String controlType,
                                         String controlName,
                                         HashMap<String, String> controlValue,
                                         LinkedHashMap<String, Object> viewObj) {
        if (controlValue.size() > 0) {
            /*if(typeOfForm.equals("MainForm")||typeOfForm.equals("SectionForm")){
                if (controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_DATA_CONTROL)) {
                    controlObject.setDefaultValue(controlValue.get("Value_id"));
                } else if (controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_DROP_DOWN)) {
                    controlObject.setDefaultValue(controlValue.get("Value_id"));
                } else if (controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_CHECK_LIST)) {
                    controlObject.setDefaultValue(controlValue.get("Value_id"));
                } else if (controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_GPS)) {
                    controlObject.setControlValue(controlValue.get("Coordinates"));
                }
            }*/

            switch (controlType) {
                case CONTROL_TYPE_TEXT_INPUT:
                    TextInput textInput = (TextInput) viewObj.get(controlName);
                    textInput.setEditValue(controlValue.get("Value"));
                    CustomTextView tv_tapTextType = textInput.gettap_text();
                    tv_tapTextType.setVisibility(View.GONE);
                    textInput.getCustomEditText().setVisibility(View.VISIBLE);

                    break;
                case CONTROL_TYPE_NUMERIC_INPUT:
                    NumericInput numverTextView = (NumericInput) viewObj.get(controlName);
                    numverTextView.getNumericTextView().setText(controlValue.get("Value"));
                    CustomTextView tv_numtapTextType = numverTextView.gettap_text();
                    tv_numtapTextType.setVisibility(View.GONE);
                    numverTextView.getNumericTextView().setVisibility(View.VISIBLE);
                    numverTextView.gettap_text().setVisibility(View.GONE);

                    break;
                case CONTROL_TYPE_PHONE:
                    Phone PhoneView = (Phone) viewObj.get(controlName);
                    PhoneView.getCustomEditText().setText(controlValue.get("Value"));
                    CustomTextView tv_phonetapTextType = PhoneView.gettap_text();
                    tv_phonetapTextType.setVisibility(View.GONE);
                    PhoneView.getCustomEditText().setVisibility(View.VISIBLE);

                    break;
                case CONTROL_TYPE_EMAIL:
                    Email EmailView = (Email) viewObj.get(controlName);
                    EmailView.getCustomEditText().setText(controlValue.get("Value"));
                    CustomTextView tv_emailtapTextType = EmailView.gettap_text();
                    tv_emailtapTextType.setVisibility(View.GONE);
                    EmailView.getCustomEditText().setVisibility(View.VISIBLE);

                    break;
                case CONTROL_TYPE_CAMERA:
                    Camera camera = (Camera) viewObj.get(controlName);
//                        if (controlValue.get("Value").startsWith("http")) {
                    camera.setImageForEdit(controlValue.get("Value"));
//                        }
                    //sanjay

                    break;
                case CONTROL_TYPE_IMAGE:
                    Image ImageView = (Image) viewObj.get(controlName);
                    if (controlValue.get("Value").startsWith("http")) {
                        String Value = controlValue.get("Value").split("\\,")[0];
                        controlObject.setImageData(Value);
                        if (isNetworkStatusAvialable(context)) {
                            Glide.with(context).load(Value).into(ImageView.mainImageView);
                        }
                    }

                    break;
                case CONTROL_TYPE_LARGE_INPUT:
                    LargeInput LargeInputView = (LargeInput) viewObj.get(controlName);
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

                    break;
                case CONTROL_TYPE_CHECKBOX:

                    Checkbox CheckBoxView = (Checkbox) viewObj.get(controlName);
                    CheckBoxView.setCheckBoxValues(controlValue);
                        /*View cbview = CheckBoxView.getCheckboxContainer().getChildAt(0);
                        CheckBox cb_main = cbview.findViewById(R.id.checkbox);
                        String text = cb_main.getText().toString().trim();
                        if (controlValue.get("Value").equalsIgnoreCase(text)) {
                            cb_main.setChecked(true);
                            break;
                        }*/

                    break;
                case CONTROL_TYPE_FILE_BROWSING:
                    FileBrowsing fileBrowsing = (FileBrowsing) viewObj.get(controlName);
                    if (controlValue.get("Value") != null && !controlValue.get("Value").trim().isEmpty()) {
                        fileBrowsing.setFileBrowsing(controlValue.get("Value"));
                        fileBrowsing.setPath(controlValue.get("Value"));
                    }

                    break;
                case CONTROL_TYPE_CALENDER:
                    Calendar calendar = (Calendar) viewObj.get(controlName);
                    String strCalendarValue = controlValue.get("Value");
                    if (strCalendarValue != null && !strCalendarValue.isEmpty()) {
                            if(strCalendarValue.contains("T")){
                                String[] defaultValue = strCalendarValue.split("T");
                                calendar.setSelectedDate(defaultValue[0]);
                            }else{
                        calendar.setSelectedDate(strCalendarValue);
                            }
                    }

                    break;
                case CONTROL_TYPE_AUDIO_PLAYER:

                    break;
                case CONTROL_TYPE_VIDEO_PLAYER:

                    break;
                case CONTROL_TYPE_PERCENTAGE:
                    Percentage PercentageView = (Percentage) viewObj.get(controlName);
                    PercentageView.getCustomEditText().setText(controlValue.get("Value"));
                    CustomTextView tv_pertapTextType = PercentageView.gettap_text();
                    tv_pertapTextType.setVisibility(View.GONE);
                    PercentageView.getCustomEditText().setVisibility(View.VISIBLE);

                    break;
                case CONTROL_TYPE_RADIO_BUTTON:
                    RadioGroupView radioGroupView = (RadioGroupView) viewObj.get(controlName);
                    if (radioGroupView != null) {
                        radioGroupView.setEditValue(controlValue);
                    }

                    break;
                case CONTROL_TYPE_DROP_DOWN:
                    DropDown dropDown = (DropDown) viewObj.get(controlName);
                    if(controlValue.containsKey("Value_id")) {
                        if (dropDown != null) {
                            dropDown.getControlObject().setDefaultValue(controlValue.get("Value_id"));
                            if (controlValue.get("Value_id").startsWith("0")) {
//                                dropDown.setItemId(controlValue.get("Value_id").substring(1),controlValue.get("Value"));
                                dropDown.setItemId(controlValue.get("Value_id"), controlValue.get("Value"));
                            } else {
                                dropDown.setItemId(controlValue.get("Value_id"), controlValue.get("Value"));
                            }
                        }
                    }else if(controlValue.containsKey("Value")){
                        if (dropDown != null) {
                            dropDown.setItemId("", controlValue.get("Value"));
                        }

                    }
                    break;
                case CONTROL_TYPE_CHECK_LIST:
                    CheckList checklist = (CheckList) viewObj.get(controlName);

                    checklist.getControlObject().setDefaultValue(controlValue.get("Value_id"));

                    List<Item> selectedItems = new ArrayList<>();
                    if (!controlValue.get("Value").contentEquals("")) {
                        String[] names = controlValue.get("Value").split("\\^");
                        if (!controlValue.get("Value_id").contentEquals("")) {
                            String[] ids = controlValue.get("Value_id").split("\\^");
                            for (int j = 0; j < names.length; j++) {
                                Item item = new Item();
                                item.setId(ids[j]);
                                item.setValue(names[j]);
                                selectedItems.add(item);
                            }
                        } else {
                            for (int j = 0; j < names.length; j++) {
                                Item item = new Item();
                                item.setId(names[j]);
                                item.setValue(names[j]);
                                selectedItems.add(item);
                            }
                        }
                    }
                    checklist.setListOfSelectedItems(selectedItems);

                    break;
                case CONTROL_TYPE_SIGNATURE:
                    SignatureView signatureView = (SignatureView) viewObj.get(controlName);
                    signatureView.setSignatureForEdit(controlValue.get("Value"));

                    break;
                case CONTROL_TYPE_URL_LINK:
                    break;
                case CONTROL_TYPE_DECIMAL:
                    DecimalView Decimalview = (DecimalView) viewObj.get(controlName);
                    Decimalview.getCustomEditText().setText(controlValue.get("Value"));
                    CustomTextView tv_DectapTextType = Decimalview.gettap_text();
                    tv_DectapTextType.setVisibility(View.GONE);
                    Decimalview.getCustomEditText().setVisibility(View.VISIBLE);

                    break;
                case CONTROL_TYPE_PASSWORD:
                    Password Passwordview = (Password) viewObj.get(controlName);
                    Passwordview.getCustomEditText().setText(controlValue.get("Value"));
                    Passwordview.getTil_password().setVisibility(View.VISIBLE);

                    break;
                case CONTROL_TYPE_CURRENCY:
                    Currency Currencyview = (Currency) viewObj.get(controlName);
                    Currencyview.getCustomEditText().setText(controlValue.get("Value"));
                    CustomTextView tv_CurtapTextType = Currencyview.gettap_text();
                    tv_CurtapTextType.setVisibility(View.GONE);
                    Currencyview.getCustomEditText().setVisibility(View.VISIBLE);

                    break;
                case CONTROL_TYPE_RATING:
                    Rating rating = (Rating) viewObj.get(controlName);
                    rating.setRating(controlValue.get("Value"));

                    break;
                case CONTROL_TYPE_DYNAMIC_LABEL:
                    DynamicLabel DynamicLabeliew = (DynamicLabel) viewObj.get(controlName);
                    CustomTextView tv_dynamicLabel = DynamicLabeliew.getValueView();
//                            tv_dynamicLabel.setText(Value);
                    DynamicLabeliew.setValue(controlValue.get("Value"));
                    break;
                case CONTROL_TYPE_QR_CODE:
                    QRCode qrCode = (QRCode) viewObj.get(controlName);
                    if (!controlValue.get("Value").contentEquals("")) {
                        qrCode.createQrCodeDynamically(controlValue.get("Value"));
                    }
                    break;
                case CONTROL_TYPE_BAR_CODE:
                    BarCode barCode = (BarCode) viewObj.get(controlName);
                    if (!controlValue.get("Value").contentEquals("")) {
                        barCode.createBarCodeDynamically(controlValue.get("Value"));
                    }
                    break;
                case CONTROL_TYPE_DATA_CONTROL:
//                        if (!controlName.equalsIgnoreCase("State")) {
                    DataControl dataControl = (DataControl) viewObj.get(controlName);

                    dataControl.getControlObject().setDefaultValue(controlValue.get("Value_id"));

                    if (!controlValue.get("Value_id").contentEquals("")) {
                        SearchableSpinner searchableSpinner = dataControl.getSpinner();
                        searchableSpinner.setItemID(controlValue.get("Value_id"));
                    }

//                        }
                    break;
                case CONTROL_TYPE_GPS:
                    Gps_Control gps_control = (Gps_Control) viewObj.get(controlName);
                    gps_control.getControlObject().setControlValue(controlValue.get("Coordinates"));

                    if (!controlValue.get("Coordinates").trim().contentEquals("")) {
                        String coordinates = controlValue.get("Coordinates");
                        String type = controlValue.get("Type");
                        List<String> Points = ImproveHelper.getGpsCoordinates(coordinates);
                        gps_control.setMapPointsDynamically(type, Points, null);
                    }

                    break;
                case CONTROL_TYPE_TIME:
                    Time time_control = (Time) viewObj.get(controlName);
                    if (!controlValue.get("Value").contentEquals("")) {
                        time_control.setTimeData(controlValue.get("Value"));
                    }

                    break;
                case CONTROL_TYPE_VOICE_RECORDING:
                    VoiceRecording voiceRecording = (VoiceRecording) viewObj.get(controlName);
                    if ((!controlValue.get("Value").contentEquals("")) || (!controlValue.get("Value").contentEquals(" "))) {
                        String Value = controlValue.get("Value");
                        if (!Value.contains("File not found")) {
                            voiceRecording.setUploadedFile(Value);
                        }
                    } else {
                        voiceRecording.setUploadedFile("");
                    }
                    break;
                case CONTROL_TYPE_VIDEO_RECORDING:
                    if (!controlValue.get("Value").contentEquals("")) {
                        String Value = controlValue.get("Value");
                        VideoRecording videoRecording = (VideoRecording) viewObj.get(controlName);
                        if (!Value.contains("File not found")) {
                            videoRecording.setVideoRecordingPath(Value);
                            videoRecording.displayVideoPreview();
                        }
                    }
                    break;
                case CONTROL_TYPE_USER:
                    if (!controlValue.get("Value_id").contentEquals("")) {
                        String Value_id = controlValue.get("Value_id");
                        String Value = controlValue.get("Value");
                        UserControl userControl = (UserControl) viewObj.get(controlName);
                        userControl.setEditValues(Value, Value_id);
                    }
                    break;
                case CONTROL_TYPE_POST:
                    if (!controlValue.get("Value_id").contentEquals("")) {
                        String Value_id = controlValue.get("Value_id");
                        String Value = controlValue.get("Value");
                        PostControl postControl = (PostControl) viewObj.get(controlName);
                        postControl.setEditValues(Value, Value_id);
                    }
                    break;
            }
        }
    }

    private HashMap<String, String> getEditValuesFromJSONObject(JSONObject jsonObject, String controlType, String controlName) {
        HashMap<String, String> controlValue = new HashMap<>();
        String controlValue_id = null;
        try {
            if ((jsonObject.has(controlName) && !jsonObject.getString(controlName).equalsIgnoreCase("") && jsonObject.getString(controlName) != null) || (jsonObject.has(controlName + "_id") || jsonObject.has(controlName + "_Coordinates"))) {
                if (controlType.equalsIgnoreCase(CONTROL_TYPE_GPS)) {
                    controlValue.put("Coordinates", jsonObject.getString(controlName + "_Coordinates"));
                    controlValue.put("Type", jsonObject.getString(controlName + "_Type"));
                } else if (controlType.equalsIgnoreCase(CONTROL_TYPE_DATA_CONTROL)) {
                    controlValue.put("Value", jsonObject.getString(controlName));
                    controlValue.put("Value_id", jsonObject.getString(controlName + "_id"));
                } else if (controlType.equalsIgnoreCase(CONTROL_TYPE_CHECKBOX) || controlType.equalsIgnoreCase(CONTROL_TYPE_CHECK_LIST) || controlType.equalsIgnoreCase(CONTROL_TYPE_RADIO_BUTTON) || controlType.equalsIgnoreCase(CONTROL_TYPE_DROP_DOWN) || controlType.equalsIgnoreCase(CONTROL_TYPE_USER) || controlType.equalsIgnoreCase(CONTROL_TYPE_POST)) {
                    controlValue.put("Value", jsonObject.getString(controlName));
                    controlValue.put("Value_id", jsonObject.getString(controlName + "id"));
                } else {
                    controlValue.put("Value", jsonObject.getString(controlName));
                }
            }
        } catch (Exception e) {

        }
        if (controlValue.get("Value") != null && controlValue.get("Value").equalsIgnoreCase("null")) {
            controlValue.put("Value", "");
        }
        return controlValue;
    }

    private void showProgressDialog(Context context, String msg) {
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

    private class SetEditDataToControlValuesStart extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(context, context.getResources().getString(
                    R.string.please_wait));
        }

        @Override
        protected synchronized Void doInBackground(Void... voids) {
            setDataToControlValues();
            return null;
        }


        @Override
        protected synchronized void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

    }
}
