package com.bhargo.user.utils;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.Control_EventObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.DataControl_Bean;
import com.bhargo.user.Java_Beans.MultiFormApp;
import com.bhargo.user.Java_Beans.New_DataControl_Bean;
import com.bhargo.user.Java_Beans.Variable_Bean;
import com.bhargo.user.pojos.AssessmentAnswer;
import com.bhargo.user.pojos.ContentInfoModel;
import com.bhargo.user.pojos.SpinnerAppsDataModel;
import com.bhargo.user.pojos.TaskDepEmpDataModel;
import com.bhargo.user.pojos.TaskDepGroupDataModel;
import com.bhargo.user.uisettings.pojos.UILayoutProperties;
import com.google.android.gms.maps.MapView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nk.mobileapps.spinnerlib.SearchableSpinner;

public class AppConstants {
    public static boolean dataControlRunnableFlag = false;
    public static final int PERMISSION_STORAGE_CODE = 1000;

    public static final String API_SMS = "/BhargoAPI/V1.0";
    //    public static final String API_NAME_CHANGE = "/ImproveApplications_V3.2"; // Main Bhargo
    //    public static final String API_NAME_CHANGE = "/Bargo_V4.0_Demo"; //Bhargo4.0
   public static final String API_NAME_CHANGE = "/Bhargo_V4.0_Demo_DbConnections"; //Bhargo4.0
//   public static final String API_NAME_CHANGE = "/bhargopg"; //Bhargo4.0 Test
   // public static final String API_NAME_CHANGE = "/WomenEmpowerment_v4.0"; //Women Empowerment
//        public static final String API_NAME_CHANGE = "/BHARGO_NDLMS"; // Main Bhargo
    //    public static final String API_NAME_CHANGE = "/SRUSHTIApplication_V3.2"; // SRUSHTI
    //   public static final String API_NAME_CHANGE = "/SelectEvApplications_V3.2"; // Select EV
    public static final String FILE_UPLOAD_METHOD = "/improve_v3.2"; // Main Bhargo
    //        public static final String FILE_UPLOAD_METHOD = "/BHARGO_NDLMS/"; // Main Bhargo
    public static final String GROUP_INFO_METHOD = "/BHARGO_WEB_DemoDbConnection_V4.0"; // Main Bhargo
    //   public static final String FILE_UPLOAD_METHOD = "/SRUSHTI_v3.2/"; // SRUSHTI
    //public static final String FILE_UPLOAD_METHOD = "/SelectEv_v3.2/"; // Select EV
    //public static boolean OfflineDataExist = false;

    public static final String OfflineDataExistKey = "OfflineDataExist";
    public static final String OneTimeForOfflineDataExistKey = "OneTimeForOfflineDataExist";

