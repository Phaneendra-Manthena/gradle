package com.bhargo.user.Java_Beans;

import com.bhargo.user.pojos.SublocationsModel;
import com.bhargo.user.pojos.UserPostDetails;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by SantoshB on 30/10/2019.
 */

public class GlobalObjects implements Serializable {

    private String User_Role,User_ID,User_Name,User_MobileNo,User_Email,User_Desigination,User_Department,User_location,User_location_name,User_Level,LocatonLevel;
    private String Reporter_Role,Reporter_ID,Reporter_Name,Reporter_MobileNo,Reporter_Email,Reporter_Desigination,Reporter_Department,Reporter_Level;
    private String Org_Name,Mobile_Date,Mobile_Time,Mobile_IME_No,Current_GPS;
    private String User_PostID, User_PostName,Reporting_PostID,Reporting_PostDepartmentID,User_Post_Location,User_Post_Location_Name,user_type,user_type_id,ManualReportingPostID,strManualReportingPersonID;
    String login_status;
    List<SublocationsModel> Sublocations;
    private String appLanguage="en";
     private String submitresponse_Status,submitresponse_Message;
     private JSONArray autoIdsResponseArray;
    private String bhargoLoginStatus;
    private String bhargoLoginMessage;

    private List<UserPostDetails> userPostDetailsList;
    private LinkedHashMap<String,GO_API_Object> API_Object;
    private LinkedHashMap<String,GO_Form_Objects> Form_Object;
    private LinkedHashMap<String,GO_Query_Objects> Query_Object;

    private List<String> CurrentApp_ControlNames;
    private LinkedHashMap<String,LinkedHashMap<String,String>> SMS_ListHash = new LinkedHashMap<>();

    public LinkedHashMap<String, LinkedHashMap<String, String>> getGetData_ResponseHashMap() {
        return GetData_ResponseHashMap;
    }

    public void setGetData_ResponseHashMap(LinkedHashMap<String, LinkedHashMap<String, String>> getData_ResponseHashMap) {
        GetData_ResponseHashMap = getData_ResponseHashMap;
    }

    ////nk realm:
    //private LinkedHashMap<String,LinkedHashMap<String,List<String>>> SubControls_List = new LinkedHashMap<>();
    //private LinkedHashMap<String,LinkedHashMap<String,List<String>>> APIs_ListHash = new LinkedHashMap<>();
    //private LinkedHashMap<String,LinkedHashMap<String,List<String>>> Querys_ListHash = new LinkedHashMap<>();
    //private LinkedHashMap<String,LinkedHashMap<String,List<String>>> Forms_ListHash = new LinkedHashMap<>();
    //private LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,List<String>>>> allApisList_Hash = new LinkedHashMap<>();
    //private LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,List<String>>>> allFormsList_Hash = new LinkedHashMap<>();
    //private LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,List<String>>>> allQueriesList_Hash = new LinkedHashMap<>();
    //private LinkedHashMap<String,LinkedHashMap<String,List<String>>> APIsRequestOfflineData_ListHash;
    //private LinkedHashMap<String,LinkedHashMap<String,List<String>>> DataControls_ListHash;

    private LinkedHashMap<String,LinkedHashMap<String,String>> GetData_ResponseHashMap = new LinkedHashMap<>();

    public LinkedHashMap<String, LinkedHashMap<String, String>> getManageData_ResponseHashMap() {
        return ManageData_ResponseHashMap;
    }

    public void setManageData_ResponseHashMap(LinkedHashMap<String, LinkedHashMap<String, String>> manageData_ResponseHashMap) {
        ManageData_ResponseHashMap = manageData_ResponseHashMap;
    }

    private LinkedHashMap<String,LinkedHashMap<String,String>> ManageData_ResponseHashMap = new LinkedHashMap<>();

    LinkedHashMap<String,String> APIs_status_ListHash = new LinkedHashMap<>();
    LinkedHashMap<String,String> Formfields_status_ListHash = new LinkedHashMap<>();
    LinkedHashMap<String,String> SQL_status_ListHash = new LinkedHashMap<>();
    LinkedHashMap<String,String> DML_status_ListHash = new LinkedHashMap<>();



    private LinkedHashMap<String,String> ScanQRCode_ListHash;


