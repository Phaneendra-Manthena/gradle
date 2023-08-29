package com.bhargo.user.controls.advanced;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.bhargo.user.Java_Beans.ActionWithoutCondition_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataTableColumn_Bean;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.SessionManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class DataTableControlOld implements UIVariables {
    private final Activity context;
    private final ControlObject controlObject;
    private final boolean subFormFlag;
    private final int subFormPos;
    private final String subFormName;
    public LinearLayout linearLayout, ll_displayName, ll_data_table_main;
    public List<String> columnsList;
    public int rows;
    View rView;
    ActionWithoutCondition_Bean actionObject;
    LinkedHashMap<String, List<String>> outputData;
    int rowHeight;
    List<DataTableColumn_Bean> dataTableColumn_beans = new ArrayList<>();
    boolean fixedWidth;
    boolean wrapContent;
    boolean isKeyPresent;
    int columnWidth;
    float floatWidth;
    List<Integer> integers = new ArrayList<>();
    List<String> strLastColItem = new ArrayList<>();
    List<List<Integer>> allRowsHeights = new ArrayList<>();
    LinkedHashMap<String, List<Integer>> integersMap = new LinkedHashMap<>();
    boolean allFootersEmpty = true;
    CustomEditText et_search;
    List<String> rowData;
    private CustomTextView tv_displayName, tv_hint;

    public DataTableControlOld(Activity context, ControlObject controlObject, boolean subFormFlag, int subFormPos, String subFormName) {
        this.context = context;
        this.controlObject = controlObject;
        this.subFormFlag = subFormFlag;
        this.subFormPos = subFormPos;
        this.subFormName = subFormName;
        initViews();
    }

    private void initViews() {
        rowData = new ArrayList<>();
        linearLayout = new LinearLayout(context);
        linearLayout.setTag(controlObject.getControlName());
        ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
        linearLayout.setLayoutParams(ImproveHelper.layout_params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        View rView = lInflater.inflate(R.layout.control_data_table, null);
        if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
            if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                rView = lInflater.inflate(R.layout.control_data_table_six, null);
            } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                rView = lInflater.inflate(R.layout.control_data_table_seven, null);
            } else {
                rView = lInflater.inflate(R.layout.control_data_table, null);
            }
        } else {
//            rView = lInflater.inflate(R.layout.control_data_table, null);
            rView = lInflater.inflate(R.layout.control_data_table_default, null);
        }

        ll_displayName = rView.findViewById(R.id.ll_displayName);
        tv_displayName = rView.findViewById(R.id.tv_displayName);
        tv_hint = rView.findViewById(R.id.tv_hint);
        ll_data_table_main = rView.findViewById(R.id.ll_data_table_main);
        et_search = rView.findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null) {
                    searchTable(charSequence.toString().trim());
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setControlValues();

        linearLayout.addView(rView);

    }

    private void searchTable(String searchStr) {
        for (int i = 0; i < rowData.size(); i++) {
            View rowView = ll_data_table_main.getChildAt(i);
            if (rowData.get(i).toLowerCase().contains(searchStr.toLowerCase())) {
                rowView.setVisibility(View.VISIBLE);
            } else {
                rowView.setVisibility(View.GONE);
            }
        }
    }


    private void setControlValues() {
        tv_displayName.setText(controlObject.getDisplayName());

        if (controlObject.getHint() == null || controlObject.getHint().contentEquals("")) {
            tv_hint.setVisibility(View.GONE);
        } else {
            tv_hint.setVisibility(View.VISIBLE);
            tv_hint.setText(controlObject.getHint());
        }
        if (controlObject.isHideDisplayName()) {
            ll_displayName.setVisibility(View.GONE);
//            if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
//                tv_displayName.setVisibility(View.GONE);
//                tv_hint.setVisibility(View.GONE);
//                ll_displayName.setVisibility(View.GONE);
//            }
        }

        if (controlObject.isDataTable_EnableSearch()) {
            et_search.setVisibility(View.VISIBLE);
        } else {
            et_search.setVisibility(View.GONE);
        }
    }

    public void setDataTableData(ActionWithoutCondition_Bean actionWithoutCondition_bean, LinkedHashMap<String, List<String>> map, List<String> columnsList) {
        actionObject = actionWithoutCondition_bean;
        outputData = map;
        this.columnsList = columnsList;
//        rows = outputData.get(columnsList.get(0).toLowerCase()).size();
        dataTableColumn_beans = actionWithoutCondition_bean.getDataTableColumn_beanList();
        rows = outputData.get(dataTableColumn_beans.get(0).getColumnName().toLowerCase()) != null ? outputData.get(dataTableColumn_beans.get(0).getColumnName().toLowerCase()).size() : outputData.get(dataTableColumn_beans.get(0).getColumnName()).size();
        fixedWidth = actionWithoutCondition_bean.isDataTableFixedWidthEnabled();
       /* wrapContent = controlObject.isDataTableRowHeightWrapContent();
       *//* if(actionWithoutCondition_bean.getDataTableRowHeight()!=null) {
            rowHeight =Integer.parseInt(actionWithoutCondition_bean.getDataTableRowHeight());
        }*//*
        if (controlObject.getDataTableRowHeight() != null) {
            rowHeight = Integer.parseInt(controlObject.getDataTableRowHeight());
        }*/

// new ctrl Design start
        if (columnsList != null && columnsList.size() > 0 && dataTableColumn_beans != null) {
            for (int j = 0; j < columnsList.size(); j++) {
                if (j < dataTableColumn_beans.size()) {
                    boolean isEnabled = dataTableColumn_beans.get(j).isColumnEnabled();

                    if (isEnabled) {
                        strLastColItem.add(columnsList.get(j));
                    }
                }
            }
        }
// new ctrl Design end

        for (int i = 0; i < rows; i++) {
            integers = new ArrayList<>();
            LinearLayout ll_grid_row = new LinearLayout(context);
            ll_grid_row.setOrientation(LinearLayout.HORIZONTAL);

//            ll_grid_row.setBackgroundColor(Color.WHITE);
            String rowStr = "";
            for (int j = 0; j < columnsList.size(); j++) {
                if (j < dataTableColumn_beans.size()) {
                    boolean isEnabled = dataTableColumn_beans.get(j).isColumnEnabled();

                    if (isEnabled) {
                        //rowStr=rowStr+outputData.get(columnsList.get(j).toLowerCase()).get(i).trim();
                        rowStr = rowStr + outputData.get(dataTableColumn_beans.get(j).getColumnName().toLowerCase()).get(i).trim();

//                        addColumn(j, i, ll_grid_row);
                        addColumn(j, i, ll_grid_row, (strLastColItem.size() - 1));// new ctrl Design start
                    }
                }
            }
            rowData.add(rowStr);


            ll_data_table_main.addView(ll_grid_row);
            allRowsHeights.add(integers);
        }
        System.out.println(":::---------" + rowData.size() + ":" + ll_data_table_main.getChildCount());

        //Footer

        LinearLayout ll_grid_row = new LinearLayout(context);
        ll_grid_row.setOrientation(LinearLayout.HORIZONTAL);

//        ll_grid_row.setBackgroundColor(Color.WHITE);
        for (int j = 0; j < columnsList.size(); j++) {
            if (j < dataTableColumn_beans.size()) {
                boolean isEnabled = dataTableColumn_beans.get(j).isColumnEnabled();
                if (isEnabled) {
//                    addColumn(j, rows, ll_grid_row);

                    addColumn(j, rows, ll_grid_row, (strLastColItem.size() - 1)); // new ctrl Design start
                }
            }
        }
        if (!allFootersEmpty) {
            ll_grid_row.setTag("Footer");
            ll_data_table_main.addView(ll_grid_row);
        }

        System.out.println(":::---------" + rowData.size() + ":" + ll_data_table_main.getChildCount());
    }

    public void ClearData() {
        ll_data_table_main.removeAllViews();
        allRowsHeights.clear();
    }

    public LinkedHashMap<String, List<String>> getExistingData(List<String> columnsList, LinkedHashMap<String, List<String>> outputMap) {
        LinkedHashMap<String, List<String>> dataMap = new LinkedHashMap<>();
/*        View rView = linearLayout.getChildAt(0);
        ll_data_table_main = rView.findViewById(R.id.ll_data_table_main);*/
        if (ll_data_table_main.getChildCount() > 0) {
            for (int i = 0; i < columnsList.size(); i++) {
                dataMap.put(columnsList.get(i), new ArrayList<>());
            }
            for (int i = 0; i < ll_data_table_main.getChildCount(); i++) {
                LinearLayout ll_grid_row = (LinearLayout) ll_data_table_main.getChildAt(i);
                if (ll_grid_row.getTag() == null) {
                    for (int j = 0; j < columnsList.size(); j++) {
                        String key = columnsList.get(j);
                        for (int k = 0; k < ll_grid_row.getChildCount(); k++) {
                            View columnView = ll_grid_row.getChildAt(k);
//                        CustomTextView textView = columnView.findViewById(R.id.ll_control_view);
                            CustomTextView textView = (CustomTextView) ((LinearLayout) columnView).getChildAt(1);
                            if (columnView.getTag().toString().contentEquals(key)) {
                                List<String> list = null;
                                if (dataMap.containsKey(key)) {
                                    list = dataMap.get(key);
                                    if (list == null) {
                                        list = new ArrayList<>();
                                    }
                                } else {
                                    list = new ArrayList<>();
                                }
                                list.add(textView.getText().toString());
                                dataMap.put(key, list);
                                isKeyPresent = true;
                                break;
                            }

                        }
                        if (!isKeyPresent) {
                            List<String> list = dataMap.get(key);
                            list.add("");
                            dataMap.put(key, list);
                        } else {
                            isKeyPresent = false;
                        }
                    }
                }

            }

            Iterator iterator = outputMap.entrySet().iterator();
            Iterator iterator1 = dataMap.entrySet().iterator();

            while (iterator1.hasNext()) {
                LinkedHashMap.Entry entry = (LinkedHashMap.Entry) iterator.next();
                LinkedHashMap.Entry entry_data = (LinkedHashMap.Entry) iterator1.next();
                List<String> list = (List<String>) entry_data.getValue();
                list.addAll((List<String>) entry.getValue());

                dataMap.put(entry_data.getKey().toString(), list);

            }

            return dataMap;
        } else {
            return outputMap;
        }

    }
    // new ctrl Design start
    private void addColumn(int columnIndex, int rowIndex, LinearLayout ll_grid_row, int strLastColItem) {
        Log.d("addColumnColumnIndex: ", "" + strLastColItem);
        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
            view = lInflater.inflate(R.layout.item_data_table_inner_control_seven, null);
        } else {
//            view = lInflater.inflate(R.layout.item_data_table_inner_control, null);
            view = lInflater.inflate(R.layout.item_data_table_inner_control_default, null);
        }
