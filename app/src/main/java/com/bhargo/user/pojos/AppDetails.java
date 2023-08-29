package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.List;

public class AppDetails implements Serializable {

    List<WorkSpaceAppsList> WorkSpaceAppsList;
    private String DistrubutionID;
    private String CreatedBy;
    private String CreatedUserName;
    private String Description;
    private String IsActive;
    private String AppName;
    private String AppVersion;
    private String DesignFormat;
    private String VideoLink;
    private String NewRow;
    private String AppIcon;
    private String AppType;
    private String ApkVersion;
    private String ApplicationReceivedDate;
    private String XMLFilePath;
    private String AppMode;
    private String DownloadURls;
    private String Z_Status_flag;
    private String PostID;
    private String DisplayAs;
    private String DisplayReportas;
    private String UserLocation;
    private String Training;
    private String Testing;
    private String VisibileIn;
    private String WorkspaceAs;
    private String DisplayAppName;
    private String TableSettingsType;
    private String TableName;
    private String TableColumns;
    private String PrimaryKey;
    private List<ForeignKey> ForeignKey;
    private String CompositeKey;
    private String UserTypeID;

    private String IsDataPermission;
    private String DataPermissionXML;
    private String DataPermissionXmlFilePath;

    private String IsControlVisibility;
    private String ControlVisibilityXML;
    private String ControlVisibilityXmlFilePath;
    private List<AutoIncrementControl> AutoIncrementControlNames;
    private List<SubFormTableColumns> SubFormDetails;
    private boolean DisplayInAppList;
    private String SharingVersion;
    private String DisplayIcon;
    private int adapterPosition=-1;
    private String voiceToTextContent;

    public int getAdapterPosition() {
        return adapterPosition;
    }

