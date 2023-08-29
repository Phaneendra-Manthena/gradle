package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nk.mobileapps.spinnerlib.SpinnerData;

public class ActionWithoutCondition_Bean implements Serializable {
    //dump testing
    private int actionPos=-1;
    private int positionInEvent=-1;

    private String ActionId;
    private String ActionName;
    private String ActionType;
    public boolean Active=true;
    /*Manage Count up Timer*/
    private String ManageCountUpTimer_Control_Selected;
    private String ManageCountUpTimer_Type;
    private String ManageCountUpTimer_Option;

    /*Manage Count Down Timer*/ //Manage Count Down Timer,CONTROL_TYPE_COUNT_UP_TIMER
    private String ManageCountDownTimer_Control_Selected;
    private String ManageCountDownTimer_Type;
    private String ManageCountDownTimer_Option;

    /*Get GPS Location*/
    private String GetGPSLocation_GPSMode;
    private String GetGPSLocation_GPSAccuracy;
    private String GetGPSLocation_Type;//Control|Variables
    private String GetGPSLocation_Control_Selected;
    private String GetGPSLocation_Variable_Selected;
    private boolean gpsCaptured;

    /*Call API*/
    private String SelectedAPIName;
    private String  _APIFormDataExits;



    private String _SelectedAPICategory;
    private final List<API_InputParam_Bean> List_API_InParams =new ArrayList<API_InputParam_Bean>();
    private final List<API_OutputParam_Bean> List_API_OutParams=new ArrayList<API_OutputParam_Bean>();
    private String Result_NoOfRecords;
    private String Result_DisplayType;
    private String Result_ListView_FilterItem;
    private String Result_ListView_FilterItemID;
    private String Result_ListView_FilterMappedControl;
    private String selectedSubForm;
    private String ServiceSource;
    private String SaveOfflineType;
    private String Multi_DataType;

    private String NoDataMessageType;
    private String NoDataMessageDisplayType;
    private String NoDataMessage;


    private String Message_Success;
    private String Message_SuccessDisplayType;
    private boolean SuccessMessageIsEnable=false;

    private String Message_SuccessNoRecords;
    private String Message_SuccessNoRecordsDisplayType;
    private boolean Message_SuccessNoRecordsIsEnable=false;

    private String Message_Fail;
    private String Message_FailNoRecordsDisplayType;
    private boolean Message_FailNoRecordsIsEnable=false;


    //BothCallAPI_and_CallFormFields
    private String ProfileImage_Mapped_item;
    private String Image_Mapped_item;
    private String ImageAdvanced_ImageorNot;
    private String ImageAdvanced_ImageSource;
    private String ImageAdvanced_ConditionColumn;
    private String ImageAdvanced_Operator;
    private List<ImageAdvanced_Mapped_Item> List_ImageAdvanced_Items;
    private String Corner_Mapped_item;
    private String Header_Mapped_item;
    private String SubHeader_Mapped_item;
    private List<String> Description_Mapped_item;
    private String DateandTime_Mapped_item;

    private String itemValue;
    //--EV App--//
    private String distance;
    private String working_hours;
    private String item_one_count;
    private String item_two_count;
    private String rating;
    private String source_icon;
    private String source_name;
    private String source_time;
    private String news_type;
    private String itemName;
    private String watsAppNo;
    private String dailNo;
    //--EV App--//
    private String dv_trans_id;
    private String orderByColumns="";
    private String order="";

    /*Chart Control*/

    private List<String> xAxisValues;
    private List<String> comboChartTypes;
    private List<String> yAxisValues;
    private String pieChartControlName;
    private String pieChartMeasurementValue;
    private List<Axis> xAxisList = new ArrayList<>();
    private List<Axis> yAxisList=new ArrayList<>();

//     /*Call Form */
    private List<Variable_Bean> List_Varibles =new ArrayList<Variable_Bean>();
    private String formType;
    private boolean closeParentEnabled;
    private boolean keepSessionEnabled;
    private boolean closeAllFormsEnabled;
    private boolean goToHomeEnabled;
    private String Select_FormName;
    private String Select_FormAppMode;
    private String Select_FormTableName;
    private List<API_InputParam_Bean> List_Form_InParams=new ArrayList<API_InputParam_Bean>();
    private List<API_OutputParam_Bean> List_Form_OutParams=new ArrayList<API_OutputParam_Bean>();
    private String Form_Result_NoOfRecords;
    private String Form_DisplayType;
    private String Form_ListView_FilterItem;
    private String Form_NewTabel;


    /*Call DML */
    private String DML_Input_Type;
    private String DML_DataSource_Value;
    private boolean DML_DataSource_ExpressionExists;
    private String DML_DataSource_ExpressionValue;

    /*Call Disable Controls*/

    private List<String> List_DisableControlIds;

    /*Call Enable Controls*/

    private List<String> List_EnableControlIds;

    /*Call Visibility On Controls*/

    private List<String> List_VisibleOnControlIds;
    List<SubControls_Advance_Bean> List_SubControl_Advance;

    /*Call Visibility OFF Controls*/

    private List<String> List_VisibleOffControlIds;

    /* Add Row */

    private String AddRowEvent_SubFormName;
    private String DeleteRowEvent_SubFormName;

    /*Call SetValues */
    private String Setvalue_TypeofSource;
    private String Setvalue_ControleID;
    private String SetValue_TypeOfValue="Value";//Value_MultipleValues
    private String SetValue_Advanced_Value;
    private String SetValue_Normal_Value;
    private String SetValue_TypeOfAppend;
    private boolean SetValue_MultiValueExpression=false;
    private String SetValue_MultiValueExpressionBody;
    private List<String> SetValue_AppendValues;
    private List<String> SetValue_AppendValuesID;
    private boolean SetValue_Subform=false;
    private String Setvalue_SubFormName;
    private String Setvalue_SubFormControlName;
    private String Setvalue_SubFormPosition;
    private String Setvalue_SubFormDynamicExpression    ;

    /*Call SetValues New */
    private String sv_TypeofSource;  //Control_Varible
    private String sv_ControlType;       //singleRowControl_MultiRowsControl

    private AssignControl_Bean sv_Single_List_AssignControls;
    private List<AssignControl_Bean> sv_single_control_assign_controls;

    private String sv_Multiple_ControlName;//ControlName_of_Subform_Grid_Dataviewer
    private String sv_Multiple_RowType;//SingleRow_MultiRow
    private String sv_Multiple_Single_assignLevel;//if_it_is_SingleRow_then_new|current|specific
    private boolean sv_Multiple_Single_rowPosition_Expression;//if_it_is_SingleRow_and_specific_Then
    private String sv_Multiple_Single_rowPosition;//if_it_is_SingleRow_and_specific_Then
    private String sv_Multiple_multi_assignType;//if_it_is_multiRow_then_Append|replace|Update

    private List<AssignControl_Bean> sv_Multiple_List_AssignControls = new ArrayList<>();
    private String sv_Map_DataSource;
    private boolean sv_Map_DataSource_Expression;

    private String sv_data_source;


    /*Call Clear Controls*/

    private List<String> List_ClearControlIds;
    private List<Item> List_ClearControlItems;

    /*Call Show Message*/
    private String MessageType;
    private String MessageOn_Below_ControlID;
    private String MessageOn_Below_ParentControlID;
    private String MessageOn_Popup_Type;
    private String Message_Noraml;
    private String Message_Advanced;

    /*Notification*/
    private String Notification_Type;
    private String Notification_SMS_GatewayID;
    private String Notification_SMS_GatewayName;
    private String Notification_SMS_GatewayServiceURL;
    private String Notification_SMS_GatewayServiceCallsAt;
    private List<SMSGateways_InputDetails_Bean> smsGateways_inputDetails_beans=new ArrayList<>();
    private String Notification_SMS_TemplateID,Notification_SMS_TemplateName;
    private List<SMS_InputParam_Bean>  List_SMS_InputParam_Bean=new ArrayList<>();
    private String Notification_SMS_TemplateMessage;

    private String Notification_SMSNo_MappedType;
    private String Notification_SMSNo_MappedID;
    private String Notification_SMS_Message;
    private String Notification_eMail_FromID;
    private String Notification_eMail_ToID;
    private String Notification_eMail_Subject;
    private String Notification_eMail_Message;
    private String Notification_eMail_Gateway;

    private String Notification_InApp_MessageReceiver;
    private String Notification_InApp_Message;
    private String Notification_Email_Type;

    public String getNotification_InApp_MessageReceiverType() {
        return Notification_InApp_MessageReceiverType;
    }

    public void setNotification_InApp_MessageReceiverType(String notification_InApp_MessageReceiverType) {
        Notification_InApp_MessageReceiverType = notification_InApp_MessageReceiverType;
    }