//        View view = lInflater.inflate(R.layout.item_data_table_inner_control, null);
        view.setTag(dataTableColumn_beans.get(columnIndex).getColumnName());
        CustomTextView header = view.findViewById(R.id.tv_label);
        CustomTextView textView = view.findViewById(R.id.ll_control_view);
        View dtView = view.findViewById(R.id.dtView);
        textView.setId(columnIndex);
        textView.setTag(String.valueOf(rowIndex));
        header.setText(dataTableColumn_beans.get(columnIndex).getHeaderName());
        header.setTag(controlObject.getControlName() + "_" + dataTableColumn_beans.get(columnIndex).getColumnName());
        if (columnIndex == 0 & new SessionManager(context).getOrgIdFromSession().equalsIgnoreCase("SELE20210719175221829")) {
            textView.setTypeface(header.getTypeface(), Typeface.BOLD);
        }
        if (rowIndex == 0 && columnIndex == 0) { //  == || refers rows and columns
            header.setTypeface(header.getTypeface(), Typeface.BOLD);
            header.setBackgroundResource(R.drawable.only_top_left_rounded_corners_default_gray_bg);
        } else if (rowIndex == 0 && columnIndex == strLastColItem) {
            header.setTypeface(header.getTypeface(), Typeface.BOLD);
            header.setBackgroundResource(R.drawable.only_top_right_rounded_corners_default_gray_bg);
        } else if (rowIndex == 0) {
            header.setTypeface(header.getTypeface(), Typeface.BOLD);
            header.setBackgroundResource(R.drawable.only_default_gray_bg);
        }

       /* if (fixedWidth) {
            floatWidth = Float.parseFloat(dataTableColumn_beans.get(columnIndex).getColumnWidth());
        } else {
            columnWidth = Integer.parseInt(dataTableColumn_beans.get(columnIndex).getColumnWidth());
        }*/
        if (rowIndex > 0) {
            header.setVisibility(View.GONE);
        }
        float total = 0.0f, avg = 0.0f;
        if (rowIndex == rows) {
            String footerFunction = dataTableColumn_beans.get(columnIndex).getFooterFunction();
            textView.setBackgroundResource(0);
            switch (footerFunction) {
                case "Summation":
                    allFootersEmpty = false;
                    total = total(dataTableColumn_beans.get(columnIndex).getColumnName());
                    if (total == 0.0f) {
                        textView.setText("NA");
                    } else {
                        textView.setText("" + total);
                    }
                    break;
                case "Average":
                    allFootersEmpty = false;
                    avg = average(dataTableColumn_beans.get(columnIndex).getColumnName());
                    if (avg == 0.0f) {
                        textView.setText("NA");
                    } else {
                        textView.setText("" + avg);
                    }
                    break;
                case "Count":
                    allFootersEmpty = false;
                    textView.setText("" + rows);
                    break;
                case "None":
                    textView.setVisibility(View.INVISIBLE);
                    break;
            }

            ll_grid_row.addView(view);
        } else {
            try {
                String value = String.valueOf(outputData.get(dataTableColumn_beans.get(columnIndex).getColumnName().toLowerCase()).get(rowIndex));

//                textView.setText(outputData.get(columnsList.get(columnIndex)).get(rowIndex));
                textView.setText(value);
            } catch (Exception e) {
                e.printStackTrace();

            }
            ll_grid_row.addView(view);
        }

        ViewTreeObserver vto = textView.getViewTreeObserver();

        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                int width = textView.getMeasuredWidth();
                int height = textView.getMeasuredHeight();
                String row = (String) textView.getTag();
                if (integersMap.containsKey(row)) {
                    integersMap.get(row).add(height);
                } else {
                    integers = new ArrayList<>();
                    integers.add(height);
                    integersMap.put(row, integers);
                }
                if (wrapContent) {
                    setAllHeights();
                }

            }
        });
        if (!fixedWidth) {
            LinearLayout.LayoutParams params = null;
            if (wrapContent) {
                params = new LinearLayout.LayoutParams(pxToDP(columnWidth), ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                params = new LinearLayout.LayoutParams(pxToDP(columnWidth), pxToDP(rowHeight));
            }
            textView.setLayoutParams(params);
            header.setLayoutParams(params);
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int mWidth = displayMetrics.widthPixels;
            LinearLayout.LayoutParams params = null;
            if (wrapContent) {
                params = new LinearLayout.LayoutParams((int) (floatWidth * mWidth / 100), ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                params = new LinearLayout.LayoutParams((int) (floatWidth * mWidth / 100), pxToDP(rowHeight));
            }
            textView.setLayoutParams(params);
            header.setLayoutParams(params);
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setTag(controlObject.getControlName());
                if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {

                    view.setTag(controlObject.getControlName());
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.SF_Container_position = rowIndex;
                        AppConstants.EventFrom_subformOrNot = subFormFlag;
                        if (subFormFlag) {

                            AppConstants.Current_ClickorChangeTagName = subFormName;
                        }
                        AppConstants.DATA_TABLE_ROW_POS = rowIndex;
                        AppConstants.GlobalObjects.setCurrent_GPS("");
                        ((MainActivity) context).ClickEvent(view);
                    }
                }
            }
        });
    }
    // new ctrl Design End