    public void setAdapterPosition(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    public String getVoiceToTextContent() {
        return voiceToTextContent;
    }

    public void setVoiceToTextContent(String voiceToTextContent) {
        this.voiceToTextContent = voiceToTextContent;
    }

    public String getSharingVersion() {
        return SharingVersion;
    }

    public void setSharingVersion(String sharingVersion) {
        SharingVersion = sharingVersion;
    }

    public String getDisplayIcon() {
        return DisplayIcon;
    }

    public void setDisplayIcon(String displayIcon) {
        DisplayIcon = displayIcon;
    }

    public FormLevelTranslation getFormLevelTranslation() {
        return formLevelTranslation;
    }

    private FormLevelTranslation formLevelTranslation;

    public boolean isDisplayInAppList() {
        return DisplayInAppList;
    }

    public void setDisplayInAppList(boolean displayInAppList) {
        DisplayInAppList = displayInAppList;
    }

    public List<SubFormTableColumns> getSubFormDetails() {
        return SubFormDetails;
    }

    public void setSubFormDetails(List<SubFormTableColumns> subFormDetails) {
        SubFormDetails = subFormDetails;
    }

    public List<AutoIncrementControl> getAutoIncrementControlNames() {
        return AutoIncrementControlNames;
    }

    public void setAutoIncrementControlNames(List<AutoIncrementControl> autoIncrementControlNames) {
        AutoIncrementControlNames = autoIncrementControlNames;
    }

    public String getTableSettingsType() {
        return TableSettingsType;
    }

    public void setTableSettingsType(String tableSettingsType) {
        TableSettingsType = tableSettingsType;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getPrimaryKey() {
        return PrimaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        PrimaryKey = primaryKey;
    }

    public List<ForeignKey> getForeignKey() {

        return ForeignKey;
    }


    public void setForeignKey(List<ForeignKey> foreignKey) {
        ForeignKey = foreignKey;
    }

    public String getCompositeKey() {
        return CompositeKey;
    }

    public void setCompositeKey(String compositeKey) {
        CompositeKey = compositeKey;
    }

    public String getTableColumns() {
        return TableColumns;
    }

    public void setTableColumns(String tableColumns) {
        TableColumns = tableColumns;
    }

    public String getDistrubutionID() {
        return DistrubutionID;
    }

    public void setDistrubutionID(String distrubutionID) {
        DistrubutionID = distrubutionID;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedUserName() {
        return CreatedUserName;
    }

    public void setCreatedUserName(String createdUserName) {
        CreatedUserName = createdUserName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getDesignFormat() {
        return DesignFormat;
    }

    public void setDesignFormat(String designFormat) {
        DesignFormat = designFormat;
    }

    public String getVideoLink() {
        return VideoLink;
    }

    public void setVideoLink(String videoLink) {
        VideoLink = videoLink;
    }

    public String getNewRow() {
        return NewRow;
    }

    public void setNewRow(String newRow) {
        NewRow = newRow;
    }

    public String getAppIcon() {
        return AppIcon;
    }

    public void setAppIcon(String appIcon) {
        AppIcon = appIcon;
    }

    public String getApkVersion() {
        return ApkVersion;
    }

    public void setApkVersion(String apkVersion) {
        ApkVersion = apkVersion;
    }

    public String getApplicationReceivedDate() {
        return ApplicationReceivedDate;
    }

    public void setApplicationReceivedDate(String applicationReceivedDate) {
        ApplicationReceivedDate = applicationReceivedDate;
    }

    public String getXMLFilePath() {
        return XMLFilePath;
    }

    public void setXMLFilePath(String XMLFilePath) {
        this.XMLFilePath = XMLFilePath;
    }

    public String getAppMode() {
        return AppMode;
    }

    public void setAppMode(String appMode) {
        AppMode = appMode;
    }

    public String getDownloadURls() {
        return DownloadURls;
    }

    public void setDownloadURls(String downloadURls) {
        DownloadURls = downloadURls;
    }

    public String getAppType() {
        return AppType;
    }

    public void setAppType(String appType) {
        AppType = appType;
    }

    public String getZ_Status_flag() {
        return Z_Status_flag;
    }

    public void setZ_Status_flag(String z_Status_flag) {
        Z_Status_flag = z_Status_flag;
    }

    public String getPostID() {
        return PostID;
    }

    public void setPostID(String postID) {
        PostID = postID;
    }

    public String getDisplayAs() {
        return DisplayAs;
    }

    public void setDisplayAs(String displayAs) {
        DisplayAs = displayAs;
    }

    public String getDisplayReportas() {
        return DisplayReportas;
    }

    public void setDisplayReportas(String displayReportas) {
        DisplayReportas = displayReportas;
    }

    public String getUserLocation() {
        return UserLocation;
    }

    public void setUserLocation(String userLocation) {
        UserLocation = userLocation;
    }

    public String getTraining() {
        return Training;
    }

    public void setTraining(String training) {
        Training = training;
    }

    public String getTesting() {
        return Testing;
    }

    public void setTesting(String testing) {
        Testing = testing;
    }

    public String getVisibileIn() {
        return VisibileIn;
    }

    public void setVisibileIn(String visibileIn) {
        VisibileIn = visibileIn;
    }

    public List<AppDetails.WorkSpaceAppsList> getWorkSpaceAppsList() {
        return WorkSpaceAppsList;
    }

    public void setWorkSpaceAppsList(List<AppDetails.WorkSpaceAppsList> workSpaceAppsList) {
        WorkSpaceAppsList = workSpaceAppsList;
    }

    public String getWorkspaceAs() {
        return WorkspaceAs;
    }

    public void setWorkspaceAs(String workspaceAs) {
        WorkspaceAs = workspaceAs;
    }

    public String getDisplayAppName() {
        return DisplayAppName;
    }

    public void setDisplayAppName(String displayAppName) {
        DisplayAppName = displayAppName;
    }


//    public String getADDColumns() {
//        return ADDColumns;
//    }
//
//    public void setADDColumns(String ADDColumns) {
//        this.ADDColumns = ADDColumns;
//    }
//
//    public String getDELETEColumns() {
//        return DELETEColumns;
//    }
//
//    public void setDELETEColumns(String DELETEColumns) {
//        this.DELETEColumns = DELETEColumns;
//    }


    public class WorkSpaceAppsList implements Serializable {

        private String DistrubutionID;

        private String CreatedBy;

        private String CreatedUserName;

        private String Description;

        private String IsActive;

        private String AppName;

        private String AppVersion;

        private String DesignFormat;

        private String VideoLink;

        private String NewRow;

        private String AppIcon;

        private String AppType;

        private String ApkVersion;

        private String ApplicationReceivedDate;

        private String XMLFilePath;
        private String AppMode;
        private String DownloadURls;
        private String Z_Status_flag;
        private String PostID;
        private String DisplayAs;
        private String DisplayReportas;
        private String UserLocation;
        private String Training;
        private String Testing;
        private String VisibileIn;
        private String WorkspaceAs;
        private String DisplayAppName;
        private String TableSettingsType;
        private String TableName;
        private String TableColumns;
        private String PrimaryKey;
        private List<ForeignKey> ForeignKey;
        private String CompositeKey;
        private List<SubFormTableColumns> SubFormDetails;

        private String IsDataPermission;
        private String DataPermissionXML;
        private String DataPermissionXmlFilePath;

        private String IsControlVisibility;
        private String ControlVisibilityXML;
        private String ControlVisibilityXmlFilePath;

        private List<AutoIncrementControl> AutoIncrementControlNames;
        private String SharingVersion;
        private String DisplayIcon;

        public String getSharingVersion() {
            return SharingVersion;
        }

        public void setSharingVersion(String sharingVersion) {
            SharingVersion = sharingVersion;
        }

        public String getDisplayIcon() {
            return DisplayIcon;
        }

        public void setDisplayIcon(String displayIcon) {
            DisplayIcon = displayIcon;
        }

        public String getTableSettingsType() {
            return TableSettingsType;
        }

        public void setTableSettingsType(String tableSettingsType) {
            TableSettingsType = tableSettingsType;
        }

        public String getTableName() {
            return TableName;
        }

        public void setTableName(String tableName) {
            TableName = tableName;
        }

        public String getTableColumns() {
            return TableColumns;
        }

        public void setTableColumns(String tableColumns) {
            TableColumns = tableColumns;
        }

        public String getPrimaryKey() {
            return PrimaryKey;
        }

        public void setPrimaryKey(String primaryKey) {
            PrimaryKey = primaryKey;
        }


        public List<ForeignKey> getForeignKey() {
            return ForeignKey;
        }

        public void setForeignKey(List<ForeignKey> foreignKey) {
            ForeignKey = foreignKey;
        }

        public String getCompositeKey() {
            return CompositeKey;
        }

        public void setCompositeKey(String compositeKey) {
            CompositeKey = compositeKey;
        }

        public List<SubFormTableColumns> getSubFormDetails() {
            return SubFormDetails;
        }

        public void setSubFormDetails(List<SubFormTableColumns> subFormDetails) {
            SubFormDetails = subFormDetails;
        }

        public String getIsDataPermission() {
            return IsDataPermission;
        }

        public void setIsDataPermission(String isDataPermission) {
            IsDataPermission = isDataPermission;
        }

        public String getDataPermissionXML() {
            return DataPermissionXML;
        }

        public void setDataPermissionXML(String dataPermissionXML) {
            DataPermissionXML = dataPermissionXML;
        }

        public String getDataPermissionXmlFilePath() {
            return DataPermissionXmlFilePath;
        }

        public void setDataPermissionXmlFilePath(String dataPermissionXmlFilePath) {
            DataPermissionXmlFilePath = dataPermissionXmlFilePath;
        }

        public String getIsControlVisibility() {
            return IsControlVisibility;
        }

        public void setIsControlVisibility(String isControlVisibility) {
            IsControlVisibility = isControlVisibility;
        }

        public String getControlVisibilityXML() {
            return ControlVisibilityXML;
        }

        public void setControlVisibilityXML(String controlVisibilityXML) {
            ControlVisibilityXML = controlVisibilityXML;
        }

        public String getControlVisibilityXmlFilePath() {
            return ControlVisibilityXmlFilePath;
        }

        public void setControlVisibilityXmlFilePath(String controlVisibilityXmlFilePath) {
            ControlVisibilityXmlFilePath = controlVisibilityXmlFilePath;
        }

        public List<AutoIncrementControl> getAutoIncrementControlNames() {
            return AutoIncrementControlNames;
        }

        public void setAutoIncrementControlNames(List<AutoIncrementControl> autoIncrementControlNames) {
            AutoIncrementControlNames = autoIncrementControlNames;
        }

        public String getDistrubutionID() {
            return DistrubutionID;
        }

        public void setDistrubutionID(String distrubutionID) {
            DistrubutionID = distrubutionID;
        }

        public String getCreatedBy() {
            return CreatedBy;
        }

        public void setCreatedBy(String createdBy) {
            CreatedBy = createdBy;
        }

        public String getCreatedUserName() {
            return CreatedUserName;
        }

        public void setCreatedUserName(String createdUserName) {
            CreatedUserName = createdUserName;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getIsActive() {
            return IsActive;
        }

        public void setIsActive(String isActive) {
            IsActive = isActive;
        }

        public String getAppName() {
            return AppName;
        }

        public void setAppName(String appName) {
            AppName = appName;
        }

        public String getAppVersion() {
            return AppVersion;
        }

        public void setAppVersion(String appVersion) {
            AppVersion = appVersion;
        }

        public String getDesignFormat() {
            return DesignFormat;
        }

        public void setDesignFormat(String designFormat) {
            DesignFormat = designFormat;
        }

        public String getVideoLink() {
            return VideoLink;
        }

        public void setVideoLink(String videoLink) {
            VideoLink = videoLink;
        }

        public String getNewRow() {
            return NewRow;
        }

        public void setNewRow(String newRow) {
            NewRow = newRow;
        }

        public String getAppIcon() {
            return AppIcon;
        }

        public void setAppIcon(String appIcon) {
            AppIcon = appIcon;
        }

        public String getAppType() {
            return AppType;
        }

        public void setAppType(String appType) {
            AppType = appType;
        }

        public String getApkVersion() {
            return ApkVersion;
        }

        public void setApkVersion(String apkVersion) {
            ApkVersion = apkVersion;
        }

        public String getApplicationReceivedDate() {
            return ApplicationReceivedDate;
        }

        public void setApplicationReceivedDate(String applicationReceivedDate) {
            ApplicationReceivedDate = applicationReceivedDate;
        }

        public String getXMLFilePath() {
            return XMLFilePath;
        }

        public void setXMLFilePath(String XMLFilePath) {
            this.XMLFilePath = XMLFilePath;
        }

        public String getAppMode() {
            return AppMode;
        }

        public void setAppMode(String appMode) {
            AppMode = appMode;
        }

        public String getDownloadURls() {
            return DownloadURls;
        }

        public void setDownloadURls(String downloadURls) {
            DownloadURls = downloadURls;
        }

        public String getZ_Status_flag() {
            return Z_Status_flag;
        }

        public void setZ_Status_flag(String z_Status_flag) {
            Z_Status_flag = z_Status_flag;
        }

        public String getPostID() {
            return PostID;
        }

        public void setPostID(String postID) {
            PostID = postID;
        }

        public String getDisplayAs() {
            return DisplayAs;
        }

        public void setDisplayAs(String displayAs) {
            DisplayAs = displayAs;
        }

        public String getDisplayReportas() {
            return DisplayReportas;
        }

        public void setDisplayReportas(String displayReportas) {
            DisplayReportas = displayReportas;
        }

        public String getUserLocation() {
            return UserLocation;
        }

        public void setUserLocation(String userLocation) {
            UserLocation = userLocation;
        }

        public String getTraining() {
            return Training;
        }

        public void setTraining(String training) {
            Training = training;
        }

        public String getTesting() {
            return Testing;
        }

        public void setTesting(String testing) {
            Testing = testing;
        }

        public String getVisibileIn() {
            return VisibileIn;
        }

        public void setVisibileIn(String visibileIn) {
            VisibileIn = visibileIn;
        }

        public String getWorkspaceAs() {
            return WorkspaceAs;
        }

        public void setWorkspaceAs(String workspaceAs) {
            WorkspaceAs = workspaceAs;
        }

        public String getDisplayAppName() {
            return DisplayAppName;
        }

        public void setDisplayAppName(String displayAppName) {
            DisplayAppName = displayAppName;
        }
    }

    /*public String getADDColumns() {
        return ADDColumns;
    }


    }

    public FormLevelTranslation getFormLevelTranslation() {
        return formLevelTranslation;
    }
    public void setDELETEColumns(String DELETEColumns) {
        this.DELETEColumns = DELETEColumns;
    }*/

    public void setFormLevelTranslation(FormLevelTranslation formLevelTranslation) {
        this.formLevelTranslation = formLevelTranslation;
    }

    public String getUserTypeID() {
        return UserTypeID;
    }

    public void setUserTypeID(String userTypeID) {
        UserTypeID = userTypeID;
    }

    public String getIsDataPermission() {
        return IsDataPermission;
    }

    public void setIsDataPermission(String isDataPermission) {
        IsDataPermission = isDataPermission;
    }

    public String getDataPermissionXML() {
        return DataPermissionXML;
    }

    public void setDataPermissionXML(String dataPermissionXML) {
        DataPermissionXML = dataPermissionXML;
    }

    public String getDataPermissionXmlFilePath() {
        return DataPermissionXmlFilePath;
    }

    public void setDataPermissionXmlFilePath(String dataPermissionXmlFilePath) {
        DataPermissionXmlFilePath = dataPermissionXmlFilePath;
    }

    public String getIsControlVisibility() {
        return IsControlVisibility;
    }

    public void setIsControlVisibility(String isControlVisibility) {
        IsControlVisibility = isControlVisibility;
    }

    public String getControlVisibilityXML() {
        return ControlVisibilityXML;
    }

    public void setControlVisibilityXML(String controlVisibilityXML) {
        ControlVisibilityXML = controlVisibilityXML;
    }

    public String getControlVisibilityXmlFilePath() {
        return ControlVisibilityXmlFilePath;
    }

    public void setControlVisibilityXmlFilePath(String controlVisibilityXmlFilePath) {
        ControlVisibilityXmlFilePath = controlVisibilityXmlFilePath;
    }
}