    /*Standard Control types */
    public static final String DATA_COLLECTION = "Datacollection";
    public static final String AUTO_REPORTS = "Auto Reports";
    public static final String QUERY_FORM = "QueryForm";
    public static final String CHILD_FORM = "CHILD";
    public static final String CHILD_FORM_NEW = "ChildForm";
    public static final String DASHBOARD = "DashBoard";
    public static final String REPORTS = "Reports";
    public static final String REPORT = "Report";
    public static final String WEB_VIEW = "WebView";
    public static final String WEB_VIEW_DATA = "Web_view_data";
    public static final String WEB_Link = "WebLink";
    public static final String MULTI_FORM = "MultiForm";
    public static final String ML = "ML";
    public static final String CONTROL_TYPE_TEXT_INPUT = "TextInput";
    public static final String CONTROL_TYPE_WIZARD = "WizardControl";
    public static final String CONTROL_TYPE_NUMERIC_INPUT = "NumericalInput";
    public static final String CONTROL_TYPE_PHONE = "Mobile";
    public static final String CONTROL_TYPE_EMAIL = "Email";
    public static final String CONTROL_TYPE_LARGE_INPUT = "LargeInput";
    public static final String CONTROL_TYPE_CAMERA = "Camera";
    public static final String WORKSPACE = "WorkSpace";
    /*Standard Control types */
    public static final String CONTROL_TYPE_FILE_BROWSING = "FileUpload";
    public static final String CONTROL_TYPE_CALENDER = "Calendar";
    public static final String CONTROL_TYPE_CHECKBOX = "CheckBox";
    public static final String CONTROL_TYPE_RADIO_BUTTON = "Radio";
    public static final String CONTROL_TYPE_DROP_DOWN = "Dropdown";
    public static final String CONTROL_TYPE_CHECK_LIST = "CheckList";
    public static final String CONTROL_TYPE_RATING = "Rating";
    public static final String CONTROL_TYPE_VOICE_RECORDING = "VoiceRecording";
    public static final String CONTROL_TYPE_VIDEO_RECORDING = "VideoRecording";
    public static final String CONTROL_TYPE_AUDIO_PLAYER = "AudioPlayer";
    public static final String CONTROL_TYPE_VIDEO_PLAYER = "VideoPlayer";
    public static final String CONTROL_TYPE_PERCENTAGE = "Percentage";
    public static final String CONTROL_TYPE_SIGNATURE = "Signature";
    public static final String CONTROL_TYPE_URL_LINK = "URLLink";
    public static final String CONTROL_TYPE_AUTO_NUMBER = "AutoNumber";
    public static final String CONTROL_TYPE_DECIMAL = "Decimal";
    public static final String CONTROL_TYPE_PASSWORD = "Password";
    public static final String CONTROL_TYPE_CURRENCY = "Currency";
    public static final String CONTROL_TYPE_IMAGE = "Image";
    public static final String CONTROL_TYPE_BUTTON = "Button";
    public static final String CONTROL_TYPE_CALENDAR_EVENT = "DateAndTime";
    public static final String CONTROL_TYPE_SUBMIT_BUTTON = "SubmitButton";
    //    public static final String CONTROL_TYPE_CALENDAR_EVENT= "CalendarEvent";
    /*Advance Controls*/
    public static final String CONTROL_TYPE_SUBFORM = "Subform";
    public static final String CONTROL_TYPE_GRID_CONTROL = "GridControl";
    public static final String CONTROL_TYPE_GPS = "GPS";
    public static final String CONTROL_TYPE_BAR_CODE = "BarCode";
    public static final String CONTROL_TYPE_QR_CODE = "QRCode";
    public static final String CONTROL_TYPE_DYNAMIC_LABEL = "DynamicLabel";
    public static final String CONTROL_TYPE_DATA = "DataControl";
    public static final String CONTROL_TYPE_MENU = "MenuControl";
    public static final String CONTROL_TYPE_MAP = "Map";
    public static final String CONTROL_TYPE_SECTION = "Section";
    public static final String CONTROL_TYPE_SECTIONEND = "SectionEnd";
    public static final String CONTROL_TYPE_MULTI_COMMENT = "MultiComment";
    public static final String CONTROL_TYPE_DATA_VIEWER = "DataViewer";
    public static final String CONTROL_TYPE_LiveTracking = "LiveTracking";
    public static final String CONTROL_TYPE_USER = "UserControl";
    public static final String CONTROL_TYPE_POST = "PostControl";
    public static final String CONTROL_TYPE_CHART = "CHART";
    public static final String CONTROL_TYPE_AUTO_GENERATION = "AutoNumbers";
    public static final String CONTROL_TYPE_DATA_TABLE = "DataTable";
    public static final String CONTROL_TYPE_TIME = "Time";
    public static final String CONTROL_TYPE_AUTO_COMPLETION = "AutoCompletion";
    public static final String CONTROL_TYPE_COUNT_DOWN_TIMER = "CountDownTimer";
    public static final String CONTROL_TYPE_COUNT_UP_TIMER = "CountUpTimer";
    public static final String CONTROL_TYPE_VIEWFILE = "ViewFile";
    public static final String CONTROL_TYPE_PAYMENT_GATEWAY = "PaymentGateWay";
    public static final String CONTROL_TYPE_CUSTOM_HEADER = "customheader";
    public static final String CONTROL_TYPE_CUSTOM_IMAGE = "customimage";
    public static final String IMG_URL = "http://103.117.174.17/ImproveUploadFiles/UploadFiles/EVStations/notifications_notifications.png";
    //    public static final boolean CUSTOM_TOOLBAR_REQUIRED = false;
    public static final String[] STANDARD_CONTROL_TYPES = {CONTROL_TYPE_TEXT_INPUT, CONTROL_TYPE_NUMERIC_INPUT, CONTROL_TYPE_PHONE, CONTROL_TYPE_EMAIL, CONTROL_TYPE_LARGE_INPUT, CONTROL_TYPE_CAMERA, CONTROL_TYPE_FILE_BROWSING, CONTROL_TYPE_CALENDER, CONTROL_TYPE_CHECKBOX, CONTROL_TYPE_RADIO_BUTTON, CONTROL_TYPE_DROP_DOWN, CONTROL_TYPE_CHECK_LIST, CONTROL_TYPE_RATING, CONTROL_TYPE_VOICE_RECORDING, CONTROL_TYPE_VIDEO_RECORDING, CONTROL_TYPE_AUDIO_PLAYER, CONTROL_TYPE_VIDEO_PLAYER, CONTROL_TYPE_PERCENTAGE, CONTROL_TYPE_SIGNATURE, CONTROL_TYPE_URL_LINK, CONTROL_TYPE_AUTO_NUMBER, CONTROL_TYPE_DECIMAL, CONTROL_TYPE_PASSWORD, CONTROL_TYPE_CURRENCY, CONTROL_TYPE_DYNAMIC_LABEL, CONTROL_TYPE_IMAGE, CONTROL_TYPE_BUTTON};
    public static final String[] ADVANCED_CONTROL_TYPES = {CONTROL_TYPE_SUBFORM, CONTROL_TYPE_GRID_CONTROL, CONTROL_TYPE_GPS, CONTROL_TYPE_BAR_CODE, CONTROL_TYPE_QR_CODE};
    public static final String ACTION_WITH_CONDITION = "ACTION_WITH_CONDITION";
    public static final String ACTION_WITH_OUT_CONDITION = "ACTION_WITH_OUT_CONDITION";
    public static final String All_conditions_satisfied = "All conditions satisfied";
    public static final String Atleast_one_condition_satisfied = "Atleast one condition satisfied";
    public static final String Static = "Static";
    public static final String Global = "Global";
    public static final String Global_PseudoControl = "SystemVariables";
    public static final String Global_submitresponse = "submitresponse";
    public static final String Global_ControlsOnForm = "ControlName";
    public static final String Global_GPSControl = "GPSControl";
    public static final String Global_SubControls = "SubControls";
    public static final String Global_DataTableControls = "DataTableControls";
    public static final String Global_DataViewerControls = "DataViewerControls";
    public static final String Global_API = "API";
    public static final String Global_FormFields = "FormFields";
    public static final String Global_GetData = "GetData";
    public static final String Global_ManageData = "ManageData";
    public static final String Global_SMS = "SMS";
    public static final String Global_OfflineTable = "offlinetables";
    public static final String Global_Query = "Query";
    public static final String Global_Dml = "dml";
    public static final String Global_variable = "Variables";
    public static final String Offline_variable = "OfflineVariable";
    public static final String Global_Calenders = "Calenders";
    public static final String Global_RequestOfflineData = "RequestOfflineData";
    public static final String Global_DataControls = "DataControls";
    public static final String Global_ScanName = "ScanName";
    public static final String Global_Transactional_offline = "transactionaloffline";
    public static final String Conditions_Equals = "Equals";
    public static final String Conditions_NotEquals = "NotEquals";
    public static final String Conditions_lessThan = "lessThan";
    public static final String Conditions_GreaterThan = "GreaterThan";
    public static final String Conditions_LessThanEqualsTo = "LessThanEqualsTo";
    public static final String Conditions_GreaterThanEqualsTo = "GreaterThanEqualsTo";
    public static final String Conditions_Contains = "Contains";
    public static final String Conditions_StartsWith = "StartsWith";
    public static final String Conditions_EndsWith = "EndsWith";
    public static final String Conditions_WithInDistance = "WithInDistance";
    public static final String Conditions_WithInBoundary = "WithInBoundary";
    public static final String Conditions_IsNull = "IsNull";
    public static final String Conditions_IsNotNull = "IsNotNull";
    public static final String Conditions_NearestStringMatch = "NearestStringMatch";
    //=======Actions_Names=======
    //Sync Form Data
    public static final String shareData = "ShareData";
    public static final String POP_UP_MANAGEMENT = "PopUpManagement";
    public static final String Remove_Row = "Remove Row";
    public static final String GET_DATA = "Get Data";
    public static final String MANAGE_DATA = "Manage Data";
    public static final String call_syncformdata = "Sync Form Data";
    public static final String call_api_query = "Call API/Query";
    public static final String call_form_fields = "Call Form Fields";
    public static final String call_sql = "Call SQL";
    public static final String call_dml = "Call DML";
    public static final String call_group_dml = "Call Group DML";
    public static final String disable_control = "Disable Control";
    public static final String enable_control = "Enable Control";
    public static final String visibility_on = "Visibility On";
    public static final String visibility_off = "Visibility Off";
    public static final String set_value = "Set Value";
    public static final String clear_control = "Clear Control";
    public static final String show_msg = "Show Message";
    public static final String notifications = "Notification";
    public static final String callform = "Call Form";
    public static final String callweb = "Call Web";
    public static final String setgps = "Set GPS";
    public static final String default_submit = "Default Submit";
    public static final String set_showmap = "Set ShowMap";
    public static final String set_calender_event = "Set Calender Event";
    public static final String set_dial_number = "Dial number";
    public static final String set_text_to_speech = "Text to speech";
    public static final String set_start_tracking = "Start live tracking";
    public static final String set_pause_tracking = "Pause live tracking";
    public static final String set_stop_tracking = "Stop live tracking";
    public static final String default_exit = "Exit from Application";
    public static final String FromNotification = "FromNotification";
    public static final String FromNotificationEV = "FromNotificationEV";
    public static final String Notification_PageName = "Notification_PageName";
    public static final String Notification_Back_Press = "Notification_Back_Press";
    public static final String Set_Focus = "Set Focus";
    public static final String Set_Selection = "Set Selection";
    public static final String Scan_QR_Code = "Scan QR Code";
    public static final String Call_Chat_Window = "Call Chat Window";
    public static final String Call_Notification_Window = "Call Notifications Window";
    public static final String Call_Apps_Window = "Call Apps Window";
    public static final String Call_Reports_Window = "Call Reports Window";
    public static final String Call_Tasks_Window = "Call Tasks Window";
    public static final String Call_ELearning_Window = "Call ELearning Window";
    public static final String Call_Bhargo_Home_Screen = "Call Bhargo Home Screen";
    public static final String Call_Bhargo_Login = "Bhargo Login";
    public static final String Call_Bhargo_Logout = "Call Logout";
    public static final String Change_Language = "Change Language";
    public static final String Set_Properties = "Set Properties";
    public static final String open_whatsapp = "Open Whatsapp";
    public static final String open_email = "Open Email";
    public static final String open_google_maps = "Open Google Maps";
    public static final String download_file = "Download File";
    public static final String get_gps_location = "Get GPS Location";
    public static final String manage_count_down_timer = "Manage Count Down Timer";
    public static final String manage_count_up_timer = "Manage Count Up Timer";
    public static final String set_properties = "Set Properties";
    public static final String add_row = "Add Row";
    public static final String delete_row = "Delete Row";
    /*Web Link*/
    public static final String GLOBAL_VALUE = "Global Value";
    public static final String STATIC_VALUE = "Static Value";
    public static final int QUERY_STRING_REQ_CODE = 573;
    public static final int REQUEST_GPS = 777;
    public static final int REQUEST_CURRENT_LOCATION = 551;
    public static final int REQUEST_GPS_ENABLE = 888;
    public static final int REQUEST_GO_CURRENT_LOCATION_Focus = 561;
    public static final int REQUEST_GO_CURRENT_LOCATION_TextChange = 562;
    public static final int REQUEST_GO_CURRENT_LOCATION_Change = 563;
    public static final int REQUEST_GO_CURRENT_LOCATION_Click = 564;
    public static final int REQUEST_GO_CURRENT_LOCATION_FormLoad = 565;
    public static final int REQUEST_GO_CURRENT_LOCATION_Submit = 566;
    public static final int REQUEST_GO_CURRENT_LOCATION_MenuClick = 567;
    public static final int REQUEST_GO_CURRENT_LOCATION_liveTrack = 568;
    public static final int REQUEST_GO_CURRENT_LOCATION_ON_FormLoad = 569;
    /*Default static values*/
    public static final String Default_OrgID = "Bhargo Innovations";
    public static final String Default_UserID = "BLUE0001";
    public static final String Default_MobileNo = "9885549002";
    /*Default static values End */
    /*Shared preferences Names*/
    public static final String SP_USER_DETAILS = "UserDeatils";
    public static final String SP_USER_POST_DETAILS = "UserPostDetails";
    public static final String SP_REPORTING_USER_DETAILS = "ReportingUserDeatils";
    public static final String SP_ROLE_MASTER_DETAILS = "role_master";
    public static final String SP_USER_TYPE_MASTER_DETAILS = "user_type_master";
    public static final String SP_POST_MASTER_DETAILS = "post_master";
    public static final String CONTROL_TYPE_DATA_CONTROL = "DataControl";
    public static final String SP_ORGANISATION_ID = "organisation_id";
    public static final String SP_USER_ID = "user_id";
    public static final String SP_MOBILE_NO = "mobile_no";
    public static final String AUTH_TOKEN_ID = "auth_token_id";
    public static final String SP_CHILD_FORM_APP_NAME = "child_form_app_name";
    public static final String SP_CHILD_FORM_CREATED_BY_ID = "child_form_created_by";
    public static final String SP_CHILD_FORM_DISTRIBUTION_ID = "child_form_distribution_id";
    public static final String SP_CHILD_FORM_QUERY_NAME = "child_form_query_name";
    /*Data Control*/
    public static final String SP_DATA_CONTROLS_ITEMS = "DataControlItems";
    //    public static final String SP_DATA_CONTROLS_STATUS_DEPENDENT = "Dependent";
//    public static final String SP_DATA_CONTROLS_STATUS_INDEPENDENT = "Independent";
    public static final String SP_DATA_CONTROLS_STATUS_DEPENDENT = "Y";
    public static final String SP_DATA_CONTROLS_STATUS_INDEPENDENT = "N";
    /*AppsList item Click*/
    public static final String S_APP_VERSION = "s_app_version";
    //======System_Varibles=======
    public static final String User_Role = "User Role";
    public static final String User_ID = "User ID";
    public static final String ORG_Name = "ORG Name";
    public static final String User_Name = "User Name";
    public static final String User_MobileNo = "User MobileNo";
    public static final String User_Email = "User Email";
    public static final String User_Desigination = "User Desigination";
    public static final String User_Department = "User Department";
    public static final String User_location = "User location";
    public static final String User_location_name = "User location Name";
    public static final String USER_TYPE = "user_type";
    public static final String USER_TYPE_ID = "user_type_id";
    public static final String Reporter_Role = "Reporter Role";
    public static final String Reporter_ID = "Reporter ID";
    public static final String Reporter_Name = "Reporter Name";
    public static final String Reporter_MobileNo = "Reporter MobileNo";
    public static final String Reporter_Email = "Reporter Email";
    public static final String Reporter_Desigination = "Reporter Desigination";
    public static final String Reporter_Department = "Reporter Department";
    public static final String Reporter_location = "Reporter location";
    public static final String Post_Id = "Post ID";
    public static final String Post_Name = "Post Name";
    public static final String Post_Location = "Post Location";
    public static final String Post_Location_Name = "Post Location Name";
    public static final String Reporter_post_id = "Reporting Post ID";
    public static final String Reporter_post_Department_ID = "Reporting Post Department ID";
    public static final String APP_Language = "App Language";
    public static final String Current_Date = "Current Date";
    public static final String Current_Time = "Current Time";
    public static final String login_status = "Login status";
    public static final String Login_Device = "Login Device";
    public static final String Login_Device_Id = "Login Device ID";
    public static final String App_Version = "App Version";
    public static final String Login_OS_Version = "Login OS Version";
    public static final String CURRENT_APP_NAME = "Current App Name";
    public static final String APP_LANGUAGE = "App Language";
    public static final String BHARGO_LOGIN_ACTION_STATUS = "Bhargo Login Action Status";
    public static final String BHARGO_LOGIN_ACTION_MESSAGE = "Bhargo Login Action Message";
    public static final String[] SYSTEM_VARIBLES = {User_Role, User_ID, Post_Id, Post_Location, User_Name, User_MobileNo, User_Email, User_Desigination, User_Department, User_location, Reporter_Role, Reporter_ID, Reporter_Name, Reporter_MobileNo, Reporter_Email, Reporter_Desigination, Reporter_Department, Reporter_location, Login_Device, Login_Device_Id, App_Version, Login_OS_Version, CURRENT_APP_NAME/*,APP_LANGUAGE*/};
    /*Controls Variables*/
    public static final int IMAGE_WITH_GPS_REQUEST_CODE = 111;
    public static final int REQUEST_SIGNATURE_CONTROL_CODE = 444;
    public static final int REQUEST_CAMERA_CONTROL_CODE = 552;
    public static final int REQ_CODE_PICK_VOICE_REC = 6382;
    public static final int REQUEST_VIDEO_RECORDING = 574;
    public static final int REQ_CODE_PICK_ONLY_VIDEO_FILE = 237;
    /*Section Variables*/
    public static final int SECTION_REQUEST_SIGNATURE_CONTROL_CODE = 9111;
    public static final int SECTION_FILE_BROWSER_RESULT_CODE = 4201;
    public static final int SECTION_REQUEST_CURRENT_LOCATION = 2221;
    public static final int SECTION_REQUEST_CAMERA_CONTROL_CODE = 3331;
    public static final int SECTION_REQ_CODE_PICK_VOICE_REC = 5221;
    public static final int SECTION_REQUEST_VIDEO_RECORDING = 5721;
    public static final int SECTION_REQ_CODE_PICK_ONLY_VIDEO_FILE = 2351;
    public static final int SECTION_REQUEST_SPEECH_TO_TEXT = 2321;
    public static final int SECTION_REQUEST_DOCUMENT_SCANNER = 0071;
    public static final int SECTION_IMAGE_WITH_GPS_REQUEST_CODE = 1081;
    /*Section Variables*/
    /*SubForm Variables*/
    public static final int SF_REQUEST_SIGNATURE_CONTROL_CODE = 911;
    public static final int SF_FILE_BROWSER_RESULT_CODE = 420;
    public static final int SF_REQUEST_CURRENT_LOCATION = 222;
    public static final int SF_REQUEST_CAMERA_CONTROL_CODE = 333;
    public static final int SF_REQ_CODE_PICK_VOICE_REC = 522;
    public static final int SF_REQUEST_VIDEO_RECORDING = 572;
    public static final int SF_REQ_CODE_PICK_ONLY_VIDEO_FILE = 235;
    public static final int SF_REQUEST_SPEECH_TO_TEXT = 232;
    public static final int SF_IMAGE_WITH_GPS_REQUEST_CODE = 108;
    public static final int REQUEST_CURRENT_LOCATION_QUERY = 119;
    public static final String QUERY_CURRENT_LAT = "Query_Current_lat";
    public static final String QUERY_CURRENT_LNG = "Query_Current_lng";
    public static final String GROUP_KEY_BHARGO = "com.bhargo.user";
    public static final String E_LEARNING_NOTIFICATION = "Elearning";
    public static final String TASK_MANAGEMENT = "TaksManagement";
    public static final String SESSIONCHAT_NOTIFICATION = "SessionChat";
    public static final int REQUEST_SPEECH_TO_TEXT = 555;
    public static final int REQUEST_SPEECH_TO_TEXT_INPUT = 89789;
    public static final int LAUNCH_MSG_REQUEST_CODE = 177;
    /*GPS*/
    public static final String LOCATION_MODE_NETWORK = "Network";
    public static final String LOCATION_MODE_SATELLITE = "Satellite";
    public static final String LOCATION_MODE_HYBRID = "Hybrid";
    /*GPS Types*/
    public static final String Single_Point_GPS = "Single point GPS";
    public static final String Two_points_line = "Two points line";
    public static final String Multi_points_line = "Multi points line";
    public static final String Four_points_square = "Four points square";
    public static final String Polygon = "Polygon";
    public static final String Vehicle_Tracking = "Vehicle Tracking";
    /*Type of intervals*/
    public static final String Interval_distance = "Distance";
    public static final String Interval_time = "Time";
    public static final String Interval_On_Change = "On Change";
    /*Index View*/
    public static final String INDEX_GPS_VIEW = "INDEX_GPS_VIEW";
    public static final String INDEX_IMAGE_VIEW = "INDEX_IMAGE_VIEW";
    public static final String INDEX_IMAGE_GPS_VIEW = "INDEX_IMAGE_GPS_VIEW";
    public static final String INDEX_NORMAL_VIEW = "INDEX_NORMAL_VIEW";
    /*MapViewType*/
    public static final String map_Multiple_Marker = "Point";
    public static final String map_Multiple_Polylines = "Line";
    public static final String map_Polygon = "Polygon";
    /*MultiComments*/
    public static final String MultiComment_source_CallFormFields = "Call form fields";
    public static final String MultiComment_source_CallAPI = "Call API";
    public static final String MultiComment_source_CallQuery = "Call Query";
    public static final String MultiComment_source_CallFormControls = "Form Controls";
    public static final String GridView_With_Image_2_Columns = "GridView With Image(2 Columns)";
    public static final String GridView_With_Image_3_Columns = "GridView With Image(3 Columns)";
    public static final String GridView_With_Image_2_Columns_call = "GridView With Image(2 Columns) & Call";
    public static final String GridView_With_Image_3_Columns_call = "GridView With Image(3 Columns) & Call";
    public static final String GridView_With_Video_2_Columns = "GridView With Video(2 Columns)";
    public static final String GridView_With_Video_3_Columns = "GridView With Video(3 Columns)";
    public static final String GridView_With_Video_2_Columns_call = "GridView With Video(2 Columns) & Call";
    public static final String GridView_With_Video_3_Columns_call = "GridView With Video(3 Columns) & Call";
    public static final String ListView_2_Columns = "ListView (2 Columns)";
    public static final String ListView_With_Image_2_Columns = "ListView With Image(2 Columns)";
    public static final String ListView_With_Image_3_Columns = "ListView With Image(3 Columns)";
    public static final String MapView_Item_Info = "Map View Item Info";
    public static final String EV_Dashboard_Design_Three = "EV Dashboard Design Three";
    public static final String EV_Dashboard_Design_One = "EV Dashboard Design One";
    public static final String EV_Dashboard_Design_Two = "EV Dashboard Design Two";
    public static final String EV_News_Design = "EV News Design";
    public static final String EV_Dealers_Design = "EV Dealers Design";
    public static final String EV_Notifications_Design = "EV Notifications Design";
    public static final String EV_Jobs_Design = "EV Jobs Design";
    public static final String ListView_With_Image_3_Columns_call = "ListView With Image(3 Columns) & Call";
    public static final String Geo_Spatial_View = "Geo Spatial View";
    public static final String TimeLine_View = "TimeLine View";
    public static final String TimeLine_With_Photo_View = "TimeLine With Photo View";
    public static final String LinearView_With_Video = "Linear View With Video";
    public static final String BlogSpot_View = "BlogSpot View";
    public static final String WIDGET_1 = "Widget Card1";
    public static final String WIDGET_2 = "Widget Card2";
    public static final String WIDGET_3 = "Widget Card3";
    public static final String WIDGET_4 = "Widget Card4";
    public static final String WIDGET_5 = "Widget Card5";
    public static final String WIDGET_6 = "Widget Card6";
    public static final String WIDGET_7 = "Widget Card7";
    public static final String WIDGET_8 = "Widget Card8";
    public static final String WIDGET_9 = "Widget Card9";
    public static final String WIDGET_10 = "Widget Card10";
    public static final String Button_Group = "Button group";
    public static final String BOTTOM_NAVIGATION = "BOTTOM_NAVIGATION";
    public static final String NAVIGATION_MENU = "Navigation Menu";
    /*Operators*/
    public static final String OPERATOR_EQUALS = "Equals";
    public static final String OPERATOR_LESS_THAN = "lessThan";
    public static final String OPERATOR_GREATER_THAN = "GreaterThan";
    public static final String OPERATOR_LESS_THAN_EQUALS = "LessThanEqualsTo";
    public static final String OPERATOR_GREATER_THAN_EQUALS = "GreaterThanEqualsTo";
    public static final String OPERATOR_CONTAINS = "Contains";
    public static final String OPERATOR_STARTS_WITH = "StartsWith";
    public static final String OPERATOR_ENDS_WITH = "EndsWith";
    public static final String OPERATOR_IS_EMPTY = "isEmpty";
    public static final String OPERATOR_BETWEEN = "Between";
    public static final String OPERATOR_NOT_EQUALS = "NotEquals";
    public static final String OPERATOR_NEAR_BY = "NearBy";
    /*Chart types */
    public static final String CHART_TYPE_LINE = "Line";
    public static final String CHART_TYPE_BAR = "Bar";
    public static final String CHART_TYPE_COMBO = "Combo";
    public static final String CHART_TYPE_ROW = "Row";
    public static final String CHART_TYPE_AREA = "Area";
    public static final String CHART_TYPE_SCATTER = "Scatter";
    public static final String CHART_TYPE_PIE = "Pie";
    public static final String CHART_TYPE_GAUGE = "Gauge";
    public static final String CHART_TYPE_STACKED= "Stacked";
    /*Lazy Loading*/
    public static final int VIEW_TYPE_ITEM = 0;
    public static final int VIEW_TYPE_LOADING = 1;
    public static final String FromFlashScreen = "FromFlashScreen";
    //Toolbar ActionItem Type
    public static final String ACTION_ITEM_ICON_ONLY = "ACTION_ITEM_ICON_ONLY";
    public static final String ACTION_ITEM_NAME_ONLY = "ACTION_ITEM_NAME_ONLY";
    public static final String ACTION_ITEM_BOTH = "ACTION_ITEM_BOTH";
    public static final String CONTROL_TYPE_PROGRESS = "PROGRESS";
    public static final String QC_FORMAT_TEXT = "Plain_Text";
    public static final String QC_FORMAT_QRCODE = "QR_Code";
    public static final String QC_FORMAT_BARCODE = "BAR_Code";
    public static final String QC_FORMAT_Video_Play = "Video_Play";
    public static final String QC_FORMAT_Audio_Play = "Audio_Play";
    public static final String QC_FORMAT_IMAGE = "Image";
    public static final String QC_FORMAT_PHONE = "Phone_Number";
    public static final String QC_FORMAT_URL = "URL";
    public static final String QC_FORMAT_MAP = "MAP";
    public static final String QC_FORMAT_RATING = "RATING";
    public static final String TYPE_CALL_FORM = "CALL_FORM";
    public static final String SET_PROPERTY_TYPE_CONTROL = "Control";
    public static final String SET_PROPERTY_TYPE_SUB_CONTROL = "SubControl";
    /*File Browser*/
    public static final String DOC = "application/msword";
    public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String IMAGE = "image/*";
    //    public static final String JPG = "image/jpg";
    public static final String JPEG = "image/jpeg";
    public static final String PNG = "image/png";
    public static final String AUDIO = "audio/*";
    public static final String VIDEO = "video/*";
    public static final String TEXT = "text/*";
    public static final String PDF = "application/pdf";
    public static final String XLS = "application/vnd.ms-excel";
    public static final String MP3 = "audio/*";
//    public static final String MP3 = "audio/x-wav";
    public static final String RETRIEVE_TYPE_POST_BASED = "Login User Post";
    public static final String RETRIEVE_TYPE_POST_LOCATION_BASED = "Login User Post and Location";
    public static final String RETRIEVE_TYPE_ENTIRE_TABLE_DATA = "Entire Data";
    public static final String RETRIEVE_TYPE_FILTER_BASED = "Filter Based";
    /*String to Compare*/
    public static final String COMPARE_SCREEN_FIT = "Screen Fit";
    public static final String COMPARE_SCROLLABLE = "Scrollable";
    public static final String COMPARE_TOP = "Top";
    public static final String COMPARE_MIDDLE = "Middle";
    public static final String COMPARE_BOTTOM = "Bottom";
    public static final String COMPARE_LEFT = "Left";
    public static final String COMPARE_RIGHT = "Right";
    public static final String COMPARE_IMAGE = "Image";
    public static final String COMPARE_COLOR = "Color";
    public static final String COMPARE_HTTP = "http";
    public static final String GRID_WITH_TWO_COLUMNS = "Grid With Two Columns";
    public static final int FORM_VALIDATED = 999;
    public static ImageView NOTIFICATION_IMAGE = null;
    public static boolean OUT_TASK_REFRESH_BOOLEAN = false;
    public static boolean IN_TASK_REFRESH_BOOLEAN = false;
    /*Standard Control types */
//        public static final String[] STANDARD_CONTROL_TYPES = {CONTROL_TYPE_TEXT_INPUT, CONTROL_TYPE_NUMERIC_INPUT, CONTROL_TYPE_PHONE
//                , CONTROL_TYPE_EMAIL, CONTROL_TYPE_LARGE_INPUT, CONTROL_TYPE_CAMERA, CONTROL_TYPE_FILE_BROWSING, CONTROL_TYPE_CALENDER
//                , CONTROL_TYPE_CHECKBOX, CONTROL_TYPE_RADIO_BUTTON, CONTROL_TYPE_DROP_DOWN, CONTROL_TYPE_CHECK_LIST, CONTROL_TYPE_RATING
//                , CONTROL_TYPE_VOICE_RECORDING, CONTROL_TYPE_VIDEO_RECORDING, CONTROL_TYPE_AUDIO_PLAYER, CONTROL_TYPE_VIDEO_PLAYER
//                , CONTROL_TYPE_PERCENTAGE, CONTROL_TYPE_SIGNATURE, CONTROL_TYPE_URL_LINK, CONTROL_TYPE_AUTO_NUMBER, CONTROL_TYPE_DECIMAL
//                , CONTROL_TYPE_PASSWORD, CONTROL_TYPE_CURRENCY, CONTROL_TYPE_DYNAMIC_LABEL, CONTROL_TYPE_IMAGE, CONTROL_TYPE_BUTTON};
//        public static final String[] ADVANCED_CONTROL_TYPES = {CONTROL_TYPE_SUBFORM, CONTROL_TYPE_GRID_CONTROL, CONTROL_TYPE_GPS
//                , CONTROL_TYPE_BAR_CODE, CONTROL_TYPE_QR_CODE};

