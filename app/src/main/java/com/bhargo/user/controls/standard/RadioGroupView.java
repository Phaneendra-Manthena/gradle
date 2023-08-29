package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.hideSoftKeyboard;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;
import static com.bhargo.user.utils.ImproveHelper.showSoftKeyBoard;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.core.content.ContextCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.Item;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.SelectVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomCheckBox;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomRadioButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.uisettings.pojos.ControlUIProperties;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RadioGroupView implements SelectVariables, UIVariables {


    private static final String TAG = "RadioGroupView";
    private final int RadioGroupViewTAG = 0;
    public List<Item> rgItems;
    Context context;
    RadioGroup rg_main;
    CustomEditText ce_other;
    ControlObject controlObject;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    View view;
    ImproveHelper improveHelper;
    CustomRadioButton radioButton;
    private LinearLayout linearLayout, ll_label,ll_displayName,ll_main_inside,ll_control_ui,layout_control,ll_tap_text;
    private CustomEditText ce_TextType;
    private CustomTextView tv_displayName, tv_hint,ct_showText;
    private ImageView iv_mandatory;
    private CustomTextView ct_alertTypeRadioGroup;
    private LinearLayout.LayoutParams layoutParams_horizontal;
    private LayoutInflater linflater = null;
    private LayoutInflater lInflater =  null;

    DataCollectionObject dataCollectionObject;
    ControlUIProperties controlUIProperties;

    public RadioGroupView(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        improveHelper = new ImproveHelper(context);
        initView();

    }
    public RadioGroupView(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName, DataCollectionObject dataCollectionObject, ControlUIProperties controlUIProperties) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        this.dataCollectionObject = dataCollectionObject;
        this.controlUIProperties = controlUIProperties;
        improveHelper = new ImproveHelper(context);
        initView();

    }

    public void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            rgItems = new ArrayList<>();
            addRadioGroupViewStrip(context);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getRadioGroupView() {

        return linearLayout;
    }

    public void addRadioGroupViewStrip(final Context context) {
        try {
            linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = lInflater.inflate(R.layout.control_radiogroup, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    view = linflater.inflate(R.layout.layout_radio_group_six, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {

                    view = linflater.inflate(R.layout.layout_radio_group_seven, null);
                /*MultiLineRadioGroup multiLineRadioGroup = new MultiLineRadioGroup(context);
                multiLineRadioGroup.setMaxInRow(3);*/
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("8")) {

                    view = linflater.inflate(R.layout.layout_radio_group_eight, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {

                    view = linflater.inflate(R.layout.layout_radio_group_default, null);
                } else {

                    view = linflater.inflate(R.layout.layout_radio_group_default, null);
                }
            } else {
//                view = linflater.inflate(R.layout.control_radiogroup, null);
                view = linflater.inflate(R.layout.layout_radio_group_default, null);
            }

            //Horizontal Alignment
            if (controlObject.isEnableHorizontalAlignment()) {
                view = linflater.inflate(R.layout.control_radiogroup_horizontal, null);
            }

            view.setTag(RadioGroupViewTAG);

            ce_TextType = view.findViewById(R.id.ce_TextType);
            rg_main = view.findViewById(R.id.rg_container);
            rg_main.setTag(controlObject.getControlName());
            ll_displayName = view.findViewById(R.id.ll_displayName);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            layout_control = view.findViewById(R.id.layout_control);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            ct_alertTypeRadioGroup = view.findViewById(R.id.ct_alertTypeText);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ce_other = view.findViewById(R.id.ce_otherchoice);
            ll_label = view.findViewById(R.id.ll_label);
            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            ce_other.setTag(controlObject.getControlName());
            ll_main_inside.setTag(controlObject.getControlName());
            ll_control_ui.setTag(controlObject.getControlName());
            layout_control.setTag(controlObject.getControlName());
            ct_showText = view.findViewById(R.id.ct_showText);

            setControlValues();
            setControlUISettings(null);
            ce_other.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence cs, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                        if (ce_other.getText() != null && ce_other.getText().toString().trim().length() > 0) {
                            controlObject.setItemSelected(isRadioGroupViewSelected());
                            controlObject.setText(getSelectedRadioButtonText());
                            controlObject.setControlValue(getSelectedRadioButtonID());
                        } else {
                            controlObject.setItemSelected(false);
                            controlObject.setText(getSelectedRadioButtonText());
                            controlObject.setControlValue(getSelectedRadioButtonID());
                        }
                }
            });


            linearLayout.addView(view);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addRadioGroupViewStrip", e);
        }
    }

    public void setnewItemsDynamically(List<String> newItems) {
//        rg_main.removeAllViews();
//        for (int count = 0; count < newItems.size(); count++) {
//            setRadioGroupItems(newItems.get(count),count, newItems.get(count));
//        }
    }

    public void setnewItemsListDynamically(List<Item> newItems) {
        try {
            rgItems = newItems;
            rg_main.removeAllViews();
            for (int count = 0; count < newItems.size(); count++) {
                Item item = newItems.get(count);
                setRadioGroupItems(item.getValue(), count, item.getId());
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setnewItemsListDynamically", e);
        }
    }

    public void addNewItemsListDynamically(List<Item> newItems) {
        try {
            //rg_main.removeAllViews();
            for (int count = 0; count < newItems.size(); count++) {
                Item item = newItems.get(count);
                setRadioGroupItems(item.getValue(), count, item.getId());
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addNewItemsListDynamically", e);
        }
    }

    public List<Item> getnewItemsListDynamically() {
        return rgItems;
    }

    private void setControlValues() {
        try {
            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.isNullAllowed()) {
                iv_mandatory.setVisibility(View.VISIBLE);
            } else {
                iv_mandatory.setVisibility(View.GONE);
            }

            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            }

            if (controlObject.getItemsList() != null && controlObject.getItemsList().size() > 0) {
                rgItems = controlObject.getItemsList();
                if (controlObject.isEnableSortByAlphabeticalOrder()) {
//                    rgItems = controlObject.getItemsList();
                    improveHelper.sortListAlphabetically(rgItems,true);
//                Collections.sort(rgItems, String.CASE_INSENSITIVE_ORDER);
                }/* else {
                    rgItems = controlObject.getItemsList();
                }*/

                for (int count = 0; count < rgItems.size(); count++) {
                    Item item = rgItems.get(count);
                    setRadioGroupItems(item.getValue(), count, item.getId());
                }
/*
                for (int count = 0; count < rgItems.size(); count++) {
                    Item item = rgItems.get(count);
                    setRadioGroupItems(item.getValue(), count, item.getId());
                }
*/


                if (controlObject.isEnableAllowOtherChoice()) {
                    String other_id = "other_" + rgItems.size();
                    ce_other.setTag(other_id);
                    setRadioGroupItems(context.getString(R.string.other), rgItems.size(), other_id);
                }


                rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(controlObject.isDisable()) {
                            if (rg_main.getChildCount() > 0 && ((RadioButton) rg_main.getChildAt(0)).getButtonTintList() != null) {
                                setRadioButtonTheme();
                            }
                        }
                        controlObject.setItemSelected(isRadioGroupViewSelected());
                        controlObject.setText(getSelectedRadioButtonText());
                        controlObject.setControlValue(getSelectedRadioButtonID());
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                if (SubformFlag) {
                                    AppConstants.SF_Container_position = SubformPos;
                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                }
                                if (AppConstants.GlobalObjects != null)
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                ((MainActivity) context).ChangeEvent(group);
                            }
                        }

                        RadioButton rb = group.findViewById(checkedId);
                        if (controlObject.isEnableAllowOtherChoice()&& rb != null &&  rb.getText().toString().equalsIgnoreCase(context.getString(R.string.other))) {
                            ce_other.setVisibility(View.VISIBLE);
                            ce_other.requestFocus();
                            showSoftKeyBoard(context, ce_other);
                            ce_other.setTag("other_"+rgItems.size());
                            controlObject.setItemSelected(isRadioGroupViewSelected());
                            controlObject.setText(getSelectedRadioButtonText());
                            controlObject.setControlValue(getSelectedRadioButtonID());

                        } else {
                            hideSoftKeyboard(context, ce_other);
                            ce_other.setText("");
                            ce_other.setTag("");
                            controlObject.setItemSelected(isRadioGroupViewSelected());
                            controlObject.setText(getSelectedRadioButtonText());
                            controlObject.setControlValue(getSelectedRadioButtonID());
                            ce_other.setVisibility(View.GONE);

                        }

                        ct_alertTypeRadioGroup.setVisibility(View.GONE);
                        ct_alertTypeRadioGroup.setText("");
                    }
                });
            }

