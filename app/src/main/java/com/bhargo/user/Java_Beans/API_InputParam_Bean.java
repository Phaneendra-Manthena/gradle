package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class API_InputParam_Bean implements Serializable {

    private String InParam_Name;// col name
    private String InParam_MappedControl_Type;
    private String InParam_Mapped_ID;//col val  String inValue = ImproveHelper.getValueFromGlobalObject(MainActivity.this, list_input.get(i).getInParam_Mapped_ID().trim());
    private boolean enable;//only true case add in where condition

    private String InParam_and_or;//same val
    private boolean InParam_ExpressionExists;
    private String InParam_ExpressionValue;
    private String InParam_Optional;
    private String InParam_Static;
    private String InParam_InputMode;
    private String InParam_Operator;//ImproveHelper.getOparator(list_input_enabled.get(i).getInParam_Operator()

    private String _InParam_nearBy;
    private String _InParam_noOfRecords;

    private String InParam_near_by_distance;
    private String InParam_near_by_records;

    private boolean InParam_isUnderArrayObject;
    private String InParam_ArrayObjectName;

    private String InParam_SelectedDataSource;
    private boolean InParam_SelectedDataExpressionExists;
    private String InParam_SelectedDataExpressionValue;

    private String InParam_GroupDML_StatementName;
    private String InParam_GroupDML_DataSource_Value;
    private boolean InParam_GroupDML_DataSource_ExpressionExists;
    private String InParam_GroupDML_DataSource_ExpressionValue;
    private String InParam_GroupDML_Input_Type;

    private boolean isGPSControl;
    private String InParam_Temp_Value;
    private String currentGps;



    private String _APIFormDataType;


    List<API_InputParam_Bean> _FilterParams ;  // API_InputParam_Bean

    private boolean _inParam_isParentAvailable;
    private String _inParam_ParentName;
    private String _inParam_DataSourceName;
    private boolean _inParam_isFiltersAvailable;
    private String _InParam_Type;








    public API_InputParam_Bean(String InParam_Name, String InParam_MappedControl_Type, String InParam_Mapped_ID) {
        this.InParam_Name = InParam_Name;
        this.InParam_MappedControl_Type = InParam_MappedControl_Type;
        this.InParam_Mapped_ID = InParam_Mapped_ID;
    }

    public String getInParam_Name() {
        return InParam_Name;
    }

    public void setInParam_Name(String inParam_Name) {
        InParam_Name = inParam_Name;
    }

    public String getInParam_MappedControl_Type() {
        return InParam_MappedControl_Type;
    }

    public void setInParam_MappedControl_Type(String inParam_MappedControl_Type) {
        InParam_MappedControl_Type = inParam_MappedControl_Type;
    }

    public String getInParam_Mapped_ID() {
        return InParam_Mapped_ID;
    }

    public void setInParam_Mapped_ID(String inParam_Mapped_ID) {
        InParam_Mapped_ID = inParam_Mapped_ID;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isInParam_ExpressionExists() {
        return InParam_ExpressionExists;
    }

    public void setInParam_ExpressionExists(boolean inParam_ExpressionExists) {
        InParam_ExpressionExists = inParam_ExpressionExists;
    }

    public String getInParam_ExpressionValue() {
        return InParam_ExpressionValue;
    }

    public void setInParam_ExpressionValue(String inParam_ExpressionValue) {
        InParam_ExpressionValue = inParam_ExpressionValue;
    }

    public String getInParam_Optional() {
        return InParam_Optional;
    }

    public void setInParam_Optional(String inParam_Optional) {
        InParam_Optional = inParam_Optional;
    }

    public String getInParam_Static() {
        return InParam_Static;
    }

    public void setInParam_Static(String inParam_Static) {
        InParam_Static = inParam_Static;
    }

    public String getInParam_InputMode() {
        return InParam_InputMode;
    }

    public void setInParam_InputMode(String inParam_InputMode) {
        InParam_InputMode = inParam_InputMode;
    }

    public String getInParam_Operator() {
        return InParam_Operator;
    }

    public void setInParam_Operator(String inParam_Operator) {
        InParam_Operator = inParam_Operator;
    }

    public String getInParam_and_or() {
        return InParam_and_or;
    }

    public void setInParam_and_or(String inParam_and_or) {
        InParam_and_or = inParam_and_or;
    }

    public String getInParam_near_by_distance() {
        return InParam_near_by_distance;
    }

    public void setInParam_near_by_distance(String inParam_near_by_distance) {
        InParam_near_by_distance = inParam_near_by_distance;
    }

    public String getInParam_near_by_records() {
        return InParam_near_by_records;
    }

    public void setInParam_near_by_records(String inParam_near_by_records) {
        InParam_near_by_records = inParam_near_by_records;
    }

    public boolean isGPSControl() {
        return isGPSControl;
    }

    public void setGPSControl(boolean GPSControl) {
        isGPSControl = GPSControl;
    }

    public boolean isInParam_isUnderArrayObject() {
        return InParam_isUnderArrayObject;
    }

    public void setInParam_isUnderArrayObject(boolean inParam_isUnderArrayObject) {
        InParam_isUnderArrayObject = inParam_isUnderArrayObject;
    }

    public String getInParam_ArrayObjectName() {
        return InParam_ArrayObjectName;
    }

    public void setInParam_ArrayObjectName(String inParam_ArrayObjectName) {
        InParam_ArrayObjectName = inParam_ArrayObjectName;
    }

    public String getInParam_SelectedDataSource() {
        return InParam_SelectedDataSource;
    }

    public void setInParam_SelectedDataSource(String inParam_SelectedDataSource) {
        InParam_SelectedDataSource = inParam_SelectedDataSource;
    }

    public boolean isInParam_SelectedDataExpressionExists() {
        return InParam_SelectedDataExpressionExists;
    }

    public void setInParam_SelectedDataExpressionExists(boolean inParam_SelectedDataExpressionExists) {
        InParam_SelectedDataExpressionExists = inParam_SelectedDataExpressionExists;
    }

    public String getInParam_SelectedDataExpressionValue() {
        return InParam_SelectedDataExpressionValue;
    }

    public void setInParam_SelectedDataExpressionValue(String inParam_SelectedDataExpressionValue) {
        InParam_SelectedDataExpressionValue = inParam_SelectedDataExpressionValue;
    }


    public String getInParam_GroupDML_StatementName() {
        return InParam_GroupDML_StatementName;
    }

    public void setInParam_GroupDML_StatementName(String inParam_GroupDML_StatementName) {
        InParam_GroupDML_StatementName = inParam_GroupDML_StatementName;
    }

    public String getInParam_GroupDML_DataSource_Value() {
        return InParam_GroupDML_DataSource_Value;
    }

    public void setInParam_GroupDML_DataSource_Value(String inParam_GroupDML_DataSource_Value) {
        InParam_GroupDML_DataSource_Value = inParam_GroupDML_DataSource_Value;
    }

    public boolean isInParam_GroupDML_DataSource_ExpressionExists() {
        return InParam_GroupDML_DataSource_ExpressionExists;
    }

    public void setInParam_GroupDML_DataSource_ExpressionExists(boolean inParam_GroupDML_DataSource_ExpressionExists) {
        InParam_GroupDML_DataSource_ExpressionExists = inParam_GroupDML_DataSource_ExpressionExists;
    }

    public String getInParam_GroupDML_DataSource_ExpressionValue() {
        return InParam_GroupDML_DataSource_ExpressionValue;
    }

    public void setInParam_GroupDML_DataSource_ExpressionValue(String inParam_GroupDML_DataSource_ExpressionValue) {
        InParam_GroupDML_DataSource_ExpressionValue = inParam_GroupDML_DataSource_ExpressionValue;
    }

    public String getInParam_GroupDML_Input_Type() {
        return InParam_GroupDML_Input_Type;
    }

    public void setInParam_GroupDML_Input_Type(String inParam_GroupDML_Input_Type) {
        InParam_GroupDML_Input_Type = inParam_GroupDML_Input_Type;
    }

    public String getInParam_Temp_Value() {
        return InParam_Temp_Value;
    }

    public void setInParam_Temp_Value(String inParam_Temp_Value) {
        InParam_Temp_Value = inParam_Temp_Value;
    }

    public String getCurrentGps() {
        return currentGps;
    }

    public void setCurrentGps(String currentGps) {
        this.currentGps = currentGps;
    }

    public String get_InParam_nearBy() {
        return _InParam_nearBy;
    }

    public void set_InParam_nearBy(String _InParam_nearBy) {
        this._InParam_nearBy = _InParam_nearBy;
    }

    public String get_InParam_noOfRecords() {
        return _InParam_noOfRecords;
    }

    public void set_InParam_noOfRecords(String _InParam_noOfRecords) {
        this._InParam_noOfRecords = _InParam_noOfRecords;
    }

    public String get_APIFormDataType() {
        return _APIFormDataType;
    }

    public void set_APIFormDataType(String _APIFormDataType) {
        this._APIFormDataType = _APIFormDataType;
    }

    public List<API_InputParam_Bean> get_FilterParams() {
        return _FilterParams;
    }

    public void set_FilterParams(List<API_InputParam_Bean> _FilterParams) {
        this._FilterParams = _FilterParams;
    }

    public boolean is_inParam_isParentAvailable() {
        return _inParam_isParentAvailable;
    }

    public void set_inParam_isParentAvailable(boolean _inParam_isParentAvailable) {
        this._inParam_isParentAvailable = _inParam_isParentAvailable;
    }

    public String get_inParam_ParentName() {
        return _inParam_ParentName;
    }

    public void set_inParam_ParentName(String _inParam_ParentName) {
        this._inParam_ParentName = _inParam_ParentName;
    }

    public String get_inParam_DataSourceName() {
        return _inParam_DataSourceName;
    }

    public void set_inParam_DataSourceName(String _inParam_DataSourceName) {
        this._inParam_DataSourceName = _inParam_DataSourceName;
    }

    public boolean is_inParam_isFiltersAvailable() {
        return _inParam_isFiltersAvailable;
    }

    public void set_inParam_isFiltersAvailable(boolean _inParam_isFiltersAvailable) {
        this._inParam_isFiltersAvailable = _inParam_isFiltersAvailable;
    }

    public String get_InParam_Type() {
        return _InParam_Type;
    }

    public void set_InParam_Type(String _InParam_Type) {
        this._InParam_Type = _InParam_Type;
    }

}