    //    public static final String YOUTUBE_API_KEY="AIzaSyA0BSqAm_blxcO8p43g0hW-DdyoRQkpmsc";
    //public static List<String> modifiedColNames=new ArrayList<>();
    public static boolean IS_SINGLE_USER = false;
    public static List<SpinnerAppsDataModel> editTaskAppDataModelsList = new ArrayList<>();
    /*Multi Form*/
    public static List<ContentInfoModel> editTaskContentDataModelsList = new ArrayList<>();
    public static List<TaskDepGroupDataModel> editTaskGroupList = new ArrayList<>();
    public static List<TaskDepEmpDataModel> editTaskIndividualList = new ArrayList<>();
    /*Call Form*/
    public static String APP_VERSION = "2.0";
    public static boolean Infoactive = false;
    public static boolean Chatactive = false;
    public static boolean GroupChatactive = false;
    public static boolean SessionChatactive = false;
    public static boolean SessionChatBackground = false;
    public static String CheckMethod = "0";
    /*Shared preferences Names*/
    //To get Selected View
    public static int selectedView = 9999;
    //To Check Selected View is API/Query based View or newly added view
    public static int ChildFormView = 9999;
    public static String controlCategory = null;
    public static String controlId = null;
    /*public static final String [] STANDARD_CONTROL_TYPES = {CONTROL_TYPE_TEXT_INPUT,CONTROL_TYPE_NUMERIC_INPUT,CONTROL_TYPE_PHONE
            ,CONTROL_TYPE_EMAIL,CONTROL_TYPE_LARGE_INPUT,CONTROL_TYPE_CAMERA,CONTROL_TYPE_FILE_BROWSING,CONTROL_TYPE_CALENDER
            ,CONTROL_TYPE_CHECKBOX,CONTROL_TYPE_RADIO_BUTTON,CONTROL_TYPE_DROP_DOWN,CONTROL_TYPE_CHECK_LIST,CONTROL_TYPE_RATING
            ,CONTROL_TYPE_VOICE_RECORDING,CONTROL_TYPE_VIDEO_RECORDING,CONTROL_TYPE_AUDIO_PLAYER,CONTROL_TYPE_VIDEO_PLAYER
            ,CONTROL_TYPE_PERCENTAGE,CONTROL_TYPE_SIGNATURE,CONTROL_TYPE_URL_LINK,CONTROL_TYPE_DECIMAL
            ,CONTROL_TYPE_PASSWORD,CONTROL_TYPE_CURRENCY,CONTROL_TYPE_DYNAMIC_LABEL,CONTROL_TYPE_IMAGE,CONTROL_TYPE_BUTTON};*/
    public static ControlObject Current_controlObject;
    public static View propertiesView;
    public static View onFocusView;
    /*===callAPI_Multiple_DisplayTypes==*/
    public static String C_IN_TASK_ID = "";
    public static String C_IN_TASK_DID = "";
    /*SYNC STATUS*/
    public static View onChangeView;
    /**/
    public static ControlObject Current_controlObject_subForm;
    public static ControlObject Current_controlObject_grid;
    public static com.bhargo.user.Java_Beans.GlobalObjects GlobalObjects;
    public static DataCollectionObject CurrentAppObject;
    public static View onSubmitClickView;
    public static View submitPropertiesView;
    public static Control_EventObject Current_onChangeEventObject;
    public static Control_EventObject Current_onFocusEventObject;
    public static Control_EventObject Current_onSubmitClickObject;
    public static Control_EventObject Current_onLoadEventObject;
    public static int EventCallsFrom = 0;
    public static String Current_ClickorChangeTagName = "";
    public static int SF_Container_position = 0;
    public static int SF_Selected_position = 0;
    public static View SF_Selected_View = null;
    public static int MAP_MARKER_POSITION = -1;
    public static int DATA_TABLE_ROW_POS = 0;
    public static int DATA_TABLE_COL_POS = 0;
    public static int dw_position = -1;//Data Viewer Item Pos
    public static boolean EventFrom_subformOrNot = false;
    public static String SF_ClickorChangeTagName = "";
    public static HashMap<String, SearchableSpinner> SEARCHABLE_SPINNER_LIST = new HashMap<>();
    public static HashMap<String, ControlObject> DATACONTROL_CO_LIST = new HashMap<>();
    public static HashMap<String, List<String>> DCN_CN_List = new HashMap<>();
    public static HashMap<String, String> DC_SELCTED_ID = new HashMap<>();
    public static HashMap<String, List<DataControl_Bean>> DATACONTROL_BEAN_LIST = new HashMap<>();
    public static HashMap<String, List<New_DataControl_Bean>> NEW_DATACONTROL_BEAN_LIST = new HashMap<>();
    public static HashMap<String, List<String>> DC_DEPENDENT_LIST = new HashMap<>();
    public static HashMap<String, List<String>> DEPENDENT_DC_NAME = new HashMap<>();
    public static HashMap<String, AssessmentAnswer> ASSESSMENT_ANSWER_LIST = new HashMap<>();
    public static boolean Initialize_Flag = false;
    public static String SYNC_STATUS_SAVED = "0";
    public static String SYNC_STATUS_SENT = "1";
    /*Changes*/
    public static boolean DataControl_Edit_Flag = true;
    public static int OrgChange = 0;
    public static int PostChange = 0;
    public static String InTaskRefresh = "0";
    public static String OutTaskRefresh = "0";
    public static String TASK_REFRESH = "TASK_REFRESH";
    public static boolean IS_TASK_FILTER_SELECTED = false;
    public static boolean IS_FROM_IN_TASK_DETAILS_APPS_LIST = false;
    public static String TASK_ATTEMPTS_BY = "0";
    public static String MAIN_WEB_LINK = "http://182.18.157.124/improve/WebForms/DashBoardBuilder/DashBoardView.html?PageName=";
    //Map_FullView
    public static MapView mapFrag = null;
    public static String LiveTrack_Trans_ID = "";
    public static int PROGRESS_CHART = 0;
    public static int PROGRESS_APPS = 0;
    public static int PROGRESS_REPORT = 0;
    public static int PROGRESS_TASK = 0;
    public static int PROGRESS_E_LEARNING = 0;
    public static String liveTrack_RETRIEVED_ACTION = "com.santhosh.LiveTrackingReceiver";