/*
    private void addColumn(int columnIndex, int rowIndex, LinearLayout ll_grid_row) {

        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
            view = lInflater.inflate(R.layout.item_data_table_inner_control_seven, null);
        } else {
//            view = lInflater.inflate(R.layout.item_data_table_inner_control, null);
            view = lInflater.inflate(R.layout.item_data_table_inner_control_default, null);
        }
//        View view = lInflater.inflate(R.layout.item_data_table_inner_control, null);
        view.setTag(dataTableColumn_beans.get(columnIndex).getColumnName());
        CustomTextView header = view.findViewById(R.id.tv_label);
        CustomTextView textView = view.findViewById(R.id.ll_control_view);
        View dtView = view.findViewById(R.id.dtView);
        textView.setId(columnIndex);
        textView.setTag(String.valueOf(rowIndex));
        header.setText(dataTableColumn_beans.get(columnIndex).getHeaderName());
        header.setTag(controlObject.getControlName() + "_" + dataTableColumn_beans.get(columnIndex).getColumnName());
        if (columnIndex == 0 & new SessionManager(context).getOrgIdFromSession().equalsIgnoreCase("SELE20210719175221829")) {
            textView.setTypeface(header.getTypeface(), Typeface.BOLD);
        }

        if (fixedWidth) {
            floatWidth = Float.parseFloat(dataTableColumn_beans.get(columnIndex).getColumnWidth());
        } else {
            columnWidth = Integer.parseInt(dataTableColumn_beans.get(columnIndex).getColumnWidth());
        }
        if (rowIndex > 0) {
            header.setVisibility(View.GONE);
        }
        float total = 0.0f, avg = 0.0f;
        if (rowIndex == rows) {
            String footerFunction = dataTableColumn_beans.get(columnIndex).getFooterFunction();
            textView.setBackgroundResource(0);
            switch (footerFunction) {
                case "Summation":
                    allFootersEmpty = false;
                    total = total(dataTableColumn_beans.get(columnIndex).getColumnName());
                    if (total == 0.0f) {
                        textView.setText("NA");
                    } else {
                        textView.setText("" + total);
                    }
                    break;
                case "Average":
                    allFootersEmpty = false;
                    avg = average(dataTableColumn_beans.get(columnIndex).getColumnName());
                    if (avg == 0.0f) {
                        textView.setText("NA");
                    } else {
                        textView.setText("" + avg);
                    }
                    break;
                case "Count":
                    allFootersEmpty = false;
                    textView.setText("" + rows);
                    break;
                case "None":
                    textView.setVisibility(View.INVISIBLE);
                    break;
            }

            ll_grid_row.addView(view);
        } else {
            try {
                String value = String.valueOf(outputData.get(dataTableColumn_beans.get(columnIndex).getColumnName().toLowerCase()).get(rowIndex));

//                textView.setText(outputData.get(columnsList.get(columnIndex)).get(rowIndex));
                textView.setText(value);
            } catch (Exception e) {
                e.printStackTrace();

            }
            ll_grid_row.addView(view);
        }

        ViewTreeObserver vto = textView.getViewTreeObserver();

        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                int width = textView.getMeasuredWidth();
                int height = textView.getMeasuredHeight();
                String row = (String) textView.getTag();
                if (integersMap.containsKey(row)) {
                    integersMap.get(row).add(height);
                } else {
                    integers = new ArrayList<>();
                    integers.add(height);
                    integersMap.put(row, integers);
                }
                if (wrapContent) {
                    setAllHeights();
                }

            }
        });
        if (!fixedWidth) {
            LinearLayout.LayoutParams params = null;
            if (wrapContent) {
                params = new LinearLayout.LayoutParams(pxToDP(columnWidth), ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                params = new LinearLayout.LayoutParams(pxToDP(columnWidth), pxToDP(rowHeight));
            }
            textView.setLayoutParams(params);
            header.setLayoutParams(params);
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int mWidth = displayMetrics.widthPixels;
            LinearLayout.LayoutParams params = null;
            if (wrapContent) {
                params = new LinearLayout.LayoutParams((int) (floatWidth * mWidth / 100), ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                params = new LinearLayout.LayoutParams((int) (floatWidth * mWidth / 100), pxToDP(rowHeight));
            }
            textView.setLayoutParams(params);
            header.setLayoutParams(params);
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setTag(controlObject.getControlName());
                if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {

                    view.setTag(controlObject.getControlName());
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.SF_Container_position = rowIndex;
                        AppConstants.EventFrom_subformOrNot = subFormFlag;
                        if (subFormFlag) {

                            AppConstants.Current_ClickorChangeTagName = subFormName;
                        }
                        AppConstants.DATA_TABLE_ROW_POS = rowIndex;
                        AppConstants.GlobalObjects.setCurrent_GPS("");
                        ((MainActivity) context).ClickEvent(view);
                    }
                }
            }
        });

    }
*/

    private void setAllHeights() {
        View rview = linearLayout.getChildAt(0);
        LinearLayout ll_data_table_main = rview.findViewById(R.id.ll_data_table_main);
        for (int i = 0; i < ll_data_table_main.getChildCount(); i++) {
            if (integersMap.containsKey(String.valueOf(i))) {
                LinearLayout ll_grid = (LinearLayout) ll_data_table_main.getChildAt(i);
                for (int j = 0; j < ll_grid.getChildCount(); j++) {
                    LinearLayout linearLayout = (LinearLayout) ll_grid.getChildAt(j);
                    CustomTextView header = (CustomTextView) linearLayout.getChildAt(0);
                    CustomTextView textView = (CustomTextView) linearLayout.getChildAt(1);
                    if (header != null && textView != null) {
                        ViewGroup.LayoutParams params1 = header.getLayoutParams();
                        ViewGroup.LayoutParams params2 = textView.getLayoutParams();
                        params1.height = ImproveHelper.getMax(integersMap.get(String.valueOf(i)));
                        params2.height = ImproveHelper.getMax(integersMap.get(String.valueOf(i)));
                        textView.setLayoutParams(params2);
                        header.setLayoutParams(params1);
                    }
                }
            }
        }

    }

    private float total(String columnName) {
        String regex = "^[0-9]*[.]?[0-9]*$";
        float total = 0.0f;
        List<String> list = outputData.get(columnName);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).matches(regex)) {
                total = total + Float.parseFloat(list.get(i));
            }
        }
        return total;
    }

    private float average(String column) {
        return total(column) / rows;
    }

    public int pxToDP(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
    }

    public LinearLayout getDataTableLayout() {
        return linearLayout;
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
    public void setVisibility(boolean visibility) {
        if (visibility) {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        } else {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }
}