    public LinkedHashMap<String, GO_Query_Objects> getQuery_Object() {
        return Query_Object;
    }

    public void setQuery_Object(LinkedHashMap<String, GO_Query_Objects> query_Object) {
        Query_Object = query_Object;
    }

    public LinkedHashMap<String, GO_Form_Objects> getForm_Object() {
        return Form_Object;
    }

    public void setForm_Object(LinkedHashMap<String, GO_Form_Objects> form_Object) {
        Form_Object = form_Object;
    }

    public LinkedHashMap<String, GO_API_Object> getAPI_Object() {
        return API_Object;
    }

    public void setAPI_Object(LinkedHashMap<String, GO_API_Object> API_Object) {
        this.API_Object = API_Object;
    }

    public LinkedHashMap<String, LinkedHashMap<String, String>> getSMS_ListHash() {
        return SMS_ListHash;
    }

    public void setSMS_ListHash(LinkedHashMap<String, LinkedHashMap<String, String>> SMS_ListHash) {
        this.SMS_ListHash = SMS_ListHash;
    }
    public String getUser_Role() {
        return User_Role;
    }

    public void setUser_Role(String user_Role) {
        User_Role = user_Role;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getUser_MobileNo() {
        return User_MobileNo;
    }

    public void setUser_MobileNo(String user_MobileNo) {
        User_MobileNo = user_MobileNo;
    }

    public String getUser_Email() {
        return User_Email;
    }

    public void setUser_Email(String user_Email) {
        User_Email = user_Email;
    }

    public String getUser_Desigination() {
        return User_Desigination;
    }

    public void setUser_Desigination(String user_Desigination) {
        User_Desigination = user_Desigination;
    }

    public String getUser_Department() {
        return User_Department;
    }

    public void setUser_Department(String user_Department) {
        User_Department = user_Department;
    }

    public String getUser_location() {
        return User_location;
    }

    public void setUser_location(String user_location) {
        User_location = user_location;
    }

    public String getUser_Level() {
        return User_Level;
    }

    public void setUser_Level(String user_Level) {
        User_Level = user_Level;
    }

    public String getReporter_Role() {
        return Reporter_Role;
    }

    public void setReporter_Role(String reporter_Role) {
        Reporter_Role = reporter_Role;
    }

    public String getReporter_ID() {
        return Reporter_ID;
    }

    public void setReporter_ID(String reporter_ID) {
        Reporter_ID = reporter_ID;
    }

    public String getReporter_Name() {
        return Reporter_Name;
    }

    public void setReporter_Name(String reporter_Name) {
        Reporter_Name = reporter_Name;
    }

    public String getReporter_MobileNo() {
        return Reporter_MobileNo;
    }

    public void setReporter_MobileNo(String reporter_MobileNo) {
        Reporter_MobileNo = reporter_MobileNo;
    }

    public String getReporter_Email() {
        return Reporter_Email;
    }

    public void setReporter_Email(String reporter_Email) {
        Reporter_Email = reporter_Email;
    }

    public String getReporter_Desigination() {
        return Reporter_Desigination;
    }

    public void setReporter_Desigination(String reporter_Desigination) {
        Reporter_Desigination = reporter_Desigination;
    }

    public String getReporter_Department() {
        return Reporter_Department;
    }

    public void setReporter_Department(String reporter_Department) {
        Reporter_Department = reporter_Department;
    }

    public String getReporter_Level() {
        return Reporter_Level;
    }

    public void setReporter_Level(String reporter_Level) {
        Reporter_Level = reporter_Level;
    }

    public String getOrg_Name() {
        return Org_Name;
    }

    public void setOrg_Name(String org_Name) {
        Org_Name = org_Name;
    }

    public String getMobile_Date() {
        return Mobile_Date;
    }

    public void setMobile_Date(String mobile_Date) {
        Mobile_Date = mobile_Date;
    }

    public String getMobile_Time() {
        return Mobile_Time;
    }

    public void setMobile_Time(String mobile_Time) {
        Mobile_Time = mobile_Time;
    }

    public String getMobile_IME_No() {
        return Mobile_IME_No;
    }

    public void setMobile_IME_No(String mobile_IME_No) {
        Mobile_IME_No = mobile_IME_No;
    }

    public String getCurrent_GPS() {
        return Current_GPS;
    }

    public void setCurrent_GPS(String current_GPS) {
        Current_GPS = current_GPS;
    }

    public List<String> getCurrentApp_ControlNames() {
        return CurrentApp_ControlNames;
    }

    public void setCurrentApp_ControlNames(List<String> currentApp_ControlNames) {
        CurrentApp_ControlNames = currentApp_ControlNames;
    }

    /*public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getSubControls_List() {
        return SubControls_List;
    }

    public void setSubControls_List(LinkedHashMap<String, LinkedHashMap<String, List<String>>> subControls_List) {
        SubControls_List = subControls_List;
    }*/

   /* public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getAPIs_ListHash() {
        return APIs_ListHash;
    }

    public void setAPIs_ListHash(LinkedHashMap<String, LinkedHashMap<String, List<String>>> APIs_ListHash) {
        this.APIs_ListHash = APIs_ListHash;
    }*/

    /*public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getQuerys_ListHash() {
        return Querys_ListHash;
    }

    public void setQuerys_ListHash(LinkedHashMap<String, LinkedHashMap<String, List<String>>> querys_ListHash) {
        Querys_ListHash = querys_ListHash;
    }*/
//nk for realm
   /* public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getForms_ListHash() {
        return Forms_ListHash;
    }

    public void setForms_ListHash(LinkedHashMap<String, LinkedHashMap<String, List<String>>> forms_ListHash) {
        Forms_ListHash = forms_ListHash;
    }*/
    public String getUser_PostID() {
        return User_PostID;
    }

    public void setUser_PostID(String user_PostID) {
        User_PostID = user_PostID;
    }

    public String getUser_PostName() {
        return User_PostName;
    }

    public void setUser_PostName(String user_PostName) {
        User_PostName = user_PostName;
    }

    public String getReporting_PostID() {
        return Reporting_PostID;
    }

    public void setReporting_PostID(String reporting_PostID) {
        Reporting_PostID = reporting_PostID;
    }

    public String getReporting_PostDepartmentID() {
        return Reporting_PostDepartmentID;
    }

    public void setReporting_PostDepartmentID(String reporting_PostDepartmentID) {
        Reporting_PostDepartmentID = reporting_PostDepartmentID;
    }

    public List<UserPostDetails> getUserPostDetailsList() {
        return userPostDetailsList;
    }

    public void setUserPostDetailsList(List<UserPostDetails> userPostDetailsList) {
        this.userPostDetailsList = userPostDetailsList;
    }

    public String getLocatonLevel() {
        return LocatonLevel;
    }

    public void setLocatonLevel(String locatonLevel) {
        LocatonLevel = locatonLevel;
    }

    public List<SublocationsModel> getSublocations() {
        return Sublocations;
    }

    public void setSublocations(List<SublocationsModel> sublocations) {
        Sublocations = sublocations;
    }

  /*  public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> getAllApisList_Hash() {
        return allApisList_Hash;
    }

    public void setAllApisList_Hash(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> allApisList_Hash) {
        this.allApisList_Hash = allApisList_Hash;
    }*/

    //nk realm: object remove because of realm
    /*public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> getAllFormsList_Hash() {
        return allFormsList_Hash;
    }

    public void setAllFormsList_Hash(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> allFormsList_Hash) {
        this.allFormsList_Hash = allFormsList_Hash;
    }*/

    /*public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> getAllQueriesList_Hash() {
        return allQueriesList_Hash;
    }

    public void setAllQueriesList_Hash(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> allQueriesList_Hash) {
        this.allQueriesList_Hash = allQueriesList_Hash;
    }*/

    public String getAppLanguage() {
        return appLanguage;
    }

    public void setAppLanguage(String appLanguage) {
        this.appLanguage = appLanguage;
    }

    public String getSubmitresponse_Status() {
        return submitresponse_Status;
    }

    public void setSubmitresponse_Status(String submitresponse_Status) {
        this.submitresponse_Status = submitresponse_Status;
    }

    public String getSubmitresponse_Message() {
        return submitresponse_Message;
    }

    public void setSubmitresponse_Message(String submitresponse_Message) {
        this.submitresponse_Message = submitresponse_Message;
    }

    /*public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getAPIsRequestOfflineData_ListHash() {
        return APIsRequestOfflineData_ListHash;
    }

    public void setAPIsRequestOfflineData_ListHash(LinkedHashMap<String, LinkedHashMap<String, List<String>>> APIsRequestOfflineData_ListHash) {
        this.APIsRequestOfflineData_ListHash = APIsRequestOfflineData_ListHash;
    }*/

    /*public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getDataControls_ListHash() {
        return DataControls_ListHash;
    }

    public void setDataControls_ListHash(LinkedHashMap<String, LinkedHashMap<String, List<String>>> dataControls_ListHash) {
        DataControls_ListHash = dataControls_ListHash;
    }*/

    public LinkedHashMap<String, String> getScanQRCode_ListHash() {
        return ScanQRCode_ListHash;
    }

    public void setScanQRCode_ListHash(LinkedHashMap<String, String> scanQRCode_ListHash) {
        ScanQRCode_ListHash = scanQRCode_ListHash;
    }

    public LinkedHashMap<String, String> getAPIs_status_ListHash() {
        return APIs_status_ListHash;
    }

    public void setAPIs_status_ListHash(LinkedHashMap<String, String> APIs_status_ListHash) {
        this.APIs_status_ListHash = APIs_status_ListHash;
    }

    public LinkedHashMap<String, String> getFormfields_status_ListHash() {
        return Formfields_status_ListHash;
    }

    public void setFormfields_status_ListHash(LinkedHashMap<String, String> formfields_status_ListHash) {
        Formfields_status_ListHash = formfields_status_ListHash;
    }

    public LinkedHashMap<String, String> getSQL_status_ListHash() {
        return SQL_status_ListHash;
    }

    public void setSQL_status_ListHash(LinkedHashMap<String, String> SQL_status_ListHash) {
        this.SQL_status_ListHash = SQL_status_ListHash;
    }

    public LinkedHashMap<String, String> getDML_status_ListHash() {
        return DML_status_ListHash;
    }

    public void setDML_status_ListHash(LinkedHashMap<String, String> DML_status_ListHash) {
        this.DML_status_ListHash = DML_status_ListHash;
    }

    public String getLogin_status() {
        return login_status;
    }

    public void setLogin_status(String login_status) {
        this.login_status = login_status;
    }

    public String getUser_location_name() {
        return User_location_name;
    }

    public void setUser_location_name(String user_location_name) {
        User_location_name = user_location_name;
    }

    public String getUser_Post_Location() {
        return User_Post_Location;
    }

    public void setUser_Post_Location(String user_Post_Location) {
        User_Post_Location = user_Post_Location;
    }

    public String getUser_Post_Location_Name() {
        return User_Post_Location_Name;
    }

    public void setUser_Post_Location_Name(String user_Post_Location_Name) {
        User_Post_Location_Name = user_Post_Location_Name;
    }

    public JSONArray getAutoIdsResponseArray() {
        return autoIdsResponseArray;
    }

    public void setAutoIdsResponseArray(JSONArray autoIdsResponseArray) {
        this.autoIdsResponseArray = autoIdsResponseArray;
    }

    public String getBhargoLoginStatus() {
        return bhargoLoginStatus;
    }

    public void setBhargoLoginStatus(String bhargoLoginStatus) {
        this.bhargoLoginStatus = bhargoLoginStatus;
    }

    public String getBhargoLoginMessage() {
        return bhargoLoginMessage;
    }

    public void setBhargoLoginMessage(String bhargoLoginMessage) {
        this.bhargoLoginMessage = bhargoLoginMessage;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_type_id() {
        return user_type_id;
    }

    public void setUser_type_id(String user_type_id) {
        this.user_type_id = user_type_id;
    }

    public String getManualReportingPostID() {
        return ManualReportingPostID;
    }

    public void setManualReportingPostID(String manualReportingPostID) {
        ManualReportingPostID = manualReportingPostID;
    }

    public String getStrManualReportingPersonID() {
        return strManualReportingPersonID;
    }

    public void setStrManualReportingPersonID(String strManualReportingPersonID) {
        this.strManualReportingPersonID = strManualReportingPersonID;
    }
}