    //    public static final String YOUTUBE_API_KEY="AIzaSyA0BSqAm_blxcO8p43g0hW-DdyoRQkpmsc";

    /*Multi Form*/
    public static String liveTrack_DrawLine_RETRIEVED_ACTION = "com.santhosh.LiveTrackingDrawLine";
    public static boolean IS_MULTI_FORM = false;
    public static String CURRENT_APP = "Current app";

    /*Call Form*/
    public static String MULTI_FORM_TYPE = "";
    public static String NAVIGATION_TYPE_CLOSE_PARENT = "Close parent";
    public static String NAVIGATION_TYPE_KEEP_SESSION = "Keep session";
    public static String CALL_FORM_SINGLE_FORM = "SINGLE_FORM";
    public static String CALL_FORM_MULTI_FORM = "MULTI_FORM";
    public static String CALL_FORM_MULTI_FORM_INNER = "MULTI_FORM_INNER";
    public static String CALL_FORM_CURRENT_MULTI_FORM = "CURRENT_MULTI_FORM";
    public static String CALL_FORM_CURRENT_MULTI_FORM_INNER = "CURRENT_MULTI_FORM_INNER";
    public static String CALL_FORM_DATA_COLLECTION = "Datacollection";
    public static String CALL_FORM_REPORT_FORM = "REPORT_FORMP";
    public static String CALL_FORM_WORK_FLOW_FORM = "WORK_FLOW_FORM";
    public static String CALL_FORM_SINGLE_DATA_MANAGEMENT = "SINGLE_FORM_DATA_MANAGEMENT";
    public static MultiFormApp currentMultiForm;
    public static String DISPLAY_TYPE_SUBFORM = "Subform";
    public static String DISPLAY_TYPE_GRIDFORM = "Gridform";
    public static String DISPLAY_TYPE_DATAVIEWER = "DataViewer";
    public static String DISPLAY_TYPE_LISTVIEW = "ListView";
    public static String DISPLAY_TYPE_MAPVIEW = "MapView";
    public static String DISPLAY_TYPE_CALENDARVIEW = "CalendarView";
    public static String DISPLAY_TYPE_CHART = "Chart";
    //    public static  String DefultAPK_Startpage="test groupDML2";
    public static String DISPLAY_TYPE_DATA_TABLE = "DataTable";
    public static String DISPLAY_TYPE_IMAGEVIEW = "ImageView";
    public static LinkedHashMap<String, LinkedHashMap<String, Object>> Global_Controls_Variables = new LinkedHashMap<>();
    public static LinkedHashMap<String, DataCollectionObject> Global_Single_Forms = new LinkedHashMap<>();
    public static String DISPLAY_TYPE_SECTION = "Section";
    public static LinkedHashMap<String, LinearLayout> Global_Layouts = new LinkedHashMap<>();
    public static LinkedHashMap<String, List<Variable_Bean>> Global_Variable_Beans = new LinkedHashMap<>();
    /* Bottom Navigation View in MultiForm App */
    public static Map<String, LinearLayout> LAYOUT_KEEP_SESSION = new HashMap<>();
    public static Map<String, LinkedHashMap<String, Object>> KEEP_SESSION_CONTROLS_MAP = new HashMap<>();
    public static Map<String, JSONObject> KEEP_SESSION_OBJECT = new HashMap<>();
    public static Map<String, View> KEEP_SESSION_VIEW_MAP = new HashMap<>();
    public static Map<String, FrameLayout> KEEP_SESSION_LAYOUT_MAP = new HashMap<>();
    public static String CURRENT_LOCATION = "";
    /*Edit Columns*/
    public static List<String> EDIT_COLUMNS = new ArrayList<>();
    public static String CONTROL = "control";
    public static String SUB_CONTROL = "subcontrol";
    public static String ROW_TYPE_SPECIFIC_ROWS = "specificRows";
    public static String ROW_TYPE_ALL_ROWS = "allRows";
    /*Language codes*/
    public static String LANG_ENGLISH = "en";
    public static String LANG_TELUGU = "te";
    public static String LANG_HINDI = "hi";
    public static String LANG_TAMIL = "ta";
    /*Properties*/
    public static String LANG_MARATHI = "mr";
    public static String LANG_KANNADA = "kn";
    public static String LANG_SINHALA = "si";
    public static DataCollectionObject dataCollectionObject_global;
    public static boolean hasData = false;
    public static boolean hasCheckList = false;
    public static HashMap<String, Boolean> isSubformHasCheckList = new HashMap<>();
    public static JSONArray checkListData = new JSONArray();
    public static HashMap<String, JSONArray> subformCheckListData = new HashMap<>();
    public static HashMap<String, List<String>> transIdsOfSubforms = new HashMap<>();
    public static boolean IS_REPORT_APP = false;
    public static String FromNotificationOnlyInTask = "";
    public static String Current_ScanName = "";
    /*Sync Form Data*/
    public static UILayoutProperties uiLayoutPropertiesStatic = new UILayoutProperties();
    public static List<String> subformWithUI = new ArrayList<>();
    public static List<String> sectionWithUI = new ArrayList<>();
    //    public static HashMap<String,String> controlPositionInUI = new HashMap<>();
    public static HashMap<String, HashMap<String, String>> controlPositionInUIAllApps = new HashMap<>();
    //Defult_apK
    /*Availibility_of_Windows*/
    //    InfoTab^TabAvailible^TabName|AppsTab^TabAvailible^TabName|.....
    //    public static  String WINDOWS_AVAILABLE= "InfoTab^Yes^Chats|AppsTab^Yes^Apps|ReportTab^Yes^Reports|TaskTab^Yes^Tasks|ELearningTab^Yes^ELearning";
    public static String WINDOWS_AVAILABLE = "AppsTab^Yes^Apps|InfoTab^Yes^Chats|ReportTab^Yes^Reports|TaskTab^No^Tasks|ELearningTab^Yes^ELearning";
    //    public static  String WINDOWS_AVAILABLE= "InfoTab^No^Chats|AppsTab^No^Apps|ReportTab^No^Reports|TaskTab^No^Tasks|ELearningTab^No^ELearning";
    //    public static  String WINDOWS_AVAILABLE= "InfoTab^Yes^Communications|AppsTab^Yes^SS Apps|ReportTab^No^Reports|TaskTab^Yes^Programs|ELearningTab^Yes^Library";
    public static String WINDOWS_AVAILABLE_HINDI = "AppsTab^Yes^ऐप्स|InfoTab^Yes^चैट|ReportTab^Yes^रिपोर्ट|TaskTab^No^कार्य|ELearningTab^Yes^ई लर्निंग";
    //        public static boolean DefultAPK = false;
    public static boolean DefultAPK = true;
    public static String DefultAPK_OrgID = "Bhargo Innovations";
    public static String DefultAPK_UserID = "";
    //    public static  String DefultAPK_Startpage="EV Stations";
    //    public static String DefultAPK_Startpage = "Farmer Logins";
    public static String DefultAPK_Startpage = "Doctor Login";

