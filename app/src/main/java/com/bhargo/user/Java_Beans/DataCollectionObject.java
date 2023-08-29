package com.bhargo.user.Java_Beans;



import com.bhargo.user.pojos.EditOrViewColumns;
import com.bhargo.user.pojos.FilterSubFormColumns;
import com.bhargo.user.ui_form.PrimaryLayoutModelClass;
import com.bhargo.user.uisettings.pojos.UIPrimaryLayoutModelClass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DataCollectionObject implements Serializable{

    public String App_Name,App_DESC,App_Title,App_Theam,App_Mode="hybrid",App_QueryBase,App_Icon,uiType;
    public boolean App_OnPreLoadEvent,App_OnLoadEvent,App_OnSubmitEvent,isUIFormNeeded,isCaptchaRequired;
    public List<ControlObject> Controls_list;
    private Control_EventObject onSubmitClickObject;
    private Control_EventObject onPreLoadEventObject;
    private Control_EventObject onLoadEventObject;
    private TableSettingSObject_Bean tableSettingsObject;


    /*Table Settings*/
    public List<String> Default_Table_Columns;
    public List<String> List_Table_Columns;
    private List<QueryFilterField_Bean> mainTableWhereConditionFields;
    List<QueryFilterField_Bean> mainTableUpdateFields;
    List<QueryFilterField_Bean> mainTableInsertFields;


    //createnew/mapexisting/none
    private String tableSettingsType;
    private String existingTableName;
    private String mapExistingType;
    /*Table Settings*/

    private LinkedHashMap<String,String> translatedAppTitleMap;
    /*Editstart*/
    boolean Edit_rowItem,Delete_rowItem;
    List<String> List_Columns=new ArrayList<String>();
    List<String> List_Control_Types=new ArrayList<String>();


    private LinkedHashMap<String,String> translatedAppNames;
    private LinkedHashMap<String,String> translatedAppDescriptions;
    public static  HashMap<String,String> Control_Types=new HashMap<String,String>();



    /*Editend*/

    /*AdvanceSettings*/
    private DataManagementOptions dataManagementOptions;
    private boolean enableViewData;
    private boolean enableEditData;
    private boolean enableDeleteData;
    private boolean addNewRecord;
    private boolean allowOnlyOneRecord;
    private boolean skipIndexPage;
    private String captionForAdd;
    private String fetchData;
    List<String> previewColumns;
    String imagePreviewColumn;
    List<String> indexPageColumns;
    String indexPageColumnsOrder;
    String indexPageColumnsOrderList;
    List<String> viewPageColumnsOrder;
    List<EditOrViewColumns> editColumns;
    private boolean lazyLoadingEnabled;
    private String lazyLoadingThreshold;
    private List<API_InputParam_Bean> filterColumns;
    private List<FilterSubFormColumns> filterSubFormColumnsList;

    /**/


    private boolean enableClearButton;

    private boolean defaultActionForSubmit = true;

    //===Variables====
    private List<Variable_Bean> List_Varibles=new ArrayList<Variable_Bean>();

    //====Custom_SubmitButton===
    private String Submit_ButtonExitType;
    private String Submit_ButtonName;
    private boolean Submit_ButtonColorEnabel;
    private String Submit_ButtonColor;
    private boolean Submit_ButtonFontSizeEnabel;
    private String Submit_ButtonFontSize;

    /*UI Form Properties*/
    public PrimaryLayoutModelClass primaryLayoutModelClass;
    UIPrimaryLayoutModelClass uiPrimaryLayoutModelClass;


    private String SubmitButtonMessageSuccess_DisplayType;
    private String SubmitButtonSuccessMessage;
    private boolean SubmitButtonSuccessMessageIsEnable=false;

    private String SubmitButtonMessageFail_DisplayType;
    private String SubmitButtonFailMessage;
    private boolean SubmitButtonFailMessageIsEnable=false;

    public boolean isUIFormNeeded() {
        return isUIFormNeeded;
    }

    public void setUIFormNeeded(boolean UIFormNeeded) {
        isUIFormNeeded = UIFormNeeded;
    }

    public String getUiType() {
        return uiType;
    }

    public void setUiType(String uiType) {
        this.uiType = uiType;
    }

    public boolean isCaptchaRequired() {
        return isCaptchaRequired;
    }

    public void setCaptchaRequired(boolean captchaRequired) {
        isCaptchaRequired = captchaRequired;
    }

    public PrimaryLayoutModelClass getPrimaryLayoutModelClass() {
        return primaryLayoutModelClass;
    }

    public void setPrimaryLayoutModelClass(PrimaryLayoutModelClass primaryLayoutModelClass) {
        this.primaryLayoutModelClass = primaryLayoutModelClass;
    }

    public String getApp_Name() {
        return App_Name;
    }

    public void setApp_Name(String app_Name) {
        App_Name = app_Name;
    }

    public String getApp_QueryBase() {
        return App_QueryBase;
    }

    public void setApp_QueryBase(String app_QueryBase) {
        App_QueryBase = app_QueryBase;
    }

    public String getApp_DESC() {
        return App_DESC;
    }

    public void setApp_DESC(String app_DESC) {
        App_DESC = app_DESC;
    }

    public String getApp_Title() {
        return App_Title;
    }

    public void setApp_Title(String app_Title) {
        App_Title = app_Title;
    }

    public String getApp_Theam() {
        return App_Theam;
    }

    public void setApp_Theam(String app_Theam) {
        App_Theam = app_Theam;
    }

    public String getApp_Mode() {
        return App_Mode;
    }

    public void setApp_Mode(String app_Mode) {
        App_Mode = app_Mode;
    }

    public boolean getApp_OnPreLoadEvent() {
        return App_OnPreLoadEvent;
    }

    public void setApp_OnPreLoadEvent(boolean app_OnPreLoadEvent) {
        App_OnPreLoadEvent = app_OnPreLoadEvent;
    }

    public boolean getApp_OnLoadEvent() {
        return App_OnLoadEvent;
    }

    public void setApp_OnLoadEvent(boolean app_OnLoadEvent) {
        App_OnLoadEvent = app_OnLoadEvent;
    }

    public boolean getApp_OnSubmitEvent() {
        return App_OnSubmitEvent;
    }

    public void setApp_OnSubmitEvent(boolean app_OnSubmitEvent) {
        App_OnSubmitEvent = app_OnSubmitEvent;
    }

    public List<ControlObject> getControls_list() {
        return Controls_list;
    }

    public void setControls_list(List<ControlObject> controls_list) {
        Controls_list = controls_list;
    }

    public Control_EventObject getOnSubmitClickObject() {
        return onSubmitClickObject;
    }

    public void setOnSubmitClickObject(Control_EventObject onSubmitClickObject) {
        this.onSubmitClickObject = onSubmitClickObject;
    }

    public Control_EventObject getOnPreLoadEventObject() {
        return onPreLoadEventObject;
    }

    public void setOnPreLoadEventObject(Control_EventObject onPreLoadEventObject) {
        this.onPreLoadEventObject = onPreLoadEventObject;
    }

    public Control_EventObject getOnLoadEventObject() {
        return onLoadEventObject;
    }

    public void setOnLoadEventObject(Control_EventObject onLoadEventObject) {
        this.onLoadEventObject = onLoadEventObject;
    }

    public TableSettingSObject_Bean getTableSettingsObject() {
        return tableSettingsObject;
    }

    public void setTableSettingsObject(TableSettingSObject_Bean tableSettingsObject) {
        this.tableSettingsObject = tableSettingsObject;
    }

    public List<String> getDefault_Table_Columns() {
        return Default_Table_Columns;
    }

    public void setDefault_Table_Columns(List<String> default_Table_Columns) {
        Default_Table_Columns = default_Table_Columns;
    }

    public List<String> getList_Table_Columns() {
        return List_Table_Columns;
    }

    public void setList_Table_Columns(List<String> list_Table_Columns) {
        List_Table_Columns = list_Table_Columns;
    }

    public List<QueryFilterField_Bean> getMainTableWhereConditionFields() {
        return mainTableWhereConditionFields;
    }

    public void setMainTableWhereConditionFields(List<QueryFilterField_Bean> mainTableWhereConditionFields) {
        this.mainTableWhereConditionFields = mainTableWhereConditionFields;
    }

    public List<QueryFilterField_Bean> getMainTableUpdateFields() {
        return mainTableUpdateFields;
    }

    public void setMainTableUpdateFields(List<QueryFilterField_Bean> mainTableUpdateFields) {
        this.mainTableUpdateFields = mainTableUpdateFields;
    }

    public List<QueryFilterField_Bean> getMainTableInsertFields() {
        return mainTableInsertFields;
    }

    public void setMainTableInsertFields(List<QueryFilterField_Bean> mainTableInsertFields) {
        this.mainTableInsertFields = mainTableInsertFields;
    }

    public String getTableSettingsType() {
        return tableSettingsType;
    }

    public void setTableSettingsType(String tableSettingsType) {
        this.tableSettingsType = tableSettingsType;
    }

    public String getExistingTableName() {
        return existingTableName;
    }

    public void setExistingTableName(String existingTableName) {
        this.existingTableName = existingTableName;
    }

    public String getMapExistingType() {
        return mapExistingType;
    }

    public void setMapExistingType(String mapExistingType) {
        this.mapExistingType = mapExistingType;
    }

    public boolean isApp_OnPreLoadEvent() {
        return App_OnPreLoadEvent;
    }

    public boolean isApp_OnLoadEvent() {
        return App_OnLoadEvent;
    }

    public boolean isApp_OnSubmitEvent() {
        return App_OnSubmitEvent;
    }

    public boolean isEnableClearButton() {
        return enableClearButton;
    }

    public void setEnableClearButton(boolean enableClearButton) {
        this.enableClearButton = enableClearButton;
    }

    public boolean isDefaultActionForSubmit() {
        return defaultActionForSubmit;
    }

    public void setDefaultActionForSubmit(boolean defaultActionForSubmit) {
        this.defaultActionForSubmit = defaultActionForSubmit;
    }

    public List<String> getList_Columns() {
        return List_Columns;
    }

    public void setList_Columns(List<String> list_Columns) {
        List_Columns = list_Columns;
    }

    public List<String> getList_Control_Types() {
        return List_Control_Types;
    }

    public void setList_Control_Types(List<String> list_Control_Types) {
        List_Control_Types = list_Control_Types;
    }

    public static HashMap<String, String> getControl_Types() {
        return Control_Types;
    }

    public static void setControl_Types(HashMap<String, String> control_Types) {
        Control_Types = control_Types;
    }

    public boolean isEdit_rowItem() {
        return Edit_rowItem;
    }

    public void setEdit_rowItem(boolean edit_rowItem) {
        Edit_rowItem = edit_rowItem;
    }

    public boolean isDelete_rowItem() {
        return Delete_rowItem;
    }

    public void setDelete_rowItem(boolean delete_rowItem) {
        Delete_rowItem = delete_rowItem;
    }

   /* public boolean isEnableViewData() {
        return enableViewData;
    }

    public void setEnableViewData(boolean enableViewData) {
        this.enableViewData = enableViewData;
    }

    public boolean isEnableEditData() {
        return enableEditData;
    }

    public void setEnableEditData(boolean enableEditData) {
        this.enableEditData = enableEditData;
    }

    public boolean isEnableDeleteData() {
        return enableDeleteData;
    }

    public void setEnableDeleteData(boolean enableDeleteData) {
        this.enableDeleteData = enableDeleteData;
    }

    public boolean isAddNewRecord() {
        return addNewRecord;
    }

    public void setAddNewRecord(boolean addNewRecord) {
        this.addNewRecord = addNewRecord;
    }

    public boolean isAllowOnlyOneRecord() {
        return allowOnlyOneRecord;
    }

    public void setAllowOnlyOneRecord(boolean allowOnlyOneRecord) {
        this.allowOnlyOneRecord = allowOnlyOneRecord;
    }

    public boolean isSkipIndexPage() {
        return skipIndexPage;
    }

    public void setSkipIndexPage(boolean skipIndexPage) {
        this.skipIndexPage = skipIndexPage;
    }

    public String getCaptionForAdd() {
        return captionForAdd;
    }

    public void setCaptionForAdd(String captionForAdd) {
        this.captionForAdd = captionForAdd;
    }

    public String getFetchData() {
        return fetchData;
    }

    public void setFetchData(String fetchData) {
        this.fetchData = fetchData;
    }

    public List<String> getPreviewColumns() {
        return previewColumns;
    }

    public void setPreviewColumns(List<String> previewColumns) {
        this.previewColumns = previewColumns;
    }

    public List<String> getIndexPageColumns() {
        return indexPageColumns;
    }

    public void setIndexPageColumns(List<String> indexPageColumns) {
        this.indexPageColumns = indexPageColumns;
    }

    public String getIndexPageColumnsOrder() {
        return indexPageColumnsOrder;
    }

    public void setIndexPageColumnsOrder(String indexPageColumnsOrder) {
        this.indexPageColumnsOrder = indexPageColumnsOrder;
    }

    public String getIndexPageColumnsOrderList() {
        return indexPageColumnsOrderList;
    }

    public void setIndexPageColumnsOrderList(String indexPageColumnsOrderList) {
        this.indexPageColumnsOrderList = indexPageColumnsOrderList;
    }

    public List<String> getViewPageColumnsOrder() {
        return viewPageColumnsOrder;
    }

    public void setViewPageColumnsOrder(List<String> viewPageColumnsOrder) {
        this.viewPageColumnsOrder = viewPageColumnsOrder;
    }

    public List<EditOrViewColumns> getEditColumns() {
        return editColumns;
    }

    public void setEditColumns(List<EditOrViewColumns> editColumns) {
        this.editColumns = editColumns;
    }*/

    public List<Variable_Bean> getList_Varibles() {
        return List_Varibles;
    }

    public void setList_Varibles(List<Variable_Bean> list_Varibles) {
        List_Varibles = list_Varibles;
    }

    public String getSubmit_ButtonExitType() {
        return Submit_ButtonExitType;
    }

    public void setSubmit_ButtonExitType(String submit_ButtonExitType) {
        Submit_ButtonExitType = submit_ButtonExitType;
    }

    public String getSubmit_ButtonName() {
        return Submit_ButtonName;
    }

    public void setSubmit_ButtonName(String submit_ButtonName) {
        Submit_ButtonName = submit_ButtonName;
    }

    public boolean isSubmit_ButtonColorEnabel() {
        return Submit_ButtonColorEnabel;
    }

    public void setSubmit_ButtonColorEnabel(boolean submit_ButtonColorEnabel) {
        Submit_ButtonColorEnabel = submit_ButtonColorEnabel;
    }

    public String getSubmit_ButtonColor() {
        return Submit_ButtonColor;
    }

    public void setSubmit_ButtonColor(String submit_ButtonColor) {
        Submit_ButtonColor = submit_ButtonColor;
    }

    public boolean isSubmit_ButtonFontSizeEnabel() {
        return Submit_ButtonFontSizeEnabel;
    }

    public void setSubmit_ButtonFontSizeEnabel(boolean submit_ButtonFontSizeEnabel) {
        Submit_ButtonFontSizeEnabel = submit_ButtonFontSizeEnabel;
    }

    public String getSubmit_ButtonFontSize() {
        return Submit_ButtonFontSize;
    }

    public void setSubmit_ButtonFontSize(String submit_ButtonFontSize) {
        Submit_ButtonFontSize = submit_ButtonFontSize;
    }

    public LinkedHashMap<String, String> getTranslatedAppTitleMap() {
        return translatedAppTitleMap;
    }

    public void setTranslatedAppTitleMap(LinkedHashMap<String, String> translatedAppTitleMap) {
        this.translatedAppTitleMap = translatedAppTitleMap;
    }

    public LinkedHashMap<String, String> getTranslatedAppNames() {
        return translatedAppNames;
    }

    public void setTranslatedAppNames(LinkedHashMap<String, String> translatedAppNames) {
        this.translatedAppNames = translatedAppNames;
    }

    public LinkedHashMap<String, String> getTranslatedAppDescriptions() {
        return translatedAppDescriptions;
    }

    public void setTranslatedAppDescriptions(LinkedHashMap<String, String> translatedAppDescriptions) {
        this.translatedAppDescriptions = translatedAppDescriptions;
    }

    public UIPrimaryLayoutModelClass getUiPrimaryLayoutModelClass() {
        return uiPrimaryLayoutModelClass;
    }

    public void setUiPrimaryLayoutModelClass(UIPrimaryLayoutModelClass uiPrimaryLayoutModelClass) {
        this.uiPrimaryLayoutModelClass = uiPrimaryLayoutModelClass;
    }

  /*  public boolean isLazyLoadingEnabled() {
        return lazyLoadingEnabled;
    }

    public void setLazyLoadingEnabled(boolean lazyLoadingEnabled) {
        this.lazyLoadingEnabled = lazyLoadingEnabled;
    }

    public String getLazyLoadingThreshold() {
        return lazyLoadingThreshold;
    }

    public void setLazyLoadingThreshold(String lazyLoadingThreshold) {
        this.lazyLoadingThreshold = lazyLoadingThreshold;
    }*/


    public String getSubmitButtonMessageSuccess_DisplayType() {
        return SubmitButtonMessageSuccess_DisplayType;
    }

    public void setSubmitButtonMessageSuccess_DisplayType(String submitButtonMessageSuccess_DisplayType) {
        SubmitButtonMessageSuccess_DisplayType = submitButtonMessageSuccess_DisplayType;
    }

    public String getSubmitButtonSuccessMessage() {
        return SubmitButtonSuccessMessage;
    }

    public void setSubmitButtonSuccessMessage(String submitButtonSuccessMessage) {
        SubmitButtonSuccessMessage = submitButtonSuccessMessage;
    }

    public boolean isSubmitButtonSuccessMessageIsEnable() {
        return SubmitButtonSuccessMessageIsEnable;
    }

    public void setSubmitButtonSuccessMessageIsEnable(boolean submitButtonSuccessMessageIsEnable) {
        SubmitButtonSuccessMessageIsEnable = submitButtonSuccessMessageIsEnable;
    }

    public String getSubmitButtonMessageFail_DisplayType() {
        return SubmitButtonMessageFail_DisplayType;
    }

    public void setSubmitButtonMessageFail_DisplayType(String submitButtonMessageFail_DisplayType) {
        SubmitButtonMessageFail_DisplayType = submitButtonMessageFail_DisplayType;
    }

    public String getSubmitButtonFailMessage() {
        return SubmitButtonFailMessage;
    }

    public void setSubmitButtonFailMessage(String submitButtonFailMessage) {
        SubmitButtonFailMessage = submitButtonFailMessage;
    }

    public boolean isSubmitButtonFailMessageIsEnable() {
        return SubmitButtonFailMessageIsEnable;
    }

    public void setSubmitButtonFailMessageIsEnable(boolean submitButtonFailMessageIsEnable) {
        SubmitButtonFailMessageIsEnable = submitButtonFailMessageIsEnable;
    }

    public List<API_InputParam_Bean> getFilterColumns() {
        return filterColumns;
    }

    public void setFilterColumns(List<API_InputParam_Bean> filterColumns) {
        this.filterColumns = filterColumns;
    }

    public List<FilterSubFormColumns> getFilterSubFormColumnsList() {
        return filterSubFormColumnsList;
    }

    public void setFilterSubFormColumnsList(List<FilterSubFormColumns> filterSubFormColumnsList) {
        this.filterSubFormColumnsList = filterSubFormColumnsList;
    }

    public String getApp_Icon() {
        return App_Icon;
    }

    public void setApp_Icon(String app_Icon) {
        App_Icon = app_Icon;
    }

    public boolean isEnableViewData() {
        return enableViewData;
    }

    public void setEnableViewData(boolean enableViewData) {
        this.enableViewData = enableViewData;
    }

    public boolean isEnableEditData() {
        return enableEditData;
    }

    public void setEnableEditData(boolean enableEditData) {
        this.enableEditData = enableEditData;
    }

    public boolean isEnableDeleteData() {
        return enableDeleteData;
    }

    public void setEnableDeleteData(boolean enableDeleteData) {
        this.enableDeleteData = enableDeleteData;
    }

    public boolean isAddNewRecord() {
        return addNewRecord;
    }

    public void setAddNewRecord(boolean addNewRecord) {
        this.addNewRecord = addNewRecord;
    }

    public boolean isAllowOnlyOneRecord() {
        return allowOnlyOneRecord;
    }

    public void setAllowOnlyOneRecord(boolean allowOnlyOneRecord) {
        this.allowOnlyOneRecord = allowOnlyOneRecord;
    }

    public boolean isSkipIndexPage() {
        return skipIndexPage;
    }

    public void setSkipIndexPage(boolean skipIndexPage) {
        this.skipIndexPage = skipIndexPage;
    }

    public String getCaptionForAdd() {
        return captionForAdd;
    }

    public void setCaptionForAdd(String captionForAdd) {
        this.captionForAdd = captionForAdd;
    }

    public String getFetchData() {
        return fetchData;
    }

    public void setFetchData(String fetchData) {
        this.fetchData = fetchData;
    }

    public List<String> getPreviewColumns() {
        return previewColumns;
    }

    public void setPreviewColumns(List<String> previewColumns) {
        this.previewColumns = previewColumns;
    }

    public String getImagePreviewColumn() {
        return imagePreviewColumn;
    }

    public void setImagePreviewColumn(String imagePreviewColumn) {
        this.imagePreviewColumn = imagePreviewColumn;
    }

    public List<String> getIndexPageColumns() {
        return indexPageColumns;
    }

    public void setIndexPageColumns(List<String> indexPageColumns) {
        this.indexPageColumns = indexPageColumns;
    }

    public String getIndexPageColumnsOrder() {
        return indexPageColumnsOrder;
    }

    public void setIndexPageColumnsOrder(String indexPageColumnsOrder) {
        this.indexPageColumnsOrder = indexPageColumnsOrder;
    }

    public String getIndexPageColumnsOrderList() {
        return indexPageColumnsOrderList;
    }

    public void setIndexPageColumnsOrderList(String indexPageColumnsOrderList) {
        this.indexPageColumnsOrderList = indexPageColumnsOrderList;
    }

    public List<String> getViewPageColumnsOrder() {
        return viewPageColumnsOrder;
    }

    public void setViewPageColumnsOrder(List<String> viewPageColumnsOrder) {
        this.viewPageColumnsOrder = viewPageColumnsOrder;
    }

    public List<EditOrViewColumns> getEditColumns() {
        return editColumns;
    }

    public void setEditColumns(List<EditOrViewColumns> editColumns) {
        this.editColumns = editColumns;
    }

    public boolean isLazyLoadingEnabled() {
        return lazyLoadingEnabled;
    }

    public void setLazyLoadingEnabled(boolean lazyLoadingEnabled) {
        this.lazyLoadingEnabled = lazyLoadingEnabled;
    }

    public String getLazyLoadingThreshold() {
        return lazyLoadingThreshold;
    }

    public void setLazyLoadingThreshold(String lazyLoadingThreshold) {
        this.lazyLoadingThreshold = lazyLoadingThreshold;
    }

    public DataManagementOptions getDataManagementOptions() {
        return dataManagementOptions;
    }

    public void setDataManagementOptions(DataManagementOptions dataManagementOptions) {
        this.dataManagementOptions = dataManagementOptions;
    }


}