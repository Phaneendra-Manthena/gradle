package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.Item;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.SelectedListVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nk.mobileapps.spinnerlib.SearchableMultiSpinner;
import nk.mobileapps.spinnerlib.SpinnerData;

public class CheckList implements SelectedListVariables, UIVariables {

    private static final String TAG = "CheckList";
    private final int CheckListTAG = 0;
    public CustomTextView ct_alertTypeCheckList;
    Context context;
    ControlObject controlObject;
    SearchableMultiSpinner multiSearchableSpinner;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    View view;
    ImproveHelper improveHelper;
    private LinearLayout linearLayout, ll_label,ll_displayName,ll_main_inside, ll_tap_text,ll_control_ui,layout_control,ll_leftRightWeight;;
    private CustomTextView tv_displayName, tv_hint,ct_showText;
    private ImageView iv_mandatory;
    List<Item> CLItems;

    public CheckList(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        improveHelper = new ImproveHelper(context);
        initView();
    }

    public ControlObject getControlObject() {
        return controlObject;
    }

    public void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            CLItems = new ArrayList<>();
            addCheckListStrip(context);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getCheckList() {

        return linearLayout;
    }

    public void addCheckListStrip(final Context context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View view = lInflater.inflate(R.layout.control_check_list, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

                    view = linflater.inflate(R.layout.layout_check_list_default, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

                    view = linflater.inflate(R.layout.layout_check_list_rounded_rectangle, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {

                    view = linflater.inflate(R.layout.layout_check_list_left_right, null);
                }else {

//                view = linflater.inflate(R.layout.control_check_list, null);
                    view = linflater.inflate(R.layout.layout_check_list_default, null);
                }
            } else {

//                view = linflater.inflate(R.layout.control_check_list, null);
                view = linflater.inflate(R.layout.layout_check_list_default, null);
            }
            view.setTag(CheckListTAG);

            multiSearchableSpinner = view.findViewById(R.id.multiSearchableSpinner);
            multiSearchableSpinner.setTag(controlObject.getControlName());
            tv_displayName = view.findViewById(R.id.tv_displayName);
            ct_alertTypeCheckList = view.findViewById(R.id.ct_alertTypeText);
            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            layout_control = view.findViewById(R.id.layout_control);
            ll_leftRightWeight = view.findViewById(R.id.ll_leftRightWeight);
            ll_label = view.findViewById(R.id.ll_label);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ct_showText = view.findViewById(R.id.ct_showText);

//        multiSearchableSpinner.setItems(loadSpinnerData(), new SearchableMultiSpinner.SpinnerListener() {
//            @Override
//            public void onItemsSelected(View v, List<SpinnerData> items, List<SpinnerData> selectedItems) {
//                if (multiSearchableSpinner.getSelectedIds() != null) {
//
//                }
//            }
//        });
//        multiSearchableSpinner.performClick();


            setControlValues();
            linearLayout.addView(view);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addCheckListStrip", e);
        }
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
            } else {
                ll_displayName.setVisibility(View.VISIBLE);
            }

                if (controlObject.isDisable()) {
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisable(), ll_tap_text, view);
            }

            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
            setItems();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    /*private void setItems1() {
        try {
            if (controlObject.getItemsList() != null && controlObject.getItemsList().size() > 0) {
                List<SpinnerData> spinnerDataList = loadSpinnerData(controlObject.getItemsList());
                if (controlObject.getDefaultItem() != null && !controlObject.getDefaultItem().contentEquals("")) {
                    String[] defaultItem = new String[1];

                    for (SpinnerData spinnerData : spinnerDataList) {

                        if (spinnerData.getId().equalsIgnoreCase(controlObject.getDefaultItem())) {
                            defaultItem[0] = spinnerData.getId();
                            multiSearchableSpinner.setItemIDs(defaultItem);
                        }
                    }

                    multiSearchableSpinner.setItemIDs(defaultItem);
                }

                if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().isEmpty() && !controlObject.getDefaultValue().contentEquals("")) {
                    if (controlObject.getDefaultValue().contains("^")) {
                        ArrayList<String> mylist = new ArrayList<String>();
                        for (SpinnerData spinnerData : spinnerDataList) {
                            if (controlObject.getDefaultValue().contains(spinnerData.getName())) {
                                mylist.add(spinnerData.getId());
                            }
                        }
                        String[] stringArray = mylist.toArray(new String[mylist.size()]);
                        multiSearchableSpinner.setItemIDs(stringArray);
                    } else {
                        for (SpinnerData spinnerData : spinnerDataList) {

                            if (spinnerData.getName().equalsIgnoreCase(controlObject.getDefaultValue())) {
                                String[] defaultItem = new String[1];
                                defaultItem[0] = "" + spinnerData.getId();

                                multiSearchableSpinner.setItemIDs(defaultItem);
                            }
                        }
                    }
                }

                multiSearchableSpinner.setItems(spinnerDataList, new SearchableMultiSpinner.SpinnerListener() {


                    @Override
                    public void onItemsSelected(View v, List<SpinnerData> items, List<SpinnerData> selectedItems) {
                        ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.control_active_background));
                        if(multiSearchableSpinner.isSelected()) {
                            controlObject.setItemSelected(multiSearchableSpinner.isSelected());
                            controlObject.setText(getSelectedNameCheckListString());
                            controlObject.setControlValue(getSelectedIDCheckListString());
                        }
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                if (SubformFlag) {
                                    AppConstants.SF_Container_position = SubformPos;
                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                }
                                AppConstants.GlobalObjects.setCurrent_GPS("");
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }

                        if (multiSearchableSpinner.getSelectedIds() != null) {
                            ct_alertTypeCheckList.setVisibility(View.GONE);
                            ct_alertTypeCheckList.setText("");
                        }
                    }
                });
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setItems", e);
        }
    }*/
    private void setItems() {
        try {

            if (controlObject.getItemsList() != null && controlObject.getItemsList().size() > 0) {
                List<SpinnerData> spinnerDataList = new ArrayList<>();
                spinnerDataList = loadSpinnerData(controlObject.getItemsList());

                multiSearchableSpinner.setItems(spinnerDataList, new SearchableMultiSpinner.SpinnerListener() {


                    @Override
                    public void onItemsSelected(View v, List<SpinnerData> items, List<SpinnerData> selectedItems) {
//                        ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_active_background));
                        if(multiSearchableSpinner.isSelected()) {
                            controlObject.setItemSelected(multiSearchableSpinner.isSelected());
                            controlObject.setText(getSelectedNameCheckListString());
                            controlObject.setControlValue(getSelectedIDCheckListString());
                        }

                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                if (SubformFlag) {
                                    AppConstants.SF_Container_position = SubformPos;
                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                }
                                AppConstants.GlobalObjects.setCurrent_GPS("");
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }
                        if (multiSearchableSpinner.getSelectedIds() != null) {
                            ct_alertTypeCheckList.setVisibility(View.GONE);
                            ct_alertTypeCheckList.setText("");
                        }
                    }
                });

                if (controlObject.getDefaultItem() != null && !controlObject.getDefaultItem().contentEquals("")) {
                    String[] defaultItem = new String[1];

                    for (SpinnerData spinnerData : spinnerDataList) {

                        if (spinnerData.getId().equalsIgnoreCase(controlObject.getDefaultItem())) {
                            defaultItem[0] = spinnerData.getId();
                            multiSearchableSpinner.setItemIDs(defaultItem);
                        }
                    }

                    multiSearchableSpinner.setItemIDs(defaultItem);
                }
                if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().isEmpty() && !controlObject.getDefaultValue().contentEquals("")) {
                    if (controlObject.getDefaultValue().contains("^")) {
                        ArrayList<String> mylist = new ArrayList<String>();
                        for (SpinnerData spinnerData : spinnerDataList) {
                            if (controlObject.getDefaultValue().contains(spinnerData.getName())) {
                                mylist.add(spinnerData.getId());
                            }
                        }
                        String[] stringArray = mylist.toArray(new String[mylist.size()]);
                        multiSearchableSpinner.setItemIDs(stringArray);
                        controlObject.setItemSelected(true);
                        controlObject.setText(getSelectedNameCheckListString());
                        controlObject.setControlValue(getSelectedIDCheckListString());

                    } else {
                        for (SpinnerData spinnerData : spinnerDataList) {

                            if (spinnerData.getName().equalsIgnoreCase(controlObject.getDefaultValue())) {
                                String[] defaultItem = new String[1];
                                defaultItem[0] = "" + spinnerData.getId();

                                multiSearchableSpinner.setItemIDs(defaultItem);
                            }
                        }
                        controlObject.setItemSelected(true);
                        controlObject.setText(getSelectedNameCheckListString());
                        controlObject.setControlValue(getSelectedIDCheckListString());

                    }
                }


            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setItems", e);
        }
    }

    private List<SpinnerData> loadSpinnerData(List<Item> items) {

        List<SpinnerData> spinnerDataList = new ArrayList<>();
        if(CLItems ==  null){
            CLItems = new ArrayList<>();
        }

        try {
            if (controlObject.isEnableSortByAlphabeticalOrder() && controlObject.isEnableSortByAscendingOrder()) {
                CLItems = items;
                improveHelper.sortListAlphabetically(CLItems,true);
//            Collections.sort(CLItems, String.CASE_INSENSITIVE_ORDER);
            }else if (controlObject.isEnableSortByAlphabeticalOrder()) {
                CLItems = items;
                improveHelper.sortListAlphabetically(CLItems,true);
//            Collections.sort(CLItems, String.CASE_INSENSITIVE_ORDER);
            }else if(controlObject.isEnableSortByAscendingOrder()){
                CLItems =  items;
                improveHelper.sortListAlphabetically(CLItems,true);
            }else if(controlObject.isEnableSortByDescendingOrder()){

                improveHelper.sortListAlphabetically(CLItems,false);
            } else {
                CLItems = items;
            }


            for (int count = 0; count < CLItems.size(); count++) {
                SpinnerData spinnerData = new SpinnerData();
                spinnerData.setId(CLItems.get(count).getId());
                spinnerData.setName(CLItems.get(count).getValue());
                spinnerDataList.add(spinnerData);
            }

            if (controlObject.isEnableAllowOtherChoice()) {
                SpinnerData spinnerData = new SpinnerData();
                String other_id = "other_1411";
                spinnerData.setId(other_id);
                spinnerData.setName(context.getString(R.string.other));
                spinnerDataList.add(spinnerData);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "loadSpinnerData", e);
        }
        return spinnerDataList;
    }



    public void setnewItemsDynamically(List<String> newItems) {
//        multiSearchableSpinner.setItems(loadSpinnerData(newItems));
    }

    public void setnewItemsListDynamically(List<Item> newItems) {
        try {
            multiSearchableSpinner.setItems(loadSpinnerData(newItems));

            //For Edit View
            if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().equals("")) {
                String[] ids = controlObject.getDefaultValue().split("\\^");
                addNewItemsListDynamically(ids);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setnewItemsListDynamically", e);
        }
    }

    public void Clear() {
        multiSearchableSpinner.clearSelections();
        controlObject.setText(null);
        controlObject.setControlValue(null);
    }


    public boolean checkListItemIsSelected() {

        return multiSearchableSpinner.isSelected();
    }

    public CustomTextView setAlertCheckList() {

        return ct_alertTypeCheckList;
    }


    public List<String> getChekedItemsNamesList() {


        return multiSearchableSpinner.getSelectedNames();

    }


    public String getSelectedNameCheckListString() {
        String checkedNamesList = "";
        try {
            int cbList = multiSearchableSpinner.getSelectedNames().size();
            for (int i = 0; i < cbList; i++) {
                checkedNamesList += multiSearchableSpinner.getSelectedNames().get(i) + "^";
            }
            if (checkedNamesList != null && checkedNamesList.length() > 0 && checkedNamesList.charAt(checkedNamesList.length() - 1) == '^') {
                checkedNamesList = checkedNamesList.substring(0, checkedNamesList.length() - 1);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getSelectedNameCheckListString", e);
        }
        return checkedNamesList;
    }

    public String getSelectedCheckListString() {
        String value = "";

        List<String> Items = multiSearchableSpinner.getSelectedNames();
        List<String> ItemIds = multiSearchableSpinner.getSelectedIds();
        for (int i = 0; i < Items.size(); i++) {
            value = value + "$" + Items.get(i) + "," + ItemIds.get(i);
        }
        if (!value.equals("")) {
            value = value.substring(1);
        }
        return value;
    }
    public void setCheckListSelectedItemIDs(String selectedInPopUpCheckList) {
        String[] sId = new String[]{};
        sId = selectedInPopUpCheckList.split("\\^");
        multiSearchableSpinner.setItemIDs(sId);
    }



    public String getSelectedIDCheckListString() {
        String checkedNamesList = "";
        try {
            int cbList = multiSearchableSpinner.getSelectedNames().size();
            for (int i = 0; i < cbList; i++) {
                checkedNamesList += multiSearchableSpinner.getSelectedIds().get(i) + "^";
            }
            if (checkedNamesList != null && checkedNamesList.length() > 0 && checkedNamesList.charAt(checkedNamesList.length() - 1) == '^') {
                checkedNamesList = checkedNamesList.substring(0, checkedNamesList.length() - 1);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getSelectedIDCheckListString", e);
        }
        return checkedNamesList;
    }

    public void setNewItemsListDynamically(List<Item> itemList) {
        multiSearchableSpinner.setItems(loadSpinnerData(itemList));
    }

    public void addNewItemsListDynamically(List<Item> itemList) {
//        multiSearchableSpinner.AddItems(loadSpinnerData(itemList));
    }

    public void addNewItemsListDynamically(String[] itemList) {
//        multiSearchableSpinner.AddItems(loadSpinnerData(itemList));
        if (multiSearchableSpinner.getAllItems() != null && multiSearchableSpinner.getAllItems().size() > 0 && !multiSearchableSpinner.getAllItems().get(0).getId().equalsIgnoreCase("")) {
            multiSearchableSpinner.setItemIDs(itemList);
            controlObject.setItemSelected(true);
            controlObject.setText(getSelectedNameCheckListString());
            controlObject.setControlValue(getSelectedIDCheckListString());
        }
    }


    @Override
    public List<Item> getListOfSelectedItems() {
        List<SpinnerData> spinnerDataList = multiSearchableSpinner.getAllItems();
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < spinnerDataList.size(); i++) {

            Item item = new Item();
            item.setId(spinnerDataList.get(i).getId());
            item.setValue(spinnerDataList.get(i).getName());
            itemList.add(item);

        }
        return itemList;
    }

    @Override
    public void setListOfSelectedItems(List<Item> selectedItems) {
        if (multiSearchableSpinner.getAllItems() != null && multiSearchableSpinner.getAllItems().size() > 0 && !multiSearchableSpinner.getAllItems().get(0).getId().equalsIgnoreCase("")) {
            String[] ids = new String[selectedItems.size()];
            for (int i = 0; i < selectedItems.size(); i++) {
                ids[i] = selectedItems.get(i).getId();
                if (controlObject.isEnableAllowOtherChoice()) {
                    for (int j = 0; j < multiSearchableSpinner.getAllItems().size(); j++) {
                        if (multiSearchableSpinner.getAllItems().get(j).getId().equals(ids[i])) {
                            multiSearchableSpinner.getAllItems().get(j).setName(selectedItems.get(i).getValue());
                            multiSearchableSpinner.setOtherEnable(true);
                            break;
                        }
                    }
                }
            }
            multiSearchableSpinner.setItemIDs(ids);
        }
    }


    @Override
    public void loadItems(List<Item> items) {
        Log.d("itemsCheckLIst","items"+items.get(0).getValue());
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
        controlObject.setDisable(!enabled);
        // multiSearchableSpinner.setEnabled(enabled);
//        setViewDisable(view, !enabled);
//        setViewDisableOrEnableDefault(context,view, enabled);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), enabled, ll_tap_text, view);
    }

    @Override
    public void clean() {

    }

    @Override
    public boolean isSortByAlphabetOrder() {
        return controlObject.isEnableSortByAlphabeticalOrder();
    }

    @Override
    public void setSortByAlphabetOrder(boolean enabled) {
        controlObject.setEnableSortByAlphabeticalOrder(enabled);
        if(enabled){
            setItems();
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
            setItems();
        }
    }

    public void setRowSelectionType(String value) {
        controlObject.setRowSelectionType(value);
        try {
           /* JSONObject checkListJsonObject = new JSONObject();
            checkListJsonObject.put("ColumnName", controlObject.getControlName());
            checkListJsonObject.put("InsertType", controlObject.getRowSelectionType());
            AppConstants.checkListData.put(checkListJsonObject);*/
            for (int i = 0; i < AppConstants.checkListData.length(); i++) {
               AppConstants.checkListData.getJSONObject(i).put("InsertType", controlObject.getRowSelectionType());
            }
        } catch (Exception e) {
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
    /*hideDisplayName,enableSortByAlphabeticalOrder,
    enableSortByAscendingOrder,enableSortByDescendingOrder, enableAllowOtherChoice,
    invisible/visibility
     */

    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        ll_displayName.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
        if (hideDisplayName) {
            tv_hint.setVisibility(View.GONE);
        }
    }

    public boolean isEnableSortByAlphabeticalOrder() {
        return controlObject.isEnableSortByAlphabeticalOrder();
    }

    public boolean isEnableSortByAscendingOrder() {
        return controlObject.isEnableSortByAscendingOrder();
    }

    public void setEnableSortByAscendingOrder(boolean enableSortByAscendingOrder) {
        controlObject.setEnableSortByAscendingOrder(enableSortByAscendingOrder);
        if(enableSortByAscendingOrder){
            setItems();
        }
    }

    public boolean isEnableSortByDescendingOrder() {
        return controlObject.isEnableSortByDescendingOrder();
    }

    public void setEnableSortByDescendingOrder(boolean enableSortByDescendingOrder) {
        controlObject.setEnableSortByDescendingOrder(enableSortByDescendingOrder);
        if(enableSortByDescendingOrder){
            setItems();
        }
    }

    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
    }

    public void deleteItem(List<SpinnerData> items){
        List<String> ids=new ArrayList<>();
        for (int i = 0; i <items.size() ; i++) {

            ids.add(items.get(i).getId());
        }
        for (int i = 0; i <controlObject.getItemsList().size() ; i++) {
            for (int j = 0; j <ids.size() ; j++) {
                if(controlObject.getItemsList().get(i).getId().contains(ids.get(j))){
                    controlObject.getItemsList().remove(i);
                }
            }

        }
    }

    public LinearLayout getLl_control_ui(){
        return ll_control_ui;
    }

    public LinearLayout getLayout_control(){
        return layout_control;
    }
    public LinearLayout getLl_leftRightWeight(){
        return ll_leftRightWeight;
    }
    public CustomTextView getTv_displayName(){
        return tv_displayName;
    }
    public LinearLayout getLl_displayName(){
        return ll_displayName;
    }
    public SearchableMultiSpinner getMultiSearchableSpinner(){
        return multiSearchableSpinner;
    }
    public LinearLayout getLl_main_inside() {
        return ll_main_inside;
    }

    public void check(String [] ids) {

        multiSearchableSpinner.setItemIDs(ids);

    }
    @Override
    public void showMessageBelowControl(String msg) {
        if (msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        } else {
            ct_showText.setVisibility(View.GONE);
        }
    }

    public void setPropertiesSortingAndOthers(List<Item> itemsListProperties){
        loadSpinnerData(itemsListProperties);
    }

}