    public String getNotification_InApp_MessageReceiverID() {
        return Notification_InApp_MessageReceiverID;
    }

    public void setNotification_InApp_MessageReceiverID(String notification_InApp_MessageReceiverID) {
        Notification_InApp_MessageReceiverID = notification_InApp_MessageReceiverID;
    }

    private String Notification_InApp_MessageReceiverType,Notification_InApp_MessageReceiverID;
    private boolean Notification_ReceiverId_ExpressionFlag=false;
    private String  Notification_ReceiverId_Expression;
    private boolean Notification_ExpressionFlag=false;
    private String Notification_InApp_TypeOfReceiver;
    private String Notification_Expression;

    /*Call Form */

    private String CallForm_Select_FormName;
    private DataManagementOptions dataManagementOptions;
    private VisibilityManagementOptions visibilityManagementOptions;
    /*Call Web*/

    private String CallWeb_Link;
    private String CallWeb_LinkType;
    private List<CallForm_ParamMapping_Bean>CallWeb_Params;

    /*Set GPS*/

    private String GeoTag_ControlName;
    private String GeoTag_GPSSource;
    private String GeoTag_GPSAccuracy;
    private String GeoTag_GPSType;
    private String GeoTag_GPSIntervalType;
    private String GeoTag_GPSIntervalValue;

    /*Defult and Exit Action in Submit Button Click event*/

    public boolean DefultSubmitAction;
    public boolean ExitFromApp;


    /*ShowMap*/
    private String Showmap_Control;
    private String Showmap_MappedValueType;
    private String Showmap_MappedValueID;
    private String Showmap_PointViewType;
    private String Showmap_Activity;
    private String Showmap_Marker;

    /*Set_Calender_event*/
    private String Calenderevent_Control;
    private String Calenderevent_Type;
    private String Calenderevent_Single_Date;
    private String Calenderevent_Multi_StartDate;
    private String Calenderevent_Multi_EndDate;
    private String Calenderevent_Message;

    /*Dial Number*/

    private String dialNumberValue;

    /*Text to voice*/

    private String voiceTextValue;

    /*Live tracking*/

    private String liveTrackingControl;

    /*Call SetFocus*/
    private String setfocus_controlId;
    private boolean setfocus_subform;
    private String setfocus_SubformName;
    private String setfocus_subform_PositionType;
    private String setfocus_Expression;

    /*Data table */
    private List<DataTableColumn_Bean> dataTableColumn_beanList = new ArrayList<>();
    private String dataTableRowHeight="40";
    private boolean dataTableRowHeightWrapContent;
    private boolean dataTableFixedWidthEnabled;

    /*Set Selection*/

    private List<SetSelectionControl_Bean> setSelectionControl_beans;

    /*Scan QR Code*/

    private String Scan_Name;

/*Download Excel, PDF and Print*/
    private String download_selectedControl;
    private String download_selectedControlType;

    /*Bhargo Login*/

    private String BhargoLogin_mobilenumber_MapValue;
    private String BhargoLogin_mobilenumber_MapType;
    private String BhargoLogin_OTP_MapValue;
    private String BhargoLogin_OTP_MapType;

    /*Change_Language*/

    private String ChangeLanguage_MapValue;
    private String ChangeLanguage_MapType;

    /*Set_Properties*/
    private String Prop_ControlName;
    private boolean Prop_Displayname;
    private String Prop_Displayname_value;
    private boolean Prop_hint;
    private String Prop_hint_Value;
    private boolean Prop_filePath;
    private String Prop_filePath_Type;
    private String Prop_filePath_TypeValue;
    private boolean Prop_multiImage_Alignment;
    private String Prop_multiImage_AlignmentType;
    private boolean Prop_hideDisplayName;
    private boolean Prop_enableHTMLViewer;
    private boolean Prop_makeItasSection;
    private boolean Prop_displayasBarCode;
    private boolean Prop_displayasQRCode;
    private boolean Prop_enableUNICode;
    private boolean Prop_maskcharacters;
    private boolean Prop_strikeText;

    private SetProperty setPropertyActionObject;

    private List<EnabledControl_Bean> enabledControl_beanList;

    /*Get Data*/

    private String getDataActionType;
    private String dataBaseTableType;
    private String connectionName;
    private String connectionId;
    private String connectionSource;
    private String getDataTableName;
    private String directQueryString;
    /*Get Data*/

    /*ManageDAta*/
    private String manageDataActionType;
    private String tableSettingsType;
    private String existingTableName;
    private String mapExistingType;
    private List<QueryFilterField_Bean> mainTableWhereConditionFields;
    private List<QueryFilterField_Bean> mainTableUpdateFields;
    private List<QueryFilterField_Bean> mainTableInsertFields;
    List<String> sqlNamesList = new ArrayList<>();
    /*ManageDAta*/
    /*Remove Row*/
    private RemoveRowAction removeRowAction;
    private String controlName;
    private String controlType;
    private String parentControlName;
    private String parentControlType;
    private String rowId;
    private List<SpinnerData> checkListIds;
    private String ddId;
    private boolean subformControl;

    private PopUpManagementAction popUpManagementAction;

    public String getDdId() {
        return ddId;
    }

    public void setDdId(String ddId) {
        this.ddId = ddId;
    }

    public List<SpinnerData> getCheckListIds() {
        return checkListIds;
    }

