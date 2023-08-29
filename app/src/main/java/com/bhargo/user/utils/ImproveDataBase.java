package com.bhargo.user.utils;

import static com.bhargo.user.utils.SQLiteHelper.UID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.PointF;
import android.text.TextUtils;
import android.util.Log;

import com.bhargo.user.Java_Beans.QueryFilterField_Bean;
import com.bhargo.user.R;
import com.bhargo.user.pojos.APIDetails;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.AppDetailsAdvancedInput;
import com.bhargo.user.pojos.AppVersionModel;
import com.bhargo.user.pojos.AutoIncrementControl;
import com.bhargo.user.pojos.CallAPIRequestDataSync;
import com.bhargo.user.pojos.ColNameAndValuePojo;
import com.bhargo.user.pojos.CommentsInfoModel;
import com.bhargo.user.pojos.DataControls;
import com.bhargo.user.pojos.DataControlsAndApis;
import com.bhargo.user.pojos.FileColAndIDPojo;
import com.bhargo.user.pojos.ForeignKey;
import com.bhargo.user.pojos.GetOfflineData;
import com.bhargo.user.pojos.GetTasksInvDtsResponse;
import com.bhargo.user.pojos.GetUserDistributionsResponse;
import com.bhargo.user.pojos.InTaskDataModel;
import com.bhargo.user.pojos.InsertUserFileVisitsModel;
import com.bhargo.user.pojos.OfflineDataSync;
import com.bhargo.user.pojos.OfflineDataTransaction;
import com.bhargo.user.pojos.OrgList;
import com.bhargo.user.pojos.OutTaskDataModel;
import com.bhargo.user.pojos.SubFormTableColumns;
import com.bhargo.user.pojos.TableNameAndColsFromDOPojo;
import com.bhargo.user.pojos.TaskAppDataModelAC;
import com.bhargo.user.pojos.TaskCmtDataModel;
import com.bhargo.user.pojos.TaskCommentDetails;
import com.bhargo.user.pojos.TaskContentDataModelAC;
import com.bhargo.user.pojos.TaskDataModelAC;
import com.bhargo.user.pojos.firebase.ChatDetails;
import com.bhargo.user.pojos.firebase.Group;
import com.bhargo.user.pojos.firebase.Notification;
import com.bhargo.user.realm.RealmTables;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImproveDataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Improve_User";
    public static final int DATABASE_VERSION = 1;
    /*APP Version table Data*/
    //nk merge pending full
    public static final String APP_VERSION_TABLE = "app_version_table";
    public static final String APP_VERSION_SERIAL_NO = "Sno";
    public static final String APP_VERSION_USER_ID = "user_id";
    public static final String APP_VERSION_ORG_ID = "org_id";
    public static final String APP_VERSION_APP_NAME = "app_name";
    public static final String APP_VERSION_OLD = "old_app_version";
    public static final String APP_VERSION_NEW = "new_app_version";
    /*Org table Data*/
    public static final String ORGANISATION_TABLE = "organisation_table";
    public static final String SERIAL_NO = "Sno";
    public static final String DB_ORG_ID = "org_id";
    public static final String DB_ORG_NAME = "org_name";
    public static final String USER_ID = "user_id";
    public static final String POST_ID = "post_id";

    /*Submit Sync Table*/
    public static final String FORM_SUBMIT_TABLE = "submit_table";
    public static final String FORM_SUBMIT_TABLE_SERIAL_NO = "Sno";
    public static final String FORM_SUBMIT_TABLE_APP_NAME = "app_name";
    public static final String FORM_SUBMIT_TABLE_JSON_STRING = "prepared_json_string";
    public static final String FORM_SUBMIT_TABLE_JSON_FILE_STRING = "prepared_files_json_string";
    public static final String FORM_SUBMIT_TABLE_JSON_STRING_SUB_FORM = "prepared_json_string_subform";
    public static final String FORM_SUBMIT_TABLE_JSON_FILE_STRING_SUB_FORM = "prepared_files_json_string_subform";
    public static final String FORM_SUBMIT_TABLE_SYNC_STATUS = "sync_status";
    public static final String FORM_SUBMIT_TABLE_OFFLINE_JSON = "offline_json";
    public static final String DISTRIBUTION_ID = "DistrubutionID";
    public static final String PAGE_NAME = "page_name";
    public static final String CREATED_USER_ID_SUBMIT = "created_user_id";
    public static final String SUBMITTED_UER_ID = "submitted_user_id";
    public static final String IMEI = "imei";
    public static final String OPERATION_TYPE = "operation_type";
    public static final String TRANS_ID = "trans_id";

    /*Submit Sync Table*/
    public static final String CallAPI_Request_TABLE = "callapi_request_table";
    public static final String CallAPI_Request_SERIAL_NO = "Sno";
    public static final String CallAPI_Request_OrgID = "CallAPI_Request_OrgID";
    public static final String CallAPI_Request_loginID = "CallAPI_Request_loginID";
    public static final String CallAPI_Request_APIName = "callapi_request_APIName";
    public static final String CallAPI_Request_INPARAMS = "callapi_request_InParams";

    public static final String CREATE_CallAPI_Request_TABLE =
            " create table " + CallAPI_Request_TABLE + " ( "
                    + CallAPI_Request_SERIAL_NO + " integer primary key autoincrement , "
                    + CallAPI_Request_OrgID + " TEXT , "
                    + CallAPI_Request_loginID + " TEXT , "
                    + CallAPI_Request_APIName + " TEXT,"
                    + CallAPI_Request_INPARAMS + " TEXT  ) ";


    /* Apps List Table*/
    public static final String APPS_LIST_TABLE = "apps_list_table";
    public static final String APPS_LIST_TABLE_IN_TASK = "in_task_apps_list_table";
    /*Data Control table Data*/
    public static final String DATA_CONTROL_TABLE = "data_control_table";
    public static final String QUERY_STRING = "query_string";
    public static final String QUERY_FORM_NAME = "form_name";
    public static final String QUERY_FETCHED_DATA_STRING = "fetched_string";
    public static final String QUERY_DATA_TABLE = "query_data_table";
    /*Create APK Version table */
    public static final String APP_VERSION_TABLE_CREATE =
            " create table " + APP_VERSION_TABLE + " ( "
                    + APP_VERSION_SERIAL_NO + " integer primary key autoincrement , "
                    + APP_VERSION_USER_ID + " TEXT , "
                    + APP_VERSION_ORG_ID + " TEXT , "
                    + APP_VERSION_APP_NAME + " TEXT , "
                    + APP_VERSION_OLD + " TEXT , "
                    + APP_VERSION_NEW + " TEXT ) ";
    /*Create Organsiation table */
    public static final String ORGANISATION_TABLE_CREATE =
            " create table " + ORGANISATION_TABLE + " ( "
                    + SERIAL_NO + " integer primary key autoincrement , "
                    + DB_ORG_ID + " TEXT , "
                    + DB_ORG_NAME + " TEXT , "
                    + USER_ID + " TEXT ) ";
    public static final String CREATE_QUERY_DATA_TABLE = " create table " + QUERY_DATA_TABLE + " ( "
            + SERIAL_NO + " integer primary key autoincrement , "
            + DB_ORG_ID + " TEXT , "
            + QUERY_FORM_NAME + " TEXT , "
            + QUERY_STRING + " TEXT  , "
            + QUERY_FETCHED_DATA_STRING + " TEXT ) ";
    /*Create ELearningView File Time Spent Offline data table Start*/
    public static final String E_LEARNING_TIME_SPENT_OFFLINE_TABLE = "E_learning_files_time_spent";
    public static final String E_LEARNING_TIME_SPENT_OFFLINE_SERIAL_NO = "El_files_sno";
    public static final String E_LEARNING_TIME_SPENT_OFFLINE_USER_ID = "user_id";
    public static final String E_LEARNING_TIME_SPENT_OFFLINE_DB_NAME = "db_name";
    public static final String E_LEARNING_TIME_SPENT_OFFLINE_PARENT_ID = "parent_id";
    public static final String E_LEARNING_TIME_SPENT_OFFLINE_SELECTED_NODE_ID = "selected_node_id";
    public static final String E_LEARNING_TIME_SPENT_OFFLINE_CATEGORY_FILE_ID = "category_file_id";
    public static final String E_LEARNING_TIME_SPENT_OFFLINE_DISTRIBUTION_ID = "distribution_id";
    public static final String E_LEARNING_TIME_SPENT_OFFLINE_DEVICE_ID = "device_id";
    public static final String E_LEARNING_TIME_SPENT_OFFLINE_MOBILE_DATE = "date";
    public static final String E_LEARNING_TIME_SPENT_OFFLINE_START_TIME = "start_time";
    public static final String E_LEARNING_TIME_SPENT_OFFLINE_END_TIME = "end_time";
    public static final String E_LEARNING_TIME_SPENT_OFFLINE_GPS = "gps";
    public static final String E_LEARNING_TIME_SPENT_OFFLINE_VISITED_THROUGH = "visited_through";
    public static final String E_LEARNING_TIME_SPENT_OFFLINE_STATUS = "is_uploaded_to_server";
    public static final String CREATE_E_LEARNING_TIME_SPENT_OFFLINE_TABLE =
            " create table " + E_LEARNING_TIME_SPENT_OFFLINE_TABLE + " ( "
                    + E_LEARNING_TIME_SPENT_OFFLINE_SERIAL_NO + " integer primary key autoincrement , "
                    + E_LEARNING_TIME_SPENT_OFFLINE_USER_ID + " TEXT , "
                    + E_LEARNING_TIME_SPENT_OFFLINE_DB_NAME + " TEXT , "
                    + E_LEARNING_TIME_SPENT_OFFLINE_PARENT_ID + " TEXT , "
                    + E_LEARNING_TIME_SPENT_OFFLINE_SELECTED_NODE_ID + " TEXT , "
                    + E_LEARNING_TIME_SPENT_OFFLINE_CATEGORY_FILE_ID + " TEXT , "
                    + E_LEARNING_TIME_SPENT_OFFLINE_DISTRIBUTION_ID + " TEXT , "
                    + E_LEARNING_TIME_SPENT_OFFLINE_DEVICE_ID + " TEXT , "
                    + E_LEARNING_TIME_SPENT_OFFLINE_MOBILE_DATE + " TEXT , "
                    + E_LEARNING_TIME_SPENT_OFFLINE_START_TIME + " TEXT , "
                    + E_LEARNING_TIME_SPENT_OFFLINE_END_TIME + " TEXT , "
                    + E_LEARNING_TIME_SPENT_OFFLINE_GPS + " TEXT , "
                    + E_LEARNING_TIME_SPENT_OFFLINE_VISITED_THROUGH + " TEXT , "
                    + E_LEARNING_TIME_SPENT_OFFLINE_STATUS + " TEXT ) ";

    public static final String IN_TASK_TABLE = "InTask";
    public static final String IN_TASK_SNO = "SNO";
    public static final String IN_TASK_TASK_ID = "TaskID";
    public static final String IN_TASK_ORG_ID = "OrgId";
    public static final String IN_TASK_USER_ID = "UserId";
    public static final String IN_TASK_POST_ID = "PostID";
    public static final String IN_TASK_TASK_NAME = "TaskName";
    public static final String IN_TASK_TASK_DESC = "TaskDesc";
    public static final String IN_TASK_PRIORITY_ID = "PriorityId";
    public static final String IN_TASK_PRIORITY = "Priority";
    public static final String IN_TASK_IS_SINGLE_USER = "IsSingleUser";
    public static final String IN_TASK_SINGLE_USER_STATUS = "SingleUserStatus";
    public static final String IN_TASK_START_DATE = "StartDate";
    public static final String IN_TASK_END_DATE = "EndDate";
    public static final String IN_TASK_START_DISPLAY_DATE = "StartDisplayDate";
    public static final String IN_TASK_END_DISPLAY_DATE = "EndDisplayDate";
    public static final String IN_TASK_TASK_STATUS = "TaskStatus";
    public static final String IN_TASK_STATUS_ID = "TaskStatusId";
    public static final String IN_TASK_TOTAL_ASSIGNED = "TotalAssigned";
    public static final String IN_TASK_TOTAL_IN_PROGRESS = "TotalInprogress";
    public static final String IN_TASK_TOTAL_COMPLETED = "TotalCompleted";
    public static final String IN_TASK_APP_INFO = "AppInfo";
    public static final String IN_TASK_FILES_INFO = "FilesInfo";
    public static final String IN_TASK_COMMENTS_INFO = "CommentsInfo";
    public static final String IN_TASK_SINGLE_USER_INFO = "SingleUserInfo";
    public static final String IN_TASK_DISTRIBUTION_STATUS = "DistributionStatus";
    public static final String IN_TASK_DISTRIBUTION_DATE = "DistributionDate";
    public static final String IN_TASK_DISTRIBUTION_DISPLAY_DATE = "DistributionDisplayDate";
    public static final String IN_TASK_CREATED_BY = "CreatedBy";
    public static final String IN_TASK_DISTRIBUTION_ID = "DistrubutionID";
    public static final String IN_TASK_UPDATION_DATE = "TaskUpdationDate";


    public static final String CREATE_IN_TASK_TABLE =
            " create table " + IN_TASK_TABLE + " ( "
                    + IN_TASK_SNO + " TEXT , "
                    + IN_TASK_TASK_ID + " TEXT , "
                    + IN_TASK_ORG_ID + " TEXT , "
                    + IN_TASK_USER_ID + " TEXT , "
                    + IN_TASK_POST_ID + " TEXT , "
                    + IN_TASK_TASK_NAME + " TEXT , "
                    + IN_TASK_TASK_DESC + " TEXT , "
                    + IN_TASK_PRIORITY_ID + " TEXT , "
                    + IN_TASK_PRIORITY + " TEXT , "
                    + IN_TASK_IS_SINGLE_USER + " TEXT , "
                    + IN_TASK_SINGLE_USER_STATUS + " TEXT  ,  "
                    + IN_TASK_START_DATE + " TEXT , "
                    + IN_TASK_END_DATE + " TEXT , "
                    + IN_TASK_START_DISPLAY_DATE + " TEXT  ,  "
                    + IN_TASK_END_DISPLAY_DATE + " TEXT , "
                    + IN_TASK_TASK_STATUS + " TEXT , "
                    + IN_TASK_STATUS_ID + " TEXT  , "
                    + IN_TASK_TOTAL_ASSIGNED + " TEXT , "
                    + IN_TASK_TOTAL_IN_PROGRESS + " TEXT , "
                    + IN_TASK_TOTAL_COMPLETED + " TEXT , "
                    + IN_TASK_APP_INFO + " TEXT , "
                    + IN_TASK_FILES_INFO + " TEXT , "
                    + IN_TASK_COMMENTS_INFO + " TEXT , "
                    + IN_TASK_SINGLE_USER_INFO + " TEXT , "
                    + IN_TASK_DISTRIBUTION_STATUS + " TEXT , "
                    + IN_TASK_DISTRIBUTION_DATE + " TEXT , "
                    + IN_TASK_DISTRIBUTION_DISPLAY_DATE + " TEXT , "
                    + IN_TASK_CREATED_BY + " TEXT , "
                    + IN_TASK_DISTRIBUTION_ID + " TEXT , "
                    + IN_TASK_UPDATION_DATE + " TEXT ) ";


    public static final String OUT_TASK_TABLE = "OutTask";
    public static final String OUT_TASK_SNO = "Sno";
    public static final String OUT_TASK_TASK_ID = "TaskID";
    public static final String OUT_TASK_ORG_ID = "OrgId";
    public static final String OUT_TASK_USER_ID = "UserId";
    public static final String OUT_TASK_POST_ID = "PostID";
    public static final String OUT_TASK_TASK_NAME = "TaskName";
    public static final String OUT_TASK_TASK_DESC = "TaskDesc";
    public static final String OUT_TASK_PRIORITY_ID = "PriorityId";
    public static final String OUT_TASK_PRIORITY = "Priority";
    public static final String OUT_TASK_TASK_STATUS = "TaskStatus";
    public static final String OUT_TASK_IS_SINGLE_USER = "IsSingleUser";
    public static final String OUT_TASK_SINGLE_USER_STATUS = "SingleUserStatus";
    public static final String OUT_TASK_START_DATE = "StartDate";
    public static final String OUT_TASK_END_DATE = "EndDate";
    public static final String OUT_TASK_DISTRIBUTION_DATE = "DistributionDate";
    public static final String OUT_TASK_START_DISPLAY_DATE = "StartDisplayDate";
    public static final String OUT_TASK_END_DISPLAY_DATE = "EndDisplayDate";
    public static final String OUT_TASK_STATUS = "Status";
    public static final String OUT_TASK_CREATED_BY = "CreatedBy";
    public static final String OUT_TASK_TOTAL_ASSIGNED = "TotalAssigned";
    public static final String OUT_TASK_TOTAL_IN_PROGRESS = "TotalInprogress";
    public static final String OUT_TASK_TOTAL_COMPLETED = "TotalCompleted";
    public static final String OUT_TASK_APP_INFO = "AppInfo";
    public static final String OUT_TASK_FILES_INFO = "FilesInfo";
    public static final String OUT_TASK_GROUP_INFO = "GroupInfo";
    public static final String OUT_TASK_EMP_INFO = "EmpInfo";
    public static final String OUT_TASK_COMMENTS_INFO = "CommentsInfo";
    public static final String OUT_TASK_DISTRIBUTION_STATUS = "DistributionStatus";
    public static final String OUT_TASK_DISTRIBUTION_DISPLAY_DATE = "DistributionDisplayDate";
    public static final String OUT_TASK_UPDATION_DATE = "TaskUpdationDate";

    public static final String CREATE_OUT_TASK_TABLE =
            " create table " + OUT_TASK_TABLE + " ( "
                    + OUT_TASK_SNO + " , "
                    + OUT_TASK_TASK_ID + " TEXT , "
                    + OUT_TASK_ORG_ID + " TEXT , "
                    + OUT_TASK_USER_ID + " TEXT , "
                    + OUT_TASK_POST_ID + " TEXT , "
                    + OUT_TASK_TASK_NAME + " TEXT , "
                    + OUT_TASK_TASK_DESC + " TEXT , "
                    + OUT_TASK_PRIORITY_ID + " TEXT , "
                    + OUT_TASK_PRIORITY + " TEXT , "
                    + OUT_TASK_TASK_STATUS + " TEXT , "
                    + OUT_TASK_IS_SINGLE_USER + " TEXT , "
                    + OUT_TASK_SINGLE_USER_STATUS + " TEXT , "
                    + OUT_TASK_START_DATE + " TEXT , "
                    + OUT_TASK_END_DATE + " TEXT , "
                    + OUT_TASK_DISTRIBUTION_DATE + " TEXT , "
                    + OUT_TASK_START_DISPLAY_DATE + " TEXT , "
                    + OUT_TASK_END_DISPLAY_DATE + " TEXT , "
                    + OUT_TASK_STATUS + " TEXT , "
                    + OUT_TASK_CREATED_BY + " TEXT , "
                    + OUT_TASK_TOTAL_ASSIGNED + " TEXT , "
                    + OUT_TASK_TOTAL_IN_PROGRESS + " TEXT , "
                    + OUT_TASK_TOTAL_COMPLETED + " TEXT , "
                    + OUT_TASK_APP_INFO + " TEXT , "
                    + OUT_TASK_FILES_INFO + " TEXT , "
                    + OUT_TASK_GROUP_INFO + " TEXT , "
                    + OUT_TASK_EMP_INFO + " TEXT , "
                    + OUT_TASK_COMMENTS_INFO + " TEXT , "
                    + OUT_TASK_DISTRIBUTION_STATUS + " TEXT , "
                    + OUT_TASK_DISTRIBUTION_DISPLAY_DATE + " TEXT , "
                    + OUT_TASK_UPDATION_DATE + " TEXT ) ";
    public static final String E_LEARNING_TABLE = "E_Learning";
    public static final String E_LEARNING_DISTRIBUTION_ID = "DistributionId";
    public static final String E_LEARNING_NO_OF_USER_ATTEMPTS = "NoOfUserAttempts";
    /*Notifications table */
    public static final String NOTIFICATION_TABLE = "notification_table";
    public static final String MESSAGE_ID = "message_id";
    public static final String SENDER_ID = "sender_id";
    public static final String SENDER_NAME = "sender_name";
    public static final String GROUP_ID = "group_id";
    public static final String GROUP_ICON = "group_icon";
    public static final String GROUP_NAME = "group_name";
    public static final String SESSION_ID = "session_id";
    public static final String SESSION_NAME = "session_name";
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";
    public static final String MESSAGE_TYPE = "message_type";
    public static final String FILE_NAME = "file_name";
    public static final String FILE_PATH = "file_path";
    public static final String TIME_STAMP = "time_stamp";
    public static final String UNREAD_COUNT = "unread_count";
    public static final String STATUS = "status";
    public static final String TEMP_COLUMN_ONE = "temp_column_one";
    public static final String TEMP_COLUMN_TWO = "temp_column_two";
    /*Notification table */
    public static final String CREATE_NOTIFICATION_TABLE =
            " create table " + NOTIFICATION_TABLE + " ( "
                    + SERIAL_NO + " integer primary key autoincrement , "
                    + MESSAGE_ID + " TEXT , "
                    + SENDER_ID + " TEXT , "
                    + SENDER_NAME + " TEXT , "
                    + GROUP_ID + " TEXT , "
                    + GROUP_ICON + " TEXT , "
                    + GROUP_NAME + " TEXT , "
                    + SESSION_ID + " TEXT , "
                    + SESSION_NAME + " TEXT ,"
                    + TITLE + " TEXT ,"
                    + MESSAGE + " TEXT ,"
                    + MESSAGE_TYPE + " TEXT ,"
                    + FILE_NAME + " TEXT ,"
                    + FILE_PATH + " TEXT ,"
                    + TIME_STAMP + " TEXT ,"
                    + UNREAD_COUNT + " TEXT ,"
                    + USER_ID + " TEXT ,"
                    + POST_ID + " TEXT ,"
                    + DB_ORG_ID + " TEXT ,"
                    + STATUS + " TEXT ,"
                    + TEMP_COLUMN_ONE + " TEXT ,"
                    + TEMP_COLUMN_TWO + " TEXT) ";
    /*Notifications table */

    /*All Notifications table */
    public static final String ALL_NOTIFICATIONS_TABLE = "all_notifications_table";
    public static final String CREATE_ALL_NOTIFICATIONS_TABLE =
            " create table " + ALL_NOTIFICATIONS_TABLE + " ( "
                    + SERIAL_NO + " integer primary key autoincrement , "
                    + MESSAGE_ID + " TEXT , "
                    + TITLE + " TEXT ,"
                    + MESSAGE + " TEXT ,"
                    + MESSAGE_TYPE + " TEXT ,"
                    + FILE_NAME + " TEXT ,"
                    + FILE_PATH + " TEXT ,"
                    + TIME_STAMP + " TEXT ,"
                    + UNREAD_COUNT + " TEXT ,"
                    + USER_ID + " TEXT ,"
                    + POST_ID + " TEXT ,"
                    + DB_ORG_ID + " TEXT ,"
                    + STATUS + " TEXT ,"
                    + TEMP_COLUMN_ONE + " TEXT ,"
                    + TEMP_COLUMN_TWO + " TEXT) ";
    /*Notifications table */

    public static final String GROUP_TABLE = "group_table";
    public static final String POST_NAME = "post_name";
    public static final String READ = "read";
    public static final String WRITE = "write";
    public static final String GROUP_TYPE = "group_type";
    public static final String DEPENDENT_ID = "dependent_id";
    public static final String USER_TYPE = "user_type";
    /*Notification table */
    public static final String CREATE_GROUP_TABLE =
            " create table " + GROUP_TABLE + " ( "
                    + SERIAL_NO + " integer primary key autoincrement , "
                    + GROUP_ID + " TEXT , "
                    + GROUP_NAME + " TEXT , "
                    + GROUP_ICON + " TEXT , "
                    + GROUP_TYPE + " TEXT , "
                    + DEPENDENT_ID + " TEXT , "
                    + USER_TYPE + " TEXT , "
                    + READ + " TEXT , "
                    + WRITE + " TEXT , "
                    + USER_ID + " TEXT , "
                    + DB_ORG_ID + " TEXT , "
                    + STATUS + " TEXT ,"
                    + TEMP_COLUMN_ONE + " TEXT ,"
                    + TEMP_COLUMN_TWO + " TEXT) ";

    /* Send Comments InTask table*/
    public static final String SEND_COMMENTS_OFFLINE_TABLE = "CommentsOfflineTable";
    public static final String SEND_COMMENTS_OFFLINE_S_NO = "SNo";
    public static final String SEND_COMMENTS_OFFLINE_ORG_ID = "OrgId";
    public static final String SEND_COMMENTS_OFFLINE_TASK_ID = "TaskId";
    public static final String SEND_COMMENTS_OFFLINE_COMMENTS = "Comments";
    public static final String SEND_COMMENTS_OFFLINE_TASK_STATUS_ID = "TaskStatus_id";
    public static final String SEND_COMMENTS_OFFLINE_USER_ID = "UserId";
    public static final String SEND_COMMENTS_OFFLINE_POST_ID = "PostId";
    public static final String SEND_COMMENTS_OFFLINE_LOCATION_CODE = "Location_Code";
    public static final String SEND_COMMENTS_OFFLINE_DEPARTMENT_ID = "DepartmentId";
    public static final String SEND_COMMENTS_OFFLINE_DESIGNATION_ID = "DesignationId";
    public static final String SEND_COMMENTS_OFFLINE_IS_SELF_COMMENT = "ISSelfComment";
    public static final String SEND_COMMENTS_OFFLINE_DEVICE_ID = "DeviceId";
    public static final String SEND_COMMENTS_OFFLINE_VERSION_NO = "VersionNo";
    public static final String SEND_COMMENTS_OFFLINE_MOBILE_DATE = "MobileDate";
    /*SendCommentOffLine table Creation*/
    public static final String CREATE_COMMENTS_OFF_LINE_TABLE =
            " create table " + SEND_COMMENTS_OFFLINE_TABLE + " ( "
                    + SEND_COMMENTS_OFFLINE_S_NO + " integer primary key autoincrement , "
                    + SEND_COMMENTS_OFFLINE_ORG_ID + " TEXT , "
                    + SEND_COMMENTS_OFFLINE_TASK_ID + " TEXT , "
                    + SEND_COMMENTS_OFFLINE_COMMENTS + " TEXT , "
                    + SEND_COMMENTS_OFFLINE_TASK_STATUS_ID + " TEXT , "
                    + SEND_COMMENTS_OFFLINE_USER_ID + " TEXT , "
                    + SEND_COMMENTS_OFFLINE_POST_ID + " TEXT , "
                    + SEND_COMMENTS_OFFLINE_LOCATION_CODE + " TEXT , "
                    + SEND_COMMENTS_OFFLINE_DEPARTMENT_ID + " TEXT , "
                    + SEND_COMMENTS_OFFLINE_DESIGNATION_ID + " TEXT , "
                    + SEND_COMMENTS_OFFLINE_IS_SELF_COMMENT + " TEXT , "
                    + SEND_COMMENTS_OFFLINE_DEVICE_ID + " TEXT , "
                    + SEND_COMMENTS_OFFLINE_VERSION_NO + " TEXT , "
                    + SEND_COMMENTS_OFFLINE_MOBILE_DATE + " TEXT ) ";

    /* Send Comments InTask table*/
    public static final String OUT_TASK_SEND_COMMENTS_OFFLINE_TABLE = "OutTaskCommentsOfflineTable";
    public static final String OUT_TASK_SEND_COMMENTS_OFFLINE_S_NO = "SNo";
    public static final String OUT_TASK_SEND_COMMENTS_OFFLINE_ORG_ID = "OrgId";
    public static final String OUT_TASK_SEND_COMMENTS_OFFLINE_TASK_ID = "TaskId";
    public static final String OUT_TASK_SEND_COMMENTS_OFFLINE_COMMENTS = "Comments";
    public static final String OUT_TASK_SEND_COMMENTS_OFFLINE_TASK_STATUS_ID = "TaskStatus_id";
    public static final String OUT_TASK_SEND_COMMENTS_OFFLINE_USER_ID = "UserId";
    public static final String OUT_TASK_SEND_COMMENTS_OFFLINE_POST_ID = "PostId";
    public static final String OUT_TASK_SEND_COMMENTS_OFFLINE_LOCATION_CODE = "Location_Code";
    public static final String OUT_TASK_SEND_COMMENTS_OFFLINE_DEPARTMENT_ID = "DepartmentId";
    public static final String OUT_TASK_SEND_COMMENTS_OFFLINE_DESIGNATION_ID = "DesignationId";
    public static final String OUT_TASK_SEND_COMMENTS_OFFLINE_IS_SELF_COMMENT = "ISSelfComment";
    public static final String OUT_TASK_SEND_COMMENTS_OFFLINE_DEVICE_ID = "DeviceId";
    public static final String OUT_TASK_SEND_COMMENTS_OFFLINE_VERSION_NO = "VersionNo";
    public static final String OUT_TASK_SEND_COMMENTS_OFFLINE_MOBILE_DATE = "MobileDate";
    /*SendCommentOffLine table Creation*/
    public static final String CREATE_COMMENTS_OFF_LINE_TABLE_OUT_TASK =
            " create table " + OUT_TASK_SEND_COMMENTS_OFFLINE_TABLE + " ( "
                    + OUT_TASK_SEND_COMMENTS_OFFLINE_S_NO + " integer primary key autoincrement , "
                    + OUT_TASK_SEND_COMMENTS_OFFLINE_ORG_ID + " TEXT , "
                    + OUT_TASK_SEND_COMMENTS_OFFLINE_TASK_ID + " TEXT , "
                    + OUT_TASK_SEND_COMMENTS_OFFLINE_COMMENTS + " TEXT , "
                    + OUT_TASK_SEND_COMMENTS_OFFLINE_TASK_STATUS_ID + " TEXT , "
                    + OUT_TASK_SEND_COMMENTS_OFFLINE_USER_ID + " TEXT , "
                    + OUT_TASK_SEND_COMMENTS_OFFLINE_POST_ID + " TEXT , "
                    + OUT_TASK_SEND_COMMENTS_OFFLINE_LOCATION_CODE + " TEXT , "
                    + OUT_TASK_SEND_COMMENTS_OFFLINE_DEPARTMENT_ID + " TEXT , "
                    + OUT_TASK_SEND_COMMENTS_OFFLINE_DESIGNATION_ID + " TEXT , "
                    + OUT_TASK_SEND_COMMENTS_OFFLINE_IS_SELF_COMMENT + " TEXT , "
                    + OUT_TASK_SEND_COMMENTS_OFFLINE_DEVICE_ID + " TEXT , "
                    + OUT_TASK_SEND_COMMENTS_OFFLINE_VERSION_NO + " TEXT , "
                    + OUT_TASK_SEND_COMMENTS_OFFLINE_MOBILE_DATE + " TEXT ) ";


    /*FailedComments table*/
    public static final String COMMENTS_FAILED_TABLE = "comments_failed_table";
    public static final String COMMENTS_FAILED_TASK_ID = "TaskId";
    public static final String COMMENTS_FAILED_ORG_ID = "OrgId";
    public static final String COMMENTS_FAILED_TASK_COMMENTS = "TaskComments";
    public static final String COMMENTS_FAILED_TASK_STATUS_ID = "TaskStatusId";
    public static final String COMMENTS_FAILED_USER_ID = "UserId";
    public static final String COMMENTS_FAILED_POST_ID = "PostId";
    public static final String COMMENTS_FAILED_LOCATION_CODE = "LocationCode";
    public static final String COMMENTS_FAILED_DEPARTMENT_ID = "DepartmentId";
    public static final String COMMENTS_FAILED_DESIGNATION_ID = "DesignationId";
    public static final String COMMENTS_FAILED_IS_SELF_COMMENT = "IsSelfComment";
    public static final String COMMENTS_FAILED_DEVICE_ID = "DeviceId";
    public static final String COMMENTS_FAILED_VERSION_NO = "VersionNo";
    public static final String COMMENTS_FAILED_MOBILE_DATE = "MobileDate";
    public static final String COMMENTS_FAILED_OFF_LINE_STATUS = "off_line_status";
    /*FailedComments table Creation */
    public static final String CREATE_COMMENTS_FAILED_TABLE =
            " create table " + COMMENTS_FAILED_TABLE + " ( "
                    + COMMENTS_FAILED_TASK_ID + " integer primary key , "
                    + COMMENTS_FAILED_ORG_ID + " TEXT , "
                    + COMMENTS_FAILED_TASK_COMMENTS + " TEXT , "
                    + COMMENTS_FAILED_TASK_STATUS_ID + " TEXT , "
                    + COMMENTS_FAILED_USER_ID + " TEXT , "
                    + COMMENTS_FAILED_POST_ID + " TEXT , "
                    + COMMENTS_FAILED_LOCATION_CODE + " TEXT , "
                    + COMMENTS_FAILED_DEPARTMENT_ID + " TEXT , "
                    + COMMENTS_FAILED_DESIGNATION_ID + " TEXT , "
                    + COMMENTS_FAILED_IS_SELF_COMMENT + " TEXT , "
                    + COMMENTS_FAILED_DEVICE_ID + " TEXT , "
                    + COMMENTS_FAILED_VERSION_NO + " TEXT , "
                    + COMMENTS_FAILED_MOBILE_DATE + " TEXT , "
                    + COMMENTS_FAILED_OFF_LINE_STATUS + " TEXT ) ";
    /* InTask all Comments Table*/
    public static final String IN_TASK_COMMENTS_TABLE = "InTaskCommentsTable";
    /* OutTask all Comments Table*/
    public static final String OUT_TASK_COMMENTS_TABLE = "OutTaskCommentsTable";
    public static final String DEPLOYMENT_TABLE = "Deployment";
    private final String TAG = "ImproveDataBase";
    private final String DEPENDENT_CONTROL = "dependent_control";
    private final String DATA_CONTROL_TYPE = "data_control_type";
    private final String CONTROL_NAME = "ControlName";
    private final String CONTROL_STATUS = "ControlStatus";
    private final String ACCESSIBILTY = "Accessibility";
    private final String VERSION = "Version";
    /*Query Form Table*/
    private final String LOC_TYPE = "loc_type";
    private final String TEXT_FILE_PATH = "TextFilePath";
    private final String CREATED_BY = "CreatedBy";
    private final String CREATED_USER_ID = "CreatedUserID";
    public final String CREATE_DATA_CONTROL_TABLE =
            " create table " + DATA_CONTROL_TABLE + " ( "
                    + FORM_SUBMIT_TABLE_SERIAL_NO + " integer primary key autoincrement , "
                    + DB_ORG_ID + " TEXT , "
                    + DEPENDENT_CONTROL + " TEXT , "
                    + DATA_CONTROL_TYPE + " TEXT , "
                    + CREATED_USER_ID + " TEXT  , "
                    + CONTROL_NAME + " TEXT  ,  "
                    + CONTROL_STATUS + " TEXT  ,  "
                    + ACCESSIBILTY + " TEXT , "
                    + VERSION + " TEXT  ,  "
                    + LOC_TYPE + " TEXT , "
                    + TEXT_FILE_PATH + " TEXT , "
                    + USER_ID + " TEXT ) ";
    private final String CREATED_USER_NAME = "CreatedUserName";
    private final String DESCRIPTION = "Description";
    private final String ISACTIVE = "IsActive";
    /*Query --Fetched data*/
    private final String APP_NAME = "AppName";
    private final String APP_TYPE = "AppType";
    private final String WORKSPACEName = "WORKSPACEName";
    private final String WORKSPACEAS = "WorkspaceAs";
    private final String USER_TYPE_IDS = "user_type_ids";
    private final String DISPLAYAPPNAME = "DisplayAppName";
    private final String APP_VERSION = "AppVersion";
    private final String TABLE_NAME = "TableName";
    private final String TABLE_SETTINGS_TYPE = "TableSettingsType";
    private final String MAP_EXISTING_TYPE = "MapExistingType";
    private final String HAS_MAP_EXISTING = "HasMapExisting";
    private final String MAIN_TABLE_INSERT_FIELDS = "MainTableInsertFields";
    private final String MAIN_TABLE_UPDATE_FIELDS = "MainTableUpdateFields";
    private final String MAIN_TABLE_FILTER_FIELDS = "MainTableFilterFields";
    private final String SUBFORM_MAP_EXISTING_DATA = "subFormMapExistingData";
    /*Create Submit table*/
    public final String CREATE_SUBMIT_TABLE =
            " create table " + FORM_SUBMIT_TABLE + " ( "
                    + FORM_SUBMIT_TABLE_SERIAL_NO + " integer primary key autoincrement , "
                    + DB_ORG_ID + " TEXT , "
                    + PAGE_NAME + " TEXT ,"
                    + CREATED_USER_ID_SUBMIT + " TEXT ,"
                    + SUBMITTED_UER_ID + " TEXT ,"
                    + DISTRIBUTION_ID + " TEXT ,"
                    + IMEI + " TEXT ,"
                    + OPERATION_TYPE + " TEXT ,"
                    + TRANS_ID + " TEXT ,"
                    + APP_VERSION + " TEXT ,"
                    + TABLE_NAME + " TEXT ,"
                    + TABLE_SETTINGS_TYPE + " TEXT ,"
                    + MAP_EXISTING_TYPE + " TEXT ,"
                    + MAIN_TABLE_INSERT_FIELDS + " TEXT ,"
                    + MAIN_TABLE_UPDATE_FIELDS + " TEXT ,"
                    + MAIN_TABLE_FILTER_FIELDS + " TEXT ,"
                    + SUBFORM_MAP_EXISTING_DATA + " TEXT ,"
                    + FORM_SUBMIT_TABLE_APP_NAME + " TEXT , "
                    + FORM_SUBMIT_TABLE_JSON_STRING + " TEXT  , "
                    + FORM_SUBMIT_TABLE_JSON_FILE_STRING + " TEXT  ,  "
                    + FORM_SUBMIT_TABLE_JSON_STRING_SUB_FORM + " TEXT  ,  "
                    + FORM_SUBMIT_TABLE_JSON_FILE_STRING_SUB_FORM + " TEXT , "
                    + FORM_SUBMIT_TABLE_SYNC_STATUS + " TEXT , "
                    + FORM_SUBMIT_TABLE_OFFLINE_JSON + " TEXT , "
                    + USER_ID + " TEXT , "
                    + POST_ID + " TEXT , "
                    + HAS_MAP_EXISTING + " TEXT ) ";
    //App Sharing Options
    private final String IS_DATA_PERMISSION = "IsDataPermission";
    private final String DATA_PERMISSION_XML = "DataPermissionXML";
    private final String DATA_PERMISSION_XML_FILEPATH = "DataPermissionXmlFilePath";
    private final String IS_CONTROL_VISIBILTY = "IsControlVisibility";
    private final String CONTROL_VISIBILTY_XML = "ControlVisibilityXML";
    private final String CONTROL_VISIBILTY_XML_FILEPATH = "ControlVisibilityXmlFilePath";
    private final String SHARING_VERSION = "SharingVersion";
    //App Sharing Options
    private final String DISPLAY_ICON = "DisplayIcon";
    private final String DESIGN_FORMAT = "DesignFormat";
    private final String VIDEO_LINK = "VideoLink";
    private final String NEWROW = "NewRow";
    private final String APP_ICON = "AppIcon";
    private final String APK_VERSION = "ApkVersion";
    private final String XML_FILE_PATH = "XMLFilePath";
    private final String APP_MODE = "AppMode";
    private final String DISPLAY_AS = "DisplayAs";
    private final String DISPLAY_REPORT_AS = "DisplayReportas";
    private final String USER_LOCATION = "UserLocation";
    private final String Traning = "Traning";
    private final String Testing = "Testing";
    private final String VISIBLE_IN = "VisibileIn";
    private final String APPLICATION_RECEIVED_DATE = "ApplicationReceivedDate";
    private final String COLUMN_ONE = "ColumnOne";
    private final String COLUMN_TWO = "ColumnTwo";
    public final String APPS_LIST_TABLE_CREATE_IN_TASK =
            " create table " + APPS_LIST_TABLE_IN_TASK + " ( "
                    + SERIAL_NO + " integer primary key autoincrement , " + DB_ORG_ID + " TEXT , " +
                    DB_ORG_NAME + " TEXT , " + DISTRIBUTION_ID + " TEXT , " + CREATED_BY + " TEXT , " +
                    CREATED_USER_NAME + " TEXT , " + DESCRIPTION + " TEXT , " + ISACTIVE + " TEXT , " +
                    APP_NAME + " TEXT , " + APP_VERSION + " TEXT , " + APP_TYPE + " TEXT , " +
                    DESIGN_FORMAT + " TEXT , " + VIDEO_LINK + " TEXT , " + NEWROW + " TEXT , " +
//                    DESIGN_FORMAT + " NVARCHAR(MAX) , " + VIDEO_LINK + " TEXT , " + NEWROW + " TEXT , " +
                    APP_ICON + " TEXT , " + APK_VERSION + " TEXT , " + APPLICATION_RECEIVED_DATE + " TEXT , " +
                    XML_FILE_PATH + " TEXT, " + COLUMN_ONE + " TEXT , " + COLUMN_TWO + " TEXT, "
                    + APP_MODE + " TEXT,"
                    + USER_ID + " TEXT,"
                    + POST_ID + " TEXT ) ";
    private final String TABLE_COLUMNS = "TableColumns";
    private final String PRIMARY_KEYS = "PrimaryKeys";
    private final String FOREIGN_KEYS = "ForeignKeys";
    private final String COMPOSITE_KEYS = "CompositeKeys";
    private final String DISPLAY_IN_APP_LIST = "DisplayInAppList";
    private final String SUBFORM_DETAILS = "SubformDetails";
    private final String AUTOINCREMENT_CONTROLNAMES = "AutoIncrementControlNames";
    public final String APPS_LIST_TABLE_CREATE =
            " create table " + APPS_LIST_TABLE + " ( "
                    + SERIAL_NO + " integer primary key autoincrement , " + DB_ORG_ID + " TEXT , " +
                    DB_ORG_NAME + " TEXT , " + DISTRIBUTION_ID + " TEXT , " + CREATED_BY + " TEXT , " +
                    CREATED_USER_NAME + " TEXT , " + DESCRIPTION + " TEXT , " + ISACTIVE + " TEXT , " +
                    APP_NAME + " TEXT , " + APP_VERSION + " TEXT , " + APP_TYPE + " TEXT , " +
                    DESIGN_FORMAT + " TEXT , " + VIDEO_LINK + " TEXT , " + NEWROW + " TEXT , " +
//                    DESIGN_FORMAT + " NVARCHAR(MAX) , " + VIDEO_LINK + " TEXT , " + NEWROW + " TEXT , " +
                    APP_ICON + " TEXT , " + APK_VERSION + " TEXT , " + APPLICATION_RECEIVED_DATE + " TEXT , " +
                    XML_FILE_PATH + " TEXT, " + COLUMN_ONE + " TEXT , " + COLUMN_TWO + " TEXT, "
                    + APP_MODE + " TEXT,"
                    + USER_ID + " TEXT,"
                    + POST_ID + " TEXT,"
                    + DISPLAY_AS + " TEXT,"
                    + DISPLAY_REPORT_AS + " TEXT,"
                    + USER_LOCATION + " TEXT,"
                    + Traning + " TEXT,"
                    + Testing + " TEXT,"
                    + VISIBLE_IN + " TEXT,"
                    + WORKSPACEName + " TEXT,"
                    + WORKSPACEAS + " TEXT,"
                    + DISPLAYAPPNAME + " TEXT,"
                    + TABLE_NAME + " TEXT,"
                    + TABLE_COLUMNS + " TEXT,"
                    + PRIMARY_KEYS + " TEXT,"
                    + FOREIGN_KEYS + " TEXT,"
                    + COMPOSITE_KEYS + " TEXT,"
                    + SUBFORM_DETAILS + " TEXT,"
                    + DISPLAY_IN_APP_LIST + " TEXT,"
                    + USER_TYPE_IDS + " TEXT,"
                    + IS_DATA_PERMISSION + " TEXT,"
                    + DATA_PERMISSION_XML + " TEXT,"
                    + DATA_PERMISSION_XML_FILEPATH + " TEXT,"
                    + IS_CONTROL_VISIBILTY + " TEXT,"
                    + CONTROL_VISIBILTY_XML + " TEXT,"
                    + CONTROL_VISIBILTY_XML_FILEPATH + " TEXT,"
                    + SHARING_VERSION + " TEXT,"
                    + DISPLAY_ICON + " TEXT,"
                    + AUTOINCREMENT_CONTROLNAMES + " TEXT ) ";
    /*Create Apps list table */
    /*API Names Table*/
    private final String API_NAMES_TABLE = "ApiNamesTable";
    private final String API_NAMES_TABLE_SERIAL_NO = "ApiNamesTable";
    private final String SERVICE_NAME = "ServiceName";
    private final String SERVICE_DESC = "ServiceDesc";
    private final String ACCESSIBILITY = "Accessibility";
    /*E-Learning Table Start*/
    private final String SERVICE_SOURCE = "ServiceSource";
    private final String SERVICE_TYPE = "ServiceType";
    private final String SERVICE_CALL_AT = "ServiceCallsAt";
    private final String SERVICE_RESULT = "ServiceResult";
    private final String SERVICE_URL = "ServiceURl";
    private final String SERVICE_MASK = "ServiceURl_Mask";
    private final String INPUT_PARAMETERS = "InputParameters";
    private final String OUTPUT_PARAMETERS = "OutputParameters";
    private final String OUTPUT_TYPE = "OutputType";
    private final String API_NAME_VERSION = "Version";
    private final String XML_PATH = "XMLPath";
    private final String METHOD_NAME = "MethodName";
    private final String XML_FORMAT = "XMLFormat";
    private final String NAME_SPACE = "NameSpace";
    private final String METHOD_TYPE = "MethodType";
    /*Create Api Names table*/
    public final String CREATE_API_NAMES_TABLE =
            " create table " + API_NAMES_TABLE + " ( "
                    + API_NAMES_TABLE_SERIAL_NO + " integer primary key autoincrement , "
                    + DB_ORG_ID + " TEXT , "
                    + CREATED_USER_ID + " TEXT , "
                    + SERVICE_NAME + " TEXT , "
                    + SERVICE_DESC + " TEXT , "
                    + ACCESSIBILITY + " TEXT , "
                    + SERVICE_SOURCE + " TEXT , "
                    + SERVICE_TYPE + " TEXT , "
                    + SERVICE_CALL_AT + " TEXT , "
                    + SERVICE_RESULT + " TEXT , "
                    + SERVICE_URL + " TEXT , "
                    + SERVICE_MASK + " TEXT , "
                    + INPUT_PARAMETERS + " TEXT , "
                    + OUTPUT_PARAMETERS + " TEXT , "
                    + OUTPUT_TYPE + " TEXT , "
                    + API_NAME_VERSION + " TEXT , "
                    + XML_PATH + " TEXT , "
                    + METHOD_NAME + " TEXT , "
                    + XML_FORMAT + " TEXT , "
                    + NAME_SPACE + " TEXT , "
                    + METHOD_TYPE + " TEXT , "
                    + USER_ID + " TEXT ) ";
    private final String E_LEARNING_SNO = "E_Learning_Sno";
    private final String E_LEARNING_ORGANIZATION_ID = "Organization_Id";
    private final String E_LEARNING_TOPIC_NAME = "TopicName";
    /*TaskContent Table */
    private final String E_LEARNING_IS_ASSESSMENT = "Is_Assessment";
    private final String E_LEARNING_START_DATE = "StartDate";
    private final String E_LEARNING_END_DATE = "EndDate";
    private final String E_LEARNING_START_TIME = "StartTime";
    private final String E_LEARNING_END_TIME = "ENDTime";
    private final String E_LEARNING_EXAM_DURATION = "ExamDuration";
    private final String E_LEARNING_NO_OF_ATTEMPTS = "NoOfAttempts";
    private final String E_LEARNING_DISTRIBUTION_DATE = "DistributionDate";
    private final String E_LEARNING_START_DISPLAY_DATE = "StartDisplayDate";
    private final String E_LEARNING_END_DISPLAY_DATE = "EndDisplayDate";
    private final String E_LEARNING_START_DISPLAY_TIME = "StartDisplayTime";
    private final String E_LEARNING_END_DISPLAY_TIME = "EndDisplayTime";
    private final String E_LEARNING_FILES_INFO = "FilesInfo";
    /*Create ELearningView File Time Spent Offline data table End*/
    private final String E_LEARNING_QUESTIONS = "Questions";
    private final String E_LEARNING_H_QUESTIONS = "hQuestions";
    private final String E_LEARNING_M_QUESTIONS = "mQuestions";
    private final String E_LEARNING_T_QUESTIONS = "tQuestions";
    private final String E_LEARNING_L_QUESTIONS = "lQuestions";
    private final String E_LEARNING_IS_COMPLEXITY = "Is_Compexcity";
    private final String E_LEARNING_TOPIC_COVER_IMAGE = "TopicCoverImage";
    private final String E_LEARNING_TOPIC_DESCRIPTION = "TopicDescription";
    private final String E_LEARNING_TOPIC_UPDATION_DATE = "TaskUpdationDate";
    public final String CREATE_E_LEARNING_TABLE =
            " create table " + E_LEARNING_TABLE + " ( "
                    + E_LEARNING_SNO + " TEXT , "
                    + E_LEARNING_DISTRIBUTION_ID + " TEXT , "
                    + E_LEARNING_ORGANIZATION_ID + " TEXT , "
                    + E_LEARNING_TOPIC_NAME + " TEXT , "
                    + E_LEARNING_IS_ASSESSMENT + " TEXT  , "
                    + E_LEARNING_START_DATE + " TEXT  ,  "
                    + E_LEARNING_END_DATE + " TEXT  ,  "
                    + E_LEARNING_START_TIME + " TEXT , "
                    + E_LEARNING_END_TIME + " TEXT  ,  "
                    + E_LEARNING_EXAM_DURATION + " TEXT , "
                    + E_LEARNING_NO_OF_ATTEMPTS + " TEXT , "
                    + E_LEARNING_DISTRIBUTION_DATE + " TEXT , "
                    + E_LEARNING_START_DISPLAY_DATE + " TEXT , "
                    + E_LEARNING_END_DISPLAY_DATE + " TEXT , "
                    + E_LEARNING_START_DISPLAY_TIME + " TEXT , "
                    + E_LEARNING_END_DISPLAY_TIME + " TEXT , "
                    + E_LEARNING_NO_OF_USER_ATTEMPTS + " TEXT , "
                    + E_LEARNING_FILES_INFO + " TEXT , "
                    + E_LEARNING_H_QUESTIONS + " TEXT , "
                    + E_LEARNING_M_QUESTIONS + " TEXT , "
                    + E_LEARNING_T_QUESTIONS + " TEXT , "
                    + E_LEARNING_L_QUESTIONS + " TEXT , "
                    + E_LEARNING_IS_COMPLEXITY + " TEXT , "
                    + USER_ID + " TEXT , "
                    + DB_ORG_ID + " TEXT , "
                    + POST_ID + " TEXT , "
                    + E_LEARNING_TOPIC_COVER_IMAGE + " TEXT , "
                    + E_LEARNING_TOPIC_DESCRIPTION + " TEXT , "
                    + E_LEARNING_TOPIC_UPDATION_DATE + " TEXT ) ";
    private final String E_LEARNING_ASSESSMENT_INFO = "AssessmentInfo";
    /*E-Learning Files Start*/
    private final String EF_FF_DISTRIBUTED_ID = "FFDistributeId";
    private final String EF_CATEGORY_FILE_ID = "CategoryFileID";
    private final String EF_PARENT_ID = "Parent_Id";
    private final String EF_SELECTED_NODE_ID = "Selected_Node_Id";
    /*  E- Learning Question - Answers End*/
    /*E-Learning Questions End*/
    /*E-Learning AssessmentInfo Start*/
    private final String EF_SELECTED_FILE_NAME = "SelectedFileName";
    /*InTasks Table Start*/
    /*E-Learning Table End*/
    private final String EF_DESCRIPTION = "FileDescription";
    private final String EF_FILE_NAME_EXT = "FileNameExt";
    private final String EF_FILE_PATH = "FilePath";
    /*InTasks Table End*/
    private final String EF_IS_PRIVATE_PUBLIC = "IsPrivatePublic";
    /*E-Learning Files End*/
    private final String EF_DOWNLOAD_URL = "DownloadUrl";
    /*E-Learning Questions Start*/
    private final String EQ_DISTRIBUTEQUESTION_ID = "DistributeQuestionID";
    private final String EQ_QUESTION_ID = "QuestionID";
    private final String EQ_QUESTION = "Question";
    private final String EQ_MARKS = "Marks";
    private final String EQ_QUESTION_COMPLEXITY_ID = "QuestionComplexityId";
    private final String EQ_QUESTION_COMPLEXITY = "QuestionComplexity";
    private final String EQ_QUESTION_TYPE_ID = "QuestionTypeId";
    private final String EQ_QUESTION_TYPE = "QuestionType";
    private final String EQ_ANSWERS = "Answers";
    /*  E- Learning Question - Answers start*/
    private final String EA_ANSWER_ID = "AnswerID";
    /*E-Learning AssessmentInfo End*/
    private final String EA_ANSWER = "Answer";
    private final String IN_TASK_DETAILS_TABLE = "InTaskDetailsTable";
    private final String IN_TASK_ID = "Task_Id";
    private final String IN_TASK_DETAILS_APPS = "Apps";
    private final String IN_TASK_DETAILS_COMMENTS = "Comments";
    private final String IN_TASK_DETAILS_CONTENT = "Content";
    private final String IN_TASK_DETAILS_DATA = "TaskData";
    public final String CREATE_IN_TASK_DETAILS_TABLE =
            " create table " + IN_TASK_DETAILS_TABLE + " ( "
                    + IN_TASK_ID + " TEXT , "
                    + IN_TASK_DETAILS_APPS + " TEXT , "
                    + IN_TASK_DETAILS_COMMENTS + " TEXT , "
                    + IN_TASK_DETAILS_CONTENT + " TEXT , "
                    + IN_TASK_DETAILS_DATA + " TEXT , "
                    + USER_ID + " TEXT , "
                    + DB_ORG_ID + " TEXT ) ";
    private final String IN_TASK_COMMENTS_SNO = "InTaskCommentsSno";
    private final String IN_TASK_COMMENTS_ID = "TaskCommentID";
    private final String IN_TASK_COMMENTS = "TaskComments";
    private final String IN_TASK_COMMENTS_STATUS_ID = "TaskStatusID";
    private final String IN_TASK_COMMENTS_STATUS = "TaskStatus";
    private final String IN_TASK_COMMENTS_USER_ID = "UserId";
    private final String IN_TASK_COMMENTS_POST_ID = "PostId";
    private final String IN_TASK_COMMENTS_LOCATION_CODE = "LocationCode";
    private final String IN_TASK_COMMENTS_DEPARTMENT_ID = "DepartmentID";
    private final String IN_TASK_COMMENTS_DISPLAY_DATE = "DisplayDate";
    private final String IN_TASK_COMMENTS_EMP_POST_ID = "EmpPostId";
    private final String IN_TASK_COMMENTS_EMP_NAME = "EmpName";
    private final String IN_TASK_COMMENTS_POST_NAME = "PostName";
    private final String IN_TASK_COMMENTS_DESIGNATION_NAME = "DesignationName";
    private final String IN_TASK_COMMENTS_DEPARTMENT_NAME = "DepartmentName";
    private final String IN_TASK_COMMENTS_PHONE_NO = "PhoneNo";
    private final String IN_TASK_COMMENTS_EMP_EMAIL = "EmpEmail";
    private final String IN_TASK_COMMENTS_IMAGE_PATH = "ImagePath";
    private final String IN_TASK_COMMENTS_TASK_ID = "TaskId";
    /*OutTask All Comments table creation*/
    private final String CREATE_IN_TASK_COMMENTS_TABLE =
            " create table " + IN_TASK_COMMENTS_TABLE + " ( "
                    + IN_TASK_COMMENTS_SNO + " integer primary key autoincrement , "
                    + IN_TASK_COMMENTS_ID + " TEXT , "
                    + IN_TASK_COMMENTS + " TEXT , "
                    + IN_TASK_COMMENTS_STATUS_ID + " TEXT , "
                    + IN_TASK_COMMENTS_STATUS + " TEXT , "
                    + IN_TASK_COMMENTS_USER_ID + " TEXT , "
                    + IN_TASK_COMMENTS_POST_ID + " TEXT , "
                    + IN_TASK_COMMENTS_LOCATION_CODE + " TEXT , "
                    + IN_TASK_COMMENTS_DEPARTMENT_ID + " TEXT , "
                    + IN_TASK_COMMENTS_DISPLAY_DATE + " TEXT , "
                    + IN_TASK_COMMENTS_EMP_POST_ID + " TEXT , "
                    + IN_TASK_COMMENTS_EMP_NAME + " TEXT , "
                    + IN_TASK_COMMENTS_POST_NAME + " TEXT , "
                    + IN_TASK_COMMENTS_DESIGNATION_NAME + " TEXT , "
                    + IN_TASK_COMMENTS_DEPARTMENT_NAME + " TEXT , "
                    + IN_TASK_COMMENTS_PHONE_NO + " TEXT , "
                    + IN_TASK_COMMENTS_EMP_EMAIL + " TEXT , "
                    + IN_TASK_COMMENTS_IMAGE_PATH + " TEXT , "
                    + IN_TASK_COMMENTS_TASK_ID + " TEXT ) ";
    private final String OUT_TASK_COMMENTS_SNO = "OutTaskCommentsSno";
    private final String OUT_TASK_COMMENTS_ID = "OutTaskCommentID";
    private final String OUT_TASK_COMMENTS = "OutTaskComments";
    private final String OUT_TASK_COMMENTS_STATUS_ID = "OutTaskStatusID";
    private final String OUT_TASK_COMMENTS_STATUS = "OutTaskStatus";
    private final String OUT_TASK_COMMENTS_USER_ID = "UserId";
    private final String OUT_TASK_COMMENTS_POST_ID = "PostId";
    private final String OUT_TASK_COMMENTS_LOCATION_CODE = "LocationCode";
    private final String OUT_TASK_COMMENTS_DEPARTMENT_ID = "DepartmentID";
    private final String OUT_TASK_COMMENTS_DISPLAY_DATE = "DisplayDate";
    private final String OUT_TASK_COMMENTS_EMP_POST_ID = "EmpPostId";
    private final String OUT_TASK_COMMENTS_EMP_NAME = "EmpName";
    private final String OUT_TASK_COMMENTS_POST_NAME = "PostName";
    private final String OUT_TASK_COMMENTS_DESIGNATION_NAME = "DesignationName";
    private final String OUT_TASK_COMMENTS_DEPARTMENT_NAME = "DepartmentName";
    private final String OUT_TASK_COMMENTS_PHONE_NO = "PhoneNo";
    private final String OUT_TASK_COMMENTS_EMP_EMAIL = "EmpEmail";
    private final String OUT_TASK_COMMENTS_IMAGE_PATH = "ImagePath";

    /*TaskLoadApplication*/
//    private final String TASK_LOAD_APPLICATIONS = "LoadApplications";
    private final String OUT_TASK_COMMENTS_TASK_ID = "TaskId";
    /*InTask All Comments table creation*/
    public final String CREATE_OUT_TASK_COMMENTS_TABLE =
            " create table " + OUT_TASK_COMMENTS_TABLE + " ( "
                    + OUT_TASK_COMMENTS_SNO + " integer primary key autoincrement , "
                    + OUT_TASK_COMMENTS_ID + " TEXT , "
                    + OUT_TASK_COMMENTS + " TEXT , "
                    + OUT_TASK_COMMENTS_STATUS_ID + " TEXT , "
                    + OUT_TASK_COMMENTS_STATUS + " TEXT , "
                    + OUT_TASK_COMMENTS_USER_ID + " TEXT , "
                    + OUT_TASK_COMMENTS_POST_ID + " TEXT , "
                    + OUT_TASK_COMMENTS_LOCATION_CODE + " TEXT , "
                    + OUT_TASK_COMMENTS_DEPARTMENT_ID + " TEXT , "
                    + OUT_TASK_COMMENTS_DISPLAY_DATE + " TEXT , "
                    + OUT_TASK_COMMENTS_EMP_POST_ID + " TEXT , "
                    + OUT_TASK_COMMENTS_EMP_NAME + " TEXT , "
                    + OUT_TASK_COMMENTS_POST_NAME + " TEXT , "
                    + OUT_TASK_COMMENTS_DESIGNATION_NAME + " TEXT , "
                    + OUT_TASK_COMMENTS_DEPARTMENT_NAME + " TEXT , "
                    + OUT_TASK_COMMENTS_PHONE_NO + " TEXT , "
                    + OUT_TASK_COMMENTS_EMP_EMAIL + " TEXT , "
                    + OUT_TASK_COMMENTS_IMAGE_PATH + " TEXT , "
                    + OUT_TASK_COMMENTS_TASK_ID + " TEXT ) ";
    private final String DEPLOYMENT_SNO = "Sno";
    private final String DEPLOYMENT_TASK_ID = "TaskId";
    private final String DEPLOYMENT_TASK_NAME = "TaskName";
    private final String DEPLOYMENT_TASK_EDIT = "TaskEdited";
    private final String DEPLOYMENT_JSON_DATA = "JSONData";
    private final String DEPLOYMENT_USER_ID = "UserId";
    private final String DEPLOYMENT_ORG_ID = "OrgId";
    private final String DEPLOYMENT_POST_ID = "PostId";
    private final String DEPLOYMENT_UPLOADED = "Uploaded";
    public final String CREATE_DEPLOYMENT_TABLE =
            " create table " + DEPLOYMENT_TABLE + " ( "
                    + DEPLOYMENT_SNO + " integer primary key autoincrement , "
                    + DEPLOYMENT_TASK_ID + " TEXT , "
                    + DEPLOYMENT_TASK_NAME + " TEXT , "
                    + DEPLOYMENT_TASK_EDIT + " TEXT , "
                    + DEPLOYMENT_JSON_DATA + " TEXT , "
                    + DEPLOYMENT_USER_ID + " TEXT , "
                    + DEPLOYMENT_ORG_ID + " TEXT , "
                    + DEPLOYMENT_POST_ID + " TEXT , "
                    + DEPLOYMENT_UPLOADED + " TEXT ) ";


    Context context;

    public ImproveDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    /*Create Query Data Table*/

    public static String replaceWithUnderscore(String str) {

        str = str.replace(" ", "_");

        return str;
    }

/*    public static boolean pointIsInCircle(PointF pointForCheck, PointF center,
                                          double radius) {
        if (getDistanceBetweenTwoPoints(pointForCheck, center) <= radius)
            return true;
        else
            return false;
    }

    public static double getDistanceBetweenTwoPoints(PointF p1, PointF p2) {
        double R = 6371000; // m
        double dLat = Math.toRadians(p2.x - p1.x);
        double dLon = Math.toRadians(p2.y - p1.y);
        double lat1 = Math.toRadians(p1.x);
        double lat2 = Math.toRadians(p2.x);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2)
                * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d;
    }*/

    public static boolean pointIsInCircle(PointF pointForCheck, PointF center,
                                          double radius) {
        return getDistanceBetweenTwoPoints(pointForCheck, center) <= radius;
    }

    public static double getDistanceBetweenTwoPoints(PointF p1, PointF p2) {
        double R = 6371000; // m
        double dLat = Math.toRadians(p2.x - p1.x);
        double dLon = Math.toRadians(p2.y - p1.y);
        double lat1 = Math.toRadians(p1.x);
        double lat2 = Math.toRadians(p2.x);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2)
                * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d;
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.setForeignKeyConstraintsEnabled (true);
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            db.execSQL(ORGANISATION_TABLE_CREATE);
            db.execSQL(CREATE_DATA_CONTROL_TABLE);
            db.execSQL(APPS_LIST_TABLE_CREATE);
            db.execSQL(APPS_LIST_TABLE_CREATE_IN_TASK);
            db.execSQL(CREATE_CallAPI_Request_TABLE);
            db.execSQL(CREATE_SUBMIT_TABLE);
            db.execSQL(CREATE_QUERY_DATA_TABLE);
            db.execSQL(CREATE_API_NAMES_TABLE);
            db.execSQL(APP_VERSION_TABLE_CREATE);
            db.execSQL(CREATE_E_LEARNING_TABLE);
            db.execSQL(CREATE_E_LEARNING_TIME_SPENT_OFFLINE_TABLE);
            db.execSQL(CREATE_IN_TASK_TABLE);
            db.execSQL(CREATE_OUT_TASK_TABLE);
            db.execSQL(CREATE_IN_TASK_DETAILS_TABLE);
            db.execSQL(CREATE_NOTIFICATION_TABLE);
            db.execSQL(CREATE_ALL_NOTIFICATIONS_TABLE);
            db.execSQL(CREATE_GROUP_TABLE);
            db.execSQL(CREATE_COMMENTS_FAILED_TABLE);
            db.execSQL(CREATE_IN_TASK_COMMENTS_TABLE);
            db.execSQL(CREATE_OUT_TASK_COMMENTS_TABLE);
            db.execSQL(CREATE_DEPLOYMENT_TABLE);
            db.execSQL(CREATE_COMMENTS_OFF_LINE_TABLE);
            db.execSQL(CREATE_COMMENTS_OFF_LINE_TABLE_OUT_TASK);
            db.execSQL(CreateTableString(RealmTables.SaveRequestTable.TABLE_NAME_,
                    RealmTables.SaveRequestTable.cols));
            System.gc();

        } catch (Exception e) {
            System.gc();
            Log.d("SQLiteDatabaseTableEx", String.valueOf(e));
        }

    }

    /*Create Data Control Table*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

            db.execSQL("DROP TABLE IF EXISTS " + ORGANISATION_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DATA_CONTROL_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + APPS_LIST_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + APPS_LIST_TABLE_IN_TASK);
            db.execSQL("DROP TABLE IF EXISTS " + FORM_SUBMIT_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + API_NAMES_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + APP_VERSION_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + E_LEARNING_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + E_LEARNING_TIME_SPENT_OFFLINE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + IN_TASK_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + OUT_TASK_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + IN_TASK_DETAILS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + NOTIFICATION_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + ALL_NOTIFICATIONS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + GROUP_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_CallAPI_Request_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + COMMENTS_FAILED_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + IN_TASK_COMMENTS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + OUT_TASK_COMMENTS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DEPLOYMENT_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + SEND_COMMENTS_OFFLINE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + OUT_TASK_SEND_COMMENTS_OFFLINE_TABLE);

            // Create tables again
            onCreate(db);
        } catch (Exception e) {
            System.gc();
            Log.d("SQLiteDatabaseTableEx", String.valueOf(e));
        }
    }

    /* Insert into Organisation Table*/
    public void insertIntoAppVersionTable(String userId, String orgId, String appName, String apkOldVersion, String apkNewVersion) {
        Log.d("insert", APP_VERSION_TABLE_CREATE + " before insert");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        /*Org table Data*/
//        for (AppDetails appDetails : appDetailsList) {
        values.put(APP_VERSION_USER_ID, userId);
        values.put(APP_VERSION_ORG_ID, orgId);
        values.put(APP_VERSION_APP_NAME, appName);
        values.put(APP_VERSION_OLD, apkOldVersion);
        values.put(APP_VERSION_NEW, apkNewVersion);

        long results = db.insert(APP_VERSION_TABLE, null, values);
        Log.d("APK_VERSION_CREATE", APP_VERSION_TABLE + " After insert " + userId + "," + orgId + ","
                + "," + appName + "," + apkOldVersion + "," + apkNewVersion +
                " Records Inserted Count " + results);
//        }

        db.close();

    }

    /* Insert into InTask Table*/
    public void insertIntoInTaskComments(List<CommentsInfoModel> dataModelList, String inTaskOrgId, String inTaskUserId, String intaskPostId, String taskid) {
        Log.d("insert", CREATE_IN_TASK_COMMENTS_TABLE + " before insert");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (CommentsInfoModel model : dataModelList) {

            values.put(IN_TASK_COMMENTS_ID, model.getTaskCommentID());
            values.put(IN_TASK_COMMENTS, model.getTaskComments());
            values.put(IN_TASK_COMMENTS_STATUS_ID, model.getTaskStatusID());
            values.put(IN_TASK_COMMENTS_STATUS, model.getTaskStatus());
            values.put(IN_TASK_COMMENTS_USER_ID, model.getUserId());
            values.put(IN_TASK_COMMENTS_POST_ID, model.getPostId());
            values.put(IN_TASK_COMMENTS_LOCATION_CODE, model.getLocationCode());
            values.put(IN_TASK_COMMENTS_DEPARTMENT_ID, model.getDepartmentID());
            values.put(IN_TASK_COMMENTS_DISPLAY_DATE, model.getDisplayDate());
            values.put(IN_TASK_COMMENTS_EMP_POST_ID, model.getPostId());
            values.put(IN_TASK_COMMENTS_EMP_NAME, model.getEmpName());
            values.put(IN_TASK_COMMENTS_POST_NAME, model.getPostName());
            values.put(IN_TASK_COMMENTS_DESIGNATION_NAME, model.getDesignationName());
            values.put(IN_TASK_COMMENTS_DEPARTMENT_NAME, model.getDepartmentName());
            values.put(IN_TASK_COMMENTS_PHONE_NO, model.getPhoneNo());
            values.put(IN_TASK_COMMENTS_EMP_EMAIL, model.getEmpEmail());
            values.put(IN_TASK_COMMENTS_IMAGE_PATH, model.getImagePath());
            values.put(IN_TASK_COMMENTS_TASK_ID, taskid);

            long results = db.insert(IN_TASK_COMMENTS_TABLE, null, values);
            Log.d("CREATE_IN_TASK_TABLE", IN_TASK_COMMENTS_TABLE + " After insert " + model.getDepartmentName() + "," + model.getTaskComments() + " Records Inserted Count " + results);
        }

        db.close();

    }

    /* Insert into InTask Table*/
    public void insertIntoOutTaskComments(List<CommentsInfoModel> dataModelList, String inTaskOrgId, String inTaskUserId, String intaskPostId, String taskid) {
        Log.d("insert", CREATE_OUT_TASK_COMMENTS_TABLE + " before insert");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (CommentsInfoModel model : dataModelList) {

            values.put(OUT_TASK_COMMENTS_ID, model.getTaskCommentID());
            values.put(OUT_TASK_COMMENTS, model.getTaskComments());
            values.put(OUT_TASK_COMMENTS_STATUS_ID, model.getTaskStatusID());
            values.put(OUT_TASK_COMMENTS_STATUS, model.getTaskStatus());
            values.put(OUT_TASK_COMMENTS_USER_ID, model.getUserId());
            values.put(OUT_TASK_COMMENTS_POST_ID, model.getPostId());
            values.put(OUT_TASK_COMMENTS_LOCATION_CODE, model.getLocationCode());
            values.put(OUT_TASK_COMMENTS_DEPARTMENT_ID, model.getDepartmentID());
            values.put(OUT_TASK_COMMENTS_DISPLAY_DATE, model.getDisplayDate());
            values.put(OUT_TASK_COMMENTS_EMP_POST_ID, model.getPostId());
            values.put(OUT_TASK_COMMENTS_EMP_NAME, model.getEmpName());
            values.put(OUT_TASK_COMMENTS_POST_NAME, model.getPostName());
            values.put(OUT_TASK_COMMENTS_DESIGNATION_NAME, model.getDesignationName());
            values.put(OUT_TASK_COMMENTS_DEPARTMENT_NAME, model.getDepartmentName());
            values.put(OUT_TASK_COMMENTS_PHONE_NO, model.getPhoneNo());
            values.put(OUT_TASK_COMMENTS_EMP_EMAIL, model.getEmpEmail());
            values.put(OUT_TASK_COMMENTS_IMAGE_PATH, model.getImagePath());
            values.put(OUT_TASK_COMMENTS_TASK_ID, taskid);

            long results = db.insert(OUT_TASK_COMMENTS_TABLE, null, values);
            Log.d("CREATE_OUT_TASK_TABLE", OUT_TASK_COMMENTS_TABLE + " After insert " + model.getDepartmentName() + "," + model.getTaskComments() + " Records Inserted Count " + results);
        }

        db.close();

    }

    /*Insert Into Deployment Table*/
    public void insertIntoDeploymentTable(String strTaskId, String strTaskName, String strEditTask,
                                          String strJsonData, String strUserId, String strOrgId, String strPostId, String uploadedStatus) {
        Log.d("insert", CREATE_DEPLOYMENT_TABLE + " before insert");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DEPLOYMENT_TASK_ID, strTaskId);
        values.put(DEPLOYMENT_TASK_NAME, strTaskName);
        values.put(DEPLOYMENT_TASK_EDIT, strEditTask);
        values.put(DEPLOYMENT_JSON_DATA, strJsonData);
        values.put(DEPLOYMENT_USER_ID, strUserId);
        values.put(DEPLOYMENT_ORG_ID, strOrgId);
        values.put(DEPLOYMENT_POST_ID, strPostId);
        values.put(DEPLOYMENT_UPLOADED, uploadedStatus);

        long results = db.insert(DEPLOYMENT_TABLE, null, values);
        Log.d("CREATE_IN", DEPLOYMENT_TABLE + " After insert " + strTaskId + "," + strTaskName + " Records Inserted Count " + results);


        db.close();

    }

    /* Insert into InTask Table*/
    public void insertIntoInTaskTable(List<InTaskDataModel> dataModelList, String inTaskOrgId, String inTaskUserId) {
        Log.d("insert", CREATE_IN_TASK_TABLE + " before insert");
        // 1. get reference to writable DB

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (InTaskDataModel model : dataModelList) {

            values.put(IN_TASK_SNO, model.getSNO());
            values.put(IN_TASK_TASK_ID, model.getTaskID());
            values.put(IN_TASK_ORG_ID, model.getOrgId());
            values.put(IN_TASK_USER_ID, model.getUserId());
            values.put(IN_TASK_POST_ID, model.getPostID());
            values.put(IN_TASK_TASK_NAME, model.getTaskName());
            values.put(IN_TASK_TASK_DESC, model.getTaskDesc());
            values.put(IN_TASK_PRIORITY_ID, model.getPriorityId());
            values.put(IN_TASK_PRIORITY, model.getPriority());
            values.put(IN_TASK_IS_SINGLE_USER, model.getIsSingleUser());
            values.put(IN_TASK_SINGLE_USER_STATUS, model.getSingleUserStatus());
            values.put(IN_TASK_START_DATE, model.getStartDate());
            values.put(IN_TASK_END_DATE, model.getEndDate());
            values.put(IN_TASK_START_DISPLAY_DATE, model.getStartDisplayDate());
            values.put(IN_TASK_END_DISPLAY_DATE, model.getEndDisplayDate());
            values.put(IN_TASK_STATUS_ID, model.getTaskStatusId());
            values.put(IN_TASK_TASK_STATUS, model.getTaskStatus());
            values.put(IN_TASK_TOTAL_ASSIGNED, model.getTotalAssigned());
            values.put(IN_TASK_TOTAL_IN_PROGRESS, model.getTotalInprogress());
            values.put(IN_TASK_TOTAL_COMPLETED, model.getTotalCompleted());
            values.put(IN_TASK_APP_INFO, model.getAppInfo());
            values.put(IN_TASK_FILES_INFO, model.getFilesInfo());
            values.put(IN_TASK_COMMENTS_INFO, model.getCommentsInfo());
            values.put(IN_TASK_SINGLE_USER_INFO, model.getSingleUserInfo());
            values.put(IN_TASK_DISTRIBUTION_STATUS, model.getDistributionStatus());
            values.put(IN_TASK_DISTRIBUTION_DATE, model.getDistributionDate());
            values.put(IN_TASK_DISTRIBUTION_DISPLAY_DATE, model.getDistributionDisplayDate());
            values.put(IN_TASK_CREATED_BY, model.getCreatedBy());
            values.put(IN_TASK_DISTRIBUTION_ID, model.getDistrubutionID());
            values.put(IN_TASK_UPDATION_DATE, model.getTaskUpdationDate());

            long results = db.insert(IN_TASK_TABLE, null, values);
            Log.d("CREATE_IN_TASK_TABLE", IN_TASK_TABLE + " After insert " + model.getTaskID() + "," + model.getTaskName() + " Records Inserted Count " + results);
        }

        db.close();

    }

    /* Insert into OutTask Table*/
    public void insertIntoOutTaskTable(List<OutTaskDataModel> dataModelList, String outTaskOrgId, String outTaskUserId) {
        Log.d("insert", CREATE_OUT_TASK_TABLE + " before insert");
        // 1. get reference to writable DB

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (OutTaskDataModel model : dataModelList) {

            values.put(OUT_TASK_SNO, model.getSNO());
            values.put(OUT_TASK_TASK_ID, model.getTaskID());
            values.put(OUT_TASK_ORG_ID, model.getOrgId());
            values.put(OUT_TASK_USER_ID, model.getUserId());
            values.put(OUT_TASK_POST_ID, model.getPostID());
            values.put(OUT_TASK_TASK_NAME, model.getTaskName());
            values.put(OUT_TASK_TASK_DESC, model.getTaskDesc());
            values.put(OUT_TASK_PRIORITY_ID, model.getPriorityId());
            values.put(OUT_TASK_PRIORITY, model.getPriority());
            values.put(OUT_TASK_TASK_STATUS, model.getTaskStatus());
            values.put(OUT_TASK_IS_SINGLE_USER, model.getIsSingleUser());
            values.put(OUT_TASK_SINGLE_USER_STATUS, model.getSingleUserStatus());
            values.put(OUT_TASK_START_DATE, model.getStartDate());
            values.put(OUT_TASK_END_DATE, model.getEndDate());
            values.put(OUT_TASK_DISTRIBUTION_DATE, model.getDistributionDate());
            values.put(OUT_TASK_START_DISPLAY_DATE, model.getStartDisplayDate());
            values.put(OUT_TASK_END_DISPLAY_DATE, model.getEndDisplayDate());
            values.put(OUT_TASK_STATUS, model.getStatus());
            values.put(OUT_TASK_CREATED_BY, model.getCreatedBy());
            values.put(OUT_TASK_TOTAL_ASSIGNED, model.getTotalAssigned());
            values.put(OUT_TASK_TOTAL_IN_PROGRESS, model.getTotalInprogress());
            values.put(OUT_TASK_TOTAL_COMPLETED, model.getTotalCompleted());
            values.put(OUT_TASK_APP_INFO, model.getAppInfo());
            values.put(OUT_TASK_FILES_INFO, model.getFilesInfo());
            values.put(OUT_TASK_GROUP_INFO, model.getGroupInfo());
            values.put(OUT_TASK_EMP_INFO, model.getEmpInfo());
            values.put(OUT_TASK_COMMENTS_INFO, model.getCommentsInfo());
            values.put(OUT_TASK_DISTRIBUTION_STATUS, model.getDistributionStatus());
            values.put(OUT_TASK_DISTRIBUTION_DISPLAY_DATE, model.getDistributionDisplayDate());
            values.put(OUT_TASK_UPDATION_DATE, model.getTaskUpdationDate());

            long results = db.insert(OUT_TASK_TABLE, null, values);
            Log.d("CREATE_OUT_TASK_TABLE", OUT_TASK_TABLE + " After insert " + model.getTaskID() + "," + model.getTaskName() + " Records Inserted Count " + results);
        }

        db.close();

    }

    /* Insert into OutTask Table*/
    public void insertIntoInTaskDetailsTable(GetTasksInvDtsResponse invDtsResponse, String strTaskId,
                                             String strOrgId, String UserId) {
        Gson gson = new Gson();
        String strGsonApps = gson.toJson(invDtsResponse.getTaskAppData());
        String strGsonComments = gson.toJson(invDtsResponse.getTaskCmtData());
        String strGsonContent = gson.toJson(invDtsResponse.getTaskContantData());
        String strGsonTaskData = gson.toJson(invDtsResponse.getTaskData());
        Log.d("insert", CREATE_IN_TASK_DETAILS_TABLE + " before insert");
        // 1. get reference to writable DB

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IN_TASK_ID, strTaskId);
        values.put(IN_TASK_DETAILS_APPS, strGsonApps);
        values.put(IN_TASK_DETAILS_COMMENTS, strGsonComments);
        values.put(IN_TASK_DETAILS_CONTENT, strGsonContent);
        values.put(IN_TASK_DETAILS_DATA, strGsonTaskData);
        values.put(DB_ORG_ID, strOrgId);
        values.put(USER_ID, UserId);

        long results = db.insert(IN_TASK_DETAILS_TABLE, null, values);
        Log.d("CREATE_OUT_TASK_TABLE", IN_TASK_DETAILS_TABLE + " After insert " + results + " - " + invDtsResponse.getStatus());

        db.close();

    }

    /* Retrieve  data from InTask */
    public GetTasksInvDtsResponse getDataFromInTaskDetailsTable(String strTaskId, String org_id, String user_id) {

        GetTasksInvDtsResponse model = new GetTasksInvDtsResponse();
        String query = "select * from " + IN_TASK_DETAILS_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "' and " + IN_TASK_ID + " = '" + strTaskId + "'  and " + USER_ID + " = '" + user_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    Gson gson = new Gson();

//    String strApps = ;
//    String strComments = cursor.getString(2);
//    String strContent = cursor.getString(3);
//    String strData = cursor.getString(4);

                    Type typeApps = new TypeToken<List<TaskAppDataModelAC>>() {
                    }.getType();
                    List<TaskAppDataModelAC> modelApps = gson.fromJson(cursor.getString(1), typeApps);
                    model.setTaskAppData(modelApps);

//                Gson gsonComments = new Gson();
                    Type typeComments = new TypeToken<List<TaskCmtDataModel>>() {
                    }.getType();
                    List<TaskCmtDataModel> modelComments = gson.fromJson(cursor.getString(2), typeComments);
                    model.setTaskCmtData(modelComments);

//                Gson gsonContent = new Gson();
                    Type typeContent = new TypeToken<List<TaskContentDataModelAC>>() {
                    }.getType();
                    List<TaskContentDataModelAC> modelContent = gson.fromJson(cursor.getString(3), typeContent);
                    model.setTaskContantData(modelContent);

//                Gson gsonTaskData = new Gson();
                    Type typeData = new TypeToken<List<TaskDataModelAC>>() {
                    }.getType();
                    List<TaskDataModelAC> modelData = gson.fromJson(cursor.getString(4), typeData);
                    model.setTaskData(modelData);

                } catch (Exception e) {
                    Log.d(TAG, "getDataFromInTaskDetailsTable: " + e);
                }
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", model.toString());

        return model;

    }

    /* get Data from APP VERSION Table*/
    public List<CommentsInfoModel> getDataFromInTaskComments(String user_id, String postId, String strTaskId) {
        List<CommentsInfoModel> modelList = new ArrayList<CommentsInfoModel>();

        String query = "select * from " + IN_TASK_COMMENTS_TABLE + " WHERE " +
                IN_TASK_COMMENTS_TASK_ID + " = '" + strTaskId + "' and " + IN_TASK_COMMENTS_USER_ID + " = '" + user_id + "' and " + IN_TASK_COMMENTS_POST_ID + " = '" + postId + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                CommentsInfoModel model = new CommentsInfoModel();
                model.setTaskCommentID(cursor.getString(1));
                model.setTaskComments(cursor.getString(2));
                model.setTaskStatusID(cursor.getString(3));
                model.setTaskStatus(cursor.getString(4));
                model.setUserId(cursor.getString(5));
                model.setPostId(cursor.getString(6));
                model.setLocationCode(cursor.getString(7));
                model.setDepartmentID(cursor.getString(8));
                model.setDisplayDate(cursor.getString(9));
                model.setPostId(cursor.getString(10));
                model.setEmpName(cursor.getString(11));
                model.setPostName(cursor.getString(12));
                model.setDesignationName(cursor.getString(13));
                model.setDepartmentName(cursor.getString(14));
                model.setPhoneNo(cursor.getString(15));
                model.setEmpEmail(cursor.getString(16));
                model.setImagePath(cursor.getString(17));
                model.setTaskId(cursor.getString(18));

                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppVersionList", modelList.toString());

        return modelList;

    }

    /* get Data from OUTTask Comments Table*/
    public List<CommentsInfoModel> getDataFromOutTaskComments(String user_id, String postId, String strTaskId) {
        List<CommentsInfoModel> modelList = new ArrayList<CommentsInfoModel>();

        String query = "select * from " + OUT_TASK_COMMENTS_TABLE + " WHERE " +
                OUT_TASK_COMMENTS_TASK_ID + " = '" + strTaskId +
                "' and " + OUT_TASK_COMMENTS_USER_ID + " = '" + user_id +
                "' and " + OUT_TASK_COMMENTS_STATUS + " = '" + "None" +
                "' and " + OUT_TASK_COMMENTS_POST_ID + " = '" + postId + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                CommentsInfoModel model = new CommentsInfoModel();
                model.setTaskCommentID(cursor.getString(1));
                model.setTaskComments(cursor.getString(2));
                model.setTaskStatusID(cursor.getString(3));
                model.setTaskStatus(cursor.getString(4));
                model.setUserId(cursor.getString(5));
                model.setPostId(cursor.getString(6));
                model.setLocationCode(cursor.getString(7));
                model.setDepartmentID(cursor.getString(8));
                model.setDisplayDate(cursor.getString(9));
                model.setPostId(cursor.getString(10));
                model.setEmpName(cursor.getString(11));
                model.setPostName(cursor.getString(12));
                model.setDesignationName(cursor.getString(13));
                model.setDepartmentName(cursor.getString(14));
                model.setPhoneNo(cursor.getString(15));
                model.setEmpEmail(cursor.getString(16));
                model.setImagePath(cursor.getString(17));
                model.setTaskId(cursor.getString(18));

                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppVersionList", modelList.toString());

        return modelList;

    }

    /* get Data from APP VERSION Table*/
    public List<AppVersionModel> getDataAppVersionTable(String strOrgId, String user_id, String appName) {
        List<AppVersionModel> modelList = new ArrayList<AppVersionModel>();

        String query = "select * from " + APP_VERSION_TABLE + " WHERE " + APP_VERSION_ORG_ID + "= '" + strOrgId + "' and " + APP_VERSION_USER_ID + " = '" + user_id + "' and " + APP_VERSION_APP_NAME + " = '" + appName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AppVersionModel model = new AppVersionModel();
                model.setStrUserId(cursor.getString(1));
                model.setStrOrgId(cursor.getString(2));
                model.setStrAppName(cursor.getString(3));
                model.setStrOldVersion(cursor.getString(4));
                model.setStrNewVersion(cursor.getString(5));

                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppVersionList", modelList.toString());

        return modelList;

    }

    /* get Data from APP VERSION Table*/
    public List<String> getDataFromDeployment(String strOrgId, String user_id, String strPostId) {

        List<String> strJsonDataList = new ArrayList<>();
        String strJsonData;
        String query = "select " + DEPLOYMENT_JSON_DATA + " from " + DEPLOYMENT_TABLE + " WHERE "
                + DEPLOYMENT_ORG_ID + " = '" + strOrgId + "' and "
                + DEPLOYMENT_USER_ID + " = '" + user_id + "' and "
                + DEPLOYMENT_POST_ID + " = '" + strPostId + "' ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                if (cursor != null) {
                    strJsonData = cursor.getString(0);
                    strJsonDataList.add(strJsonData);
                }

            } while (cursor.moveToNext());
        }

        Log.d("RetrieveDeploymentList", strJsonDataList.toString());

        return strJsonDataList;

    }

    /* get Data from IN TASK Table*/
    public List<InTaskDataModel> getDataFromInTaskTable(String strOrgId, String user_id, String taskStatus_id, String strPostId) {
        List<InTaskDataModel> modelList = new ArrayList<InTaskDataModel>();
        String query = null;


//        String query = "select * from " + IN_TASK_TABLE + " WHERE " + IN_TASK_ORG_ID + " = '" + strOrgId + "' and " + IN_TASK_USER_ID + " = '" + user_id + "' and " +
//                IN_TASK_POST_ID + " LIKE '%" + strPostId + "%' or " + IN_TASK_POST_ID + " = '' or " + IN_TASK_POST_ID + " LIKE '%0$0%'";

        if (taskStatus_id.equalsIgnoreCase("0")) {
            query = "select * from " + IN_TASK_TABLE + " WHERE " + IN_TASK_ORG_ID + " = '" + strOrgId + "' and " + IN_TASK_USER_ID + " = '" + user_id + "' and " +
                    IN_TASK_POST_ID + " LIKE '%" + strPostId + "%' or " + IN_TASK_POST_ID + " = '' or " + IN_TASK_POST_ID + " LIKE '%0$0%' ";

//            query = "select * from " + IN_TASK_TABLE + " WHERE " + IN_TASK_ORG_ID + " = '" + strOrgId + "' and " + IN_TASK_USER_ID + " = '" + user_id + "'";
        } else {
//            query = "select * from " + IN_TASK_TABLE + " WHERE " + IN_TASK_ORG_ID + " = '" + strOrgId + "' and " + IN_TASK_USER_ID + " = '" + user_id + "' and " + IN_TASK_STATUS_ID + " = '" + taskStatus_id + "'";
            query = "select * from " + IN_TASK_TABLE + " WHERE " + IN_TASK_ORG_ID + " = '" + strOrgId + "' and " + IN_TASK_USER_ID + " = '" + user_id + "' and " + IN_TASK_STATUS_ID + " = '" + taskStatus_id + "' and " +
                    IN_TASK_POST_ID + " LIKE '%" + strPostId + "%' or " + IN_TASK_POST_ID + " = '' or " + IN_TASK_POST_ID + " LIKE '%0$0%' ";
//            query = "select * from " + IN_TASK_TABLE + " WHERE " + IN_TASK_ORG_ID + " = '" + strOrgId + "' and " + IN_TASK_USER_ID + " = '" + user_id + "' and " +
//                    IN_TASK_POST_ID + " LIKE '%" + strPostId + "%' or " + IN_TASK_POST_ID + " = '' or " + IN_TASK_POST_ID + " LIKE '%0$0%'" + " and " + IN_TASK_STATUS_ID + " = '" + taskStatus_id + "'";

        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                InTaskDataModel model = new InTaskDataModel();
                model.setSNO(cursor.getString(0));
                model.setTaskID(cursor.getString(1));
                model.setOrgId(cursor.getString(2));
                model.setUserId(cursor.getString(3));
                model.setPostID(cursor.getString(4));
                model.setTaskName(cursor.getString(5));
                model.setTaskDesc(cursor.getString(6));
                model.setPriorityId(cursor.getString(7));
                model.setPriority(cursor.getString(8));
                model.setIsSingleUser(cursor.getString(9));
                model.setSingleUserStatus(cursor.getString(10));
                model.setStartDate(cursor.getString(11));
                model.setEndDate(cursor.getString(12));
                model.setStartDisplayDate(cursor.getString(13));
                model.setEndDisplayDate(cursor.getString(14));
                model.setTaskStatus(cursor.getString(15));
                model.setTaskStatusId(cursor.getString(16));
                model.setTotalAssigned(cursor.getString(17));
                model.setTotalInprogress(cursor.getString(18));
                model.setTotalCompleted(cursor.getString(19));
                model.setAppInfo(cursor.getString(20));
                model.setFilesInfo(cursor.getString(21));
                model.setCommentsInfo(cursor.getString(22));
                model.setSingleUserInfo(cursor.getString(23));
                model.setDistributionStatus(cursor.getString(24));
                model.setDistributionDate(cursor.getString(25));
                model.setDistributionDisplayDate(cursor.getString(26));
                model.setCreatedBy(cursor.getString(27));

                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveInTaskList", modelList.toString());

        return modelList;

    }

    /* get Data from OUT TASK Table*/
//    public List<OutTaskDataModel> getDataFromOutTaskTable(String strOrgId, String user_id, String taskStatus_id) {
    public List<OutTaskDataModel> getDataFromOutTaskTable(String strOrgId, String user_id, String strPostId) {
        List<OutTaskDataModel> modelList = new ArrayList<OutTaskDataModel>();
//        String query;

//        if (taskStatus_id.equalsIgnoreCase("0")) {
//        query = "select * from " + OUT_TASK_TABLE + " WHERE " + OUT_TASK_ORG_ID + " = '" + strOrgId + "' and " + OUT_TASK_USER_ID + " = '" + user_id + "'";
//        query = "select * from " + OUT_TASK_TABLE + " WHERE " + OUT_TASK_ORG_ID + " = '" + strOrgId + "' and " + OUT_TASK_USER_ID + " = '" + user_id + "'";
        String query = "select * from " + OUT_TASK_TABLE + " WHERE " + OUT_TASK_ORG_ID + " = '" + strOrgId + "' and " + OUT_TASK_USER_ID + " = '" + user_id + "' and " +
                OUT_TASK_POST_ID + " LIKE '%" + strPostId + "%' or " + OUT_TASK_POST_ID + " = '' or " + OUT_TASK_POST_ID + " LIKE '%0$0%'";
//        } else {
//            query = "select * from " + OUT_TASK_TABLE + " WHERE " + OUT_TASK_ORG_ID + " = '" + strOrgId + "' and " + OUT_TASK_USER_ID + " = '" + user_id + "' and " + OUT_TASK_STATUS_ID + " = '" + taskStatus_id + "'";
//        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                OutTaskDataModel model = new OutTaskDataModel();
                model.setSNO(cursor.getString(0));
                model.setTaskID(cursor.getString(1));
                model.setOrgId(cursor.getString(2));
                model.setUserId(cursor.getString(3));
                model.setPostID(cursor.getString(4));
                model.setTaskName(cursor.getString(5));
                model.setTaskDesc(cursor.getString(6));
                model.setPriorityId(cursor.getString(7));
                model.setPriority(cursor.getString(8));
                model.setTaskStatus(cursor.getString(9));
                model.setIsSingleUser(cursor.getString(10));
                model.setSingleUserStatus(cursor.getString(11));
                model.setStartDate(cursor.getString(12));
                model.setEndDate(cursor.getString(13));
                model.setDistributionDate(cursor.getString(14));
                model.setStartDisplayDate(cursor.getString(15));
                model.setEndDisplayDate(cursor.getString(16));
                model.setStatus(cursor.getString(17));
                model.setCreatedBy(cursor.getString(18));
                model.setTotalAssigned(cursor.getString(19));
                model.setTotalInprogress(cursor.getString(20));
                model.setTotalCompleted(cursor.getString(21));
                model.setAppInfo(cursor.getString(22));
                model.setFilesInfo(cursor.getString(23));
                model.setGroupInfo(cursor.getString(24));
                model.setEmpInfo(cursor.getString(25));
                model.setCommentsInfo(cursor.getString(26));
                model.setDistributionStatus(cursor.getString(27));
                model.setDistributionDisplayDate(cursor.getString(28));

                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveOutTaskList", modelList.toString());

        return modelList;

    }
    /*Get data from Organisation Table*/

    /* Insert into Organisation Table*/
    public long insertIntOrganisationsTable(List<OrgList> orgLists, String phone_no) {
        Log.d("insert", ORGANISATION_TABLE_CREATE + " before insert");
        // 1. get reference to writable DB
        long results = 0;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (OrgList orgList : orgLists) {
            values.put(DB_ORG_ID, orgList.getOrgID());
            values.put(DB_ORG_NAME, orgList.getOrgName());
            values.put(USER_ID, phone_no);
            results = db.insert(ORGANISATION_TABLE, null, values);
            Log.d("ORG_TABLE_CREATE", ORGANISATION_TABLE + " After insert " + orgList.getOrgID() + "," + orgList.getOrgName() +
                    " Records Inserted Count " + results);
        }

        db.close();
        return results;
    }

    /* Insert into Api Names Table*/
    public void insertIntoAPINamesTable(List<APIDetails> apiDetailsList, String orgId, String user_id) {
        Log.d("insert", API_NAMES_TABLE + " before insert");
        // 1. get reference to writable DB

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (APIDetails apiDetails : apiDetailsList) {
            values.put(DB_ORG_ID, orgId);
            values.put(CREATED_USER_ID, apiDetails.getCreatedUserID());
            String[] splitServiceName = apiDetails.getServiceName().split("\\^");
            String strServiceName = splitServiceName[0];
            values.put(SERVICE_NAME, strServiceName);
            values.put(SERVICE_DESC, apiDetails.getServiceDesc());
            values.put(ACCESSIBILITY, apiDetails.getAccessibility());
            values.put(SERVICE_SOURCE, apiDetails.getServiceSource());
            values.put(SERVICE_TYPE, apiDetails.getServiceType());
            values.put(SERVICE_CALL_AT, apiDetails.getServiceCallsAt());
            values.put(SERVICE_RESULT, apiDetails.getServiceResult());
            values.put(SERVICE_URL, apiDetails.getServiceURl());
            values.put(SERVICE_MASK, apiDetails.getServiceURl_Mask());
            values.put(INPUT_PARAMETERS, apiDetails.getInputParameters());
            values.put(OUTPUT_PARAMETERS, apiDetails.getOutputParameters());
            values.put(OUTPUT_TYPE, apiDetails.getOutputType());
            values.put(API_NAME_VERSION, apiDetails.getVersion());
            values.put(XML_PATH, apiDetails.getXMLPath());
            values.put(METHOD_NAME, apiDetails.getMethodName());
            values.put(XML_FORMAT, apiDetails.getXMLFormat());
            values.put(NAME_SPACE, apiDetails.getNameSpace());
            values.put(METHOD_TYPE, apiDetails.getMethodType());
            values.put(USER_ID, user_id);
            long results = db.insert(API_NAMES_TABLE, null, values);
            Log.d("API_NAMES_TABLE", API_NAMES_TABLE + " After insert Records Inserted Count " + results);
        }

        db.close();

    }

    public List<OrgList> getDataFromOrganisationTable(String user_id) {
        List<OrgList> modelList = new ArrayList<OrgList>();
        String query = "select * from " + ORGANISATION_TABLE + " where " + USER_ID + " = '" + user_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                OrgList model = new OrgList();
                model.setOrgID(cursor.getString(1));
                model.setOrgName(cursor.getString(2));

                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveOrgList", modelList.toString());

        return modelList;

    }

    /* get Data from API Names  Table*/
    public List<APIDetails> getDataFromAPINames(String strOrgId, String user_id) {
        List<APIDetails> modelList = new ArrayList<APIDetails>();

        String query = "select * from " + API_NAMES_TABLE + " WHERE " + DB_ORG_ID + "= '" + strOrgId + "' and " + USER_ID + " = '" + user_id + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                APIDetails model = new APIDetails();
                model.setServiceName(cursor.getString(3));
                model.setVersion(cursor.getString(15));

                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveOrgList", modelList.toString());

        return modelList;

    }

    /* get Data From API Names Refresh  */
    public APIDetails getDataFromAPINamesRefresh(String strServiceName, String strOrgId, String user_id) {
        APIDetails model = new APIDetails();

        String query = "select * from " + API_NAMES_TABLE + " WHERE " + DB_ORG_ID + "= '" + strOrgId + "' and " + USER_ID + " = '" + user_id + "' and " + SERVICE_NAME + " = '" + strServiceName + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                model.setServiceName(cursor.getString(3));
                model.setVersion(cursor.getString(15));


            } while (cursor.moveToNext());
        }


        return model;

    }

    public void deleteOrganisationData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ORGANISATION_TABLE, "", null);
        db.close();
    }

    public void deleteAPINamesData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(API_NAMES_TABLE, "", null);
        db.close();
    }

    public void deleteDataControlListData(String org_id, String controlName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATA_CONTROL_TABLE, DB_ORG_ID + " = '" + org_id + "' and " + CONTROL_NAME + " = " + controlName, null);
        db.close();
    }

    /* Insert into District Table*/
    public void insertIntoDataControlTable(DataControls dataControls, String orgID, String user_id) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DB_ORG_ID, orgID);
        values.put(DEPENDENT_CONTROL, dataControls.getDependentControl());
        values.put(DATA_CONTROL_TYPE, dataControls.getDataControlType());
        values.put(CREATED_USER_ID, dataControls.getCreatedUserID());
        values.put(CONTROL_NAME, dataControls.getControlName());
        values.put(CONTROL_STATUS, dataControls.getControlStatus());
        values.put(ACCESSIBILTY, dataControls.getAccessibility());
        values.put(VERSION, dataControls.getVersion());
        values.put(LOC_TYPE, dataControls.getLoc_type());
        values.put(TEXT_FILE_PATH, dataControls.getTextFilePath());
        values.put(USER_ID, user_id);

        long results = db.insert(DATA_CONTROL_TABLE, null, values);

        db.close();

    }

    /* Insert into District Table*/
    public void insertIntoNewDataControlTable(DataControlsAndApis.DataControlDetails dataControls, String orgID, String user_id) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DB_ORG_ID, orgID);
        values.put(DEPENDENT_CONTROL, dataControls.getDependentControlName());
        values.put(DATA_CONTROL_TYPE, dataControls.getAccessingType());
        values.put(CREATED_USER_ID, "");
        values.put(CONTROL_NAME, dataControls.getDataControlName());
        values.put(CONTROL_STATUS, dataControls.getStatus());
        values.put(ACCESSIBILTY, dataControls.getAccessingType());
        values.put(VERSION, dataControls.getVersion());
        values.put(LOC_TYPE, "");
        values.put(TEXT_FILE_PATH, dataControls.getTextFilePath());
        values.put(USER_ID, user_id);

        long results = db.insert(DATA_CONTROL_TABLE, null, values);

        db.close();

    }

    public void deleteAppsListData(String org_id, String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(APPS_LIST_TABLE, DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = " + "'" + user_id + "'", null);
        db.close();
    }

    public void deleteAppData(String org_id, String appName, String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(APPS_LIST_TABLE, DB_ORG_ID + " = '" + org_id + "' and " + APP_NAME + " = '" + appName + "' and " + USER_ID + " = " + user_id, null);
        db.close();
    }

    /* Insert into Apps Table*/
    public void insertIntoAppsListTable(List<AppDetails> appDetailsList, String orgID, String user_id) {
        Log.d("insert", APPS_LIST_TABLE_CREATE + " before insert");
        // 1. get reference to writable DB

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (AppDetails appDetails : appDetailsList) {

            if (appDetails.getVisibileIn() == null || appDetails.getVisibileIn().equalsIgnoreCase("")) {
                appDetails.setVisibileIn("Both");
            }

            if (appDetails.getVisibileIn().equalsIgnoreCase("Both") || appDetails.getVisibileIn().equalsIgnoreCase("Mobile")) {
                values.put(DB_ORG_ID, orgID);
                values.put(DISTRIBUTION_ID, appDetails.getDistrubutionID());
                values.put(CREATED_BY, appDetails.getCreatedBy());
                values.put(CREATED_USER_NAME, appDetails.getCreatedUserName());
                values.put(DESCRIPTION, appDetails.getDescription());
                values.put(ISACTIVE, appDetails.getIsActive());
                values.put(APP_NAME, appDetails.getAppName());
                values.put(APP_TYPE, appDetails.getAppType());
                values.put(APP_VERSION, appDetails.getAppVersion());
                values.put(DESIGN_FORMAT, appDetails.getDesignFormat());
                values.put(VIDEO_LINK, appDetails.getVideoLink());
                values.put(NEWROW, appDetails.getNewRow());
                values.put(APP_ICON, appDetails.getAppIcon());
                values.put(APK_VERSION, appDetails.getApkVersion());
                values.put(APPLICATION_RECEIVED_DATE, appDetails.getApplicationReceivedDate());
                values.put(XML_FILE_PATH, appDetails.getXMLFilePath());
                values.put(APP_MODE, appDetails.getAppMode());
                values.put(USER_ID, user_id);
                values.put(POST_ID, appDetails.getPostID());
                values.put(DISPLAY_AS, appDetails.getDisplayAs());
                values.put(DISPLAY_REPORT_AS, appDetails.getDisplayReportas());
                values.put(USER_LOCATION, appDetails.getUserLocation());
                values.put(Traning, appDetails.getTraining());
                values.put(Testing, appDetails.getTesting());
                values.put(VISIBLE_IN, appDetails.getVisibileIn());
                values.put(WORKSPACEAS, appDetails.getWorkspaceAs());
                values.put(WORKSPACEName, "");

                values.put(DISPLAYAPPNAME, appDetails.getDisplayAppName());
                Log.d(TAG, "getDisplayAppName: " + appDetails.getDisplayAppName());
                //nk
                if (appDetails.getTableName() != null) {
                    values.put(TABLE_NAME, replaceWithUnderscore(appDetails.getTableName()));
                }
                values.put(TABLE_COLUMNS, appDetails.getTableColumns());
                values.put(PRIMARY_KEYS, appDetails.getPrimaryKey());
                Gson gson = new Gson();
                String foreignKeyDetails = gson.toJson(appDetails.getForeignKey());
                values.put(FOREIGN_KEYS, foreignKeyDetails);
                values.put(COMPOSITE_KEYS, appDetails.getCompositeKey());
                String subformDetails = gson.toJson(appDetails.getSubFormDetails());
                values.put(SUBFORM_DETAILS, subformDetails);
                values.put(DISPLAY_IN_APP_LIST, String.valueOf(appDetails.isDisplayInAppList()));
                values.put(USER_TYPE_IDS, appDetails.getUserTypeID());
                values.put(IS_DATA_PERMISSION, appDetails.getIsDataPermission());
                values.put(DATA_PERMISSION_XML, appDetails.getDataPermissionXML());
                values.put(DATA_PERMISSION_XML_FILEPATH, appDetails.getDataPermissionXmlFilePath());
                values.put(IS_CONTROL_VISIBILTY, appDetails.getIsControlVisibility());
                values.put(CONTROL_VISIBILTY_XML, appDetails.getControlVisibilityXML());
                values.put(CONTROL_VISIBILTY_XML_FILEPATH, appDetails.getControlVisibilityXmlFilePath());
                values.put(SHARING_VERSION, appDetails.getSharingVersion());
                values.put(DISPLAY_ICON, appDetails.getDisplayIcon());

//            db.delete(APPS_LIST_TABLE, APP_NAME + " = '" + appDetails.getAppName() + "'  ", null);
                long results = db.insert(APPS_LIST_TABLE, null, values);

//            long results = db.insert(E_LEARNING_TIME_SPENT_OFFLINE_TABLE, null, values);
                Log.d(TAG, "InsertingValuesIntoAppsList: " + results + "-" + appDetails.getDisplayAs() + " - " + appDetails.getAppName());

                if (appDetails.getAppType() != null
                        && appDetails.getAppType().equalsIgnoreCase("WorkSpace")) {
//                    if (appDetails.getWorkspaceAs().equalsIgnoreCase("Individual")) {
                    List<AppDetails.WorkSpaceAppsList> WorkSpaceAppsList = appDetails.getWorkSpaceAppsList();
                    for (int i = 0; i < WorkSpaceAppsList.size(); i++) {
                        AppDetails.WorkSpaceAppsList workSpaceApps = WorkSpaceAppsList.get(i);
                        ContentValues values1 = new ContentValues();
                        if (workSpaceApps.getVisibileIn() == null) {
                            workSpaceApps.setVisibileIn("Both");
                        }
                        if (workSpaceApps.getVisibileIn().equalsIgnoreCase("Both") || workSpaceApps.getVisibileIn().equalsIgnoreCase("Mobile")) {
                            values1.put(DB_ORG_ID, orgID);
                            values1.put(DISTRIBUTION_ID, appDetails.getDistrubutionID());
                            values1.put(CREATED_BY, workSpaceApps.getCreatedBy());
                            values1.put(CREATED_USER_NAME, workSpaceApps.getCreatedUserName());
                            values1.put(DESCRIPTION, workSpaceApps.getDescription());
                            values1.put(ISACTIVE, workSpaceApps.getIsActive());
                            values1.put(APP_NAME, workSpaceApps.getAppName());
                            values1.put(APP_TYPE, workSpaceApps.getAppType());
                            values1.put(APP_VERSION, workSpaceApps.getAppVersion());
                            values1.put(DESIGN_FORMAT, workSpaceApps.getDesignFormat());
                            values1.put(VIDEO_LINK, workSpaceApps.getVideoLink());
                            values1.put(NEWROW, workSpaceApps.getNewRow());
                            values1.put(APP_ICON, workSpaceApps.getAppIcon());
                            values1.put(APK_VERSION, workSpaceApps.getApkVersion());
                            values1.put(APPLICATION_RECEIVED_DATE, workSpaceApps.getApplicationReceivedDate());
                            values1.put(XML_FILE_PATH, workSpaceApps.getXMLFilePath());
                            values1.put(APP_MODE, workSpaceApps.getAppMode());
                            values1.put(USER_ID, user_id);
                            values1.put(POST_ID, appDetails.getPostID());
                            values1.put(DISPLAY_AS, appDetails.getDisplayAs());
                            values1.put(DISPLAY_REPORT_AS, appDetails.getDisplayReportas());
                            values1.put(USER_LOCATION, workSpaceApps.getUserLocation());
                            values1.put(Traning, workSpaceApps.getTraining());
                            values1.put(Testing, workSpaceApps.getTesting());
                            values1.put(VISIBLE_IN, workSpaceApps.getVisibileIn());
                            values1.put(WORKSPACEName, appDetails.getAppName());
                            values1.put(WORKSPACEAS, appDetails.getWorkspaceAs());
                            if (workSpaceApps.getDisplayAppName() != null && !workSpaceApps.getDisplayAppName().isEmpty()) {
                                values1.put(DISPLAYAPPNAME, workSpaceApps.getDisplayAppName());
                            } else {
                                values1.put(DISPLAYAPPNAME, appDetails.getDisplayAppName());
                            }
                            //nk
                            if (workSpaceApps.getTableName() != null) {
                                values1.put(TABLE_NAME, replaceWithUnderscore(workSpaceApps.getTableName()));
                            }
                            values1.put(TABLE_COLUMNS, workSpaceApps.getTableColumns());
                            values1.put(PRIMARY_KEYS, workSpaceApps.getPrimaryKey());
                            String foreignKeyDetails_workspace = gson.toJson(workSpaceApps.getForeignKey());
                            values1.put(FOREIGN_KEYS, foreignKeyDetails_workspace);
                            values1.put(COMPOSITE_KEYS, workSpaceApps.getCompositeKey());
                            String subformDetails_workspace = gson.toJson(workSpaceApps.getSubFormDetails());
                            values1.put(SUBFORM_DETAILS, subformDetails_workspace);
                            String AutoIncrementControlNames_workspace = gson.toJson(workSpaceApps.getAutoIncrementControlNames());
                            values1.put(AUTOINCREMENT_CONTROLNAMES, AutoIncrementControlNames_workspace);
                            values1.put(DISPLAY_IN_APP_LIST, "true");
                            values1.put(USER_TYPE_IDS, appDetails.getUserTypeID());
                            values1.put(IS_DATA_PERMISSION, workSpaceApps.getIsDataPermission());
                            values1.put(DATA_PERMISSION_XML, workSpaceApps.getDataPermissionXML());
                            values1.put(DATA_PERMISSION_XML_FILEPATH, workSpaceApps.getDataPermissionXmlFilePath());
                            values1.put(IS_CONTROL_VISIBILTY, workSpaceApps.getIsControlVisibility());
                            values1.put(CONTROL_VISIBILTY_XML, workSpaceApps.getControlVisibilityXML());
                            values1.put(CONTROL_VISIBILTY_XML_FILEPATH, workSpaceApps.getControlVisibilityXmlFilePath());
                            values1.put(SHARING_VERSION, workSpaceApps.getSharingVersion());
                            values1.put(DISPLAY_ICON, appDetails.getDisplayIcon());
//nk
                            long results1 = db.insert(APPS_LIST_TABLE, null, values1);

                            Log.d(TAG, "InsertingValuesIntoAppsList: " + results1 + "- " + appDetails.getPostID() + "-" + workSpaceApps.getDisplayAs() + " - " + workSpaceApps.getAppName());
                        }


                    }

//                    }
                   /* else if(appDetails.getWorkspaceAs().equalsIgnoreCase("Workspace")){

                    }*/
                }


            }
        }

        db.close();

    }


    /*Insert into Intask Appslist*/
    public void insertIntoInTaskAppsListTable(List<AppDetails> appDetailsList, String orgID, String user_id) {
        Log.d("insert", APPS_LIST_TABLE_CREATE_IN_TASK + " before insert");
        // 1. get reference to writable DB

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (AppDetails appDetails : appDetailsList) {
            values.put(DB_ORG_ID, orgID);
            values.put(DISTRIBUTION_ID, appDetails.getDistrubutionID());
            values.put(CREATED_BY, appDetails.getCreatedBy());
            values.put(CREATED_USER_NAME, appDetails.getCreatedUserName());
            values.put(DESCRIPTION, appDetails.getDescription());
            values.put(ISACTIVE, appDetails.getIsActive());
            values.put(APP_NAME, appDetails.getAppName());
            values.put(APP_TYPE, appDetails.getAppType());
            values.put(APP_VERSION, appDetails.getAppVersion());
            values.put(DESIGN_FORMAT, appDetails.getDesignFormat());
            values.put(VIDEO_LINK, appDetails.getVideoLink());
            values.put(NEWROW, appDetails.getNewRow());
            values.put(APP_ICON, appDetails.getAppIcon());
            values.put(APK_VERSION, appDetails.getApkVersion());
            values.put(APPLICATION_RECEIVED_DATE, appDetails.getApplicationReceivedDate());
            values.put(XML_FILE_PATH, appDetails.getXMLFilePath());
            values.put(APP_MODE, appDetails.getAppMode());
            values.put(USER_ID, user_id);
            values.put(POST_ID, appDetails.getPostID());
//            db.delete(APPS_LIST_TABLE, APP_NAME + " = '" + appDetails.getAppName() + "'  ", null);
            long results = db.insert(APPS_LIST_TABLE_IN_TASK, null, values);
//            long results = db.insert(E_LEARNING_TIME_SPENT_OFFLINE_TABLE, null, values);
            Log.d(TAG, "InsertingValuesIntoInTaskAppsList: " + results + "-" + appDetails.getAppName() + " - " + appDetails.getPostID());
        }

        db.close();

    }

    /* Insert into E-Learning Files Time Spent Table*/
    public void insertIntoELearningFilesTimeSpentOffLine(List<InsertUserFileVisitsModel> modelList, String offLineStatus) {
        Log.d("insert", E_LEARNING_TIME_SPENT_OFFLINE_TABLE + " before insert");
        // 1. get reference to writable DB

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (InsertUserFileVisitsModel appDetails : modelList) {
            values.put(E_LEARNING_TIME_SPENT_OFFLINE_USER_ID, appDetails.getUserID());
            values.put(E_LEARNING_TIME_SPENT_OFFLINE_DB_NAME, appDetails.getDBNAME());
            values.put(E_LEARNING_TIME_SPENT_OFFLINE_PARENT_ID, appDetails.getParent_Id());
            values.put(E_LEARNING_TIME_SPENT_OFFLINE_SELECTED_NODE_ID, appDetails.getSelected_Node_Id());
            values.put(E_LEARNING_TIME_SPENT_OFFLINE_CATEGORY_FILE_ID, appDetails.getCategoryFileID());
            values.put(E_LEARNING_TIME_SPENT_OFFLINE_DISTRIBUTION_ID, appDetails.getDistributionId());
            values.put(E_LEARNING_TIME_SPENT_OFFLINE_DEVICE_ID, appDetails.getDeviceID());
            values.put(E_LEARNING_TIME_SPENT_OFFLINE_MOBILE_DATE, appDetails.getMobileDate());
            values.put(E_LEARNING_TIME_SPENT_OFFLINE_START_TIME, appDetails.getStartTime());
            values.put(E_LEARNING_TIME_SPENT_OFFLINE_END_TIME, appDetails.getEndTime());
            values.put(E_LEARNING_TIME_SPENT_OFFLINE_GPS, appDetails.getGPS());
            values.put(E_LEARNING_TIME_SPENT_OFFLINE_VISITED_THROUGH, appDetails.getIs_Visited_Through());
            values.put(E_LEARNING_TIME_SPENT_OFFLINE_STATUS, "NotUploaded");
//            db.insert(E_LEARNING_TIME_SPENT_OFFLINE_TABLE, null, values);
//            db.delete(E_LEARNING_TIME_SPENT_OFFLINE_TABLE, E_LEARNING_TIME_SPENT_OFFLINE_DISTRIBUTION_ID + " = '" + appDetails.getDistributionId() + "'  ", null);
            long results = db.insert(E_LEARNING_TIME_SPENT_OFFLINE_TABLE, null, values);
            Log.d(TAG, "FilesTimeSpentOffLine: " + results + "-" + appDetails.getCategoryFileID());
//            String selectCheck = "select * from " + E_LEARNING_TIME_SPENT_OFFLINE_TABLE;
//            SQLiteDatabase db1 = this.getWritableDatabase();
//            Cursor cursor = db1.rawQuery(selectCheck, null);
//            if (cursor.moveToFirst()) {
//                Log.d(TAG, "insertIntoELearningFilesTimeSpentOffLine: " + cursor.getString(10));
//            }
        }

        db.close();

    }

    /* Insert into E-Learning Files Time Spent Table*/
    public void insertIntoFailedCommentsTable(List<TaskCommentDetails> modelList, String offLineStatus) {
        Log.d("insert", COMMENTS_FAILED_TABLE + " before insert");
        // 1. get reference to writable DB

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (TaskCommentDetails commentDetails : modelList) {
            values.put(COMMENTS_FAILED_TASK_ID, commentDetails.getTaskId());
            values.put(COMMENTS_FAILED_ORG_ID, commentDetails.getOrgId());
            values.put(COMMENTS_FAILED_TASK_COMMENTS, commentDetails.getTaskComments());
            values.put(COMMENTS_FAILED_TASK_STATUS_ID, commentDetails.getTaskStatusId());
            values.put(COMMENTS_FAILED_USER_ID, commentDetails.getUserId());
            values.put(COMMENTS_FAILED_POST_ID, commentDetails.getPostId());
            values.put(COMMENTS_FAILED_LOCATION_CODE, commentDetails.getLocationCode());
            values.put(COMMENTS_FAILED_DEPARTMENT_ID, commentDetails.getDepartmentId());
            values.put(COMMENTS_FAILED_DESIGNATION_ID, commentDetails.getDesignationId());
            values.put(COMMENTS_FAILED_IS_SELF_COMMENT, commentDetails.getIsSelfComment());
            values.put(COMMENTS_FAILED_DEVICE_ID, commentDetails.getDeviceId());
            values.put(COMMENTS_FAILED_VERSION_NO, commentDetails.getVersionNo());
            values.put(COMMENTS_FAILED_MOBILE_DATE, commentDetails.getMobileDate());
            values.put(COMMENTS_FAILED_OFF_LINE_STATUS, "NotUploaded");

            long results = db.insert(COMMENTS_FAILED_TABLE, null, values);
            Log.d(TAG, "FailedCommentsStatus: " + results + "-" + commentDetails.getTaskId());

        }

        db.close();

    }

    /* Insert into SendCommentsOffline Table*/
    public void insertIntoSendCommentOffline(List<TaskCommentDetails> modelList, String offLineStatus) {
        Log.d("insert", SEND_COMMENTS_OFFLINE_TABLE + " before insert");
        // 1. get reference to writable DB

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (TaskCommentDetails commentDetails : modelList) {

            values.put(SEND_COMMENTS_OFFLINE_ORG_ID, commentDetails.getOrgId());
            values.put(SEND_COMMENTS_OFFLINE_TASK_ID, commentDetails.getTaskId());
            values.put(SEND_COMMENTS_OFFLINE_COMMENTS, commentDetails.getTaskComments());
            values.put(SEND_COMMENTS_OFFLINE_TASK_STATUS_ID, commentDetails.getTaskStatusId());
            values.put(SEND_COMMENTS_OFFLINE_USER_ID, commentDetails.getUserId());
            values.put(SEND_COMMENTS_OFFLINE_POST_ID, commentDetails.getPostId());
            values.put(SEND_COMMENTS_OFFLINE_LOCATION_CODE, commentDetails.getLocationCode());
            values.put(SEND_COMMENTS_OFFLINE_DEPARTMENT_ID, commentDetails.getDepartmentId());
            values.put(SEND_COMMENTS_OFFLINE_DESIGNATION_ID, commentDetails.getDesignationId());
            values.put(SEND_COMMENTS_OFFLINE_IS_SELF_COMMENT, commentDetails.getIsSelfComment());
            values.put(SEND_COMMENTS_OFFLINE_DEVICE_ID, commentDetails.getDeviceId());
            values.put(SEND_COMMENTS_OFFLINE_VERSION_NO, commentDetails.getVersionNo());
            values.put(SEND_COMMENTS_OFFLINE_MOBILE_DATE, commentDetails.getMobileDate());

            long results = db.insert(SEND_COMMENTS_OFFLINE_TABLE, null, values);
            Log.d(TAG, "SendCommentsStatus: " + results + " - " + commentDetails.getTaskId());

        }

        db.close();

    }

    public void insertIntoOutTaskSendCommentOffline(List<TaskCommentDetails> modelList, String offLineStatus) {
        Log.d("insert", SEND_COMMENTS_OFFLINE_TABLE + " before insert");
        // 1. get reference to writable DB

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (TaskCommentDetails commentDetails : modelList) {

            values.put(SEND_COMMENTS_OFFLINE_ORG_ID, commentDetails.getOrgId());
            values.put(SEND_COMMENTS_OFFLINE_TASK_ID, commentDetails.getTaskId());
            values.put(SEND_COMMENTS_OFFLINE_COMMENTS, commentDetails.getTaskComments());
            values.put(SEND_COMMENTS_OFFLINE_TASK_STATUS_ID, commentDetails.getTaskStatusId());
            values.put(SEND_COMMENTS_OFFLINE_USER_ID, commentDetails.getUserId());
            values.put(SEND_COMMENTS_OFFLINE_POST_ID, commentDetails.getPostId());
            values.put(SEND_COMMENTS_OFFLINE_LOCATION_CODE, commentDetails.getLocationCode());
            values.put(SEND_COMMENTS_OFFLINE_DEPARTMENT_ID, commentDetails.getDepartmentId());
            values.put(SEND_COMMENTS_OFFLINE_DESIGNATION_ID, commentDetails.getDesignationId());
            values.put(SEND_COMMENTS_OFFLINE_IS_SELF_COMMENT, commentDetails.getIsSelfComment());
            values.put(SEND_COMMENTS_OFFLINE_DEVICE_ID, commentDetails.getDeviceId());
            values.put(SEND_COMMENTS_OFFLINE_VERSION_NO, commentDetails.getVersionNo());
            values.put(SEND_COMMENTS_OFFLINE_MOBILE_DATE, commentDetails.getMobileDate());

            long results = db.insert(SEND_COMMENTS_OFFLINE_TABLE, null, values);
            Log.d(TAG, "SendCommentsStatus: " + results + " - " + commentDetails.getTaskId());

        }

        db.close();

    }

    /* Insert into Apps Table*/
    public void insertAppDataIntoAppsListTable(AppDetails appDetails, String orgID, String user_id) {
        Log.d("insert", APPS_LIST_TABLE_CREATE + " before insert");
        // 1. get reference to writable DB

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DB_ORG_ID, orgID);
        values.put(DISTRIBUTION_ID, appDetails.getDistrubutionID());
        values.put(CREATED_BY, appDetails.getCreatedBy());
        values.put(CREATED_USER_NAME, appDetails.getCreatedUserName());
        values.put(DESCRIPTION, appDetails.getDescription());
        values.put(ISACTIVE, appDetails.getIsActive());
        values.put(APP_NAME, appDetails.getAppName());
        values.put(APP_VERSION, appDetails.getAppVersion());
        values.put(APP_TYPE, appDetails.getAppType());
        values.put(DESIGN_FORMAT, appDetails.getDesignFormat());
        values.put(VIDEO_LINK, appDetails.getVideoLink());
        values.put(NEWROW, appDetails.getNewRow());
        values.put(APP_ICON, appDetails.getAppIcon());
        values.put(APK_VERSION, appDetails.getAppVersion());
        values.put(APPLICATION_RECEIVED_DATE, appDetails.getApplicationReceivedDate());
        values.put(XML_FILE_PATH, appDetails.getXMLFilePath());
        values.put(APP_MODE, appDetails.getAppMode());
        values.put(USER_ID, user_id);
        long results = db.insert(APPS_LIST_TABLE, null, values);


        db.close();

    }

    /* Get Data from Send Comments Offline */
    public List<TaskCommentDetails> getDataFromSendCommentsOffLine(String org_id, String user_id, String strPostId) {
        List<TaskCommentDetails> modelList = new ArrayList<TaskCommentDetails>();

        String query = "select * from " + SEND_COMMENTS_OFFLINE_TABLE + " WHERE " + SEND_COMMENTS_OFFLINE_ORG_ID + " = '" + org_id + "' and " + SEND_COMMENTS_OFFLINE_USER_ID + " = '" + user_id + "' and " +
                SEND_COMMENTS_OFFLINE_POST_ID + " LIKE '%" + strPostId + "%' or " + SEND_COMMENTS_OFFLINE_POST_ID + " = '' or " + SEND_COMMENTS_OFFLINE_POST_ID + " LIKE '%0$0%' ";

        Log.d(TAG, "getDataFromSendCommentsOfflineTableQuery: " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                TaskCommentDetails model = new TaskCommentDetails();
                model.setOrgId(cursor.getString(1));
                model.setTaskId(cursor.getString(2));
                model.setTaskComments(cursor.getString(3));
                model.setTaskStatusId(cursor.getString(4));
                model.setUserId(cursor.getString(5));
                model.setPostId(cursor.getString(6));
                model.setLocationCode(cursor.getString(7));
                model.setDepartmentId(cursor.getString(8));
                model.setDesignationId(cursor.getString(9));
                model.setIsSelfComment(cursor.getString(10));
                model.setDeviceId(cursor.getString(11));
                model.setVersionNo(cursor.getString(12));
                model.setMobileDate(cursor.getString(13));

                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("CommentOfflineList", modelList.toString());

        return modelList;

    }

    /* Get Data from Send Comments Offline */
    public List<TaskCommentDetails> getDataFromOutTaskSendCommentsOffLine(String org_id, String user_id, String strPostId) {
        List<TaskCommentDetails> modelList = new ArrayList<TaskCommentDetails>();

        String query = "select * from " + OUT_TASK_SEND_COMMENTS_OFFLINE_TABLE + " WHERE " + OUT_TASK_SEND_COMMENTS_OFFLINE_ORG_ID + " = '" + org_id + "' and " + OUT_TASK_SEND_COMMENTS_OFFLINE_USER_ID + " = '" + user_id + "' and " +
                OUT_TASK_SEND_COMMENTS_OFFLINE_POST_ID + " LIKE '%" + strPostId + "%' or " + OUT_TASK_SEND_COMMENTS_OFFLINE_POST_ID + " = '' or " + SEND_COMMENTS_OFFLINE_POST_ID + " LIKE '%0$0%' ";

        Log.d(TAG, "OutTaskSendCommentsOfflineTableQuery: " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                TaskCommentDetails model = new TaskCommentDetails();
                model.setOrgId(cursor.getString(1));
                model.setTaskId(cursor.getString(2));
                model.setTaskComments(cursor.getString(3));
                model.setTaskStatusId(cursor.getString(4));
                model.setUserId(cursor.getString(5));
                model.setPostId(cursor.getString(6));
                model.setLocationCode(cursor.getString(7));
                model.setDepartmentId(cursor.getString(8));
                model.setDesignationId(cursor.getString(9));
                model.setIsSelfComment(cursor.getString(10));
                model.setDeviceId(cursor.getString(11));
                model.setVersionNo(cursor.getString(12));
                model.setMobileDate(cursor.getString(13));

                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d(TAG, "OutTaskCommentOfflineList" + modelList);

        return modelList;

    }

/*
    public List<AppDetails> getDataFromAppsListTable(String org_id, String user_id, String strPostId, String displayAs) {
        List<AppDetails> modelList = new ArrayList<AppDetails>();
        Gson gson = new Gson();
//        String query;
        //1.0 Query
//        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
        //3.0 Query
//        if (strPostId != null) {
        Log.d(TAG, "In_PostId: " + strPostId);
//        String query1 = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " +
//                POST_ID + " = '" + strPostId + "' or " + POST_ID + " LIKE '%" + strPostId + "%' or " + POST_ID + " = '' and "
//                + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";

        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DISPLAY_IN_APP_LIST + " = '" + true + "' AND " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " +
                DISPLAY_AS + " = '" + displayAs + "' and (" +
                POST_ID + " = '" + strPostId + "' or " + POST_ID + "= '0' or " +
                POST_ID + " LIKE '%" + strPostId + "%' or " +
                POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + ") and "
                + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " ==''  ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";

// present Working
        */
/*
         String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " +
                POST_ID + " LIKE '" + strPostId + "%' or " + POST_ID + " = '' and "
                + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
        *//*

     */
/* String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " + DISPLAY_AS + "= '" + displayAs + "' and " +
                POST_ID + " LIKE '%" + strPostId + "%' or " + POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + " and "
                + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";*//*

//        } else {
//            query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
//        }
        Log.d(TAG, "getDataFromAppsListTable: " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AppDetails model = new AppDetails();
                model.setDistrubutionID(cursor.getString(3));
                model.setCreatedBy(cursor.getString(4));
                model.setCreatedUserName(cursor.getString(5));
                model.setDescription(cursor.getString(6));
                model.setIsActive(cursor.getString(7));
                model.setAppName(cursor.getString(8));
//                model.setAppName(cursor.getString(31));
                model.setAppVersion(cursor.getString(9));
                model.setAppType(cursor.getString(10));
                model.setDesignFormat("");//cursor.getString(11));
                model.setVideoLink(cursor.getString(12));
                model.setNewRow(cursor.getString(13));
                model.setAppIcon(cursor.getString(14));
                model.setApkVersion(cursor.getString(15));
                model.setApplicationReceivedDate(cursor.getString(16));
                model.setXMLFilePath(cursor.getString(17));
                model.setAppMode(cursor.getString(20));
//                if (strPostId != null) {
                model.setPostID(cursor.getString(22));
                model.setDisplayAs(cursor.getString(23));
                model.setDisplayAppName(cursor.getString(31));
                //nk
                model.setTableName(cursor.getString(32));
                model.setTableColumns(cursor.getString(33));
                model.setPrimaryKey(cursor.getString(34));
                Type typeAppsForeignKey = new TypeToken<List<ForeignKey>>() {
                }.getType();
                List<ForeignKey> foreignKeyList = gson.fromJson(cursor.getString(35), typeAppsForeignKey);
                model.setForeignKey(foreignKeyList);
                model.setCompositeKey(cursor.getString(36));
                List<SubFormTableColumns> subFormTableColumnsList = new ArrayList<>();
                if (cursor.getString(37) != null) {
                    Type typeApps = new TypeToken<List<SubFormTableColumns>>() {
                    }.getType();
                    subFormTableColumnsList = gson.fromJson(cursor.getString(37), typeApps);
                    model.setSubFormDetails(subFormTableColumnsList);
                }

                model.setIsDataPermission(cursor.getString(39));
                model.setDataPermissionXML(cursor.getString(40));
                model.setDataPermissionXmlFilePath(cursor.getString(41));
                model.setIsControlVisibility(cursor.getString(42));
                model.setControlVisibilityXML(cursor.getString(43));
                model.setControlVisibilityXmlFilePath(cursor.getString(44));

//                }else{
//
//                }

                if (model.getAppType().equalsIgnoreCase(AppConstants.ML)) {
                    model.setTraining(cursor.getString(26));
                    modelList.add(model);
                    if (cursor.getString(27) != null && cursor.getString(27).equalsIgnoreCase("True")) {
                        AppDetails model1 = new AppDetails();
                        model1.setDistrubutionID(cursor.getString(3));
                        model1.setCreatedBy(cursor.getString(4));
                        model1.setCreatedUserName(cursor.getString(5));
                        model1.setDescription(cursor.getString(6));
                        model1.setIsActive(cursor.getString(7));
                        model1.setAppName(cursor.getString(8));
                        model1.setAppVersion(cursor.getString(9));
                        model1.setAppType(cursor.getString(10));
                        model1.setDesignFormat("");//cursor.getString(11));
                        model1.setVideoLink(cursor.getString(12));
                        model1.setNewRow(cursor.getString(13));
                        model1.setAppIcon(cursor.getString(14));
                        model1.setApkVersion(cursor.getString(15));
                        model1.setApplicationReceivedDate(cursor.getString(16));
                        model1.setXMLFilePath(cursor.getString(17));
                        model1.setAppMode(cursor.getString(20));
                        model1.setPostID(cursor.getString(22));
                        model1.setTesting(cursor.getString(27));
                        //nk
                        model1.setTableName(cursor.getString(32));
                        model1.setTableColumns(cursor.getString(33));
                        model1.setPrimaryKey(cursor.getString(34));
                        model1.setForeignKey(foreignKeyList);
                        model1.setCompositeKey(cursor.getString(36));
                        model.setSubFormDetails(subFormTableColumnsList);
                        modelList.add(model1);
                    }
                } else {
                    modelList.add(model);
                }


            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", modelList.toString());

        return modelList;

    }
*/

    /* Retrive  data from database */
    /* Retrive  data from getDataFromAppsListTable database */
    public List<AppDetails> getDataFromAppsListTable(String org_id, String user_id, String strUserTypedIds, String strPostId, String displayAs, String strUserTypeId) {
        List<AppDetails> modelList = new ArrayList<AppDetails>();
        if (strPostId != null || strUserTypeId != null) {
            Gson gson = new Gson();
            if (strPostId == null || !strPostId.contains("_")) {
                strPostId = "0";
            }
            if (strUserTypeId == null || strUserTypeId.contains("_")) {
                strUserTypeId = "0";
            }
            String query = "";
            if (strPostId != null && strPostId.equalsIgnoreCase("0")) {
                query = "select * from " + APPS_LIST_TABLE + " WHERE " + DISPLAY_AS + " = '" + displayAs + "' and " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' " +
                        "and (" + USER_TYPE_IDS + " != '0' and " + USER_TYPE_IDS + " = '" + strUserTypeId +
                        "' or " + USER_TYPE_IDS + " LIKE '%" + strUserTypeId + "%') and " + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " != '' ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
            } else if (strUserTypeId != null && strUserTypeId.equalsIgnoreCase("0")) {
                query = "select * from " + APPS_LIST_TABLE + " WHERE " + DISPLAY_AS + " = '" + displayAs + "' and " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' " +
                        "and ( " + POST_ID + " != '0' and " + POST_ID + " = '" + strPostId + "' or " + POST_ID + " LIKE '%" + strPostId + "%') and " + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " != '' ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";

            } else {
                query = "select * from " + APPS_LIST_TABLE + " WHERE " + DISPLAY_AS + " = '" + displayAs + "' and " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' " +
                        "and (( " + POST_ID + " != '0' and " + POST_ID + " = '" + strPostId + "' or " + POST_ID + " LIKE '%" + strPostId + "%') or (" + USER_TYPE_IDS + " != '0' and " + USER_TYPE_IDS + " = '" + strUserTypeId +
                        "' or " + USER_TYPE_IDS + " LIKE '%" + strUserTypeId + "%')) and " + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " != '' ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
            }

            Log.d(TAG, "getDataFromAppsListTable: " + query);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    AppDetails model = new AppDetails();
                    model.setDistrubutionID(cursor.getString(3));
                    model.setCreatedBy(cursor.getString(4));
                    model.setCreatedUserName(cursor.getString(5));
                    model.setDescription(cursor.getString(6));
                    model.setIsActive(cursor.getString(7));
                    model.setAppName(cursor.getString(8));
//                model.setAppName(cursor.getString(31));
                    model.setAppVersion(cursor.getString(9));
                    model.setAppType(cursor.getString(10));
                    model.setDesignFormat("");//cursor.getString(11));
                    model.setVideoLink(cursor.getString(12));
                    model.setNewRow(cursor.getString(13));
                    model.setAppIcon(cursor.getString(14));
                    model.setApkVersion(cursor.getString(15));
                    model.setApplicationReceivedDate(cursor.getString(16));
                    model.setXMLFilePath(cursor.getString(17));
                    model.setAppMode(cursor.getString(20));
//                if (strPostId != null) {
                    model.setPostID(cursor.getString(22));
                    model.setDisplayAs(cursor.getString(23));
                    model.setDisplayAppName(cursor.getString(31));
                    model.setDisplayIcon(cursor.getString(47));
                    //nk
                    model.setTableName(cursor.getString(32));
                    model.setTableColumns(cursor.getString(33));
                    model.setPrimaryKey(cursor.getString(34));

                    Type typeAppsForeignKey = new TypeToken<List<ForeignKey>>() {
                    }.getType();
                    List<ForeignKey> foreignKeyList = gson.fromJson(cursor.getString(35), typeAppsForeignKey);
                    model.setForeignKey(foreignKeyList);
                    model.setCompositeKey(cursor.getString(36));
                    List<SubFormTableColumns> subFormTableColumnsList = new ArrayList<>();
                    if (cursor.getString(37) != null) {
                        Type typeApps = new TypeToken<List<SubFormTableColumns>>() {
                        }.getType();
                        subFormTableColumnsList = gson.fromJson(cursor.getString(37), typeApps);
                        model.setSubFormDetails(subFormTableColumnsList);
                    }
                    model.setUserTypeID(cursor.getString(39));
                    model.setIsDataPermission(cursor.getString(40));
                    model.setDataPermissionXML(cursor.getString(41));
                    model.setDataPermissionXmlFilePath(cursor.getString(42));
                    model.setIsControlVisibility(cursor.getString(43));
                    model.setControlVisibilityXML(cursor.getString(44));
                    model.setControlVisibilityXmlFilePath(cursor.getString(45));
                    List<AutoIncrementControl> autoIncrementControlList = new ArrayList<>();
                    if (cursor.getString(48) != null) {
                        Type typeApps = new TypeToken<List<AutoIncrementControl>>() {
                        }.getType();
                        autoIncrementControlList = gson.fromJson(cursor.getString(48), typeApps);
                        model.setAutoIncrementControlNames(autoIncrementControlList);
                    }
                    if (model.getAppType().equalsIgnoreCase(AppConstants.ML)) {
                        model.setTraining(cursor.getString(26));
                        modelList.add(model);
                        if (cursor.getString(27) != null && cursor.getString(27).equalsIgnoreCase("True")) {
                            AppDetails model1 = new AppDetails();
                            model1.setDistrubutionID(cursor.getString(3));
                            model1.setCreatedBy(cursor.getString(4));
                            model1.setCreatedUserName(cursor.getString(5));
                            model1.setDescription(cursor.getString(6));
                            model1.setIsActive(cursor.getString(7));
                            model1.setAppName(cursor.getString(8));
                            model1.setAppVersion(cursor.getString(9));
                            model1.setAppType(cursor.getString(10));
                            model1.setDesignFormat("");//cursor.getString(11));
                            model1.setVideoLink(cursor.getString(12));
                            model1.setNewRow(cursor.getString(13));
                            model1.setAppIcon(cursor.getString(14));
                            model1.setApkVersion(cursor.getString(15));
                            model1.setApplicationReceivedDate(cursor.getString(16));
                            model1.setXMLFilePath(cursor.getString(17));
                            model1.setAppMode(cursor.getString(20));
                            model1.setPostID(cursor.getString(22));
                            model1.setTesting(cursor.getString(27));
                            //nk
                            model1.setTableName(cursor.getString(32));
                            model1.setTableColumns(cursor.getString(33));
                            model1.setPrimaryKey(cursor.getString(34));
                            model1.setForeignKey(foreignKeyList);
                            model1.setCompositeKey(cursor.getString(36));
                            model.setSubFormDetails(subFormTableColumnsList);
                            modelList.add(model1);
                        }
                    } else {
                        modelList.add(model);
                    }


                } while (cursor.moveToNext());
            }

            Log.d("RetrieveAppsList", modelList.toString());
        }
        return modelList;

    }

    public List<AppDetails> getDataFromAppsListTableOld(String org_id, String user_id, String strUserTypedIds, String strPostId, String displayAs, String strUserTypeId) {
        List<AppDetails> modelList = new ArrayList<AppDetails>();
        Gson gson = new Gson();
//        String queryCopy = "select * from " + APPS_LIST_TABLE + " WHERE " + DISPLAY_IN_APP_LIST + " = '" + true + "' AND " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " +
//                DISPLAY_AS + " = '" + displayAs + "' and (" +
//                POST_ID + " = '" + strPostId + "' or " + POST_ID + "= '0' or " +
//                POST_ID + " LIKE '%" + strPostId + "%' or " +
//                POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + ") and "
//                + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " ==''  ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
/*
        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DISPLAY_IN_APP_LIST + " = '" + true + "' AND " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " +
                DISPLAY_AS + " = '" + displayAs + "' and (" +
                POST_ID + " = '" + strPostId + "' or " + POST_ID + "= '0' or " +
                POST_ID + " LIKE '%" + strPostId + "%' or " +
                POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + ") and "
                + DISTRIBUTION_ID + " != '' and ("+ WORKSPACEName +" =='' OR " + WORKSPACEAS + " LIKE '%NA%' OR "+WORKSPACEAS+" is NULL)" +
                " and ("+WORKSPACEAS +" != 'Individual' or "+WORKSPACEName+" !='')  ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
*/
        String queryModify = "select * from " + APPS_LIST_TABLE + " WHERE " + DISPLAY_IN_APP_LIST + " = '" + true + "' AND " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " +
                DISPLAY_AS + " = '" + displayAs + "' and (" +
                POST_ID + " = '" + strPostId + "' or " + POST_ID + "= '0' or " +
                POST_ID + " LIKE '%" + strPostId + "%' or " +
                POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + ")" +
                " and (( " + WORKSPACEAS + " ='Workspace' and " + APP_TYPE + " = 'WorkSpace') or (" + WORKSPACEAS + " = 'Individual' and "
                + APP_TYPE + " != 'WorkSpace') or ( " + WORKSPACEAS + " = 'NA' and " + APP_TYPE + " != 'WorkSpace' )) ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' " +
                "and (( " + POST_ID + " != '0' and " + POST_ID + " = '" + strPostId + "' or " + POST_ID + " LIKE '%" + strPostId + "%') or (" + USER_TYPE_IDS + " != '0' and " + USER_TYPE_IDS + " = '" + strUserTypeId +
                "' or " + USER_TYPE_IDS + " LIKE '%" + strUserTypeId + "%')) and " + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " != '' ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";


        Log.d(TAG, "getDataFromAppsListTable: " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AppDetails model = new AppDetails();
                model.setDistrubutionID(cursor.getString(3));
                model.setCreatedBy(cursor.getString(4));
                model.setCreatedUserName(cursor.getString(5));
                model.setDescription(cursor.getString(6));
                model.setIsActive(cursor.getString(7));
                model.setAppName(cursor.getString(8));
//                model.setAppName(cursor.getString(31));
                model.setAppVersion(cursor.getString(9));
                model.setAppType(cursor.getString(10));
                model.setDesignFormat("");//cursor.getString(11));
                model.setVideoLink(cursor.getString(12));
                model.setNewRow(cursor.getString(13));
                model.setAppIcon(cursor.getString(14));
                model.setApkVersion(cursor.getString(15));
                model.setApplicationReceivedDate(cursor.getString(16));
                model.setXMLFilePath(cursor.getString(17));
                model.setAppMode(cursor.getString(20));
//                if (strPostId != null) {
                model.setPostID(cursor.getString(22));
                model.setDisplayAs(cursor.getString(23));
                model.setDisplayAppName(cursor.getString(31));
                //nk
                model.setTableName(cursor.getString(32));
                model.setTableColumns(cursor.getString(33));
                model.setPrimaryKey(cursor.getString(34));
                Type typeAppsForeignKey = new TypeToken<List<ForeignKey>>() {
                }.getType();
                List<ForeignKey> foreignKeyList = gson.fromJson(cursor.getString(35), typeAppsForeignKey);
                model.setForeignKey(foreignKeyList);
                model.setCompositeKey(cursor.getString(36));
                List<SubFormTableColumns> subFormTableColumnsList = new ArrayList<>();
                if (cursor.getString(37) != null) {
                    Type typeApps = new TypeToken<List<SubFormTableColumns>>() {
                    }.getType();
                    subFormTableColumnsList = gson.fromJson(cursor.getString(37), typeApps);
                    model.setSubFormDetails(subFormTableColumnsList);
                }
                model.setUserTypeID(cursor.getString(39));
                model.setIsDataPermission(cursor.getString(40));
                model.setDataPermissionXML(cursor.getString(41));
                model.setDataPermissionXmlFilePath(cursor.getString(42));
                model.setIsControlVisibility(cursor.getString(43));
                model.setControlVisibilityXML(cursor.getString(44));
                model.setControlVisibilityXmlFilePath(cursor.getString(45));
//                }else{
//
//                }

                if (model.getAppType().equalsIgnoreCase(AppConstants.ML)) {
                    model.setTraining(cursor.getString(26));
                    modelList.add(model);
                    if (cursor.getString(27) != null && cursor.getString(27).equalsIgnoreCase("True")) {
                        AppDetails model1 = new AppDetails();
                        model1.setDistrubutionID(cursor.getString(3));
                        model1.setCreatedBy(cursor.getString(4));
                        model1.setCreatedUserName(cursor.getString(5));
                        model1.setDescription(cursor.getString(6));
                        model1.setIsActive(cursor.getString(7));
                        model1.setAppName(cursor.getString(8));
                        model1.setAppVersion(cursor.getString(9));
                        model1.setAppType(cursor.getString(10));
                        model1.setDesignFormat("");//cursor.getString(11));
                        model1.setVideoLink(cursor.getString(12));
                        model1.setNewRow(cursor.getString(13));
                        model1.setAppIcon(cursor.getString(14));
                        model1.setApkVersion(cursor.getString(15));
                        model1.setApplicationReceivedDate(cursor.getString(16));
                        model1.setXMLFilePath(cursor.getString(17));
                        model1.setAppMode(cursor.getString(20));
                        model1.setPostID(cursor.getString(22));
                        model1.setTesting(cursor.getString(27));
                        //nk
                        model1.setTableName(cursor.getString(32));
                        model1.setTableColumns(cursor.getString(33));
                        model1.setPrimaryKey(cursor.getString(34));
                        model1.setForeignKey(foreignKeyList);
                        model1.setCompositeKey(cursor.getString(36));
                        model.setSubFormDetails(subFormTableColumnsList);
                        modelList.add(model1);
                    }
                } else {
                    modelList.add(model);
                }


            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", modelList.toString());

        return modelList;

    }

    public List<AppDetails> getDataFromAppsListTableWithFalseCase(String org_id, String user_id, String strPostId, String displayAs) {
        List<AppDetails> modelList = new ArrayList<AppDetails>();
        Gson gson = new Gson();
        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DISPLAY_IN_APP_LIST + " = '" + false + "' AND " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " +
                DISPLAY_AS + " = '" + displayAs + "' and (" +
                POST_ID + " = '" + strPostId + "' or " + POST_ID + "= '0' or " +
                POST_ID + " LIKE '%" + strPostId + "%' or " +
                POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + ") and "
                + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " ==''  ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";

        Log.d(TAG, "getDataFromAppsListTable: " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AppDetails model = new AppDetails();
                model.setDistrubutionID(cursor.getString(3));
                model.setCreatedBy(cursor.getString(4));
                model.setCreatedUserName(cursor.getString(5));
                model.setDescription(cursor.getString(6));
                model.setIsActive(cursor.getString(7));
                model.setAppName(cursor.getString(8));
//                model.setAppName(cursor.getString(31));
                model.setAppVersion(cursor.getString(9));
                model.setAppType(cursor.getString(10));
                model.setDesignFormat("");//cursor.getString(11));
                model.setVideoLink(cursor.getString(12));
                model.setNewRow(cursor.getString(13));
                model.setAppIcon(cursor.getString(14));
                model.setApkVersion(cursor.getString(15));
                model.setApplicationReceivedDate(cursor.getString(16));
                model.setXMLFilePath(cursor.getString(17));
                model.setAppMode(cursor.getString(20));
//                if (strPostId != null) {
                model.setPostID(cursor.getString(22));
                model.setDisplayAs(cursor.getString(23));
                model.setDisplayAppName(cursor.getString(31));
                //nk
                model.setTableName(cursor.getString(32));
                model.setTableColumns(cursor.getString(33));
                model.setPrimaryKey(cursor.getString(34));
                Type typeAppsForeignKey = new TypeToken<List<ForeignKey>>() {
                }.getType();
                List<ForeignKey> foreignKeyList = gson.fromJson(cursor.getString(35), typeAppsForeignKey);
                model.setForeignKey(foreignKeyList);
                model.setCompositeKey(cursor.getString(36));
                List<SubFormTableColumns> subFormTableColumnsList = new ArrayList<>();
                if (cursor.getString(37) != null) {
                    Type typeApps = new TypeToken<List<SubFormTableColumns>>() {
                    }.getType();
                    subFormTableColumnsList = gson.fromJson(cursor.getString(37), typeApps);
                    model.setSubFormDetails(subFormTableColumnsList);
                }

//                }else{
//
//                }

                if (model.getAppType().equalsIgnoreCase(AppConstants.ML)) {
                    model.setTraining(cursor.getString(26));
                    modelList.add(model);
                    if (cursor.getString(27) != null && cursor.getString(27).equalsIgnoreCase("True")) {
                        AppDetails model1 = new AppDetails();
                        model1.setDistrubutionID(cursor.getString(3));
                        model1.setCreatedBy(cursor.getString(4));
                        model1.setCreatedUserName(cursor.getString(5));
                        model1.setDescription(cursor.getString(6));
                        model1.setIsActive(cursor.getString(7));
                        model1.setAppName(cursor.getString(8));
                        model1.setAppVersion(cursor.getString(9));
                        model1.setAppType(cursor.getString(10));
                        model1.setDesignFormat("");//cursor.getString(11));
                        model1.setVideoLink(cursor.getString(12));
                        model1.setNewRow(cursor.getString(13));
                        model1.setAppIcon(cursor.getString(14));
                        model1.setApkVersion(cursor.getString(15));
                        model1.setApplicationReceivedDate(cursor.getString(16));
                        model1.setXMLFilePath(cursor.getString(17));
                        model1.setAppMode(cursor.getString(20));
                        model1.setPostID(cursor.getString(22));
                        model1.setTesting(cursor.getString(27));
                        //nk
                        model1.setTableName(cursor.getString(32));
                        model1.setTableColumns(cursor.getString(33));
                        model1.setPrimaryKey(cursor.getString(34));
                        model1.setForeignKey(foreignKeyList);
                        model1.setCompositeKey(cursor.getString(36));
                        model.setSubFormDetails(subFormTableColumnsList);
                        modelList.add(model1);
                    }
                } else {
                    modelList.add(model);
                }


            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", modelList.toString());

        return modelList;

    }

    public List<AppDetails> getDataFromAppsListTableWithinWorkspace(String org_id, String user_id, String strPostId, String displayAs, String WorkspaceName) {
        List<AppDetails> modelList = new ArrayList<AppDetails>();
//        String query;
        //1.0 Query
//        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
        //3.0 Query
//        if (strPostId != null) {
        Log.d(TAG, "In_PostId: " + strPostId);
        String query1 = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " +
                POST_ID + " = '" + strPostId + "' or " + POST_ID + " LIKE '%" + strPostId + "%' or " + POST_ID + " = '' and "
                + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";

        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " +
                DISPLAY_AS + " = '" + displayAs + "' and (" +
                POST_ID + " = '" + strPostId + "' or " + POST_ID + "= '0' or " +
                POST_ID + " LIKE '%" + strPostId + "%' or " +
                POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + ") and "
                + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " =='" + WorkspaceName + "'  ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";

// present Working
        /*
         String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " +
                POST_ID + " LIKE '" + strPostId + "%' or " + POST_ID + " = '' and "
                + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
        */
      /* String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " + DISPLAY_AS + "= '" + displayAs + "' and " +
                POST_ID + " LIKE '%" + strPostId + "%' or " + POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + " and "
                + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";*/
//        } else {
//            query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
//        }
        Log.d(TAG, "getDataFromAppsListTable: " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AppDetails model = new AppDetails();
                model.setDistrubutionID(cursor.getString(3));
                model.setCreatedBy(cursor.getString(4));
                model.setCreatedUserName(cursor.getString(5));
                model.setDescription(cursor.getString(6));
                model.setIsActive(cursor.getString(7));
                model.setAppName(cursor.getString(8));
                model.setAppVersion(cursor.getString(9));
                model.setAppType(cursor.getString(10));
                model.setDesignFormat("");//cursor.getString(11));
                model.setVideoLink(cursor.getString(12));
                model.setNewRow(cursor.getString(13));
                model.setAppIcon(cursor.getString(14));
                model.setApkVersion(cursor.getString(15));
                model.setApplicationReceivedDate(cursor.getString(16));
                model.setXMLFilePath(cursor.getString(17));
                model.setAppMode(cursor.getString(20));
//                if (strPostId != null) {
                model.setPostID(cursor.getString(22));
                model.setDisplayAs(cursor.getString(23));
                model.setDisplayAppName(cursor.getString(31));
                model.setIsDataPermission(cursor.getString(39));
                model.setDataPermissionXML(cursor.getString(40));
                model.setDataPermissionXmlFilePath(cursor.getString(41));
                model.setIsControlVisibility(cursor.getString(42));
                model.setControlVisibilityXML(cursor.getString(43));
                model.setControlVisibilityXmlFilePath(cursor.getString(44));
//                }else{
//
//                }

                if (model.getAppType().equalsIgnoreCase(AppConstants.ML)) {
                    model.setTraining(cursor.getString(26));
                    modelList.add(model);
                    if (cursor.getString(27) != null && cursor.getString(27).equalsIgnoreCase("True")) {
                        AppDetails model1 = new AppDetails();
                        model1.setDistrubutionID(cursor.getString(3));
                        model1.setCreatedBy(cursor.getString(4));
                        model1.setCreatedUserName(cursor.getString(5));
                        model1.setDescription(cursor.getString(6));
                        model1.setIsActive(cursor.getString(7));
                        model1.setAppName(cursor.getString(8));
                        model1.setAppVersion(cursor.getString(9));
                        model1.setAppType(cursor.getString(10));
                        model1.setDesignFormat("");//cursor.getString(11));
                        model1.setVideoLink(cursor.getString(12));
                        model1.setNewRow(cursor.getString(13));
                        model1.setAppIcon(cursor.getString(14));
                        model1.setApkVersion(cursor.getString(15));
                        model1.setApplicationReceivedDate(cursor.getString(16));
                        model1.setXMLFilePath(cursor.getString(17));
                        model1.setAppMode(cursor.getString(18));
                        model1.setPostID(cursor.getString(22));
                        model1.setTesting(cursor.getString(27));

                        modelList.add(model1);
                    }
                } else {
                    modelList.add(model);
                }


            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", modelList.toString());

        return modelList;

    }

    public List<AppDetails> getDataFromAppsListTableRefresh(String org_id, String user_id, String strPostId, String displayAs, String strUserTypeId) {
        List<AppDetails> modelList = new ArrayList<AppDetails>();

        /* Workspace Individual Scenario*/
        String queryFirst = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and "
                + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " != '' ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";

        /* Work Space Refresh*/
        String queryModify = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' " +
                "and ( " + POST_ID + " = '" + strPostId + "' or " + POST_ID + " LIKE '%" + strPostId + "%' or " + POST_ID + " = '' ) and " +
                " ( " + USER_TYPE_IDS + " = '0" + strUserTypeId + "' or " + USER_TYPE_IDS + " LIKE '%" + strUserTypeId + "%' or " + USER_TYPE_IDS + " = '' ) and "
                + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " != '' ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";

//        select * from apps_list_table WHERE org_id = 'Kabaddi Competition' and user_id = 'KABA00000008' and
//                ((post_id !='0' and post_id ='1_1_1_1' or post_id LIKE '%1_1_1_1%' ) or
//                (user_type_ids !='0' and user_type_ids ='2' or user_type_ids LIKE '%1$2$3%'))
//        and WORKSPACEName !='' ORDER BY CAST(DistrubutionID as integer) DESC

        String query_16022023 = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' " +
                "and (( " + POST_ID + " != '0' and " + POST_ID + " = '" + strPostId + "' or " + POST_ID + " LIKE '%" + strPostId + "%') or (" + USER_TYPE_IDS + " != '0' and " + USER_TYPE_IDS + " = '" + strUserTypeId +
                "' or " + USER_TYPE_IDS + " LIKE '%" + strUserTypeId + "%')) and " + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " != '' ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";


        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' " +
                "and (( " + POST_ID + " != '0' and " + POST_ID + " = '" + strPostId + "' or " + POST_ID + " LIKE '%" + strPostId + "%') or (" + USER_TYPE_IDS + " != '0' and " + USER_TYPE_IDS + " = '" + strUserTypeId +
                "' or " + USER_TYPE_IDS + " LIKE '%" + strUserTypeId + "%')) and " + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " != '' and " + APP_TYPE + " = 'Datacollection' ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";


/*
// Old Query
         String query1 = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " +
                DISPLAY_AS + "= '" + displayAs + "' and (" +
                POST_ID + " = '" + strPostId + "' or " + POST_ID + "= '0' or " +
                POST_ID + " LIKE '%" + strPostId + "%' or " +
                POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + ") and "
                + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
*/

        Log.d(TAG, "getDataFromAppsListTableRefresh: " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AppDetails model = new AppDetails();
                model.setDistrubutionID(cursor.getString(3));
                model.setCreatedBy(cursor.getString(4));
                model.setCreatedUserName(cursor.getString(5));
                model.setDescription(cursor.getString(6));
                model.setIsActive(cursor.getString(7));
                model.setAppName(cursor.getString(8));
                model.setAppVersion(cursor.getString(9));
                model.setAppType(cursor.getString(10));
                model.setDesignFormat("");//cursor.getString(11));
                model.setVideoLink(cursor.getString(12));
                model.setNewRow(cursor.getString(13));
                model.setAppIcon(cursor.getString(14));
                model.setApkVersion(cursor.getString(15));
                model.setApplicationReceivedDate(cursor.getString(16));
                model.setXMLFilePath(cursor.getString(17));
                model.setAppMode(cursor.getString(18));
//                if (strPostId != null) {
                model.setPostID(cursor.getString(22));
                model.setDisplayAppName(cursor.getString(31));
                model.setSharingVersion(cursor.getString(46));
//                }else{
//
//                }

                if (model.getAppType().equalsIgnoreCase(AppConstants.ML)) {
                    model.setTraining(cursor.getString(26));
                    modelList.add(model);
                    if (cursor.getString(27) != null && cursor.getString(27).equalsIgnoreCase("True")) {
                        AppDetails model1 = new AppDetails();
                        model1.setDistrubutionID(cursor.getString(3));
                        model1.setCreatedBy(cursor.getString(4));
                        model1.setCreatedUserName(cursor.getString(5));
                        model1.setDescription(cursor.getString(6));
                        model1.setIsActive(cursor.getString(7));
                        model1.setAppName(cursor.getString(8));
                        model1.setAppVersion(cursor.getString(9));
                        model1.setAppType(cursor.getString(10));
                        model1.setDesignFormat("");//cursor.getString(11));
                        model1.setVideoLink(cursor.getString(12));
                        model1.setNewRow(cursor.getString(13));
                        model1.setAppIcon(cursor.getString(14));
                        model1.setApkVersion(cursor.getString(15));
                        model1.setApplicationReceivedDate(cursor.getString(16));
                        model1.setXMLFilePath(cursor.getString(17));
                        model1.setAppMode(cursor.getString(18));
                        model1.setPostID(cursor.getString(22));
                        model1.setTesting(cursor.getString(27));

                        modelList.add(model1);
                    }
                } else {
                    modelList.add(model);
                }


            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", modelList.toString());

        return modelList;

    }

    public List<AppDetails> getDataFromAppsListTableRefreshOld(String org_id, String user_id, String strPostId, String displayAs, String strUserTypeId) {
        List<AppDetails> modelList = new ArrayList<AppDetails>();

        /* Workspace Individual Scenario*/
        String queryFirst = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and "
                + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " != '' ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";

        /* Work Space Refresh*/
        String queryModify = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' " +
                "and ( " + POST_ID + " = '" + strPostId + "' or " + POST_ID + " LIKE '%" + strPostId + "%' or " + POST_ID + " = '' ) and " +
                " ( " + USER_TYPE_IDS + " = '0" + strUserTypeId + "' or " + USER_TYPE_IDS + " LIKE '%" + strUserTypeId + "%' or " + USER_TYPE_IDS + " = '' ) and "
                + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " != '' ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";

//        select * from apps_list_table WHERE org_id = 'Kabaddi Competition' and user_id = 'KABA00000008' and
//                ((post_id !='0' and post_id ='1_1_1_1' or post_id LIKE '%1_1_1_1%' ) or
//                (user_type_ids !='0' and user_type_ids ='2' or user_type_ids LIKE '%1$2$3%'))
//        and WORKSPACEName !='' ORDER BY CAST(DistrubutionID as integer) DESC

        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' " +
                "and (( " + POST_ID + " != '0' and " + POST_ID + " = '" + strPostId + "' or " + POST_ID + " LIKE '%" + strPostId + "%') or (" + USER_TYPE_IDS + " != '0' and " + USER_TYPE_IDS + " = '" + strUserTypeId +
                "' or " + USER_TYPE_IDS + " LIKE '%" + strUserTypeId + "%')) and " + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " != '' ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";


/*
// Old Query
         String query1 = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " +
                DISPLAY_AS + "= '" + displayAs + "' and (" +
                POST_ID + " = '" + strPostId + "' or " + POST_ID + "= '0' or " +
                POST_ID + " LIKE '%" + strPostId + "%' or " +
                POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + ") and "
                + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
*/

        Log.d(TAG, "getDataFromAppsListTableRefresh: " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AppDetails model = new AppDetails();
                model.setDistrubutionID(cursor.getString(3));
                model.setCreatedBy(cursor.getString(4));
                model.setCreatedUserName(cursor.getString(5));
                model.setDescription(cursor.getString(6));
                model.setIsActive(cursor.getString(7));
                model.setAppName(cursor.getString(8));
                model.setAppVersion(cursor.getString(9));
                model.setAppType(cursor.getString(10));
                model.setDesignFormat("");//cursor.getString(11));
                model.setVideoLink(cursor.getString(12));
                model.setNewRow(cursor.getString(13));
                model.setAppIcon(cursor.getString(14));
                model.setApkVersion(cursor.getString(15));
                model.setApplicationReceivedDate(cursor.getString(16));
                model.setXMLFilePath(cursor.getString(17));
                model.setAppMode(cursor.getString(18));
//                if (strPostId != null) {
                model.setPostID(cursor.getString(22));
                model.setDisplayAppName(cursor.getString(31));
//                }else{
//
//                }

                if (model.getAppType().equalsIgnoreCase(AppConstants.ML)) {
                    model.setTraining(cursor.getString(26));
                    modelList.add(model);
                    if (cursor.getString(27) != null && cursor.getString(27).equalsIgnoreCase("True")) {
                        AppDetails model1 = new AppDetails();
                        model1.setDistrubutionID(cursor.getString(3));
                        model1.setCreatedBy(cursor.getString(4));
                        model1.setCreatedUserName(cursor.getString(5));
                        model1.setDescription(cursor.getString(6));
                        model1.setIsActive(cursor.getString(7));
                        model1.setAppName(cursor.getString(8));
                        model1.setAppVersion(cursor.getString(9));
                        model1.setAppType(cursor.getString(10));
                        model1.setDesignFormat("");//cursor.getString(11));
                        model1.setVideoLink(cursor.getString(12));
                        model1.setNewRow(cursor.getString(13));
                        model1.setAppIcon(cursor.getString(14));
                        model1.setApkVersion(cursor.getString(15));
                        model1.setApplicationReceivedDate(cursor.getString(16));
                        model1.setXMLFilePath(cursor.getString(17));
                        model1.setAppMode(cursor.getString(18));
                        model1.setPostID(cursor.getString(22));
                        model1.setTesting(cursor.getString(27));

                        modelList.add(model1);
                    }
                } else {
                    modelList.add(model);
                }


            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", modelList.toString());

        return modelList;

    }

    /* Retrive  data from getDataFromInTaskAppsListTable database */
    public List<AppDetails> getDataFromInTaskAppsListTable(String org_id, String user_id, String strPostId) {
        List<AppDetails> modelList = new ArrayList<AppDetails>();
//        String query;
        //1.0 Query
        String query = "select * from " + APPS_LIST_TABLE_IN_TASK + " WHERE " + DB_ORG_ID + "='" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
        //3.0 Query
//        if (strPostId != null) {
//         String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " +
//                POST_ID + " LIKE '%" + strPostId + "%' or " + POST_ID + " = '' and "
//                + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
//        String query = "select * from " + APPS_LIST_TABLE_IN_TASK + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " +
//                POST_ID + " LIKE '%" + strPostId + "%' or " + POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + " and "
//                + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
//        } else {
//            query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
//        }
        Log.d(TAG, "getDataFromAppsListTable: " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AppDetails model = new AppDetails();
                model.setDistrubutionID(cursor.getString(3));
                model.setCreatedBy(cursor.getString(4));
                model.setCreatedUserName(cursor.getString(5));
                model.setDescription(cursor.getString(6));
                model.setIsActive(cursor.getString(7));
                model.setAppName(cursor.getString(8));
                model.setAppVersion(cursor.getString(9));
                model.setAppType(cursor.getString(10));
                model.setDesignFormat("");//cursor.getString(11));
                model.setVideoLink(cursor.getString(12));
                model.setNewRow(cursor.getString(13));
                model.setAppIcon(cursor.getString(14));
                model.setApkVersion(cursor.getString(15));
                model.setApplicationReceivedDate(cursor.getString(16));
                model.setXMLFilePath(cursor.getString(17));
                model.setAppMode(cursor.getString(18));
//                if (strPostId != null) {
                model.setPostID(cursor.getString(22));
//                }else{
//
//                }
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveInTaskAppsList", modelList.toString());

        return modelList;

    }

    /* Retrive  data from database */
    public List<AppDetails> getDataFromAppsListTableNotDistributed(String org_id, String user_id) {
        List<AppDetails> modelList = new ArrayList<AppDetails>();
        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " + DISTRIBUTION_ID + " is NULL";
//        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " + DISTRIBUTION_ID +" = ''";
//        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + APP_TYPE + " = '" + "QueryForm" + "' and "+ DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "'";
        Log.d(TAG, "getDataFromAppsListTableNotDistribution: " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AppDetails model = new AppDetails();
                model.setDistrubutionID(cursor.getString(3));
                model.setCreatedBy(cursor.getString(4));
                model.setCreatedUserName(cursor.getString(5));
                model.setDescription(cursor.getString(6));
                model.setIsActive(cursor.getString(7));
                model.setAppName(cursor.getString(8));
                model.setAppVersion(cursor.getString(9));
                model.setAppType(cursor.getString(10));
                model.setDesignFormat("");//cursor.getString(11));
                model.setVideoLink(cursor.getString(12));
                model.setNewRow(cursor.getString(13));
                model.setAppIcon(cursor.getString(14));
                model.setApkVersion(cursor.getString(15));
                model.setApplicationReceivedDate(cursor.getString(16));
                model.setXMLFilePath(cursor.getString(17));
                model.setAppMode(cursor.getString(20));
                model.setPostID(cursor.getString(22));


                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", modelList.toString());

        return modelList;

    }

    /* Retrive  data from database */
    public AppDetails getAppDetails(String org_id, String appName, String user_id) {
        AppDetails model = new AppDetails();
        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + APP_NAME + "='" + appName + "' and " + DB_ORG_ID + "='" + org_id + "' and " + USER_ID + " = '" + user_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    model.setDistrubutionID(cursor.getString(3));
                    model.setCreatedBy(cursor.getString(4));
                    model.setCreatedUserName(cursor.getString(5));
                    model.setDescription(cursor.getString(6));
                    model.setIsActive(cursor.getString(7));
                    model.setAppName(cursor.getString(8));
//                    model.setAppName(cursor.getString(31));
                    model.setAppVersion(cursor.getString(9));
                    model.setAppType(cursor.getString(10));
                    model.setDesignFormat(cursor.getString(11));
                    model.setVideoLink(cursor.getString(12));
                    model.setNewRow(cursor.getString(13));
                    model.setAppIcon(cursor.getString(14));
                    model.setApkVersion(cursor.getString(15));
                    model.setApplicationReceivedDate(cursor.getString(16));
                    model.setXMLFilePath(cursor.getString(17));
                    model.setAppMode(cursor.getString(20));
                    model.setPostID(cursor.getString(22));
                    model.setDisplayAppName(cursor.getString(31));

                } while (cursor.moveToNext());
            }
            Log.d("RetrieveAppsList", model.toString());
            db.close();
            return model;
        } else {
            return null;
        }


    }

    /* Retrieve  data from database */
    public OutTaskDataModel getDataFromOutTaskTableObject(String outTaskId, String org_id, String user_id, String postId) {
        OutTaskDataModel model = new OutTaskDataModel();
        String query = "select * from " + OUT_TASK_TABLE + " WHERE " + OUT_TASK_TASK_ID + " = '" + outTaskId + "' and " + OUT_TASK_ORG_ID + " = '" + org_id + "' and " + OUT_TASK_USER_ID + " = '" + user_id + "'" +
                " and " + OUT_TASK_POST_ID + " = '" + postId + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {


                    model.setSNO(cursor.getString(0));
                    model.setTaskID(cursor.getString(1));
                    model.setOrgId(cursor.getString(2));
                    model.setUserId(cursor.getString(3));
                    model.setPostID(cursor.getString(4));
                    model.setTaskName(cursor.getString(5));
                    model.setTaskDesc(cursor.getString(6));
                    model.setPriorityId(cursor.getString(7));
                    model.setPriority(cursor.getString(8));
                    model.setTaskStatus(cursor.getString(9));
                    model.setIsSingleUser(cursor.getString(10));
                    model.setSingleUserStatus(cursor.getString(11));
                    model.setStartDate(cursor.getString(12));
                    model.setEndDate(cursor.getString(13));
                    model.setDistributionDate(cursor.getString(14));
                    model.setStartDisplayDate(cursor.getString(15));
                    model.setEndDisplayDate(cursor.getString(16));
                    model.setStatus(cursor.getString(17));
                    model.setCreatedBy(cursor.getString(18));
                    model.setTotalAssigned(cursor.getString(19));
                    model.setTotalInprogress(cursor.getString(20));
                    model.setTotalCompleted(cursor.getString(21));
                    model.setAppInfo(cursor.getString(22));
                    model.setFilesInfo(cursor.getString(23));
                    model.setGroupInfo(cursor.getString(24));
                    model.setEmpInfo(cursor.getString(25));
                    model.setCommentsInfo(cursor.getString(26));
                    model.setDistributionStatus(cursor.getString(27));


                } while (cursor.moveToNext());
            }
            Log.d("RetrieveAppsList", model.toString());

            return model;
        } else {
            return null;
        }


    }

    /*Retrieve single Object from InTaskTable*/
    public InTaskDataModel getSingleObjectFromInTaskTable(String inTaskId, String org_id, String user_id, String postId) {
        InTaskDataModel model = new InTaskDataModel();
        String query = "select * from " + IN_TASK_TABLE + " WHERE " + IN_TASK_TASK_ID + " = '" + inTaskId + "' and " + IN_TASK_ORG_ID + " = '" + org_id + "' and " + IN_TASK_USER_ID + " = '" + user_id + "'" +
                " and " + IN_TASK_POST_ID + " = '" + postId + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    model.setSNO(cursor.getString(0));
                    model.setTaskID(cursor.getString(1));
                    model.setOrgId(cursor.getString(2));
                    model.setUserId(cursor.getString(3));
                    model.setPostID(cursor.getString(4));
                    model.setTaskName(cursor.getString(5));
                    model.setTaskDesc(cursor.getString(6));
                    model.setPriorityId(cursor.getString(7));
                    model.setPriority(cursor.getString(8));
                    model.setIsSingleUser(cursor.getString(9));
                    model.setSingleUserStatus(cursor.getString(10));
                    model.setStartDate(cursor.getString(11));
                    model.setEndDate(cursor.getString(12));
                    model.setStartDisplayDate(cursor.getString(13));
                    model.setEndDisplayDate(cursor.getString(14));
                    model.setTaskStatus(cursor.getString(15));
                    model.setTaskStatusId(cursor.getString(16));
                    model.setTotalAssigned(cursor.getString(17));
                    model.setTotalInprogress(cursor.getString(18));
                    model.setTotalCompleted(cursor.getString(19));
                    model.setAppInfo(cursor.getString(20));
                    model.setFilesInfo(cursor.getString(21));
                    model.setCommentsInfo(cursor.getString(22));
                    model.setSingleUserInfo(cursor.getString(23));
                    model.setDistributionStatus(cursor.getString(24));
                    model.setDistributionDate(cursor.getString(25));
                    model.setDistributionDisplayDate(cursor.getString(26));
                    model.setCreatedBy(cursor.getString(27));


                } while (cursor.moveToNext());
            }
            Log.d("RetrieveIntAskObject", model.toString());

            return model;
        } else {
            return null;
        }


    }

    /*Retrieve single Object from SendCommentsOffLine*/
    public TaskCommentDetails getSingleObjectFromSendCommentsOffLine(String inTaskId, String inTaskStatusId, String org_id, String user_id, String postId) {
        TaskCommentDetails model = new TaskCommentDetails();
        String query = "select * from " + SEND_COMMENTS_OFFLINE_TABLE + " WHERE "
                + SEND_COMMENTS_OFFLINE_TASK_ID + " = '" + inTaskId + "' and "
                + SEND_COMMENTS_OFFLINE_TASK_STATUS_ID + " = '" + inTaskStatusId + "' and "
                + SEND_COMMENTS_OFFLINE_ORG_ID + " = '" + org_id + "' and "
                + SEND_COMMENTS_OFFLINE_USER_ID + " = '" + user_id + "'" +
                " and " + SEND_COMMENTS_OFFLINE_POST_ID + " = '" + postId + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    model.setOrgId(cursor.getString(1));
                    model.setTaskId(cursor.getString(2));
                    model.setTaskComments(cursor.getString(3));
                    model.setTaskStatusId(cursor.getString(4));
                    model.setUserId(cursor.getString(5));
                    model.setPostId(cursor.getString(6));
                    model.setLocationCode(cursor.getString(7));
                    model.setDepartmentId(cursor.getString(8));
                    model.setDesignationId(cursor.getString(9));
                    model.setIsSelfComment(cursor.getString(10));
                    model.setDeviceId(cursor.getString(11));
                    model.setVersionNo(cursor.getString(12));
                    model.setMobileDate(cursor.getString(13));

                } while (cursor.moveToNext());
            }
            Log.d("RetrieveIntAskObject", model.toString());

            return model;
        } else {
            return null;
        }


    }

    /* Retrive  data from database Single InTaskAppDetails */
    public AppDetails getAppDetailsInTask(String org_id, String appName, String user_id) {
        AppDetails model = new AppDetails();
        String query = "select * from " + APPS_LIST_TABLE_IN_TASK + " WHERE " + APP_NAME + "='" + appName + "' and " + DB_ORG_ID + "='" + org_id + "' and " + USER_ID + " = '" + user_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    model.setDistrubutionID(cursor.getString(3));
                    model.setCreatedBy(cursor.getString(4));
                    model.setCreatedUserName(cursor.getString(5));
                    model.setDescription(cursor.getString(6));
                    model.setIsActive(cursor.getString(7));
                    model.setAppName(cursor.getString(8));
                    model.setAppVersion(cursor.getString(9));
                    model.setAppType(cursor.getString(10));
                    model.setDesignFormat(cursor.getString(11));
                    model.setVideoLink(cursor.getString(12));
                    model.setNewRow(cursor.getString(13));
                    model.setAppIcon(cursor.getString(14));
                    model.setApkVersion(cursor.getString(15));
                    model.setApplicationReceivedDate(cursor.getString(16));
                    model.setXMLFilePath(cursor.getString(17));
                    model.setAppMode(cursor.getString(20));
                    model.setPostID(cursor.getString(22));

                } while (cursor.moveToNext());
            }
            Log.d("RetrieveAppsList", model.toString());

            return model;
        } else {
            return null;
        }


    }

    /* Retrive  data from database Single InTaskAppDetails */
    public AppDetails getAppDetails(String org_id, String appName) {
        AppDetails model = new AppDetails();
        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + APP_NAME + "='" + appName + "' and " + DB_ORG_ID + "='" + org_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    model.setDistrubutionID(cursor.getString(3));
                    model.setCreatedBy(cursor.getString(4));
                    model.setCreatedUserName(cursor.getString(5));
                    model.setDescription(cursor.getString(6));
                    model.setIsActive(cursor.getString(7));
                    model.setAppName(cursor.getString(8));
                    model.setAppVersion(cursor.getString(9));
                    model.setAppType(cursor.getString(10));
                    model.setDesignFormat(cursor.getString(11));
                    model.setVideoLink(cursor.getString(12));
                    model.setNewRow(cursor.getString(13));
                    model.setAppIcon(cursor.getString(14));
                    model.setApkVersion(cursor.getString(15));
                    model.setApplicationReceivedDate(cursor.getString(16));
                    model.setXMLFilePath(cursor.getString(17));
                    model.setAppMode(cursor.getString(20));
                    model.setPostID(cursor.getString(22));
                    model.setPostID(cursor.getString(22));
                    model.setTableName(cursor.getString(32));
                } while (cursor.moveToNext());
            }
            Log.d("RetrieveAppsList", model.toString());

            return model;
        } else {
            return null;
        }


    }

    /* Retrive  Single Object data from database */
    public GetUserDistributionsResponse getSingleDataFromE_LearningTable(String distributedID, String org_id, String user_id) {
        String query = "select * from " + E_LEARNING_TABLE + " WHERE " + E_LEARNING_DISTRIBUTION_ID + "='" + distributedID + "' and " + DB_ORG_ID + "='" + org_id + "' and " + USER_ID + " = '" + user_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        GetUserDistributionsResponse model = new GetUserDistributionsResponse();
        if (cursor.moveToFirst()) {
            do {

                model.setSNO(cursor.getString(0));
                model.setDistributionId(cursor.getString(1));
                model.setOrganization_Id(cursor.getString(2));
                model.setTopicName(cursor.getString(3));
                model.setIs_Assessment(cursor.getString(4));
                model.setStartDate(cursor.getString(5));
                model.setEndDate(cursor.getString(6));
                model.setStartTime(cursor.getString(7));
                model.setEndTime(cursor.getString(8));
                model.setExamDuration(cursor.getString(9));
                model.setNoOfAttempts(cursor.getString(10));
                model.setDistributionDate(cursor.getString(11));
                model.setStartDisplayDate(cursor.getString(12));
                model.setEndDisplayDate(cursor.getString(13));
                model.setStartDisplayTime(cursor.getString(14));
                model.setEndDisplayTime(cursor.getString(15));
                model.setNoOfUserAttempts(cursor.getString(16));
                model.setFilesInfo(cursor.getString(17));
                model.sethQuestions(cursor.getString(18));
                model.setmQuestions(cursor.getString(19));
                model.settQuestions(cursor.getString(20));
                model.setlQuestions(cursor.getString(21));
                model.setIs_Compexcity(cursor.getString(22));
                model.setPostID(cursor.getString(25));
                model.setTopicCoverImage(cursor.getString(26));
                model.setTopicDescription(cursor.getString(27));
//                model.setQuestions(cursor.getString(18));
//                model.setAssessmentInfo(cursor.getString(20));

            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", model.toString());

        return model;

    }

    /* Retrive  Refresh Appslist data from database */
    public AppDetails getAppDetailsRefresh(String org_id, String appName, String user_id) {
        AppDetails model = new AppDetails();
        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + APP_NAME + "='" + appName + "' and " + DB_ORG_ID + "='" + org_id + "'  and " + USER_ID + " = '" + user_id + "'";
//        String query = "select * from " + APPS_LIST_TABLE + " ";

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                do {

                    model.setDistrubutionID(cursor.getString(3));
                    model.setCreatedBy(cursor.getString(4));
                    model.setCreatedUserName(cursor.getString(5));
                    model.setDescription(cursor.getString(6));
                    model.setIsActive(cursor.getString(7));
                    model.setAppName(cursor.getString(8));
                    model.setAppVersion(cursor.getString(9));
                    model.setAppType(cursor.getString(10));
                    model.setDesignFormat(cursor.getString(11));
                    model.setVideoLink(cursor.getString(12));
                    model.setNewRow(cursor.getString(13));
                    model.setAppIcon(cursor.getString(14));
                    model.setApkVersion(cursor.getString(15));
                    model.setApplicationReceivedDate(cursor.getString(16));
                    model.setXMLFilePath(cursor.getString(17));
                    model.setAppMode(cursor.getString(20));


                } while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.getMessage();
        } finally {
            db.endTransaction();
        }

        Log.d("RetrieveAppsList", model.toString());

        return model;

    }

    /* Retrive  Refresh Appslist data from database */
    public AppDetails getAppDetailsRefreshQuery(String org_id, String queryName, String user_id) {
        AppDetails model = new AppDetails();
        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + APP_NAME + "='" + queryName + "' and " + DB_ORG_ID + "='" + org_id + "'  and " + USER_ID + " = '" + user_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                model.setDistrubutionID(cursor.getString(3));
                model.setCreatedBy(cursor.getString(4));
                model.setCreatedUserName(cursor.getString(5));
                model.setDescription(cursor.getString(6));
                model.setIsActive(cursor.getString(7));
                model.setAppName(cursor.getString(8));
                model.setAppVersion(cursor.getString(9));
                model.setAppType(cursor.getString(10));
                model.setDesignFormat(cursor.getString(11));
                model.setVideoLink(cursor.getString(12));
                model.setNewRow(cursor.getString(13));
                model.setAppIcon(cursor.getString(14));
                model.setApkVersion(cursor.getString(15));
                model.setApplicationReceivedDate(cursor.getString(16));
                model.setXMLFilePath(cursor.getString(17));
                model.setAppMode(cursor.getString(20));


            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", model.toString());

        return model;

    }

    /* Retrive  data from database */
    public List<OfflineDataSync> getDataFromSubmitFormListTable(String org_id, String status, String user_id) {
        List<OfflineDataSync> modelList = new ArrayList<OfflineDataSync>();
//        String query = "select * from " + FORM_SUBMIT_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "' and  " + FORM_SUBMIT_TABLE_SYNC_STATUS + "='" + status + "'";
        String query = "SELECT t.app_name,t.prepared_json_string,t.prepared_files_json_string," +
                "t.prepared_json_string_subform,t.prepared_files_json_string_subform," +
                "(SELECT COUNT('x') FROM submit_table ct WHERE ct.app_name = t.app_name  and " +
                "sync_status = '" + status + "' and " + DB_ORG_ID + " = '" + org_id + "'  and " +
                "" + USER_ID + " = '" + user_id + "') as rec_count FROM submit_table t where sync_status = '0' and " +
                "user_id = '" + user_id + "'group by t.app_name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                OfflineDataSync model = new OfflineDataSync();
                model.setApp_name(cursor.getString(0));
                model.setPrepared_json_string(cursor.getString(1));
                model.setPrepared_files_json_string(cursor.getString(2));
                model.setPrepared_json_string_subform(cursor.getString(3));
                model.setPrepared_files_json_string_subform(cursor.getString(4));
                model.setRec_count(cursor.getString(5));
                model.setSync_Type("App");

                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveOfflineAppsList", modelList.toString());

        return modelList;

    }

    public List<OfflineDataSync> getDataFromCallAPI_RequestTable(String org_id, String user_id) {
        List<OfflineDataSync> DataSync_modelList = new ArrayList<OfflineDataSync>();

        List<CallAPIRequestDataSync> modelList = new ArrayList<CallAPIRequestDataSync>();

//        String query = "SELECT * From "+CallAPI_Request_TABLE+ " WHERE "+CallAPI_Request_OrgID+ "='" +org_id+"' and "+CallAPI_Request_OrgID+"='"+ user_id+"'";
        String query = "SELECT t.Sno,t.CallAPI_Request_OrgID,t.CallAPI_Request_loginID," +
                "t.CallAPI_Request_APIName,t.CallAPI_Request_INPARAMS," +
                "(SELECT COUNT('x') FROM CallAPI_Request_TABLE ct WHERE ct.CallAPI_Request_APIName = t.CallAPI_Request_APIName  and " + CallAPI_Request_OrgID + " = '" + org_id +
                "'  and " + CallAPI_Request_loginID + " = '" + user_id + "') as rec_count FROM CallAPI_Request_TABLE t where  CallAPI_Request_loginID = '" + user_id + "'group by t.CallAPI_Request_APIName";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                OfflineDataSync datasync_model = new OfflineDataSync();

                CallAPIRequestDataSync model = new CallAPIRequestDataSync();
                model.setSno(cursor.getString(cursor.getColumnIndex(CallAPI_Request_SERIAL_NO)));
                model.setOrgId(cursor.getString(cursor.getColumnIndex(CallAPI_Request_OrgID)));
                model.setUserID(cursor.getString(cursor.getColumnIndex(CallAPI_Request_loginID)));
                model.setAPIName(cursor.getString(cursor.getColumnIndex(CallAPI_Request_APIName)));
                model.setInputJSONData(cursor.getString(cursor.getColumnIndex(CallAPI_Request_INPARAMS)));
                modelList.add(model);

                datasync_model.setApp_name(cursor.getString(cursor.getColumnIndex(CallAPI_Request_APIName)));
                datasync_model.setSync_Type("Request");
                datasync_model.setCallAPIRequest(model);
                datasync_model.setRec_count(cursor.getString(5));

                DataSync_modelList.add(datasync_model);

            } while (cursor.moveToNext());
        }

        Log.d("CallAPI_RequestTable", DataSync_modelList.toString());

        return DataSync_modelList;

    }

    public void createFormTable(String formTable) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "createFormTabledb: " + formTable);
        db.execSQL(formTable);
        db.close();
    }

    public boolean tableExists(String table, SQLiteDatabase db) {


        int count = 0;
        String[] args = {"table", table};
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type=? AND name=?", args, null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        return count > 0;

       /* String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='"+tableName+"'";
        Cursor mCursor = db.rawQuery(sql, null);
        if (mCursor.getCount() > 0) {
            return true;
        }
        mCursor.close();
        return false;*/
    }

    public boolean tableExists(String table) {
        SQLiteDatabase db = this.getWritableDatabase();

        int count = 0;
        String[] args = {"table", table};
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type=? AND name=?", args, null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count > 0;

       /* String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='"+tableName+"'";
        Cursor mCursor = db.rawQuery(sql, null);
        if (mCursor.getCount() > 0) {
            return true;
        }
        mCursor.close();
        return false;*/
    }

    public void CreateTablesDynamic(String tableName, String[] tableCols) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(CreateTableStringByID(tableName,
                UID, tableCols));
        db.close();
    }

    public void AlterCols(String tableName, String col) {
        SQLiteDatabase db = this.getWritableDatabase();
        String alter = "ALTER TABLE " + tableName + " ADD " + col + " TEXT";
        db.execSQL(alter);
        db.close();
    }

    public String[] CreateCols(String tableCols) {
        String[] splits = tableCols.split("\\,");
        String[] cols = new String[splits.length];
        for (int i = 0; i < splits.length; i++) {
            cols[i] = splits[i].substring(0, splits[i].indexOf(" "));
        }
        return cols;
    }

    public long insertIntoCallAPI_Request(String tableName, String orgID, String loginID, String APIName, String InputMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CallAPI_Request_OrgID, orgID);
        cv.put(CallAPI_Request_loginID, loginID);
        cv.put(CallAPI_Request_APIName, APIName);
        cv.put(CallAPI_Request_INPARAMS, InputMap);


        long result = db.insert(tableName, null, cv);
        db.close();
        return result;
    }

    public long insertIntoFormTable(String orgID, String distributionID, String createdBy, String sync_status, String appVersion, String tableName, List<String> stringListSubmit, List<HashMap<String, String>> stringListFiles) {
        String delimeter = "\\|";

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DB_ORG_ID, orgID);
        cv.put(DISTRIBUTION_ID, distributionID);
        cv.put(CREATED_BY, createdBy);
        cv.put(FORM_SUBMIT_TABLE_SYNC_STATUS, sync_status);
        cv.put(APP_VERSION, appVersion);
        for (int i = 0; i < stringListSubmit.size(); i++) {
            String[] toInsert = stringListSubmit.get(i).split(delimeter);
            cv.put(toInsert[0], toInsert[1]);
        }
        for (int i = 0; i < stringListFiles.size(); i++) {
            String controlName = null;
            HashMap<String, String> toInsertFile = stringListFiles.get(i);
            for (String key : toInsertFile.keySet()) {
                System.out.println(key);
                controlName = key;
            }
            String filePath = toInsertFile.get(controlName);
            cv.put(controlName, filePath);
        }
        long result = db.insert(tableName, null, cv);
        db.close();
        return result;

    }

    public String insertintoTableForManageData(String tableName, String trans_id, ContentValues colValCV) {
        String insertResult = "";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            long result = db.insertOrThrow(tableName, null, colValCV);
            if (result == -1) {
                insertResult = "Insertion Failed!";
            } else {
                String query = "SELECT last_insert_rowid() FROM " + tableName;
                Cursor cur = db.rawQuery(query, null);
                if (cur.moveToFirst()) {
                    //**Update row id in trans id column of Main Form Table**//
                    ContentValues contentValuesUpdate = new ContentValues();
                    contentValuesUpdate.put(trans_id, cur.getString(0));
                    db.update(tableName, contentValuesUpdate, "rowid='" + cur.getString(0) + "'", null);
                    //**Update row id in trans id column of Main Form Table**//
                }
            }
        } catch (Exception e) {
            insertResult = e.getMessage();
        } finally {
            db.close();
        }
        return insertResult;
    }

    public long insertintoTable(String tableName, String[] colNames,
                                String[] colVals) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < colNames.length; i++) {
            cv.put(colNames[i], colVals[i]);
        }
        long result = db.insert(tableName, null, cv);
        db.close();
        return result;

    }

    /* Retrieve  data from database */
    public DataControls getDataControlVersion(String org_id, String control_name, String user_id) {
        DataControls model = new DataControls();
        String query = "select * from " + DATA_CONTROL_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "' and " + CONTROL_NAME + " = '" + control_name + "'  and " + USER_ID + " = '" + user_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                model.setVersion(cursor.getString(8));

            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", model.toString());

        return model;

    }

    /* Retrieve  data from database */
    public DataControls getDataControlIsAvailable(String org_id) {
        DataControls model = new DataControls();
        String query = "select * from " + DATA_CONTROL_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                model.setVersion(cursor.getString(8));
                model.setControlName(cursor.getString(5));

            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", model.toString());

        return model;

    }

    public List<DataControls> getDataControlsList(String org_id, String user_id) {
        List<DataControls> dataControlsList = new ArrayList<>();
        String query = "select * from " + DATA_CONTROL_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "'  and " + USER_ID + " = '" + user_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                DataControls model = new DataControls();
                model.setVersion(cursor.getString(8));
                model.setControlName(cursor.getString(5));
                model.setAccessibility(cursor.getString(7));
                dataControlsList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", dataControlsList.toString());

        return dataControlsList;

    }

    public DataControls getDataControls(String ControlName) {
        DataControls dataControls = new DataControls();
        String query = "select * from " + DATA_CONTROL_TABLE + " WHERE " + CONTROL_NAME + "='" + ControlName + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                DataControls model = new DataControls();
                model.setVersion(cursor.getString(8));
                model.setControlName(cursor.getString(5));
                model.setAccessibility(cursor.getString(7));
                model.setTextFilePath(cursor.getString(10));
                model.setControlStatus(cursor.getString(6));
                model.setDataControlType(cursor.getString(3));
                dataControls = model;
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", dataControls.toString());

        return dataControls;

    }

    public List<DataControls> getDataControlsListByOrgID(String org_id) {
        List<DataControls> dataControlsList = new ArrayList<>();
        String query = "select * from " + DATA_CONTROL_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                DataControls model = new DataControls();
                model.setVersion(cursor.getString(8));
                model.setControlName(cursor.getString(5));
                dataControlsList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", dataControlsList.toString());

        return dataControlsList;

    }

    /*Get Refreshed Data controls*/
    public DataControls getDataControlsListRefresh(String controlName, String org_id, String user_id) {
        DataControls dataControls = new DataControls();
        String query = "select * from " + DATA_CONTROL_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "'  and " + USER_ID + " = '" + user_id + "' and " + CONTROL_NAME + " = '" + controlName + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                dataControls.setControlName(cursor.getString(5)); // created Id
                dataControls.setVersion(cursor.getString(8)); // Version
//                dataControlsList.add(model);
            } while (cursor.moveToNext());
        }

//        Log.d("RetrieveAppsList", dataControlsList.toString());

        return dataControls;

    }

    /*Get Refreshed Data controls*/
    public DataControls getDataControlByUsingName(String dataControlName, String org_id, String user_id) {
        DataControls dataControls = new DataControls();
        String query = "select * from " + DATA_CONTROL_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "'  and " + USER_ID + " = '" + user_id + "' and " + CONTROL_NAME + " = '" + dataControlName + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                dataControls.setDependentControl(cursor.getString(cursor.getColumnIndex(DEPENDENT_CONTROL)));
                dataControls.setDataControlType(cursor.getString(cursor.getColumnIndex(DATA_CONTROL_TYPE)));
                dataControls.setCreatedUserID(cursor.getString(cursor.getColumnIndex(CREATED_USER_ID)));
                dataControls.setControlName(cursor.getString(cursor.getColumnIndex(CONTROL_NAME)));
                dataControls.setControlStatus(cursor.getString(cursor.getColumnIndex(CONTROL_STATUS)));
                dataControls.setAccessibility(cursor.getString(cursor.getColumnIndex(ACCESSIBILTY)));
                dataControls.setVersion(cursor.getString(cursor.getColumnIndex(VERSION)));
                dataControls.setLoc_type(cursor.getString(cursor.getColumnIndex(LOC_TYPE)));
                dataControls.setTextFilePath(cursor.getString(cursor.getColumnIndex(TEXT_FILE_PATH)));

//                dataControlsList.add(model);
            } while (cursor.moveToNext());
        }

//        Log.d("RetrieveAppsList", dataControlsList.toString());

        return dataControls;

    }

    /*  Delete All Tables */
    public void deleteDatabaseTables(String user_id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL("delete from " + API_NAMES_TABLE + " Where " + USER_ID + " = '" + user_id + "'");
            db.execSQL("delete from " + APPS_LIST_TABLE + " Where " + USER_ID + " = '" + user_id + "'");
            db.execSQL("delete from " + DATA_CONTROL_TABLE + " Where " + USER_ID + " = '" + user_id + "'");
            db.execSQL("delete from " + IN_TASK_TABLE + " Where " + IN_TASK_USER_ID + " = '" + user_id + "'");
            db.execSQL("delete from " + OUT_TASK_TABLE + " Where " + OUT_TASK_USER_ID + " = '" + user_id + "'");
            db.execSQL("delete from " + FORM_SUBMIT_TABLE + " Where " + USER_ID + " = '" + user_id + "'");
            db.execSQL("delete from " + APP_VERSION_TABLE + " Where " + APP_VERSION_USER_ID + " = '" + user_id + "'");
            db.execSQL("delete from " + E_LEARNING_TABLE + " Where " + USER_ID + " = '" + user_id + "'");
            db.execSQL("delete from " + E_LEARNING_TIME_SPENT_OFFLINE_TABLE + " Where " + E_LEARNING_TIME_SPENT_OFFLINE_USER_ID + " = '" + user_id + "'");
//            db.execSQL("delete from " + ORGANISATION_TABLE + "");

//            db.execSQL("delete from " + FORM_SUBMIT_TABLE + " Where "+USER_ID+" = '"+user_id+"'");
            db.close();
        } catch (Exception e) {
            Log.d("SQLiteDatabaseTableEx", String.valueOf(e));
        }
    }

    public int updateFormApiNames(APIDetails apiDetails, String serviceName, String orgId, String user_id) {

        int msg = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_ORG_ID, orgId);
        values.put(CREATED_USER_ID, apiDetails.getCreatedUserID());
        values.put(SERVICE_NAME, apiDetails.getServiceName());
        values.put(SERVICE_DESC, apiDetails.getServiceDesc());
        values.put(ACCESSIBILITY, apiDetails.getAccessibility());
        values.put(SERVICE_SOURCE, apiDetails.getServiceSource());
        values.put(SERVICE_TYPE, apiDetails.getServiceType());
        values.put(SERVICE_CALL_AT, apiDetails.getServiceCallsAt());
        values.put(SERVICE_RESULT, apiDetails.getServiceResult());
        values.put(SERVICE_URL, apiDetails.getServiceURl());
        values.put(SERVICE_MASK, apiDetails.getServiceURl_Mask());
        values.put(INPUT_PARAMETERS, apiDetails.getInputParameters());
        values.put(OUTPUT_PARAMETERS, apiDetails.getOutputParameters());
        values.put(OUTPUT_TYPE, apiDetails.getOutputType());
        values.put(API_NAME_VERSION, apiDetails.getVersion());
        values.put(XML_PATH, apiDetails.getXMLPath());
        values.put(METHOD_NAME, apiDetails.getMethodName());
        values.put(XML_FORMAT, apiDetails.getXMLFormat());
        values.put(NAME_SPACE, apiDetails.getNameSpace());
        values.put(METHOD_TYPE, apiDetails.getMethodType());

        if (apiDetails.getZ_Status_flag().equalsIgnoreCase("Update")) {
            msg = db.update(API_NAMES_TABLE, values, SERVICE_NAME + " = '" + serviceName + "'  and " + USER_ID + " = '" + user_id + "'", null);
        } else if (apiDetails.getZ_Status_flag().equalsIgnoreCase("Deleted")) {
//            msg = db.delete(API_NAMES_TABLE, SERVICE_NAME + " = '" + serviceName + "'  and " + USER_ID + " = '" + user_id + "'", null);
            String[] splitServiceName = serviceName.split("\\^");
            String strServiceName = splitServiceName[0];
            msg = db.delete(API_NAMES_TABLE, SERVICE_NAME + "=?", new String[]{strServiceName});
//                msg = db.delete(API_NAMES_TABLE, SERVICE_NAME + " = '" + strServiceName +"'", new String[]{user_id});
        }
        db.close();
        return msg;
    }

    //Update Apps List

    public int updateAppsList(AppDetails appDetails, String appName, String orgID, String user_id, String workSpaceName) {

        int msg = 0;
        if (appDetails.getVisibileIn().equalsIgnoreCase("") || appDetails.getVisibileIn() == null) {
            appDetails.setVisibileIn("Both");
        }
        if (appDetails.getVisibileIn().equalsIgnoreCase("Both") || appDetails.getVisibileIn().equalsIgnoreCase("Mobile")) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DB_ORG_ID, orgID);
            values.put(DISTRIBUTION_ID, appDetails.getDistrubutionID());
            values.put(CREATED_BY, appDetails.getCreatedBy());
            values.put(CREATED_USER_NAME, appDetails.getCreatedUserName());
            values.put(DESCRIPTION, appDetails.getDescription());
            values.put(ISACTIVE, appDetails.getIsActive());
            values.put(APP_NAME, appDetails.getAppName().split("\\^")[0]);
            values.put(APP_TYPE, appDetails.getAppType());
            values.put(APP_VERSION, appDetails.getAppVersion());
            values.put(APP_TYPE, appDetails.getAppType());
            values.put(DESIGN_FORMAT, appDetails.getDesignFormat());
            values.put(VIDEO_LINK, appDetails.getVideoLink());
            values.put(NEWROW, appDetails.getNewRow());
            values.put(APP_ICON, appDetails.getAppIcon());
            values.put(APK_VERSION, appDetails.getAppVersion());
            values.put(APPLICATION_RECEIVED_DATE, appDetails.getApplicationReceivedDate());
            values.put(XML_FILE_PATH, appDetails.getXMLFilePath());
            values.put(APP_MODE, appDetails.getAppMode());
            values.put(DISPLAY_AS, appDetails.getDisplayAs());
            values.put(DISPLAY_REPORT_AS, appDetails.getDisplayReportas());
            values.put(USER_LOCATION, appDetails.getUserLocation());
            values.put(Traning, appDetails.getTraining());
            values.put(Testing, appDetails.getTesting());
            values.put(VISIBLE_IN, appDetails.getVisibileIn());
            values.put(DISPLAYAPPNAME, appDetails.getAppName());
            if (appDetails.getTableName() != null) {
                values.put(TABLE_NAME, replaceWithUnderscore(appDetails.getTableName()));
            }
            values.put(TABLE_COLUMNS, appDetails.getTableColumns());
            values.put(PRIMARY_KEYS, appDetails.getPrimaryKey());
            Gson gson = new Gson();
            String foreignKeyDetails = gson.toJson(appDetails.getForeignKey());
            values.put(FOREIGN_KEYS, foreignKeyDetails);
            values.put(COMPOSITE_KEYS, appDetails.getCompositeKey());
            String subformDetails = gson.toJson(appDetails.getSubFormDetails());
            values.put(SUBFORM_DETAILS, subformDetails);
            values.put(DISPLAY_IN_APP_LIST, String.valueOf(appDetails.isDisplayInAppList()));
            if (appDetails.getZ_Status_flag().equalsIgnoreCase("Update")) {
                msg = db.update(APPS_LIST_TABLE, values, DISPLAYAPPNAME + " = '" + appDetails.getAppName() + "'  and " + USER_ID + " = '" + user_id + "' and " + WORKSPACEName + " = '" + workSpaceName + "'", null);
            } else if (appDetails.getZ_Status_flag().equalsIgnoreCase("Deleted")) {
                msg = db.delete(APPS_LIST_TABLE, DISPLAYAPPNAME + "=? + and " + USER_ID + "=? + and " + WORKSPACEName + "=?", new String[]{appDetails.getDisplayAppName(), user_id, workSpaceName});
            }

            db.close();

        }
        return msg;
    }

    //Update Workspace Apps List
    public int updateAppsListWorkSpace(AppDetails appDetails, AppDetails.WorkSpaceAppsList appDetailsWorkSpace, String appName, String orgID, String user_id, String workSpaceName) {

        String displayAppName = "";
        int msg = 0;
        Gson gson = new Gson();
        if (appDetails.getVisibileIn() == null || appDetails.getVisibileIn().equalsIgnoreCase("")) {
            appDetails.setVisibileIn("Both");
        }
        if (appDetails.getVisibileIn().equalsIgnoreCase("Both") || appDetails.getVisibileIn().equalsIgnoreCase("Mobile")) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values_wp = new ContentValues();
            values_wp.put(DISTRIBUTION_ID, appDetails.getDistrubutionID());
            values_wp.put(CREATED_BY, appDetailsWorkSpace.getCreatedBy());
            values_wp.put(CREATED_USER_NAME, appDetailsWorkSpace.getCreatedUserName());
            values_wp.put(APP_NAME, appDetailsWorkSpace.getAppName().split("\\^")[0]);
            values_wp.put(APP_TYPE, appDetailsWorkSpace.getAppType());
            values_wp.put(APP_VERSION, appDetailsWorkSpace.getAppVersion());
            values_wp.put(APP_ICON, appDetailsWorkSpace.getAppIcon());
            values_wp.put(APK_VERSION, appDetailsWorkSpace.getApkVersion());
            values_wp.put(DESCRIPTION, appDetailsWorkSpace.getDescription());
            values_wp.put(DESIGN_FORMAT, appDetailsWorkSpace.getDesignFormat());
            values_wp.put(XML_FILE_PATH, appDetailsWorkSpace.getXMLFilePath());
            values_wp.put(TABLE_NAME, appDetailsWorkSpace.getTableName());
            values_wp.put(TABLE_COLUMNS, appDetailsWorkSpace.getTableColumns());
            values_wp.put(ISACTIVE, appDetailsWorkSpace.getIsActive());
            values_wp.put(APP_MODE, appDetailsWorkSpace.getAppMode());
            //nk
            if (appDetailsWorkSpace.getTableName() != null) {
                values_wp.put(TABLE_NAME, replaceWithUnderscore(appDetailsWorkSpace.getTableName()));
            }
            values_wp.put(TABLE_COLUMNS, appDetailsWorkSpace.getTableColumns());
            values_wp.put(PRIMARY_KEYS, appDetailsWorkSpace.getPrimaryKey());
            String foreignKeyDetails_workspace = gson.toJson(appDetailsWorkSpace.getForeignKey());
            values_wp.put(FOREIGN_KEYS, foreignKeyDetails_workspace);
            values_wp.put(COMPOSITE_KEYS, appDetailsWorkSpace.getCompositeKey());
            String subformDetails_workspace = gson.toJson(appDetailsWorkSpace.getSubFormDetails());
            values_wp.put(SUBFORM_DETAILS, subformDetails_workspace);
            String AutoIncrementControlNames_workspace = gson.toJson(appDetailsWorkSpace.getAutoIncrementControlNames());
            values_wp.put(AUTOINCREMENT_CONTROLNAMES, AutoIncrementControlNames_workspace);
            values_wp.put(DISPLAY_IN_APP_LIST, "true");
            values_wp.put(USER_TYPE_IDS, appDetails.getUserTypeID());
            values_wp.put(Traning, appDetails.getTraining());
            values_wp.put(Testing, appDetails.getTesting());
            if (appDetailsWorkSpace.getDisplayAppName() != null && !appDetailsWorkSpace.getDisplayAppName().isEmpty()) {
                displayAppName = appDetailsWorkSpace.getDisplayAppName();
            } else {
                displayAppName = appDetails.getDisplayAppName();
            }
            values_wp.put(DISPLAYAPPNAME, displayAppName);
            values_wp.put(IS_DATA_PERMISSION, appDetailsWorkSpace.getIsDataPermission());
            values_wp.put(DATA_PERMISSION_XML, appDetailsWorkSpace.getDataPermissionXML());
            values_wp.put(DATA_PERMISSION_XML_FILEPATH, appDetailsWorkSpace.getDataPermissionXmlFilePath());
            values_wp.put(IS_CONTROL_VISIBILTY, appDetailsWorkSpace.getIsControlVisibility());
            values_wp.put(CONTROL_VISIBILTY_XML, appDetailsWorkSpace.getControlVisibilityXML());
            values_wp.put(CONTROL_VISIBILTY_XML_FILEPATH, appDetailsWorkSpace.getControlVisibilityXmlFilePath());
            values_wp.put(SHARING_VERSION, appDetailsWorkSpace.getSharingVersion());
            values_wp.put(DISPLAY_ICON, appDetails.getDisplayIcon());

            if (appDetails.getZ_Status_flag().equalsIgnoreCase("Update")) {
                msg = db.update(APPS_LIST_TABLE, values_wp, DISPLAYAPPNAME + " = '" + displayAppName + "'  and " + USER_ID + " = '" + user_id + "' and " + WORKSPACEName + " = '" + workSpaceName + "'", null);

            } else if (appDetails.getZ_Status_flag().equalsIgnoreCase("Deleted")) {
                msg = db.delete(APPS_LIST_TABLE, DISPLAYAPPNAME + "=? + and " + USER_ID + "=? + and " + WORKSPACEName + "=?", new String[]{appDetails.getDisplayAppName(), user_id, workSpaceName});
            }

            db.close();

        }
        return msg;
    }

    public int updateWorkspaceAppsList(String WorkspaceName) {
        int msg = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        msg = db.delete(APPS_LIST_TABLE, APP_NAME + "=?", new String[]{WorkspaceName});
        msg = db.delete(APPS_LIST_TABLE, WORKSPACEName + "=?", new String[]{WorkspaceName});
        db.close();
        return msg;
    }

    public int deleteWorkspaceAppsList(String WorkspaceName) {
        int msg = 0;
        SQLiteDatabase db = this.getWritableDatabase();
//        msg = db.delete(APPS_LIST_TABLE,WORKSPACEName+ "=?  and "+DISTRIBUTION_ID+ "=?" , new String[]{WorkspaceName,distributionId});
        msg = db.delete(APPS_LIST_TABLE, APP_NAME + "=?", new String[]{WorkspaceName});
        db.close();
        return msg;
    }

    public int deleteWorkspaceApp(String appName, String distributionId) {
        int msg = 0;
        SQLiteDatabase db = this.getWritableDatabase();
//        msg = db.delete(APPS_LIST_TABLE,WORKSPACEName+ "=?  and "+DISTRIBUTION_ID+ "=?" , new String[]{WorkspaceName,distributionId});
        msg = db.delete(APPS_LIST_TABLE, APP_NAME + "=? and " + DISTRIBUTION_ID + "=?", new String[]{appName, distributionId});
        db.close();
        return msg;
    }
/*
    public int deleteWorkspaceAppsListApps(String WorkspaceName, String distributionId) {
        Log.d(TAG, "deleteWorkspaceAppsListApps: "+WorkspaceName+" DID "+distributionId);
        int msg = 0;
        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        msg = db.delete(APPS_LIST_TABLE, APP_NAME + "=? and "+DISTRIBUTION_ID+ "=?" , new String[]{WorkspaceName,distributionId});
        msg = db.delete(APPS_LIST_TABLE, APP_NAME + "=? and "+DISTRIBUTION_ID+ "=? and "+WORKSPACEName+ "=?" , new String[]{WorkspaceName,distributionId,""});
        db.close();
        return msg;
    }
*/

    public int deleteWorkspaceAppsListNew(String WorkspaceName, String distributionId, List<AppDetails.WorkSpaceAppsList> appDetailsList) {
        int msg = 0;
        SQLiteDatabase db = this.getWritableDatabase();
//        msg = db.delete(APPS_LIST_TABLE,WORKSPACEName+ "=?  and "+DISTRIBUTION_ID+ "=?" , new String[]{WorkspaceName,distributionId});
        if (appDetailsList != null && appDetailsList.size() > 0) {
            for (int i = 0; i < appDetailsList.size(); i++) {
                msg = db.delete(APPS_LIST_TABLE, APP_NAME + "=? and " + DISTRIBUTION_ID + "=?", new String[]{appDetailsList.get(i).getAppName(), distributionId});
            }
        }

        db.close();
        return msg;
    }

    public int deleteWorkspaceAppsListChange(String WorkspaceName, String distributionId) {
        int msg = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        msg = db.delete(APPS_LIST_TABLE, APP_NAME + "=? and " + DISTRIBUTION_ID + "=?", new String[]{WorkspaceName, distributionId});
        db.close();
        return msg;
    }

    /*Update ELearning table*/
    public int UpdateELearningTable(GetUserDistributionsResponse distributionsResponse, String distribution_id, String orgID, String user_id) {

        int msg = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(E_LEARNING_SNO, distributionsResponse.getSNO());
        values.put(E_LEARNING_DISTRIBUTION_ID, distributionsResponse.getDistributionId());
        values.put(E_LEARNING_ORGANIZATION_ID, distributionsResponse.getOrganization_Id());
        values.put(E_LEARNING_TOPIC_NAME, distributionsResponse.getTopicName());
        values.put(E_LEARNING_IS_ASSESSMENT, distributionsResponse.getIs_Assessment());
        values.put(E_LEARNING_START_DATE, distributionsResponse.getStartDate());
        values.put(E_LEARNING_END_DATE, distributionsResponse.getEndDate());
        values.put(E_LEARNING_START_TIME, distributionsResponse.getStartTime());
        values.put(E_LEARNING_END_TIME, distributionsResponse.getEndTime());
        values.put(E_LEARNING_EXAM_DURATION, distributionsResponse.getExamDuration());
        values.put(E_LEARNING_NO_OF_ATTEMPTS, distributionsResponse.getNoOfAttempts());
        values.put(E_LEARNING_DISTRIBUTION_DATE, distributionsResponse.getDistributionDate());
        values.put(E_LEARNING_START_DISPLAY_DATE, distributionsResponse.getStartDisplayDate());
        values.put(E_LEARNING_END_DISPLAY_DATE, distributionsResponse.getEndDisplayDate());
        values.put(E_LEARNING_START_DISPLAY_TIME, distributionsResponse.getStartDisplayTime());
        values.put(E_LEARNING_END_DISPLAY_TIME, distributionsResponse.getEndDisplayTime());
        values.put(E_LEARNING_NO_OF_USER_ATTEMPTS, distributionsResponse.getNoOfUserAttempts());
        values.put(E_LEARNING_FILES_INFO, distributionsResponse.getFilesInfo());
        values.put(E_LEARNING_H_QUESTIONS, distributionsResponse.gethQuestions());
        values.put(E_LEARNING_M_QUESTIONS, distributionsResponse.getmQuestions());
        values.put(E_LEARNING_T_QUESTIONS, distributionsResponse.gettQuestions());
        values.put(E_LEARNING_L_QUESTIONS, distributionsResponse.getlQuestions());
        values.put(E_LEARNING_IS_COMPLEXITY, distributionsResponse.getIs_Compexcity());
//            values.put(E_LEARNING_QUESTIONS, distributionsResponse.getQuestions());
        values.put(DB_ORG_ID, orgID);
        values.put(USER_ID, user_id);
        values.put(E_LEARNING_TOPIC_COVER_IMAGE, distributionsResponse.getTopicCoverImage());
        values.put(E_LEARNING_TOPIC_DESCRIPTION, distributionsResponse.getTopicDescription());

        if (distributionsResponse.getDistributionStatus().equalsIgnoreCase("2")) { //Update
            msg = db.update(E_LEARNING_TABLE, values, E_LEARNING_DISTRIBUTION_ID + " = '" + distribution_id + "'  and " + USER_ID + " = '" + user_id + "'", null);
//            msg = db.update(APPS_LIST_TABLE, values,  USER_ID + " = '" + user_id + "'", null);
        } else if (distributionsResponse.getDistributionStatus().equalsIgnoreCase("0")) { // Deleted
//            msg = db.delete(APPS_LIST_TABLE, APP_NAME + " = '" + appName + "'  and " + USER_ID + " = '" + user_id + "'", null);
//            String[] splitAppName = appName.split("\\^");
//            String strAppName = splitAppName[0];
            msg = db.delete(E_LEARNING_TABLE, E_LEARNING_DISTRIBUTION_ID + "=?", new String[]{distribution_id});
//            msg = db.delete(APPS_LIST_TABLE,APP_NAME +" = '"+ strAppName +"'", new String[]{user_id});
        }
        db.close();

        return msg;

    }

    /*Update InTask table*/
    /*Update InTask table*/
    public int UpdateInTaskTable(InTaskDataModel inTaskDataModel, String taskId, String orgID, String user_id, String postId) {

        int msg = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(IN_TASK_TASK_ID, inTaskDataModel.getTaskID());
        values.put(IN_TASK_ORG_ID, inTaskDataModel.getOrgId());
        values.put(IN_TASK_USER_ID, inTaskDataModel.getUserId());
        values.put(IN_TASK_POST_ID, inTaskDataModel.getPostID());
        values.put(IN_TASK_TASK_NAME, inTaskDataModel.getTaskName());
        values.put(IN_TASK_TASK_DESC, inTaskDataModel.getTaskDesc());
        values.put(IN_TASK_PRIORITY_ID, inTaskDataModel.getPriorityId());
        values.put(IN_TASK_PRIORITY, inTaskDataModel.getPriority());
        values.put(IN_TASK_IS_SINGLE_USER, inTaskDataModel.getIsSingleUser());
        values.put(IN_TASK_SINGLE_USER_STATUS, inTaskDataModel.getSingleUserStatus());
        values.put(IN_TASK_START_DATE, inTaskDataModel.getStartDate());
        values.put(IN_TASK_END_DATE, inTaskDataModel.getEndDate());
        values.put(IN_TASK_START_DISPLAY_DATE, inTaskDataModel.getStartDisplayDate());
        values.put(IN_TASK_END_DISPLAY_DATE, inTaskDataModel.getEndDisplayDate());
        values.put(IN_TASK_STATUS_ID, inTaskDataModel.getTaskStatusId());
        values.put(IN_TASK_TASK_STATUS, inTaskDataModel.getTaskStatus());
        values.put(IN_TASK_TOTAL_ASSIGNED, inTaskDataModel.getTotalAssigned());
        values.put(IN_TASK_TOTAL_IN_PROGRESS, inTaskDataModel.getTotalInprogress());
        values.put(IN_TASK_TOTAL_COMPLETED, inTaskDataModel.getTotalCompleted());
        values.put(IN_TASK_APP_INFO, inTaskDataModel.getAppInfo());
        values.put(IN_TASK_FILES_INFO, inTaskDataModel.getFilesInfo());
        values.put(IN_TASK_COMMENTS_INFO, inTaskDataModel.getCommentsInfo());
        values.put(IN_TASK_SINGLE_USER_INFO, inTaskDataModel.getSingleUserInfo());
        values.put(IN_TASK_DISTRIBUTION_STATUS, inTaskDataModel.getDistributionStatus());
        values.put(IN_TASK_DISTRIBUTION_DATE, inTaskDataModel.getDistributionDate());
        values.put(IN_TASK_DISTRIBUTION_ID, inTaskDataModel.getDistrubutionID());
        values.put(IN_TASK_UPDATION_DATE, inTaskDataModel.getTaskUpdationDate());


        if (inTaskDataModel.getDistributionStatus().equalsIgnoreCase("2")) { //Update

            msg = db.update(IN_TASK_TABLE, values, IN_TASK_TASK_ID + " = '" + taskId + "'  and "
                    + IN_TASK_USER_ID + " = '" + user_id + "' and "
                    + IN_TASK_POST_ID + " = '" + postId + "' and "
                    + IN_TASK_ORG_ID + " = '" + orgID + "'", null);
            Log.d(TAG, "UpdateInTaskTable: " + msg);
        } else if (inTaskDataModel.getDistributionStatus().equalsIgnoreCase("0")) { // Deleted

            msg = db.delete(IN_TASK_TABLE, IN_TASK_TASK_ID + "=?", new String[]{taskId});

        }
        db.close();

        return msg;

    }

    /*Update OutTask table*/
    public long UpdateOutTaskTable(OutTaskDataModel OutTaskDataModel, String taskId, String orgID, String user_id, String postId) {

        long msg = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(OUT_TASK_TASK_ID, OutTaskDataModel.getTaskID());
        values.put(OUT_TASK_ORG_ID, OutTaskDataModel.getOrgId());
        values.put(OUT_TASK_USER_ID, OutTaskDataModel.getUserId());
        values.put(OUT_TASK_POST_ID, OutTaskDataModel.getPostID());
        values.put(OUT_TASK_TASK_NAME, OutTaskDataModel.getTaskName());
        values.put(OUT_TASK_TASK_DESC, OutTaskDataModel.getTaskDesc());
        values.put(OUT_TASK_PRIORITY_ID, OutTaskDataModel.getPriorityId());
        values.put(OUT_TASK_PRIORITY, OutTaskDataModel.getPriority());
        values.put(OUT_TASK_TASK_STATUS, OutTaskDataModel.getTaskStatus());
        values.put(OUT_TASK_IS_SINGLE_USER, OutTaskDataModel.getIsSingleUser());
        values.put(OUT_TASK_SINGLE_USER_STATUS, OutTaskDataModel.getSingleUserStatus());
        values.put(OUT_TASK_START_DATE, OutTaskDataModel.getStartDate());
        values.put(OUT_TASK_END_DATE, OutTaskDataModel.getEndDate());
        values.put(OUT_TASK_DISTRIBUTION_DATE, OutTaskDataModel.getDistributionDate());
        values.put(OUT_TASK_START_DISPLAY_DATE, OutTaskDataModel.getStartDisplayDate());
        values.put(OUT_TASK_END_DISPLAY_DATE, OutTaskDataModel.getEndDisplayDate());
        values.put(OUT_TASK_STATUS, OutTaskDataModel.getStatus());
        values.put(OUT_TASK_CREATED_BY, OutTaskDataModel.getCreatedBy());
        values.put(OUT_TASK_TOTAL_ASSIGNED, OutTaskDataModel.getTotalAssigned());
        values.put(OUT_TASK_TOTAL_IN_PROGRESS, OutTaskDataModel.getTotalInprogress());
        values.put(OUT_TASK_TOTAL_COMPLETED, OutTaskDataModel.getTotalCompleted());
        values.put(OUT_TASK_APP_INFO, OutTaskDataModel.getAppInfo());
        values.put(OUT_TASK_FILES_INFO, OutTaskDataModel.getFilesInfo());
        values.put(OUT_TASK_GROUP_INFO, OutTaskDataModel.getGroupInfo());
        values.put(OUT_TASK_EMP_INFO, OutTaskDataModel.getEmpInfo());
        values.put(OUT_TASK_COMMENTS_INFO, OutTaskDataModel.getCommentsInfo());
        values.put(OUT_TASK_DISTRIBUTION_STATUS, OutTaskDataModel.getDistributionStatus());
        values.put(OUT_TASK_DISTRIBUTION_DISPLAY_DATE, OutTaskDataModel.getDistributionDisplayDate());
        values.put(OUT_TASK_UPDATION_DATE, OutTaskDataModel.getTaskUpdationDate());

        if (OutTaskDataModel.getDistributionStatus().equalsIgnoreCase("2")) { //Update
            msg = db.update(OUT_TASK_TABLE, values,
                    OUT_TASK_TASK_ID + " = '" + taskId + "' and "
                            + OUT_TASK_USER_ID + " = '" + user_id + "' and "
                            + OUT_TASK_POST_ID + " = '" + postId + "' and "
                            + OUT_TASK_ORG_ID + " = '" + orgID + "'", null);

        } else if (OutTaskDataModel.getDistributionStatus().equalsIgnoreCase("0")) { // Deleted

            msg = db.delete(OUT_TASK_TABLE, OUT_TASK_TASK_ID + "=?", new String[]{taskId});

        }
        db.close();

        return msg;

    }

    public int updateDataControlsList(DataControls dataControls, String controlName, String orgID, String user_id) {

        String strQuery = "select * from " + DATA_CONTROL_TABLE + " where " + CONTROL_NAME + " = " + controlName + " and " +
                DB_ORG_ID + " = " + orgID + " ";

        int msg = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DB_ORG_ID, orgID);
        values.put(DEPENDENT_CONTROL, dataControls.getDependentControl());
        values.put(DATA_CONTROL_TYPE, dataControls.getDataControlType());
        values.put(CREATED_USER_ID, dataControls.getCreatedUserID());
        values.put(CONTROL_NAME, dataControls.getControlName());
        values.put(CONTROL_STATUS, dataControls.getControlStatus());
        values.put(ACCESSIBILTY, dataControls.getAccessibility());
        values.put(VERSION, dataControls.getVersion());
        values.put(LOC_TYPE, dataControls.getLoc_type());
        values.put(TEXT_FILE_PATH, dataControls.getTextFilePath());

        if (dataControls.getZ_Status_flag().equalsIgnoreCase("Update")) {
            msg = db.update(DATA_CONTROL_TABLE, values, CONTROL_NAME + " = '" + controlName + "'  and " + USER_ID + " = '" + user_id + "'", null);
        } else if (dataControls.getZ_Status_flag().equalsIgnoreCase("Deleted")) {
//            msg = db.delete(DATA_CONTROL_TABLE, CONTROL_NAME + " = '" + controlName.replace("^","") + "'  and " + USER_ID + " = '" + user_id + "'", null);
            String[] splitControlName = controlName.split("\\^");
            String strControlName = splitControlName[0];
            msg = db.delete(DATA_CONTROL_TABLE, CONTROL_NAME + "=?", new String[]{strControlName});
//            msg = db.delete(DATA_CONTROL_TABLE, CONTROL_NAME +" = '"+strControlName+"'", new String[]{user_id});
        }
        db.close();

        return msg;

    }

    public int updateNewDataControlsList(DataControlsAndApis.DataControlDetails dataControls, String controlName, String orgID, String user_id) {

        String strQuery = "select * from " + DATA_CONTROL_TABLE + " where " + CONTROL_NAME + " = " + controlName + " and " +
                DB_ORG_ID + " = " + orgID + " ";

        int msg = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DB_ORG_ID, orgID);
        values.put(DEPENDENT_CONTROL, dataControls.getDependentControlName());
        values.put(DATA_CONTROL_TYPE, "");
        values.put(CREATED_USER_ID, "");
        values.put(CONTROL_NAME, dataControls.getDataControlName());
        values.put(ACCESSIBILTY, dataControls.getAccessingType());
        values.put(VERSION, dataControls.getVersion());
        values.put(LOC_TYPE, "");
        values.put(TEXT_FILE_PATH, dataControls.getTextFilePath());

        if (dataControls.getStatus().equalsIgnoreCase("Update")) {
            msg = db.update(DATA_CONTROL_TABLE, values, CONTROL_NAME + " = '" + controlName + "'  and " + USER_ID + " = '" + user_id + "'", null);
        } else if (dataControls.getStatus().equalsIgnoreCase("Deleted")) {
//            msg = db.delete(DATA_CONTROL_TABLE, CONTROL_NAME + " = '" + controlName.replace("^","") + "'  and " + USER_ID + " = '" + user_id + "'", null);
            String[] splitControlName = controlName.split("\\^");
            String strControlName = splitControlName[0];
            msg = db.delete(DATA_CONTROL_TABLE, CONTROL_NAME + "=?", new String[]{strControlName});
//            msg = db.delete(DATA_CONTROL_TABLE, CONTROL_NAME +" = '"+strControlName+"'", new String[]{user_id});
        }
        db.close();

        return msg;

    }

    /* Insert into Form Submit Table*/
    public long insertIntoFormSubmitTable(String orgID, String pageName, String createdUserId, String submittedUserId, String distributionID, String imei, String operationType, String transId, String appVersion, String tableSettingsType, String mapExistingType, List<QueryFilterField_Bean> insertFields, List<QueryFilterField_Bean> updateFields, List<QueryFilterField_Bean> filterFields, String preparedJsonData, String preparedFilesJsonData, String preparedJsonDataSubForm, String preparedFilesJsonDataSubForm, String status, JSONObject offlineJson, String user_id, String post_id, String has_map_existing) {

        // 1. get reference to writable DB
        Gson gson = new Gson();
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DB_ORG_ID, orgID);
        values.put(PAGE_NAME, pageName);
        values.put(CREATED_USER_ID_SUBMIT, createdUserId);
        values.put(SUBMITTED_UER_ID, submittedUserId);
        values.put(DISTRIBUTION_ID, distributionID);
        values.put(IMEI, imei);
        values.put(OPERATION_TYPE, operationType);
        values.put(TRANS_ID, transId);
        values.put(APP_VERSION, appVersion);
        values.put(TABLE_SETTINGS_TYPE, tableSettingsType);
        values.put(MAIN_TABLE_INSERT_FIELDS, gson.toJson(insertFields));
        values.put(MAIN_TABLE_UPDATE_FIELDS, gson.toJson(updateFields));
        values.put(MAIN_TABLE_FILTER_FIELDS, gson.toJson(filterFields));
        values.put(MAP_EXISTING_TYPE, mapExistingType);
        values.put(FORM_SUBMIT_TABLE_APP_NAME, pageName);
        values.put(FORM_SUBMIT_TABLE_JSON_STRING, preparedJsonData);
        values.put(FORM_SUBMIT_TABLE_JSON_FILE_STRING, preparedFilesJsonData);
        values.put(FORM_SUBMIT_TABLE_JSON_STRING_SUB_FORM, preparedJsonDataSubForm);
        values.put(FORM_SUBMIT_TABLE_JSON_FILE_STRING_SUB_FORM, preparedFilesJsonDataSubForm);
        values.put(FORM_SUBMIT_TABLE_SYNC_STATUS, status);
        values.put(FORM_SUBMIT_TABLE_OFFLINE_JSON, String.valueOf(offlineJson));
        values.put(USER_ID, user_id);
        values.put(POST_ID, post_id);
        values.put(HAS_MAP_EXISTING, has_map_existing);

        long results = db.insert(FORM_SUBMIT_TABLE, null, values);
        Log.d("FORM_SUBMIT_TABLE", FORM_SUBMIT_TABLE + " After insert " + pageName + ", " + preparedJsonData + ", " + status +
                " Records Inserted Count " + results);

        db.close();
        return results;
    }

    /* Insert into Form Submit Table*/
    public long updateIntoFormSubmitTable(String orgID, String pageName, String createdUserId, String submittedUserId, String distributionID, String imei, String operationType, String transId, String appVersion, String tableSettingsType, String mapExistingType, List<QueryFilterField_Bean> insertFields, List<QueryFilterField_Bean> updateFields, List<QueryFilterField_Bean> filterFields, String preparedJsonData, String preparedFilesJsonData, String preparedJsonDataSubForm, String preparedFilesJsonDataSubForm, String status, JSONObject offlineJson, String user_id, String sno, String post_id, String has_map_existing) {
        // 1. get reference to writable DB
        Gson gson = new Gson();
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DB_ORG_ID, orgID);
        values.put(PAGE_NAME, pageName);
        values.put(CREATED_USER_ID_SUBMIT, createdUserId);
        values.put(SUBMITTED_UER_ID, submittedUserId);
        values.put(DISTRIBUTION_ID, distributionID);
        values.put(IMEI, imei);
        values.put(OPERATION_TYPE, operationType);
        values.put(TRANS_ID, transId);
        values.put(APP_VERSION, appVersion);
        values.put(TABLE_SETTINGS_TYPE, tableSettingsType);
        values.put(MAP_EXISTING_TYPE, mapExistingType);
        values.put(MAIN_TABLE_INSERT_FIELDS, gson.toJson(insertFields));
        values.put(MAIN_TABLE_UPDATE_FIELDS, gson.toJson(updateFields));
        values.put(MAIN_TABLE_FILTER_FIELDS, gson.toJson(filterFields));
        values.put(FORM_SUBMIT_TABLE_APP_NAME, pageName);
        values.put(FORM_SUBMIT_TABLE_JSON_STRING, preparedJsonData);
        values.put(FORM_SUBMIT_TABLE_JSON_FILE_STRING, preparedFilesJsonData);
        values.put(FORM_SUBMIT_TABLE_JSON_STRING_SUB_FORM, preparedJsonDataSubForm);
        values.put(FORM_SUBMIT_TABLE_JSON_FILE_STRING_SUB_FORM, preparedFilesJsonDataSubForm);
        values.put(FORM_SUBMIT_TABLE_SYNC_STATUS, status);
        values.put(FORM_SUBMIT_TABLE_OFFLINE_JSON, String.valueOf(offlineJson));
        values.put(USER_ID, user_id);
        values.put(POST_ID, post_id);
        values.put(HAS_MAP_EXISTING, has_map_existing);
//        long results = db.update(FORM_SUBMIT_TABLE, values, );
        long results = db.update(FORM_SUBMIT_TABLE, values, FORM_SUBMIT_TABLE_SERIAL_NO + " = " + sno, null);
        Log.d("FORM_SUBMIT_TABLE", FORM_SUBMIT_TABLE + " After insert " + pageName + ", " + preparedJsonData + ", " + status +
                " Records Inserted Count " + results);

        db.close();
        return results;
    }

    public int updateFormSubmitDataStatus(List<OfflineDataSync> offlineDataSyncList) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean wasSuccess = true;
        int msg = 0;
        db.beginTransaction();
        try {
            for (OfflineDataSync model : offlineDataSyncList) {
                ContentValues cv = new ContentValues();
                cv.put(FORM_SUBMIT_TABLE_SYNC_STATUS, "1");
                msg = db.update(FORM_SUBMIT_TABLE, cv, FORM_SUBMIT_TABLE_SERIAL_NO + " = " + model.getSno(), null);

            }
            if (msg == -1) {
                wasSuccess = false;
            } else {
                db.setTransactionSuccessful();
            }

        } finally {
            db.endTransaction();
            db.close();
        }
        return msg;
    }

    /**
     * This method is used to get total records count in that table in corresponding to the column.
     *
     * @param tableName-name  of the Table.
     * @param colName-name    of the column.
     * @param colValue-column value.
     * @return
     */
    public int getCountByValue(String tableName, String colName, String colValue) {

        SQLiteDatabase db = this.getReadableDatabase();
        int cnt = 0;
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE "
                + colName + "='" + colValue + "'";
        // System.out.println("CountQuery:" + query);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        cnt = Integer.parseInt(cursor.getString(0));
        cursor.close();
        db.close();
        return cnt;
    }

    /**
     * This method is used to delete row data which we pass.
     *
     * @param context-Object  of Context, context from where the activity is going
     *                        to start.
     * @param tablename-name  of the Table.
     * @param columnName-name of the Column.
     * @param value-column    value.
     * @return
     */
    public boolean deleteRows(Context context, final String tablename,
                              String columnName, String value) {

        SQLiteDatabase db = this.getReadableDatabase();
        boolean flag = db.delete(tablename, columnName + "='" + value + "'",
                null) > 0;
        db.close();
        return flag;
    }

    /**
     * This method is used to delete particular row data.
     *
     * @param context-Object  of Context, context from where the activity is going
     *                        to start.
     * @param tablename-name  of the Table.
     * @param columnName-name of the column.
     * @param value-column    value.
     * @return
     */
    public boolean deleteRowData(Context context,
                                 final String tablename, String[] columnName, String[] value) {

        SQLiteDatabase db = this.getReadableDatabase();
        String whereClause = "";
        for (int k = 0; k < columnName.length; k++) {
            whereClause = whereClause + columnName[k] + "='" + value[k] + "'"
                    + " AND ";
        }
        whereClause = whereClause.substring(0, whereClause.length() - 5);

        boolean flag = db.delete(tablename, whereClause, null) > 0;
        // System.out.println("flag in database:" + flag);
        db.close();
        return flag;

    }

    /**
     * This method is used to delete data of all rows.
     *
     * @param context-Object of Context, context from where the activity is going
     *                       to start.
     * @param tablename-name of the Table.
     * @return
     */
    public boolean deleteAllRows(Context context, final String tablename) {

        SQLiteDatabase db = this.getReadableDatabase();
        boolean flag = db.delete(tablename, null, null) > 0;
        db.close();
        return flag;

    }

    /**
     * This method is used to update data of a particular row.
     *
     * @param context-Object      of Context, context from where the activity is going
     *                            to start.
     * @param tablename-name      of the Table.
     * @param columnNames-column  names.
     * @param columnValues-column values.
     * @param whereColumn-where   column name.
     * @param whereValue-where    column value.
     * @return
     */
    public boolean updateRowData(Context context,
                                 final String tablename, String[] columnNames,
                                 String[] columnValues, String[] whereColumn, String[] whereValue) {

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < columnNames.length; i++) {
            cv.put(columnNames[i], columnValues[i]);
        }
        boolean flag = false;
        if (whereColumn != null) {
            String whereClause = "";
            for (int k = 0; k < whereColumn.length; k++) {
                whereClause = whereClause + whereColumn[k] + "='"
                        + whereValue[k] + "'" + " AND ";
            }
            whereClause = whereClause.substring(0, whereClause.length() - 5);
            System.out.println("whereclause......" + whereClause);
            flag = db.update(tablename, cv, whereClause, null) > 0;
        } else {
            flag = db.update(tablename, cv, null, null) > 0;
        }

        db.close();
        System.out.println("flag.in updating ................." + flag);
        return flag;
    }

    /**
     * This method is used to get table column data by passing condition.
     *
     * @param TableName-name          of the table.
     * @param outColNamesByComma-name of the columns with comma separation.
     * @param wherecolnames-column    names to pass in where condition.
     * @param wherecolumnValue-column values to pass in where condition.
     * @return
     */
    public List<List<String>> getTableColDataByCond(String TableName,
                                                    String outColNamesByComma, String[] wherecolnames,
                                                    String[] wherecolumnValue) {
        if (outColNamesByComma.substring(outColNamesByComma.length() - 1).equalsIgnoreCase(",")) {
            outColNamesByComma = outColNamesByComma.substring(0, outColNamesByComma.length() - 1);
        }
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + outColNamesByComma + " FROM " + TableName
                + " WHERE ";
        for (int k = 0; k < wherecolnames.length; k++)
            query = query + wherecolnames[k] + "='" + wherecolumnValue[k] + "'"
                    + " AND ";
        query = query.substring(0, query.length() - 5);
        // //System.out.println("Final Query:" + query);
        Cursor cur = db.rawQuery(query, null);
        List<List<String>> list = new ArrayList<List<String>>();
        if (cur.getCount() > 0)
            list = cursorToListArr(cur);
        cur.close();
        db.close();
        return list;
    }

    /**
     * This method will return the list of rows in that table.
     *
     * @param c-cursor name
     * @return
     */
    public List<List<String>> cursorToListArr(Cursor c) {
        List<List<String>> rowList = new ArrayList<List<String>>();
        while (c.moveToNext()) {
            List<String> arr = new ArrayList<String>();
            for (int i = 0; i < c.getColumnCount(); i++) {
                if (c.getString(i) != null) {
                    arr.add(c.getString(i));
                } else {
                    arr.add("");
                }
            }
            rowList.add(arr);
        }
        c.close();
        return rowList;
    }

    public List<List<ColNameAndValuePojo>> cursorToListPojo(Cursor c) {
        List<List<ColNameAndValuePojo>> rowList = new ArrayList<List<ColNameAndValuePojo>>();
        while (c.moveToNext()) {
            List<ColNameAndValuePojo> arr = new ArrayList<ColNameAndValuePojo>();
            for (int i = 0; i < c.getColumnCount(); i++) {
                ColNameAndValuePojo colNameAndValuePojo = new ColNameAndValuePojo();
                colNameAndValuePojo.setColName(c.getColumnName(i));
                colNameAndValuePojo.setColValue(c.getString(i));
                arr.add(colNameAndValuePojo);
            }
            rowList.add(arr);
        }
        c.close();
        return rowList;
    }

    public int updateFormSubmitDataStatus(String sno, String user_id) {

        int msg = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FORM_SUBMIT_TABLE_SYNC_STATUS, "1");
        msg = db.update(FORM_SUBMIT_TABLE, cv, FORM_SUBMIT_TABLE_SERIAL_NO + " = '" + sno + "' and " + USER_ID + " = '" + user_id + "'", null);
        db.close();

        return msg;

    }

    /* Retrive data from database */
    public List<OfflineDataSync> getAppDataFromSubmitFormListTable(String org_id, String appName, String status, String user_id) {
        List<OfflineDataSync> modelList = new ArrayList<OfflineDataSync>();
        String query = "select * from " + FORM_SUBMIT_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "' and  " + PAGE_NAME + "='" + appName + "' and " + FORM_SUBMIT_TABLE_SYNC_STATUS + "='" + status + "' and " + USER_ID + " = '" + user_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                OfflineDataSync model = new OfflineDataSync();
                model.setSno(cursor.getString(0));
                model.setOrgId(cursor.getString(1));
                model.setApp_name(cursor.getString(17));
                model.setCreatedUserID(cursor.getString(3));
                model.setSubmittedUserID(cursor.getString(4));
                model.setDistributionID(cursor.getString(5));
                model.setIMEI(cursor.getString(6));
                model.setAppVersion(cursor.getString(9));
                model.setTableName(cursor.getString(10));
                model.setTableSettingsType(cursor.getString(11));
                model.setMapExistingType(cursor.getString(12));
                model.setInsertFields(cursor.getString(13));
                model.setUpdateFields(cursor.getString(14));
                model.setFilterFields(cursor.getString(15));
                model.setSubFormMapExistingData(cursor.getString(16));
                model.setPrepared_json_string(cursor.getString(18));
                model.setPrepared_files_json_string(cursor.getString(19));
                model.setPrepared_json_string_subform(cursor.getString(20));
                model.setPrepared_files_json_string_subform(cursor.getString(21));
                model.setSync_status(cursor.getString(22));
                model.setOffline_json(cursor.getString(23));
                model.setSubmittedUserPostID(cursor.getString(25));
                model.setHasMapExisting(cursor.getString(26));


                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveOfflineAppsList", modelList.toString());

        return modelList;

    }

    public List<OfflineDataSync> getDataFromCallAPI_RequestTableByAPIName(String org_id, String user_id, String APIName) {
        List<OfflineDataSync> DataSync_modelList = new ArrayList<OfflineDataSync>();

        List<CallAPIRequestDataSync> modelList = new ArrayList<CallAPIRequestDataSync>();

        String query = "SELECT * From " + CallAPI_Request_TABLE + " WHERE " + CallAPI_Request_OrgID + "='" + org_id
                + "' and " + CallAPI_Request_loginID + "='" + user_id + "' and " + CallAPI_Request_APIName + "='" + APIName + "'";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                OfflineDataSync datasync_model = new OfflineDataSync();

                CallAPIRequestDataSync model = new CallAPIRequestDataSync();
                model.setSno(cursor.getString(cursor.getColumnIndex(CallAPI_Request_SERIAL_NO)));
                model.setOrgId(cursor.getString(cursor.getColumnIndex(CallAPI_Request_OrgID)));
                model.setUserID(cursor.getString(cursor.getColumnIndex(CallAPI_Request_loginID)));
                model.setAPIName(cursor.getString(cursor.getColumnIndex(CallAPI_Request_APIName)));
                model.setInputJSONData(cursor.getString(cursor.getColumnIndex(CallAPI_Request_INPARAMS)));
                modelList.add(model);

                datasync_model.setApp_name(cursor.getString(cursor.getColumnIndex(CallAPI_Request_APIName)));
                datasync_model.setSync_Type("Request");
                datasync_model.setCallAPIRequest(model);

                DataSync_modelList.add(datasync_model);

            } while (cursor.moveToNext());
        }

        Log.d("CallAPI_RequestTable", DataSync_modelList.toString());

        return DataSync_modelList;

    }

    /* Retrive data from database */
    public List<GetOfflineData> getOfflineDataFromSubmitFormListTable(String org_id, String appName, String status, String user_id) {
        List<GetOfflineData> modelList = new ArrayList<GetOfflineData>();
        String query = "select * from " + FORM_SUBMIT_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "' and  " + PAGE_NAME + "='" + appName + "' and " + FORM_SUBMIT_TABLE_SYNC_STATUS + "='" + status + "' and " + USER_ID + " = '" + user_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                GetOfflineData model = new GetOfflineData();
                model.setSno(cursor.getString(0));
                model.setOrgId(cursor.getString(1));
                model.setApp_name(cursor.getString(10));
                model.setCreatedUserID(cursor.getString(3));
                model.setSubmittedUserID(cursor.getString(4));
                model.setDistributionID(cursor.getString(5));
                model.setIMEI(cursor.getString(6));
                model.setAppVersion(cursor.getString(9));

                model.setPrepared_json_string(cursor.getString(11));
                model.setPrepared_files_json_string(cursor.getString(12));
                model.setPrepared_json_string_subform(cursor.getString(13));
                model.setPrepared_files_json_string_subform(cursor.getString(14));
                model.setSync_status(cursor.getString(15));
                model.setOffline_json(cursor.getString(16));


                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveOfflineAppsList", modelList.toString());

        return modelList;

    }
    /*Insert Into E_Learning TableEnd*/

    public boolean deleteRowData(String sno) {
        Boolean status;
        SQLiteDatabase db = this.getWritableDatabase();
        status = db.delete(FORM_SUBMIT_TABLE, FORM_SUBMIT_TABLE_SERIAL_NO + " = '" + sno + "'", null) > 0;
        db.close();
        return status;
    }

    public boolean deleteCallAPIRequestRowData(String sno) {
        Boolean status;
        SQLiteDatabase db = this.getWritableDatabase();
        status = db.delete(CallAPI_Request_TABLE, CallAPI_Request_SERIAL_NO + " = '" + sno + "'", null) > 0;
        db.close();
        return status;
    }

    /*Insert Into E_Learning Table Start*/
    /* Insert into Form Submit Table*/
    public void insertIntoELearningTable(List<GetUserDistributionsResponse> getUserDistributionsList, String OrgId, String UserId) {
        // 1. get reference to writable DB

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (GetUserDistributionsResponse distributionsResponse : getUserDistributionsList) {
            values.put(E_LEARNING_SNO, distributionsResponse.getSNO());
            values.put(E_LEARNING_DISTRIBUTION_ID, distributionsResponse.getDistributionId());
            values.put(E_LEARNING_ORGANIZATION_ID, distributionsResponse.getOrganization_Id());
            values.put(E_LEARNING_TOPIC_NAME, distributionsResponse.getTopicName());
            values.put(E_LEARNING_IS_ASSESSMENT, distributionsResponse.getIs_Assessment());
            values.put(E_LEARNING_START_DATE, distributionsResponse.getStartDate());
            values.put(E_LEARNING_END_DATE, distributionsResponse.getEndDate());
            values.put(E_LEARNING_START_TIME, distributionsResponse.getStartTime());
            values.put(E_LEARNING_END_TIME, distributionsResponse.getEndTime());
            values.put(E_LEARNING_EXAM_DURATION, distributionsResponse.getExamDuration());
            values.put(E_LEARNING_NO_OF_ATTEMPTS, distributionsResponse.getNoOfAttempts());
            values.put(E_LEARNING_DISTRIBUTION_DATE, distributionsResponse.getDistributionDate());
            values.put(E_LEARNING_START_DISPLAY_DATE, distributionsResponse.getStartDisplayDate());
            values.put(E_LEARNING_END_DISPLAY_DATE, distributionsResponse.getEndDisplayDate());
            values.put(E_LEARNING_START_DISPLAY_TIME, distributionsResponse.getStartDisplayTime());
            values.put(E_LEARNING_END_DISPLAY_TIME, distributionsResponse.getEndDisplayTime());
            values.put(E_LEARNING_NO_OF_USER_ATTEMPTS, distributionsResponse.getNoOfUserAttempts());
            values.put(E_LEARNING_FILES_INFO, distributionsResponse.getFilesInfo());
            values.put(E_LEARNING_H_QUESTIONS, distributionsResponse.gethQuestions());
            values.put(E_LEARNING_M_QUESTIONS, distributionsResponse.getmQuestions());
            values.put(E_LEARNING_T_QUESTIONS, distributionsResponse.gettQuestions());
            values.put(E_LEARNING_L_QUESTIONS, distributionsResponse.getlQuestions());
            values.put(E_LEARNING_IS_COMPLEXITY, distributionsResponse.getIs_Compexcity());
            values.put(E_LEARNING_TOPIC_COVER_IMAGE, distributionsResponse.getTopicCoverImage());
            values.put(E_LEARNING_TOPIC_DESCRIPTION, distributionsResponse.getTopicDescription());
            values.put(E_LEARNING_TOPIC_UPDATION_DATE, distributionsResponse.getTaskUpdationDate());
            values.put(DB_ORG_ID, OrgId);
            values.put(USER_ID, UserId);
            values.put(POST_ID, distributionsResponse.getPostID());
//            values.put(E_LEARNING_QUESTIONS, distributionsResponse.getQuestions());

            long results = db.insert(E_LEARNING_TABLE, null, values);
            Log.d("E_LEARNING_TABLE : ", "After insert Records Inserted Count " + results);
        }

        db.close();

    }

    /*GetData From E_Learning TableStart*/
    public List<GetUserDistributionsResponse> getDataFromE_LearningTable(String OrgId, String UserId, String strPostId,String strUserTypeId) {
        List<GetUserDistributionsResponse> distributionsResponseList = new ArrayList<>();

        if (strPostId != null || strUserTypeId != null) {
            Gson gson = new Gson();
            if (strPostId == null || !strPostId.contains("_")) {
                strPostId = "0";
            }
            if (strUserTypeId == null || strUserTypeId.contains("_")) {
                strUserTypeId = "0";
            }
            String query = "";

            if (strPostId != null && strPostId.equalsIgnoreCase("0")) { // Get UserType Based Data
                query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + " = '" + OrgId + "' and " + USER_ID + " = '" + UserId + "' " +
                        "and ( " + USER_TYPE_IDS + " != '0' and " + USER_TYPE_IDS + " = '" + strUserTypeId +
                        "' or " + USER_TYPE_IDS + " LIKE '%" + strUserTypeId + "%' ) and " + E_LEARNING_DISTRIBUTION_ID + " != '' ORDER BY CAST( " + E_LEARNING_DISTRIBUTION_ID + " as integer ) DESC";
            } else if (strUserTypeId != null && strUserTypeId.equalsIgnoreCase("0")) { // Get PostId Based Data
                query = "select * from " + E_LEARNING_TABLE + " WHERE " + DB_ORG_ID + "='" + OrgId + "' and " +
                        USER_ID + " = '" + UserId + "' and ( " + POST_ID + " LIKE '%" + strPostId + "%' or " + POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + " ) and "
                        + E_LEARNING_DISTRIBUTION_ID + " != ''   ORDER BY CAST( " + E_LEARNING_DISTRIBUTION_ID + " as integer ) DESC";
            }else{  // Get PostId Based Data
                query = "select * from " + E_LEARNING_TABLE + " WHERE " + DB_ORG_ID + "='" + OrgId + "' and " +
                        USER_ID + " = '" + UserId + "' and ( " + POST_ID + " LIKE '%" + strPostId + "%' or " + POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + " ) and "
                        + E_LEARNING_DISTRIBUTION_ID + " != ''   ORDER BY CAST( " + E_LEARNING_DISTRIBUTION_ID + " as integer ) DESC";
            }

// New Version with posts
/*
        query = "select * from " + E_LEARNING_TABLE + " WHERE " + DB_ORG_ID + "='" + OrgId + "' and " +
                USER_ID + " = '" + UserId + "' and (" +
                POST_ID + " LIKE '%" + strPostId + "%' or " + POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + ") and "
                + E_LEARNING_DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + E_LEARNING_DISTRIBUTION_ID + " as integer) DESC";
*/


        Log.d(TAG, "getDataFromE_LearningTable: " + query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                GetUserDistributionsResponse model = new GetUserDistributionsResponse();
                model.setSNO(cursor.getString(0));
                model.setDistributionId(cursor.getString(1));
                model.setOrganization_Id(cursor.getString(2));
                model.setTopicName(cursor.getString(3));
                model.setIs_Assessment(cursor.getString(4));
                model.setStartDate(cursor.getString(5));
                model.setEndDate(cursor.getString(6));
                model.setStartTime(cursor.getString(7));
                model.setEndTime(cursor.getString(8));
                model.setExamDuration(cursor.getString(9));
                model.setNoOfAttempts(cursor.getString(10));
                model.setDistributionDate(cursor.getString(11));
                model.setStartDisplayDate(cursor.getString(12));
                model.setEndDisplayDate(cursor.getString(13));
                model.setStartDisplayTime(cursor.getString(14));
                model.setEndDisplayTime(cursor.getString(15));
                model.setNoOfUserAttempts(cursor.getString(16));
                model.setFilesInfo(cursor.getString(17));
                model.sethQuestions(cursor.getString(18));
                model.setmQuestions(cursor.getString(19));
                model.settQuestions(cursor.getString(20));
                model.setlQuestions(cursor.getString(21));
                model.setIs_Compexcity(cursor.getString(22));
                model.setPostID(cursor.getString(25));
                model.setTopicCoverImage(cursor.getString(26));
                model.setTopicDescription(cursor.getString(27));
                model.setTaskUpdationDate(cursor.getString(28));
//                model.setQuestions(cursor.getString(18));
//                model.setAssessmentInfo(cursor.getString(20));


                distributionsResponseList.add(model);
            } while (cursor.moveToNext());
        }
    }

        Log.d("RetrieveELearningData", distributionsResponseList.toString());

        return distributionsResponseList;
    }
    public List<GetUserDistributionsResponse> getDataFromE_LearningTableOld(String OrgId, String UserId, String strPostId) {
        List<GetUserDistributionsResponse> distributionsResponseList = new ArrayList<>();
// New Version with posts
        String query = "select * from " + E_LEARNING_TABLE + " WHERE " + DB_ORG_ID + "='" + OrgId + "' and " +
                USER_ID + " = '" + UserId + "' and (" +
                POST_ID + " LIKE '%" + strPostId + "%' or " + POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + ") and "
                + E_LEARNING_DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + E_LEARNING_DISTRIBUTION_ID + " as integer) DESC";

        //old version with out posts
//        String query = "select * from " + E_LEARNING_TABLE + " WHERE " + DB_ORG_ID + "='" + OrgId + "' and " + USER_ID + " = '" + UserId + "' and " + E_LEARNING_DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + E_LEARNING_DISTRIBUTION_ID + " as integer) DESC";

        Log.d(TAG, "getDataFromE_LearningTable: " + query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                GetUserDistributionsResponse model = new GetUserDistributionsResponse();
                model.setSNO(cursor.getString(0));
                model.setDistributionId(cursor.getString(1));
                model.setOrganization_Id(cursor.getString(2));
                model.setTopicName(cursor.getString(3));
                model.setIs_Assessment(cursor.getString(4));
                model.setStartDate(cursor.getString(5));
                model.setEndDate(cursor.getString(6));
                model.setStartTime(cursor.getString(7));
                model.setEndTime(cursor.getString(8));
                model.setExamDuration(cursor.getString(9));
                model.setNoOfAttempts(cursor.getString(10));
                model.setDistributionDate(cursor.getString(11));
                model.setStartDisplayDate(cursor.getString(12));
                model.setEndDisplayDate(cursor.getString(13));
                model.setStartDisplayTime(cursor.getString(14));
                model.setEndDisplayTime(cursor.getString(15));
                model.setNoOfUserAttempts(cursor.getString(16));
                model.setFilesInfo(cursor.getString(17));
                model.sethQuestions(cursor.getString(18));
                model.setmQuestions(cursor.getString(19));
                model.settQuestions(cursor.getString(20));
                model.setlQuestions(cursor.getString(21));
                model.setIs_Compexcity(cursor.getString(22));
                model.setPostID(cursor.getString(25));
                model.setTopicCoverImage(cursor.getString(26));
                model.setTopicDescription(cursor.getString(27));
                model.setTaskUpdationDate(cursor.getString(28));
//                model.setQuestions(cursor.getString(18));
//                model.setAssessmentInfo(cursor.getString(20));


                distributionsResponseList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveELearningData", distributionsResponseList.toString());

        return distributionsResponseList;
    }

    /*GetData From E_Learning TableEnd*/
    /* Retrive  getDataFromELearningFilesTimeSpent*/
    public List<InsertUserFileVisitsModel> getDataFromELearningFilesTimeSpent(String org_id, String user_id) {
        List<InsertUserFileVisitsModel> modelList = new ArrayList<InsertUserFileVisitsModel>();
//        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
        String query = "select * from " + E_LEARNING_TIME_SPENT_OFFLINE_TABLE + " WHERE " +
                E_LEARNING_TIME_SPENT_OFFLINE_DB_NAME + "='" + org_id + "' " +
                "and " + E_LEARNING_TIME_SPENT_OFFLINE_USER_ID + " = '" + user_id + "' " +
                "and " + E_LEARNING_TIME_SPENT_OFFLINE_STATUS + " = '" + "NotUploaded" + "' " +
                "and " + E_LEARNING_TIME_SPENT_OFFLINE_DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + E_LEARNING_TIME_SPENT_OFFLINE_DISTRIBUTION_ID + " as integer) DESC";
        Log.d(TAG, "GetDataFromFilesTimeSpentTable: " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                InsertUserFileVisitsModel model = new InsertUserFileVisitsModel();
                model.setUserID(cursor.getString(1));
                model.setDBNAME(cursor.getString(2));
                model.setParent_Id(cursor.getString(3));
                model.setSelected_Node_Id(cursor.getString(4));
                model.setCategoryFileID(cursor.getString(5));
                model.setDistributionId(cursor.getString(6));
                model.setDeviceID(cursor.getString(7));
                model.setMobileDate(cursor.getString(8));
                model.setStartTime(cursor.getString(9));
                model.setEndTime(cursor.getString(10));
                model.setGPS(cursor.getString(11));
                model.setIs_Visited_Through(cursor.getString(12));
                model.setFilesOffLineStatus(cursor.getString(13));

                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", modelList.toString());

        return modelList;

    }

    /* Retrive  getDataFromELearningFilesTimeSpent*/
    public List<TaskCommentDetails> getDataFromCommentsFailed(String org_id, String user_id) {
        List<TaskCommentDetails> modelList = new ArrayList<TaskCommentDetails>();
//        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "' and " + USER_ID + " = '" + user_id + "' and " + DISTRIBUTION_ID + " != ''   ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";
        String query = "select * from " + COMMENTS_FAILED_TABLE;
        Log.d(TAG, "GetDataFromCommentsFailed: " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                TaskCommentDetails model = new TaskCommentDetails();
                model.setTaskId(cursor.getString(0));
                model.setOrgId(cursor.getString(1));
                model.setTaskComments(cursor.getString(2));
                model.setTaskStatusId(cursor.getString(3));
                model.setUserId(cursor.getString(4));
                model.setPostId(cursor.getString(5));
                model.setLocationCode(cursor.getString(6));
                model.setDepartmentId(cursor.getString(7));
                model.setDesignationId(cursor.getString(8));
                model.setIsSelfComment(cursor.getString(9));
                model.setDeviceId(cursor.getString(10));
                model.setVersionNo(cursor.getString(11));
                model.setMobileDate(cursor.getString(12));

                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", modelList.toString());

        return modelList;

    }

    public void deleteELearningTimeSpentRow(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int numDeleteRow = db.delete(E_LEARNING_TIME_SPENT_OFFLINE_TABLE, E_LEARNING_TIME_SPENT_OFFLINE_CATEGORY_FILE_ID + " = '" + name + "'", null);
        db.close();
        Log.d(TAG, "deleteELearningTimeSpentRow: " + numDeleteRow + "-" + name);
    }

    /* Insert into Notification Table*/
    public long insertIntoNotificationTable(String messageId, String senderId, String senderName, String groupId, String groupIcon, String groupName, String sessionId, String sessionName, String title, String message, String messageType, String fileName, String filePath, String timestamp, String status, String unreadCount, String userId, String postId) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(MESSAGE_ID, messageId);
        values.put(SENDER_ID, senderId);
        values.put(SENDER_NAME, senderName);
        values.put(GROUP_ID, groupId);
        values.put(GROUP_ICON, groupIcon);
        values.put(GROUP_NAME, groupName);
        values.put(SESSION_ID, sessionId);
        values.put(SESSION_NAME, sessionName);
        values.put(TITLE, title);
        values.put(MESSAGE, message);
        values.put(MESSAGE_TYPE, messageType);
        values.put(FILE_NAME, fileName);
        values.put(FILE_PATH, filePath);
        values.put(TIME_STAMP, timestamp);
        values.put(UNREAD_COUNT, unreadCount);
        values.put(STATUS, status);
        values.put(USER_ID, userId);
        values.put(POST_ID, postId);

        long results = db.insert(NOTIFICATION_TABLE, null, values);

        db.close();

        return results;

    }

    /* Insert into All Notifications Table*/
    public long insertIntoAllNotificationsTable(String messageId, String title, String message, String messageType, String fileName, String filePath, String timestamp, String status, String unreadCount, String userId, String postId, String orgId) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(MESSAGE_ID, messageId);
        values.put(TITLE, title);
        values.put(MESSAGE, message);
        values.put(MESSAGE_TYPE, messageType);
        values.put(FILE_NAME, fileName);
        values.put(FILE_PATH, filePath);
        values.put(TIME_STAMP, timestamp);
        values.put(UNREAD_COUNT, unreadCount);
        values.put(STATUS, status);
        values.put(USER_ID, userId);
        values.put(POST_ID, postId);
        values.put(DB_ORG_ID, orgId);

        long results = db.insert(ALL_NOTIFICATIONS_TABLE, null, values);

        db.close();

        return results;

    }

    /* Insert into Notification Table*/
    public long insertIntoNotificationTable(Map<String, String> jsondata, String userId, String postId, String status, String unreadCount) {
        long results = 0;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        try {
//            String unreadCount = "0";
//            String messageId = jsondata.getString("messageId");
            String senderId = jsondata.get("senderId");
            String senderName = jsondata.get("senderName");
            String groupId = jsondata.get("groupId");
            String groupIcon = jsondata.get("groupIcon");
            String groupName = jsondata.get("groupName");
            String sessionId = jsondata.get("sessionId");
            String sessionName = jsondata.get("sessionName");
            String title = jsondata.get("title");
            String message = jsondata.get("message");
            String messageType = jsondata.get("messageType");
            String fileName = jsondata.get("fileName");
            String filePath = jsondata.get("filePath");
            String timestamp = jsondata.get("timestamp");


//            values.put(MESSAGE_ID, messageId);
            values.put(SENDER_ID, senderId);
            values.put(SENDER_NAME, senderName);
            values.put(GROUP_ID, groupId);
            values.put(GROUP_ICON, groupIcon);
            values.put(GROUP_NAME, groupName);
            values.put(SESSION_ID, sessionId);
            values.put(SESSION_NAME, sessionName);
            values.put(TITLE, title);
            values.put(MESSAGE, message);
            values.put(MESSAGE_TYPE, messageType);
            values.put(FILE_NAME, fileName);
            values.put(FILE_PATH, filePath);
            values.put(TIME_STAMP, timestamp);
            values.put(UNREAD_COUNT, unreadCount);
            values.put(STATUS, status);
            values.put(USER_ID, userId);
            values.put(POST_ID, postId);

            results = db.insert(NOTIFICATION_TABLE, null, values);

            db.close();
        } catch (Exception e) {
            Log.d(TAG, "onMessageReceived: " + e);

        }

        return results;

    }

    /* Retrive  Conversation list data from database */
    public List<Notification> getConversationListForPostId(String user_id, String post_id) {

        List<Notification> notificationList = new ArrayList<>();
//        String query = "SELECT t.sender_id,t.sender_name,t.group_id,t.group_icon,t.group_name,t.session_id,t.session_name, t.title, t.message, t.message_type,t.file_name,t.file_path, t.time_stamp,(SELECT COUNT('x') FROM notification_table ct WHERE ct.session_id = t.session_id and unread_count = '1') as rec_count FROM notification_table t WHERE " + USER_ID + " = '" + user_id + "' and (" + POST_ID + " = '" + post_id + "' or " + POST_ID + " is null) group by t.session_id order by time_stamp";
//        String query = "SELECT t.sender_id,t.sender_name,t.group_id,t.group_icon,t.group_name,t.session_id,t.session_name, t.title, t.message, t.message_type,t.file_name,t.file_path, t.time_stamp,(SELECT COUNT('x') FROM notification_table ct WHERE ct.session_name = t.session_name and unread_count = '1') as rec_count FROM notification_table t WHERE " + USER_ID + " = '" + user_id + "' and " + POST_ID + " = '" + post_id + "' group by t.session_name order by time_stamp";
//        String query = "SELECT t.sender_id,t.sender_name,t.group_id,t.group_icon,t.group_name,t.session_id,t.session_name, t.title, t.message, t.message_type,t.file_name,t.file_path, t.time_stamp,(SELECT COUNT('x') FROM notification_table ct WHERE ct.session_name = t.session_name and unread_count = '1') as rec_count FROM notification_table t group by t.session_name order by time_stamp";
//        String query = "SELECT t.sender_id,t.sender_name,t.group_id,t.group_icon,t.group_name,t.session_id,t.session_name, t.title, t.message, t.message_type,t.file_name,t.file_path, max(t.time_stamp),(SELECT COUNT('x') FROM notification_table ct WHERE ct.session_id = t.session_id  and (" + POST_ID + " = '" + post_id + "') and unread_count = '1') as rec_count FROM notification_table t WHERE " + USER_ID + " = '" + user_id + "' and (" + POST_ID + " = '" + post_id + "') group by t.session_id order by t.time_stamp";
//        String query = "SELECT t.sender_id,t.sender_name,t.group_id,t.group_icon,t.group_name,t.session_id,t.session_name, t.title, t.message, t.message_type,t.file_name,t.file_path, max(t.time_stamp),(SELECT COUNT('x') FROM notification_table ct WHERE ct.session_id = t.session_id  and (" + POST_ID + " = '" + post_id + "' or " + POST_ID + " = '' or  " + POST_ID + " is null) and unread_count = '1') as rec_count FROM notification_table t WHERE " + USER_ID + " = '" + user_id + "' and " + DB_ORG_ID + "='" + org_id + "' and (" + POST_ID + " = '" + post_id + "' or " + POST_ID + " = '' or " + POST_ID + " is null) group by t.session_id order by t.time_stamp";
        String query = "SELECT t.sender_id,t.sender_name,t.group_id,t.group_icon,t.group_name,t.session_id,t.session_name, t.title, t.message, t.message_type,t.file_name,t.file_path, max(t.time_stamp),(SELECT COUNT('x') FROM notification_table ct WHERE ct.session_id = t.session_id  and (" + POST_ID + " = '" + post_id + "') and unread_count = '1') as rec_count FROM notification_table t WHERE " + USER_ID + " = '" + user_id + "' and (" + POST_ID + " = '" + post_id + "') group by t.session_id order by t.time_stamp";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {
            do {
                Notification model = new Notification();

                model.setSenderId(cursor.getString(0));
                model.setSenderName(cursor.getString(1));
                model.setGroupId(cursor.getString(2));
                model.setGroupIcon(cursor.getString(3));
                model.setGroupName(cursor.getString(4));
                model.setSessionId(cursor.getString(5));
                model.setSessionName(cursor.getString(6));
                model.setTitle(cursor.getString(7));
                model.setMessage(cursor.getString(8));
                model.setMessageType(cursor.getString(9));
                model.setFileName(cursor.getString(10));
                model.setFilePath(cursor.getString(11));
                model.setTimeStamp(cursor.getString(12));
                model.setUnreadCount(cursor.getString(13));

                notificationList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", notificationList.toString());

        return notificationList;

    }

    /* Retrive  Conversation list data from database */
    public List<Notification> getConversationList(String user_id, String post_id, String org_id) {

        List<Notification> notificationList = new ArrayList<>();
//        String query = "SELECT t.sender_id,t.sender_name,t.group_id,t.group_icon,t.group_name,t.session_id,t.session_name, t.title, t.message, t.message_type,t.file_name,t.file_path, max(t.time_stamp),(SELECT COUNT('x') FROM notification_table ct WHERE ct.session_id = t.session_id  and unread_count = '1') as rec_count FROM notification_table t WHERE " + USER_ID + " = '" + user_id + "' and " + DB_ORG_ID + "='" + org_id + "'  group by t.session_id order by t.time_stamp";
        String query = "SELECT t.sender_id,t.sender_name,t.group_id,t.group_icon,t.group_name,t.session_id,t.session_name, t.title, t.message, t.message_type,t.file_name,t.file_path, max(t.time_stamp),(SELECT COUNT('x') FROM notification_table ct WHERE ct.session_id = t.session_id  and unread_count = '1') as rec_count FROM notification_table t WHERE " + USER_ID + " = '" + user_id + "' and  (" + POST_ID + " = '" + post_id + "') group by t.session_id order by t.time_stamp";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Notification model = new Notification();

                model.setSenderId(cursor.getString(0));
                model.setSenderName(cursor.getString(1));
                model.setGroupId(cursor.getString(2));
                model.setGroupIcon(cursor.getString(3));
                model.setGroupName(cursor.getString(4));
                model.setSessionId(cursor.getString(5));
                model.setSessionName(cursor.getString(6));
                model.setTitle(cursor.getString(7));
                model.setMessage(cursor.getString(8));
                model.setMessageType(cursor.getString(9));
                model.setFileName(cursor.getString(10));
                model.setFilePath(cursor.getString(11));
                model.setTimeStamp(cursor.getString(12));
                model.setUnreadCount(cursor.getString(13));

                notificationList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", notificationList.toString());

        return notificationList;

    }

    public List<Notification> getConversationListForNoPost(String user_id, String org_id) {

        List<Notification> notificationList = new ArrayList<>();
//        String query = "SELECT t.sender_id,t.sender_name,t.group_id,t.group_icon,t.group_name,t.session_id,t.session_name, t.title, t.message, t.message_type,t.file_name,t.file_path, t.time_stamp,(SELECT COUNT('x') FROM notification_table ct WHERE ct.session_id = t.session_id and unread_count = '1') as rec_count FROM notification_table t WHERE " + USER_ID + " = '" + user_id + "' and (" + POST_ID + " = '" + post_id + "' or " + POST_ID + " is null) group by t.session_id order by time_stamp";
//        String query = "SELECT t.sender_id,t.sender_name,t.group_id,t.group_icon,t.group_name,t.session_id,t.session_name, t.title, t.message, t.message_type,t.file_name,t.file_path, t.time_stamp,(SELECT COUNT('x') FROM notification_table ct WHERE ct.session_name = t.session_name and unread_count = '1') as rec_count FROM notification_table t WHERE " + USER_ID + " = '" + user_id + "' and " + POST_ID + " = '" + post_id + "' group by t.session_name order by time_stamp";
//        String query = "SELECT t.sender_id,t.sender_name,t.group_id,t.group_icon,t.group_name,t.session_id,t.session_name, t.title, t.message, t.message_type,t.file_name,t.file_path, t.time_stamp,(SELECT COUNT('x') FROM notification_table ct WHERE ct.session_name = t.session_name and unread_count = '1') as rec_count FROM notification_table t group by t.session_name order by time_stamp";
        String query = "SELECT t.sender_id,t.sender_name,t.group_id,t.group_icon,t.group_name,t.session_id,t.session_name, t.title, t.message, t.message_type,t.file_name,t.file_path, max(t.time_stamp),(SELECT COUNT('x') FROM notification_table ct WHERE ct.session_id = t.session_id  and (" + POST_ID + " = '' or " + POST_ID + " is null) and unread_count = '1') as rec_count FROM notification_table t WHERE " + USER_ID + " = '" + user_id + "' and " + DB_ORG_ID + "='" + org_id + "' and (" + POST_ID + " = '' or " + POST_ID + " is null) group by t.session_id order by t.time_stamp";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Notification model = new Notification();

                model.setSenderId(cursor.getString(0));
                model.setSenderName(cursor.getString(1));
                model.setGroupId(cursor.getString(2));
                model.setGroupIcon(cursor.getString(3));
                model.setGroupName(cursor.getString(4));
                model.setSessionId(cursor.getString(5));
                model.setSessionName(cursor.getString(6));
                model.setTitle(cursor.getString(7));
                model.setMessage(cursor.getString(8));
                model.setMessageType(cursor.getString(9));
                model.setFileName(cursor.getString(10));
                model.setFilePath(cursor.getString(11));
                model.setTimeStamp(cursor.getString(12));
                model.setUnreadCount(cursor.getString(13));

                notificationList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", notificationList.toString());

        return notificationList;

    }

    /* Retrive  Session chat messages list data from database */
    public List<ChatDetails> getSessionChatMessagesList(String group_name, String session_id, String user_id, String post_id) {

        List<ChatDetails> notificationList = new ArrayList<>();


      /*  String query = "select * from " + NOTIFICATION_TABLE + " WHERE " +
                GROUP_NAME + "='" + group_name + "' " +
                "and " + SESSION_ID + " = '" + session_id + "' and " + USER_ID + " = '" + user_id + "' and (" + POST_ID + " = '" + post_id + "' or  " + POST_ID + " is null)  group by message order by time_stamp";

*/
        String query = "select * from " + NOTIFICATION_TABLE + " WHERE " +
                GROUP_NAME + "='" + group_name + "' " +
                "and " + SESSION_ID + " = '" + session_id + "' and " + USER_ID + " = '" + user_id + "' and (" + POST_ID + " = '" + post_id + "' or  " + POST_ID + " is null) order by time_stamp";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ChatDetails model = new ChatDetails();

                model.setSenderID(cursor.getString(2));
                model.setSenderName(cursor.getString(3));
                model.setText(cursor.getString(10));
                model.setMsgType(cursor.getString(11));
                model.setCreatedDate(cursor.getString(14));
                model.setFileName(cursor.getString(12));
                model.setFilePath(cursor.getString(13));
                model.setStatus(cursor.getString(16));

                notificationList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", notificationList.toString());

        return notificationList;

    }

    /* Retrive  Session chat messages list data from database */
    public List<ChatDetails> getNewMessage(String group_id, String session_id, String user_id, String post_id, String user_type_id) {

        List<ChatDetails> notificationList = new ArrayList<>();
        String query = "select * from " + NOTIFICATION_TABLE + " WHERE " +
                GROUP_ID + "='" + group_id + "'" +
                " and " + SESSION_ID + " = '" + session_id + "' and " + USER_ID + " = '" + user_id + "' and  (" + POST_ID + " = '" + post_id + "' or " + POST_ID + " = '" + user_type_id + "')  order by " + TIME_STAMP + " DESC LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ChatDetails model = new ChatDetails();
                model.setSenderID(cursor.getString(2));
                model.setSenderName(cursor.getString(3));
                model.setText(cursor.getString(10));
                model.setMsgType(cursor.getString(11));
                model.setCreatedDate(cursor.getString(14));
                model.setFileName(cursor.getString(12));
                model.setFilePath(cursor.getString(13));
                model.setStatus(cursor.getString(18));

                notificationList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", notificationList.toString());

        return notificationList;

    }

    public void updateUnreadCount(String groupName, String sessionId, String user_id, String post_id) {

        int msg = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UNREAD_COUNT, "0");
//        msg = db.update(NOTIFICATION_TABLE, cv, GROUP_NAME + " = '" + groupName + "' and " + SESSION_ID + " = '" + sessionId + "' and " + USER_ID + " = '" + user_id + "' and " + DB_ORG_ID + "='" + org_id + "' and (" + POST_ID + " = '" + post_id + "' or " + POST_ID + " = '' or  " + POST_ID + " is null)", null);
        msg = db.update(NOTIFICATION_TABLE, cv, GROUP_NAME + " = '" + groupName + "' and " + SESSION_ID + " = '" + sessionId + "' and " + USER_ID + " = '" + user_id + "'", null);
        db.close();

    }

    /* Insert into Notification Table*/
    public long insertListIntoNotificationTable(List<Notification> notificationList, String userId, String orgId) {
        long results = 0;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        List<String> postsList = new ArrayList<>();
        for (Notification notification : notificationList) {
            postsList = getPosts(notification.getGroupId(), userId);
            if (postsList == null || postsList.size() == 0) {
                values.put(SENDER_ID, notification.getSenderId());
                values.put(SENDER_NAME, notification.getSenderName());
                values.put(GROUP_ID, notification.getGroupId());
                values.put(GROUP_ICON, notification.getGroupIcon());
                values.put(GROUP_NAME, notification.getGroupName());
                values.put(SESSION_ID, notification.getSessionId());
                values.put(SESSION_NAME, notification.getSessionName());
                values.put(TITLE, notification.getTitle());
                values.put(MESSAGE, notification.getMessage());
                values.put(MESSAGE_TYPE, notification.getMessageType());
                values.put(FILE_NAME, notification.getFileName());
                values.put(FILE_PATH, notification.getFilePath());
                values.put(TIME_STAMP, notification.getTimeStamp());
                values.put(UNREAD_COUNT, notification.getUnreadCount());
                values.put(STATUS, notification.getIsActive());
                values.put(USER_ID, userId);
                values.put(POST_ID, "");
                values.put(DB_ORG_ID, orgId);
                results = db.insert(NOTIFICATION_TABLE, null, values);
            } else {
                for (String postId : postsList) {
                    values.put(SENDER_ID, notification.getSenderId());
                    values.put(SENDER_NAME, notification.getSenderName());
                    values.put(GROUP_ID, notification.getGroupId());
                    values.put(GROUP_ICON, notification.getGroupIcon());
                    values.put(GROUP_NAME, notification.getGroupName());
                    values.put(SESSION_ID, notification.getSessionId());
                    values.put(SESSION_NAME, notification.getSessionName());
                    values.put(TITLE, notification.getTitle());
                    values.put(MESSAGE, notification.getMessage());
                    values.put(MESSAGE_TYPE, notification.getMessageType());
                    values.put(FILE_NAME, notification.getFileName());
                    values.put(FILE_PATH, notification.getFilePath());
                    values.put(TIME_STAMP, notification.getTimeStamp());
                    values.put(UNREAD_COUNT, notification.getUnreadCount());
                    values.put(STATUS, notification.getIsActive());
                    values.put(USER_ID, userId);
                    values.put(POST_ID, postId);
                    values.put(DB_ORG_ID, orgId);

                    results = db.insert(NOTIFICATION_TABLE, null, values);
                }
            }
        }

        db.close();

        return results;

    }

    /* Insert into Group Table*/
    public long insertListIntoGroupTable(List<Group> groupList, String userId, String orgId) {
        long results = 0;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        for (Group group : groupList) {

            values.put(GROUP_ID, group.getGroupID());
            values.put(GROUP_ICON, group.getGroupIcon());
            values.put(GROUP_NAME, group.getGroupName());
            values.put(DEPENDENT_ID, group.getDependentID());
            values.put(USER_TYPE, group.getUserType());
            values.put(READ, group.getRead());
            values.put(WRITE, group.getWrite());
            values.put(USER_ID, userId);
            values.put(DB_ORG_ID, orgId);

            results = db.insert(GROUP_TABLE, null, values);
        }
        db.close();

        return results;

    }

    public void deleteGroupTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GROUP_TABLE, "", null);
        db.close();
    }

    /* Retrive  Session chat messages list data from database */
    public List<String> getPosts(String group_id, String user_id) {

        List<String> postsList = new ArrayList<>();
        String query = "select * from " + GROUP_TABLE + " WHERE " +
                GROUP_ID + "='" + group_id + "' " + "and " + USER_ID + " = '" + user_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
//                if (cursor.getString(4) != null) {
                postsList.add(cursor.getString(5));
//                }

            } while (cursor.moveToNext());
        }

        return postsList;

    }

    public String checkWritePermission(String group_id, String user_id, String org_id) {

        String writePerm = null;
        String query = "select " + WRITE + " from " + GROUP_TABLE + " WHERE " +
                GROUP_ID + "='" + group_id + "' " + "and " + USER_ID + " = '" + user_id + "' and " + DB_ORG_ID + " = '" + org_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                writePerm = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        db.close();
        return writePerm;

    }

    /* Retrive  Session chat messages list data from database */
    public List<Group> getGroupsList(String user_id, String post_id, String user_type_id, String user_type,
                                     String write, String org_id) {

        List<Group> groupList = new ArrayList<>();
        String query = "select * from " + GROUP_TABLE + " WHERE " + USER_ID + " = '" + user_id + "' " +
                "and " + DB_ORG_ID + " = '" + org_id + "' and  (" + DEPENDENT_ID + " = '" + post_id + "' or " + DEPENDENT_ID + " = '" + user_type_id + "') and " + WRITE + " = '" + write + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Group model = new Group();

                model.setGroupID(cursor.getString(1));
                model.setGroupName(cursor.getString(2));
                model.setGroupIcon(cursor.getString(3));
                model.setGroupType(cursor.getString(4));

                groupList.add(model);
            } while (cursor.moveToNext());
        }


        return groupList;

    }

    public void deleteUserNotifications(String user_id, String post_id, String org_id) {
        SQLiteDatabase db = this.getWritableDatabase();
//        long res = db.delete(NOTIFICATION_TABLE, "" + USER_ID + " = '" + user_id + "' and " + DB_ORG_ID + " = '" + org_id + "'and(" + POST_ID + " = '" + post_id + "')", null);
        long res = db.delete(NOTIFICATION_TABLE, "" + USER_ID + " = '" + user_id + "' and " + DB_ORG_ID + " = '" + org_id + "'", null);
        db.close();
    }

    public void deleteDefaultUserNotifications(String user_id, String post_id, String org_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.delete(NOTIFICATION_TABLE, "" + USER_ID + " = '" + user_id + "' and " + DB_ORG_ID + " = '" + org_id + "' and (" + POST_ID + " = '' or  " + POST_ID + " is null)", null);
        db.close();
    }

    public void deleteUserNotificationsSystem(String user_id, String org_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.delete(NOTIFICATION_TABLE, "" + USER_ID + " = '" + user_id + "' and " + DB_ORG_ID + " = '" + org_id + "'", null);
        db.close();
    }

    /* Retrive  Session chat messages list data from database */
    public boolean checkGroupExists(String group_id, String org_id) {


        String query = "select * from " + GROUP_TABLE + " WHERE " + GROUP_ID + " = '" + group_id + "' and " + DB_ORG_ID + " = '" + org_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }

    public void updateNoOfUserAttemptsInELearningTable(int noOfUserAttemptsCount, String distribution_id, String user_id) {
        int msg = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(E_LEARNING_NO_OF_ATTEMPTS, noOfUserAttemptsCount);
        msg = db.update(E_LEARNING_TABLE, cv, E_LEARNING_DISTRIBUTION_ID + " = '" + distribution_id + "'  and " + USER_ID + " = '" + user_id + "'", null);
        db.close();
    }

    /*Retrive ELearning Single data from apps List*/
    public GetUserDistributionsResponse getELearningData(String distributionId, String org_id, String user_id) {
        GetUserDistributionsResponse model = new GetUserDistributionsResponse();
        String query = "select * from " + E_LEARNING_TABLE + " WHERE " + E_LEARNING_DISTRIBUTION_ID + "='" + distributionId + "' and " + DB_ORG_ID + "='" + org_id + "' and " + USER_ID + " = '" + user_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    model.setSNO(cursor.getString(0));
                    model.setDistributionId(cursor.getString(1));
                    model.setOrganization_Id(cursor.getString(2));
                    model.setTopicName(cursor.getString(3));
                    model.setIs_Assessment(cursor.getString(4));
                    model.setStartDate(cursor.getString(5));
                    model.setEndDate(cursor.getString(6));
                    model.setStartTime(cursor.getString(7));
                    model.setEndTime(cursor.getString(8));
                    model.setExamDuration(cursor.getString(9));
                    model.setNoOfAttempts(cursor.getString(10));
                    model.setDistributionDate(cursor.getString(11));
                    model.setStartDisplayDate(cursor.getString(12));
                    model.setEndDisplayDate(cursor.getString(13));
                    model.setStartDisplayTime(cursor.getString(14));
                    model.setEndDisplayTime(cursor.getString(15));
                    model.setNoOfUserAttempts(cursor.getString(16));
                    model.setFilesInfo(cursor.getString(17));
                    model.sethQuestions(cursor.getString(18));
                    model.setmQuestions(cursor.getString(19));
                    model.settQuestions(cursor.getString(20));
                    model.setlQuestions(cursor.getString(21));
                    model.setIs_Compexcity(cursor.getString(22));
                    model.setPostID(cursor.getString(25));
                    model.setTopicCoverImage(cursor.getString(26));
                    model.setTopicDescription(cursor.getString(27));
                    model.setTaskUpdationDate(cursor.getString(28));
                } while (cursor.moveToNext());
            }
            Log.d("RetrieveAppsList", model.toString());

            return model;
        } else {
            return null;
        }

    }

    public void deleteAllRowsInTable(String TABLE_NAME) {

        SQLiteDatabase db = this.getWritableDatabase();

//        msg = db.delete(DEPLOYMENT_TABLE, DEPLOYMENT_TASK_ID + "=?", new String[]{taskId});
//        msg = db.delete(DEPLOYMENT_TABLE, DEPLOYMENT_SNO + "=?", new String[]{sNO});

        db.execSQL("delete from " + TABLE_NAME);
        db.close();
    }

    public boolean doesTableExist(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public boolean isTableEmpty(String table) {

        boolean isTableEmpty;
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + table;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        //leave
        //populate table
        isTableEmpty = icount <= 0;
        db.close();
        return isTableEmpty;
    }

    public void deleteRowsByPostIdInTable(String TABLE_NAME, String postId) {

        SQLiteDatabase db = this.getWritableDatabase();

//        msg = db.delete(DEPLOYMENT_TABLE, DEPLOYMENT_TASK_ID + "=?", new String[]{taskId});
//        msg = db.delete(DEPLOYMENT_TABLE, DEPLOYMENT_SNO + "=?", new String[]{sNO});

//       long msg = db.delete(TABLE_NAME, OUT_TASK_POST_ID +" = '"+postId+"'" , null);
//        Log.d(TAG, "deleteRowsByPostIdInTable: "+msg);
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + OUT_TASK_POST_ID + " = '" + postId + "'");
        db.close();
    }

    /*Update intask singleUserSenario*/
    public int UpdateInTaskTableSingleUserInfo(String taskId, String orgID, String user_id, String postId, String strSingleUserInfo) {

        int msg = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(IN_TASK_SINGLE_USER_INFO, strSingleUserInfo);

        msg = db.update(IN_TASK_TABLE, values, IN_TASK_TASK_ID + " = '" + taskId + "'  and "
                + IN_TASK_USER_ID + " = '" + user_id + "' and "
                + IN_TASK_POST_ID + " = '" + postId + "' and "
                + IN_TASK_ORG_ID + " = '" + orgID + "'", null);
        Log.d(TAG, "UpdateInTaskTableSingleUserInfo: " + msg);

        db.close();

        return msg;

    }

    /* Retrive  Session chat messages list data from database */
    public List<Notification> getNotificationsList(String user_id, String post_id, String org_id, boolean DefultAPK) {

        List<Notification> notificationList = new ArrayList<>();
        String query;
        if (DefultAPK) {
            query = "select * from " + ALL_NOTIFICATIONS_TABLE + " WHERE " + USER_ID + " = '" + user_id + "' and " + DB_ORG_ID + "='" + org_id + "' and (" + POST_ID + " = '" + post_id + "' or " + POST_ID + " = '' or  " + POST_ID + " is null)  order by time_stamp";
        } else {
            query = "select * from " + ALL_NOTIFICATIONS_TABLE + " WHERE " + DB_ORG_ID + "='" + org_id + "' order by time_stamp";

        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Notification model = new Notification();
                model.setTitle(cursor.getString(2));
                model.setMessage(cursor.getString(3));
                model.setTimeStamp(cursor.getString(7));
                model.setStatus(cursor.getString(12));
                notificationList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("Retrievenotifi", notificationList.toString());

        return notificationList;

    }

    /* Insert into Form Submit Table*/
    public long insertIntoFormSubmitTable(String orgID, String pageName, JSONObject offlineJson) {
        // 1. get reference to writable DB
        Gson gson = new Gson();
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DB_ORG_ID, orgID);
        values.put(PAGE_NAME, pageName);
        values.put(FORM_SUBMIT_TABLE_OFFLINE_JSON, String.valueOf(offlineJson));

        long results = db.insert(FORM_SUBMIT_TABLE, null, values);


        db.close();
        return results;
    }

    public String CreateTableString(String tablename, String[] columnnames) {
        String CREATE_TABLE = "CREATE TABLE " + tablename + " (";
        for (String data : columnnames) {
            CREATE_TABLE = CREATE_TABLE + data + " TEXT,";
        }
        CREATE_TABLE = CREATE_TABLE.substring(0, CREATE_TABLE.length() - 1)
                + ")";
        System.out.println("CREATE_TABLE :" + CREATE_TABLE);
        return CREATE_TABLE;
    }

    public String CreateTableStringByID(String tablename, String primarycolumn,
                                        String[] restcolumns) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + tablename + " ("
                + primarycolumn + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        for (String data : restcolumns) {
            CREATE_TABLE = CREATE_TABLE + data + " TEXT,";
        }
        CREATE_TABLE = CREATE_TABLE.substring(0, CREATE_TABLE.length() - 1)
                + ")";
        return CREATE_TABLE;
    }

    public String CreateTableStringByComposite(String tablename, String[] primarycolumns,
                                               String[] cols) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + tablename + "(";
        for (String data : cols) {
            CREATE_TABLE = CREATE_TABLE + data + " TEXT,";
        }
        CREATE_TABLE = CREATE_TABLE + "PRIMARY KEY (";
        for (String data : primarycolumns) {
            CREATE_TABLE = CREATE_TABLE + data + ",";
        }
        CREATE_TABLE = CREATE_TABLE.substring(0, CREATE_TABLE.length() - 1)
                + "))";
        return CREATE_TABLE;
    }

    public void dropAndCreateTablesBasedOnConditions(AppDetails appDetails, boolean dataExist) {
        if (dataExist) {
            //data exist
            String re_createTable = createTablesBasedOnDataTypeWithOrPrimaryKeys(appDetails.getAppName(), appDetails.getTableColumns(), replaceWithUnderscore(appDetails.getTableName()), appDetails.getPrimaryKey(), appDetails.getCompositeKey(), appDetails.getForeignKey(), false);
            dropColumn(re_createTable, replaceWithUnderscore(appDetails.getTableName()), new String[]{});

            if (appDetails.getSubFormDetails() != null) {
                for (SubFormTableColumns subFormTableColumns : appDetails.getSubFormDetails()) {
                    dropAndCreateTablesBasedOnConditions(subFormTableColumns, dataExist);
                }
            }

        } else {
            //no data
            String createTable = createTablesBasedOnDataTypeWithOrPrimaryKeys(appDetails.getAppName(), appDetails.getTableColumns(), replaceWithUnderscore(appDetails.getTableName()), appDetails.getPrimaryKey(), appDetails.getCompositeKey(), appDetails.getForeignKey(), false);
            if (!createTable.trim().equals("")) {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(getDropTableQuery(appDetails.getTableName()));
                db.execSQL(createTable);
                db.close();
            }
            if (appDetails.getSubFormDetails() != null) {
                for (SubFormTableColumns subFormTableColumns : appDetails.getSubFormDetails()) {
                    dropAndCreateTablesBasedOnConditions(subFormTableColumns, dataExist);
                }
            }
        }

    }

    public void dropAndCreateTablesBasedOnConditions(SubFormTableColumns subFormTableColumns, boolean dataExist) {

        if (dataExist) {

            String re_createTable = createTablesBasedOnDataTypeWithOrPrimaryKeys(subFormTableColumns.getSubFormName(), subFormTableColumns.getTableColumns(), replaceWithUnderscore(subFormTableColumns.getTableName()), subFormTableColumns.getPrimaryKey(), subFormTableColumns.getCompositeKey(), subFormTableColumns.getForeignKey(), true);
            dropColumn(re_createTable, replaceWithUnderscore(subFormTableColumns.getTableName()), new String[]{});
        } else {
            String createTable = createTablesBasedOnDataTypeWithOrPrimaryKeys(subFormTableColumns.getSubFormName(), subFormTableColumns.getTableColumns(), replaceWithUnderscore(subFormTableColumns.getTableName()), subFormTableColumns.getPrimaryKey(), subFormTableColumns.getCompositeKey(), subFormTableColumns.getForeignKey(), true);
            if (!createTable.trim().equals("")) {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(getDropTableQuery(subFormTableColumns.getTableName()));
                db.execSQL(createTable);
                db.close();
            }
        }


    }

    public boolean checkTableWithRecords(AppDetails appDetails) {
        boolean flag = false;
        if (appDetails.getTableName() != null) {
            String mainformTable = replaceWithUnderscore(appDetails.getTableName());
            if (tableExists(mainformTable)) {
                if (getCountByValue(mainformTable, "Bhargo_SyncStatus", "0") > 0) {
                    flag = true;
                }
            }
        }

        if (!flag) {
            if (appDetails.getSubFormDetails() != null) {
                for (SubFormTableColumns subFormTableColumns : appDetails.getSubFormDetails()) {
                    String subformTable = replaceWithUnderscore(subFormTableColumns.getTableName());
                    if (tableExists(subformTable)) {
                        if (getCountByValue(subformTable, "Bhargo_SyncStatus", "0") > 0) {
                            flag = true;
                            break;
                        }
                    }
                }
            }
        }
        return flag;
    }

    public void createTablesBasedOnConditions(AppDetails.WorkSpaceAppsList appDetails) {
        try {
            System.out.println("=====createTablesBasedOnConditions:" + appDetails.getAppName() + "=======");
            // if (appDetails.getAppMode()!=null && !appDetails.getAppMode().trim().equals("Online")) {
            //Hybrid/Offline Case
            //1. Table Exist or No

            if (appDetails.getTableName() != null) {
   /* SQLiteDatabase db1 = this.getWritableDatabase();
    db1.execSQL("DROP TABLE " + appDetails.getTableName() + "");
    db1.execSQL("DROP TABLE BLUE00000006_offline_with_sf3_divisions");*/
                if (tableExists(replaceWithUnderscore(appDetails.getTableName()))) {
                    //version check
                    List<List<String>> app_and_apk_version_ = getTableColDataByCond(APPS_LIST_TABLE, APP_VERSION + "," + APK_VERSION, new String[]{APP_NAME}, new String[]{appDetails.getTableName()});
                    if (app_and_apk_version_.size() > 0 && app_and_apk_version_.get(0).equals(appDetails.getAppVersion()) && app_and_apk_version_.get(1).equals(appDetails.getApkVersion())) {
                        System.out.println("Recreate Table: No ");
                    } else {
                        System.out.println("Recreate Table: " + appDetails.getTableName());
                        List<String> tableCols = getTableColumns(replaceWithUnderscore(appDetails.getTableName()));
                        String[] cols = appDetails.getTableColumns().trim().equals("") ? new String[0] : appDetails.getTableColumns().trim().split("\\,");
                        //2.New Cols Adding
                        List<String> newCols = new ArrayList<>();
                        List<String> newColsDataType = new ArrayList<>();
                        for (String col : cols) {
                            String[] spiltCol_DataType = col.split(" ");
                            if (!tableCols.contains(spiltCol_DataType[0])) {
                                newCols.add(spiltCol_DataType[0].trim());
                                newColsDataType.add(spiltCol_DataType[1].trim());
                            }
                        }
                        /*ALTER TABLE tablename ADD colName INT NOT NULL;*/
                        if (newCols.size() > 0) {
                            SQLiteDatabase db = this.getWritableDatabase();

                            for (int i = 0; i < newCols.size(); i++) {
                                String CREATE_ALTER = "ALTER TABLE " + replaceWithUnderscore(appDetails.getTableName()) + " ADD ";
                                if (newColsDataType.get(i).contains("(MAX)")) {
                                    CREATE_ALTER = CREATE_ALTER + " " + newCols.get(i) + " " + " TEXT";
                                } else {
                                    CREATE_ALTER = CREATE_ALTER + " " + newCols.get(i) + " " + newColsDataType.get(i) + "";
                                }
                                db.execSQL(CREATE_ALTER);
                            }
                            db.close();
                        }
                        //3.Exist Cols Removing
                        //Bhavani
                        List<String> objCols = new ArrayList<>();
                        String tableName = "Bhargo_";
                        String transID = "Bhargo_Trans_Id";
                        String[] extraCols = new String[]{transID, tableName + "Trans_Date", tableName + "Is_Active", tableName + "OrganisationID", tableName + "CreatedUserID", tableName + "SubmittedUserID", tableName + "DistributionID",
                                tableName + "IMEI", tableName + "FormName", tableName + "SubFormName", tableName + "AppVersion", tableName + "SyncStatus", tableName + "UserID", tableName + "PostID", tableName + "IsCheckList", tableName + "CheckListNames", tableName + "FromServer"};
                        for (int j = 0; j < extraCols.length; j++) {
                            objCols.add(extraCols[j]);
                        }
                        //Bhavani
//            List<String> objCols=new ArrayList<>();
                        for (int j = 0; j < cols.length; j++) {
                            String[] spiltCol_DataType = cols[j].split(" ");
                            objCols.add(spiltCol_DataType[0]);
                        }
                        List<String> delCols = new ArrayList<>();
                        for (int i = 0; i < tableCols.size(); i++) {
                            if (!objCols.contains(tableCols.get(i))) {
                                delCols.add(tableCols.get(i).trim());
                            }
                        }
                        //*ALTER TABLE tablename DROP COLUMN col1,DROP COLUMN col2;*//*
                        if (delCols.size() > 0) {
               /* SQLiteDatabase db = this.getWritableDatabase();
                String CREATE_ALTER="ALTER TABLE "+appDetails.getTableName();
                for (int i = 0; i < delCols.size(); i++) {
                    CREATE_ALTER=CREATE_ALTER+ " DROP COLUMN "+delCols.get(i)+" ," ;
                }
                CREATE_ALTER = CREATE_ALTER.substring(0, CREATE_ALTER.length() - 1);
                db.execSQL(CREATE_ALTER);
                db.close();*/
                            String re_createTable = createTablesBasedOnDataTypeWithOrPrimaryKeys(appDetails.getAppName(), appDetails.getTableColumns(), replaceWithUnderscore(appDetails.getTableName()), appDetails.getPrimaryKey(), appDetails.getCompositeKey(), appDetails.getForeignKey(), false);
                            dropColumn(re_createTable, replaceWithUnderscore(appDetails.getTableName()), delCols.toArray(new String[0]));
                        }
                    }


                } else {
                    System.out.println("=====New Table=======");
                    //New Table Creating As per DataType To Cols:
                    //Done
                    String createTable = createTablesBasedOnDataTypeWithOrPrimaryKeys(appDetails.getAppName(), appDetails.getTableColumns(), replaceWithUnderscore(appDetails.getTableName()), appDetails.getPrimaryKey(), appDetails.getCompositeKey(), appDetails.getForeignKey(), false);
                    if (!createTable.trim().equals("")) {
                        SQLiteDatabase db = this.getWritableDatabase();
                        db.execSQL(createTable);
                        db.close();
                    }
                }
            }
            if (appDetails.getSubFormDetails() != null) {
                for (SubFormTableColumns subFormTableColumns : appDetails.getSubFormDetails()) {
                    createTablesBasedOnConditions(subFormTableColumns);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

    public void createTablesBasedOnConditions(AppDetails appDetails) {
        try {
            System.out.println("=====createTablesBasedOnConditions:" + appDetails.getAppName() + "=======");
            // if (appDetails.getAppMode()!=null && !appDetails.getAppMode().trim().equals("Online")) {
            //Hybrid/Offline Case
            //1. Table Exist or No

            if (appDetails.getTableName() != null) {
   /* SQLiteDatabase db1 = this.getWritableDatabase();
    db1.execSQL("DROP TABLE " + appDetails.getTableName() + "");
    db1.execSQL("DROP TABLE BLUE00000006_offline_with_sf3_divisions");*/
                if (tableExists(replaceWithUnderscore(appDetails.getTableName()))) {
                    List<String> tableCols = getTableColumns(replaceWithUnderscore(appDetails.getTableName()));
                    String[] cols = appDetails.getTableColumns().trim().equals("") ? new String[0] : appDetails.getTableColumns().trim().split("\\,");
                    //2.New Cols Adding
                    List<String> newCols = new ArrayList<>();
                    List<String> newColsDataType = new ArrayList<>();
                    for (String col : cols) {
                        String[] spiltCol_DataType = col.split(" ");
                        if (!tableCols.contains(spiltCol_DataType[0])) {
                            newCols.add(spiltCol_DataType[0].trim());
                            newColsDataType.add(spiltCol_DataType[1].trim());
                        }
                    }
                    /*ALTER TABLE tablename ADD colName INT NOT NULL;*/
                    if (newCols.size() > 0) {
                        SQLiteDatabase db = this.getWritableDatabase();

                        for (int i = 0; i < newCols.size(); i++) {
                            String CREATE_ALTER = "ALTER TABLE " + replaceWithUnderscore(appDetails.getTableName()) + " ADD ";
                            if (newColsDataType.get(i).contains("(MAX)")) {
                                CREATE_ALTER = CREATE_ALTER + " " + newCols.get(i) + " " + " TEXT";
                            } else {
                                CREATE_ALTER = CREATE_ALTER + " " + newCols.get(i) + " " + newColsDataType.get(i) + "";
                            }
                            db.execSQL(CREATE_ALTER);
                        }
                        db.close();
                    }
                    //3.Exist Cols Removing
                    //Bhavani
                    List<String> objCols = new ArrayList<>();
                    String tableName = replaceWithUnderscore(appDetails.getTableName()) + "_";
                    String transID = "Bhargo_Trans_Id";
                    String[] extraCols = new String[]{transID, tableName + "Trans_Date", tableName + "Is_Active", tableName + "OrganisationID", tableName + "CreatedUserID", tableName + "SubmittedUserID", tableName + "DistributionID",
                            tableName + "IMEI", tableName + "FormName", tableName + "SubFormName", tableName + "AppVersion", tableName + "SyncStatus", tableName + "UserID", tableName + "PostID", tableName + "IsCheckList", tableName + "CheckListNames", tableName + "FromServer"};
                    for (int j = 0; j < extraCols.length; j++) {
                        objCols.add(extraCols[j]);
                    }
                    //Bhavani
//            List<String> objCols=new ArrayList<>();
                    for (int j = 0; j < cols.length; j++) {
                        String[] spiltCol_DataType = cols[j].split(" ");
                        objCols.add(spiltCol_DataType[0]);
                    }
                    List<String> delCols = new ArrayList<>();
                    for (int i = 0; i < tableCols.size(); i++) {
                        if (!objCols.contains(tableCols.get(i))) {
                            delCols.add(tableCols.get(i).trim());
                        }
                    }
                    //*ALTER TABLE tablename DROP COLUMN col1,DROP COLUMN col2;*//*
                    if (delCols.size() > 0) {
               /* SQLiteDatabase db = this.getWritableDatabase();
                String CREATE_ALTER="ALTER TABLE "+appDetails.getTableName();
                for (int i = 0; i < delCols.size(); i++) {
                    CREATE_ALTER=CREATE_ALTER+ " DROP COLUMN "+delCols.get(i)+" ," ;
                }
                CREATE_ALTER = CREATE_ALTER.substring(0, CREATE_ALTER.length() - 1);
                db.execSQL(CREATE_ALTER);
                db.close();*/
                        String re_createTable = createTablesBasedOnDataTypeWithOrPrimaryKeys(appDetails.getAppName(), appDetails.getTableColumns(), replaceWithUnderscore(appDetails.getTableName()), appDetails.getPrimaryKey(), appDetails.getCompositeKey(), appDetails.getForeignKey(), false);
                        dropColumn(re_createTable, replaceWithUnderscore(appDetails.getTableName()), delCols.toArray(new String[0]));
                    }

                } else {
                    System.out.println("=====New Table=======");
                    //New Table Creating As per DataType To Cols:
                    //Done
                    String createTable = createTablesBasedOnDataTypeWithOrPrimaryKeys(appDetails.getAppName(), appDetails.getTableColumns(), replaceWithUnderscore(appDetails.getTableName()), appDetails.getPrimaryKey(), appDetails.getCompositeKey(), appDetails.getForeignKey(), false);
                    if (!createTable.trim().equals("")) {
                        SQLiteDatabase db = this.getWritableDatabase();
                        db.execSQL(createTable);
                        db.close();
                    }
                }
            }
            if (appDetails.getSubFormDetails() != null) {
                for (SubFormTableColumns subFormTableColumns : appDetails.getSubFormDetails()) {
                    createTablesBasedOnConditions(subFormTableColumns);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

    public void createTablesBasedOnConditions(SubFormTableColumns subFormTableColumns) {
        // if (appDetails.getAppMode()!=null && !appDetails.getAppMode().trim().equals("Online")) {
        //Hybrid/Offline Case
        //1. Table Exist or No

        if (tableExists(replaceWithUnderscore(subFormTableColumns.getTableName()))) {
            System.out.println("=====Exist Table=======");
            List<List<String>> app_and_apk_version = getTableColDataByCond(APPS_LIST_TABLE, APP_VERSION + "," + APK_VERSION, new String[]{APP_NAME}, new String[]{subFormTableColumns.getTableName()});
           /* if(app_and_apk_version.get(0).equals(subFormTableColumns.getAppVersion()) && app_and_apk_version.get(1).equals(appDetails.getApkVersion()) ){
                return;
            }*/
            System.out.println("Recreate SubFormTable: " + app_and_apk_version.toString());
            //System.out.println("Recreate appDetails: "+appDetails.toString()+":"+appDetails.getApkVersion()+":"+appDetails.getAppVersion());

            List<String> tableCols = getTableColumns(replaceWithUnderscore(subFormTableColumns.getTableName()));
            String[] cols = subFormTableColumns.getTableColumns().trim().equals("") ? new String[0] : subFormTableColumns.getTableColumns().trim().split("\\,");
            //2.New Cols Adding
            List<String> newCols = new ArrayList<>();
            List<String> newColsDataType = new ArrayList<>();
            for (String col : cols) {
                String[] spiltCol_DataType = col.split(" ");
                if (!tableCols.contains(spiltCol_DataType[0])) {
                    newCols.add(spiltCol_DataType[0].trim());
                    newColsDataType.add(spiltCol_DataType[1].trim());
                }
            }
            /*ALTER TABLE tablename ADD colName INT NOT NULL;*/
            if (newCols.size() > 0) {
                SQLiteDatabase db = this.getWritableDatabase();

                for (int i = 0; i < newCols.size(); i++) {
                    String CREATE_ALTER = "ALTER TABLE " + replaceWithUnderscore(subFormTableColumns.getTableName()) + " ADD ";
                    if (newColsDataType.get(i).contains("(MAX)")) {
                        CREATE_ALTER = CREATE_ALTER + " " + newCols.get(i) + " " + " TEXT";
                    } else {
                        CREATE_ALTER = CREATE_ALTER + " " + newCols.get(i) + " " + newColsDataType.get(i) + "";
                    }
                    db.execSQL(CREATE_ALTER);
                }
                db.close();
            }
            //3.Exist Cols Removing
            //Bhavani
            List<String> objCols = new ArrayList<>();
            String tableName = "Bhargo_";
            //pending issue with transid in subform
            String[] extraCols = new String[]{"Bhargo_Trans_Id", tableName + "Trans_Date", tableName + "Is_Active", tableName + "OrganisationID", tableName + "CreatedUserID", tableName + "SubmittedUserID", tableName + "DistributionID",
                    tableName + "IMEI", tableName + "FormName", tableName + "SubFormName", tableName + "AppVersion", tableName + "SyncStatus", tableName + "UserID", tableName + "PostID", tableName + "IsCheckList", tableName + "CheckListNames", tableName + "FromServer"};
            for (int j = 0; j < extraCols.length; j++) {
                objCols.add(extraCols[j]);
            }
            //Bhavani
//            List<String> objCols=new ArrayList<>();
            for (int j = 0; j < cols.length; j++) {
                String[] spiltCol_DataType = cols[j].split(" ");
                objCols.add(spiltCol_DataType[0]);
            }
            List<String> delCols = new ArrayList<>();
            for (int i = 0; i < tableCols.size(); i++) {
                if (!objCols.contains(tableCols.get(i))) {
                    delCols.add(tableCols.get(i).trim());
                }
            }
            //*ALTER TABLE tablename DROP COLUMN col1,DROP COLUMN col2;*//*
            if (delCols.size() > 0) {
               /* SQLiteDatabase db = this.getWritableDatabase();
                String CREATE_ALTER="ALTER TABLE "+appDetails.getTableName();
                for (int i = 0; i < delCols.size(); i++) {
                    CREATE_ALTER=CREATE_ALTER+ " DROP COLUMN "+delCols.get(i)+" ," ;
                }
                CREATE_ALTER = CREATE_ALTER.substring(0, CREATE_ALTER.length() - 1);
                db.execSQL(CREATE_ALTER);
                db.close();*/
                String re_createTable = createTablesBasedOnDataTypeWithOrPrimaryKeys(subFormTableColumns.getSubFormName(), subFormTableColumns.getTableColumns(), replaceWithUnderscore(subFormTableColumns.getTableName()), subFormTableColumns.getPrimaryKey(), subFormTableColumns.getCompositeKey(), subFormTableColumns.getForeignKey(), true);
                dropColumn(re_createTable, replaceWithUnderscore(subFormTableColumns.getTableName()), delCols.toArray(new String[0]));
            }

        } else {
            System.out.println("=====New Table=======");
            //New Table Creating As per DataType To Cols
            String createTable = createTablesBasedOnDataTypeWithOrPrimaryKeys(subFormTableColumns.getSubFormName(), subFormTableColumns.getTableColumns(), subFormTableColumns.getTableName(), subFormTableColumns.getPrimaryKey(), subFormTableColumns.getCompositeKey(), subFormTableColumns.getForeignKey(), true);
            if (!createTable.trim().equals("")) {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(createTable);
                db.close();
            }
        }
        // }
    }


    public String createTablesBasedOnDataTypeWithOrPrimaryKeys(String formName, String tableColumns, String tableName, String primaryKey, String compositekey, List<ForeignKey> foreignKeyList, boolean createSubFormTable) {
        //Extra Cols: 1.Serial No.,2.Organisation ID,3.Created User ID,4.Submitted User ID
        //5.Distribution ID,6.IMEI,7.Transaction ID,8.App Version,9.Sync Status,10.User ID
        //11.Post ID,12.Is CheckList,13.CheckList Names
        //Done:TransID First

        String[] cols = tableColumns.trim().equals("") ? new String[0] : tableColumns.trim().split("\\,");
        // String serialno = tableName + "_SerialNo";
        String tableName_with_underscore = "Bhargo_";
        String transID = "Bhargo_Trans_Id";
        String[] extraCols = new String[]{transID, tableName_with_underscore + "Trans_Date",
                tableName_with_underscore + "Is_Active", tableName_with_underscore + "OrganisationID",
                tableName_with_underscore + "CreatedUserID", tableName_with_underscore + "SubmittedUserID",
                tableName_with_underscore + "DistributionID",
                tableName_with_underscore + "IMEI", tableName_with_underscore + "FormName",
                tableName_with_underscore + "SubFormName", tableName_with_underscore + "AppVersion",
                tableName_with_underscore + "SyncStatus", tableName_with_underscore + "UserID",
                tableName_with_underscore + "PostID", tableName_with_underscore + "IsCheckList",
                tableName_with_underscore + "CheckListNames", tableName_with_underscore + "FromServer",
                tableName_with_underscore + "TableSettingsType", tableName_with_underscore + "TypeofSubmission",
                "AutoIncrementVal", "AutoNumberJson"
        };

//        String primaryKeyDefault = "SerialNo";
        String primaryKeys = primaryKey != null ? primaryKey.trim() : "";
        if (primaryKey.contains(transID)) {
            primaryKeys = transID;
        }
        String[] compositeKeys = compositekey != null ? (compositekey.trim().equals("") ? new String[0] : compositekey.trim().split("\\,")) : new String[0];

        if (cols.length > 0) {

            String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + tableName + " ( ";
            //Extra Cols
            for (String data : extraCols) {
                CREATE_TABLE = CREATE_TABLE + data + " TEXT,";
            }
            String ref_trans_id = "Bhargo_Ref_TransID";
            if (createSubFormTable) {
                boolean contains_ref_trans_id = false;
                for (int i = 0; i < cols.length; i++) {
                    if (cols[i].contains(ref_trans_id)) {
                        contains_ref_trans_id = true;
                        break;
                    }
                }
                if (!contains_ref_trans_id) {
                    CREATE_TABLE = CREATE_TABLE + ref_trans_id + " TEXT,";
                }
            }
            /*if (checkForMapExistingTable(formName, tableName)) {
                CREATE_TABLE = CREATE_TABLE + "WhereClause" + " TEXT,";
            }*/

            //Table Cols
            for (String data : cols) {
                if (data.contains("(MAX)")) {
                    CREATE_TABLE = CREATE_TABLE + data.substring(0, data.indexOf(" ")) + " TEXT,";
                } else {
                    CREATE_TABLE = CREATE_TABLE + data + " ,";
                }
            }

            //CompositeKeys/PrimaryKeys
            if (compositeKeys.length > 0) {
                //More Then One Primary Keys: PRIMARY KEY (employee_id , course_id)
                // CREATE_TABLE = CREATE_TABLE + serialno + " INT AUTO_INCREMENT ,";
                CREATE_TABLE = CREATE_TABLE + " PRIMARY KEY (";
                // CREATE_TABLE = CREATE_TABLE + serialno + " ,";
                for (String compKey : compositeKeys) {
                    CREATE_TABLE = CREATE_TABLE + compKey + " ,";
                }
                CREATE_TABLE = CREATE_TABLE.substring(0, CREATE_TABLE.length() - 1)
                        + "))";
            } else {
                if (!primaryKeys.trim().equals("")) {
                    //&& !primaryKeys.equalsIgnoreCase(serialno)
                    //More Then One Primary Key: PRIMARY KEY (employee_id , course_id)
                    // CREATE_TABLE = CREATE_TABLE + serialno + " INT AUTO_INCREMENT ,";
                    CREATE_TABLE = CREATE_TABLE + " PRIMARY KEY (" + primaryKeys + "),";
                } else {
                    //Single Primary Key:colName INT AUTO_INCREMENT PRIMARY KEY,
                    //CREATE_TABLE = CREATE_TABLE + " " + serialno + " INTEGER PRIMARY KEY ,";
                }
//                if (foreignKeyList.size() == 0) {
                    CREATE_TABLE = CREATE_TABLE.substring(0, CREATE_TABLE.length() - 1) + ")";
//                }
            }
            /*if (foreignKeyList.size() > 0) {
                //More Then One Foreign Key

                for (ForeignKey foreignKey : foreignKeyList) {
                    CREATE_TABLE = CREATE_TABLE + " FOREIGN KEY (" + foreignKey.getForeignKey() + ")" +
                            " REFERENCES " + foreignKey.getTableName() + " (" + foreignKey.getColumnName() + "),";
                }
                CREATE_TABLE = CREATE_TABLE.substring(0, CREATE_TABLE.length() - 1)
                        + ")";
            }*/
            System.out.println("=====CREATE_TABLE=======:" + CREATE_TABLE);
            return CREATE_TABLE;
        } else {
            System.out.println("=====NO CREATE_TABLE=======");
            return "";
        }
    }

    private boolean checkForMapExistingTable(String formName, String tableName) {
        String form = replaceWithUnderscore(formName);
        String table = tableName.substring(tableName.indexOf("_") + 1);
        return !form.equalsIgnoreCase(table);
    }

    public List<String> getTableColumns(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> ar = new ArrayList<>();
        Cursor c = null;
        try {
            //Cursor dbCursor = db.query(tableName, null, null, null, null, null, null);
            c = db.rawQuery("select * from " + tableName + " limit 1", null);
            if (c != null) {
                ar = new ArrayList<String>(Arrays.asList(c.getColumnNames()));
            }
        } catch (Exception e) {
            Log.v(tableName, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
            db.close();
        }
        return ar;
    }

    private void dropColumn(String createTableCmd,
                            String tableName,
                            String[] colsToRemove) {

        List<String> updatedTableColumns = getTableColumns(tableName);
        // Remove the columns we don't want anymore from the table's list of columns
        updatedTableColumns.removeAll(Arrays.asList(colsToRemove));

        String columnsSeperated = TextUtils.join(",", updatedTableColumns);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tableName + "_old;");
        db.execSQL("ALTER TABLE " + tableName + " RENAME TO " + tableName + "_old;");

        // Creating the table on its new format (no redundant columns)
        db.execSQL(createTableCmd);

        // Populating the table with the data
        db.execSQL("INSERT INTO " + tableName + "(" + columnsSeperated + ") SELECT "
                + columnsSeperated + " FROM " + tableName + "_old;");
        db.execSQL("DROP TABLE IF EXISTS " + tableName + "_old;");
        db.close();
    }

    /* Insert into Form Submit Table*/
    public long insertIntoFormTable(ContentValues contentValues, String tableName) {
        long results = 0;
        // 1. get reference to writable DB
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            results = db.insert(tableName, null, contentValues);
            Log.d("FORM_SUBMIT_TABLE", tableName + " After insert  Records Inserted Count " + results);

            db.close();

        } catch (Exception e) {
            Log.d(TAG, "insertIntoFormTable: " + e);
        }
        return results;
    }

    public OfflineDataTransaction insertIntoMainFormTable(ContentValues contentValues, String tableName, String trans_id, boolean replaceOnSameRow) {
        OfflineDataTransaction offlineDataTransaction = new OfflineDataTransaction();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            db.beginTransaction();
            long results;
            if (replaceOnSameRow) {
                results = db.insertWithOnConflict(tableName, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
            } else {
                results = db.insertOrThrow(tableName, null, contentValues);
            }
            if (results != -1) {
                db.setTransactionSuccessful();
                String query = "SELECT last_insert_rowid() FROM " + tableName;
                Cursor result = db.rawQuery(query, null);
                if (result.moveToFirst()) {
                    offlineDataTransaction.setMainFormTransID(result.getString(0));
                }
                //**Update row id in trans id column of Main Form Table**//
                ContentValues contentValuesUpdate = new ContentValues();
                contentValuesUpdate.put(trans_id, result.getString(0));
                db.update(tableName, contentValuesUpdate, "rowid='" + result.getString(0) + "'", null);
                //**Update row id in trans id column of Main Form Table**//
                result.close();
            }
        } catch (Exception e) {
            Log.d(TAG, "deleteFormData: " + e);
            offlineDataTransaction.setErrorMessage(e.getMessage());
        } finally {
            db.endTransaction();
        }
        db.close();
        return offlineDataTransaction;
    }

    public OfflineDataTransaction insertIntoMainFormTableMapExisting(ContentValues contentValues, ContentValues contentValuesInsert, String tableName, String trans_id, boolean replaceOnSameRow, String mapExistingType) {
        OfflineDataTransaction offlineDataTransaction = null;
        if (mapExistingType.equalsIgnoreCase("Insert")) {
            offlineDataTransaction = new OfflineDataTransaction();
            SQLiteDatabase db = this.getReadableDatabase();

            try {
                db.beginTransaction();
                long results;
                if (replaceOnSameRow) {
                    results = db.insertWithOnConflict(tableName, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                } else {
                    results = db.insertOrThrow(tableName, null, contentValues);
                }
                if (results != -1) {
                    db.setTransactionSuccessful();
                    String query = "SELECT last_insert_rowid() FROM " + tableName;
                    Cursor result = db.rawQuery(query, null);
                    if (result.moveToFirst()) {
                        offlineDataTransaction.setMainFormTransID(result.getString(0));
                    }
                    //**Update row id in trans id column of Main Form Table**//
                    ContentValues contentValuesUpdate = new ContentValues();
                    contentValuesUpdate.put(trans_id, result.getString(0));
                    db.update(tableName, contentValuesUpdate, "rowid='" + result.getString(0) + "'", null);
                    //**Update row id in trans id column of Main Form Table**//
                    result.close();
                }
            } catch (Exception e) {
                offlineDataTransaction.setErrorMessage(e.getMessage());
            } finally {
                db.endTransaction();
            }
            db.close();
        } else if (mapExistingType.equalsIgnoreCase("Update")) {
            offlineDataTransaction = new OfflineDataTransaction();
            Gson gson = new Gson();
            SQLiteDatabase db = this.getWritableDatabase();
            try {
                db.beginTransaction();
//                String whereClause = getFilters(gson.fromJson(contentValues.getAsString("WhereClause"), JSONArray.class));
                String whereClause = getFilters(new JSONArray(contentValues.getAsString("WhereClause")));
                String rowid = getRowid(tableName, whereClause);
                contentValues.remove("WhereClause");
                long results = db.update(tableName, contentValues, whereClause, null);
                if (results != 0) {
                    offlineDataTransaction.setMainFormTransID(rowid);
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                offlineDataTransaction.setErrorMessage(e.getMessage());
            } finally {
                db.endTransaction();
            }
            db.close();
        } else if (mapExistingType.equalsIgnoreCase("Insert or Update")) {
            long results = -1;
            Gson gson = new Gson();
            SQLiteDatabase db = this.getWritableDatabase();
            try {
                db.beginTransaction();
                String whereClause = getFilters(new JSONArray(contentValues.getAsString("WhereClause")));
                String rowid = getRowid(tableName, whereClause);
                contentValues.remove("WhereClause");
                results = db.update(tableName, contentValues, whereClause, null);
                if (results != 0) {
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                offlineDataTransaction.setErrorMessage(e.getMessage());
            } finally {
                db.endTransaction();
            }
            db.close();
            if (results == 0) {
                offlineDataTransaction = new OfflineDataTransaction();
                SQLiteDatabase dbInsert = this.getReadableDatabase();

                try {
                    dbInsert.beginTransaction();
                    long resultsInsert;
                    if (replaceOnSameRow) {
                        resultsInsert = dbInsert.insertWithOnConflict(tableName, null, contentValuesInsert, SQLiteDatabase.CONFLICT_REPLACE);
                    } else {
                        resultsInsert = dbInsert.insertOrThrow(tableName, null, contentValuesInsert);
                    }
                    if (resultsInsert != -1) {
                        dbInsert.setTransactionSuccessful();
                        String query = "SELECT last_insert_rowid() FROM " + tableName;
                        Cursor result = dbInsert.rawQuery(query, null);
                        if (result.moveToFirst()) {
                            offlineDataTransaction.setMainFormTransID(result.getString(0));
                        }
                        //**Update row id in trans id column of Main Form Table**//
                        ContentValues contentValuesUpdate = new ContentValues();
                        contentValuesUpdate.put(trans_id, result.getString(0));
                        dbInsert.update(tableName, contentValuesUpdate, "rowid='" + result.getString(0) + "'", null);
                        //**Update row id in trans id column of Main Form Table**//
                        result.close();
                    }
                } catch (Exception e) {
                    offlineDataTransaction.setErrorMessage(e.getMessage());
                } finally {
                    dbInsert.endTransaction();
                }
                dbInsert.close();
            }

        }
        return offlineDataTransaction;
    }

    private String getFilters(JSONArray filterColumnsList) {
        StringBuilder filters = new StringBuilder();
        try {
       /* for (FilterColumns filterColumn : filterColumnsList) {
            filters.append(" ").append(filterColumn.getColumnName()).append(" ").append(filterColumn.getOperator()).append(" ").append("'").append(filterColumn.getValue()).append("'").append(" ").append(filterColumn.getCondition());
        }*/
            for (int i = 0; i < filterColumnsList.length(); i++) {
                JSONObject filterColumn = filterColumnsList.getJSONObject(i);
                filters.append(" ").append(filterColumn.get("ColumnName")).append(" ").append(filterColumn.get("Operator")).append(" ").append("'").append(filterColumn.get("Value")).append("'").append(" ").append(filterColumn.get("Condition"));

            }
        } catch (Exception e) {

        }
        return filters.toString();
    }

    /* Insert into Form Submit Table*/
    public long updateIntoFormTable(ContentValues contentValues, String tableName, String whereClauseColumn, String whereClauseValue) {
        // 1. get reference to writable DB
        Gson gson = new Gson();
        SQLiteDatabase db = this.getWritableDatabase();


        long results = db.update(tableName, contentValues, whereClauseColumn + " = " + whereClauseValue, null);
        Log.d("FORM_SUBMIT_TABLE", tableName + " After insert  Records Inserted Count " + results);

        db.close();
        return results;
    }

    public List<GetOfflineData> getOfflineDataFromFormTable(String query, String tableColumns) {
        List<GetOfflineData> modelList = new ArrayList<GetOfflineData>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                GetOfflineData model = new GetOfflineData();
                model.setSno(cursor.getString(0));
                model.setOrgId(cursor.getString(1));
                model.setApp_name(cursor.getString(2));
                model.setCreatedUserID(cursor.getString(3));
                model.setSubmittedUserID(cursor.getString(4));
                model.setDistributionID(cursor.getString(5));
                model.setIMEI(cursor.getString(6));
                model.setAppVersion(cursor.getString(7));
                model.setSync_status(cursor.getString(8));
                model.setOffline_json(cursor.getString(9));


                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("RetrieveOfflineAppsList", modelList.toString());
        db.close();
        return modelList;

    }

    public JSONArray getOfflineDataWithFiltersLimit(String org_id, String appName, String offlinestatus, String onlinestatus, String user_id, String post_id, String tableName, String trans_id, String filters, int type, int limit) {
       /* type 1:User and Post
        type 2: User Post and Location
        type 3: Entire Data
        type 4: filters */
        JSONArray jsonArray = new JSONArray();
        String query = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String OrganisationID = "Bhargo_OrganisationID";
            String FormName = "Bhargo_FormName";
            String SyncStatus = "Bhargo_SyncStatus";
            String UserID = "Bhargo_SubmittedUserID";
            String PostID = "Bhargo_PostID";
            if (type == 1) {
                query = "select * from " + tableName + " WHERE " + OrganisationID + " ='" + org_id + "' and " + UserID + " ='" + user_id + "' and " + PostID + " ='" + post_id + "' and  " + FormName + " ='" + appName + "'" +
                        " and (" + SyncStatus + " = '" + offlinestatus + "' or " + SyncStatus + " = '" + onlinestatus + "' ) LIMIT '" + limit + "'";
            } else if (type == 2) {
            } else if (type == 3) {
                query = "select * from " + tableName + " WHERE " + OrganisationID + " ='" + org_id + "' and  " + FormName + " ='" + appName + "'" +
                        " and (" + SyncStatus + " = '" + offlinestatus + "' or " + SyncStatus + " = '" + onlinestatus + "' ) LIMIT '" + limit + "'";
            } else if (type == 4) {
                query = "select * from " + tableName + " WHERE " + OrganisationID + " ='" + org_id + "' and  " + FormName + " ='" + appName + "'" +
                        " and (" + SyncStatus + " = '" + offlinestatus + "' or " + SyncStatus + " = '" + onlinestatus + "' ) and " + filters + " LIMIT '" + limit + "'";
            }
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    JSONObject model = new JSONObject();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        if (cursor.getColumnName(i).equalsIgnoreCase(trans_id)) {
                            model.put("Trans_ID", cursor.getString(i));
                        } else {
                            model.put(cursor.getColumnName(i), cursor.getString(i));
                        }
                    }
                    jsonArray.put(model);
                } while (cursor.moveToNext());
            }
            db.close();
        } catch (Exception e) {
            Log.d("getOfflineDataWith", e.toString());
        }

        return jsonArray;

    }

    public JSONArray getOfflineDataWithFilters(String org_id, String appName, String offlinestatus,
                                               String onlinestatus, String user_id, String post_id,
                                               String tableName, String trans_id, String filters,
                                               int type, AppDetailsAdvancedInput appDetailsAdvancedInput) {
       /* type 1:User and Post
        type 2: User Post and Location
        type 3: Entire Data
        type 4: filters */
        JSONArray jsonArray = new JSONArray();
        String query = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String OrganisationID = "Bhargo_OrganisationID";
            String FormName = "Bhargo_FormName";
            String SyncStatus = "Bhargo_SyncStatus";
            String UserID = "Bhargo_SubmittedUserID";
            String PostID = "Bhargo_PostID";
            if (type == 1) {
                query = "select * from " + tableName + " WHERE " + OrganisationID + " ='" + org_id + "' and " + UserID + " ='" + user_id + "' and " + PostID + " ='" + post_id + "' and  " + FormName + " ='" + appName + "'" +
                        " and (" + SyncStatus + " = '" + offlinestatus + "' or " + SyncStatus + " = '" + onlinestatus + "' ) ";
            } else if (type == 2) {
            } else if (type == 3) {
                query = "select * from " + tableName + " WHERE " + OrganisationID + " ='" + org_id + "' and  " + FormName + " ='" + appName + "'" +
                        " and (" + SyncStatus + " = '" + offlinestatus + "' or " + SyncStatus + " = '" + onlinestatus + "' ) ";
            } else if (type == 4) {
                query = "select * from " + tableName + " WHERE " + OrganisationID + " ='" + org_id + "' and  " + FormName + " ='" + appName + "'" +
                        " and (" + SyncStatus + " = '" + offlinestatus + "' or " + SyncStatus + " = '" + onlinestatus + "' ) and " + filters + " ";
            }
            if (appDetailsAdvancedInput.getOrderbyStatus().equalsIgnoreCase("true") && appDetailsAdvancedInput.getOrderByColumns() != null && !appDetailsAdvancedInput.getOrderByColumns() .equals("") && appDetailsAdvancedInput.getOrderByType() != null && !appDetailsAdvancedInput.getOrderByType().equals("")) {
                query = query + " ORDER BY " + appDetailsAdvancedInput.getOrderByColumns();
                query = query + " " + appDetailsAdvancedInput.getOrderByType();
            }
            if (appDetailsAdvancedInput.getLazyLoading().equalsIgnoreCase("True")) {
                String range[] = appDetailsAdvancedInput.getRange().split("-");
                query = query + " LIMIT "+range[0]+","+range[1]+"";
            }
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    JSONObject model = new JSONObject();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        if (cursor.getColumnName(i).equalsIgnoreCase(trans_id)) {
                            model.put("Trans_ID", cursor.getString(i));
                        } else {
                            model.put(cursor.getColumnName(i), cursor.getString(i));
                        }
                    }
                    jsonArray.put(model);
                } while (cursor.moveToNext());
            }
            db.close();
        } catch (Exception e) {

        }

        return jsonArray;

    }

    public String getCallFormFieldsDataWithFilters(String org_id, String tableName, JSONArray filtersData, String OrderByColumns, String Order) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            String query = null;

            jsonObject.put("Status", "200");
            jsonObject.put("Message", "Success");

            JSONObject detailsObj = new JSONObject();

            if (tableExists(tableName)) {
                SQLiteDatabase db = this.getWritableDatabase();
                query = getFormFieldsQuery(tableName, org_id, filtersData, OrderByColumns, Order);
                Cursor cursor = db.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        JSONObject model = new JSONObject();
                        for (int i = 0; i < cursor.getColumnCount(); i++) {
                            model.put(cursor.getColumnName(i), cursor.getString(i));
                        }
                        jsonArray.put(model);
                    } while (cursor.moveToNext());
                    detailsObj.put("Detailedmessage", "Success");
                    detailsObj.put("Rowcount", jsonArray.length() + "");
                } else {
                    jsonObject.put("Status", "100");
                    jsonObject.put("Message", "Data not found");
                    detailsObj.put("Detailedmessage", "Failed");
                    detailsObj.put("Rowcount", "0");
                }
                db.close();
            } else {
                jsonObject.put("Status", "100");
                jsonObject.put("Message", "No Local Table");
                detailsObj.put("Detailedmessage", "Failed");
                detailsObj.put("Rowcount", "0");
            }
            jsonObject.put("FormData", jsonArray);
            jsonObject.put("Details", detailsObj);
        } catch (Exception e) {
            Log.d(TAG, "getCallFormFieldsDataWithFilters: " + e.getStackTrace());
        }
        return jsonObject.toString();

    }

    private String getFormFieldsQueryBeforeGPS(String tableName, String org_id, JSONArray filtersData, String orderByColumns, String order) {
        String OrganisationID = tableName + "_" + "OrganisationID";
        String query = "select * from " + tableName + " WHERE " + OrganisationID + " ='" + org_id + "'";
        String filters = null;
        if (filtersData != null && filtersData.length() > 0) {
            filters = getFormFieldsFilters(filtersData);
            if (filters != null) {
                query = query + "AND" + filters;
            }
        }
        if (orderByColumns != null) {
            query = query + " ORDER BY " + orderByColumns;
        }
        if (order != null) {
            query = query + " " + order;
        }
        return query;
    }

    private String getFormFieldsQuery(String tableName, String org_id, JSONArray filtersData, String orderByColumns, String order) {
        String OrganisationID = "Bhargo_" + "OrganisationID";
        String query = "";
//        String queryWithGPSFilters = checkForGPSFilters(filtersData,tableName,orderByColumns,  order,org_id);
//        if (queryWithGPSFilters != null && !queryWithGPSFilters.isEmpty()) {
//            query = queryWithGPSFilters;
//        }else{
        query = "select * from " + tableName + " WHERE " + OrganisationID + " ='" + org_id + "'";
        String filters = null;
        if (filtersData != null && filtersData.length() > 0) {
            filters = getFormFieldsFilters(filtersData);
            if (filters != null) {
                query = query + "AND" + filters;
            }
        }
        if (orderByColumns != null && !orderByColumns.equals("") && order != null && !order.equals("")) {
            query = query + " ORDER BY " + orderByColumns;
            query = query + " " + order;
        }

//        }
        return query;
    }

    private String checkForGPSFilters(JSONArray filtersData, String tableName, String orderByColumns, String order, String org_id) {
        String OrganisationID = tableName + "_" + "OrganisationID";
        String gpsFilters = "";
        String columnName = "";
        float nearBy = 0;
        String currentGPS = "";
        String noOfRec = "";
        try {
            for (int i = 0; i < filtersData.length(); i++) {
                JSONObject jsonFilter = filtersData.getJSONObject(i);
                if (jsonFilter.has("ColumnType")) {
                    if (jsonFilter.getString("ColumnType").equalsIgnoreCase("GPS")) {
                        columnName = jsonFilter.getString("ColumnName");
                        nearBy = Float.parseFloat(jsonFilter.getString("NearBy")) / 1000;
                        currentGPS = jsonFilter.getString("CurrentGPS");
                        noOfRec = jsonFilter.getString("NoOfRec");
//                        gpsFilters = "(6371 * acos ( cos ( radians("+currentGPS.split("\\$")[0]+")  * cos( radians(  substr("+columnName+", 0, instr("+columnName+", '\\$')) ) ) * cos( radians(  substr("+columnName+", instr("+columnName+",  '\\$') + length( '\\$')) ) - radians("+currentGPS.split("\\$")[1]+") ) + sin ( radians("+currentGPS.split("\\$")[0]+")) * sin( radians( substr("+columnName+", 0, instr("+columnName+", '\\$')))))) AS distance";
                        gpsFilters = "(6371 * acos ( cos ( radians(" + currentGPS.split("\\$")[0] + "))  * cos( radians(  substr(" + columnName + ", 0, instr(" + columnName + ",\"$\")) ) ) * cos( radians(  substr(" + columnName + ", instr(" + columnName + ",  \"$\") + length( \"$\")) ) - radians(" + currentGPS.split("\\$")[1] + ") ) + sin ( radians(" + currentGPS.split("\\$")[0] + ")) * sin( radians( substr(" + columnName + ", 0, instr(" + columnName + ", \"$\")))))) AS distance";
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "checkForGPSFilters: " + e.getStackTrace());
        }
        String query = "select *," + gpsFilters + " from " + tableName + " WHERE " + OrganisationID + " ='" + org_id + "'";

        String filters = null;
        if (filtersData != null && filtersData.length() > 0) {
            filters = getFormFieldsFilters(filtersData);
            query = query + "AND" + filters;
        }
        query = query + " GROUP BY distance HAVING distance < " + nearBy;
        if (orderByColumns != null) {
            query = query + " ORDER BY " + orderByColumns;
        }
        if (order != null) {
            query = query + " " + order;
        }
        if (!noOfRec.isEmpty()) {
            query = query + " LIMIT " + noOfRec;
        }
        return query;
    }

   /* public JSONArray getOfflineDataFromFormTableJSON(String org_id, String appName, String offlinestatus, String onlinestatus, String user_id, String tableName, String trans_id) {
        JSONArray jsonArray = new JSONArray();
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String OrganisationID = tableName + "_" + "OrganisationID";
            String FormName = tableName + "_" + "FormName";
            String SyncStatus = tableName + "_" + "SyncStatus";
            String UserID = tableName + "_" + "SubmittedUserID";
            String query = "select * from " + tableName + " WHERE " + OrganisationID + " ='" + org_id + "' and  " + FormName + " ='" + appName + "'" +
                    " and (" + SyncStatus + " = '" + offlinestatus + "' or " + SyncStatus + " = '" + onlinestatus + "' )";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    JSONObject model = new JSONObject();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        if (cursor.getColumnName(i).equalsIgnoreCase(trans_id)) {
                            model.put("Trans_ID", cursor.getString(i));
                        } else {
                            model.put(cursor.getColumnName(i), cursor.getString(i));
                        }
                    }
                    jsonArray.put(model);
                } while (cursor.moveToNext());
            }
            db.close();
        } catch (Exception e) {

        }

        return jsonArray;

    }*/

    public JSONArray getOfflineDataFromSubFormTableJSON(String org_id, String appName, String status, String user_id, String tableName, String trans_id, String mainform_trans_id, String mainform_trans_id_value, String is_active) {
        JSONArray jsonArray = new JSONArray();

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String query = "select * from " + tableName + " WHERE " + mainform_trans_id + " = '" + mainform_trans_id_value + "' and " + is_active + "= 'yes'";
            Log.d(TAG, "getOfflineDataFromSubFormTableJSON: " + query);
            Cursor cursor = db.rawQuery(query, null);


            if (cursor.moveToFirst()) {
                do {
                    JSONObject model = new JSONObject();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        if (cursor.getColumnName(i).equalsIgnoreCase(trans_id)) {
                            model.put("Trans_ID", cursor.getString(i));
                        } else {
                            model.put(cursor.getColumnName(i), cursor.getString(i));
                        }
                    }
                    jsonArray.put(model);
                } while (cursor.moveToNext());
            }
            db.close();
        } catch (Exception e) {

        }

        return jsonArray;

    }

    public boolean deleteFormRowData(String tableName, String transIDColumn, String transIDValue) {
        Boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();
        status = db.delete(tableName, transIDColumn + " = '" + transIDValue + "'", null) > 0;
        db.close();
        return status;
    }

    public int getCount(String tableName) {

        SQLiteDatabase db = this.getReadableDatabase();
        int cnt = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
        cursor.moveToFirst();
        cnt = Integer.parseInt(cursor.getString(0));
        cursor.close();
        db.close();
        return cnt;
    }

    public List<List<String>> getDataByQuery(String query) {
        List<List<String>> list = new ArrayList<List<String>>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery(query, null);
            if (cur.getCount() > 0)
                list = cursorToListArr(cur);
            cur.close();
            db.close();

        } catch (SQLiteException e) {
            Log.d(TAG, "getDataByQuery: " + e);
        }
        return list;
    }

    public List<String> getPrimaryOrCompositeKeys(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(query, null);
        List<String> list = new ArrayList<>();
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                list.add(cur.getString(0));
            }
        }
        cur.close();
        db.close();
        return list;
    }

    public List<List<String>> getTableColDataByORCond(String TableName,
                                                      String outColNamesByComma, String[] condcolnames,
                                                      String[] condcolValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT " + outColNamesByComma + " FROM " + TableName
                + " WHERE ";
        for (int k = 0; k < condcolnames.length; k++)
            query = query + condcolnames[k] + "='" + condcolValue[k] + "'"
                    + " OR ";
        query = query.substring(0, query.length() - 4);
        System.out.println("query:" + query);
        Cursor cur = db.rawQuery(query, null);
        List<List<String>> list = new ArrayList<List<String>>();
        if (cur.getCount() > 0)
            list = cursorToListArr(cur);
        cur.close();
        db.close();
        return list;
    }

    public int getCountByValues(String tableName, String[] colName,
                                String[] colValue) {

        SQLiteDatabase db = this.getReadableDatabase();
        int cnt = 0;
        String countQuery = "SELECT  COUNT(*) FROM " + tableName + " WHERE ";
        for (int k = 0; k < colName.length; k++)
            countQuery = countQuery + colName[k] + "='" + colValue[k]
                    + "' AND ";
        countQuery = countQuery.substring(0, countQuery.length() - 5);
        // System.out.println("countQuery:" + countQuery);
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        cnt = Integer.parseInt(cursor.getString(0));
        cursor.close();
        db.close();
        return cnt;
    }

    public List<List<String>> getTableColData(String TableName,
                                              String outColNamesByComma) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT " + outColNamesByComma + " FROM "
                + TableName, null);
        List<List<String>> list = new ArrayList<List<String>>();
        if (cur.getCount() > 0)
            list = cursorToListArr(cur);
        cur.close();
        db.close();
        return list;
    }

    public List<List<ColNameAndValuePojo>> getTableColNameAndValue(String TableName,
                                                                   String outColNamesByComma) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT " + outColNamesByComma + " FROM "
                + TableName, null);
        List<List<ColNameAndValuePojo>> list = new ArrayList<List<ColNameAndValuePojo>>();
        if (cur.getCount() > 0)
            list = cursorToListPojo(cur);
        cur.close();
        db.close();
        return list;
    }

    public List<List<ColNameAndValuePojo>> getTableColNameAndValueByCond(String TableName,
                                                                         String outColNamesByComma, String[] wherecolnames,
                                                                         String[] wherecolumnValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + outColNamesByComma + " FROM " + TableName
                + " WHERE ";
        for (int k = 0; k < wherecolnames.length; k++)
            query = query + wherecolnames[k] + "='" + wherecolumnValue[k] + "'"
                    + " AND ";
        query = query.substring(0, query.length() - 5);
        // //System.out.println("Final Query:" + query);
        Cursor cur = db.rawQuery(query, null);
        List<List<ColNameAndValuePojo>> list = new ArrayList<List<ColNameAndValuePojo>>();
        if (cur.getCount() > 0)
            list = cursorToListPojo(cur);
        cur.close();
        db.close();
        return list;
    }

    public List<FileColAndIDPojo> getFilePathFromTables(List<TableNameAndColsFromDOPojo> tableNameAndColsForFilesPojoList, String trans_id_maintablecolVal) {
        List<FileColAndIDPojo> modelList = new ArrayList<FileColAndIDPojo>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        for (int i = 0; i < tableNameAndColsForFilesPojoList.size(); i++) {
            TableNameAndColsFromDOPojo tableNameAndColsForFilesPojo = tableNameAndColsForFilesPojoList.get(i);
            String[] cols = tableNameAndColsForFilesPojo.getCols().split(",");
            String tableName = tableNameAndColsForFilesPojo.getTableName();
            if (tableExists(tableName, db)) {
                if (tableNameAndColsForFilesPojo.getType().equals("M")) {
                    //Main Table
                    String trans_id_maintablecolName = "Bhargo_Trans_Id";
                    cursor = db.rawQuery("SELECT " + tableNameAndColsForFilesPojo.getCols() + ",rowid" + " FROM "
                            + tableNameAndColsForFilesPojo.getTableName() + " where " + trans_id_maintablecolName + "='" + trans_id_maintablecolVal + "'", null);
                } else {
                    //SubForm Table
                    //BLUE00000006_test_sync1_members_Ref_TransID
                    String ref_trans_id_subformtablecolName = "Bhargo_Ref_TransID";
                    cursor = db.rawQuery("SELECT " + tableNameAndColsForFilesPojo.getCols() + ",rowid" + " FROM "
                            + tableNameAndColsForFilesPojo.getTableName() + " where " + ref_trans_id_subformtablecolName + "='" + trans_id_maintablecolVal + "'", null);
                }

                if (cursor.moveToFirst()) {
                    do {
                        int colCount = cursor.getColumnCount() - 1;
                        String rowId = cursor.getString(colCount);
                        for (int j = 0; j < colCount; j++) {
                            String colVal = cursor.getString(j);
                            String colName = cursor.getColumnName(j);
                            if (colVal != null && colVal.contains(".") && !colVal.contains("http")) {
                                FileColAndIDPojo fileColAndIDPojo = new FileColAndIDPojo();
                                fileColAndIDPojo.setTableName(tableName);
                                fileColAndIDPojo.setColName(colName);
                                fileColAndIDPojo.setColVal(colVal);
                                fileColAndIDPojo.setRowId(rowId);
                                modelList.add(fileColAndIDPojo);
                            }
                        }
                    } while (cursor.moveToNext());
                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        Log.d("getFilePathFromTables", modelList.toString());
        return modelList;

    }

    public String updateByValuesForManageData(String tablename, ContentValues cv, String whereClause) {
        SQLiteDatabase db = this.getReadableDatabase();
        String updateStatus = "";
        try {
            boolean flag = db.update(tablename, cv, whereClause, null) > 0;
            if (!flag) {
                updateStatus = "Update Failed!";
            }
        } catch (Exception e) {
            updateStatus = "Error_" + e.getMessage();
        } finally {
            db.close();
        }
        return updateStatus;
    }

    public String multiUpdateByValuesForManageData(String tablename, List<ContentValues> lcv, List<String> lwhereClause) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String errorMsg = "";
        long result;
        try {
            boolean flag = true;
            for (int i = 0; i < lcv.size(); i++) {
                flag = db.update(tablename, lcv.get(i), lwhereClause.get(i), null) > 0;
                if (!flag) {
                    errorMsg = "Error Failed To Update!";
                    break;
                }
            }
            if (flag)
                db.setTransactionSuccessful();
        } catch (Exception e) {
            errorMsg = "Error " + e.getMessage();
        } finally {
            db.endTransaction();
        }
        db.close();
        return errorMsg;
    }

    public boolean updateByValues(String tablename, String[] columnNames,
                                  String[] columnValues, String[] whereColumn, String[] whereValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < columnNames.length; i++) {
            cv.put(columnNames[i], columnValues[i]);
        }
        String query = "";
        for (int i = 0; i < whereColumn.length; i++)
            query = query + whereColumn[i] + "='" + whereValue[i] + "' AND ";
        query = query.substring(0, query.length() - 5);
        System.out.println("query:" + query);
        boolean flag = db.update(tablename, cv, query, null) > 0;
        db.close();

        return flag;
    }

    public boolean deleteByValues(String tablename, String[] wherecolumnNames,
                                  String[] wherecolumnValues) {
        SQLiteDatabase db = this.getReadableDatabase();
        String cond = "";
        for (int i = 0; i < wherecolumnNames.length; i++) {
            cond = cond + wherecolumnNames[i] + " = '" + wherecolumnValues[i]
                    + "' AND ";
        }
        cond = cond.substring(0, cond.length() - 5);
        boolean flag = db.delete(tablename, cond, null) > 0;
        // System.out.println("deleted?" + flag);
        db.close();
        return flag;
    }

    public OfflineDataTransaction insertSubFormData1(List<OfflineDataTransaction> offlineDataSubFormList) {
        OfflineDataTransaction failedForm = new OfflineDataTransaction();
        long msg = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            for (OfflineDataTransaction offlineDataSubForm : offlineDataSubFormList) {
                failedForm = offlineDataSubForm;
                if (offlineDataSubForm.isReplaceOnSameRow()) {
                    msg = db.insertWithOnConflict(offlineDataSubForm.getTableName(), null, offlineDataSubForm.getContentValues(), SQLiteDatabase.CONFLICT_REPLACE);
                } else {
                    msg = db.insertOrThrow(offlineDataSubForm.getTableName(), null, offlineDataSubForm.getContentValues());
                }
                //**Update row id in trans id column of sub Form Table**//
                String query = "SELECT last_insert_rowid() FROM " + offlineDataSubForm.getTableName();
                Cursor result = db.rawQuery(query, null);
                if (result.moveToFirst()) {
                }
                ContentValues contentValuesUpdate = new ContentValues();
                contentValuesUpdate.put(offlineDataSubForm.getTransIDColumn(), result.getString(0));
                db.update(offlineDataSubForm.getTableName(), contentValuesUpdate, "rowid='" + result.getString(0) + "'", null);
                result.close();
                //**Update row id in trans id column of sub Form Table**//
            }
            if (msg != -1) {
                db.setTransactionSuccessful();
            }

        } catch (Exception e) {
           /* Log.d(TAG, "insertSubformFormData: " + e.toString());
            failedForm.setErrorMessage(e.getMessage());*/
            String message = "";
            if (failedForm.isRejectWithMessage() && failedForm.getCompositeKeyErrorMessage() != null) {
                message = failedForm.getCompositeKeyErrorMessage();
            } else {
                message = e.getMessage();
            }
            failedForm.setErrorMessage(message);
        } finally {
            db.endTransaction();
        }
        db.close();
        return failedForm;
    }

    public OfflineDataTransaction insertSubFormData(List<OfflineDataTransaction> offlineDataSubFormList) {
        OfflineDataTransaction failedForm = new OfflineDataTransaction();
        long msg = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            for (OfflineDataTransaction offlineDataSubForm : offlineDataSubFormList) {
                if (offlineDataSubForm.getSubFormTableSettingsType().equalsIgnoreCase(context.getString(R.string.create_new_table))) {
                    failedForm = offlineDataSubForm;
                    if (offlineDataSubForm.isReplaceOnSameRow()) {
                        msg = db.insertWithOnConflict(offlineDataSubForm.getTableName(), null, offlineDataSubForm.getContentValues(), SQLiteDatabase.CONFLICT_REPLACE);
                    } else {
                        msg = db.insertOrThrow(offlineDataSubForm.getTableName(), null, offlineDataSubForm.getContentValues());
                    }
                    //**Update row id in trans id column of sub Form Table**//
                    String query = "SELECT last_insert_rowid() FROM " + offlineDataSubForm.getTableName();
                    Cursor result = db.rawQuery(query, null);
                    if (result.moveToFirst()) {
                    }
                    ContentValues contentValuesUpdate = new ContentValues();
                    contentValuesUpdate.put(offlineDataSubForm.getTransIDColumn(), result.getString(0));
                    db.update(offlineDataSubForm.getTableName(), contentValuesUpdate, "rowid='" + result.getString(0) + "'", null);
                    result.close();
                    //**Update row id in trans id column of sub Form Table**//
                } else if (offlineDataSubForm.getSubFormTableSettingsType().equalsIgnoreCase(context.getString(R.string.map_existing_table))) {
                    failedForm = offlineDataSubForm;
                    if (offlineDataSubForm.getSubFormTableMapExistingType().equalsIgnoreCase(context.getString(R.string.insert))) {
                        if (offlineDataSubForm.isReplaceOnSameRow()) {
                            msg = db.insertWithOnConflict(offlineDataSubForm.getTableName(), null, offlineDataSubForm.getContentValues(), SQLiteDatabase.CONFLICT_REPLACE);
                        } else {
                            msg = db.insertOrThrow(offlineDataSubForm.getTableName(), null, offlineDataSubForm.getContentValues());
                        }
                        //**Update row id in trans id column of sub Form Table**//
                        String query = "SELECT last_insert_rowid() FROM " + offlineDataSubForm.getTableName();
                        Cursor result = db.rawQuery(query, null);
                        if (result.moveToFirst()) {
                        }
                        ContentValues contentValuesUpdate = new ContentValues();
                        contentValuesUpdate.put(offlineDataSubForm.getTransIDColumn(), result.getString(0));
                        db.update(offlineDataSubForm.getTableName(), contentValuesUpdate, "rowid='" + result.getString(0) + "'", null);
                        result.close();
                        //**Update row id in trans id column of sub Form Table**//
                    } else if (offlineDataSubForm.getSubFormTableMapExistingType().equalsIgnoreCase(context.getString(R.string.update))) {
//                        String whereClause = getFilters(gson.fromJson(offlineDataSubForm.getSubFormContentValuesUpdate().getAsString("WhereClause"), JSONArray.class));
                        String whereClause = getFilters(new JSONArray(offlineDataSubForm.getSubFormContentValuesUpdate().getAsString("WhereClause")));
                        offlineDataSubForm.getSubFormContentValuesUpdate().remove("WhereClause");
                        msg = db.update(offlineDataSubForm.getTableName(), offlineDataSubForm.getSubFormContentValuesUpdate(), whereClause, null);
                    } else if (offlineDataSubForm.getSubFormTableMapExistingType().equalsIgnoreCase(context.getString(R.string.updateorinsert))) {
                        String whereClause = getFilters(new JSONArray(offlineDataSubForm.getSubFormContentValuesUpdate().getAsString("WhereClause")));
                        offlineDataSubForm.getSubFormContentValuesUpdate().remove("WhereClause");
                        msg = db.update(offlineDataSubForm.getTableName(), offlineDataSubForm.getSubFormContentValuesUpdate(), whereClause, null);
                        if (msg == 0) {
                            if (offlineDataSubForm.isReplaceOnSameRow()) {
                                msg = db.insertWithOnConflict(offlineDataSubForm.getTableName(), null, offlineDataSubForm.getContentValues(), SQLiteDatabase.CONFLICT_REPLACE);
                            } else {
                                msg = db.insertOrThrow(offlineDataSubForm.getTableName(), null, offlineDataSubForm.getContentValues());
                            }
                            //**Update row id in trans id column of sub Form Table**//
                            String query = "SELECT last_insert_rowid() FROM " + offlineDataSubForm.getTableName();
                            Cursor result = db.rawQuery(query, null);
                            if (result.moveToFirst()) {
                            }
                            ContentValues contentValuesUpdate = new ContentValues();
                            contentValuesUpdate.put(offlineDataSubForm.getTransIDColumn(), result.getString(0));
                            db.update(offlineDataSubForm.getTableName(), contentValuesUpdate, "rowid='" + result.getString(0) + "'", null);
                            result.close();
                            //**Update row id in trans id column of sub Form Table**//
                        }
                    }
                }
            }
            if (msg != -1 && msg != 0) {
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
           /* Log.d(TAG, "insertSubformFormData: " + e.toString());
            failedForm.setErrorMessage(e.getMessage());*/
            String message = "";
            if (failedForm.isRejectWithMessage() && failedForm.getCompositeKeyErrorMessage() != null) {
                message = failedForm.getCompositeKeyErrorMessage();
            } else {
                message = e.getMessage();
            }
            failedForm.setErrorMessage(message);
        } finally {
            db.endTransaction();
        }
        db.close();
        return failedForm;
    }

    /*public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentProviderResult[] results = super.applyBatch(operations);
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }*/

    public OfflineDataTransaction updateFormData(List<OfflineDataTransaction> offlineDataUpdateList, List<OfflineDataTransaction> offlineDataInsertList) {
        OfflineDataTransaction failedForm = new OfflineDataTransaction();
        long msg = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            for (OfflineDataTransaction offlineDataUpdate : offlineDataUpdateList) {
                failedForm = offlineDataUpdate;
                msg = db.update(offlineDataUpdate.getTableName(), offlineDataUpdate.getContentValues(), offlineDataUpdate.getTransIDColumn() + " = '" + offlineDataUpdate.getTransIDValue() + "'", null);
            }
            for (OfflineDataTransaction offlineDataInsert : offlineDataInsertList) {
                failedForm = offlineDataInsert;
                if (offlineDataInsert.isReplaceOnSameRow()) {
                    msg = db.insertWithOnConflict(offlineDataInsert.getTableName(), null, offlineDataInsert.getContentValues(), SQLiteDatabase.CONFLICT_REPLACE);
                } else {
                    msg = db.insertOrThrow(offlineDataInsert.getTableName(), null, offlineDataInsert.getContentValues());
                }
                //**Update row id in trans id column of sub Form Table**//
                String query = "SELECT last_insert_rowid() FROM " + offlineDataInsert.getTableName();
                Cursor result = db.rawQuery(query, null);
                if (result.moveToFirst()) {
                }
                ContentValues contentValuesUpdate = new ContentValues();
                contentValuesUpdate.put(offlineDataInsert.getTransIDColumn(), result.getString(0));
                db.update(offlineDataInsert.getTableName(), contentValuesUpdate, "rowid='" + result.getString(0) + "'", null);
                result.close();
            }
            if (msg != -1) {
                db.setTransactionSuccessful();
            } else
                db.endTransaction();
        } catch (Exception e) {
            String message = "";
            if (failedForm.isRejectWithMessage() && failedForm.getCompositeKeyErrorMessage() != null) {
                message = failedForm.getCompositeKeyErrorMessage();
            } else {
                message = e.getMessage();
            }
            failedForm.setErrorMessage(message);
        } finally {
            db.endTransaction();
        }
        db.close();
        return failedForm;
    }

    public OfflineDataTransaction deleteFormData(List<OfflineDataTransaction> offlineDataTransactionList) {
        OfflineDataTransaction failedForm = new OfflineDataTransaction();
        boolean status = false;
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        List<Boolean> statusList = new ArrayList<>();
        try {
            for (OfflineDataTransaction offlineDataTransaction : offlineDataTransactionList) {
                failedForm = offlineDataTransaction;
                status = db.delete(offlineDataTransaction.getTableName(), offlineDataTransaction.getTransIDColumn() + " = '" + offlineDataTransaction.getTransIDValue() + "'", null) > 0;
                if (status) {
                    statusList.add(status);
                }
            }
            if (statusList.size() == offlineDataTransactionList.size()) {
                db.setTransactionSuccessful();
            } else
                db.endTransaction();
        } catch (Exception e) {
            failedForm.setErrorMessage(e.getMessage());
            Log.d(TAG, "deleteFormData: " + e);
        } finally {
            db.endTransaction();
        }
        db.close();
        return failedForm;
    }

    public boolean bulkSqlQuery(List<String> sqlQueryList) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Insert types:
        //INSERT INTO TABLE_NAME (column1, column2, column3,...columnN) VALUES (value1, value2, value3,...valueN);
        //INSERT INTO TABLE_NAME VALUES (value1,value2,value3,...valueN);
        //Delete tables
        //String sql = "INSERT INTO " + tableName + colNames + " VALUES " + colVals + ";";
        db.beginTransaction();
        try {
            for (int i = 0; i < sqlQueryList.size(); i++) {
                SQLiteStatement statement = db.compileStatement(sqlQueryList.get(i));
                statement.execute();
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            return false;
        } finally {
            db.endTransaction();
            db.close();
            return true;
        }
    }

    public String bulkContentValues(List<ContentValues> contentValuesList, List<String> tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String errorMsg = "";
        long result;
        try {
            for (int i = 0; i < tableName.size(); i++) {
                String cond = "Bhargo_SyncStatus" + " = '2'";
                db.delete(tableName.get(i), cond, null);
            }
            for (int i = 0; i < contentValuesList.size(); i++) {
                result = db.insertOrThrow(tableName.get(i), null, contentValuesList.get(i));
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            errorMsg = e.getMessage();
        } finally {
            db.endTransaction();
        }
        db.close();
        return errorMsg;
    }

    public String bulkContentValues(List<ContentValues> contentValuesList, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String errorMsg = "";
        long result;
        try {
            //Delete Record With SyncStatus 2
            String cond = "Bhargo_SyncStatus" + " = '2'";
            db.delete(tableName, cond, null);

            for (int i = 0; i < contentValuesList.size(); i++) {
                result = db.insertOrThrow(tableName, null, contentValuesList.get(i));
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            errorMsg = e.getMessage();
        } finally {
            db.endTransaction();
        }
        db.close();
        return errorMsg;
    }

    /*private String getInsertQuery(String tableColumns, String tableName, String primaryKey, String compositekey) {
        String[] cols = tableColumns.trim().equals("") ? new String[0] : tableColumns.trim().split("\\,");
        String trans_id = "Bhargo_Trans_Id";
        String tableName_with_underscore = tableName + "_";
        String[] extraCols = new String[]{tableName_with_underscore + "Trans_Date", tableName_with_underscore + "Is_Active", tableName_with_underscore + "SerialNo", tableName_with_underscore + "OrganisationID", tableName_with_underscore + "CreatedUserID", tableName_with_underscore + "SubmittedUserID", tableName_with_underscore + "DistributionID",
                tableName_with_underscore + "IMEI", tableName_with_underscore + "FormName", tableName_with_underscore + "SubFormName", tableName_with_underscore + "AppVersion", tableName_with_underscore + "SyncStatus", tableName_with_underscore + "UserID", tableName_with_underscore + "PostID", tableName_with_underscore + "IsCheckList", tableName_with_underscore + "CheckListNames", tableName_with_underscore + "FromServer"};

        String primaryKeys = primaryKey != null ? primaryKey.trim() : "";
        String[] compositeKeys = compositekey != null ? (compositekey.trim().equals("") ? new String[0] : compositekey.trim().split("\\,")) : new String[0];

        if (cols.length > 0) {

            String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + tableName + " ( ";
            //Extra Cols
            for (String data : extraCols) {
                CREATE_TABLE = CREATE_TABLE + data + " TEXT,";
            }
            //Table Cols
            for (String data : cols) {
                if (data.contains("(MAX)")) {
                    CREATE_TABLE = CREATE_TABLE + data.substring(0, data.indexOf(" ")) + " TEXT,";
                } else {
                    CREATE_TABLE = CREATE_TABLE + data + " ,";
                }
            }

            //CompositeKeys/PrimaryKeys
            if (compositeKeys.length > 0) {
                //More Then One Primary Keys: PRIMARY KEY (employee_id , course_id)
                CREATE_TABLE = CREATE_TABLE + trans_id + " TEXT ,";
                CREATE_TABLE = CREATE_TABLE + " PRIMARY KEY (";
                for (String compKey : compositeKeys) {
                    CREATE_TABLE = CREATE_TABLE + compKey + " ,";
                }
                CREATE_TABLE = CREATE_TABLE.substring(0, CREATE_TABLE.length() - 1)
                        + "))";
            } else {
                if (!primaryKeys.trim().equals("") && !primaryKeys.equalsIgnoreCase(trans_id)) {
                    //More Then One Primary Keys: PRIMARY KEY (employee_id , course_id)
                    CREATE_TABLE = CREATE_TABLE + trans_id + " TEXT ,";
                    CREATE_TABLE = CREATE_TABLE + " PRIMARY KEY (" + primaryKeys + "),";
                } else {
                    //Single Primary Key:colName INT AUTO_INCREMENT PRIMARY KEY,
                    CREATE_TABLE = CREATE_TABLE + " " + trans_id + " INTEGER PRIMARY KEY ,";
                }
                CREATE_TABLE = CREATE_TABLE.substring(0, CREATE_TABLE.length() - 1)
                        + ")";
            }
            System.out.println("=====CREATE_TABLE=======:" + CREATE_TABLE);
            return CREATE_TABLE;
        } else {
            System.out.println("=====NO CREATE_TABLE=======");
            return "";
        }
    }*/

    public AppDetails getAppDetailsBasedOnTableName(String tableName) {
        AppDetails model = new AppDetails();
        Gson gson = new Gson();
        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + TABLE_NAME + "='" + tableName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    model.setDistrubutionID(cursor.getString(3));
                    model.setCreatedBy(cursor.getString(4));
                    model.setCreatedUserName(cursor.getString(5));
                    model.setDescription(cursor.getString(6));
                    model.setIsActive(cursor.getString(7));
                    model.setAppName(cursor.getString(8));
                    model.setAppVersion(cursor.getString(9));
                    model.setAppType(cursor.getString(10));
                    model.setDesignFormat(cursor.getString(11));
                    model.setVideoLink(cursor.getString(12));
                    model.setNewRow(cursor.getString(13));
                    model.setAppIcon(cursor.getString(14));
                    model.setApkVersion(cursor.getString(15));
                    model.setApplicationReceivedDate(cursor.getString(16));
                    model.setXMLFilePath(cursor.getString(17));
                    model.setAppMode(cursor.getString(20));
                    model.setPostID(cursor.getString(22));
                    model.setDisplayAs(cursor.getString(23));
                    model.setDisplayAppName(cursor.getString(31));
                    //nk
                    model.setTableName(cursor.getString(32));
                    model.setTableColumns(cursor.getString(33));
                    model.setPrimaryKey(cursor.getString(34));
                    Type typeAppsForeignKey = new TypeToken<List<ForeignKey>>() {
                    }.getType();
                    List<ForeignKey> foreignKeyList = gson.fromJson(cursor.getString(35), typeAppsForeignKey);
                    model.setForeignKey(foreignKeyList);
                    model.setCompositeKey(cursor.getString(36));
                    Type typeApps = new TypeToken<List<SubFormTableColumns>>() {
                    }.getType();
                    List<SubFormTableColumns> subFormTableColumnsList = gson.fromJson(cursor.getString(37), typeApps);
                    model.setSubFormDetails(subFormTableColumnsList);
                    List<AutoIncrementControl> autoIncrementControlList = new ArrayList<>();
                    if (cursor.getString(48) != null) {
                        Type typeAppsAuto = new TypeToken<List<AutoIncrementControl>>() {
                        }.getType();
                        autoIncrementControlList = gson.fromJson(cursor.getString(48), typeAppsAuto);
                        model.setAutoIncrementControlNames(autoIncrementControlList);
                    }
                } while (cursor.moveToNext());
            }
            Log.d("RetrieveAppsList", model.toString());
            db.close();
            return model;
        } else {
            return null;
        }
    }

    public AppDetails getAppDetails(String appName) {
        AppDetails model = new AppDetails();
        Gson gson = new Gson();
        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + APP_NAME + "='" + appName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    model.setDistrubutionID(cursor.getString(3));
                    model.setCreatedBy(cursor.getString(4));
                    model.setCreatedUserName(cursor.getString(5));
                    model.setDescription(cursor.getString(6));
                    model.setIsActive(cursor.getString(7));
                    model.setAppName(cursor.getString(8));
                    model.setAppVersion(cursor.getString(9));
                    model.setAppType(cursor.getString(10));
                    model.setDesignFormat(cursor.getString(11));
                    model.setVideoLink(cursor.getString(12));
                    model.setNewRow(cursor.getString(13));
                    model.setAppIcon(cursor.getString(14));
                    model.setApkVersion(cursor.getString(15));
                    model.setApplicationReceivedDate(cursor.getString(16));
                    model.setXMLFilePath(cursor.getString(17));
                    model.setAppMode(cursor.getString(20));
                    model.setPostID(cursor.getString(22));
                    model.setDisplayAs(cursor.getString(23));
                    model.setDisplayAppName(cursor.getString(31));
                    //nk
                    model.setTableName(cursor.getString(32));
                    model.setTableColumns(cursor.getString(33));
                    model.setPrimaryKey(cursor.getString(34));
                    Type typeAppsForeignKey = new TypeToken<List<ForeignKey>>() {
                    }.getType();
                    List<ForeignKey> foreignKeyList = gson.fromJson(cursor.getString(35), typeAppsForeignKey);
                    model.setForeignKey(foreignKeyList);
                    model.setCompositeKey(cursor.getString(36));
                    Type typeApps = new TypeToken<List<SubFormTableColumns>>() {
                    }.getType();
                    List<SubFormTableColumns> subFormTableColumnsList = gson.fromJson(cursor.getString(37), typeApps);
                    model.setSubFormDetails(subFormTableColumnsList);
                    List<AutoIncrementControl> autoIncrementControlList = new ArrayList<>();
                    if (cursor.getString(48) != null) {
                        Type typeAppsAuto = new TypeToken<List<AutoIncrementControl>>() {
                        }.getType();
                        autoIncrementControlList = gson.fromJson(cursor.getString(48), typeAppsAuto);
                        model.setAutoIncrementControlNames(autoIncrementControlList);
                    }
                } while (cursor.moveToNext());
            }
            Log.d("RetrieveAppsList", model.toString());
            db.close();
            return model;
        } else {
            return null;
        }
    }

    public String getDropTableQuery(String tablename) {
        return "DROP TABLE IF EXISTS " + tablename;
    }

    public String getValueFromOfflineTableCopy(String tablename, String columnName) {
        String value = "";
        List<String> tableNamesList = getTableNamesFromDB();
        for (String tableNameFromDB : tableNamesList) {
            if (tablename.equalsIgnoreCase(tableNameFromDB)) {
                if (tableExists(tableNameFromDB)) {
                    String query = "select group_concat(" + columnName + ", ',') from " + tableNameFromDB + "";
                    SQLiteDatabase db = this.getWritableDatabase();
                    Cursor c = db.rawQuery(query, null);
                    if (c.moveToFirst()) {
                        value = c.getString(0);
                    }
                    db.close();
                }
                break;
            }

        }
        return value;
    }

    public String getValueFromOfflineTable(String tablename, String columnName) {
        String value = "";
        List<String> tableNamesList = getTableNamesFromDB();
        for (String tableNameFromDB : tableNamesList) {
            if (tablename.equalsIgnoreCase(tableNameFromDB)) {
                if (tableExists(tableNameFromDB)) {
                    String query = "select " + columnName + " from " + tableNameFromDB + " ORDER BY rowid";
                    SQLiteDatabase db = this.getWritableDatabase();
                    Cursor c = db.rawQuery(query, null);
                    if (c.moveToFirst()) {
                        do {
                            if (c != null) {
                                value += c.getString(0) + ",";
                            }
                        } while (c.moveToNext());
                    }
                    db.close();
                }
                break;
            }

        }
        if (value != null && value.length() > 0 && value.endsWith(",")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    public String getExactTableName(String tableName) {
        List<String> tableNamesList = getTableNamesFromDB();
        for (String tableNameFromDB : tableNamesList) {
            if (tableName.equalsIgnoreCase(tableNameFromDB)) {
                return tableNameFromDB;
            }
        }
        return tableName;
    }

    public String getColumnDataCopy(String tableName, String columnName, String filters) {
        String value = "";
        String query = "select group_concat(" + columnName + ", ',') from " + tableName + " WHERE ";
        String[] test = filters.substring(filters.indexOf("(") + 1, filters.lastIndexOf(")")).split("\\$");
        StringBuilder whereStr = new StringBuilder();
        for (int i = 0; i < test.length; i++) {
            if (test[i].startsWith("filter")) {
                String[] test1 = (test[i].substring(test[i].indexOf("(") + 1, test[i].lastIndexOf(")") - 1)).split(",");
                String test2 = test1[2];
                test2 = test2.substring(1);
                whereStr.append(test1[0]).append(ImproveHelper.getOparator(test1[1])).append("'").append(test2).append("'");
            } else {
                whereStr.append(" ").append(test[i].toUpperCase()).append(" ");
            }
        }
        query = query + whereStr;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            value = c.getString(0);
        }
        db.close();

        return value;
    }

    public String getColumnData(String tableName, String columnName, String filters) {
        String value = "";
        String query = "select " + columnName + " from " + tableName + " WHERE ";
        String[] test = filters.substring(filters.indexOf("(") + 1, filters.lastIndexOf(")")).split("\\$");
        StringBuilder whereStr = new StringBuilder();
        for (int i = 0; i < test.length; i++) {
            if (test[i].startsWith("filter")) {
                String[] test1 = (test[i].substring(test[i].indexOf("(") + 1, test[i].lastIndexOf(")"))).split(",");
                String test2 = test1[2];
                whereStr.append(test1[0]).append(ImproveHelper.getOparator(test1[1])).append("'").append(test2).append("'");
            } else {
                whereStr.append(" ").append(test[i].toUpperCase()).append(" ");
            }
        }
        query = query + whereStr;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                if (c != null) {
                    value += c.getString(0) + ",";
                }
            } while (c.moveToNext());
        }
        db.close();
        if (value != null && value.length() > 0 && value.endsWith(",")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    public String createTableWhileInsertion(AppDetails appDetails) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String errorMsg = "";
        long result;
        try {
            String createTableMainForm = createTablesBasedOnDataTypeWithOrPrimaryKeys(appDetails.getAppName(), appDetails.getTableColumns(), replaceWithUnderscore(appDetails.getTableName()), appDetails.getPrimaryKey(), appDetails.getCompositeKey(), appDetails.getForeignKey(), false);
            if (!createTableMainForm.trim().equals("")) {
                db.execSQL(createTableMainForm);
            }
            if (appDetails.getSubFormDetails() != null) {
                for (SubFormTableColumns subFormTableColumns : appDetails.getSubFormDetails()) {
                    System.out.println("=====New Table=======");
                    //New Table Creating As per DataType To Cols
                    String createTableSubForm = createTablesBasedOnDataTypeWithOrPrimaryKeys(subFormTableColumns.getSubFormName(), subFormTableColumns.getTableColumns(), subFormTableColumns.getTableName(), subFormTableColumns.getPrimaryKey(), subFormTableColumns.getCompositeKey(), subFormTableColumns.getForeignKey(), true);
                    if (!createTableSubForm.trim().equals("")) {
                        db.execSQL(createTableSubForm);
                    }
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            errorMsg = e.getMessage();
        } finally {
            db.endTransaction();
        }
        return errorMsg;
    }

    public void deleteTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

    /* Get All Table Names From DB*/
    public List<String> getTableNamesFromDB() {
        List<String> tableNamesList = new ArrayList<>();
        String query = "SELECT name FROM sqlite_master WHERE type = 'table'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor != null) {
                    tableNamesList.add(cursor.getString(0));
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return tableNamesList;

    }

    /* Get All Table Names From DB*/
    public String getDesignFormat(String orgId, String appName) {
        String designFormat = "";
        String query = "SELECT " + DESIGN_FORMAT + " FROM " + APPS_LIST_TABLE + " WHERE " + APP_NAME + "='" + appName + "' and " + DB_ORG_ID + "='" + orgId + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor != null) {
                    designFormat = cursor.getString(0);
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return designFormat;

    }

    public String getDesignFormat(String appName) {
        String designFormat = "";
        String query = "SELECT " + DESIGN_FORMAT + " FROM " + APPS_LIST_TABLE + " WHERE " + APP_NAME + "='" + appName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor != null) {
                    designFormat = cursor.getString(0);
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return designFormat;

    }

    public long deleteSessionNotifications(String session_id, String group_id, String org_id) {
        long res = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        res = db.delete(NOTIFICATION_TABLE, "" + SESSION_ID + " = '" + session_id + "' and " + GROUP_ID + " = '" + group_id + "' and " + DB_ORG_ID + " = '" + org_id + "'", null);
        db.close();
        return res;
    }

    public String getTabCol(String tablename, String outcolumname) {
        String countQuery = "SELECT " + outcolumname + " FROM " + tablename;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        String SID = "";
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SID = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return SID;
    }

    private String getFormFieldsFilters(JSONArray filterColumnsList) {
        StringBuilder filters = new StringBuilder();
        try {
            for (int i = 0; i < filterColumnsList.length(); i++) {
                JSONObject filterColumn = filterColumnsList.getJSONObject(i);
                if (!filterColumn.has("ColumnType")) {
                    filters.append(" ").append(filterColumn.get("ColumnName")).append(" ").append(filterColumn.get("Oparator")).append(" ").append("'").append(filterColumn.get("Value")).append("'").append(" ").append(filterColumn.get("Condition"));
                }
            }
        } catch (Exception e) {

        }
        String filtersData = filters.toString();
        if (filtersData.endsWith("AND")) {
            filtersData = filtersData.substring(0, filtersData.length() - 3);
        }
        return filtersData;
    }

    public String getNearestLocations(String org_id, String tableName, JSONArray filtersData, String OrderByColumns, String Order) {
        JSONObject jsonObject = new JSONObject();
        SQLiteDatabase db = this.getWritableDatabase();
        String columnName = "";
        double nearBy = 0;
        String currentGPS = "";
        String noOfRec = "";
        try {
            JSONArray jsonArray = new JSONArray();
            jsonObject.put("Status", "200");
            jsonObject.put("Message", "Success");
            for (int i = 0; i < filtersData.length(); i++) {
                JSONObject jsonFilter = filtersData.getJSONObject(i);
                if (jsonFilter.has("ColumnType")) {
                    if (jsonFilter.getString("ColumnType").equalsIgnoreCase("GPS")) {
                        columnName = jsonFilter.getString("ColumnName");
                        nearBy = Float.parseFloat(jsonFilter.getString("NearBy"));
                        currentGPS = jsonFilter.getString("CurrentGPS");
                        noOfRec = jsonFilter.getString("NoOfRec");
                    }
                }
            }
            String OrganisationID = tableName + "_" + "OrganisationID";
            String query = "select * from " + tableName + " WHERE " + OrganisationID + " ='" + org_id + "'";

            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    if (cursor != null) {
                        for (int i = 0; i < cursor.getColumnCount(); i++) {
                            if (cursor.getColumnName(i).equalsIgnoreCase(columnName)) {
                                String location = cursor.getString(i);
                                PointF pointForCheck = new PointF(Float.parseFloat(location.split("\\$")[0]), Float.parseFloat(location.split("\\$")[1]));
                                PointF center = new PointF(Float.parseFloat(currentGPS.split("\\$")[0]), Float.parseFloat(currentGPS.split("\\$")[1]));
                                if (pointIsInCircle(pointForCheck, center, nearBy)) {
                                    JSONObject model = new JSONObject();
                                    for (int j = 0; j < cursor.getColumnCount(); j++) {
                                        model.put(cursor.getColumnName(j), cursor.getString(j));
                                    }
                                    jsonArray.put(model);
                                } else {
                                    cursor.moveToNext();
                                }
                            }
                        }
                    }
                } while (cursor.moveToNext());
            }
            jsonObject.put("FormData", jsonArray);
        } catch (Exception e) {

        }
        db.close();

        return jsonObject.toString();

    }

    public List<AppDetails> getAppsListForSearch(String org_id, String user_id, String strPostId, String userTypeId, String displayAs, String AppName) {
        List<AppDetails> modelList = new ArrayList<AppDetails>();
        Gson gson = new Gson();
       /* String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DISPLAY_IN_APP_LIST + " = '" + true + "' AND "+APP_NAME+" like '%"+AppName+"%' AND " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and (" +
                POST_ID + " = '" + strPostId + "' or " + POST_ID + "= '0' or " +
                POST_ID + " LIKE '%" + strPostId + "%' or " +
                POST_ID + " = '' or " + POST_ID + " LIKE '%0$0%'" + ") and "
                + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " ==''  ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";*/
        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + DISPLAY_IN_APP_LIST + " = '" + true + "' AND " + DISPLAYAPPNAME + " like '%" + AppName + "%' AND " + DB_ORG_ID + " = '" + org_id + "' and " + USER_ID + " = '" + user_id + "' and (" +
                POST_ID + " = '" + strPostId + "' or " + USER_TYPE_IDS + "= '" + userTypeId + "') and "
                + DISTRIBUTION_ID + " != '' and " + WORKSPACEName + " !=''  ORDER BY CAST(" + DISTRIBUTION_ID + " as integer) DESC";

        Log.d(TAG, "getDataFromAppsListTable: " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AppDetails model = new AppDetails();
                model.setDistrubutionID(cursor.getString(3));
                model.setCreatedBy(cursor.getString(4));
                model.setCreatedUserName(cursor.getString(5));
                model.setDescription(cursor.getString(6));
                model.setIsActive(cursor.getString(7));
                model.setAppName(cursor.getString(8));
//                model.setAppName(cursor.getString(31));
                model.setAppVersion(cursor.getString(9));
                model.setAppType(cursor.getString(10));
                model.setDesignFormat("");//cursor.getString(11));
                model.setVideoLink(cursor.getString(12));
                model.setNewRow(cursor.getString(13));
                model.setAppIcon(cursor.getString(14));
                model.setApkVersion(cursor.getString(15));
                model.setApplicationReceivedDate(cursor.getString(16));
                model.setXMLFilePath(cursor.getString(17));
                model.setAppMode(cursor.getString(20));
//                if (strPostId != null) {
                model.setPostID(cursor.getString(22));
                model.setDisplayAs(cursor.getString(23));
                model.setDisplayAppName(cursor.getString(31));
                model.setDisplayIcon(cursor.getString(47));
                //nk
                model.setTableName(cursor.getString(32));
                model.setTableColumns(cursor.getString(33));
                model.setPrimaryKey(cursor.getString(34));

                Type typeAppsForeignKey = new TypeToken<List<ForeignKey>>() {
                }.getType();
                List<ForeignKey> foreignKeyList = gson.fromJson(cursor.getString(35), typeAppsForeignKey);
                model.setForeignKey(foreignKeyList);
                model.setCompositeKey(cursor.getString(36));
                List<SubFormTableColumns> subFormTableColumnsList = new ArrayList<>();
                if (cursor.getString(37) != null) {
                    Type typeApps = new TypeToken<List<SubFormTableColumns>>() {
                    }.getType();
                    subFormTableColumnsList = gson.fromJson(cursor.getString(37), typeApps);
                    model.setSubFormDetails(subFormTableColumnsList);
                }
                model.setUserTypeID(cursor.getString(39));
                model.setIsDataPermission(cursor.getString(40));
                model.setDataPermissionXML(cursor.getString(41));
                model.setDataPermissionXmlFilePath(cursor.getString(42));
                model.setIsControlVisibility(cursor.getString(43));
                model.setControlVisibilityXML(cursor.getString(44));
                model.setControlVisibilityXmlFilePath(cursor.getString(45));
                List<AutoIncrementControl> autoIncrementControlList = new ArrayList<>();
                if (cursor.getString(48) != null) {
                    Type typeApps = new TypeToken<List<AutoIncrementControl>>() {
                    }.getType();
                    autoIncrementControlList = gson.fromJson(cursor.getString(48), typeApps);
                    model.setAutoIncrementControlNames(autoIncrementControlList);
                }
                if (model.getAppType().equalsIgnoreCase(AppConstants.ML)) {
                    model.setTraining(cursor.getString(26));
                    modelList.add(model);
                    if (cursor.getString(27) != null && cursor.getString(27).equalsIgnoreCase("True")) {
                        AppDetails model1 = new AppDetails();
                        model1.setDistrubutionID(cursor.getString(3));
                        model1.setCreatedBy(cursor.getString(4));
                        model1.setCreatedUserName(cursor.getString(5));
                        model1.setDescription(cursor.getString(6));
                        model1.setIsActive(cursor.getString(7));
                        model1.setAppName(cursor.getString(8));
                        model1.setAppVersion(cursor.getString(9));
                        model1.setAppType(cursor.getString(10));
                        model1.setDesignFormat("");//cursor.getString(11));
                        model1.setVideoLink(cursor.getString(12));
                        model1.setNewRow(cursor.getString(13));
                        model1.setAppIcon(cursor.getString(14));
                        model1.setApkVersion(cursor.getString(15));
                        model1.setApplicationReceivedDate(cursor.getString(16));
                        model1.setXMLFilePath(cursor.getString(17));
                        model1.setAppMode(cursor.getString(20));
                        model1.setPostID(cursor.getString(22));
                        model1.setTesting(cursor.getString(27));
                        //nk
                        model1.setTableName(cursor.getString(32));
                        model1.setTableColumns(cursor.getString(33));
                        model1.setPrimaryKey(cursor.getString(34));
                        model1.setForeignKey(foreignKeyList);
                        model1.setCompositeKey(cursor.getString(36));
                        model.setSubFormDetails(subFormTableColumnsList);
                        modelList.add(model1);
                    }
                } else {
                    modelList.add(model);
                }


            } while (cursor.moveToNext());
        }

        Log.d("RetrieveAppsList", modelList.toString());

        return modelList;

    }

    public int deleteWorkspaceAppsList(String WorkspaceName, String distributionId) {
        int msg = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        msg = db.delete(APPS_LIST_TABLE, WORKSPACEName + "=?  and " + DISTRIBUTION_ID + "=?", new String[]{WorkspaceName, distributionId});
        msg = db.delete(APPS_LIST_TABLE, APP_NAME + "=? and " + DISTRIBUTION_ID + "=?", new String[]{WorkspaceName, distributionId});
        db.close();
        return msg;
    }

    public int deleteWorkspaceAppsListApps(String WorkspaceName, String distributionId) {
        Log.d(TAG, "deleteWorkspaceAppsListApps: " + WorkspaceName + " DID " + distributionId);
        int msg = 0;
        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        msg = db.delete(APPS_LIST_TABLE, APP_NAME + "=? and "+DISTRIBUTION_ID+ "=?" , new String[]{WorkspaceName,distributionId});
        msg = db.delete(APPS_LIST_TABLE, APP_NAME + "=? and " + DISTRIBUTION_ID + "=? and " + WORKSPACEName + "=?", new String[]{WorkspaceName, distributionId, ""});
        db.close();
        return msg;
    }

    /* Retrive  Session chat messages list data from database */
    public boolean checkAppExists(String app_name) {

        String query = "select * from " + APPS_LIST_TABLE + " WHERE " + APP_NAME + " = '" + app_name + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }

    public String getRowid(String tableName, String whereClause) {
        String rowid = "";
        String query = "SELECT rowid FROM " + tableName + " WHERE " + whereClause + "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor != null) {
                    rowid = cursor.getString(0);
                }
            } while (cursor.moveToNext());
        }
        return rowid;

    }

    public boolean columnExistsInTable(String table, String columnToCheck) {
        Cursor cursor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            cursor = db.rawQuery("SELECT * FROM " + table + " LIMIT 0", null);

            // getColumnIndex()  will return the index of the column
            //in the table if it exists, otherwise it will return -1
            if (cursor.getColumnIndex(columnToCheck) != -1) {
                //great, the column exists
                return true;
            }else {
                //sorry, the column does not exist
                return false;
            }

        } catch (SQLiteException Exp) {
            //Something went wrong with SQLite.
            //If the table exists and your query was good,
            //the problem is likely that the column doesn't exist in the table.
            return false;
        } finally {
            //close the db  if you no longer need it
            if (db != null) db.close();
            //close the cursor
            if (cursor != null) cursor.close();
        }
    }
}
