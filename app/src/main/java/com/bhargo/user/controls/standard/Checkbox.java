package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.hideSoftKeyboard;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.showSoftKeyBoard;
import static java.lang.String.CASE_INSENSITIVE_ORDER;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.AssignControl_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.Item;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.SelectVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomCheckBox;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.uisettings.pojos.ControlUIProperties;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Checkbox implements SelectVariables, UIVariables {

    private static final String TAG = "Checkbox";
    private final int CheckboxTAG = 0;
    public LinearLayout cb_main_items, cb_container_for_boolean, ll_design, ll_label, ll_displayName, ll_main_inside;
    Context context;
    CustomEditText ce_other;
    ControlObject controlObject;
    List<String> cbItems;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    ImproveHelper improveHelper;
    private LinearLayout linearLayout;
    private CustomTextView tv_displayName, tv_hint,ct_showText;
    private ImageView iv_mandatory;
    private CustomTextView ct_alertTypeCheckbox;
    private View view;

    DataCollectionObject dataCollectionObject;
    ControlUIProperties controlUIProperties;
    List<Item> controlObjectItemsList;

    public Checkbox(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        improveHelper = new ImproveHelper(context);
        controlObjectItemsList = new ArrayList<>();
        initView();
    }

    public Checkbox(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName, DataCollectionObject dataCollectionObject, ControlUIProperties controlUIProperties) {
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

            cbItems = new ArrayList<>();
            addCheckboxStrip(context);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getCheckbox() {
        return linearLayout;
    }

    public LinearLayout getCheckboxContainer() {
        if (controlObject.getCheckbox_ValueType().equalsIgnoreCase("Boolean")) {
            return linearLayout.getChildAt(0).findViewById(R.id.cb_container_for_boolean);
        } else {
            return linearLayout.getChildAt(0).findViewById(R.id.cb_container);
        }
    }


    public void addCheckboxStrip(final Context context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    view = linflater.inflate(R.layout.layout_check_box_six, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {

                    view = linflater.inflate(R.layout.layout_check_box_seven, null);

                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("8")) {

                    view = linflater.inflate(R.layout.layout_check_box_eight, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {

                    view = linflater.inflate(R.layout.layout_check_box_default, null);
                } else {

                    view = linflater.inflate(R.layout.layout_check_box_default, null);
                }
            } else {
                view = linflater.inflate(R.layout.layout_check_box_default, null);
            }
            view.setTag(CheckboxTAG);
            cb_container_for_boolean = view.findViewById(R.id.cb_container_for_boolean);
            cb_main_items = view.findViewById(R.id.cb_container);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ct_alertTypeCheckbox = view.findViewById(R.id.ct_alertTypeText);

            ce_other = view.findViewById(R.id.ce_otherchoice);

            ll_label = view.findViewById(R.id.ll_label);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_design = view.findViewById(R.id.ll_design);
            ct_showText = view.findViewById(R.id.ct_showText);
//            Item item = new Item();
//            item.setId("1");
//            item.setValue("Bhavani");
//            List<Item> itemList = new ArrayList<>();
//            itemList.add(item);
//            controlObject.setItemsList(itemList);
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
                            controlObject.setText(getSelectedNameCheckboxString());
                            controlObject.setControlValue(getSelectedId());
                        } else {
                            controlObject.setText(null);
                            controlObject.setControlValue(null);
                        }
                }
            });


            linearLayout.addView(view);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addCheckboxStrip", e);
        }
    }

    public void setValueToCheckBoxItem(String value) {
        if (controlObject.getCheckbox_ValueType().equals("Value")) {
            cb_main_items.removeAllViews();
            String[] valuesArr = value.split("\\,");
            if (valuesArr.length > 0) {
                cb_main_items.removeAllViews();
                for (int j = 0; j < valuesArr.length; j++) {
                    setCheckBoxItems(valuesArr[j], valuesArr[j]);
                }
            }
        } else {
            cb_container_for_boolean.removeAllViews();
            if (value != null && !value.trim().equals("")) {
                setCheckBoxBoolean(value);
            } else {
                setCheckBoxBoolean("");
            }
        }
    }

    public void setValueToCheckBoxBoolean(AssignControl_Bean assignControl_bean) {

        ExpressionMainHelper ehelper = new ExpressionMainHelper();
        String checkedVal = "", uncheckedVal = "";
        if (assignControl_bean.isTwoValue_expression1()) {
            checkedVal = ehelper.ExpressionHelper(context, assignControl_bean.getTwoValue_value1());
        } else {
            checkedVal = assignControl_bean.getTwoValue_value1();
        }

        if (assignControl_bean.isTwoValue_expression2()) {
            uncheckedVal = ehelper.ExpressionHelper(context, assignControl_bean.getTwoValue_value2());
        } else {
            uncheckedVal = assignControl_bean.getTwoValue_value2();
        }

        if (checkedVal != null && !checkedVal.trim().equals("")) {
            controlObject.setCheckbox_CheckedValue(checkedVal);
        }

        if (uncheckedVal != null && !uncheckedVal.trim().equals("")) {
            controlObject.setCheckbox_unCheckedValue(uncheckedVal);
        }
        cb_container_for_boolean.removeAllViews();
        setCheckBoxBoolean("");
    }

    public void setValueToCheckBoxBoolean(List<String> lvalues) {

        cb_container_for_boolean.removeAllViews();
        if (lvalues != null && lvalues.size() > 0) {
            setCheckBoxBoolean(lvalues.get(0));
        } else {
            setCheckBoxBoolean("");
        }

    }

    public void setValueToCheckBoxItems(List<String> lvalues) {
        if (controlObject.getCheckbox_ValueType().equals("Value")) {
            cb_main_items.removeAllViews();
            if (lvalues.size() > 0) {
                for (int j = 0; j < lvalues.size(); j++) {
                    setCheckBoxItems(lvalues.get(j), lvalues.get(j));
                }
            }
        }
    }

    public void setValueToCheckBoxItemslistReplace(List<Item> itemList) {
        if (controlObject.getCheckbox_ValueType().equals("Value")) {
            cb_main_items.removeAllViews();
            if (itemList.size() > 0) {
                for (int j = 0; j < itemList.size(); j++) {
                    setCheckBoxItems(itemList.get(j).getId(), itemList.get(j).getValue());
                }
            }
        }
    }

    public void setValueToCheckBoxItemslistAppend(List<Item> itemList) {
        if (controlObject.getCheckbox_ValueType().equals("Value")) {
            if (itemList.size() > 0) {
                for (int j = 0; j < itemList.size(); j++) {
                    setCheckBoxItems(itemList.get(j).getId(), itemList.get(j).getValue());
                }
            }
        }
    }

    private void setControlValues() {
        try {
            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.isHideDisplayName()) { // Hide DisplayName
                ll_displayName.setVisibility(View.GONE);
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
            cb_container_for_boolean.setVisibility(View.GONE);
            if (controlObject.getCheckbox_ValueType().equals("Value")) {
                if (controlObject.getItems() != null && controlObject.getItems().size() > 0) {
                    controlObjectItemsList =  controlObject.getItemsList();
                    if (controlObject.isEnableSortByAlphabeticalOrder()) {
                        cbItems = controlObject.getItems();
                        improveHelper.sortListAlphabetically(controlObjectItemsList,true);
//                        Collections.sort(cbItems, CASE_INSENSITIVE_ORDER);
                       /* Collections.sort(controlObjectItemsList, new Comparator<Item>() {
                            @Override
                            public int compare(Item item, Item t1) {
                                String s1 = item.getValue();
                                String s2 = t1.getValue();
                                return s1.compareToIgnoreCase(s2);
                            }

                        });*/
                    } else {
                        cbItems = controlObject.getItems();
                    }

                    for (int count = 0; count < controlObjectItemsList.size(); count++) {
                        setCheckBoxItems(controlObjectItemsList.get(count).getId(), controlObjectItemsList.get(count).getValue());
                    }
//                    for (int count = 0; count < controlObjectItemsList.size(); count++) {
//                        setCheckBoxItems(controlObjectItemsList.get(count).getId(), controlObjectItemsList.get(count).getValue());
//                    }

                    if (controlObject.isEnableAllowOtherChoice()) {
                        allowOtherChoices();
                    }
                } else {
                    setCheckBoxItems("", "");
                }
            }
            else if (controlObject.getCheckbox_ValueType().equals("Boolean")) {
                cb_container_for_boolean.setVisibility(View.VISIBLE);
                setCheckBoxBoolean("");
            } else {
                setCheckBoxItems("", "");
            }


            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    private void allowOtherChoices() {
        String other_id = "other_" + controlObjectItemsList.size();
        setCheckBoxItems(other_id, context.getString(R.string.other));

        CheckBox checkBox = (CheckBox) cb_main_items.getChildAt(cbItems.size());
//        checkBox.setTag(controlObject.getControlName());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {

                if (isChecked) {
                    ce_other.setVisibility(View.VISIBLE);
                    ce_other.requestFocus();
                    showSoftKeyBoard(context, ce_other);
                    ce_other.setTag(other_id);
                    controlObject.setText(getSelectedNameCheckboxString());
                    controlObject.setControlValue(getSelectedId());
                } else {
                    ce_other.setText("");
                    ce_other.setTag("");
                    controlObject.setText(getSelectedNameCheckboxString());
                    controlObject.setControlValue(getSelectedId());
                    hideSoftKeyboard(context, ce_other);
                    ce_other.setVisibility(View.GONE);
                }
                if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                        if (SubformFlag) {
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName = SubformName;

                        }
                        AppConstants.GlobalObjects.setCurrent_GPS("");
                        ((MainActivity) context).ChangeEvent(view);
                    }
                }
                ct_alertTypeCheckbox.setVisibility(View.GONE);
                ct_alertTypeCheckbox.setText("");
            }
        });
    }

    public void setCheckBoxBoolean(String value) {
        try {
            final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view;
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("8")) {
                    view = lInflater.inflate(R.layout.item_check_box_eight, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {
                    view = lInflater.inflate(R.layout.item_check_box_nine, null);
                } else {
                    view = lInflater.inflate(R.layout.item_check_box_default, null);
                }
            } else {
                view = lInflater.inflate(R.layout.item_check_box_default, null);
            }

            CustomCheckBox checkBox = view.findViewById(R.id.checkbox);
            cb_container_for_boolean.addView(view);

            if (controlObject.getCheckbox_CheckedValue().equalsIgnoreCase(value)) {
                checkBox.setChecked(true);
            }

            if (controlObject.getCheckbox_unCheckedValue().equalsIgnoreCase(value)) {
                checkBox.setChecked(false);
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton view, boolean b) {
                    controlObject.setText(getSelectedNameCheckboxString());
                    controlObject.setControlValue(getSelectedId());
//                    if (checkBox.getButtonTintList() != null) {
                    setCheckBoxTheme(cb_container_for_boolean);
//                    }
                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;

                            }
                            AppConstants.GlobalObjects.setCurrent_GPS("");
                            ((MainActivity) context).ChangeEvent(view);
                        }
                    }
                    ct_alertTypeCheckbox.setVisibility(View.GONE);
                    ct_alertTypeCheckbox.setText("");


                }
            });

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setCheckBoxItems", e);
        }
    }

    public void setCheckBoxItems(String id, String text) {
        try {
            final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View iView;
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("8")) {
                    iView = lInflater.inflate(R.layout.item_check_box_eight, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {
                    iView = lInflater.inflate(R.layout.item_check_box_nine, null);
                } else {
                    iView = lInflater.inflate(R.layout.item_check_box_default, null);
                }
            } else {
                iView = lInflater.inflate(R.layout.item_check_box_default, null);
            }

            CustomCheckBox checkBox = iView.findViewById(R.id.checkbox);
            checkBox.setText(text);
            checkBox.setTag(id);
            if (controlObject.getDisplayNameAlignment() != null) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("8")) {
                    LinearLayout linearLayout = iView.findViewById(R.id.cb_item_layout);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 10, 10, 10);
                    linearLayout.setLayoutParams(params);
                } else if (
                        controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {
                    LinearLayout linearLayout = iView.findViewById(R.id.cb_item_layout);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 10, 10, 10);
                    linearLayout.setLayoutParams(params);
                }
            }
            cb_main_items.addView(iView);

            if (controlObject.getDefaultItem() != null) {
                if (!controlObject.getDefaultItem().equalsIgnoreCase("") && controlObject.getDefaultItem().equalsIgnoreCase(text)) {
                    checkBox.setChecked(true);
                    controlObject.setText(getSelectedNameCheckboxString());
                    controlObject.setControlValue(getSelectedId());

                }
            }

            if (controlObject.getDefaultValue() != null) {

                if (!controlObject.getDefaultValue().equalsIgnoreCase("") && controlObject.getDefaultValue().contains(text)) {
                    checkBox.setChecked(true);
                    controlObject.setText(getSelectedNameCheckboxString());
                    controlObject.setControlValue(getSelectedId());

                }
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton view, boolean isChecked) {

                        controlObject.setText(getSelectedNameCheckboxString());
                        controlObject.setControlValue(getSelectedId());

                    if (checkBox.getButtonTintList() != null) {
                        setCheckBoxTheme(cb_main_items);
                    }

                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;

                            }
                            AppConstants.GlobalObjects.setCurrent_GPS("");
                            ((MainActivity) context).ChangeEvent(iView);
                        }
                    }
                    ct_alertTypeCheckbox.setVisibility(View.GONE);
                    ct_alertTypeCheckbox.setText("");


                }
            });

            setControlUISettings(checkBox);
            if (controlObject.isDisable()) {
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisable(), cb_main_items, iView);
            }