    public void setCheckListIds(List<SpinnerData> checkListIds) {
        this.checkListIds = checkListIds;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getParentControlName() {
        return parentControlName;
    }

    public void setParentControlName(String parentControlName) {
        this.parentControlName = parentControlName;
    }

    public String getParentControlType() {
        return parentControlType;
    }

    public RemoveRowAction getRemoveRowAction() {
        return removeRowAction;
    }

    public void setRemoveRowAction(RemoveRowAction removeRowAction) {
        this.removeRowAction = removeRowAction;
    }

    /*Remove Row*/
    /*Sync Form Data*/

    public SyncFormData getSyncFormData() {
        return syncFormData;
    }

    public void setSyncFormData(SyncFormData syncFormData) {
        this.syncFormData = syncFormData;
    }

    private SyncFormData syncFormData;


    public int getPositionInEvent() {
        return positionInEvent;
    }

    public void setPositionInEvent(int positionInEvent) {
        this.positionInEvent = positionInEvent;
    }

    public int getActionPos() {
        return actionPos;
    }

    public void setActionPos(int actionPos) {
        this.actionPos = actionPos;
    }

    public String getActionName() {
        return ActionName;
    }

    public void setActionName(String actionName) {
        ActionName = actionName;
    }

    public String getActionType() {
        return ActionType;
    }

    public void setActionType(String actionType) {
        ActionType = actionType;
    }

    public String getSelectedAPIName() {
        return SelectedAPIName;
    }

    public void setSelectedAPIName(String selectedAPIName) {
        SelectedAPIName = selectedAPIName;
    }

    public List<API_InputParam_Bean> getList_API_InParams() {
        return List_API_InParams;
    }

    public void setList_API_InParams(List<API_InputParam_Bean> list_API_InParams) {
        List_API_InParams.addAll(list_API_InParams) ;
    }

    public List<API_OutputParam_Bean> getList_API_OutParams() {
        return List_API_OutParams;
    }

    public void setList_API_OutParams(List<API_OutputParam_Bean> list_API_OutParams) {
        List_API_OutParams.addAll(list_API_OutParams) ;
    }

    public String getResult_NoOfRecords() {
        return Result_NoOfRecords;
    }

    public void setResult_NoOfRecords(String result_NoOfRecords) {
        Result_NoOfRecords = result_NoOfRecords;
    }

    public String getResult_DisplayType() {
        return Result_DisplayType;
    }

    public void setResult_DisplayType(String result_DisplayType) {
        Result_DisplayType = result_DisplayType;
    }

    public String getResult_ListView_FilterItem() {
        return Result_ListView_FilterItem;
    }

    public void setResult_ListView_FilterItem(String result_ListView_FilterItem) {
        Result_ListView_FilterItem = result_ListView_FilterItem;
    }

    public String getResult_ListView_FilterItemID() {
        return Result_ListView_FilterItemID;
    }

    public void setResult_ListView_FilterItemID(String result_ListView_FilterItemID) {
        Result_ListView_FilterItemID = result_ListView_FilterItemID;
    }

    public String getResult_ListView_FilterMappedControl() {
        return Result_ListView_FilterMappedControl;
    }

    public void setResult_ListView_FilterMappedControl(String result_ListView_FilterMappedControl) {
        Result_ListView_FilterMappedControl = result_ListView_FilterMappedControl;
    }

    public String getSaveOfflineType() {
        return SaveOfflineType;
    }

    public void setSaveOfflineType(String saveOfflineType) {
        SaveOfflineType = saveOfflineType;
    }

    public String getMulti_DataType() {
        return Multi_DataType;
    }

    public void setMulti_DataType(String multi_DataType) {
        Multi_DataType = multi_DataType;
    }

    public String getSelectedSubForm() {
        return selectedSubForm;
    }

    public void setSelectedSubForm(String selectedSubForm) {
        this.selectedSubForm = selectedSubForm;
    }

    public String getServiceSource() {
        return ServiceSource;
    }

    public void setServiceSource(String serviceSource) {
        ServiceSource = serviceSource;
    }


    public String getProfileImage_Mapped_item() {
        return ProfileImage_Mapped_item;
    }

    public void setProfileImage_Mapped_item(String profileImage_Mapped_item) {
        ProfileImage_Mapped_item = profileImage_Mapped_item;
    }

    public String getImage_Mapped_item() {
        return Image_Mapped_item;
    }

    public void setImage_Mapped_item(String image_Mapped_item) {
        Image_Mapped_item = image_Mapped_item;
    }

    public String getImageAdvanced_ImageorNot() {
        return ImageAdvanced_ImageorNot;
    }

    public void setImageAdvanced_ImageorNot(String imageAdvanced_ImageorNot) {
        ImageAdvanced_ImageorNot = imageAdvanced_ImageorNot;
    }

    public String getImageAdvanced_ImageSource() {
        return ImageAdvanced_ImageSource;
    }

    public void setImageAdvanced_ImageSource(String imageAdvanced_ImageSource) {
        ImageAdvanced_ImageSource = imageAdvanced_ImageSource;
    }

    public String getImageAdvanced_ConditionColumn() {
        return ImageAdvanced_ConditionColumn;
    }

    public void setImageAdvanced_ConditionColumn(String imageAdvanced_ConditionColumn) {
        ImageAdvanced_ConditionColumn = imageAdvanced_ConditionColumn;
    }

    public String getImageAdvanced_Operator() {
        return ImageAdvanced_Operator;
    }

    public void setImageAdvanced_Operator(String imageAdvanced_Operator) {
        ImageAdvanced_Operator = imageAdvanced_Operator;
    }

    public List<ImageAdvanced_Mapped_Item> getList_ImageAdvanced_Items() {
        return List_ImageAdvanced_Items;
    }

    public void setList_ImageAdvanced_Items(List<ImageAdvanced_Mapped_Item> list_ImageAdvanced_Items) {
        List_ImageAdvanced_Items = list_ImageAdvanced_Items;
    }

    public String getCorner_Mapped_item() {
        return Corner_Mapped_item;
    }

    public void setCorner_Mapped_item(String corner_Mapped_item) {
        Corner_Mapped_item = corner_Mapped_item;
    }

    public String getHeader_Mapped_item() {
        return Header_Mapped_item;
    }

    public void setHeader_Mapped_item(String header_Mapped_item) {
        Header_Mapped_item = header_Mapped_item;
    }

    public String getSubHeader_Mapped_item() {
        return SubHeader_Mapped_item;
    }

    public void setSubHeader_Mapped_item(String subHeader_Mapped_item) {
        SubHeader_Mapped_item = subHeader_Mapped_item;
    }

    public List<String> getDescription_Mapped_item() {
        return Description_Mapped_item;
    }

    public void setDescription_Mapped_item(List<String> description_Mapped_item) {
        Description_Mapped_item = description_Mapped_item;
    }

    public String getDateandTime_Mapped_item() {
        return DateandTime_Mapped_item;
    }

    public void setDateandTime_Mapped_item(String dateandTime_Mapped_item) {
        DateandTime_Mapped_item = dateandTime_Mapped_item;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(String working_hours) {
        this.working_hours = working_hours;
    }

    public String getItem_one_count() {
        return item_one_count;
    }

    public void setItem_one_count(String item_one_count) {
        this.item_one_count = item_one_count;
    }

    public String getItem_two_count() {
        return item_two_count;
    }

    public void setItem_two_count(String item_two_count) {
        this.item_two_count = item_two_count;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSource_icon() {
        return source_icon;
    }

    public void setSource_icon(String source_icon) {
        this.source_icon = source_icon;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getSource_time() {
        return source_time;
    }

    public void setSource_time(String source_time) {
        this.source_time = source_time;
    }

    public String getNews_type() {
        return news_type;
    }

    public void setNews_type(String news_type) {
        this.news_type = news_type;
    }

    public List<Variable_Bean> getList_Varibles() {
        return List_Varibles;
    }

    public void setList_Varibles(List<Variable_Bean> list_Varibles) {
        List_Varibles = list_Varibles;
    }


    public String getSelect_FormName() {
        return Select_FormName;
    }

    public void setSelect_FormName(String select_FormName) {
        Select_FormName = select_FormName;
    }

    public String getSelect_FormAppMode() {
        return Select_FormAppMode;
    }

    public void setSelect_FormAppMode(String select_FormAppMode) {
        Select_FormAppMode = select_FormAppMode;
    }

    public String getSelect_FormTableName() {
        return Select_FormTableName;
    }

    public void setSelect_FormTableName(String select_FormTableName) {
        Select_FormTableName = select_FormTableName;
    }

    public List<API_InputParam_Bean> getList_Form_InParams() {
        return List_Form_InParams;
    }

    public void setList_Form_InParams(List<API_InputParam_Bean> list_Form_InParams) {
        List_Form_InParams = list_Form_InParams;
    }

    public List<API_OutputParam_Bean> getList_Form_OutParams() {
        return List_Form_OutParams;
    }

    public void setList_Form_OutParams(List<API_OutputParam_Bean> list_Form_OutParams) {
        List_Form_OutParams = list_Form_OutParams;
    }

    public String getForm_Result_NoOfRecords() {
        return Form_Result_NoOfRecords;
    }

    public void setForm_Result_NoOfRecords(String form_Result_NoOfRecords) {
        Form_Result_NoOfRecords = form_Result_NoOfRecords;
    }

    public String getForm_DisplayType() {
        return Form_DisplayType;
    }

    public void setForm_DisplayType(String form_DisplayType) {
        Form_DisplayType = form_DisplayType;
    }

    public String getForm_ListView_FilterItem() {
        return Form_ListView_FilterItem;
    }

    public void setForm_ListView_FilterItem(String form_ListView_FilterItem) {
        Form_ListView_FilterItem = form_ListView_FilterItem;
    }

    public String getForm_NewTabel() {
        return Form_NewTabel;
    }

    public void setForm_NewTabel(String form_NewTabel) {
        Form_NewTabel = form_NewTabel;
    }

    public List<String> getList_DisableControlIds() {
        return List_DisableControlIds;
    }

    public void setList_DisableControlIds(List<String> list_DisableControlIds) {
        List_DisableControlIds = list_DisableControlIds;
    }

    public List<String> getList_EnableControlIds() {
        return List_EnableControlIds;
    }

    public void setList_EnableControlIds(List<String> list_EnableControlIds) {
        List_EnableControlIds = list_EnableControlIds;
    }

    public List<String> getList_VisibleOnControlIds() {
        return List_VisibleOnControlIds;
    }

    public void setList_VisibleOnControlIds(List<String> list_VisibleOnControlIds) {
        List_VisibleOnControlIds = list_VisibleOnControlIds;
    }

    public List<SubControls_Advance_Bean> getList_SubControl_Advance() {
        return List_SubControl_Advance;
    }

    public void setList_SubControl_Advance(List<SubControls_Advance_Bean> list_SubControl_Advance) {
        List_SubControl_Advance = list_SubControl_Advance;
    }

    public List<String> getList_VisibleOffControlIds() {
        return List_VisibleOffControlIds;
    }

    public void setList_VisibleOffControlIds(List<String> list_VisibleOffControlIds) {
        List_VisibleOffControlIds = list_VisibleOffControlIds;
    }



    public String getSetvalue_TypeofSource() {
        return Setvalue_TypeofSource;
    }

    public void setSetvalue_TypeofSource(String setvalue_TypeofSource) {
        Setvalue_TypeofSource = setvalue_TypeofSource;
    }

    public String getSetvalue_ControleID() {
        return Setvalue_ControleID;
    }

    public void setSetvalue_ControleID(String setvalue_ControleID) {
        Setvalue_ControleID = setvalue_ControleID;
    }

    public String getSetValue_TypeOfValue() {
        return SetValue_TypeOfValue;
    }

    public void setSetValue_TypeOfValue(String setValue_TypeOfValue) {
        SetValue_TypeOfValue = setValue_TypeOfValue;
    }

    public String getSetValue_TypeOfAppend() {
        return SetValue_TypeOfAppend;
    }

    public void setSetValue_TypeOfAppend(String setValue_TypeOfAppend) {
        SetValue_TypeOfAppend = setValue_TypeOfAppend;
    }

    public boolean isSetValue_MultiValueExpression() {
        return SetValue_MultiValueExpression;
    }

    public void setSetValue_MultiValueExpression(boolean setValue_MultiValueExpression) {
        SetValue_MultiValueExpression = setValue_MultiValueExpression;
    }

    public String getSetValue_MultiValueExpressionBody() {
        return SetValue_MultiValueExpressionBody;
    }

    public void setSetValue_MultiValueExpressionBody(String setValue_MultiValueExpressionBody) {
        SetValue_MultiValueExpressionBody = setValue_MultiValueExpressionBody;
    }

    public List<String> getSetValue_AppendValues() {
        return SetValue_AppendValues;
    }

    public void setSetValue_AppendValues(List<String> setValue_AppendValues) {
        SetValue_AppendValues = setValue_AppendValues;
    }

    public String getSetValue_Advanced_Value() {
        return SetValue_Advanced_Value;
    }

    public void setSetValue_Advanced_Value(String setValue_Advanced_Value) {
        SetValue_Advanced_Value = setValue_Advanced_Value;
    }

    public String getSetValue_Normal_Value() {
        return SetValue_Normal_Value;
    }

    public void setSetValue_Normal_Value(String setValue_Normal_Value) {
        SetValue_Normal_Value = setValue_Normal_Value;
    }

    public boolean isSetValue_Subform() {
        return SetValue_Subform;
    }

    public void setSetValue_Subform(boolean setValue_Subform) {
        SetValue_Subform = setValue_Subform;
    }

    public String getSetvalue_SubFormName() {
        return Setvalue_SubFormName;
    }

    public void setSetvalue_SubFormName(String setvalue_SubFormName) {
        Setvalue_SubFormName = setvalue_SubFormName;
    }

    public String getSetvalue_SubFormControlName() {
        return Setvalue_SubFormControlName;
    }

    public void setSetvalue_SubFormControlName(String setvalue_SubFormControlName) {
        Setvalue_SubFormControlName = setvalue_SubFormControlName;
    }

    public String getSetvalue_SubFormPosition() {
        return Setvalue_SubFormPosition;
    }

    public void setSetvalue_SubFormPosition(String setvalue_SubFormPosition) {
        Setvalue_SubFormPosition = setvalue_SubFormPosition;
    }

    public String getSetvalue_SubFormDynamicExpression() {
        return Setvalue_SubFormDynamicExpression;
    }

    public void setSetvalue_SubFormDynamicExpression(String setvalue_SubFormDynamicExpression) {
        Setvalue_SubFormDynamicExpression = setvalue_SubFormDynamicExpression;
    }

    public List<String> getList_ClearControlIds() {
        return List_ClearControlIds;
    }

    public void setList_ClearControlIds(List<String> list_ClearControlIds) {
        List_ClearControlIds = list_ClearControlIds;
    }

    public List<Item> getList_ClearControlItems() {
        return List_ClearControlItems;
    }

    public void setList_ClearControlItems(List<Item> list_ClearControlItems) {
        List_ClearControlItems = list_ClearControlItems;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public String getMessageOn_Below_ControlID() {
        return MessageOn_Below_ControlID;
    }

    public void setMessageOn_Below_ControlID(String messageOn_Below_ControlID) {
        MessageOn_Below_ControlID = messageOn_Below_ControlID;
    }

    public String getMessageOn_Below_ParentControlID() {
        return MessageOn_Below_ParentControlID;
    }

    public void setMessageOn_Below_ParentControlID(String messageOn_Below_ParentControlID) {
        MessageOn_Below_ParentControlID = messageOn_Below_ParentControlID;
    }

    public String getMessageOn_Popup_Type() {
        return MessageOn_Popup_Type;
    }

    public void setMessageOn_Popup_Type(String messageOn_Popup_Type) {
        MessageOn_Popup_Type = messageOn_Popup_Type;
    }

    public String getMessage_Noraml() {
        return Message_Noraml;
    }

    public void setMessage_Noraml(String message_Noraml) {
        Message_Noraml = message_Noraml;
    }

    public String getMessage_Advanced() {
        return Message_Advanced;
    }

    public void setMessage_Advanced(String message_Advanced) {
        Message_Advanced = message_Advanced;
    }

    public String getNotification_Type() {
        return Notification_Type;
    }

    public void setNotification_Type(String notification_Type) {
        Notification_Type = notification_Type;
    }



    public String getNotification_SMSNo_MappedType() {
        return Notification_SMSNo_MappedType;
    }

    public void setNotification_SMSNo_MappedType(String notification_SMSNo_MappedType) {
        Notification_SMSNo_MappedType = notification_SMSNo_MappedType;
    }

    public String getNotification_SMSNo_MappedID() {
        return Notification_SMSNo_MappedID;
    }

    public void setNotification_SMSNo_MappedID(String notification_SMSNo_MappedID) {
        Notification_SMSNo_MappedID = notification_SMSNo_MappedID;
    }

    public String getNotification_SMS_Message() {
        return Notification_SMS_Message;
    }

    public void setNotification_SMS_Message(String notification_SMS_Message) {
        Notification_SMS_Message = notification_SMS_Message;
    }

    public String getNotification_eMail_FromID() {
        return Notification_eMail_FromID;
    }

    public void setNotification_eMail_FromID(String notification_eMail_FromID) {
        Notification_eMail_FromID = notification_eMail_FromID;
    }

    public String getNotification_eMail_ToID() {
        return Notification_eMail_ToID;
    }

    public void setNotification_eMail_ToID(String notification_eMail_ToID) {
        Notification_eMail_ToID = notification_eMail_ToID;
    }

    public String getNotification_eMail_Subject() {
        return Notification_eMail_Subject;
    }

    public void setNotification_eMail_Subject(String notification_eMail_Subject) {
        Notification_eMail_Subject = notification_eMail_Subject;
    }

    public String getNotification_eMail_Message() {
        return Notification_eMail_Message;
    }

    public void setNotification_eMail_Message(String notification_eMail_Message) {
        Notification_eMail_Message = notification_eMail_Message;
    }
    public String getNotification_InApp_MessageReceiver() {
        return Notification_InApp_MessageReceiver;
    }

    public String getNotification_eMail_Gateway() {
        return Notification_eMail_Gateway;
    }

    public void setNotification_eMail_Gateway(String notification_eMail_Gateway) {
        Notification_eMail_Gateway = notification_eMail_Gateway;
    }

    public String getNotification_Email_Type() {
        return Notification_Email_Type;
    }

    public void setNotification_Email_Type(String notification_Email_Type) {
        Notification_Email_Type = notification_Email_Type;
    }

    public void setNotification_InApp_MessageReceiver(String notification_InApp_MessageReceiver) {
        Notification_InApp_MessageReceiver = notification_InApp_MessageReceiver;
    }

    public boolean isNotification_ReceiverId_ExpressionFlag() {
        return Notification_ReceiverId_ExpressionFlag;
    }

    public void setNotification_ReceiverId_ExpressionFlag(boolean notification_ReceiverId_ExpressionFlag) {
        Notification_ReceiverId_ExpressionFlag = notification_ReceiverId_ExpressionFlag;
    }

    public String getNotification_ReceiverId_Expression() {
        return Notification_ReceiverId_Expression;
    }

    public void setNotification_ReceiverId_Expression(String notification_ReceiverId_Expression) {
        Notification_ReceiverId_Expression = notification_ReceiverId_Expression;
    }

    public String getNotification_InApp_Message() {
        return Notification_InApp_Message;
    }

    public void setNotification_InApp_Message(String notification_InApp_Message) {
        Notification_InApp_Message = notification_InApp_Message;
    }

    public boolean isNotification_ExpressionFlag() {
        return Notification_ExpressionFlag;
    }

    public void setNotification_ExpressionFlag(boolean notification_ExpressionFlag) {
        Notification_ExpressionFlag = notification_ExpressionFlag;
    }

    public String getNotification_InApp_TypeOfReceiver() {
        return Notification_InApp_TypeOfReceiver;
    }

    public void setNotification_InApp_TypeOfReceiver(String notification_InApp_TypeOfReceiver) {
        Notification_InApp_TypeOfReceiver = notification_InApp_TypeOfReceiver;
    }

    public String getNotification_Expression() {
        return Notification_Expression;
    }

    public void setNotification_Expression(String notification_Expression) {
        Notification_Expression = notification_Expression;
    }

    public String getCallForm_Select_FormName() {
        return CallForm_Select_FormName;
    }

    public void setCallForm_Select_FormName(String callForm_Select_FormName) {
        CallForm_Select_FormName = callForm_Select_FormName;
    }

    public boolean isGpsCaptured() {
        return gpsCaptured;
    }

    public void setGpsCaptured(boolean gpsCaptured) {
        this.gpsCaptured = gpsCaptured;
    }

    public DataManagementOptions getDataManagementOptions() {
        return dataManagementOptions;
    }

    public void setDataManagementOptions(DataManagementOptions dataManagementOptions) {
        this.dataManagementOptions = dataManagementOptions;
    }

    public VisibilityManagementOptions getVisibilityManagementOptions() {
        return visibilityManagementOptions;
    }

    public void setVisibilityManagementOptions(VisibilityManagementOptions visibilityManagementOptions) {
        this.visibilityManagementOptions = visibilityManagementOptions;
    }

    public String getCallWeb_Link() {
        return CallWeb_Link;
    }

    public void setCallWeb_Link(String callWeb_Link) {
        CallWeb_Link = callWeb_Link;
    }

    public String getCallWeb_LinkType() {
        return CallWeb_LinkType;
    }

    public void setCallWeb_LinkType(String callWeb_LinkType) {
        CallWeb_LinkType = callWeb_LinkType;
    }

    public List<CallForm_ParamMapping_Bean> getCallWeb_Params() {
        return CallWeb_Params;
    }

    public void setCallWeb_Params(List<CallForm_ParamMapping_Bean> callWeb_Params) {
        CallWeb_Params = callWeb_Params;
    }

    public String getGeoTag_ControlName() {
        return GeoTag_ControlName;
    }

    public void setGeoTag_ControlName(String geoTag_ControlName) {
        GeoTag_ControlName = geoTag_ControlName;
    }

    public String getGeoTag_GPSSource() {
        return GeoTag_GPSSource;
    }

    public void setGeoTag_GPSSource(String geoTag_GPSSource) {
        GeoTag_GPSSource = geoTag_GPSSource;
    }

    public String getGeoTag_GPSAccuracy() {
        return GeoTag_GPSAccuracy;
    }

    public void setGeoTag_GPSAccuracy(String geoTag_GPSAccuracy) {
        GeoTag_GPSAccuracy = geoTag_GPSAccuracy;
    }

    public String getGeoTag_GPSType() {
        return GeoTag_GPSType;
    }

    public void setGeoTag_GPSType(String geoTag_GPSType) {
        GeoTag_GPSType = geoTag_GPSType;
    }

    public String getGeoTag_GPSIntervalType() {
        return GeoTag_GPSIntervalType;
    }

    public void setGeoTag_GPSIntervalType(String geoTag_GPSIntervalType) {
        GeoTag_GPSIntervalType = geoTag_GPSIntervalType;
    }

    public String getGeoTag_GPSIntervalValue() {
        return GeoTag_GPSIntervalValue;
    }

    public void setGeoTag_GPSIntervalValue(String geoTag_GPSIntervalValue) {
        GeoTag_GPSIntervalValue = geoTag_GPSIntervalValue;
    }

    public boolean isDefultSubmitAction() {
        return DefultSubmitAction;
    }

    public void setDefultSubmitAction(boolean defultSubmitAction) {
        DefultSubmitAction = defultSubmitAction;
    }

    public boolean isExitFromApp() {
        return ExitFromApp;
    }

    public void setExitFromApp(boolean exitFromApp) {
        ExitFromApp = exitFromApp;
    }

    public String getShowmap_Control() {
        return Showmap_Control;
    }

    public void setShowmap_Control(String showmap_Control) {
        Showmap_Control = showmap_Control;
    }

    public String getShowmap_MappedValueType() {
        return Showmap_MappedValueType;
    }

    public void setShowmap_MappedValueType(String showmap_MappedValueType) {
        Showmap_MappedValueType = showmap_MappedValueType;
    }

    public String getShowmap_MappedValueID() {
        return Showmap_MappedValueID;
    }

    public void setShowmap_MappedValueID(String showmap_MappedValueID) {
        Showmap_MappedValueID = showmap_MappedValueID;
    }

    public String getShowmap_PointViewType() {
        return Showmap_PointViewType;
    }

    public void setShowmap_PointViewType(String showmap_PointViewType) {
        Showmap_PointViewType = showmap_PointViewType;
    }

    public String getShowmap_Activity() {
        return Showmap_Activity;
    }

    public void setShowmap_Activity(String showmap_Activity) {
        Showmap_Activity = showmap_Activity;
    }

    public String getShowmap_Marker() {
        return Showmap_Marker;
    }

    public void setShowmap_Marker(String showmap_Marker) {
        Showmap_Marker = showmap_Marker;
    }

    public String getCalenderevent_Control() {
        return Calenderevent_Control;
    }

    public void setCalenderevent_Control(String calenderevent_Control) {
        Calenderevent_Control = calenderevent_Control;
    }

    public String getCalenderevent_Type() {
        return Calenderevent_Type;
    }

    public void setCalenderevent_Type(String calenderevent_Type) {
        Calenderevent_Type = calenderevent_Type;
    }

    public String getCalenderevent_Single_Date() {
        return Calenderevent_Single_Date;
    }

    public void setCalenderevent_Single_Date(String calenderevent_Single_Date) {
        Calenderevent_Single_Date = calenderevent_Single_Date;
    }

    public String getCalenderevent_Multi_StartDate() {
        return Calenderevent_Multi_StartDate;
    }

    public void setCalenderevent_Multi_StartDate(String calenderevent_Multi_StartDate) {
        Calenderevent_Multi_StartDate = calenderevent_Multi_StartDate;
    }

    public String getCalenderevent_Multi_EndDate() {
        return Calenderevent_Multi_EndDate;
    }

    public void setCalenderevent_Multi_EndDate(String calenderevent_Multi_EndDate) {
        Calenderevent_Multi_EndDate = calenderevent_Multi_EndDate;
    }

    public String getCalenderevent_Message() {
        return Calenderevent_Message;
    }

    public void setCalenderevent_Message(String calenderevent_Message) {
        Calenderevent_Message = calenderevent_Message;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public boolean isCloseParentEnabled() {
        return closeParentEnabled;
    }

    public void setCloseParentEnabled(boolean closeParentEnabled) {
        this.closeParentEnabled = closeParentEnabled;
    }

    public boolean isKeepSessionEnabled() {
        return keepSessionEnabled;
    }

    public void setKeepSessionEnabled(boolean keepSessionEnabled) {
        this.keepSessionEnabled = keepSessionEnabled;
    }

    public boolean isCloseAllFormsEnabled() {
        return closeAllFormsEnabled;
    }

    public void setCloseAllFormsEnabled(boolean closeAllFormsEnabled) {
        this.closeAllFormsEnabled = closeAllFormsEnabled;
    }

    public boolean isGoToHomeEnabled() {
        return goToHomeEnabled;
    }

    public void setGoToHomeEnabled(boolean goToHomeEnabled) {
        this.goToHomeEnabled = goToHomeEnabled;
    }

    public String getDialNumberValue() {
        return dialNumberValue;
    }

    public void setDialNumberValue(String dialNumberValue) {
        this.dialNumberValue = dialNumberValue;
    }

    public String getVoiceTextValue() {
        return voiceTextValue;
    }

    public void setVoiceTextValue(String voiceTextValue) {
        this.voiceTextValue = voiceTextValue;
    }

    public String getLiveTrackingControl() {
        return liveTrackingControl;
    }

    public void setLiveTrackingControl(String liveTrackingControl) {
        this.liveTrackingControl = liveTrackingControl;
    }

    public String getSetfocus_controlId() {
        return setfocus_controlId;
    }

    public void setSetfocus_controlId(String setfocus_controlId) {
        this.setfocus_controlId = setfocus_controlId;
    }

    public boolean isSetfocus_subform() {
        return setfocus_subform;
    }

    public void setSetfocus_subform(boolean setfocus_subform) {
        this.setfocus_subform = setfocus_subform;
    }

    public String getSetfocus_SubformName() {
        return setfocus_SubformName;
    }

    public void setSetfocus_SubformName(String setfocus_SubformName) {
        this.setfocus_SubformName = setfocus_SubformName;
    }

    public String getSetfocus_subform_PositionType() {
        return setfocus_subform_PositionType;
    }

    public void setSetfocus_subform_PositionType(String setfocus_subform_PositionType) {
        this.setfocus_subform_PositionType = setfocus_subform_PositionType;
    }

    public String getSetfocus_Expression() {
        return setfocus_Expression;
    }

    public void setSetfocus_Expression(String setfocus_Expression) {
        this.setfocus_Expression = setfocus_Expression;
    }

    public List<String> getSetValue_AppendValuesID() {
        return SetValue_AppendValuesID;
    }

    public void setSetValue_AppendValuesID(List<String> setValue_AppendValuesID) {
        SetValue_AppendValuesID = setValue_AppendValuesID;
    }

    /*Call Set Values New*/

    public String getSv_TypeofSource() {
        return sv_TypeofSource;
    }

    public void setSv_TypeofSource(String sv_TypeofSource) {
        this.sv_TypeofSource = sv_TypeofSource;
    }

    public String getSv_ControlType() {
        return sv_ControlType;
    }

    public void setSv_ControlType(String sv_ControlType) {
        this.sv_ControlType = sv_ControlType;
    }

    public AssignControl_Bean getSv_Single_List_AssignControls() {
        return sv_Single_List_AssignControls;
    }

    public void setSv_Single_List_AssignControls(AssignControl_Bean sv_Single_List_AssignControls) {
        this.sv_Single_List_AssignControls = sv_Single_List_AssignControls;
    }

    public String getSv_Multiple_ControlName() {
        return sv_Multiple_ControlName;
    }

    public void setSv_Multiple_ControlName(String sv_Multiple_ControlName) {
        this.sv_Multiple_ControlName = sv_Multiple_ControlName;
    }

    public String getSv_Multiple_RowType() {
        return sv_Multiple_RowType;
    }

    public void setSv_Multiple_RowType(String sv_Multiple_RowType) {
        this.sv_Multiple_RowType = sv_Multiple_RowType;
    }

    public String getSv_Multiple_Single_assignLevel() {
        return sv_Multiple_Single_assignLevel;
    }

    public void setSv_Multiple_Single_assignLevel(String sv_Multiple_Single_assignLevel) {
        this.sv_Multiple_Single_assignLevel = sv_Multiple_Single_assignLevel;
    }

    public boolean isSv_Multiple_Single_rowPosition_Expression() {
        return sv_Multiple_Single_rowPosition_Expression;
    }

    public void setSv_Multiple_Single_rowPosition_Expression(boolean sv_Multiple_Single_rowPosition_Expression) {
        this.sv_Multiple_Single_rowPosition_Expression = sv_Multiple_Single_rowPosition_Expression;
    }

    public String getSv_Multiple_Single_rowPosition() {
        return sv_Multiple_Single_rowPosition;
    }

    public void setSv_Multiple_Single_rowPosition(String sv_Multiple_Single_rowPosition) {
        this.sv_Multiple_Single_rowPosition = sv_Multiple_Single_rowPosition;
    }

    public String getSv_Multiple_multi_assignType() {
        return sv_Multiple_multi_assignType;
    }

    public void setSv_Multiple_multi_assignType(String sv_Multiple_multi_assignType) {
        this.sv_Multiple_multi_assignType = sv_Multiple_multi_assignType;
    }

    public List<AssignControl_Bean> getSv_Multiple_List_AssignControls() {
        return sv_Multiple_List_AssignControls;
    }

    public void setSv_Multiple_List_AssignControls(List<AssignControl_Bean> sv_Multiple_List_AssignControls) {
        this.sv_Multiple_List_AssignControls = sv_Multiple_List_AssignControls;
    }

    public List<AssignControl_Bean> getSv_single_control_assign_controls() {
        return sv_single_control_assign_controls;
    }

    public void setSv_single_control_assign_controls(List<AssignControl_Bean> sv_single_control_assign_controls) {
        this.sv_single_control_assign_controls = sv_single_control_assign_controls;
    }

    public List<String> getxAxisValues() {
        return xAxisValues;
    }

    public void setxAxisValues(List<String> xAxisValues) {
        this.xAxisValues = xAxisValues;
    }

    public List<String> getyAxisValues() {
        return yAxisValues;
    }

    public void setyAxisValues(List<String> yAxisValues) {
        this.yAxisValues = yAxisValues;
    }

    public List<Axis> getxAxisList() {
        return xAxisList;
    }

    public void setxAxisList(List<Axis> xAxisList) {
        this.xAxisList = xAxisList;
    }

    public List<Axis> getyAxisList() {
        return yAxisList;
    }

    public void setyAxisList(List<Axis> yAxisList) {
        this.yAxisList = yAxisList;
    }

    public String getPieChartControlName() {
        return pieChartControlName;
    }

    public void setPieChartControlName(String pieChartControlName) {
        this.pieChartControlName = pieChartControlName;
    }

    public String getPieChartMeasurementValue() {
        return pieChartMeasurementValue;
    }

    public void setPieChartMeasurementValue(String pieChartMeasurementValue) {
        this.pieChartMeasurementValue = pieChartMeasurementValue;
    }

    public List<String> getComboChartTypes() {
        return comboChartTypes;
    }

    public void setComboChartTypes(List<String> comboChartTypes) {
        this.comboChartTypes = comboChartTypes;
    }

    public List<DataTableColumn_Bean> getDataTableColumn_beanList() {
        return dataTableColumn_beanList;
    }

    public void setDataTableColumn_beanList(List<DataTableColumn_Bean> dataTableColumn_beanList) {
        this.dataTableColumn_beanList = dataTableColumn_beanList;
    }

    public String getDataTableRowHeight() {
        return dataTableRowHeight;
    }

    public void setDataTableRowHeight(String dataTableRowHeight) {
        this.dataTableRowHeight = dataTableRowHeight;
    }

    public boolean isDataTableRowHeightWrapContent() {
        return dataTableRowHeightWrapContent;
    }

    public void setDataTableRowHeightWrapContent(boolean dataTableRowHeightWrapContent) {
        this.dataTableRowHeightWrapContent = dataTableRowHeightWrapContent;
    }

    public boolean isDataTableFixedWidthEnabled() {
        return dataTableFixedWidthEnabled;
    }

    public void setDataTableFixedWidthEnabled(boolean dataTableFixedWidthEnabled) {
        this.dataTableFixedWidthEnabled = dataTableFixedWidthEnabled;
    }

    public String getSv_Map_DataSource() {
        return sv_Map_DataSource;
    }

    public void setSv_Map_DataSource(String sv_Map_DataSource) {
        this.sv_Map_DataSource = sv_Map_DataSource;
    }

    public boolean isSv_Map_DataSource_Expression() {
        return sv_Map_DataSource_Expression;
    }

    public void setSv_Map_DataSource_Expression(boolean sv_Map_DataSource_Expression) {
        this.sv_Map_DataSource_Expression = sv_Map_DataSource_Expression;
    }

    public String getSv_data_source() {
        return sv_data_source;
    }

    public void setSv_data_source(String sv_data_source) {
        this.sv_data_source = sv_data_source;
    }

    public List<SetSelectionControl_Bean> getSetSelectionControl_beans() {
        return setSelectionControl_beans;
    }

    public void setSetSelectionControl_beans(List<SetSelectionControl_Bean> setSelectionControl_beans) {
        this.setSelectionControl_beans = setSelectionControl_beans;
    }

    public List<SMS_InputParam_Bean> getList_SMS_InputParam_Bean() {
        return List_SMS_InputParam_Bean;
    }

    public void setList_SMS_InputParam_Bean(List<SMS_InputParam_Bean> list_SMS_InputParam_Bean) {
        List_SMS_InputParam_Bean = list_SMS_InputParam_Bean;
    }

    public String getDML_Input_Type() {
        return DML_Input_Type;
    }

    public void setDML_Input_Type(String DML_Input_Type) {
        this.DML_Input_Type = DML_Input_Type;
    }

    public String getDML_DataSource_Value() {
        return DML_DataSource_Value;
    }

    public void setDML_DataSource_Value(String DML_DataSource_Value) {
        this.DML_DataSource_Value = DML_DataSource_Value;
    }

    public boolean isDML_DataSource_ExpressionExists() {
        return DML_DataSource_ExpressionExists;
    }

    public void setDML_DataSource_ExpressionExists(boolean DML_DataSource_ExpressionExists) {
        this.DML_DataSource_ExpressionExists = DML_DataSource_ExpressionExists;
    }

    public String getDML_DataSource_ExpressionValue() {
        return DML_DataSource_ExpressionValue;
    }

    public void setDML_DataSource_ExpressionValue(String DML_DataSource_ExpressionValue) {
        this.DML_DataSource_ExpressionValue = DML_DataSource_ExpressionValue;
    }

    public String getScan_Name() {
        return Scan_Name;
    }

    public void setScan_Name(String scan_Name) {
        Scan_Name = scan_Name;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public String getOrderByColumns() {
        return orderByColumns;
    }

    public void setOrderByColumns(String orderByColumns) {
        this.orderByColumns = orderByColumns;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getNoDataMessageType() {
        return NoDataMessageType;
    }

    public void setNoDataMessageType(String noDataMessageType) {
        NoDataMessageType = noDataMessageType;
    }

    public String getNoDataMessageDisplayType() {
        return NoDataMessageDisplayType;
    }

    public void setNoDataMessageDisplayType(String noDataMessageDisplayType) {
        NoDataMessageDisplayType = noDataMessageDisplayType;
    }

    public String getNoDataMessage() {
        return NoDataMessage;
    }

    public void setNoDataMessage(String noDataMessage) {
        NoDataMessage = noDataMessage;
    }

    public String getMessage_Success() {
        return Message_Success;
    }

    public void setMessage_Success(String message_Success) {
        Message_Success = message_Success;
    }

    public String getMessage_SuccessDisplayType() {
        return Message_SuccessDisplayType;
    }

    public void setMessage_SuccessDisplayType(String message_SuccessDisplayType) {
        Message_SuccessDisplayType = message_SuccessDisplayType;
    }

    public boolean isSuccessMessageIsEnable() {
        return SuccessMessageIsEnable;
    }

    public void setSuccessMessageIsEnable(boolean successMessageIsEnable) {
        SuccessMessageIsEnable = successMessageIsEnable;
    }

    public String getMessage_SuccessNoRecords() {
        return Message_SuccessNoRecords;
    }

    public void setMessage_SuccessNoRecords(String message_SuccessNoRecords) {
        Message_SuccessNoRecords = message_SuccessNoRecords;
    }

    public String getMessage_SuccessNoRecordsDisplayType() {
        return Message_SuccessNoRecordsDisplayType;
    }

    public void setMessage_SuccessNoRecordsDisplayType(String message_SuccessNoRecordsDisplayType) {
        Message_SuccessNoRecordsDisplayType = message_SuccessNoRecordsDisplayType;
    }

    public boolean isMessage_SuccessNoRecordsIsEnable() {
        return Message_SuccessNoRecordsIsEnable;
    }

    public void setMessage_SuccessNoRecordsIsEnable(boolean message_SuccessNoRecordsIsEnable) {
        Message_SuccessNoRecordsIsEnable = message_SuccessNoRecordsIsEnable;
    }

    public String getMessage_Fail() {
        return Message_Fail;
    }

    public void setMessage_Fail(String message_Fail) {
        Message_Fail = message_Fail;
    }

    public String getMessage_FailNoRecordsDisplayType() {
        return Message_FailNoRecordsDisplayType;
    }

    public void setMessage_FailNoRecordsDisplayType(String message_FailNoRecordsDisplayType) {
        Message_FailNoRecordsDisplayType = message_FailNoRecordsDisplayType;
    }

    public boolean isMessage_FailNoRecordsIsEnable() {
        return Message_FailNoRecordsIsEnable;
    }

    public void setMessage_FailNoRecordsIsEnable(boolean message_FailNoRecordsIsEnable) {
        Message_FailNoRecordsIsEnable = message_FailNoRecordsIsEnable;
    }

    public String getBhargoLogin_mobilenumber_MapValue() {
        return BhargoLogin_mobilenumber_MapValue;
    }

    public void setBhargoLogin_mobilenumber_MapValue(String bhargoLogin_mobilenumber_MapValue) {
        BhargoLogin_mobilenumber_MapValue = bhargoLogin_mobilenumber_MapValue;
    }

    public String getBhargoLogin_mobilenumber_MapType() {
        return BhargoLogin_mobilenumber_MapType;
    }

    public void setBhargoLogin_mobilenumber_MapType(String bhargoLogin_mobilenumber_MapType) {
        BhargoLogin_mobilenumber_MapType = bhargoLogin_mobilenumber_MapType;
    }

    public String getBhargoLogin_OTP_MapValue() {
        return BhargoLogin_OTP_MapValue;
    }

    public void setBhargoLogin_OTP_MapValue(String bhargoLogin_OTP_MapValue) {
        BhargoLogin_OTP_MapValue = bhargoLogin_OTP_MapValue;
    }

    public String getBhargoLogin_OTP_MapType() {
        return BhargoLogin_OTP_MapType;
    }

    public void setBhargoLogin_OTP_MapType(String bhargoLogin_OTP_MapType) {
        BhargoLogin_OTP_MapType = bhargoLogin_OTP_MapType;
    }

    public String getChangeLanguage_MapValue() {
        return ChangeLanguage_MapValue;
    }

    public void setChangeLanguage_MapValue(String changeLanguage_MapValue) {
        ChangeLanguage_MapValue = changeLanguage_MapValue;
    }

    public String getChangeLanguage_MapType() {
        return ChangeLanguage_MapType;
    }

    public void setChangeLanguage_MapType(String changeLanguage_MapType) {
        ChangeLanguage_MapType = changeLanguage_MapType;
    }

    public String getProp_ControlName() {
        return Prop_ControlName;
    }

    public void setProp_ControlName(String prop_ControlName) {
        Prop_ControlName = prop_ControlName;
    }

    public boolean isProp_Displayname() {
        return Prop_Displayname;
    }

    public void setProp_Displayname(boolean prop_Displayname) {
        Prop_Displayname = prop_Displayname;
    }

    public String getProp_Displayname_value() {
        return Prop_Displayname_value;
    }

    public void setProp_Displayname_value(String prop_Displayname_value) {
        Prop_Displayname_value = prop_Displayname_value;
    }

    public boolean isProp_hint() {
        return Prop_hint;
    }

    public void setProp_hint(boolean prop_hint) {
        Prop_hint = prop_hint;
    }

    public String getProp_hint_Value() {
        return Prop_hint_Value;
    }

    public void setProp_hint_Value(String prop_hint_Value) {
        Prop_hint_Value = prop_hint_Value;
    }

    public boolean isProp_filePath() {
        return Prop_filePath;
    }

    public void setProp_filePath(boolean prop_filePath) {
        Prop_filePath = prop_filePath;
    }

    public String getProp_filePath_Type() {
        return Prop_filePath_Type;
    }

    public void setProp_filePath_Type(String prop_filePath_Type) {
        Prop_filePath_Type = prop_filePath_Type;
    }

    public String getProp_filePath_TypeValue() {
        return Prop_filePath_TypeValue;
    }

    public void setProp_filePath_TypeValue(String prop_filePath_TypeValue) {
        Prop_filePath_TypeValue = prop_filePath_TypeValue;
    }

    public boolean isProp_multiImage_Alignment() {
        return Prop_multiImage_Alignment;
    }

    public void setProp_multiImage_Alignment(boolean prop_multiImage_Alignment) {
        Prop_multiImage_Alignment = prop_multiImage_Alignment;
    }

    public String getProp_multiImage_AlignmentType() {
        return Prop_multiImage_AlignmentType;
    }

    public void setProp_multiImage_AlignmentType(String prop_multiImage_AlignmentType) {
        Prop_multiImage_AlignmentType = prop_multiImage_AlignmentType;
    }

    public boolean isProp_hideDisplayName() {
        return Prop_hideDisplayName;
    }

    public void setProp_hideDisplayName(boolean prop_hideDisplayName) {
        Prop_hideDisplayName = prop_hideDisplayName;
    }

    public boolean isProp_enableHTMLViewer() {
        return Prop_enableHTMLViewer;
    }

    public void setProp_enableHTMLViewer(boolean prop_enableHTMLViewer) {
        Prop_enableHTMLViewer = prop_enableHTMLViewer;
    }

    public boolean isProp_makeItasSection() {
        return Prop_makeItasSection;
    }

    public void setProp_makeItasSection(boolean prop_makeItasSection) {
        Prop_makeItasSection = prop_makeItasSection;
    }

    public boolean isProp_displayasBarCode() {
        return Prop_displayasBarCode;
    }

    public void setProp_displayasBarCode(boolean prop_displayasBarCode) {
        Prop_displayasBarCode = prop_displayasBarCode;
    }

    public boolean isProp_displayasQRCode() {
        return Prop_displayasQRCode;
    }

    public void setProp_displayasQRCode(boolean prop_displayasQRCode) {
        Prop_displayasQRCode = prop_displayasQRCode;
    }

    public boolean isProp_enableUNICode() {
        return Prop_enableUNICode;
    }

    public void setProp_enableUNICode(boolean prop_enableUNICode) {
        Prop_enableUNICode = prop_enableUNICode;
    }

    public boolean isProp_maskcharacters() {
        return Prop_maskcharacters;
    }

    public void setProp_maskcharacters(boolean prop_maskcharacters) {
        Prop_maskcharacters = prop_maskcharacters;
    }

    public boolean isProp_strikeText() {
        return Prop_strikeText;
    }

    public void setProp_strikeText(boolean prop_strikeText) {
        Prop_strikeText = prop_strikeText;
    }

    public String getDv_trans_id() {
        return dv_trans_id;
    }

    public void setDv_trans_id(String dv_trans_id) {
        this.dv_trans_id = dv_trans_id;
    }

    public void setEnabledControl_beanList(List<EnabledControl_Bean> enabledControl_beanList) {
        this.enabledControl_beanList = enabledControl_beanList;
    }

    public List<EnabledControl_Bean> getEnabledControl_beanList() {
        return enabledControl_beanList;
    }

    public String getGetGPSLocation_GPSMode() {
        return GetGPSLocation_GPSMode;
    }

    public void setGetGPSLocation_GPSMode(String getGPSLocation_GPSMode) {
        GetGPSLocation_GPSMode = getGPSLocation_GPSMode;
    }

    public String getGetGPSLocation_GPSAccuracy() {
        return GetGPSLocation_GPSAccuracy;
    }

    public void setGetGPSLocation_GPSAccuracy(String getGPSLocation_GPSAccuracy) {
        GetGPSLocation_GPSAccuracy = getGPSLocation_GPSAccuracy;
    }
    public String getGetGPSLocation_Type() {
        return GetGPSLocation_Type;
    }

    public void setGetGPSLocation_Type(String getGPSLocation_Type) {
        GetGPSLocation_Type = getGPSLocation_Type;
    }



    public String getGetGPSLocation_Control_Selected() {
        return GetGPSLocation_Control_Selected;
    }

    public void setGetGPSLocation_Control_Selected(String getGPSLocation_Control_Selected) {
        GetGPSLocation_Control_Selected = getGPSLocation_Control_Selected;
    }

    public String getGetGPSLocation_Variable_Selected() {
        return GetGPSLocation_Variable_Selected;
    }

    public void setGetGPSLocation_Variable_Selected(String getGPSLocation_Variable_Selected) {
        GetGPSLocation_Variable_Selected = getGPSLocation_Variable_Selected;
    }
    public String getManageCountDownTimer_Control_Selected() {
        return ManageCountDownTimer_Control_Selected;
    }

    public void setManageCountDownTimer_Control_Selected(String manageCountDownTimer_Control_Selected) {
        ManageCountDownTimer_Control_Selected = manageCountDownTimer_Control_Selected;
    }

    public String getManageCountDownTimer_Type() {
        return ManageCountDownTimer_Type;
    }

    public void setManageCountDownTimer_Type(String manageCountDownTimer_Type) {
        ManageCountDownTimer_Type = manageCountDownTimer_Type;
    }

    public String getManageCountDownTimer_Option() {
        return ManageCountDownTimer_Option;
    }

    public void setManageCountDownTimer_Option(String manageCountDownTimer_Option) {
        ManageCountDownTimer_Option = manageCountDownTimer_Option;
    }
    public String getManageCountUpTimer_Control_Selected() {
        return ManageCountUpTimer_Control_Selected;
    }

    public void setManageCountUpTimer_Control_Selected(String manageCountUpTimer_Control_Selected) {
        ManageCountUpTimer_Control_Selected = manageCountUpTimer_Control_Selected;
    }

    public String getManageCountUpTimer_Type() {
        return ManageCountUpTimer_Type;
    }

    public void setManageCountUpTimer_Type(String manageCountUpTimer_Type) {
        ManageCountUpTimer_Type = manageCountUpTimer_Type;
    }

    public String getManageCountUpTimer_Option() {
        return ManageCountUpTimer_Option;
    }

    public void setManageCountUpTimer_Option(String manageCountUpTimer_Option) {
        ManageCountUpTimer_Option = manageCountUpTimer_Option;
    }

    public SetProperty getSetPropertyActionObject() {
        return setPropertyActionObject;
    }

    public void setSetPropertyActionObject(SetProperty setPropertyActionObject) {
        this.setPropertyActionObject = setPropertyActionObject;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getWatsAppNo() {
        return watsAppNo;
    }

    public void setWatsAppNo(String watsAppNo) {
        this.watsAppNo = watsAppNo;
    }

    public String getDailNo() {
        return dailNo;
    }

    public void setDailNo(String dailNo) {
        this.dailNo = dailNo;
    }

    public String getNotification_SMS_GatewayID() {
        return Notification_SMS_GatewayID;
    }

    public void setNotification_SMS_GatewayID(String notification_SMS_GatewayID) {
        Notification_SMS_GatewayID = notification_SMS_GatewayID;
    }

    public String getNotification_SMS_GatewayName() {
        return Notification_SMS_GatewayName;
    }

    public void setNotification_SMS_GatewayName(String notification_SMS_GatewayName) {
        Notification_SMS_GatewayName = notification_SMS_GatewayName;
    }

    public String getNotification_SMS_GatewayServiceURL() {
        return Notification_SMS_GatewayServiceURL;
    }

    public void setNotification_SMS_GatewayServiceURL(String notification_SMS_GatewayServiceURL) {
        Notification_SMS_GatewayServiceURL = notification_SMS_GatewayServiceURL;
    }

    public String getNotification_SMS_GatewayServiceCallsAt() {
        return Notification_SMS_GatewayServiceCallsAt;
    }

    public void setNotification_SMS_GatewayServiceCallsAt(String notification_SMS_GatewayServiceCallsAt) {
        Notification_SMS_GatewayServiceCallsAt = notification_SMS_GatewayServiceCallsAt;
    }

    public List<SMSGateways_InputDetails_Bean> getSmsGateways_inputDetails_beans() {
        return smsGateways_inputDetails_beans;
    }

    public void setSmsGateways_inputDetails_beans(List<SMSGateways_InputDetails_Bean> smsGateways_inputDetails_beans) {
        this.smsGateways_inputDetails_beans = smsGateways_inputDetails_beans;
    }

    public String getNotification_SMS_TemplateMessage() {
        return Notification_SMS_TemplateMessage;
    }

    public void setNotification_SMS_TemplateMessage(String notification_SMS_TemplateMessage) {
        Notification_SMS_TemplateMessage = notification_SMS_TemplateMessage;
    }
    public String getNotification_SMS_TemplateID() {
        return Notification_SMS_TemplateID;
    }

    public void setNotification_SMS_TemplateID(String notification_SMS_TemplateID) {
        Notification_SMS_TemplateID = notification_SMS_TemplateID;
    }

    public String getNotification_SMS_TemplateName() {
        return Notification_SMS_TemplateName;
    }

    public void setNotification_SMS_TemplateName(String notification_SMS_TemplateName) {
        Notification_SMS_TemplateName = notification_SMS_TemplateName;
    }

    public String getGetDataActionType() {
        return getDataActionType;
    }

    public void setGetDataActionType(String getDataActionType) {
        this.getDataActionType = getDataActionType;
    }

    public String getDataBaseTableType() {
        return dataBaseTableType;
    }

    public void setDataBaseTableType(String dataBaseTableType) {
        this.dataBaseTableType = dataBaseTableType;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getConnectionSource() {
        return connectionSource;
    }

    public void setConnectionSource(String connectionSource) {
        this.connectionSource = connectionSource;
    }

    public String getGetDataTableName() {
        return getDataTableName;
    }

    public void setGetDataTableName(String getDataTableName) {
        this.getDataTableName = getDataTableName;
    }

    public String getDirectQueryString() {
        return directQueryString;
    }

    public void setDirectQueryString(String directQueryString) {
        this.directQueryString = directQueryString;
    }

    public String getManageDataActionType() {
        return manageDataActionType;
    }

    public void setManageDataActionType(String manageDataActionType) {
        this.manageDataActionType = manageDataActionType;
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

    public List<String> getSqlNamesList() {
        return sqlNamesList;
    }

    public void setSqlNamesList(List<String> sqlNamesList) {
        this.sqlNamesList = sqlNamesList;
    }

    public String getActionId() {
        return ActionId;
    }

    public void setActionId(String actionId) {
        ActionId = actionId;
    }

    public String getAddRowEvent_SubFormName() {
        return AddRowEvent_SubFormName;
    }

    public void setAddRowEvent_SubFormName(String addRowEvent_SubFormName) {
        AddRowEvent_SubFormName = addRowEvent_SubFormName;
    }

    public String getDeleteRowEvent_SubFormName() {
        return DeleteRowEvent_SubFormName;
    }

    public void setDeleteRowEvent_SubFormName(String deleteRowEvent_SubFormName) {
        DeleteRowEvent_SubFormName = deleteRowEvent_SubFormName;
    }

    public PopUpManagementAction getPopUpManagementAction() {
        return popUpManagementAction;
    }

    public void setPopUpManagementAction(PopUpManagementAction popUpManagementAction) {
        this.popUpManagementAction = popUpManagementAction;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getDownload_selectedControl() {
        return download_selectedControl;
    }

    public void setDownload_selectedControl(String download_selectedControl) {
        this.download_selectedControl = download_selectedControl;
    }

    public String getDownload_selectedControlType() {
        return download_selectedControlType;
    }

    public void setDownload_selectedControlType(String download_selectedControlType) {
        this.download_selectedControlType = download_selectedControlType;
    }

    public String get_APIFormDataExits() {
        return _APIFormDataExits;
    }

    public void set_APIFormDataExits(String _APIFormDataExits) {
        this._APIFormDataExits = _APIFormDataExits;
    }
    public String get_SelectedAPICategory() {
        return _SelectedAPICategory;
    }

    public void set_SelectedAPICategory(String _SelectedAPICategory) {
        this._SelectedAPICategory = _SelectedAPICategory;
    }
}