/*if(controlObject.isEnableHorizontalAlignment()){
            setDisplaySettingsForRadioButton(context, radioButton, controlObject);}else{
    setDisplaySettings(context, tv_displayName, controlObject);
}*/
            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    private void setDisplaySettingsForRadioButton(Context context, CustomRadioButton radioButton, ControlObject controlObject) {

        radioButton.setTextSize(Float.parseFloat(controlObject.getTextSize()));
           /* if (controlObject.getTextHexColor() != null && controlObject.getTextHexColor() != "") {
                ce_TextType.setTextColor(Color.parseColor(controlObject.getTextHexColor()));
            }*/
           /* if (controlObject.getTextStyle().equalsIgnoreCase("Bold")) {

                Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
                ce_TextType.setTypeface(typeface_bold);
            } else if (controlObject.getTextStyle().equalsIgnoreCase("Italic")) {

                Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
                ce_TextType.setTypeface(typeface_italic);

        }*/
    }


    public void Clear() {
        try {
            rg_main.clearCheck();
            controlObject.setText(null);
            controlObject.setControlValue(null);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "Clear", e);
        }
    }

    private void setRadioGroupItems(String text, int id, String itemID) {
        try {
            lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = null;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 100);
            layoutParams.setMargins(0, 0, 0, 5);
            layoutParams_horizontal = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams_horizontal.setMargins(5, 5, 10, 5);
            layoutParams_horizontal.gravity = Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL;

            LinearLayout.LayoutParams layoutParamsNine = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            layoutParamsNine.setMargins(0, 0, 0, pxToDP(5));
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("8")) {
                    view = lInflater.inflate(R.layout.item_radiobutton_eight, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {
                    view = lInflater.inflate(R.layout.item_radiobutton_nine, null);
                } else {
                    view = lInflater.inflate(R.layout.item_radio_button_default, null);
//                    view = lInflater.inflate(R.layout.item_radiobutton, null);
                }
            } else {
                view = lInflater.inflate(R.layout.item_radio_button_default, null);

            }
            if (controlObject.isEnableHorizontalAlignment()) {
                view = lInflater.inflate(R.layout.item_radiobutton_horizontal, null);
            }

            radioButton = view.findViewById(R.id.radioButton);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {
                    radioButton.setLayoutParams(layoutParamsNine);
                } else {
                    radioButton.setLayoutParams(layoutParams);
                }
            }
            if (controlObject.isEnableHorizontalAlignment()) {
                radioButton.setLayoutParams(layoutParams_horizontal);
            }
            radioButton.setId(id);
            radioButton.setTag(itemID);
            radioButton.setText(text);

            rg_main.addView(radioButton);

            if (controlObject.getDefaultItem() != null) {
                if (controlObject.getDefaultItem().equalsIgnoreCase(text)) {
                    radioButton.setChecked(true);
                    controlObject.setItemSelected(true);
                    controlObject.setText(getSelectedRadioButtonText());
                    controlObject.setControlValue(getSelectedRadioButtonID());
                }
            }
            if (controlObject.getDefaultValue() != null) {
                if (controlObject.getDefaultValue().equalsIgnoreCase(text)) {
                    radioButton.setChecked(true);
                    controlObject.setItemSelected(true);
                    controlObject.setText(getSelectedRadioButtonText());
                    controlObject.setControlValue(getSelectedRadioButtonID());
                }
            }
            if (controlObject.isEnableHorizontalAlignment()) {
                setDisplaySettingsForRadioButton(context, radioButton, controlObject);
            }
            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            }

            setControlUISettings(radioButton);
            if (controlObject.isDisable()) {
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisable(), ll_tap_text, view);
            }


        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setRadioGroupItems", e);
        }
    }

    public boolean isRadioGroupViewSelected() {

        boolean isRadioGroupViewChecked = false;
        try {
            if (rg_main.getCheckedRadioButtonId() != -1) {
                isRadioGroupViewChecked = true;
            }
                if (ce_other.getVisibility() == View.VISIBLE) {
                    isRadioGroupViewChecked = ce_other.getText().toString().isEmpty() || !ce_other.getText().toString().isEmpty();
            }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "isRadioGroupViewSelected", e);
        }
        return isRadioGroupViewChecked;
    }

    public CustomTextView setAlertRadioGroup() {

        return ct_alertTypeRadioGroup;
    }

    //
    public boolean isRadioGroupOthersEmpty() {
        boolean isOtherChecked = false;
        if (controlObject.isEnableAllowOtherChoice()) {

            int radioButtonID = rg_main.getCheckedRadioButtonId();
            View radioButton = rg_main.findViewById(radioButtonID);
            int idx = rg_main.indexOfChild(radioButton);
            RadioButton r = (RadioButton) rg_main.getChildAt(idx);
            String selectedtext = r.getText().toString();

            if (selectedtext.equalsIgnoreCase(context.getString(R.string.other)) && ce_other.getText().toString().isEmpty()) {

                isOtherChecked = true;

            }
        }

        return isOtherChecked;


    }

    public String getSelectedRadioButtonString() {
        String value = "";
        if (rg_main.getCheckedRadioButtonId() != -1) {
            RadioButton rb_item = view.findViewById(rg_main.getCheckedRadioButtonId());
            if (rb_item.getText().toString().trim().equalsIgnoreCase("Other")) {
                value = ce_other.getText().toString().trim() + "," + ce_other.getText().toString().trim();
            } else {
                value = ((String) rb_item.getTag()).trim() + "," + rb_item.getText().toString().trim();
            }
        }
        return value;
    }

    public String getSelectedRadioButtonText() {
        String selectedRadioButtonText = null;
        try {
            int radioButtonID = rg_main.getCheckedRadioButtonId();
            if (radioButtonID != -1) {
                View radioButton = rg_main.findViewById(radioButtonID);
                int idx = rg_main.indexOfChild(radioButton);
                RadioButton r = (RadioButton) rg_main.getChildAt(radioButtonID);
                String selectedtext = r.getText().toString();

                if (selectedtext.equalsIgnoreCase(context.getString(R.string.other)) && !ce_other.getText().toString().isEmpty()) {
                    selectedRadioButtonText = ce_other.getText().toString().trim();
                } else {
                    selectedRadioButtonText = selectedtext;
                }
                Log.d("getSelectedRadioButton:", selectedRadioButtonText);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getSelectedRadioButtonText", e);
        }
        return selectedRadioButtonText;
    }

    public String getSelectedRadioButtonID() {
        String selectedRadioButtonText = null;
        try {
            int radioButtonID = rg_main.getCheckedRadioButtonId();
            if (radioButtonID != -1) {
                View radioButton = rg_main.findViewById(radioButtonID);
                int idx = rg_main.indexOfChild(radioButton);
                RadioButton r = (RadioButton) rg_main.getChildAt(idx);
                String selectedtext = r.getTag().toString();

                if (selectedtext.equalsIgnoreCase(context.getString(R.string.other)) && !ce_other.getText().toString().isEmpty()) {
                    selectedRadioButtonText = ce_other.getTag().toString().trim();
                } else {
                    selectedRadioButtonText = selectedtext;
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getSelectedRadioButtonID", e);
        }

        return selectedRadioButtonText;
    }


    public RadioGroup getRadioGroup() {

        return rg_main;
    }

    public void setEditValue(HashMap<String, String> controlValue) {
        try {
            if (rg_main != null) {
                for (int i = 0; i < rg_main.getChildCount(); i++) {
                    CustomRadioButton radioButton = (CustomRadioButton) rg_main.getChildAt(i);
                    String itemId = ((String) radioButton.getTag());
                    if (controlValue.containsKey("Value_id")&&controlValue.get("Value_id").contentEquals(itemId)) {
                        rg_main.check(radioButton.getId());
                        controlObject.setSelectedItem(new Item(itemId, radioButton.getText().toString()));
                        break;
                    }else if(controlValue.containsKey("Value")){
                        if(radioButton.getText().toString().equalsIgnoreCase(controlValue.get("Value"))){
                            rg_main.check(radioButton.getId());
                            controlObject.setSelectedItem(new Item(itemId, radioButton.getText().toString()));
                        }
                    }
                }
            }

            if (controlObject.isEnableAllowOtherChoice() && !controlValue.get("Value_id").isEmpty()) {
                rg_main.check(rgItems.size());
                ce_other.setText(controlValue.get("Value"));
                ce_other.setTag(controlValue.get("Value_id"));
                controlObject.setSelectedItem(new Item(controlValue.get("Value_id"), ce_other.getText().toString()));

            }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "check", e);
        }
    }

    public void check(String id) {
        try {
            if (rg_main != null && id != null && !id.isEmpty()) {
                for (int i = 0; i < rg_main.getChildCount(); i++) {
                    CustomRadioButton radioButton = (CustomRadioButton) rg_main.getChildAt(i);
                    String itemId = ((String) radioButton.getTag());
                    if (id.contentEquals(itemId)) {
                        rg_main.check(radioButton.getId());
                        controlObject.setSelectedItem(new Item(itemId, radioButton.getText().toString()));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "check", e);
        }
    }

    public int pxToDP(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
    }


    @Override
    public Item getSelectedItem() {
        return null;
    }

    @Override
    public void setSelectedItem(Item selectedItem) {
        check(selectedItem.getId());
        controlObject.setSelectedItem(selectedItem);
    }

    @Override
    public int getVisibility() {
        return 0;
    }

    @Override
    public void setVisibility(boolean visibility) {

        if (visibility) {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        } else {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }

    @Override
    public boolean isEnabled() {
        return controlObject.isDisable();
    }

    @Override
    public void setEnabled(boolean enabled) {
        //rg_main.setEnabled(!enabled);
//        setViewDisable(view, !enabled);
        controlObject.setDisable(!enabled);
//        setViewDisableOrEnableDefault(context,view, enabled);
        for (int i = 0; i < rg_main.getChildCount(); i++) {
            View view =   rg_main.getChildAt(i);
            improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), enabled, ll_tap_text, view);
        }
    }

    @Override
    public void clean() {

    }

    @Override
    public void loadItems(List<Item> items) {

        addNewItemsListDynamically(items);

    }

    @Override
    public void appendItems(List<Item> items) {

    }

    @Override
    public boolean isSortByAlphabetOrder() {
        return controlObject.isEnableSortByAlphabeticalOrder();
    }

    @Override
    public void setSortByAlphabetOrder(boolean enabled) {
        controlObject.setEnableSortByAlphabeticalOrder(enabled);
        if(enabled){
            if (controlObject.getItemsList() != null && controlObject.getItemsList().size() > 0) {
                rgItems = controlObject.getItemsList();
                if (controlObject.isEnableSortByAlphabeticalOrder()) {
                    improveHelper.sortListAlphabetically(rgItems, true);
                    setnewItemsListDynamically(rgItems);
                }
            }
        }
    }

    @Override
    public boolean isAllowOtherChoices() {
        return controlObject.isEnableAllowOtherChoice();
    }

    @Override
    public void setAllowOtherChoices(boolean enabled) {
        controlObject.setEnableAllowOtherChoice(enabled);
        if(enabled){
            if (controlObject.getItemsList() != null && controlObject.getItemsList().size() > 0) {
                rgItems = controlObject.getItemsList();
//                if (controlObject.isEnableSortByAlphabeticalOrder()) {
//                    improveHelper.sortListAlphabetically(rgItems, true);
                    setnewItemsListDynamically(rgItems);
                    setRadioGroupItems(context.getString(R.string.other), rgItems.size(), context.getString(R.string.other));
//                }
            }
        }

    }

    @Override
    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        }else{
            ct_showText.setVisibility(View.GONE);
        }
    }

    @Override
    public String getTextSize() {
        return controlObject.getTextSize();
    }

    @Override
    public void setTextSize(String size) {
        if (size != null) {
            controlObject.setTextSize(size);
            tv_displayName.setTextSize(Float.parseFloat(size));
        }

    }

    @Override
    public String getTextStyle() {
        return controlObject.getTextStyle();
    }

    @Override
    public void setTextStyle(String style) {
        if (style != null && style.equalsIgnoreCase("Bold")) {
            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
            tv_displayName.setTypeface(typeface_bold);
            controlObject.setTextStyle(style);
        } else if (style != null && style.equalsIgnoreCase("Italic")) {
            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
            tv_displayName.setTypeface(typeface_italic);
            controlObject.setTextStyle(style);
        }
    }

    @Override
    public String getTextColor() {
        return controlObject.getTextHexColor();
    }

    @Override
    public void setTextColor(String color) {
        if (color != null && !color.equalsIgnoreCase("")) {
            controlObject.setTextHexColor(color);
            controlObject.setTextColor(Color.parseColor(color));
            tv_displayName.setTextColor(Color.parseColor(color));
        }
    }

    /*General*/
    /*displayName,hint*/

    public String getDisplayName() {
        return controlObject.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        tv_displayName.setText(displayName);
        controlObject.setDisplayName(displayName);
    }

    public String getHint() {
        return controlObject.getHint();
    }

    public void setHint(String hint) {
        if (hint != null && !hint.isEmpty()) {
            tv_hint.setVisibility(View.VISIBLE);
            tv_hint.setText(hint);
        } else {
            tv_hint.setVisibility(View.GONE);
        }
        controlObject.setHint(hint);
    }

    /*Validations*/
    /*mandatory,mandatoryErrorMessage*/
    public boolean isMandatory() {
        return controlObject.isNullAllowed();
    }

    public void setMandatory(boolean mandatory) {
        iv_mandatory.setVisibility(mandatory ? View.VISIBLE : View.GONE);
        controlObject.setNullAllowed(mandatory);
    }

    public String getMandatoryErrorMessage() {
        return controlObject.getMandatoryFieldError();
    }

    public void setMandatoryErrorMessage(String mandatoryErrorMessage) {
        controlObject.setMandatoryFieldError(mandatoryErrorMessage);
    }

    /*Options*/
    /*hideDisplayName,enableSortByAlphabeticalOrder,enableAllowOtherChoice,enableHorizontalAlignment
    invisible/visibility
     */

    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        ll_label.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
        if (hideDisplayName) {
            tv_hint.setVisibility(View.GONE);
        }
    }

    public boolean isEnableSortByAlphabeticalOrder() {
        return controlObject.isEnableSortByAlphabeticalOrder();
    }

    public void setEnableSortByAlphabeticalOrder(boolean enableSortByAlphabeticalOrder) {
        controlObject.setEnableSortByAlphabeticalOrder(enableSortByAlphabeticalOrder);
    }

    public boolean isEnableAllowOtherChoice() {
        return controlObject.isEnableAllowOtherChoice();
    }

    public void setEnableAllowOtherChoice(boolean enableAllowOtherChoice) {
        controlObject.setEnableAllowOtherChoice(enableAllowOtherChoice);
    }

    public boolean isEnableHorizontalAlignment() {
        return controlObject.isEnableHorizontalAlignment();
    }

    public void setEnableHorizontalAlignment(boolean enableHorizontalAlignment) {
        controlObject.setEnableHorizontalAlignment(enableHorizontalAlignment);
        linearLayout.removeAllViews();
        addRadioGroupViewStrip(context);
    }

    public void setAlertRadioButtonError(boolean fromValidation) {
        for (int i = 0; i < rg_main.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) rg_main.getChildAt(i);
            if (radioButton.isChecked()) {
                radioButton.setButtonTintList(getRadioButtonColors());
            } else {
                if (fromValidation) {
                    radioButton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.control_error_color)));
                } else {
                    radioButton.setButtonTintList(getRadioButtonColors());
                }
            }
        }

    }

    public void setRadioButtonTheme() {
        for (int i = 0; i < rg_main.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) rg_main.getChildAt(i);
            radioButton.setButtonTintList(getRadioButtonColors());

        }
    }

    private ColorStateList getRadioButtonColors() {
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked}, // checked
                        new int[]{android.R.attr.state_enabled} // unchecked
                },
                new int[]{
                        ContextCompat.getColor(context, R.color.control_radio_button_selected), // checked
                        ContextCompat.getColor(context, R.color.control_radio_button_default)   // unchecked
                }
        );
    }

    public void deleteItem(String id){
      /*  for (int i = 0; i < rg_main.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) rg_main.getChildAt(i);
            if(radioButton.getId()==Integer.parseInt(id)){
                rg_main.removeViewAt(i);
                break;
            }


        }*/
        for (int i = 0; i <rgItems.size() ; i++) {
            if(rgItems.get(i).getId().contentEquals(id)){
                rgItems.remove(i);
                break;
            }
        }
/*
        for (int i = 0; i <controlObject.getItemsList().size() ; i++) {
            if(controlObject.getItemsList().get(i).getId().contentEquals(id)){
                controlObject.getItemsList().remove(i);
                break;
            }
        }
*/
    }

    public LinearLayout getLl_main_inside() {
        return ll_main_inside;
    }

    private void setControlUISettings(CustomRadioButton radioButton) {
        try {
            if (dataCollectionObject.isUIFormNeeded) {

                if (controlUIProperties != null) {
                    if (radioButton != null && controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.CUSTOM_WIDTH)
                            && controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                        if(controlObject.getDisplayNameAlignment() != null) {
                            if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                                radioButton.setWidth(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomWidthInDP())));
                                radioButton.setHeight(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomHeightInDP())));
                            } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                                radioButton.setWidth(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomWidthInDP())));
                                radioButton.setHeight(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomHeightInDP())));
                            } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("8")) {
                                radioButton.setWidth(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomWidthInDP())));
                                radioButton.setHeight(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomHeightInDP())));
                            } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {
                                LinearLayout.LayoutParams layoutParamsNine = new LinearLayout.LayoutParams(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomWidthInDP())),
                                        improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomHeightInDP())));
                                layoutParamsNine.setMargins(0, 0, 0, pxToDP(5));
                                radioButton.setLayoutParams(layoutParamsNine);
