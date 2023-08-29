package com.bhargo.user.controls.advanced;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.ActionWithoutCondition_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataTableColumn_Bean;
import com.bhargo.user.Java_Beans.DisplaySettings;
import com.bhargo.user.Java_Beans.Param;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.generatefiles.ExcelUtils;
import com.bhargo.user.generatefiles.GeneratePDFReport;
import com.bhargo.user.realm.RealmDBHelper;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PropertiesNames;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class DataTableControl implements UIVariables {
    private static final String TAG = "DataTableControl";
    private final Activity context;
    private final ControlObject controlObject;
    private final boolean subFormFlag;
    private final int subFormPos;
    private final String subFormName;
    public LinearLayout linearLayout, ll_displayName,ll_main_inside,ll_control_ui,ll_data_table_main;
    public int rows;
    public TableLayout tl_header, tl_body, tl_footer;
    public LinearLayout ll_table;
    View rView;

    public ActionWithoutCondition_Bean getActionObject() {
        return actionObject;
    }

    public void setActionObject(ActionWithoutCondition_Bean actionObject) {
        this.actionObject = actionObject;
    }

    ActionWithoutCondition_Bean actionObject;

    public List<DataTableColumn_Bean> getDataTableColumn_beans() {
        return dataTableColumn_beans;
    }

    public void setDataTableColumn_beans(List<DataTableColumn_Bean> dataTableColumn_beans) {
        this.dataTableColumn_beans = dataTableColumn_beans;
    }

    List<DataTableColumn_Bean> dataTableColumn_beans = new ArrayList<>();
    boolean isKeyPresent;
    boolean isSetPropertiesEnable;

    boolean allFootersEmpty = true;
    List<String> rowDataForSearching;
    //new id's
    CustomEditText et_search;
    ImageView iv_pdf, iv_xls;
    RelativeLayout rl_paging;
    Spinner sp_pagingsize;
    LinearLayout ll_pagingbts,ll_search;
    ImageButton iv_previous, iv_next;
    TextView tv_page;
    LinkedHashMap<String, List<String>> bodyDataLHM;
    List<String> headerColNames = new ArrayList<>();
    //List<List<String>> llBodyData=new ArrayList<>();
    int currentPage = 0;
    private CustomTextView tv_displayName, tv_hint,ct_showText;

    public DataTableControl(Activity context, ControlObject controlObject, boolean subFormFlag, int subFormPos, String subFormName) {
        this.context = context;
        this.controlObject = controlObject;
        this.subFormFlag = subFormFlag;
        this.subFormPos = subFormPos;
        this.subFormName = subFormName;
        initViews();
    }

    private void initViews() {
        rowDataForSearching = new ArrayList<>();
        linearLayout = new LinearLayout(context);
        linearLayout.setTag(controlObject.getControlName());
        ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
        linearLayout.setLayoutParams(ImproveHelper.layout_params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
            if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                rView = lInflater.inflate(R.layout.control_data_table_six, null);
            } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                rView = lInflater.inflate(R.layout.control_data_table_seven, null);
            } else {
//                rView = lInflater.inflate(R.layout.control_data_table, null);
                rView = lInflater.inflate(R.layout.control_data_table_default, null);
            }
        } else {
            rView = lInflater.inflate(R.layout.control_data_table_default, null);
        }

        /*Display Id's*/
        ll_displayName = rView.findViewById(R.id.ll_displayName);
        ll_main_inside = rView.findViewById(R.id.ll_main_inside);
        ll_control_ui = rView.findViewById(R.id.ll_control_ui);
        ll_data_table_main = rView.findViewById(R.id.ll_data_table_main);
        tv_displayName = rView.findViewById(R.id.tv_displayName);
        tv_hint = rView.findViewById(R.id.tv_hint);
        /*Table id's*/
        ll_search = rView.findViewById(R.id.ll_search);
        et_search = rView.findViewById(R.id.et_search);
        iv_pdf = rView.findViewById(R.id.iv_pdf);
        iv_xls = rView.findViewById(R.id.iv_xls);
        ll_table = rView.findViewById(R.id.ll_table);
        tl_header = rView.findViewById(R.id.tl_header);
        tl_body = rView.findViewById(R.id.tl_body);
        tl_footer = rView.findViewById(R.id.tl_footer);
        rl_paging = rView.findViewById(R.id.rl_paging);
        sp_pagingsize = rView.findViewById(R.id.sp_pagingsize);
        ll_pagingbts = rView.findViewById(R.id.ll_pagingbts);
        iv_previous = rView.findViewById(R.id.iv_previous);
        iv_next = rView.findViewById(R.id.iv_next);
        tv_page = rView.findViewById(R.id.tv_page);
        ct_showText = rView.findViewById(R.id.ct_showText);

        setControlValues();

        linearLayout.addView(rView);

    }

    private void setListeners() {
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
        sp_pagingsize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ll_pagingbts.setVisibility(View.GONE);
                } else {
                    ll_pagingbts.setVisibility(View.VISIBLE);

                }
                paging();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        iv_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneratePDFReport generatePDFReport = new GeneratePDFReport();
                File filePath = getOutputFilePath(actionObject.getSelect_FormName(), ".pdf");
                generatePDFReport.setHeaderData(dataTableColumn_beans, controlObject);
                generatePDFReport.setBodyData(RealmDBHelper.getTableDataBaseOnDataTableColBean(context, actionObject.getActionId(), dataTableColumn_beans));
                boolean pdfStatus = generatePDFReport.createPDF(context, actionObject.getSelect_FormName(), filePath);
                if (pdfStatus) {
                    ImproveHelper improveHelper = new ImproveHelper();
                    improveHelper.openFile(filePath.getAbsolutePath(), context);
                } else {
                    ImproveHelper.showToast(context, "Failed To Generate PDF Report! Please Try Again.");
                }
            }
        });

        iv_xls.setOnClickListener(view -> excelSheetFromData());
    }

    private File getOutputFilePath(String fileName, String fileType) {
        File createFile;
        File fileStorage = ImproveHelper.createFolder(context, "ImproveUserFiles/CreateFiles");
        //String filePath = fileName.replace(" ","_") + "_" + System.currentTimeMillis() + fileType;
        String filePath = fileName.replace(" ", "_") + fileType;
        createFile = new File(fileStorage, filePath);//Create Output file
        return createFile;
    }

    private void excelSheetFromData(){
        //excelSheets();
        File filePath = getOutputFilePath("excel" + actionObject.getSelect_FormName(), ".xls");

        List<List<String>> llTableData= RealmDBHelper.getTableDataBaseOnDataTableColBean(context, actionObject.getActionId(), dataTableColumn_beans);
        boolean isExcelGenerated = ExcelUtils.exportDataIntoWorkbook(context,
                filePath, actionObject.getSelect_FormName(), llTableData, dataTableColumn_beans, controlObject);
        if (isExcelGenerated) {
            ImproveHelper improveHelper = new ImproveHelper();
            improveHelper.openFile(filePath.getAbsolutePath(), context);
        } else {

        }
    }

    private void paging() {
        //  if (actionObject != null && actionObject.getSelect_FormName() != null) {
        //bodyDataLHM = RealmDBHelper.getTableDataBaseOnDataTableColBeanInLinkedHashMap(context, actionObject.getSelect_FormName(), dataTableColumn_beans);
        //rows = bodyDataLHM.get(dataTableColumn_beans.get(0).getColumnName().toLowerCase()) != null ? bodyDataLHM.get(dataTableColumn_beans.get(0).getColumnName().toLowerCase()).size() : bodyDataLHM.get(dataTableColumn_beans.get(0).getColumnName()).size();
        if (!isSetPropertiesEnable && (sp_pagingsize.getSelectedItem().equals("All") || rows == 0)) {
            setBodyData();
        } else {
            if (sp_pagingsize.getSelectedItem().equals("All") || rows == 0) {
                return;
            }
            int totalRowsInPage = Integer.parseInt(sp_pagingsize.getSelectedItem().toString());
            int totalPages = rows / totalRowsInPage;// 0 or 1 means 1 page
            int totalRecord = totalPages * totalRowsInPage;
            if (totalRecord < rows) {
                totalPages = totalPages + 1;
            }
            if (totalPages == 0 || totalPages == 1) {
                ll_pagingbts.setVisibility(View.GONE);
                setBodyData();
            } else {
                ll_pagingbts.setVisibility(View.VISIBLE);
                currentPage = 1;
                iv_previous.setVisibility(View.GONE);
                loadTableAccordingToPage(totalRowsInPage, totalPages);
                int finalTotalPages = totalPages;
                iv_previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentPage == 1) {
                            iv_previous.setVisibility(View.GONE);
                        } else {
                            currentPage--;
                            iv_next.setVisibility(View.VISIBLE);
                            loadTableAccordingToPage(totalRowsInPage, finalTotalPages);
                        }
                    }
                });
                iv_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentPage == finalTotalPages) {
                            iv_next.setVisibility(View.GONE);
                        } else {
                            iv_previous.setVisibility(View.VISIBLE);
                            currentPage++;
                            loadTableAccordingToPage(totalRowsInPage, finalTotalPages);
                        }
                    }
                });
            }

        }
        // }

    }

    private void searchTable(String searchStr) {
        if (searchStr.length() == 0) {
            tl_footer.setVisibility(View.VISIBLE);
        } else {
            tl_footer.setVisibility(View.GONE);
        }
        for (int i = 0; i < rowDataForSearching.size(); i++) {
            View rowView = tl_body.getChildAt(i);
            if (rowDataForSearching.get(i).toLowerCase().contains(searchStr.toLowerCase())) {
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
        } else {
            ll_displayName.setVisibility(View.VISIBLE);
        }

        if (controlObject.isDataTable_EnableSearch()) {
            ll_search.setVisibility(View.VISIBLE);
            et_search.setVisibility(View.VISIBLE);
        } else {
            et_search.setVisibility(View.GONE);
            ll_search.setVisibility(View.GONE);
        }
        ll_pagingbts.setVisibility(View.GONE);
        if (controlObject.isDataTable_isPaging()) {
            rl_paging.setVisibility(View.VISIBLE);
        } else {
            rl_paging.setVisibility(View.GONE);
        }

        if (controlObject.isDownloadPDF()) {
            iv_pdf.setVisibility(View.VISIBLE);
        } else {
            iv_pdf.setVisibility(View.GONE);
        }
        if (controlObject.isDownloadExcel()) {
            iv_xls.setVisibility(View.VISIBLE);
        } else {
            iv_xls.setVisibility(View.GONE);
        }

        if(controlObject.isInvisible()){
            setVisibility(false);
        }

        setDisplaySettings(context, tv_displayName, controlObject);
    }

    //Working
/*
    private void applyBorderForHeaderAndBodyAndFooter(FrameLayout frameLayout, CustomTextView textView,
                                                      int rowIndex, int colIndex, boolean headerRow, boolean footerRow, DataTableColumn_Bean dtb) {
        try {
            //bordertype
            String borderType = controlObject.getDataTable_BorderType() == null ? "Full Border" : controlObject.getDataTable_BorderType();
            String borderColor = controlObject.getDataTable_BorderColor() == null ? "#000000" : controlObject.getDataTable_BorderColor();
            int borderThickness = controlObject.getDataTable_BorderThickness() == null ? 3 : Integer.parseInt(controlObject.getDataTable_BorderThickness());
            String colBorder = controlObject.getDataTable_colBorder() == null ? "No" : controlObject.getDataTable_colBorder();
//            ll_table.setBackgroundColor(Color.parseColor(borderColor));
            ll_table.setPadding(borderThickness, borderThickness, borderThickness, borderThickness);

            ll_table.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_default_transparent_bg));
            GradientDrawable drawable = (GradientDrawable) ll_table.getBackground();
            drawable.setColor(Color.parseColor(borderColor));
//            dynamicBorderColorChange(ll_table,1,borderColor);

//            dynamicBorderColorChange();
            //borderColor="#000000";
            //borderThickness=8;
            //borderType = "Full Border"; 1
            //borderType = "Horizontal Border"; 2
            //borderType = "Vertical Border"; 3
            //borderType = "Inside Border"; 4
            //borderType = "Outside Border"; 5
            //borderType = "No Border"; 6
            if (borderType.equalsIgnoreCase("Full Border") || borderType.equalsIgnoreCase("1")) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_bottom));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(borderThickness, borderThickness, borderThickness, borderThickness);
                textView.setLayoutParams(layoutParams);
            } else if (borderType.equalsIgnoreCase("Horizontal Border") || borderType.equalsIgnoreCase("2")) {
                if (rowIndex == rows && footerRow) {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == rows - 1 && !footerRow) {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                }

            } else if (borderType.equalsIgnoreCase("Vertical Border") || borderType.equalsIgnoreCase("3")) {
                if (colIndex == (headerColNames.size() - 1)) {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, 0);
                    textView.setLayoutParams(layoutParams);
                }
            } else if (borderType.equalsIgnoreCase("Inside Border") || borderType.equalsIgnoreCase("4")) {
                ll_table.setPadding(0, 0, 0, 0);
                if (rowIndex == rows && colIndex == headerColNames.size() - 1 && footerRow) {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == rows && footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == rows - 1 && colIndex == headerColNames.size() - 1 && !footerRow) {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == rows - 1 && !footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (colIndex == headerColNames.size() - 1) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, borderThickness);
                    textView.setLayoutParams(layoutParams);
                }
            } else if (borderType.equalsIgnoreCase("Outside Border") || borderType.equalsIgnoreCase("5")) {
                if (rowIndex == 0 && colIndex == 0 && headerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left_top));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(borderThickness, borderThickness, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == 0 && colIndex == headerColNames.size() - 1 && headerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_top));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, borderThickness, borderThickness, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == 0 && headerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_top));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, borderThickness, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == (rows) && colIndex == 0 && footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(borderThickness, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == (rows) && colIndex == headerColNames.size() - 1 && footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == (rows - 1) && colIndex == 0 && !footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(borderThickness, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == (rows - 1) && colIndex == headerColNames.size() - 1 && !footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else if (colIndex == 0) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(borderThickness, 0, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (colIndex == headerColNames.size() - 1) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == (rows) && footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == (rows - 1) && !footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    textView.setLayoutParams(layoutParams);
                }

            } else {
                //No Border
                ll_table.setPadding(0, 0, 0, 0);
                frameLayout.setBackgroundResource(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                textView.setLayoutParams(layoutParams);
            }

            //header
            if (headerRow && colBorder.equalsIgnoreCase("no")) {
                frameLayout.setBackgroundResource(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                textView.setLayoutParams(layoutParams);
            }


            //footer
            if (footerRow && textView.getVisibility() == View.INVISIBLE) {
                textView.setVisibility(View.VISIBLE);
                if (colIndex == 0) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left_top));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(borderThickness, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else if (colIndex == headerColNames.size() - 1) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_top));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                }
            }

            // Header BG
            if (rowIndex == 0 && colIndex == 0 && headerRow) { // header 0 pos
                if (borderType.equalsIgnoreCase("Outside Border") || borderType.equalsIgnoreCase("5")) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(borderThickness, borderThickness, 0, 0);
                    textView.setLayoutParams(layoutParams);
                }
                textView.setBackground(context.getResources().getDrawable(R.drawable.only_top_left_rounded_corners_default_gray_bg));
                onlyHeaderColor(textView, dtb);
            } else if (rowIndex == 0 && colIndex == headerColNames.size() - 1 && headerRow) { //header last pos
                if (borderType.equalsIgnoreCase("Outside Border") || borderType.equalsIgnoreCase("5")) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, borderThickness, borderThickness, 0);
                    textView.setLayoutParams(layoutParams);
                }
                textView.setBackgroundResource(R.drawable.only_top_right_rounded_corners_default_gray_bg);
                onlyHeaderColor(textView, dtb);
            } else if (headerRow) {
                if (borderType.equalsIgnoreCase("Outside Border") || borderType.equalsIgnoreCase("5")) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, borderThickness, 0, 0);
                    textView.setLayoutParams(layoutParams);
                }
            }

            // Body BG
            if(controlObject.isHideColumnNames()) {
                if (rowIndex == 0 && !footerRow) { // First Row
                    if (colIndex == 0) {
//                    Log.d(TAG, "applyBorderIndexes: "+" ColIndex"+colIndex+" RowIndex"+rowIndex + " Rows "+rows);
                        if (borderType.equalsIgnoreCase("No Border") || borderType.equalsIgnoreCase("6")) {
                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                            layoutParams.setMargins(0, 0, 0, 0);
                            textView.setLayoutParams(layoutParams);
                        }
                        textView.setBackground(context.getResources().getDrawable(R.drawable.only_top_left_rounded_corners_default_gray_bg));
                        onlyBodyColor(textView, rowIndex, dtb);
                    } else if (colIndex == headerColNames.size() - 1) {
                        if (borderType.equalsIgnoreCase("No Border") || borderType.equalsIgnoreCase("6")) {
                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                            layoutParams.setMargins(0, 0, 0, 0);
                            textView.setLayoutParams(layoutParams);
                        }
                        textView.setBackground(context.getResources().getDrawable(R.drawable.only_top_right_rounded_corners_default_gray_bg));
                        onlyBodyColor(textView, rowIndex, dtb);
                    }
                }
            }
            if (rowIndex == rows - 1 && !footerRow) { // LastRow()
                if (colIndex == 0) {
                    if (borderType.equalsIgnoreCase("No Border") || borderType.equalsIgnoreCase("6")) {
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                        layoutParams.setMargins(0, 0, 0, 0);
                        textView.setLayoutParams(layoutParams);
                    }
                    textView.setBackground(context.getResources().getDrawable(R.drawable.only_bottom_left_rounded_corners_default_gray_bg));
                    onlyBodyColor(textView, rowIndex, dtb);
                } else if (colIndex == headerColNames.size() - 1) {
                    if (borderType.equalsIgnoreCase("No Border") || borderType.equalsIgnoreCase("6")) {
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                        layoutParams.setMargins(0, 0, 0, 0);
                        textView.setLayoutParams(layoutParams);
                    }
                    textView.setBackground(context.getResources().getDrawable(R.drawable.only_bottom_right_rounded_corners_default_gray_bg));
                    onlyBodyColor(textView, rowIndex, dtb);
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "applyBorderForHeaderAndBodyAndFooter: " + e);
        }

    }
*/

    private void applyBorderForHeaderAndBodyAndFooter(FrameLayout frameLayout, CustomTextView textView,
                                                      int rowIndex, int colIndex, boolean headerRow, boolean footerRow, DataTableColumn_Bean dtb, int pagingRowsCount) {
        try {
            //bordertype
            String borderType = controlObject.getDataTable_BorderType() == null ? "Full Border" : controlObject.getDataTable_BorderType();
            String borderColor = controlObject.getDataTable_BorderColor() == null ? "#000000" : controlObject.getDataTable_BorderColor();
            int borderThickness = controlObject.getDataTable_BorderThickness() == null ? 3 : Integer.parseInt(controlObject.getDataTable_BorderThickness());
            String colBorder = controlObject.getDataTable_colBorder() == null ? "No" : controlObject.getDataTable_colBorder();
//            ll_table.setBackgroundColor(Color.parseColor(borderColor));
            ll_table.setPadding(borderThickness, borderThickness, borderThickness, borderThickness);

            ll_table.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_default_transparent_bg));
            GradientDrawable drawable = (GradientDrawable) ll_table.getBackground();
            drawable.setColor(Color.parseColor(borderColor));
//            dynamicBorderColorChange(ll_table,1,borderColor);

//            dynamicBorderColorChange();
            //borderColor="#000000";
            //borderThickness=8;
            //borderType = "Full Border"; 1
            //borderType = "Horizontal Border"; 2
            //borderType = "Vertical Border"; 3
            //borderType = "Inside Border"; 4
            //borderType = "Outside Border"; 5
            //borderType = "No Border"; 6
            if (borderType.equalsIgnoreCase("Full Border") || borderType.equalsIgnoreCase("1")) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_bottom));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(borderThickness, borderThickness, borderThickness, borderThickness);
                textView.setLayoutParams(layoutParams);
            } else if (borderType.equalsIgnoreCase("Horizontal Border") || borderType.equalsIgnoreCase("2")) {
                if (rowIndex == rows && footerRow) {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == rows - 1 && !footerRow) {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                }

            } else if (borderType.equalsIgnoreCase("Vertical Border") || borderType.equalsIgnoreCase("3")) {
                if (colIndex == (headerColNames.size() - 1)) {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, 0);
                    textView.setLayoutParams(layoutParams);
                }
            } else if (borderType.equalsIgnoreCase("Inside Border") || borderType.equalsIgnoreCase("4")) {
                ll_table.setPadding(0, 0, 0, 0);
                if (rowIndex == rows && colIndex == headerColNames.size() - 1 && footerRow) {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == rows && footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == rows - 1 && colIndex == headerColNames.size() - 1 && !footerRow) {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == rows - 1 && !footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (colIndex == headerColNames.size() - 1) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, borderThickness);
                    textView.setLayoutParams(layoutParams);
                }
            } else if (borderType.equalsIgnoreCase("Outside Border") || borderType.equalsIgnoreCase("5")) {
                if (rowIndex == 0 && colIndex == 0 && headerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left_top));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(borderThickness, borderThickness, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == 0 && colIndex == headerColNames.size() - 1 && headerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_top));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, borderThickness, borderThickness, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == 0 && headerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_top));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, borderThickness, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == (rows) && colIndex == 0 && footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(borderThickness, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == (rows) && colIndex == headerColNames.size() - 1 && footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == (rows - 1) && colIndex == 0 && !footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(borderThickness, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == (rows - 1) && colIndex == headerColNames.size() - 1 && !footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else if (colIndex == 0) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(borderThickness, 0, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (colIndex == headerColNames.size() - 1) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, 0);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == (rows) && footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else if (rowIndex == (rows - 1) && !footerRow) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_bottom));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    textView.setLayoutParams(layoutParams);
                }

            } else {
                //No Border
                ll_table.setPadding(0, 0, 0, 0);
                frameLayout.setBackgroundResource(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                textView.setLayoutParams(layoutParams);
            }

            //header
            if (headerRow && colBorder.equalsIgnoreCase("no")) {
                frameLayout.setBackgroundResource(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                textView.setLayoutParams(layoutParams);
            }


            //footer
            if (footerRow && textView.getVisibility() == View.INVISIBLE) {
                textView.setVisibility(View.VISIBLE);
                if (colIndex == 0) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left_top));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(borderThickness, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else if (colIndex == headerColNames.size() - 1) {
                    //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_top));
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, borderThickness, borderThickness);
                    textView.setLayoutParams(layoutParams);
                } else {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, borderThickness);
                    textView.setLayoutParams(layoutParams);
                }
            }

            // Header BG
            if (rowIndex == 0 && colIndex == 0 && headerRow) { // header 0 pos
                if (borderType.equalsIgnoreCase("Outside Border") || borderType.equalsIgnoreCase("5")) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(borderThickness, borderThickness, 0, 0);
                    textView.setLayoutParams(layoutParams);
                }
                textView.setBackground(context.getResources().getDrawable(R.drawable.only_top_left_rounded_corners_default_gray_bg));
                onlyHeaderColor(textView, dtb);
            } else if (rowIndex == 0 && colIndex == headerColNames.size() - 1 && headerRow) { //header last pos
                if (borderType.equalsIgnoreCase("Outside Border") || borderType.equalsIgnoreCase("5")) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, borderThickness, borderThickness, 0);
                    textView.setLayoutParams(layoutParams);
                }
                textView.setBackgroundResource(R.drawable.only_top_right_rounded_corners_default_gray_bg);
                onlyHeaderColor(textView, dtb);
            } else if (headerRow) {
                if (borderType.equalsIgnoreCase("Outside Border") || borderType.equalsIgnoreCase("5")) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, borderThickness, 0, 0);
                    textView.setLayoutParams(layoutParams);
                }
            }

            // Body BG
            if (controlObject.isHideColumnNames()) {
                if (rowIndex == 0 && !footerRow) { // First Row
                    if (colIndex == 0) {
//                    Log.d(TAG, "applyBorderIndexes: "+" ColIndex"+colIndex+" RowIndex"+rowIndex + " Rows "+rows);
                        if (borderType.equalsIgnoreCase("No Border") || borderType.equalsIgnoreCase("6")) {
                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                            layoutParams.setMargins(0, 0, 0, 0);
                            textView.setLayoutParams(layoutParams);
                        }
                        textView.setBackground(context.getResources().getDrawable(R.drawable.only_top_left_rounded_corners_default_gray_bg));
                        onlyBodyColor(textView, rowIndex, dtb);
                    } else if (colIndex == headerColNames.size() - 1) {
                        if (borderType.equalsIgnoreCase("No Border") || borderType.equalsIgnoreCase("6")) {
                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                            layoutParams.setMargins(0, 0, 0, 0);
                            textView.setLayoutParams(layoutParams);
                        }
                        textView.setBackground(context.getResources().getDrawable(R.drawable.only_top_right_rounded_corners_default_gray_bg));
                        onlyBodyColor(textView, rowIndex, dtb);
                    }
                }
            }
            if (rowIndex == rows - 1 && !footerRow) { // LastRow()
                if (colIndex == 0) {
                    if (borderType.equalsIgnoreCase("No Border") || borderType.equalsIgnoreCase("6")) {
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                        layoutParams.setMargins(0, 0, 0, 0);
                        textView.setLayoutParams(layoutParams);
                    }
                    textView.setBackground(context.getResources().getDrawable(R.drawable.only_bottom_left_rounded_corners_default_gray_bg));
                    onlyBodyColor(textView, rowIndex, dtb);
                } else if (colIndex == headerColNames.size() - 1) {
                    if (borderType.equalsIgnoreCase("No Border") || borderType.equalsIgnoreCase("6")) {
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                        layoutParams.setMargins(0, 0, 0, 0);
                        textView.setLayoutParams(layoutParams);
                    }
                    textView.setBackground(context.getResources().getDrawable(R.drawable.only_bottom_right_rounded_corners_default_gray_bg));
                    onlyBodyColor(textView, rowIndex, dtb);
                }
            }

            //Paging LastRow BG
            if (controlObject.isDataTable_isPaging()) {
                if (rowIndex == pagingRowsCount - 1 && !footerRow) {
                    if (colIndex == 0) {
                        if (borderType.equalsIgnoreCase("No Border") || borderType.equalsIgnoreCase("6")) {
                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                            layoutParams.setMargins(0, 0, 0, 0);
                            textView.setLayoutParams(layoutParams);
                        }
                        textView.setBackground(context.getResources().getDrawable(R.drawable.only_bottom_left_rounded_corners_default_gray_bg));
                        onlyBodyColor(textView, rowIndex, dtb);
                    } else if (colIndex == headerColNames.size() - 1) {
                        if (borderType.equalsIgnoreCase("No Border") || borderType.equalsIgnoreCase("6")) {
                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                            layoutParams.setMargins(0, 0, 0, 0);
                            textView.setLayoutParams(layoutParams);
                        }
                        textView.setBackground(context.getResources().getDrawable(R.drawable.only_bottom_right_rounded_corners_default_gray_bg));
                        onlyBodyColor(textView, rowIndex, dtb);
                    }
                }

            }


        } catch (Exception e) {
            Log.d(TAG, "applyBorderForHeaderAndBodyAndFooter: " + e);
        }

    }


    private void headerNames() {
        List<DataTableColumn_Bean> updated_DataTableColumn_Bean = new ArrayList<>();
        headerColNames = new ArrayList<>();
        for (int dataTableCol = 0; dataTableCol < dataTableColumn_beans.size(); dataTableCol++) {
            boolean isEnabled = dataTableColumn_beans.get(dataTableCol).isColumnEnabled();
            if (isEnabled) {
                updated_DataTableColumn_Bean.add(dataTableColumn_beans.get(dataTableCol));
                headerColNames.add(dataTableColumn_beans.get(dataTableCol).getColumnName().toLowerCase());
            }
        }
        this.dataTableColumn_beans = updated_DataTableColumn_Bean;
    }

    private void setOptionSettingForHeader(CustomTextView tv_header) {
        //col width:col width,wrap context
        //col height
        //col border
        //col text size
        if (controlObject.getDataTable_colTextSize() != null) {
            tv_header.setTextSize(Float.parseFloat(controlObject.getDataTable_colTextSize()));
        }
        //col text style
        String style = controlObject.getDataTable_colTextStyle();
        if (style != null && style.equalsIgnoreCase("Bold")) {
            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
            tv_header.setTypeface(typeface_bold);
        } else if (style != null && style.equalsIgnoreCase("Italic")) {
            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
            tv_header.setTypeface(typeface_italic);
        }
        //col text color
        if (controlObject.getDataTable_colTextColor() != null) {
            tv_header.setTextColor(Color.parseColor(controlObject.getDataTable_colTextColor()));
        }
        //col text alignment
        if (controlObject.getDataTable_colAlignment() != null) {
            if (controlObject.getDataTable_colAlignment().equalsIgnoreCase("Left")) {
                tv_header.setGravity(Gravity.LEFT);
            } else if (controlObject.getDataTable_colAlignment().equalsIgnoreCase("Center")) {
                tv_header.setGravity(Gravity.CENTER);
            } else {
                tv_header.setGravity(Gravity.RIGHT);
            }
        }
        //col color
        if (controlObject.getDataTable_colColor() != null) {
            //framelayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border));
            tv_header.setBackgroundColor(Color.parseColor(controlObject.getDataTable_colColor()));
        }
    }

    private void onlyHeaderColor(CustomTextView textView, DataTableColumn_Bean dtb) {
        GradientDrawable drawable = (GradientDrawable) textView.getBackground();
        if (controlObject.getDataTable_colColor() != null) {
            drawable.setColor(Color.parseColor(controlObject.getDataTable_colColor()));
        }

        //DataTableColumn_Bean dtb
        if (dtb != null && dtb.isSettingsEdited()) {
            if (dtb.getSettings() != null && dtb.getSettings().getHeaderSettings() != null) {
                DisplaySettings headerSettings = dtb.getSettings().getHeaderSettings();
                //col color
                if (headerSettings.getBackGroundColor() != null) {
                    //tv_header.setBackgroundColor(Color.parseColor(headerSettings.getBackGroundColor()));
                    drawable.setColor(Color.parseColor(headerSettings.getBackGroundColor()));
                }
            }
        }
    }

    private void onlyBodyColor(CustomTextView textView, int rowIndex, DataTableColumn_Bean dtb) {
        GradientDrawable drawable = (GradientDrawable) textView.getBackground();
        if (controlObject.getDataTable_rowColorType() != null) {
            if (controlObject.getDataTable_rowColorType().equalsIgnoreCase("Single Color") || controlObject.getDataTable_rowColorType().equalsIgnoreCase("Single")) {
                if (controlObject.getDataTable_rowColor1() != null) {
                    drawable.setColor(Color.parseColor(controlObject.getDataTable_rowColor1()));
                }
            } else {
                if (rowIndex % 2 == 0) {
                    if (controlObject.getDataTable_rowColor1() != null) {
                        drawable.setColor(Color.parseColor(controlObject.getDataTable_rowColor1()));
                    }
                } else {
                    if (controlObject.getDataTable_rowColor2() != null) {
                        drawable.setColor(Color.parseColor(controlObject.getDataTable_rowColor2()));
                    }
                }
            }
        }
        if (dtb != null && dtb.isSettingsEdited()) {
            if (dtb.getSettings() != null && dtb.getSettings().getBodySettings() != null) {
                DisplaySettings bodySettings = dtb.getSettings().getBodySettings();
                //col color
                if (bodySettings.getBackGroundColor() != null) {
                    drawable.setColor(Color.parseColor(bodySettings.getBackGroundColor()));
                }

            }
        }
    }

    private void setAdvSettingForHeader(DataTableColumn_Bean dtb, CustomTextView tv_header) {
        if (dtb.isSettingsEdited()) {
            if (dtb.getSettings() != null && dtb.getSettings().getHeaderSettings() != null) {
                DisplaySettings headerSettings = dtb.getSettings().getHeaderSettings();
                //col text size
                if (headerSettings.getTextSize() != null) {
                    tv_header.setTextSize(Float.parseFloat(headerSettings.getTextSize()));
                }
                //col text style
                String style = headerSettings.getTextStyle();
                if (style != null && style.equalsIgnoreCase("Bold")) {
                    Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
                    tv_header.setTypeface(typeface_bold);
                } else if (style != null && style.equalsIgnoreCase("Italic")) {
                    Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
                    tv_header.setTypeface(typeface_italic);
                } else {
                    Typeface typeface_normal = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name));
                    tv_header.setTypeface(typeface_normal);
                }
                //col text alignment
                if (headerSettings.getTextAlignment() != null) {
                    if (headerSettings.getTextAlignment().equalsIgnoreCase("Left")) {
                        tv_header.setGravity(Gravity.LEFT);
                    } else if (headerSettings.getTextAlignment().equalsIgnoreCase("Center") ||
                            headerSettings.getTextAlignment().equalsIgnoreCase("Center")) {
                        tv_header.setGravity(Gravity.CENTER);
                    } else {
                        tv_header.setGravity(Gravity.RIGHT);
                    }
                }
                //col color
                if (headerSettings.getBackGroundColor() != null) {
                    tv_header.setBackgroundColor(Color.parseColor(headerSettings.getBackGroundColor()));
                }
                //col text color
                if (headerSettings.getTextColor() != null) {
                    tv_header.setTextColor(Color.parseColor(headerSettings.getTextColor()));
                }
            }
        }
    }


    private void setHeaderData() {
        //Add header cols to tablelayout
        TableRow tr_header = new TableRow(context);
        tr_header.setTag(-1);
        //add headers
        for (int dataTableCol = 0, colIndex = 0; dataTableCol < dataTableColumn_beans.size(); dataTableCol++) {
            DataTableColumn_Bean dtb = dataTableColumn_beans.get(dataTableCol);
            boolean isEnabled = dtb.isColumnEnabled();
            boolean isSortingEnable = dtb.getSettings().isEnableSorting();
            if (isEnabled) {
                LayoutInflater header_lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View header_view = null;
                if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                    header_view = header_lInflater.inflate(R.layout.item_data_table_inner_control_seven_header, null);
                } else {
                    header_view = header_lInflater.inflate(R.layout.item_data_table_inner_control_header, null);
                }
                CustomTextView tv_header = header_view.findViewById(R.id.tv_label);
                ImageView iv_sorting = header_view.findViewById(R.id.iv_sorting);
                FrameLayout framelayout = header_view.findViewById(R.id.framelayout);
                iv_sorting.setTag(dataTableColumn_beans.get(dataTableCol).getColumnName());
                tv_header.setText(dataTableColumn_beans.get(dataTableCol).getHeaderName());
                tv_header.setTag(controlObject.getControlName() + "_" + dataTableColumn_beans.get(dataTableCol).getColumnName());

                setOptionSettingForHeader(tv_header);
                setAdvSettingForHeader(dtb, tv_header);

                applyBorderForHeaderAndBodyAndFooter(framelayout, tv_header, 0, colIndex, true, false, dtb,-1);
                tr_header.addView(header_view);
                //col width & height
                setHeaderWidthHeight(header_view, framelayout, dtb);
                if (isSortingEnable) {
                    iv_sorting.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_unfold_more_24));
                    iv_sorting.setVisibility(View.VISIBLE);
                    iv_sorting.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("ResourceType")
                        @Override
                        public void onClick(View v) {
                            if (iv_sorting.getId() == 0) {
                                iv_sorting.setId(1);
                                // iv_sorting.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_unfold_more_24));
                                sortByCol(iv_sorting.getTag().toString(), "Asc");
                            } else {
                                iv_sorting.setId(0);
                                // iv_sorting.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_unfold_more_24));
                                sortByCol(iv_sorting.getTag().toString(), "Dsc");
                            }

                            /*PopupMenu popupMenu = new PopupMenu(context, iv_sorting);
                            popupMenu.getMenuInflater().inflate(R.menu.sort, popupMenu.getMenu());
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    sortByCol(iv_sorting.getTag().toString(), menuItem.getTitle().toString());
                                    return true;
                                }
                            });
                            popupMenu.show();*/
                        }
                    });
                }
                colIndex++;
            }
        }
        if (controlObject.isHideColumnNames()) {
            tr_header.setVisibility(View.GONE);
        }
        tl_header.addView(tr_header);
    }

    private void sortByCol(String sortColName, String sortingType) {
        bodyDataLHM = RealmDBHelper.getTableDataBaseOnDataTableColBeanWithSortInLinkedHashMap(context, actionObject.getActionId(), dataTableColumn_beans, sortColName, sortingType);
        setBodyData();
    }

    private void setHeaderWidthHeight(View view, FrameLayout frameLayout, DataTableColumn_Bean dtb) {
        try {
            TableRow.LayoutParams tr_params = new TableRow.LayoutParams();
            ViewGroup.MarginLayoutParams params =
                    (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;
            //Control obj wise width & height for all header cells
            //col width
            if (controlObject.getDataTable_colWidthType() != null) {
                if (controlObject.getDataTable_colWidthType().equalsIgnoreCase("Wrap Content")) {
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    tr_params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                } else if (controlObject.getDataTable_colWidthType().equalsIgnoreCase("Screen Fit")) {
                    //float floatWidth = Float.parseFloat(dataTableColumn_beans.get(headerIndex).getColumnWidth());
                    float floatWidth = 100 / headerColNames.size();
                    params.width = (int) (floatWidth * screenWidth / 100);
                    tr_params.width = (int) (floatWidth * screenWidth / 100);
                } else {
                    //int colWidthSize=Integer.parseInt(dataTableColumn_beans.get(headerIndex).getColumnWidth());
                    int colWidthSize = Integer.parseInt(controlObject.getDataTable_colWidthSize());
                    params.width = pxToDP(colWidthSize);
                    tr_params.width = pxToDP(colWidthSize);
                }
            }
            //Adv col width
            if (dtb != null && dtb.isSettingsEdited() && dtb.getSettings() != null) {
                if (dtb.getSettings().isWrapContent()) {
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    tr_params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                } else {
                    if (dtb.getSettings().getColumnWidth() != null) {
                        int colWidthSize = Integer.parseInt(dtb.getSettings().getColumnWidth());
                        params.width = pxToDP(colWidthSize);
                        tr_params.width = pxToDP(colWidthSize);
                    }
                }
            }
            //col height
            if (controlObject.getDataTable_colHeightType() != null) {
                if (controlObject.getDataTable_colHeightType().equalsIgnoreCase("Wrap Content")) {
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    tr_params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                } else {
                    int colHeightSize = Integer.parseInt(controlObject.getDataTable_colHeightSize());
                    params.height = pxToDP(colHeightSize);
                    tr_params.height = pxToDP(colHeightSize);
                }
            }
            frameLayout.setLayoutParams(tr_params);
            view.setLayoutParams(params);

            view.requestLayout();
        } catch (Exception e) {
            ImproveHelper.improveException(context, "DataTable", "setWidth", e);
        }
    }

    private void loadTableAccordingToPage(int totalRows, int totalPages) {
        tv_page.setText("Page " + currentPage + " of " + totalPages);
        tl_body.removeAllViews();
        rowDataForSearching.clear();
        //rows = bodyDataLHM.get(dataTableColumn_beans.get(0).getColumnName().toLowerCase()) != null ? bodyDataLHM.get(dataTableColumn_beans.get(0).getColumnName().toLowerCase()).size() : bodyDataLHM.get(dataTableColumn_beans.get(0).getColumnName()).size();
        int temp = currentPage - 1;
        int index = currentPage == 1 ? 0 : (temp * totalRows);
        for (int i = index, k = 0; k < totalRows; i++, k++) {
            if (i < rows) {
                TableRow tr_body = new TableRow(context);
                String rowStr = "";
                for (int dataTableCol = 0, colIndex = 0; dataTableCol < dataTableColumn_beans.size(); dataTableCol++) {
                    DataTableColumn_Bean dtb = dataTableColumn_beans.get(dataTableCol);
                    boolean isEnabled = dataTableColumn_beans.get(dataTableCol).isColumnEnabled();
                    if (isEnabled) {
                        String rowData = bodyDataLHM.get(dataTableColumn_beans.get(dataTableCol).getColumnName()).get(i).trim();
                        rowStr = rowStr + rowData;
                        tr_body.setTag(dataTableColumn_beans.get(dataTableCol).getColumnName());
                        setRowData(dataTableCol, colIndex, i, tr_body, rowData, dtb, totalRows);
                        colIndex++;
                    }
                }
                rowDataForSearching.add(rowStr);
                tl_body.addView(tr_body);
            }
        }
    }

    private void setBodyData() {
        isSetPropertiesEnable = false;
        tl_body.removeAllViews();
        rowDataForSearching.clear();
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            TableRow tr_body = new TableRow(context);
            String rowStr = "";
            for (int dataTableCol = 0, colIndex = 0; dataTableCol < dataTableColumn_beans.size(); dataTableCol++) {
                DataTableColumn_Bean dtb = dataTableColumn_beans.get(dataTableCol);
                boolean isEnabled = dataTableColumn_beans.get(dataTableCol).isColumnEnabled();
                if (isEnabled) {
                    String rowData = bodyDataLHM.get(dataTableColumn_beans.get(dataTableCol).getColumnName().toLowerCase()).get(rowIndex).trim();
                    rowStr = rowStr + rowData;
                    // tr_body.setTag(dataTableColumn_beans.get(dataTableCol).getColumnName());
                    setRowData(dataTableCol, colIndex, rowIndex, tr_body, rowData, dtb, -1);
                    colIndex++;
                }
            }
            rowDataForSearching.add(rowStr);
            tl_body.addView(tr_body);
        }
        //ParticularRowsColoring
        particularRowColoring();
    }

    private int viewWidth(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    private void setBodyWidthHeight(View view, FrameLayout ll_cell, int colIndex, DataTableColumn_Bean dtb) {
        try {
            TableRow.LayoutParams tr_params = new TableRow.LayoutParams();
            ViewGroup.MarginLayoutParams params =
                    (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;
            //get col width for cell width from Option
            if (controlObject.getDataTable_colWidthType() != null) {
                if (controlObject.getDataTable_colWidthType().equalsIgnoreCase("Wrap Content")) {
                    //params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    //tr_params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    int width = viewWidth(((TableRow) this.tl_header.getChildAt(0)).getChildAt(colIndex));
                    params.width = width;
                    tr_params.width = width;
                } else if (controlObject.getDataTable_colWidthType().equalsIgnoreCase("Screen Fit")) {
                    //float floatWidth = Float.parseFloat(dataTableColumn_beans.get(headerIndex).getColumnWidth());
                    float floatWidth = 100 / headerColNames.size();
                    params.width = (int) (floatWidth * screenWidth / 100);
                    tr_params.width = (int) (floatWidth * screenWidth / 100);
                } else {
                    //int colWidthSize=Integer.parseInt(dataTableColumn_beans.get(headerIndex).getColumnWidth());
                    int colWidthSize = Integer.parseInt(controlObject.getDataTable_colWidthSize());
                    params.width = pxToDP(colWidthSize);
                    tr_params.width = pxToDP(colWidthSize);
                }
            }
            //From Adv Settings
            //Adv col width
            if (dtb != null && dtb.isSettingsEdited() && dtb.getSettings() != null) {
                if (dtb.getSettings().isWrapContent()) {
                    int width = viewWidth(((TableRow) this.tl_header.getChildAt(0)).getChildAt(colIndex));
                    params.width = width;
                    tr_params.width = width;
                } else {
                    if (dtb.getSettings().getColumnWidth() != null) {
                        int colWidthSize = Integer.parseInt(dtb.getSettings().getColumnWidth());
                        params.width = pxToDP(colWidthSize);
                        tr_params.width = pxToDP(colWidthSize);
                    }
                }
            }
            //row height
            if (controlObject.getDataTable_rowHeigthType() != null) {
                if (controlObject.getDataTable_rowHeigthType().equalsIgnoreCase("Wrap Content")) {
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    tr_params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                } else {
                    int rowHeightSize = Integer.parseInt(controlObject.getDataTable_rowHeightSize());
                    params.height = pxToDP(rowHeightSize);
                    tr_params.height = pxToDP(rowHeightSize);
                }
            }
            ll_cell.setLayoutParams(tr_params);
            view.setLayoutParams(params);
            view.requestLayout();
        } catch (Exception e) {
            ImproveHelper.improveException(context, "DataTable", "setRowWidthHeight", e);
        }
    }

    private void setOptionSettingForBody(CustomTextView textView, int rowIndex) {
        //row height
        //row text size
        if (controlObject.getDataTable_rowTextSize() != null) {
            textView.setTextSize(Float.parseFloat(controlObject.getDataTable_rowTextSize()));
        }
        //row text style
        String style = controlObject.getDataTable_rowTextStyle();
        if (style != null && style.equalsIgnoreCase("Bold")) {
            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
            textView.setTypeface(typeface_bold);
        } else if (style != null && style.equalsIgnoreCase("Italic")) {
            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
            textView.setTypeface(typeface_italic);
        }
        //row text color
        if (controlObject.getDataTable_rowTextColor() != null) {
            textView.setTextColor(Color.parseColor(controlObject.getDataTable_rowTextColor()));
        }

        //row text alignment
        if (controlObject.getDataTable_rowAlignment() != null) {
            if (controlObject.getDataTable_rowAlignment().equalsIgnoreCase("Left")) {
                textView.setGravity(Gravity.LEFT);
            } else if (controlObject.getDataTable_rowAlignment().equalsIgnoreCase("Center")) {
                textView.setGravity(Gravity.CENTER);
            } else {
                textView.setGravity(Gravity.RIGHT);
            }
        }

        //row color
        if (controlObject.getDataTable_rowColorType() != null) {
            //ll_cell.setBackground(ContextCompat.getDrawable(context, R.drawable.border));
            if (controlObject.getDataTable_rowColorType().equalsIgnoreCase("Single Color") || controlObject.getDataTable_rowColorType().equalsIgnoreCase("Single")) {
                if (controlObject.getDataTable_rowColor1() != null) {
                    textView.setBackgroundColor(Color.parseColor(controlObject.getDataTable_rowColor1()));
                }
            } else {
                if (rowIndex % 2 == 0) {
                    if (controlObject.getDataTable_rowColor1() != null) {
                        textView.setBackgroundColor(Color.parseColor(controlObject.getDataTable_rowColor1()));
                    }
                } else {
                    if (controlObject.getDataTable_rowColor2() != null) {
                        textView.setBackgroundColor(Color.parseColor(controlObject.getDataTable_rowColor2()));
                    }
                }
            }
        }

        //particular row coloring : it done after complete loading table
    }

    private void setAdvSettingForBody(DataTableColumn_Bean dtb, CustomTextView tv_body) {
        if (dtb.isSettingsEdited()) {
            if (dtb.getSettings() != null && dtb.getSettings().getBodySettings() != null) {
                DisplaySettings bodySettings = dtb.getSettings().getBodySettings();
                //col text size
                if (bodySettings.getTextSize() != null) {
                    tv_body.setTextSize(Float.parseFloat(bodySettings.getTextSize()));
                }
                //col text style
                String style = bodySettings.getTextStyle();
                if (style != null && style.equalsIgnoreCase("Bold")) {
                    Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
                    tv_body.setTypeface(typeface_bold);
                } else if (style != null && style.equalsIgnoreCase("Italic")) {
                    Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
                    tv_body.setTypeface(typeface_italic);
                } else {
                    Typeface typeface_normal = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name));
                    tv_body.setTypeface(typeface_normal);
                }
                //col text alignment
                if (bodySettings.getTextAlignment() != null) {
                    if (bodySettings.getTextAlignment().equalsIgnoreCase("Left")) {
                        tv_body.setGravity(Gravity.LEFT);
                    } else if (bodySettings.getTextAlignment().equalsIgnoreCase("Centre") ||
                            bodySettings.getTextAlignment().equalsIgnoreCase("Center")) {
                        tv_body.setGravity(Gravity.CENTER);
                    } else {
                        tv_body.setGravity(Gravity.RIGHT);
                    }
                }
                //col color
                if (bodySettings.getBackGroundColor() != null) {
                    tv_body.setBackgroundColor(Color.parseColor(bodySettings.getBackGroundColor()));
                }
                //col text color
                if (bodySettings.getTextColor() != null) {
                    tv_body.setTextColor(Color.parseColor(bodySettings.getTextColor()));
                }
            }
        }
    }

    private void setRowData(int dataTableColl, int colIndex, int rowIndex, TableRow tr_body, String rowData, DataTableColumn_Bean dtb, int pagingRowsCount) {
        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View body_view = null;
        if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
            body_view = lInflater.inflate(R.layout.item_data_table_inner_control_seven, null);
        } else {
            body_view = lInflater.inflate(R.layout.item_data_table_inner_control, null);
        }
        tr_body.setTag(body_view);
        body_view.setTag(dtb.getColumnName());
        FrameLayout frameLayout = body_view.findViewById(R.id.framelayout);
        CustomTextView textView = body_view.findViewById(R.id.ll_control_view);
        textView.setId(colIndex);
        textView.setTag(String.valueOf(rowIndex));
        String value = String.valueOf(rowData);
        textView.setText(value);

        setOptionSettingForBody(textView, rowIndex);
        setAdvSettingForBody(dtb, textView);

        applyBorderForHeaderAndBodyAndFooter(frameLayout, textView, rowIndex, colIndex, false, false, dtb, pagingRowsCount);
        tr_body.addView(body_view);
        //row height
        setBodyWidthHeight(body_view, frameLayout, colIndex, dtb);

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

    private void setFooterData() {
        //Footer
        TableRow tr_footer = new TableRow(context);
        //tl_footer.setBackgroundColor(Color.WHITE);
        //TableLayout.LayoutParams layoutParams = (TableLayout.LayoutParams) tl_footer.getLayoutParams();
        //layoutParams.setMargins(1, 1, 1, 1);
        //tl_footer.setLayoutParams(layoutParams);
        for (int j = 0; j < dataTableColumn_beans.size(); j++) {
            boolean isEnabled = dataTableColumn_beans.get(j).isColumnEnabled();
            if (isEnabled) {
                footerRow(j, rows, tr_footer);
            }
        }
        if (!allFootersEmpty) {
            tr_footer.setTag("Footer");
            tl_footer.addView(tr_footer);
        }
    }

    private void setAdvSettingForFooter(DataTableColumn_Bean dtb, CustomTextView tv_footer) {

        if (dtb.isSettingsEdited()) {
            if (dtb.getSettings() != null && dtb.getSettings().getFooterSettings() != null) {
                DisplaySettings footerSettings = dtb.getSettings().getFooterSettings();
                //col text size
                if (footerSettings.getTextSize() != null) {
                    tv_footer.setTextSize(Float.parseFloat(footerSettings.getTextSize()));
                }
                //col text style
                String style = footerSettings.getTextStyle();
                if (style != null && style.equalsIgnoreCase("Bold")) {
                    Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
                    tv_footer.setTypeface(typeface_bold);
                } else if (style != null && style.equalsIgnoreCase("Italic")) {
                    Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
                    tv_footer.setTypeface(typeface_italic);
                } else {
                    Typeface typeface_normal = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name));
                    tv_footer.setTypeface(typeface_normal);
                }
                //col text alignment
                if (footerSettings.getTextAlignment() != null) {
                    if (footerSettings.getTextAlignment().equalsIgnoreCase("Left")) {
                        tv_footer.setGravity(Gravity.LEFT);
                    } else if (footerSettings.getTextAlignment().equalsIgnoreCase("Centre") ||
                            footerSettings.getTextAlignment().equalsIgnoreCase("Center")) {
                        tv_footer.setGravity(Gravity.CENTER);
                    } else {
                        tv_footer.setGravity(Gravity.RIGHT);
                    }
                }
                //col color
                if (footerSettings.getBackGroundColor() != null) {
                    tv_footer.setBackgroundColor(Color.parseColor(footerSettings.getBackGroundColor()));
                }
                //col text color
                if (footerSettings.getTextColor() != null) {
                    tv_footer.setTextColor(Color.parseColor(footerSettings.getTextColor()));
                }
            }
        }

    }

    private void footerRow(int columnIndex, int rowIndex, TableRow tr_footer) {
        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View footer_view = null;
        if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
            footer_view = lInflater.inflate(R.layout.item_data_table_inner_control_seven, null);
        } else {
            footer_view = lInflater.inflate(R.layout.item_data_table_inner_control, null);
        }
        footer_view.setTag(dataTableColumn_beans.get(columnIndex).getColumnName());
        CustomTextView textView = footer_view.findViewById(R.id.ll_control_view);
        FrameLayout frameLayout = footer_view.findViewById(R.id.framelayout);
        textView.setId(columnIndex);
        textView.setTag(String.valueOf(rowIndex));

        float total = 0.0f, avg = 0.0f;
        if (rowIndex == rows) {
            String footerFunction = dataTableColumn_beans.get(columnIndex).getFooterFunction();
            textView.setBackgroundResource(0);
            switch (footerFunction) {
                case "Summation":
                    allFootersEmpty = false;
                    total = colTotal(dataTableColumn_beans.get(columnIndex).getColumnName());
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
                    textView.setText("");
                    textView.setVisibility(View.INVISIBLE);
                    break;
            }

            setAdvSettingForFooter(dataTableColumn_beans.get(columnIndex), textView);
            applyBorderForHeaderAndBodyAndFooter(frameLayout, textView, rowIndex, columnIndex, false, true, dataTableColumn_beans.get(columnIndex),-1);
            //tr_footer.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tr_footer.addView(footer_view);
            //row height
            setBodyWidthHeight(footer_view, frameLayout, columnIndex, dataTableColumn_beans.get(columnIndex));
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

    private float colTotal(String columnName) {
        return RealmDBHelper.getTotalFromParticularCol(context, actionObject.getActionId(), columnName);
    }

    public void setDataTableData(ActionWithoutCondition_Bean actionWithoutCondition_bean, LinkedHashMap<String, List<String>> resultSetData) {
        actionObject = actionWithoutCondition_bean;
        dataTableColumn_beans = actionWithoutCondition_bean.getDataTableColumn_beanList();
        bodyDataLHM = resultSetData;//RealmDBHelper.getTableDataBaseOnDataTableColBeanInLinkedHashMap(context, actionWithoutCondition_bean.getSelect_FormName(), dataTableColumn_beans);
        headerNames();
        rows = bodyDataLHM.get(headerColNames.get(0)).size();

        setHeaderData();
        setBodyData();
        setFooterData();
        setListeners();
    }


    public void ClearData() {
        tl_header.removeAllViews();
        tl_body.removeAllViews();
        tl_footer.removeAllViews();
    }

    //nk check once again LinkedHashMap remove or no
    public LinkedHashMap<String, List<String>> getExistingData(List<String> columnsList, LinkedHashMap<String, List<String>> outputMap) {
        LinkedHashMap<String, List<String>> dataMap = new LinkedHashMap<>();
        if (tl_body.getChildCount() > 0) {
            for (int i = 0; i < columnsList.size(); i++) {
                dataMap.put(columnsList.get(i), new ArrayList<>());
            }
            for (int i = 0; i < tl_body.getChildCount(); i++) {
                TableRow ll_grid_row = (TableRow) tl_body.getChildAt(i);
                if (ll_grid_row.getTag() != null) {
                    for (int j = 0; j < columnsList.size(); j++) {
                        String key = columnsList.get(j);
                        for (int k = 0; k < ll_grid_row.getChildCount(); k++) {
                            View columnView = ll_grid_row.getChildAt(k);
//                        CustomTextView textView = columnView.findViewById(R.id.ll_control_view);
                            //CustomTextView textView = (CustomTextView) ((LinearLayout) columnView).getChildAt(1);
                            CustomTextView textView = (CustomTextView) ((FrameLayout) ((LinearLayout) columnView).getChildAt(0)).getChildAt(0);
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


    private float total(String columnName) {
        String regex = "^[0-9]*[.]?[0-9]*$";
        float total = 0.0f;
        List<String> list = new ArrayList<>(); //nk pending outputData.get(columnName);
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

    public void setDisplayName(String strDisplayName) {
        tv_displayName.setText(strDisplayName);
        controlObject.setDisplayName(strDisplayName);
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

    public void setDataTable_colWidthType(String widthType) {
        controlObject.setDataTable_colWidthType(widthType);
    }

    public void dataTableSetPropertiesAction(List<Param> propertiesListMain) {
        try {

            List<Param> propertiesList = new ArrayList<>();
            String propertyText = "";
            ExpressionMainHelper expressionMainHelper = new ExpressionMainHelper();
            propertiesList = propertiesListMain;
            if (propertiesList != null && propertiesList.size() > 0) {
                isSetPropertiesEnable = true;
                for (int i = 0; i < propertiesList.size(); i++) {
                    Param property = propertiesList.get(i);
                    if (property.getName().contentEquals("ExpressionBuilder")) {
                        propertyText = expressionMainHelper.ExpressionHelper(context, property.getText());
                    } else {
                        propertyText = property.getText();
                    }
                    if (!propertyText.contentEquals("")) {
//                        Log.d(TAG, "DataTableSetPropertiesAction: " + property.getValue() + " - " + propertyText);
                        if (property.getValue().contentEquals(PropertiesNames.DISPLAY_NAME)) {
                            tv_displayName.setText(propertyText);
                            controlObject.setDisplayName(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.HINT)) {
                            if (property.getValue() != null && !property.getValue().isEmpty()) {
                                tv_hint.setVisibility(View.VISIBLE);
                                tv_hint.setText(propertyText);
                            } else {
                                tv_hint.setVisibility(View.GONE);
                            }
                            controlObject.setHint(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.HEADER_WIDTH_TYPE)) {
                            controlObject.setDataTable_colWidthType(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.HEADER_WIDTH_FIXED_SIZE)) {
                            controlObject.setDataTable_colWidthSize(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.HEADER_HEIGHT_TYPE)) {
                            controlObject.setDataTable_colHeightType(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.HEADER_HEIGHT_FIXED_SIZE)) {
                            controlObject.setDataTable_colHeightSize(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.HideHeaderColumnNames)) {
                            controlObject.setHideColumnNames(Boolean.parseBoolean(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.COL_TEXT_SIZE)) {
                            controlObject.setDataTable_colTextSize(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.COL_TEXT_STYLE)) {
                            controlObject.setDataTable_colTextStyle(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.COL_TEXT_COLOR)) {
                            controlObject.setDataTable_colTextColor(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.COL_TEXT_ALIGNMENT)) {
                            controlObject.setDataTable_colAlignment(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.COL_COLOR)) {
                            controlObject.setDataTable_colColor(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.ROW_HEIGHT_TYPE)) {
                            controlObject.setDataTable_rowHeigthType(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.ROW_HEIGHT_FIXED_SIZE)) {
                            controlObject.setDataTable_rowHeightSize(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.ROW_TEXT_SIZE)) {
                            controlObject.setDataTable_rowTextSize(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.ROW_TEXT_STYLE)) {
                            controlObject.setDataTable_rowTextStyle(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.ROW_TEXT_COLOR)) {
                            controlObject.setDataTable_rowTextColor(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.ROW_TEXT_COLOR)) {
                            controlObject.setDataTable_rowTextColor(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.ROW_TEXT_ALIGNMENT)) {
                            controlObject.setDataTable_rowAlignment(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.ROW_COLOR_TYPE)) {
                            controlObject.setDataTable_rowColorType(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.ROW_COLOR_1)) {
                            controlObject.setDataTable_rowColor1(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.ROW_COLOR_2)) {
                            controlObject.setDataTable_rowColor2(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.ROW_PARTICULAR_COLORING)) {
                            controlObject.setDataTable_ParticularRowsColoring(Boolean.parseBoolean(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.ROW_PARTICULAR_COLOR_IDS)) {
                            controlObject.setDataTable_ParticularRowsColoringIds(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.ROW_PARTICULAR_COLOR)) {
                            controlObject.setDataTable_ParticularRowColor(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.CONTROL_BORDER_TYPE)) {
                            controlObject.setDataTable_BorderType(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.CONTROL_BORDER_COLOR)) {
                            controlObject.setDataTable_BorderColor(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.CONTROL_BORDER_THICKNESS)) {
                            controlObject.setDataTable_BorderThickness(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.COL_BORDER)) {
                            controlObject.setDataTable_colBorder(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.PAGING)) {
                            controlObject.setDataTable_isPaging(Boolean.parseBoolean(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.DOWNLOAD_AS_EXCEL)) {
                            controlObject.setDownloadExcel(Boolean.parseBoolean(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.DOWNLOAD_AS_PDF)) {
                            controlObject.setDownloadPDF(Boolean.parseBoolean(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.ENABLE_SEARCH)) {
                            controlObject.setDataTable_EnableSearch(Boolean.parseBoolean(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.INVISIBLE)) {
                            setVisibility(!Boolean.parseBoolean(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.HIDE_DISPLAY_NAME)) {
                            controlObject.setHideDisplayName(Boolean.parseBoolean(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.FONT_SIZE)) {
                            controlObject.setTextSize(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.FONT_COLOR)) {
                            setTextColor(propertyText);
                            controlObject.setTextHexColor(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.FONT_STYLE)) {
                            setTextStyle(propertyText);
                        }
                    }
                }
                headerNames();
                // Update Header Data
                for (int i = 0; i < tl_header.getChildCount(); i++) {
                    TableRow tableRow = (TableRow) tl_header.getChildAt(i);
                    for (int row = 0; row < tableRow.getChildCount(); row++) {
                        View header_view = tableRow.getChildAt(row);
                        CustomTextView tv_header = header_view.findViewById(R.id.tv_label);
                        ImageView iv_sorting = header_view.findViewById(R.id.iv_sorting);
                        FrameLayout framelayout = header_view.findViewById(R.id.framelayout);
                        setOptionSettingForHeader(tv_header);
//                setAdvSettingForHeader(dtb, tv_header);

                        applyBorderForHeaderAndBodyAndFooter(framelayout, tv_header, 0, row, true, false, null,-1);
                        //col width & height
                        setHeaderWidthHeight(header_view, framelayout, null);
                    }
                    if (controlObject.isHideColumnNames()) {
                        tableRow.setVisibility(View.GONE);
                    }
                }

                // UpdateBody Data
                for (int rowIndex = 0; rowIndex < tl_body.getChildCount(); rowIndex++) {
                    TableRow tr_body = (TableRow) tl_body.getChildAt(rowIndex);
                    for (int colIndex = 0; colIndex < tr_body.getChildCount(); colIndex++) {
                        View body_view = tr_body.getChildAt(colIndex);
                        FrameLayout frameLayout = body_view.findViewById(R.id.framelayout);
                        CustomTextView textView = (CustomTextView) ((FrameLayout) ((LinearLayout) body_view).getChildAt(0)).getChildAt(0);
                        setOptionSettingForBody(textView, rowIndex);
                        applyBorderForHeaderAndBodyAndFooter(frameLayout, textView, rowIndex, colIndex, false, false, null,-1);
                        //row width & height
                        setBodyWidthHeight(body_view, frameLayout, colIndex, null);
                    }
                }
//            particularRowColoring
                particularRowColoring();
                setControlValues();
            } else {
                isSetPropertiesEnable = false;
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
            Log.d(TAG, "DataTableSetPropertiesActionE: " + e);
        }
    }

    public String setStyleFromIndex(String propertyText) {

        if (propertyText.equalsIgnoreCase("0")) {
            propertyText = "Bold";
        } else if (propertyText.equalsIgnoreCase("1")) {
            propertyText = "Italic";
        }
        return propertyText;
    }

    //ParticularRowsColoring
    public void particularRowColoring() {
        if (controlObject.isDataTable_ParticularRowsColoring()) {
            if (controlObject.getDataTable_ParticularRowsColoringIds() != null &&
                    controlObject.getDataTable_ParticularRowColor() != null) {
                String[] rowIDs = controlObject.getDataTable_ParticularRowsColoringIds().split(",");
                for (int i = 0; i < rowIDs.length; i++) {
                    if (tl_body.getChildCount() >= Integer.parseInt(rowIDs[i])) {
                        TableRow tableRow = (TableRow) tl_body.getChildAt(Integer.parseInt(rowIDs[i]));
                        if(tableRow != null && tableRow.getChildCount()>0) {
                            for (int j = 0; j < tableRow.getChildCount(); j++) {
                                CustomTextView textView = (CustomTextView) ((FrameLayout) ((LinearLayout) tableRow.getChildAt(j)).getChildAt(0)).getChildAt(0);
                                textView.setBackgroundColor(Color.parseColor(controlObject.getDataTable_ParticularRowColor()));
                            }
                        }
                    }
                }
            }
        }
    }

    /*ControlUi Setting start*/
    public LinearLayout getLl_main_inside(){
        return ll_main_inside;
    }
    public LinearLayout getLl_control_ui(){
        return ll_control_ui;
    }
    public LinearLayout getLl_data_table_main(){
        return ll_data_table_main;
    }
    public CustomTextView getTv_displayName(){
        return tv_displayName;
    }

    /*ControlUi Setting end*/
    public ControlObject getControlObject() {
        return controlObject;
    }


    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        }else{
            ct_showText.setVisibility(View.GONE);
        }
    }

}
