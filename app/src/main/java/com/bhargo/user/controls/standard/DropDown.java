package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.hideSoftKeyboard;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.showSoftKeyBoard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.Item;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.SelectVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nk.mobileapps.spinnerlib.SearchableSpinner;
import nk.mobileapps.spinnerlib.SpinnerData;


public class DropDown implements SelectVariables, UIVariables {

    private static final String TAG = "DropDown";
    private final int DropDownTAG = 0;
    public SearchableSpinner searchableSpinner;
    Context context;
    ControlObject controlObject;
    List<Item> ddItems;
    CustomEditText ce_other;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    View view;
    ImproveHelper improveHelper;
    private LinearLayout linearLayout, ll_main_inside, ll_spinner, ll_tap_text, ll_control_ui, layout_control, ll_leftRightWeight;
    private LinearLayout ll_label, ll_displayName;
    private CustomTextView tv_tapTextType;
    private CustomTextView tv_displayName, tv_hint, ct_alertTypeDropDown,ct_showText;
    private ImageView iv_mandatory;
    private List<SpinnerData> spinnerDataList = new ArrayList<>();

    public DropDown(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
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
//        final SearchableSpinner searchableSpinner = new SearchableSpinner(context);
//        searchableSpinner.setItems(loadSpinnerData(), new SearchableSpinner.SpinnerListener() {
//            @Override
//            public void onItemsSelected(View v, List<SpinnerData> items, int position) {
//                if (searchableSpinner.getSelectedId() != null) {
//
//                }
//            }
//        });

            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            ddItems = new ArrayList<>();

            addDropDownStrip(context);

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public List<SpinnerData> loadSpinnerData(List<Item> items) {
        spinnerDataList = new ArrayList<>();
        SpinnerData data = new SpinnerData();
        data.setName(AppConstants.SPINNER_INIT_NAME);
        data.setId(AppConstants.SPINNER_INIT_ID);
        spinnerDataList.add(data);
        try {
            if (controlObject.isEnableSortByAlphabeticalOrder()) {
                ddItems = items;
                improveHelper.sortListAlphabetically(ddItems, true);
            } else if (controlObject.isEnableSortByAlphabeticalOrder() && controlObject.isEnableSortByAscendingOrder()) {
                ddItems = items;
/*
                Collections.sort(ddItems, new Comparator<Item>() {
                    @Override
                    public int compare(Item item, Item t1) {
                        return item.getValue().compareTo(t1.getValue());
                    }
                });
*/
                improveHelper.sortListAlphabetically(ddItems, true);

            } else if (controlObject.isEnableSortByAlphabeticalOrder() && controlObject.isEnableSortByDescendingOrder()) {
                ddItems = items;
//                improveHelper.sortListAlphabetically(ddItems,false);

                Collections.sort(ddItems, new Comparator<Item>() {
                    @Override
                    public int compare(Item item, Item t1) {
                        return t1.getValue().compareTo(item.getValue());
                    }
                });

            } else if (controlObject.isEnableSortByAscendingOrder()) {
                ddItems = items;
                improveHelper.sortListAlphabetically(ddItems, true);
            } else if (controlObject.isEnableSortByDescendingOrder()) {
                ddItems = items;
//                improveHelper.sortListAlphabetically(ddItems,false);


                Collections.sort(ddItems, new Comparator<Item>() {
                    @Override
                    public int compare(Item item, Item t1) {
                        return t1.getValue().compareTo(item.getValue());
                    }
                });

            } else {
                ddItems = items;
            }


            for (int count = 0; count < ddItems.size(); count++) {
                SpinnerData spinnerData = new SpinnerData();
                spinnerData.setId(ddItems.get(count).getId());
                spinnerData.setName(ddItems.get(count).getValue());
                spinnerDataList.add(spinnerData);
            }

            if (controlObject.isEnableAllowOtherChoice()) {
                SpinnerData spinnerData = new SpinnerData();
                String other_id = "other_" + ddItems.size();
                spinnerData.setId(other_id);
                spinnerData.setName(context.getString(R.string.other));
                spinnerDataList.add(spinnerData);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "loadSpinnerData", e);
        }
        return spinnerDataList;
    }


    public LinearLayout getDropdown() {

        return linearLayout;
    }

    public void addDropDownStrip(final Context context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//         view = lInflater.inflate(R.layout.control_dropdown, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

                    view = linflater.inflate(R.layout.layout_drop_down_default, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

                    view = linflater.inflate(R.layout.layout_drop_down_rounded_rectangle, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {

                    view = linflater.inflate(R.layout.layout_drop_down_left_right, null);
                }
            } else {

                view = linflater.inflate(R.layout.layout_drop_down_default, null);
            }
            view.setTag(DropDownTAG);

            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            ll_spinner = view.findViewById(R.id.ll_spinner);
            searchableSpinner = view.findViewById(R.id.searchableSpinner_main);
            searchableSpinner.setItems(new ArrayList<>());
            searchableSpinner.init(AppConstants.SPINNER_INIT_NAME);
            ct_alertTypeDropDown = view.findViewById(R.id.ct_alertTypeText);
            searchableSpinner.setTag(controlObject.getControlName());
            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ce_other = view.findViewById(R.id.ce_otherchoice);
            ll_label = view.findViewById(R.id.ll_label);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            layout_control = view.findViewById(R.id.layout_control);
            ll_leftRightWeight = view.findViewById(R.id.ll_leftRightWeight);
            ct_showText = view.findViewById(R.id.ct_showText);
            ce_other.setTag(controlObject.getControlName());

            /*searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> View, View v, int position, long id) {

                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;

                            }
                            if( AppConstants.GlobalObjects!=null)
                            AppConstants.GlobalObjects.setCurrent_GPS("");
                            ((MainActivity) context).ChangeEvent(View);
                        }
                    }

                    if(searchableSpinner.getSelectedName()!=null) {
                        String selectedItem = searchableSpinner.getSelectedName();
                        if (selectedItem.equalsIgnoreCase(context.getString(R.string.other))) {
                            ce_other.setVisibility(android.view.View.VISIBLE);
                            ce_other.requestFocus();
                            showSoftKeyBoard(context, ce_other);
                        } else {
                            ce_other.setText("");
                            hideSoftKeyboard(context, ce_other);
                            ce_other.setVisibility(android.view.View.GONE);
                        }
                        if (searchableSpinner.isSelected()) {
                            ct_alertTypeDropDown.setVisibility(android.view.View.GONE);
                            ct_alertTypeDropDown.setText("");
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/
//        tv_tapTextType.setText("Tap to select Drop down");

//        ImageView iv_textTypeImage = view.findViewById(R.id.iv_textTypeImage);
//        iv_textTypeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_spinner_s));

//        ll_tap_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ll_spinner.setVisibility(View.VISIBLE);
//                ll_tap_text.setVisibility(View.GONE);
//                searchableSpinner.setSelected(true);
//            }
//        });
//        searchableSpinner.setItems(loadSpinnerData(), new SearchableSpinner.SpinnerListener() {
//            @Override
//            public void onItemsSelected(View v, List<SpinnerData> items, int position) {
//                if (searchableSpinner.getSelectedId() != null) {
//
//                }
//            }
//        });

            setControlValues();
            searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> View, View v, int position, long id) {


//                    ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.control_active_background));
                    String selectedItem = searchableSpinner.getSelectedName();
                    controlObject.setItemSelected(dropdownItemIsSelected());
                    controlObject.setText(getSelectedDropDownItem());
                    controlObject.setControlValue(getSelectedDropDownItemID());
                    if (!selectedItem.contentEquals(AppConstants.SPINNER_INIT_NAME)) {
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                if (SubformFlag) {
                                    AppConstants.SF_Container_position = SubformPos;
                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                }
                                if (AppConstants.GlobalObjects != null)
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                ((MainActivity) context).ChangeEvent(View);
                            }
                        }


                        if (selectedItem.equalsIgnoreCase(context.getString(R.string.other))) {
                            ce_other.setVisibility(android.view.View.VISIBLE);
                            ce_other.requestFocus();
                            showSoftKeyBoard(context, ce_other);
                        } else {
                            ce_other.setText("");
                            hideSoftKeyboard(context, ce_other);
                            ce_other.setVisibility(android.view.View.GONE);
                        }
                        if (searchableSpinner.isSelected()) {
                            ct_alertTypeDropDown.setVisibility(android.view.View.GONE);
                            ct_alertTypeDropDown.setText("");
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


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
                        controlObject.setText("" + editable);
                    }
                }
            });

            linearLayout.addView(view);

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addDropDownStrip", e);
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

            setItems();

            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    public void Clear() {
        controlObject.setText(null);
        controlObject.setControlValue(null);
        searchableSpinner.clearSelections();
        spinnerDataList = new ArrayList<>();
        SpinnerData data = new SpinnerData();
        data.setName(AppConstants.SPINNER_INIT_NAME);
        data.setId(AppConstants.SPINNER_INIT_ID);
        spinnerDataList.add(data);
        searchableSpinner.setItems(spinnerDataList);
    }

    private void setItems() {
        try {

            if (controlObject.getItemsList() != null && controlObject.getItemsList().size() > 0) {
                List<SpinnerData> spinnerDataList = new ArrayList<>();
                spinnerDataList = loadSpinnerData(controlObject.getItemsList());
                searchableSpinner.setItems(spinnerDataList, new SearchableSpinner.SpinnerListener() {
                    @Override
                    public void onItemsSelected(View v, List<SpinnerData> items, int position) {
                        if (searchableSpinner.getSelectedId() != null) {

                        }
                    }
                });
                if (controlObject.getDefaultItem() != null && !controlObject.getDefaultItem().contentEquals("")) {
                    for (SpinnerData spinnerData : spinnerDataList) {

                        if (spinnerData.getId().equalsIgnoreCase(controlObject.getDefaultItem())) {
                            searchableSpinner.setItemID(spinnerData.getId());
                            controlObject.setItemSelected(true);
                            controlObject.setText(getSelectedDropDownItem());
                            controlObject.setControlValue(getSelectedDropDownItemID());
                        }
                    }
                }
                if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().contentEquals("")) {

                    for (SpinnerData spinnerData : spinnerDataList) {

                        if (spinnerData.getName().equalsIgnoreCase(controlObject.getDefaultValue())) {
                            searchableSpinner.setItemID(spinnerData.getId());
                        }
                        //bhavani
//                        if (spinnerData.getId().startsWith("0")) {
//                            spinnerData.setId(spinnerData.getId().substring(1));
//                        }
//                        if (spinnerData.getId().startsWith("0")) {
                        spinnerData.setId(spinnerData.getId());
//                        }
                        if (spinnerData.getId().equalsIgnoreCase(controlObject.getDefaultValue())) {
                            searchableSpinner.setItemID(spinnerData.getId());
                            controlObject.setItemSelected(true);
                            controlObject.setText(getSelectedDropDownItem());
                            controlObject.setControlValue(getSelectedDropDownItemID());

                        }
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setItems", e);
        }
    }

    public void setnewItemsDynamically(List<String> newItems) {
//        searchableSpinner.setItems(loadSpinnerData(newItems));
    }

    public void setnewItemsListDynamically(List<Item> newItems) {
        try {
            Log.d(TAG, "setnewItemsListDynamically: " + "test");
            List<SpinnerData> spinnerDataList = loadSpinnerData(newItems);
            searchableSpinner.setItems(spinnerDataList);
            if (controlObject.getDefaultItem() != null && !controlObject.getDefaultItem().contentEquals("")) {
                for (SpinnerData spinnerData : spinnerDataList) {

                    if (spinnerData.getId().equalsIgnoreCase(controlObject.getDefaultItem())) {
                        searchableSpinner.setItemID(spinnerData.getId());
                    }
                }
            }
            if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().contentEquals("")) {

                for (SpinnerData spinnerData : spinnerDataList) {

                    if (spinnerData.getName().equalsIgnoreCase(controlObject.getDefaultValue())) {
                        searchableSpinner.setItemID(spinnerData.getId());
                    }
                   /* if (spinnerData.getId().startsWith("0")) {
                        spinnerData.setId(spinnerData.getId().substring(1));
                    }*/
                    //bhavani
                    spinnerData.setId(spinnerData.getId());
                    if (spinnerData.getId().equalsIgnoreCase(controlObject.getDefaultValue())) {
                        searchableSpinner.setItemID(spinnerData.getId());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setnewItemsListDynamically", e);
        }
    }

    public List<Item> getnewItemsListDynamically() {
        return ddItems;
    }

    public void addItemsListDynamically(List<Item> newItems) {

        searchableSpinner.AddItems(loadSpinnerData(newItems));
    }

    public boolean dropdownItemIsSelected() {
        if (searchableSpinner.getSelectedId() != null && !searchableSpinner.getSelectedId().equalsIgnoreCase("-1")) {
            return searchableSpinner.isSelected();
        }
        return !searchableSpinner.isSelected();
    }

    public CustomTextView setAlertDropDown() {

        return ct_alertTypeDropDown;
    }

    public boolean isDropDownOthersEmpty() {
        boolean isOtherChecked = false;
        try {
            if (controlObject.isEnableAllowOtherChoice()) {


                String selectedtext = searchableSpinner.getSelectedName();

                if (selectedtext.equalsIgnoreCase(context.getString(R.string.other)) && ce_other.getText().toString().isEmpty()) {

                    isOtherChecked = true;

                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "isDropDOwnOthersEmpty", e);
        }
        return isOtherChecked;


    }

    public String getSelectedDropDownString() {
        String value = "";
        if (searchableSpinner.getSelectedName() != null) {
            if (searchableSpinner.getSelectedName().trim().equalsIgnoreCase("Other")) {
                value = ce_other.getText().toString().trim() + "," + ce_other.getText().toString().trim();
            } else {
                value = searchableSpinner.getSelectedId().trim() + "," + searchableSpinner.getSelectedName().trim();
            }
        }
        return value;
    }

    public String getSelectedDropDownItem() {

        String selectedItem = null;
        try {
            String selectedtext = searchableSpinner.getSelectedName();

            if (selectedtext != null && selectedtext.equalsIgnoreCase(context.getString(R.string.other)) && !ce_other.getText().toString().isEmpty()) {

                selectedItem = ce_other.getText().toString();

            } else {
                selectedItem = selectedtext;
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getSelectedDropDownItem", e);
        }
        if (selectedItem.equalsIgnoreCase("-- Select --")) {
            selectedItem = "";
        }
        controlObject.setText(selectedItem);
        return selectedItem;
    }

    public String getSelectedDropDownItemID() {

        String selectedItem = null;
        try {
            String selectedtext = searchableSpinner.getSelectedId();

            if (selectedtext != null && selectedtext.equalsIgnoreCase(context.getString(R.string.other)) && !ce_other.getText().toString().isEmpty()) {

                selectedItem = ce_other.getText().toString();

            } else {
                selectedItem = selectedtext;
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getSelectedDropDownItemID", e);
        }
        if (selectedItem.equalsIgnoreCase("-1") || selectedItem.equalsIgnoreCase("-- Select --")) {
            selectedItem = "";
        }
        return selectedItem;
    }

    public void setSelectedDropdownItem(String Name) {
        searchableSpinner.setItemID(Name);
    }

    public boolean isOtherOptionEnable() {
        return controlObject.isEnableAllowOtherChoice();
    }

    public void setItemId(String id, String value) {
        if (id != null && !id.isEmpty()) {
            searchableSpinner.setItemID(id);
            if (isOtherOptionEnable() && !value.isEmpty()) {
                ce_other.setVisibility(View.VISIBLE);
                ce_other.setText(value);
            }
        }else{
            searchableSpinner.setItemValue(value);
        }
    }

    public LinearLayout getLl_main_inside() {
        return ll_main_inside;
    }

    public LinearLayout getLl_spinner() {
        return ll_spinner;
    }


    @Override
    public Item getSelectedItem() {
        return null;
    }

    @Override
    public void setSelectedItem(Item selectedItem) {

        setItemId(selectedItem.getId(), "");
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
        //searchableSpinner.setEnabled(!enabled);
        controlObject.setDisable(!enabled);
//        setViewDisable(view, !enabled);
//        setViewDisableOrEnableDefault(context,view, enabled);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisable(), ll_tap_text, view);
    }

    @Override
    public void clean() {

    }

    @Override
    public void loadItems(List<Item> items) {

        setnewItemsListDynamically(items);

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
        if (enabled) {
            setItems();
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
    /*hideDisplayName,enableSortByAlphabeticalOrder,enableSortByChronologicalOrder,
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


    public boolean isEnableSortByChronologicalOrder() {
        return controlObject.isEnableSortByChronologicalOrder();
    }

    public void setEnableSortByChronologicalOrder(boolean enableSortByChronologicalOrder) {
        controlObject.setEnableSortByChronologicalOrder(enableSortByChronologicalOrder);
        setItems();
    }

    public boolean isEnableSortByAscendingOrder() {
        return controlObject.isEnableSortByAscendingOrder();
    }

    public void setEnableSortByAscendingOrder(boolean enableSortByAscendingOrder) {
        controlObject.setEnableSortByAscendingOrder(enableSortByAscendingOrder);
        if(enableSortByAscendingOrder) {
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

    public boolean isEnableAllowOtherChoice() {
        return controlObject.isEnableAllowOtherChoice();
    }

    public void setEnableAllowOtherChoice(boolean enableAllowOtherChoice) {
        controlObject.setEnableAllowOtherChoice(enableAllowOtherChoice);
        if(enableAllowOtherChoice){
            setItems();
        }

    }

    public SearchableSpinner getSearchableSpinner() {
        return searchableSpinner;
    }

    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
    }

    public void deleteItem(List<String> list) {
        for (int i = 0; i < spinnerDataList.size(); i++) {
            if (list.contains(spinnerDataList.get(i).getId())) {
                spinnerDataList.remove(i);
                i--;
            }
        }
        searchableSpinner.setItems(spinnerDataList);

    }

    public LinearLayout getLl_control_ui() {
        return ll_control_ui;
    }

    public LinearLayout getLayout_control() {
        return layout_control;
    }

    public LinearLayout getLl_leftRightWeight() {
        return ll_leftRightWeight;
    }

    public CustomTextView getTv_displayName() {
        return tv_displayName;
    }

    public LinearLayout getLl_displayName() {
        return ll_displayName;
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
}