//                                radioButton.setWidth(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomWidthInDP())));
//                                radioButton.setHeight(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomHeightInDP())));
                            }
                        }else {
                            radioButton.setWidth(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomWidthInDP())));
                            radioButton.setHeight(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomHeightInDP())));
                        }
                    }

                    if (controlUIProperties.getFontSize() != null && !controlUIProperties.getFontSize().isEmpty()) {
                        tv_displayName.setTextSize(Float.parseFloat(controlUIProperties.getFontSize()));
                    }
                    if (controlUIProperties.getFontColorHex() != null && !controlUIProperties.getFontColorHex().isEmpty()) {
                        tv_displayName.setTextColor(Color.parseColor(controlUIProperties.getFontColorHex()));
                    }
                    if (controlUIProperties.getFontStyle() != null && !controlUIProperties.getFontStyle().isEmpty()) {
                        String style = controlUIProperties.getFontStyle();
                        if (style != null && style.equalsIgnoreCase("Bold")) {
                            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_bold));
                            tv_displayName.setTypeface(typeface_bold);

                        } else if (style != null && style.equalsIgnoreCase("Italic")) {
                            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_italic));
                            tv_displayName.setTypeface(typeface_italic);
                        }
                    }


                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }
    public ControlObject getControlObject(){
        return controlObject;
    }

    public boolean isAllowOtherTextEmpty(){
        boolean isEmpty =  false;
        if(ce_other.getVisibility() == View.VISIBLE && ce_other.getText().toString().isEmpty() ){
            isEmpty = true;
        }
        return isEmpty;
    }

    public void removeAndAddAllView(){

    }
}