//            if(ce_other != null) {
//                ce_other.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//                        if (ce_other.getText() != null && ce_other.getText().toString().trim().length() > 0) {
//                            controlObject.setText("" + editable);
//                        }
//                        controlObject.setControlValue(getSelectedId());
//
//                    }
//                });
//            }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setCheckBoxItems", e);
        }
    }

    private void setControlUISettings(CustomCheckBox checkBox) {
        try {
            if (dataCollectionObject.isUIFormNeeded) {

                if (controlUIProperties != null) {
                    if (checkBox != null && controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.CUSTOM_WIDTH)
                            && controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                        if (controlObject.getDisplayNameAlignment() != null) {
                            if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                                checkBox.setWidth(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomWidthInDP())));
                                checkBox.setHeight(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomHeightInDP())));
                            } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                                checkBox.setWidth(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomWidthInDP())));
                                checkBox.setHeight(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomHeightInDP())));
                            } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("8")) {
                                checkBox.setWidth(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomWidthInDP())));
                                checkBox.setHeight(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomHeightInDP())));
                            } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {
                                checkBox.setWidth(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomWidthInDP())));
                                checkBox.setHeight(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomHeightInDP())));
                            }
                        } else {
                            checkBox.setWidth(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomWidthInDP())));
                            checkBox.setHeight(improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomHeightInDP())));
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

    public int pxToDP(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
    }

    public boolean isCheckBoxListEmpty() {
        boolean isChecked = true;
        try {
            if (controlObject.getCheckbox_ValueType().equalsIgnoreCase("Value")) {
                int cbList = cb_main_items.getChildCount();
                for (int i = 0; i < cbList; i++) {

                    CheckBox checkBox = (CheckBox) cb_main_items.getChildAt(i);
                    if (checkBox.isChecked()) {
                        isChecked = false;

                        if (controlObject.isEnableAllowOtherChoice()) {
                            isChecked = isCheckBoxOthersEmptySelected();
                        }
                        break;
                    }
                }
            } else if (controlObject.getCheckbox_ValueType().equalsIgnoreCase("Boolean")) {
                int cbList = cb_container_for_boolean.getChildCount();
                for (int i = 0; i < cbList; i++) {

                    CheckBox checkBox = (CheckBox) cb_container_for_boolean.getChildAt(i);
                    if (checkBox.isChecked()) {
                        isChecked = false;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "isCheckBoxListEmpty", e);
        }
        return isChecked;


    }

    public boolean isCheckBoxOthersEmptySelected() {
        boolean isOtherChecked = false;
        try {
                int cbList = cb_main_items.getChildCount();
                CheckBox cbOther = (CheckBox) cb_main_items.getChildAt(cbList - 1);
                if (cbOther.isChecked()) {
                    isOtherChecked = true;
                }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "isCheckBoxOthersEmptySelected", e);
        }
        return isOtherChecked;
    }


    public CustomTextView setAlertCheckbox() {

        return ct_alertTypeCheckbox;
    }

    public List<String> getSelectedNameCheckbox() {
        List<String> checkedNamesList = new ArrayList<>();
        try {
            int cbList = cb_main_items.getChildCount();
            for (int i = 0; i < cbList; i++) {
                CheckBox checkBox = (CheckBox) cb_main_items.getChildAt(i);
                if (checkBox.isChecked()) {
                    if (checkBox.getText().toString().equalsIgnoreCase(context.getString(R.string.other))) {
                        checkedNamesList.add(ce_other.getText().toString());
                        ce_other.setTag("other_" + cbList);
                    } else {
                        checkedNamesList.add(checkBox.getText().toString());
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getSelectedNameCheckbox", e);
        }
        return checkedNamesList;
    }

    /*public String getSelectedCheckBoxString() {
        String value = "";
        for (int i = 0; i < cb_main_items.getChildCount(); i++) {
            CheckBox cb_item = (CheckBox) cb_main_items.getChildAt(i);
            if (cb_item.isChecked()) {
                if (cb_item.getText().toString().trim().equalsIgnoreCase("Other")) {
                    value = value + "," + ce_other.getText().toString().trim();
                } else {
                    value = value + "," + cb_item.getText().toString().trim();
                }
            }
        }
        if (value.equals("")) {
            return value;
        } else {
            return value.substring(1);
        }
    }*/


    public String getSelectedNameCheckboxString() {
        String checkedNamesList = "";
        try {
            if (controlObject.getCheckbox_ValueType().equalsIgnoreCase("Value")) {
                int cbList = cb_main_items.getChildCount();
                for (int i = 0; i < cbList; i++) {
                    CheckBox checkBox = null;
                    if (controlObject.getDisplayNameAlignment() != null && (controlObject.getDisplayNameAlignment().equalsIgnoreCase("8") ||
                            controlObject.getDisplayNameAlignment().equalsIgnoreCase("9"))) {
                        checkBox = (CheckBox) ((LinearLayout) cb_main_items.getChildAt(i)).getChildAt(0);
                    } else {
                        checkBox = (CheckBox) cb_main_items.getChildAt(i);
                    }
                    if (checkBox.isChecked()) {
                        if (checkBox.getText().toString().equalsIgnoreCase(context.getString(R.string.other))) {
                            checkedNamesList += ce_other.getText().toString() + "^";
                        } else {
                            checkedNamesList += checkBox.getText().toString() + "^";
                        }
                    }
                }
                if (checkedNamesList != null && checkedNamesList.length() > 0 && checkedNamesList.charAt(checkedNamesList.length() - 1) == '^') {
                    checkedNamesList = checkedNamesList.substring(0, checkedNamesList.length() - 1);
                }
            } else if (controlObject.getCheckbox_ValueType().equalsIgnoreCase("Boolean")) {

                int cbList = cb_container_for_boolean.getChildCount();
                for (int i = 0; i < cbList; i++) {
                    CheckBox checkBox = (CheckBox) cb_container_for_boolean.getChildAt(i);
                    if (checkBox.isChecked()) {
                        checkedNamesList += controlObject.getCheckbox_CheckedValue() + "^";
                    } else {
                        checkedNamesList += controlObject.getCheckbox_unCheckedValue() + "^";
                    }
                }
                if (checkedNamesList != null && checkedNamesList.length() > 0 && checkedNamesList.charAt(checkedNamesList.length() - 1) == '^') {
                    checkedNamesList = checkedNamesList.substring(0, checkedNamesList.length() - 1);
                }
            }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getSelectedNameCheckboxString", e);
        }
        return checkedNamesList;
    }

    public String getSelectedId1() {
        String strSelectedId = null;
        try {
            LinearLayout cb_main = linearLayout.getChildAt(0).findViewById(R.id.cb_container);
            View cbview = cb_main.getChildAt(0);
            CheckBox checkBox = cbview.findViewById(R.id.checkbox);
            strSelectedId = (String) checkBox.getTag();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getSelectedId", e);
        }
        return strSelectedId;
    }

    public String getSelectedId() {
        String checkedIdsList = "";
        try {
            if (controlObject.getCheckbox_ValueType().equalsIgnoreCase("Value")) {
                int cbList = cb_main_items.getChildCount();
                for (int i = 0; i < cbList; i++) {
                    CheckBox checkBox = null;
                    if (controlObject.getDisplayNameAlignment() != null && (controlObject.getDisplayNameAlignment().equalsIgnoreCase("8") ||
                            controlObject.getDisplayNameAlignment().equalsIgnoreCase("9"))) {
                        checkBox = (CheckBox) ((LinearLayout) cb_main_items.getChildAt(i)).getChildAt(0);
                    } else {
                        checkBox = (CheckBox) cb_main_items.getChildAt(i);
                    }
                    if (checkBox.isChecked()) {
                        if (checkBox.getText().toString().equalsIgnoreCase(context.getString(R.string.other))) {
                            checkedIdsList += ce_other.getTag().toString() + "^";
                        } else {
                            checkedIdsList += checkBox.getTag().toString() + "^";
                        }
                    }
                }
                if (checkedIdsList != null && checkedIdsList.length() > 0 && checkedIdsList.charAt(checkedIdsList.length() - 1) == '^') {
                    checkedIdsList = checkedIdsList.substring(0, checkedIdsList.length() - 1);
                }
            } else if (controlObject.getCheckbox_ValueType().equalsIgnoreCase("Boolean")) {
                int cbList = cb_container_for_boolean.getChildCount();
                for (int i = 0; i < cbList; i++) {
                    CheckBox checkBox = (CheckBox) cb_container_for_boolean.getChildAt(i);
                    if (checkBox.isChecked()) {
                        checkedIdsList += "true" + "^";
                    } else {
                        checkedIdsList += "false" + "^";
                    }
                }
                if (checkedIdsList != null && checkedIdsList.length() > 0 && checkedIdsList.charAt(checkedIdsList.length() - 1) == '^') {
                    checkedIdsList = checkedIdsList.substring(0, checkedIdsList.length() - 1);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getSelectedNameCheckboxString", e);
        }
        return checkedIdsList;
    }


    public void setChecked(boolean bool) {
        try {
            LinearLayout cb_main = linearLayout.getChildAt(0).findViewById(R.id.cb_container);
            View cbview = cb_main.getChildAt(0);
            CheckBox checkBox = cbview.findViewById(R.id.checkbox);
            checkBox.setChecked(bool);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setChecked", e);
        }
    }

    public void check(String value) {
        try {
            if (controlObject.getCheckbox_ValueType().equalsIgnoreCase("Value")) {
                if (cb_main_items != null) {
                    for (int i = 0; i < cb_main_items.getChildCount(); i++) {
                        CheckBox checkBox = (CheckBox) cb_main_items.getChildAt(i);
                        String itemId = (String) checkBox.getTag();
                        if (value.contentEquals(checkBox.getText().toString())) {
                            checkBox.setChecked(true);
                            controlObject.setSelectedItem(new Item(itemId, checkBox.getText().toString()));
                            break;
                        }
                    }
                }
            } else if (controlObject.getCheckbox_ValueType().equalsIgnoreCase("Boolean")) {
                if (cb_container_for_boolean != null) {
                    for (int i = 0; i < cb_container_for_boolean.getChildCount(); i++) {
                        CheckBox checkBox = (CheckBox) cb_container_for_boolean.getChildAt(i);
                        if (value.contentEquals(controlObject.getCheckbox_CheckedValue())) {
                            checkBox.setChecked(true);
                        } else if (value.contentEquals(controlObject.getCheckbox_unCheckedValue())) {
                            checkBox.setChecked(false);
                        }
                    }
                }
            }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "check", e);
        }
    }


    @Override
    public Item getSelectedItem() {
        return null;
    }

    @Override
    public void setSelectedItem(Item selectedItem) {
        check(selectedItem.getId());

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
//        setViewDisable(view, !enabled);
        controlObject.setDisable(!enabled);
//        setViewDisableOrEnableDefault(context, view, enabled);
//        improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), enabled, cb_main_items, view);
        for (int i = 0; i < cb_main_items.getChildCount(); i++) {
            View view = cb_main_items.getChildAt(i);
            improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), enabled, cb_main_items, view);
        }


    }

    @Override
    public void clean() {

    }

    @Override
    public void loadItems(List<Item> items) {
        controlObject.setItemsList(items);

        for (int i = 0; i < items.size(); i++) {

            Item item = items.get(i);

            setCheckBoxItems(item.getId(), item.getValue());

        }
    }

    @Override
    public void appendItems(List<Item> items) {

        if (controlObjectItemsList != null) {
            controlObjectItemsList.addAll(items);
        }

    }

    @Override
    public boolean isSortByAlphabetOrder() {
        return controlObject.isEnableSortByAlphabeticalOrder();
    }

    @Override
    public void setSortByAlphabetOrder(boolean enabled) {
        controlObject.setEnableSortByAlphabeticalOrder(enabled);
        if (enabled) {
//            improveHelper.sortListAlphabetically(controlObjectItemsList,true);
//            allowOtherChoices();
            cbItems = controlObject.getItems();
//            controlObjectItemsList =  new ArrayList<>();
            controlObjectItemsList =  controlObject.getItemsList();
            improveHelper.sortListAlphabetically(controlObjectItemsList,true);

            if (controlObjectItemsList != null && controlObjectItemsList.size() > 0) {
                cb_main_items.removeAllViews();
                for (int count = 0; count < controlObjectItemsList.size(); count++) {
                    setCheckBoxItems(controlObjectItemsList.get(count).getId(), controlObjectItemsList.get(count).getValue());
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
        if(enabled) {
//            cb_main_items.removeViewAt(cbItems.size());
            if (controlObjectItemsList != null && controlObjectItemsList.size() > 0) {
                cb_main_items.removeAllViews();
                for (int j = 0; j < controlObjectItemsList.size(); j++) {
                    setCheckBoxItems(controlObjectItemsList.get(j).getId(), controlObjectItemsList.get(j).getValue());
                }
                allowOtherChoices();
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

    public void setCheckedValue(String CheckedValue) {
        controlObject.setCheckbox_CheckedValue(CheckedValue);
    }

    public void setUnCheckedValue(String unCheckedValue) {
        controlObject.setCheckbox_unCheckedValue(unCheckedValue);
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
    /*hideDisplayName,enableSortByAlphabeticalOrder,enableAllowOtherChoice
    invisible/visibility
     */

    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        ll_displayName.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
    }

    public void setAlertCheckBoxError(boolean fromValidation) {
        for (int i = 0; i < cb_main_items.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) cb_main_items.getChildAt(i);
            if (checkBox.isChecked()) {
                checkBox.setButtonTintList(getCheckBoxColors());
            } else {
                if (fromValidation) {
                    checkBox.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.control_error_color)));

                } else {
                    checkBox.setButtonTintList(getCheckBoxColors());
                }
            }
        }
    }

    public void setCheckBoxTheme(LinearLayout cb_main) {
        for (int i = 0; i < cb_main.getChildCount(); i++) {
            CustomCheckBox checkBox = (CustomCheckBox) cb_main.getChildAt(i);
            checkBox.setButtonTintList(getCheckBoxColors());

        }
    }

    private ColorStateList getCheckBoxColors() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.bhargo_color_one, typedValue, true);
        @ColorInt int color = typedValue.data;

        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked}, // checked
                        new int[]{android.R.attr.state_enabled} // unchecked
                },
               /* new int[]{
                        ContextCompat.getColor(context, R.color.control_radio_button_selected), // checked
                        ContextCompat.getColor(context, R.color.control_radio_button_default)   // unchecked
                } */
                new int[]{
                        color, // checked
                        ContextCompat.getColor(context, R.color.control_radio_button_default)   // unchecked
                }
        );
    }

    public void setCheckBoxValues(HashMap<String, String> controlValue) {
        String id = controlValue.get("Value_id");
        String value = controlValue.get("Value");
        if (controlObject.getCheckbox_ValueType().equalsIgnoreCase("Value")) {
            String other_id = "other_" + controlObjectItemsList.size();
            int checkboxListSize = getCheckboxContainer().getChildCount();
            for (int i = 0; i < checkboxListSize; i++) {
                View cbview = getCheckboxContainer().getChildAt(i);
                CustomCheckBox cb_main = cbview.findViewById(R.id.checkbox);
                setControlUISettings(cb_main);
                String cb_id = cb_main.getTag().toString().trim();
                if (cb_id.equalsIgnoreCase(other_id)) {
                    ce_other.setTag(other_id);
                    cb_main.setChecked(true);
                    ce_other.setText(value);
                } else {
                    if (id.contains("^")) {
                        String[] valuesC = id.split("\\^");
                        if (Arrays.asList(valuesC).contains(cb_id)) {
                            cb_main.setChecked(true);
                        }
                    } else if (id.equals(cb_id)) {
                        cb_main.setChecked(true);
                    }
                }
            }
        } else if (controlObject.getCheckbox_ValueType().equalsIgnoreCase("Boolean")) {
            int checkboxListSize = getCheckboxContainer().getChildCount();
            for (int i = 0; i < checkboxListSize; i++) {
                View cbview = getCheckboxContainer().getChildAt(i);
                CheckBox cb_main = cbview.findViewById(R.id.checkbox);

                if (value.equals(controlObject.getCheckbox_CheckedValue())) {
                    cb_main.setChecked(true);
                }
                if (value.equals(controlObject.getCheckbox_unCheckedValue())) {
                    cb_main.setChecked(false);
                }

            }
        }
    }

    public LinearLayout getLl_main_inside() {
        return ll_main_inside;
    }

    public ControlObject getControlObject() {
        return controlObject;
    }

    public void clear() {
        controlObject.setText(null);
        controlObject.setControlValue(null);
        for (int i = 0; i < cb_main_items.getChildCount(); i++) {
            CheckBox cb_main = (CheckBox) cb_main_items.getChildAt(i);
            cb_main.setChecked(false);
        }

    }


    public boolean isAllowOtherTextEmpty(){
        boolean isEmpty =  false;
        if(ce_other.getVisibility() == View.VISIBLE && ce_other.getText().toString().isEmpty() ){
            isEmpty = true;
        }
        return isEmpty;
    }


}