    public static String DefultAPK_afterLoginPage = "Bhargo Home Page";
    public static boolean DefultAPK_afterLoginPage_loaded = true;
    public static int BOTTOM_NAV_POS = -1;
    public static boolean HOME_CLICKED = false;
    public static String FromNotificationOnlyCommunication = "OnlyCommunication";
    public static int VIDEO_STRETCH_SCREEN_HEIGHT = 0;
    public static String SPINNER_INIT_NAME = "-- Select --";
 public static String SPINNER_INIT_ID = "-1";
    public static Boolean EL_SINGLE_TIME = false;
    public static final String E_LEARNING_FILES = "ELearningFiles";
    public static int ITEM_MAIN_MENU_POSITION = -1;
    public static int ITEM_SUB_MENU_POSITION = -1;
    public static Boolean ITEM_MAIN_ACTIVITY_ON_BACK = false;


    public static String Trans_id = "Bhargo_Trans_Id";//Trans Id
    public static String Ref_Trans_id = "Bhargo_Ref_TransID";//ref Trans Id

    //public static  String DefultAPK_afterLoginPage="Bhargo Home Page";
    //    public static final String THEME = "THEME1";
    public static String THEME = "THEME1";
    public static String FORM_THEME = "THEME1";
    public static Boolean IS_FORM_THEME = false;

    //    public static final String THEME = "THEME3";
    //    public static final String THEME = "THEME4";
    //    public static final String THEME = "THEME5";
    //    public static final String THEME = "THEME6";
    //    public static final String THEME = "THEME7";

    public static final String SD_CARD_ORG_NAME_FOLDER = "Improve_User/";
    public static final String CUSTOM_WIDTH = "Custom Width";
    public static final String CUSTOM_HEIGHT = "Custom Height";
    public static final String DEFAULT_WIDTH = "Default Width";
    public static final String DEFAULT_HEIGHT = "Default Height";
    public static final String MATCH_PARENT = "Match Parent";
    public static final String FILL = "Fill";
    public static final String COVER = "Cover";
    public static final String CONTAIN = "Contain";
    public static final String DEFAULT = "Default";



    public static boolean EDIT_MODE = false;
    //commit code
    public static String SHOW_MESSAGE_BELOW_CONTROL = "show_message_below_control";
    public static String VERTICAL_ALIGNMENT = "Vertical Alignment";
    public static String HORIZONTAL_ALIGNMENT = "Horizontal Alignment";
    public static String SLIDER = "Slider";
    public static String GALLERY_VIEW = "Gallery View";




}
