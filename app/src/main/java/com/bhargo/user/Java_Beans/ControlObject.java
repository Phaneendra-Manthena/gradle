package com.bhargo.user.Java_Beans;



import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SECTION;

import com.bhargo.user.pojos.CalendarEvent;
import com.bhargo.user.ui_form.PrimaryLayoutModelClass;
import com.bhargo.user.uisettings.pojos.UIPrimaryLayoutModelClass;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

public class ControlObject implements Serializable ,Cloneable  {

    public String getCheckbox_ValueType() {
        return checkbox_ValueType;
    }

    public void setCheckbox_ValueType(String checkbox_ValueType) {
        this.checkbox_ValueType = checkbox_ValueType;
    }

    public String getCheckbox_CheckedValue() {
        return checkbox_CheckedValue;
    }

    public void setCheckbox_CheckedValue(String checkbox_CheckedValue) {
        this.checkbox_CheckedValue = checkbox_CheckedValue;
    }

    public String getCheckbox_unCheckedValue() {
        return checkbox_unCheckedValue;
    }

    public void setCheckbox_unCheckedValue(String checkbox_unCheckedValue) {
        this.checkbox_unCheckedValue = checkbox_unCheckedValue;
    }

    private String checkbox_ValueType;
    private String checkbox_CheckedValue;
    private String checkbox_unCheckedValue;
    private String controlCategory; //standard|advanced|data
    private String controlType,controlTitle;
    private String controlID;
    private int controlAddPos = -1;
    private boolean editClicked;
    private String controlName;
    private String displayName;
    private String defaultValue;
    private String hint;
    private String value;
    private boolean nullAllowed;
    private String mandatoryFieldError;
    private boolean uniqueField;
    private String uniqueFieldError;
    private String error;
    private String contains;
    private boolean readFromBarcode;
    private boolean valueBased;
    private boolean lengthBased;
    private String minLength;
    private String maxLength;
    private String lengthError;
    private String valueError;
    private String displayText;
    private List<String> items;
    private List<Item> itemsList;
    private int defaultItemIndex = -1;
    private String defaultItem;
    private String defaultItemId;
    private boolean readFromQRCode;
    private boolean googleLocationSearch;
    private boolean currentLocation;
    private boolean voiceText;
    private boolean invisible;
    private boolean enableMaskCharacters;
    private boolean disable;
    private String textSize;
    private String textSizeId;
    private String textStyle;
    private String textHexColor;
    private String textHexColorBG;
    private int textColor;
    private int textColorBG;
    private boolean enableCappingDigits;
    private boolean enableUpperLimit;
    private boolean enableLowerLimit;
    private String upperLimit;
    private String lowerLimit;
    private String cappingError;
    private String upperLimitErrorMesage;
    private String lowerLimitErrorMesage;
    private String cappingDigits;
    private boolean readOnly;
    private boolean enableDefaultValue;
    private boolean enableCountryCode;
    private boolean enableCountries;
    private List<String> enabledCountryIds;
    private List<String> enabledCountryNames;
    private boolean restrictEmailDomain;
    private List<String> restrictedDomainIds;
    private List<String> restrictedDomainNames;
    private String maxCharacters;
    private String minCharacters;
    private boolean enableMaxCharacters;
    private boolean enableMinCharacters;

    private boolean makeItAsPopup;

    public boolean isAllowOnlyAlphabets() {
        return allowOnlyAlphabets;
    }

    public void setAllowOnlyAlphabets(boolean allowOnlyAlphabets) {
        this.allowOnlyAlphabets = allowOnlyAlphabets;
    }

    public boolean isValidateFormFields() {
        return validateFormFields;
    }

    public void setValidateFormFields(boolean validateFormFields) {
        this.validateFormFields = validateFormFields;
    }

    public boolean isRemoveSelectedItemInNewRow() {
        return removeSelectedItemInNewRow;
    }

    public void setRemoveSelectedItemInNewRow(boolean removeSelectedItemInNewRow) {
        this.removeSelectedItemInNewRow = removeSelectedItemInNewRow;
    }

    private boolean allowOnlyAlphabets;
    private String maxCharError;
    private String minCharError;
    private boolean enableMaxUploadSize;
    private boolean enableMinUploadSize;
    private String maxUploadSize;
    private String minUploadSize;
    private String maxSizeUnit;
    private String minSizeUnit;
    private String maxUploadError;
    private String minUploadError;
    private boolean enableAspectRatio;
    private boolean enableImageWithGps;
    private String aspectRatioWidth;
    private String aspectRatioHeight;
    private String aspectRatio;
    private boolean zoomControl;
    private boolean flash;
    private boolean enableCamera;
    private boolean enableCapture;
    private boolean captureFromCamera;
    private String captureOrientation;
    private boolean captureFromFile;
    private boolean enableFilePhoto;
    private boolean enableExtensions;
    private boolean enableScan;
    private String fileExtensionError;
    private List<String> extensionsListIds;
    private List<String> extensionsListNames;
    private boolean geoTagging;
    private List<String> enabledExtensions;
    private int order;

    private boolean enableBetweenStartAndEndDate;
    private boolean enableCurrentDateAsDefault;
    private String startDate;
    private String endDate;
    private String startDateError;
    private String endDateError;
    private boolean enableBeforeCurrentDate;
    private boolean enableAfterCurrentDate;
    private String betweenStartAndEndDateError;
    private String beforeCurrentDateError;
    private String afterCurrentDateError;

    private boolean getYearFromSelection;
    private boolean getMonthFromSelection;
    private boolean getDayFromSelection;
    private boolean getDateFromSelection;

    private boolean enableSortByAlphabeticalOrder;
    private boolean enableAllowOtherChoice;
    private boolean enableHorizontalAlignment;
    private boolean enableSortByChronologicalOrder;
    private boolean enableSortByAscendingOrder;
    private boolean enableSortByDescendingOrder;

    private boolean disableRatingCount;
    private boolean disableRating;
    private boolean selectRatingItemType;
    private String ratingType;
    private boolean customItemNames;
    private int ratingItemColor;
    private String ratingItemCount;

    private boolean enableVoiceMinimumDuration;
    private boolean enableVoiceMaximumDuration;
    private String voiceMinimumDuration;
    private String voiceMaximumDuration;
    private String minimumDurationError;
    private String maximumDurationError;


    private boolean enableAudioFormat;
    private String audioFormat;
    private List<String> audioFormatIds;
    private List<String> audioFormatNames;
    private boolean enableUploadAudioFile;

    private boolean enableVideoMinimumDuration;
    private boolean enableVideoMaximumDuration;
    private String videoMinimumDuration;
    private String videoMaximumDuration;
    private boolean enableUploadVideoFile;
    private boolean enableCompression;
    private String compressionQuality;

    private boolean enableVideoFormat;
    private String videoFormat;
    private List<String> videoFormatIds;
    private List<String> videoFormatNames;
    private boolean enablePlayBackground;
    private boolean enableStayAwake;

    private boolean enableUploadSignature;
    private boolean enableSignatureOnScreen;

    private String url;
    private String urlPlaceholderText;
    private boolean hideURL;

    private boolean disableClick;
    public boolean validateFormFields;
    private boolean enableStartValue;

    private String startValue;

    private boolean enableDecimalCharacters;
    private String charactersAfterDecimal;

    private boolean enableShowOrHideOption;
    private boolean enableEncryption;
    private String encryptionType;
    private  String  encryptionTypeId;
    private boolean enablePasswordLength;
    private boolean enablePasswordPolicy;
    private List<String> passwordPolicy;
    private List<String> passwordPolicyIds;
    private String passwordLength;
    private String passwordLengthError;
    private boolean enableMinimumAmount;
    private String minAmount;
    private String minAmountError;
    private boolean enableMaximumAmount;
    private String maxAmount;
    private String maxAmountError;
    private boolean enableMinimumValue;
    private String minValue;
    private boolean enableMaximumValue;
    private String maxValue;
    private boolean enableCurrencySelection;
    private String currency;
    private List<String> currencyListIds;
    private List<String> currencyListNames;
    private Boolean strikeText;
    private Boolean isUnderLineText;

    private String filePath;
    private String ImageData;
    private String ImageDataType;
    private boolean enableMultipleImages;
    private String imagesArrangementType;
    private boolean hideDisplayName;
    private boolean zoomImageEnable;
    private boolean enableHorizontalScroll;
    private boolean makeAsSection;
    private String minimumRows;
    private String minimumRowsError;
    private String maximumRows;
    private String maximumRowsError;
/*Menu Control*/
public String typeOfMenu;
    public String noOfRows;
    public String noofColumns;
    public boolean fitToScreenWidth;
    public boolean fitToScreenHeight;
    public int typeOfBorder;
    public String borderHexColor;
    public int borderColor;
    public String borderMarginLeft;
    public String borderMarginRight;
    public String borderMarginTop;
    public String borderMarginBottom;
    public String borderPaddingLeft;
    public String borderPaddingRight;
    public String borderPaddingTop;
    public String borderPaddingBottom;

    public String typeOfButton;
    public String buttonIconAlignment;
    public String iconPath;
    public String buttonColor;
    public String buttonHexColor;
    public String buttonRadius;

    List<ControlObject> menuControlObjectList;


    /*Data_Viewer*/
    private  String DataViewer_UI_Pattern;
    private  String DataViewer_Shape;
    private  String DataViewer_DefualtImage_path;
    private  String DataViewer_FrameBG_HexColor;
    private  String DataViewer_FrameBG_HexColorTwo;
    private  String DataViewer_Item_HexColor;
    private  String DataViewer_Item_BGHexColor;

    private  String DataViewer_Header_HexColor;
    private  String DataViewer_SubHeader_HexColor;
    private  String DataViewer_Description_HexColor;
    private  String DataViewer_DateTime_HexColor;

    private boolean lazyLoadingEnabled;
    private String threshold;
    private int currentMaxPosition =0;
    private String ImageSpecificationType;
    private String imageWidth;
    private String imageHeight;

    //add by nk
    private boolean DataViewer_searchEnabled;
    private boolean DataViewer_HeaderSearchEnabled;
    private boolean DataViewer_SubHeaderSearchEnabled;
    private boolean DataViewer_DescriptionSearchEnabled;
    private boolean DataViewer_CornerSearchEnabled;



    /* Advance Validations
     *   SubForm*/
    private boolean enableDisplayOrientation;
    private String displayOrientation;
    private boolean enableDisplayNameOfAddButton;
    private String displayNameOfAddButton;
    private List<ControlObject> subFormControlList;
    private List<ControlObject> sectionControlList;
    private int subFormControlAddPos = -1;
    private boolean ataglance;
    private boolean enableLocationFormatting;
    private String locationFormat;
    private boolean enableSavingLatitudeAndLongitudeInSeparateColumns;
    private boolean enableAccuracy;
    private List<String> searchItemIds;
    private boolean searchEnable;
    /*SECTION*/

    public String panelType;
    public int sectionBGColor;
    public String sectionBGHexColor;
    private boolean enableCollapseOrExpand;

    /*GPS*/
    private String accuracy;
    private boolean enableLocationMode;
    private String locationMode;
    private boolean enableGpsType;
    private String gpsType;
    private String gpsTypeID;
    private boolean enableVehicleTracking;
    private boolean enableGeoFencing;
    private boolean showMap;
    private boolean enableDisplayAsBarCode;
    private boolean enableDisplayAsQRCode;
    private boolean enableUnicode;
    private String unicodeFormat;
    private String typeOfInterval;
    private String distanceInMeters;
    private String timeInMinutes;
    private String distanceAround;
    private String nearBy;

    /*Dynamic Label*/
    private String unicodeFormatId;
    private String maskCharacterType;
    private String noOfCharactersToMask;
    private String maskCharacterDirection;
    private String backGroundColor;
    private String backGroupImage;
    private List<Control_EventObject> List_OnchangeEvents;
    private List<Control_EventObject> List_OnFocusEvents;
    private Control_EventObject onChangeEventObject;
    private Control_EventObject onFocusEventObject;
    public boolean isLayoutBackGroundEnable;
    public String LayoutBackGroundColor;
    private String audioData;
    private String videoData;

    /*onAddRowEvent*/
    private Control_EventObject onAddRowEventObject;
    private boolean onAddRowEventExists;

    /*onDeleteRowEvent*/
    private Control_EventObject onDeleteRowEventObject;
    private boolean onDeleteRowEventExists;


    /*OnChangeEventObject*/
    private Control_EventObject onClickEventObject;


    /*OnFocusEventObject*/
    private boolean onChangeEventExists;
    private boolean onFocusEventExists;
    private boolean onClickEventExists;

    /* Map MarkerClick event*/
    private boolean onMapMarkerClickEventExists;
    private Control_EventObject onMapMarkerClickEventObject;
    private String dataControlStatus;
    private String dependentControl;
    private String dataControlAccessibility;
    private String dataControlTextFileName;

    /*Data Controls*/
    private String dataControlTextFilePath;
    private int dataControlIndex;
    private String dataControlName;

    private String dataControlLocationType;
    private boolean enableUserControlBinding;
    private boolean enablePostLocationBinding;
/*Map Control*/

    private List<RenderingType> renderingTypeList;
    private String baseMap;
    private String mapView;
    private String mapViewType;
    private boolean showCurrentLocation;
    private boolean zoomControls;
    public String mapIcon;
    public String mapHeight;
    /*Data control*/
    private boolean userControlBind;

    /*Value of the Control*/

    private String controlValue;
    private String fieldDisplayFormat;

    /*CalendarEventControl*/
    private List<CalendarEvent> calendarEventType;
    private boolean isWeekDays;

    /*/*GRID*/
    private boolean enableFixGridWidth;



    private List<String> gridColumnsWidths;
    private List<GridColumnSettings> gridColumnSettings;
    private boolean isGridControl;
    private boolean hideBorder;
    private boolean hideColumnNames;

    public boolean isGridControl_HideDeleteButton() {
        return gridControl_HideDeleteButton;
    }

    public void setGridControl_HideDeleteButton(boolean gridControl_HideDeleteButton) {
        this.gridControl_HideDeleteButton = gridControl_HideDeleteButton;
    }

    private boolean gridControl_HideDeleteButton;
    private boolean gridControl_HideAddButton;
    private String gridControl_ColHeightType;
    private String gridControl_ColHeightSize;
    private String gridControl_ColTextSize;
    private String gridControl_ColTextStyle;
    private String gridControl_ColTextColor;

    private String gridControl_ColTextAlignment;
    private String gridControl_ColColor;
    private String gridControl_ColBorder;
    private String gridControl_BorderType;
    private String gridControl_BorderColor;
    private String gridControl_BorderThickness;
    private String gridControl_rowHeigthType,gridControl_rowHeightSize;
    private String gridControl_rowColorType;
    private String gridControl_rowColor1;
    private String gridControl_rowColor2;
    private boolean gridControl_LazyLoading;
    private String gridControl_threshold;
    /*GRID*/

    /*Button_DisplaySettings*/
    private  String Shape;

    /*Translation*/
    private LinkedHashMap<String,String> translationsMap;
    private LinkedHashMap<String,ControlObject> translationsMappingObject;

    /*User Control*/

    private String userType;
    private boolean showUsersWithPostName;
    private List<UserGroup> groups;

    /*Chart Control*/
    private String chartType;
    private String chartColorHex;
    private int chartColor;
    private List<String> chartColors;

    private boolean hideLegends;

    /*Progress Control*/
    private String progress_maxvalue,progress_actualvalue;
    private boolean hide_progress_maxvalue,hide_progress_actualvalue;

    private String progressColorHex;
    private int progressColor;
    /*countdowntimer Control*/
    private String timer_hr;
    private String timer_min;
    private String timer_sec;



    private String timerFormatOptions = "hh:mm:ss";
    private boolean timerAutoStart;

    private String timerColorHex;
    private int timerColor;

    /*Table Settings*/
    private TableSettingSObject_Bean subFormtableSettingsObject;
    public List<String> subFormList_Table_Columns;
    private List<QueryFilterField_Bean> subFormWhereConditionFields;
    List<QueryFilterField_Bean> subFormUpdateFields;
    List<QueryFilterField_Bean> subFormInsertFields;
    //createnew/mapexisting/none
    private String tableSettingsType;
    private String existingTableName;
    private String mapExistingType;
    /*Table Settings*/


    /*AutoNumbers Control*/
    private String preFixValue;
    private String suffixValue;
    private String suffix1Value;

    /*UI form*/
    PrimaryLayoutModelClass primaryLayoutModelClass;
    public boolean isUIFormNeededSubForm;
    private String uiType;
    public boolean isSectionUIFormNeeded;;
    UIPrimaryLayoutModelClass uiPrimaryLayoutModelClass;
    /*ViewFile Control*/
    private String filelink;



    private boolean hide_filelink;
    private boolean downloadFile;
    /*Data Table*/
    /*Data Table*/
   /* private String dataTableRowHeight="40";
    private boolean dataTableRowHeightWrapContent;*/
    private List<String> columnHeaders;
    private boolean dataTable_EnableSearch;
    private boolean isDownloadExcel;
    private boolean isDownloadPDF;
    private String dataTable_colWidthType,dataTable_colWidthSize,dataTable_colHeightType,dataTable_colHeightSize;
    private String dataTable_colTextSize,dataTable_colTextStyle,dataTable_colTextColor;
    private String dataTable_colAlignment,dataTable_colColor,dataTable_colBorder;
    private String dataTable_rowHeigthType,dataTable_rowHeightSize;
    private String dataTable_rowTextSize,dataTable_rowTextStyle,dataTable_rowTextColor;
    private String dataTable_rowAlignment,dataTable_rowColorType,dataTable_rowColor1,dataTable_rowColor2,dataTable_BorderType;
    private boolean dataTable_isPaging;
    private String dataTable_BorderColor;
    private String dataTable_BorderThickness;
    private boolean dataTable_ParticularRowsColoring;
    private String dataTable_ParticularRowsColoringIds;
    private String dataTable_ParticularRowColor;
    /*TimeControl*/
    private boolean isMandatoryTime;
    private String mandatoryTimeErrorMessage;
    private boolean isBeforeCurrentTime;
    private String beforeCurrentTimeErrorMessage;
    private boolean isAfterCurrentTime;
    private String afterCurrentTimeErrorMessage;
    private boolean isBetweenStartEndTime;
    private String betweenStartTime;
    private String betweenEndTime;
    private String betweenStartEndTimeErrorMessage;
    private boolean isCurrentTime;
    private boolean isTimeFormat;
    private String timeFormat = "12:00 Hrs";
    private boolean isTimeFormatOptions;
    private String timeFormatOptions = "hh:mm";
    /*Large Input Text Editor*/
    private boolean htmlEditorEnabled;
    private boolean htmlViewerEnabled;
    /*RequiredNewUi*/
    private String displayNameAlignment; // DisplayNameAlignment1,2,3...
    private String textAlignment; // DisplayNameAlignment1,2,3...
    private String backgroundType;
    private String iconAlignment;

    private String rowSelectionType;

    /*  Getters and Setters*/

    public List<String> getCurrencyListIds() {
        return currencyListIds;
    }

    /*Auto_Completion_Control*/

    private int minChartoSeearch=0;
    private String searchKeyAt="";


//    SubmitButton_Properties
    private String SubmitButtonMessage;
    private String SubmitButtonMessageDisplayType;
    private String SubmitButtonSuccessMessage;
    private String SubmitButtonFailMessage;
    private boolean isAppClose=true;
    private boolean enableChangeButtonColor;
    private boolean enableChangeButtonColorBG;

    //Ui Settings SubForm

    private boolean enableSubFormDelete=false;
    private boolean enableSubFormAddNewRow=false;
    private boolean isGridWithTwoColumns;
    private boolean sectionControl=false;
    private boolean removeSelectedItemInNewRow;
    private String customImageFit="Default";
    private String customImageRadius = "0";
    private String customImageURL;




    public ControlObject() {
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException
    {
        // Super() keyword refers to parent class
        return super.clone();
    }

    public ControlObject(ControlObject controlObject) {
        this.controlTitle = controlObject.getControlTitle();
        this.controlCategory = controlObject.getControlCategory();
        this.controlType = controlObject.getControlType();
        this.controlID = controlObject.getControlID();
        this.controlAddPos = controlObject.getControlAddPos();
        this.editClicked = controlObject.isEditClicked();
        this.controlName = controlObject.getControlName();
        this.displayName = controlObject.getDisplayName();
        this.defaultValue = controlObject.getDefaultValue();
        this.hint =controlObject.getHint();
        this.value = controlObject.getValue();
        this.nullAllowed = controlObject.isNullAllowed();
        this.mandatoryFieldError = controlObject.getMandatoryFieldError();
        this.uniqueField = controlObject.isUniqueField();
        this.uniqueFieldError = controlObject.getUniqueFieldError();
        this.error = controlObject.getError();
        this.contains = controlObject.getContains();
        this.readFromBarcode = controlObject.isReadFromBarcode();
        this.valueBased = controlObject.isValueBased();
        this.lengthBased = controlObject.isLengthBased();
        this.minLength = controlObject.getMinLength();
        this.maxLength = controlObject.getMaxLength();
        this.lengthError = controlObject.getLengthError();
        this.valueError = controlObject.getValueError();
        this.displayText = controlObject.getDisplayText();
        this.items = controlObject.getItems();
        this.itemsList = controlObject.getItemsList();
        this.defaultItemIndex = controlObject.getDefaultItemIndex();
        this.defaultItem = controlObject.getDefaultItem();
        this.defaultItemId = controlObject.getDefaultItemId();
        this.readFromQRCode = controlObject.isReadFromQRCode();
        this.googleLocationSearch = controlObject.isGoogleLocationSearch();
        this.currentLocation = controlObject.isCurrentLocation();
        this.voiceText = controlObject.isVoiceText();
        this.invisible = controlObject.isInvisible();
        this.enableMaskCharacters = controlObject.isEnableMaskCharacters();
        this.disable = controlObject.isDisable();
        this.textSize = controlObject.getTextSize();
        this.textSizeId = controlObject.getTextSizeId();
        this.textStyle = controlObject.getTextStyle();
        this.textHexColor = controlObject.getTextHexColor();
        this.textColor = controlObject.getTextColor();
        this.enableCappingDigits = controlObject.isEnableCappingDigits();
        this.enableUpperLimit = controlObject.isEnableUpperLimit();
        this.enableLowerLimit = controlObject.isEnableLowerLimit();
        this.upperLimit = controlObject.getUpperLimit();
        this.lowerLimit = controlObject.getLowerLimit();
        this.cappingError = controlObject.getCappingError();
        this.upperLimitErrorMesage = controlObject.getUpperLimitErrorMesage();
        this.lowerLimitErrorMesage = controlObject.getLowerLimitErrorMesage();
        this.cappingDigits = controlObject.getCappingDigits();
        this.readOnly = controlObject.isReadOnly();
        this.enableDefaultValue = controlObject.isEnableDefaultValue();
        this.enableCountryCode = controlObject.isEnableCountryCode();
        this.enableCountries = controlObject.isEnableCountries();
        this.enabledCountryIds = controlObject.getEnabledCountryIds();
        this.enabledCountryNames = controlObject.getEnabledCountryNames();
        this.restrictEmailDomain = controlObject.isRestrictEmailDomain();
        this.restrictedDomainIds = controlObject.getRestrictedDomainIds();
        this.restrictedDomainNames = controlObject.getRestrictedDomainNames();
        this.maxCharacters = controlObject.getMaxCharacters();
        this.minCharacters = controlObject.getMinCharacters();
        this.enableMaxCharacters = controlObject.isEnableMaxCharacters();
        this.enableMinCharacters = controlObject.isEnableMinCharacters();
        this.maxCharError = controlObject.getMaxCharError();
        this.minCharError = controlObject.getMinCharError();
        this.enableMaxUploadSize = controlObject.isEnableMaxUploadSize();
        this.enableMinUploadSize = controlObject.isEnableMinUploadSize();
        this.maxUploadSize = controlObject.getMaxUploadSize();
        this.minUploadSize = controlObject.getMinUploadSize();
        this.maxSizeUnit = controlObject.getMaxSizeUnit();
        this.minSizeUnit = controlObject.getMinSizeUnit();
        this.maxUploadError = controlObject.getMaxUploadError();
        this.minUploadError = controlObject.getMinUploadError();
        this.enableAspectRatio = controlObject.isEnableAspectRatio();
        this.enableImageWithGps = controlObject.isEnableImageWithGps();
        this.aspectRatioWidth = controlObject.getAspectRatioWidth();
        this.aspectRatioHeight = controlObject.getAspectRatioHeight();
        this.aspectRatio = controlObject.getAspectRatio();
        this.zoomControl = controlObject.isZoomControl();
        this.flash = controlObject.isFlash();
        this.enableCamera = controlObject.isEnableCamera();
        this.enableCapture = controlObject.isEnableCapture();
        this.captureFromCamera = controlObject.isCaptureFromCamera();
        this.captureFromFile = controlObject.isCaptureFromFile();
        this.enableFilePhoto = controlObject.isEnableFilePhoto();
        this.enableExtensions = controlObject.isEnableExtensions();
        this.fileExtensionError = controlObject.getFileExtensionError();
        this.extensionsListIds = controlObject.getExtensionsListIds();
        this.extensionsListNames = controlObject.getExtensionsListNames();
        this.geoTagging = controlObject.isGeoTagging();
        this.enabledExtensions = controlObject.getEnabledExtensions();
        this.order = controlObject.getOrder();
        this.enableBetweenStartAndEndDate = controlObject.isEnableBetweenStartAndEndDate();
        this.enableCurrentDateAsDefault = controlObject.isEnableCurrentDateAsDefault();
        this.startDate = controlObject.getStartDate();
        this.endDate = controlObject.getEndDate();
        this.startDateError = controlObject.getStartDateError();
        this.endDateError = controlObject.getEndDateError();
        this.enableBeforeCurrentDate = controlObject.isEnableBeforeCurrentDate();
        this.enableAfterCurrentDate = controlObject.isEnableAfterCurrentDate();
        this.betweenStartAndEndDateError = controlObject.getBetweenStartAndEndDateError();
        this.beforeCurrentDateError = controlObject.getBeforeCurrentDateError();
        this.afterCurrentDateError = controlObject.getAfterCurrentDateError();
        this.getYearFromSelection = controlObject.isGetYearFromSelection();
        this.getMonthFromSelection = controlObject.isGetMonthFromSelection();
        this.getDayFromSelection = controlObject.isGetDayFromSelection();
        this.getDateFromSelection = controlObject.isGetDateFromSelection();
        this.enableSortByAlphabeticalOrder = controlObject.isEnableSortByAlphabeticalOrder();
        this.enableAllowOtherChoice = controlObject.isEnableAllowOtherChoice();
        this.enableHorizontalAlignment = controlObject.isEnableHorizontalAlignment();
        this.enableSortByChronologicalOrder = controlObject.isEnableSortByChronologicalOrder();
        this.enableSortByAscendingOrder = controlObject.isEnableSortByAscendingOrder();
        this.enableSortByDescendingOrder = controlObject.isEnableSortByDescendingOrder();
        this.disableRatingCount = controlObject.isDisableRatingCount();
        this.disableRating = controlObject.isDisableRating();
        this.selectRatingItemType = controlObject.isSelectRatingItemType();
        this.ratingType = controlObject.getRatingType();
        this.customItemNames = controlObject.isCustomItemNames();
        this.ratingItemColor = controlObject.getRatingItemColor();
        this.ratingItemCount = controlObject.getRatingItemCount();
        this.enableVoiceMinimumDuration = controlObject.isEnableVoiceMinimumDuration();
        this.enableVoiceMaximumDuration = controlObject.isEnableVoiceMaximumDuration();
        this.voiceMinimumDuration = controlObject.getVoiceMinimumDuration();
        this.voiceMaximumDuration = controlObject.getVoiceMaximumDuration();
        this.minimumDurationError = controlObject.getMinimumDurationError();
        this.maximumDurationError = controlObject.getMaximumDurationError();
        this.enableAudioFormat = controlObject.isEnableAudioFormat();
        this.audioFormat = controlObject.getAudioFormat();
        this.audioFormatIds = controlObject.getAudioFormatIds();
        this.audioFormatNames = controlObject.getAudioFormatNames();
        this.enableUploadAudioFile = controlObject.isEnableUploadAudioFile();
        this.enableVideoMinimumDuration = controlObject.isEnableVideoMinimumDuration();
        this.enableVideoMaximumDuration = controlObject.isEnableVideoMaximumDuration();
        this.videoMinimumDuration = controlObject.getVideoMinimumDuration();
        this.videoMaximumDuration = controlObject.getVideoMaximumDuration();
        this.enableUploadVideoFile = controlObject.isEnableUploadVideoFile();
        this.enableVideoFormat = controlObject.isEnableVideoFormat();
        this.videoFormat = controlObject.getVideoFormat();
        this.videoFormatIds = controlObject.getVideoFormatIds();
        this.videoFormatNames = controlObject.getVideoFormatNames();
        this.enablePlayBackground = controlObject.isEnablePlayBackground();
        this.enableStayAwake = controlObject.isEnableStayAwake();
        this.enableUploadSignature = controlObject.isEnableUploadSignature();
        this.enableSignatureOnScreen = controlObject.isEnableSignatureOnScreen();
        this.url = controlObject.getUrl();
        this.disableClick = controlObject.isDisableClick();
        this.enableStartValue = controlObject.isEnableStartValue();
        this.startValue = controlObject.getStartValue();
        this.enableDecimalCharacters = controlObject.isEnableDecimalCharacters();
        this.charactersAfterDecimal = controlObject.getCharactersAfterDecimal();
        this.enableShowOrHideOption = controlObject.isEnableShowOrHideOption();
        this.enableEncryption = controlObject.isEnableEncryption();
        this.enableMinimumAmount = controlObject.isEnableMinimumAmount();
        this.minAmount = controlObject.getMinAmount();
        this.minAmountError = controlObject.getMinAmountError();
        this.enableMaximumAmount = controlObject.isEnableMaximumAmount();
        this.maxAmount = controlObject.getMaxAmount();
        this.maxAmountError = controlObject.getMaxAmountError();
        this.enableMinimumValue = controlObject.isEnableMinimumValue();
        this.minValue = controlObject.getMinValue();
        this.enableMaximumValue = controlObject.isEnableMaximumValue();
        this.maxValue = controlObject.getMaxValue();
        this.enableCurrencySelection = controlObject.isEnableCurrencySelection();
        this.currency = controlObject.getCurrency();
        this.currencyListIds = controlObject.getCurrencyListIds();
        this.currencyListNames = controlObject.getCurrencyListNames();
        this.strikeText = controlObject.getStrikeText();
        this.isUnderLineText = controlObject.getUnderLineText();
        this.filePath = controlObject.getFilePath();
        ImageData = controlObject.getImageData();
        ImageDataType = controlObject.getImageDataType();
        this.enableMultipleImages = controlObject.isEnableMultipleImages();
        this.imagesArrangementType = controlObject.getImagesArrangementType();
        this.hideDisplayName = controlObject.isHideDisplayName();
        this.enableHorizontalScroll = controlObject.isEnableHorizontalScroll();
        this.makeAsSection = controlObject.isMakeAsSection();
        this.minimumRows = controlObject.getMinimumRows();
        this.minimumRowsError = controlObject.getMinimumRowsError();
        this.maximumRows = controlObject.getMaximumRows();
        this.maximumRowsError = controlObject.getMaximumRowsError();
        this.typeOfMenu = controlObject.getTypeOfMenu();
        this.noOfRows = controlObject.getNoOfRows();
        this.noofColumns = controlObject.getNoofColumns();
        this.fitToScreenWidth = controlObject.isFitToScreenWidth();
        this.fitToScreenHeight = controlObject.isFitToScreenHeight();
        this.typeOfBorder = controlObject.getTypeOfBorder();
        this.borderHexColor = controlObject.getBorderHexColor();
        this.borderColor = controlObject.getBorderColor();
        this.borderMarginLeft = controlObject.getBorderMarginLeft();
        this.borderMarginRight = controlObject.getBorderMarginRight();
        this.borderMarginTop = controlObject.getBorderMarginTop();
        this.borderMarginBottom = controlObject.getBorderMarginBottom();
        this.borderPaddingLeft = controlObject.getBorderPaddingLeft();
        this.borderPaddingRight = controlObject.getBorderPaddingRight();
        this.borderPaddingTop = controlObject.getBorderPaddingTop();
        this.borderPaddingBottom = controlObject.getBorderPaddingBottom();
        this.typeOfButton = controlObject.getTypeOfButton();
        this.iconPath = controlObject.getIconPath();
        this.buttonColor = controlObject.getButtonColor();
        this.buttonHexColor = controlObject.getButtonHexColor();
        this.buttonRadius = controlObject.getButtonRadius();
        this.menuControlObjectList = controlObject.getMenuControlObjectList();
        DataViewer_UI_Pattern = controlObject.getDataViewer_UI_Pattern();
        DataViewer_Shape = controlObject.getDataViewer_Shape();
        DataViewer_DefualtImage_path = controlObject.getDataViewer_DefualtImage_path();
        DataViewer_FrameBG_HexColor = controlObject.getDataViewer_FrameBG_HexColor();
        DataViewer_Header_HexColor = controlObject.getDataViewer_Header_HexColor();
        DataViewer_SubHeader_HexColor = controlObject.getDataViewer_SubHeader_HexColor();
        DataViewer_Description_HexColor = controlObject.getDataViewer_Description_HexColor();
        DataViewer_DateTime_HexColor = controlObject.getDataViewer_DateTime_HexColor();
        this.lazyLoadingEnabled = controlObject.isLazyLoadingEnabled();
        this.threshold = controlObject.getThreshold();
        this.currentMaxPosition = controlObject.getCurrentMaxPosition();
        this.enableDisplayOrientation = controlObject.isEnableDisplayOrientation();
        this.displayOrientation = controlObject.getDisplayOrientation();
        this.enableDisplayNameOfAddButton = controlObject.isEnableDisplayNameOfAddButton();
        this.displayNameOfAddButton = controlObject.getDisplayNameOfAddButton();
        this.subFormControlList = controlObject.getSubFormControlList();
        this.sectionControlList = controlObject.getSectionControlList();
        this.subFormControlAddPos = controlObject.getSubFormControlAddPos();
        this.ataglance = controlObject.isAtaglance();
        this.enableLocationFormatting = controlObject.isEnableLocationFormatting();
        this.locationFormat = controlObject.getLocationFormat();
        this.enableSavingLatitudeAndLongitudeInSeparateColumns = controlObject.isEnableSavingLatitudeAndLongitudeInSeparateColumns();
        this.enableAccuracy = controlObject.isEnableAccuracy();
        this.panelType = controlObject.getPanelType();
        this.sectionBGColor = controlObject.getSectionBGColor();
        this.sectionBGHexColor = controlObject.getSectionBGHexColor();
        this.accuracy = controlObject.getAccuracy();
        this.enableLocationMode = controlObject.isEnableLocationMode();
        this.locationMode = controlObject.getLocationMode();
        this.enableGpsType = controlObject.isEnableGpsType();
        this.gpsType = controlObject.getGpsType();
        this.gpsTypeID = controlObject.getGpsTypeID();
        this.enableVehicleTracking = controlObject.isEnableVehicleTracking();
        this.enableGeoFencing = controlObject.isEnableGeoFencing();
        this.showMap = controlObject.isShowMap();
        this.enableDisplayAsBarCode = controlObject.isEnableDisplayAsBarCode();
        this.enableDisplayAsQRCode = controlObject.isEnableDisplayAsQRCode();
        this.enableUnicode = controlObject.isEnableUnicode();
        this.unicodeFormat = controlObject.getUnicodeFormat();
        this.typeOfInterval = controlObject.getTypeOfInterval();
        this.distanceInMeters = controlObject.getDistanceInMeters();
        this.timeInMinutes = controlObject.getTimeInMinutes();
        this.distanceAround = controlObject.getDistanceAround();
        this.nearBy = controlObject.getNearBy();
        this.unicodeFormatId = controlObject.getUnicodeFormatId();
        this.maskCharacterType = controlObject.getMaskCharacterType();
        this.noOfCharactersToMask = controlObject.getNoOfCharactersToMask();
        this.maskCharacterDirection = controlObject.getMaskCharacterDirection();
        this.backGroundColor = controlObject.getBackGroundColor();
        this.backGroupImage = controlObject.getBackGroupImage();
        List_OnchangeEvents = controlObject.getList_OnchangeEvents();
        List_OnFocusEvents = controlObject.getList_OnFocusEvents();
        this.onChangeEventObject = controlObject.getOnChangeEventObject();
        this.onFocusEventObject = controlObject.getOnFocusEventObject();
        this.isLayoutBackGroundEnable = controlObject.isLayoutBackGroundEnable();
        LayoutBackGroundColor = controlObject.getLayoutBackGroundColor();
        this.audioData = controlObject.getAudioData();
        this.videoData = controlObject.getVideoData();
        this.onClickEventObject = controlObject.getOnClickEventObject();
        this.onAddRowEventExists = controlObject.isOnAddRowEventExists();
        this.onAddRowEventObject = controlObject.getOnAddRowEventObject();
        this.onDeleteRowEventExists = controlObject.isOnDeleteRowEventExists();
        this.onDeleteRowEventObject = controlObject.getOnDeleteRowEventObject();
        this.onChangeEventExists = controlObject.isOnChangeEventExists();
        this.onFocusEventExists = controlObject.isOnFocusEventExists();
        this.onClickEventExists = controlObject.isOnClickEventExists();
        this.onMapMarkerClickEventExists = controlObject.isOnMapMarkerClickEventExists();
        this.onMapMarkerClickEventObject = controlObject.getOnMapMarkerClickEventObject();
        this.dataControlStatus = controlObject.getDataControlStatus();
        this.dependentControl = controlObject.getDependentControl();
        this.dataControlAccessibility = controlObject.getDataControlAccessibility();
        this.dataControlTextFileName = controlObject.getDataControlTextFileName();
        this.dataControlTextFilePath = controlObject.getDataControlTextFilePath();
        this.dataControlIndex = controlObject.getDataControlIndex();
        this.dataControlName = controlObject.getDataControlName();
        this.dataControlLocationType = controlObject.getDataControlLocationType();
        this.enableUserControlBinding = controlObject.isEnableUserControlBinding();
        this.enablePostLocationBinding = controlObject.isEnablePostLocationBinding();
        this.renderingTypeList = controlObject.getRenderingTypeList();
        this.baseMap = controlObject.getBaseMap();
        this.mapView = controlObject.getMapView();
        this.mapViewType = controlObject.getMapViewType();
        this.showCurrentLocation = controlObject.isShowCurrentLocation();
        this.zoomControls = controlObject.isZoomControls();
        this.mapIcon = controlObject.getMapIcon();
        this.mapHeight = controlObject.getMapHeight();
        this.userControlBind = controlObject.isUserControlBind();
        this.controlValue = controlObject.getControlValue();
        this.fieldDisplayFormat = controlObject.getFieldDisplayFormat();
        this.calendarEventType = controlObject.getCalendarEventType();
        this.isWeekDays = controlObject.isWeekDays();
        this.enableFixGridWidth = controlObject.isEnableFixGridWidth();
        this.gridColumnSettings = controlObject.getGridColumnSettings();
        this.isGridControl = controlObject.isGridControl();
        this.hideBorder = controlObject.isHideBorder();
        this.hideColumnNames = controlObject.isHideColumnNames();
        Shape = controlObject.getShape();
        this.translationsMap = controlObject.getTranslationsMap();
        this.translationsMappingObject = controlObject.getTranslationsMappingObject();
        this.userType = controlObject.getUserType();
        this.groups = controlObject.getGroups();
        this.chartType = controlObject.getChartType();
        this.chartColorHex = controlObject.getChartColorHex();
        this.chartColor = controlObject.getChartColor();
        this.subFormtableSettingsObject = controlObject.getSubFormtableSettingsObject();
        this.subFormList_Table_Columns = controlObject.getSubFormList_Table_Columns();
        this.subFormWhereConditionFields = controlObject.getSubFormWhereConditionFields();
        this.subFormUpdateFields = controlObject.getSubFormUpdateFields();
        this.subFormInsertFields = controlObject.getSubFormInsertFields();
        this.tableSettingsType = controlObject.getTableSettingsType();
        this.existingTableName = controlObject.getExistingTableName();
        this.mapExistingType = controlObject.getMapExistingType();
        this.preFixValue = controlObject.getPreFixValue();
        this.suffixValue = controlObject.getSuffixValue();
        this.primaryLayoutModelClass = controlObject.getPrimaryLayoutModelClass();
        this.isUIFormNeededSubForm = controlObject.isUIFormNeededSubForm();
        this.uiPrimaryLayoutModelClass = controlObject.getUiPrimaryLayoutModelClass();

        this.columnHeaders = controlObject.getColumnHeaders();
        this.isMandatoryTime = controlObject.isMandatoryTime();
        this.mandatoryTimeErrorMessage = controlObject.getMandatoryTimeErrorMessage();
        this.isBeforeCurrentTime = controlObject.isBeforeCurrentTime();
        this.beforeCurrentTimeErrorMessage = controlObject.getBeforeCurrentTimeErrorMessage();
        this.isAfterCurrentTime = controlObject.isAfterCurrentTime();
        this.afterCurrentTimeErrorMessage = controlObject.getAfterCurrentTimeErrorMessage();
        this.isBetweenStartEndTime = controlObject.isBetweenStartEndTime();
        this.betweenStartTime = controlObject.getBetweenStartTime();
        this.betweenEndTime = controlObject.getBetweenEndTime();
        this.betweenStartEndTimeErrorMessage = controlObject.getBetweenStartAndEndDateError();
        this.isCurrentTime = controlObject.isCurrentTime();
        this.isTimeFormat = controlObject.isTimeFormat();
        this.timeFormat = controlObject.getTimeFormat();
        this.isTimeFormatOptions = controlObject.isTimeFormatOptions();
        this.timeFormatOptions = controlObject.getTimeFormatOptions();
        this.htmlEditorEnabled = controlObject.isHtmlEditorEnabled();
        this.htmlViewerEnabled = controlObject.isHtmlViewerEnabled();
        this.displayNameAlignment = controlObject.getDisplayNameAlignment();
        this.backgroundType = controlObject.getBackgroundType();
        this.iconAlignment = controlObject.getIconAlignment();
        this.rowSelectionType = controlObject.getRowSelectionType();
        this.minChartoSeearch = controlObject.getMinChartoSeearch();
        this.searchKeyAt = controlObject.getSearchKeyAt();
        this.SubmitButtonMessage = controlObject.getSubmitButtonMessage();
        SubmitButtonMessageDisplayType = controlObject.getSubmitButtonMessageDisplayType();
        SubmitButtonSuccessMessage = controlObject.getSubmitButtonSuccessMessage();
        SubmitButtonFailMessage = controlObject.getSubmitButtonFailMessage();
        this.isAppClose = controlObject.isAppClose();
        this.enableSubFormDelete = controlObject.isEnableSubFormDelete();
        this.enableSubFormAddNewRow = controlObject.isEnableSubFormAddNewRow();
        this.zoomImageEnable = controlObject.isZoomImageEnable();
        this.captureOrientation = controlObject.getCaptureOrientation();
        this.textHexColorBG = controlObject.getTextHexColorBG();
        this.enableChangeButtonColorBG = controlObject.isEnableChangeButtonColorBG();
        this.enableChangeButtonColor = controlObject.isEnableChangeButtonColor();
        this.customImageFit = controlObject.getCustomImageFit();
        this.customImageRadius = controlObject.getCustomImageRadius();
        this.customImageURL = controlObject.getCustomImageURL();
        this.text = controlObject.getText();
    }



    public void setCurrencyListIds(List<String> currencyListIds) {
        this.currencyListIds = currencyListIds;
    }

    public List<String> getCurrencyListNames() {
        return currencyListNames;
    }

    public void setCurrencyListNames(List<String> currencyListNames) {
        this.currencyListNames = currencyListNames;
    }

    public Boolean getStrikeText() {
        return strikeText;
    }

    public void setStrikeText(Boolean strikeText) {
        this.strikeText = strikeText;
    }

    public Boolean getUnderLineText() {
        return isUnderLineText;
    }

    public void setUnderLineText(Boolean underLineText) {
        isUnderLineText = underLineText;
    }

    public String getControlTitle() {
        return controlTitle;
    }

    public void setControlTitle(String controlTitle) {
        this.controlTitle = controlTitle;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getControlCategory() {
        return controlCategory;
    }

    public void setControlCategory(String controlCategory) {
        this.controlCategory = controlCategory;
    }

    public String getControlID() {
        return controlID;
    }

    public void setControlID(String controlID) {
        this.controlID = controlID;
    }

    public int getControlAddPos() {
        return controlAddPos;
    }

    public void setControlAddPos(int controlAddPos) {
        this.controlAddPos = controlAddPos;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isNullAllowed() {
        return nullAllowed;
    }

    public void setNullAllowed(boolean nullAllowed) {
        this.nullAllowed = nullAllowed;
    }

    public boolean isUniqueField() {
        return uniqueField;
    }

    public void setUniqueField(boolean uniqueField) {
        this.uniqueField = uniqueField;
    }

    public String getUniqueFieldError() {
        return uniqueFieldError;
    }

    public void setUniqueFieldError(String uniqueFieldError) {
        this.uniqueFieldError = uniqueFieldError;
    }

    public String getMandatoryFieldError() {
        return mandatoryFieldError;
    }

    public void setMandatoryFieldError(String mandatoryFieldError) {
        this.mandatoryFieldError = mandatoryFieldError;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getContains() {
        return contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public boolean isReadFromBarcode() {
        return readFromBarcode;
    }

    public void setReadFromBarcode(boolean readFromBarcode) {
        this.readFromBarcode = readFromBarcode;
    }

    public boolean isValueBased() {
        return valueBased;
    }

    public void setValueBased(boolean valueBased) {
        this.valueBased = valueBased;
    }

    public boolean isLengthBased() {
        return lengthBased;
    }

    public void setLengthBased(boolean lengthBased) {
        this.lengthBased = lengthBased;
    }

    public String getMinLength() {
        return minLength;
    }

    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    public String getLengthError() {
        return lengthError;
    }

    public void setLengthError(String lengthError) {
        this.lengthError = lengthError;
    }

    public String getValueError() {
        return valueError;
    }

    public void setValueError(String valueError) {
        this.valueError = valueError;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public List<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Item> itemsList) {
        this.itemsList = itemsList;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public int getDefaultItemIndex() {
        return defaultItemIndex;
    }

    public void setDefaultItemIndex(int defaultItemIndex) {
        this.defaultItemIndex = defaultItemIndex;
    }

    public String getDefaultItem() {
        return defaultItem;
    }

    public void setDefaultItem(String defaultItem) {
        this.defaultItem = defaultItem;
    }

    public String getDefaultItemId() {
        return defaultItemId;
    }

    public void setDefaultItemId(String defaultItemId) {
        this.defaultItemId = defaultItemId;
    }

    public boolean isReadFromQRCode() {
        return readFromQRCode;
    }

    public void setReadFromQRCode(boolean readFromQRCode) {
        this.readFromQRCode = readFromQRCode;
    }

    public boolean isGoogleLocationSearch() {
        return googleLocationSearch;
    }

    public void setGoogleLocationSearch(boolean googleLocationSearch) {
        this.googleLocationSearch = googleLocationSearch;
    }

    public boolean isCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(boolean currentLocation) {
        this.currentLocation = currentLocation;
    }

    public boolean isVoiceText() {
        return voiceText;
    }

    public void setVoiceText(boolean voiceText) {
        this.voiceText = voiceText;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public boolean isEnableMaskCharacters() {
        return enableMaskCharacters;
    }

    public void setEnableMaskCharacters(boolean enableMaskCharacters) {
        this.enableMaskCharacters = enableMaskCharacters;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getTextSize() {
        return textSize;
    }

    public void setTextSize(String textSize) {
        this.textSize = textSize;
    }

    public String getTextSizeId() {
        return textSizeId;
    }

    public void setTextSizeId(String textSizeId) {
        this.textSizeId = textSizeId;
    }

    public String getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
    }

    public String getTextHexColor() {
        return textHexColor;
    }

    public void setTextHexColor(String textHexColor) {
        this.textHexColor = textHexColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }


    public boolean isEnableCappingDigits() {
        return enableCappingDigits;
    }

    public void setEnableCappingDigits(boolean cappingDigits) {
        this.enableCappingDigits = cappingDigits;
    }

    public String getCappingError() {
        return cappingError;
    }

    public void setCappingError(String cappingError) {
        this.cappingError = cappingError;
    }

    public boolean isEnableUpperLimit() {
        return enableUpperLimit;
    }

    public void setEnableUpperLimit(boolean enableUpperLimit) {
        this.enableUpperLimit = enableUpperLimit;
    }

    public boolean isEnableLowerLimit() {
        return enableLowerLimit;
    }

    public void setEnableLowerLimit(boolean enableLowerLimit) {
        this.enableLowerLimit = enableLowerLimit;
    }

    public String getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(String upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(String lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getUpperLimitErrorMesage() {
        return upperLimitErrorMesage;
    }

    public void setUpperLimitErrorMesage(String upperLimitErrorMesage) {
        this.upperLimitErrorMesage = upperLimitErrorMesage;
    }

    public String getLowerLimitErrorMesage() {
        return lowerLimitErrorMesage;
    }

    public void setLowerLimitErrorMesage(String lowerLimitErrorMesage) {
        this.lowerLimitErrorMesage = lowerLimitErrorMesage;
    }

    public String getCappingDigits() {
        return cappingDigits;
    }

    public void setCappingDigits(String cappingDigits) {
        this.cappingDigits = cappingDigits;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isEnableDefaultValue() {
        return enableDefaultValue;
    }

    public void setEnableDefaultValue(boolean enableDefaultValue) {
        this.enableDefaultValue = enableDefaultValue;
    }

    public boolean isEnableCountryCode() {
        return enableCountryCode;
    }

    public void setEnableCountryCode(boolean enableCountryCode) {
        this.enableCountryCode = enableCountryCode;
    }


    public boolean isEnableCountries() {
        return enableCountries;
    }

    public void setEnableCountries(boolean enableCountries) {
        this.enableCountries = enableCountries;
    }

    public List<String> getEnabledCountryIds() {
        return enabledCountryIds;
    }

    public void setEnabledCountryIds(List<String> enabledCountryIds) {
        this.enabledCountryIds = enabledCountryIds;
    }

    public List<String> getEnabledCountryNames() {
        return enabledCountryNames;
    }

    public void setEnabledCountryNames(List<String> enabledCountryNames) {
        this.enabledCountryNames = enabledCountryNames;
    }

    public List<String> getRestrictedDomainIds() {
        return restrictedDomainIds;
    }

    public void setRestrictedDomainIds(List<String> restrictedDomainIds) {
        this.restrictedDomainIds = restrictedDomainIds;
    }

    public List<String> getRestrictedDomainNames() {
        return restrictedDomainNames;
    }

    public void setRestrictedDomainNames(List<String> restrictedDomainNames) {
        this.restrictedDomainNames = restrictedDomainNames;
    }

    public String getMaxCharacters() {
        return maxCharacters;
    }

    public void setMaxCharacters(String maxCharacters) {
        this.maxCharacters = maxCharacters;
    }

    public String getMinCharacters() {
        return minCharacters;
    }

    public void setMinCharacters(String minCharacters) {
        this.minCharacters = minCharacters;
    }

    public boolean isRestrictEmailDomain() {
        return restrictEmailDomain;
    }

    public void setRestrictEmailDomain(boolean restrictEmailDomain) {
        this.restrictEmailDomain = restrictEmailDomain;
    }

    public boolean isEnableMaxCharacters() {
        return enableMaxCharacters;
    }

    public void setEnableMaxCharacters(boolean enableMaxCharacters) {
        this.enableMaxCharacters = enableMaxCharacters;
    }

    public boolean isEnableMinCharacters() {
        return enableMinCharacters;
    }

    public void setEnableMinCharacters(boolean enableMinCharacters) {
        this.enableMinCharacters = enableMinCharacters;
    }

    public String getMaxCharError() {
        return maxCharError;
    }

    public void setMaxCharError(String maxCharError) {
        this.maxCharError = maxCharError;
    }

    public String getMinCharError() {
        return minCharError;
    }

    public void setMinCharError(String minCharError) {
        this.minCharError = minCharError;
    }

    public String getMaxUploadSize() {
        return maxUploadSize;
    }

    public void setMaxUploadSize(String maxUploadSize) {
        this.maxUploadSize = maxUploadSize;
    }

    public String getMinUploadSize() {
        return minUploadSize;
    }

    public void setMinUploadSize(String minUploadSize) {
        this.minUploadSize = minUploadSize;
    }

    public boolean isEnableMaxUploadSize() {
        return enableMaxUploadSize;
    }

    public void setEnableMaxUploadSize(boolean enableMaxUploadSize) {
        this.enableMaxUploadSize = enableMaxUploadSize;
    }

    public boolean isEnableMinUploadSize() {
        return enableMinUploadSize;
    }

    public void setEnableMinUploadSize(boolean enableMinUploadSize) {
        this.enableMinUploadSize = enableMinUploadSize;
    }

    public String getMaxSizeUnit() {
        return maxSizeUnit;
    }

    public void setMaxSizeUnit(String maxSizeUnit) {
        this.maxSizeUnit = maxSizeUnit;
    }

    public String getMinSizeUnit() {
        return minSizeUnit;
    }

    public void setMinSizeUnit(String minSizeUnit) {
        this.minSizeUnit = minSizeUnit;
    }

    public String getMaxUploadError() {
        return maxUploadError;
    }

    public void setMaxUploadError(String maxUploadError) {
        this.maxUploadError = maxUploadError;
    }

    public String getMinUploadError() {
        return minUploadError;
    }

    public void setMinUploadError(String minUploadError) {
        this.minUploadError = minUploadError;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public boolean isEnableAspectRatio() {
        return enableAspectRatio;
    }

    public void setEnableAspectRatio(boolean enableAspectRatio) {
        this.enableAspectRatio = enableAspectRatio;
    }

    public boolean isEnableImageWithGps() {
        return enableImageWithGps;
    }

    public void setEnableImageWithGps(boolean enableImageWithGps) {
        this.enableImageWithGps = enableImageWithGps;
    }

    public String getAspectRatioWidth() {
        return aspectRatioWidth;
    }

    public void setAspectRatioWidth(String aspectRatioWidth) {
        this.aspectRatioWidth = aspectRatioWidth;
    }

    public String getAspectRatioHeight() {
        return aspectRatioHeight;
    }

    public void setAspectRatioHeight(String aspectRatioHeight) {
        this.aspectRatioHeight = aspectRatioHeight;
    }

    public boolean isZoomControl() {
        return zoomControl;
    }

    public void setZoomControl(boolean zoomControl) {
        this.zoomControl = zoomControl;
    }

    public boolean isFlash() {
        return flash;
    }

    public void setFlash(boolean flash) {
        this.flash = flash;
    }

    public boolean isEnableCapture() {
        return enableCapture;
    }

    public void setEnableCapture(boolean enableCapture) {
        this.enableCapture = enableCapture;
    }

    public boolean isCaptureFromCamera() {
        return captureFromCamera;
    }

    public void setCaptureFromCamera(boolean captureFromCamera) {
        this.captureFromCamera = captureFromCamera;
    }

    public boolean isCaptureFromFile() {
        return captureFromFile;
    }

    public void setCaptureFromFile(boolean captureFromFile) {
        this.captureFromFile = captureFromFile;
    }

    public boolean isEnableFilePhoto() {
        return enableFilePhoto;
    }

    public void setEnableFilePhoto(boolean enableFilePhoto) {
        this.enableFilePhoto = enableFilePhoto;
    }

    public boolean isEnableCamera() {
        return enableCamera;
    }

    public void setEnableCamera(boolean enableCamera) {
        this.enableCamera = enableCamera;
    }

    public boolean isEnableExtensions() {
        return enableExtensions;
    }

    public void setEnableExtensions(boolean enableExtensions) {
        this.enableExtensions = enableExtensions;
    }

    public String getFileExtensionError() {
        return fileExtensionError;
    }

    public void setFileExtensionError(String fileExtensionError) {
        this.fileExtensionError = fileExtensionError;
    }

    public List<String> getExtensionsListIds() {
        return extensionsListIds;
    }

    public void setExtensionsListIds(List<String> extensionsListIds) {
        this.extensionsListIds = extensionsListIds;
    }

    public List<String> getExtensionsListNames() {
        return extensionsListNames;
    }

    public void setExtensionsListNames(List<String> extensionsListNames) {
        this.extensionsListNames = extensionsListNames;
    }

    public String getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(String audioFormat) {
        this.audioFormat = audioFormat;
    }

    public List<String> getAudioFormatIds() {
        return audioFormatIds;
    }

    public void setAudioFormatIds(List<String> audioFormatIds) {
        this.audioFormatIds = audioFormatIds;
    }

    public List<String> getAudioFormatNames() {
        return audioFormatNames;
    }

    public void setAudioFormatNames(List<String> audioFormatNames) {
        this.audioFormatNames = audioFormatNames;
    }

    public boolean isGeoTagging() {
        return geoTagging;
    }

    public void setGeoTagging(boolean geoTagging) {
        this.geoTagging = geoTagging;
    }

    public List<String> getEnabledExtensions() {
        return enabledExtensions;
    }

    public void setEnabledExtensions(List<String> enabledExtensions) {
        this.enabledExtensions = enabledExtensions;
    }

    public boolean isEnableBetweenStartAndEndDate() {
        return enableBetweenStartAndEndDate;
    }

    public void setEnableBetweenStartAndEndDate(boolean enableBetweenStartAndEndDate) {
        this.enableBetweenStartAndEndDate = enableBetweenStartAndEndDate;
    }

    public boolean isEnableCurrentDateAsDefault() {
        return enableCurrentDateAsDefault;
    }

    public void setEnableCurrentDateAsDefault(boolean enableCurrentDateAsDefault) {
        this.enableCurrentDateAsDefault = enableCurrentDateAsDefault;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDateError() {
        return startDateError;
    }

    public void setStartDateError(String startDateError) {
        this.startDateError = startDateError;
    }

    public String getEndDateError() {
        return endDateError;
    }

    public void setEndDateError(String endDateError) {
        this.endDateError = endDateError;
    }

    public boolean isEnableBeforeCurrentDate() {
        return enableBeforeCurrentDate;
    }

    public void setEnableBeforeCurrentDate(boolean enableBeforeCurrentDate) {
        this.enableBeforeCurrentDate = enableBeforeCurrentDate;
    }

    public boolean isEnableAfterCurrentDate() {
        return enableAfterCurrentDate;
    }

    public void setEnableAfterCurrentDate(boolean enableAfterCurrentDate) {
        this.enableAfterCurrentDate = enableAfterCurrentDate;
    }

    public String getBetweenStartAndEndDateError() {
        return betweenStartAndEndDateError;
    }

    public void setBetweenStartAndEndDateError(String betweenStartAndEndDateError) {
        this.betweenStartAndEndDateError = betweenStartAndEndDateError;
    }

    public String getBeforeCurrentDateError() {
        return beforeCurrentDateError;
    }

    public void setBeforeCurrentDateError(String beforeCurrentDateError) {
        this.beforeCurrentDateError = beforeCurrentDateError;
    }

    public String getAfterCurrentDateError() {
        return afterCurrentDateError;
    }

    public void setAfterCurrentDateError(String afterCurrentDateError) {
        this.afterCurrentDateError = afterCurrentDateError;
    }

    public boolean isGetYearFromSelection() {
        return getYearFromSelection;
    }

    public void setGetYearFromSelection(boolean getYearFromSelection) {
        this.getYearFromSelection = getYearFromSelection;
    }

    public boolean isGetMonthFromSelection() {
        return getMonthFromSelection;
    }

    public void setGetMonthFromSelection(boolean getMonthFromSelection) {
        this.getMonthFromSelection = getMonthFromSelection;
    }

    public boolean isGetDayFromSelection() {
        return getDayFromSelection;
    }

    public void setGetDayFromSelection(boolean getDayFromSelection) {
        this.getDayFromSelection = getDayFromSelection;
    }

    public boolean isGetDateFromSelection() {
        return getDateFromSelection;
    }

    public void setGetDateFromSelection(boolean getDateFromSelection) {
        this.getDateFromSelection = getDateFromSelection;
    }

    public boolean isEnableSortByAlphabeticalOrder() {
        return enableSortByAlphabeticalOrder;
    }

    public void setEnableSortByAlphabeticalOrder(boolean enableSortByAlphabeticalOrder) {
        this.enableSortByAlphabeticalOrder = enableSortByAlphabeticalOrder;
    }

    public boolean isEnableAllowOtherChoice() {
        return enableAllowOtherChoice;
    }

    public void setEnableAllowOtherChoice(boolean enableAllowOtherChoice) {
        this.enableAllowOtherChoice = enableAllowOtherChoice;
    }

    public boolean isEnableHorizontalAlignment() {
        return enableHorizontalAlignment;
    }

    public void setEnableHorizontalAlignment(boolean enableHorizontalAlignment) {
        this.enableHorizontalAlignment = enableHorizontalAlignment;
    }

    public boolean isEnableSortByChronologicalOrder() {
        return enableSortByChronologicalOrder;
    }

    public void setEnableSortByChronologicalOrder(boolean enableSortByChronologicalOrder) {
        this.enableSortByChronologicalOrder = enableSortByChronologicalOrder;
    }

    public boolean isEnableSortByAscendingOrder() {
        return enableSortByAscendingOrder;
    }

    public void setEnableSortByAscendingOrder(boolean enableSortByAscendingOrder) {
        this.enableSortByAscendingOrder = enableSortByAscendingOrder;
    }

    public boolean isEnableSortByDescendingOrder() {
        return enableSortByDescendingOrder;
    }

    public void setEnableSortByDescendingOrder(boolean enableSortByDescendingOrder) {
        this.enableSortByDescendingOrder = enableSortByDescendingOrder;
    }

    public boolean isDisableRatingCount() {
        return disableRatingCount;
    }

    public void setDisableRatingCount(boolean disableRatingCount) {
        this.disableRatingCount = disableRatingCount;
    }

    public boolean isDisableRating() {
        return disableRating;
    }

    public void setDisableRating(boolean disableRating) {
        this.disableRating = disableRating;
    }

    public boolean isSelectRatingItemType() {
        return selectRatingItemType;
    }

    public void setSelectRatingItemType(boolean selectRatingItemType) {
        this.selectRatingItemType = selectRatingItemType;
    }

    public String getRatingType() {
        return ratingType;
    }

    public void setRatingType(String ratingType) {
        this.ratingType = ratingType;
    }

    public boolean isCustomItemNames() {
        return customItemNames;
    }

    public void setCustomItemNames(boolean customItemNames) {
        this.customItemNames = customItemNames;
    }

    public int getRatingItemColor() {
        return ratingItemColor;
    }

    public void setRatingItemColor(int ratingItemColor) {
        this.ratingItemColor = ratingItemColor;
    }

    public boolean isEnableVoiceMinimumDuration() {
        return enableVoiceMinimumDuration;
    }

    public void setEnableVoiceMinimumDuration(boolean enableVoiceMinimumDuration) {
        this.enableVoiceMinimumDuration = enableVoiceMinimumDuration;
    }

    public boolean isEnableVoiceMaximumDuration() {
        return enableVoiceMaximumDuration;
    }

    public void setEnableVoiceMaximumDuration(boolean enableVoiceMaximumDuration) {
        this.enableVoiceMaximumDuration = enableVoiceMaximumDuration;
    }

    public String getVoiceMinimumDuration() {
        return voiceMinimumDuration;
    }

    public void setVoiceMinimumDuration(String voiceMinimumDuration) {
        this.voiceMinimumDuration = voiceMinimumDuration;
    }

    public String getVoiceMaximumDuration() {
        return voiceMaximumDuration;
    }

    public void setVoiceMaximumDuration(String voiceMaximumDuration) {
        this.voiceMaximumDuration = voiceMaximumDuration;
    }

    public String getMinimumDurationError() {
        return minimumDurationError;
    }

    public void setMinimumDurationError(String minimumDurationError) {
        this.minimumDurationError = minimumDurationError;
    }

    public String getMaximumDurationError() {
        return maximumDurationError;
    }

    public void setMaximumDurationError(String maximumDurationError) {
        this.maximumDurationError = maximumDurationError;
    }

    public boolean isEnableAudioFormat() {
        return enableAudioFormat;
    }

    public void setEnableAudioFormat(boolean enableAudioFormat) {
        this.enableAudioFormat = enableAudioFormat;
    }


    public boolean isEnableVideoMinimumDuration() {
        return enableVideoMinimumDuration;
    }

    public void setEnableVideoMinimumDuration(boolean enableVideoMinimumDuration) {
        this.enableVideoMinimumDuration = enableVideoMinimumDuration;
    }

    public boolean isEnableVideoMaximumDuration() {
        return enableVideoMaximumDuration;
    }

    public void setEnableVideoMaximumDuration(boolean enableVideoMaximumDuration) {
        this.enableVideoMaximumDuration = enableVideoMaximumDuration;
    }

    public String getVideoMinimumDuration() {
        return videoMinimumDuration;
    }

    public void setVideoMinimumDuration(String videoMinimumDuration) {
        this.videoMinimumDuration = videoMinimumDuration;
    }

    public String getVideoMaximumDuration() {
        return videoMaximumDuration;
    }

    public void setVideoMaximumDuration(String videoMaximumDuration) {
        this.videoMaximumDuration = videoMaximumDuration;
    }

    public boolean isEnableUploadVideoFile() {
        return enableUploadVideoFile;
    }

    public void setEnableUploadVideoFile(boolean enableUploadVideoFile) {
        this.enableUploadVideoFile = enableUploadVideoFile;
    }

    public boolean isEnableCompression() {
        return enableCompression;
    }

    public void setEnableCompression(boolean enableCompression) {
        this.enableCompression = enableCompression;
    }

    public String getCompressionQuality() {
        return compressionQuality;
    }

    public void setCompressionQuality(String compressionQuality) {
        this.compressionQuality = compressionQuality;
    }

    public boolean isEnableVideoFormat() {
        return enableVideoFormat;
    }

    public void setEnableVideoFormat(boolean enableVideoFormat) {
        this.enableVideoFormat = enableVideoFormat;
    }

    public String getVideoFormat() {
        return videoFormat;
    }

    public void setVideoFormat(String videoFormat) {
        this.videoFormat = videoFormat;
    }

    public List<String> getVideoFormatIds() {
        return videoFormatIds;
    }

    public void setVideoFormatIds(List<String> videoFormatIds) {
        this.videoFormatIds = videoFormatIds;
    }

    public List<String> getVideoFormatNames() {
        return videoFormatNames;
    }

    public void setVideoFormatNames(List<String> videoFormatNames) {
        this.videoFormatNames = videoFormatNames;
    }

    public boolean isEnablePlayBackground() {
        return enablePlayBackground;
    }

    public void setEnablePlayBackground(boolean enablePlayBackground) {
        this.enablePlayBackground = enablePlayBackground;
    }

    public boolean isEnableStayAwake() {
        return enableStayAwake;
    }

    public void setEnableStayAwake(boolean enableStayAwake) {
        this.enableStayAwake = enableStayAwake;
    }

    public boolean isEnableUploadSignature() {
        return enableUploadSignature;
    }

    public void setEnableUploadSignature(boolean enableUploadSignature) {
        this.enableUploadSignature = enableUploadSignature;
    }

    public boolean isEnableSignatureOnScreen() {
        return enableSignatureOnScreen;
    }

    public void setEnableSignatureOnScreen(boolean enableSignatureOnScreen) {
        this.enableSignatureOnScreen = enableSignatureOnScreen;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlPlaceholderText() {
        return urlPlaceholderText;
    }

    public void setUrlPlaceholderText(String urlPlaceholderText) {
        this.urlPlaceholderText = urlPlaceholderText;
    }

    public boolean isHideURL() {
        return hideURL;
    }

    public void setHideURL(boolean hideURL) {
        this.hideURL = hideURL;
    }

    public boolean isDisableClick() {
        return disableClick;
    }

    public void setDisableClick(boolean disableClick) {
        this.disableClick = disableClick;
    }

    public boolean isEnableStartValue() {
        return enableStartValue;
    }

    public void setEnableStartValue(boolean enableStartValue) {
        this.enableStartValue = enableStartValue;
    }

    public String getStartValue() {
        return startValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue;
    }

    public boolean isEnableDecimalCharacters() {
        return enableDecimalCharacters;
    }

    public void setEnableDecimalCharacters(boolean enableDecimalCharacters) {
        this.enableDecimalCharacters = enableDecimalCharacters;
    }

    public String getCharactersAfterDecimal() {
        return charactersAfterDecimal;
    }

    public void setCharactersAfterDecimal(String charactersAfterDecimal) {
        this.charactersAfterDecimal = charactersAfterDecimal;
    }

    public boolean isEnableShowOrHideOption() {
        return enableShowOrHideOption;
    }

    public void setEnableShowOrHideOption(boolean enableShowOrHideOption) {
        this.enableShowOrHideOption = enableShowOrHideOption;
    }

    public boolean isEnableEncryption() {
        return enableEncryption;
    }

    public void setEnableEncryption(boolean enableEncryption) {
        this.enableEncryption = enableEncryption;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public String getEncryptionTypeId() {
        return encryptionTypeId;
    }

    public void setEncryptionTypeId(String encryptionTypeId) {
        this.encryptionTypeId = encryptionTypeId;
    }

    public boolean isEnablePasswordLength() {
        return enablePasswordLength;
    }

    public void setEnablePasswordLength(boolean enablePasswordLength) {
        this.enablePasswordLength = enablePasswordLength;
    }

    public boolean isEnablePasswordPolicy() {
        return enablePasswordPolicy;
    }

    public void setEnablePasswordPolicy(boolean enablePasswordPolicy) {
        this.enablePasswordPolicy = enablePasswordPolicy;
    }

    public List<String> getPasswordPolicy() {
        return passwordPolicy;
    }

    public void setPasswordPolicy(List<String> passwordPolicy) {
        this.passwordPolicy = passwordPolicy;
    }

    public List<String> getPasswordPolicyIds() {
        return passwordPolicyIds;
    }

    public void setPasswordPolicyIds(List<String> passwordPolicyIds) {
        this.passwordPolicyIds = passwordPolicyIds;
    }

    public String getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(String passwordLength) {
        this.passwordLength = passwordLength;
    }

    public String getPasswordLengthError() {
        return passwordLengthError;
    }

    public void setPasswordLengthError(String passwordLengthError) {
        this.passwordLengthError = passwordLengthError;
    }

    public boolean isEnableMinimumAmount() {
        return enableMinimumAmount;
    }

    public void setEnableMinimumAmount(boolean enableMinimumAmount) {
        this.enableMinimumAmount = enableMinimumAmount;
    }

    public boolean isEnableMaximumAmount() {
        return enableMaximumAmount;
    }

    public void setEnableMaximumAmount(boolean enableMaximumAmount) {
        this.enableMaximumAmount = enableMaximumAmount;
    }

    public boolean isEnableMinimumValue() {
        return enableMinimumValue;
    }

    public void setEnableMinimumValue(boolean enableMinimumValue) {
        this.enableMinimumValue = enableMinimumValue;
    }

    public boolean isEnableMaximumValue() {
        return enableMaximumValue;
    }

    public void setEnableMaximumValue(boolean enableMaximumValue) {
        this.enableMaximumValue = enableMaximumValue;
    }

    public boolean isEnableCurrencySelection() {
        return enableCurrencySelection;
    }

    public void setEnableCurrencySelection(boolean enableCurrencySelection) {
        this.enableCurrencySelection = enableCurrencySelection;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getMinAmountError() {
        return minAmountError;
    }

    public void setMinAmountError(String minAmountError) {
        this.minAmountError = minAmountError;
    }

    public String getMaxAmountError() {
        return maxAmountError;
    }

    public void setMaxAmountError(String maxAmountError) {
        this.maxAmountError = maxAmountError;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getMinimumRows() {
        return minimumRows;
    }

    public void setMinimumRows(String minimumRows) {
        this.minimumRows = minimumRows;
    }

    public String getMinimumRowsError() {
        return minimumRowsError;
    }

    public void setMinimumRowsError(String minimumRowsError) {
        this.minimumRowsError = minimumRowsError;
    }

    public String getMaximumRows() {
        return maximumRows;
    }

    public void setMaximumRows(String maximumRows) {
        this.maximumRows = maximumRows;
    }

    public String getMaximumRowsError() {
        return maximumRowsError;
    }

    public void setMaximumRowsError(String maximumRowsError) {
        this.maximumRowsError = maximumRowsError;
    }

    public boolean isEnableDisplayOrientation() {
        return enableDisplayOrientation;
    }

    public void setEnableDisplayOrientation(boolean enableDisplayOrientation) {
        this.enableDisplayOrientation = enableDisplayOrientation;
    }

    public String getDisplayOrientation() {
        return displayOrientation;
    }

    public void setDisplayOrientation(String displayOrientation) {
        this.displayOrientation = displayOrientation;
    }

    public boolean isEnableDisplayNameOfAddButton() {
        return enableDisplayNameOfAddButton;
    }

    public void setEnableDisplayNameOfAddButton(boolean enableDisplayNameOfAddButton) {
        this.enableDisplayNameOfAddButton = enableDisplayNameOfAddButton;
    }

    public String getDisplayNameOfAddButton() {
        return displayNameOfAddButton;
    }

    public void setDisplayNameOfAddButton(String displayNameOfAddButton) {
        this.displayNameOfAddButton = displayNameOfAddButton;
    }

    public List<ControlObject> getSubFormControlList() {
        return subFormControlList;
    }

    public void setSubFormControlList(List<ControlObject> subFormControlList) {
        this.subFormControlList = subFormControlList;
    }

    public List<ControlObject> getSectionControlList() {
        return sectionControlList;
    }

    public void setSectionControlList(List<ControlObject> sectionControlList) {
        this.sectionControlList = sectionControlList;
    }

    public int getSubFormControlAddPos() {
        return subFormControlAddPos;
    }

    public void setSubFormControlAddPos(int subFormControlAddPos) {
        this.subFormControlAddPos = subFormControlAddPos;
    }

    public boolean isAtaglance() {
        return ataglance;
    }

    public void setAtaglance(boolean ataglance) {
        this.ataglance = ataglance;
    }

    public boolean isEnableLocationFormatting() {
        return enableLocationFormatting;
    }

    public void setEnableLocationFormatting(boolean enableLocationFormatting) {
        this.enableLocationFormatting = enableLocationFormatting;
    }

    public String getLocationFormat() {
        return locationFormat;
    }

    public void setLocationFormat(String locationFormat) {
        this.locationFormat = locationFormat;
    }

    public boolean isEnableSavingLatitudeAndLongitudeInSeparateColumns() {
        return enableSavingLatitudeAndLongitudeInSeparateColumns;
    }

    public void setEnableSavingLatitudeAndLongitudeInSeparateColumns(boolean enableSavingLatitudeAndLongitudeInSeparateColumns) {
        this.enableSavingLatitudeAndLongitudeInSeparateColumns = enableSavingLatitudeAndLongitudeInSeparateColumns;
    }



    public boolean isEnableAccuracy() {
        return enableAccuracy;
    }

    public void setEnableAccuracy(boolean enableAccuracy) {
        this.enableAccuracy = enableAccuracy;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public boolean getEnableLocationMode() {
        return enableLocationMode;
    }

    public String getLocationMode() {
        return locationMode;
    }

    public void setLocationMode(String locationMode) {
        this.locationMode = locationMode;
    }

    public boolean isEnableGpsType() {
        return enableGpsType;
    }

    public void setEnableGpsType(boolean enableGpsType) {
        this.enableGpsType = enableGpsType;
    }

    public String getGpsType() {
        return gpsType;
    }

    public void setGpsType(String gpsType) {
        this.gpsType = gpsType;
    }

    public boolean isEnableLocationMode() {
        return enableLocationMode;
    }

    public void setEnableLocationMode(boolean enableLocationMode) {
        this.enableLocationMode = enableLocationMode;
    }

    public String getGpsTypeID() {
        return gpsTypeID;
    }

    public void setGpsTypeID(String gpsTypeID) {
        this.gpsTypeID = gpsTypeID;
    }

    public boolean isEnableVehicleTracking() {
        return enableVehicleTracking;
    }

    public void setEnableVehicleTracking(boolean enableVehicleTracking) {
        this.enableVehicleTracking = enableVehicleTracking;
    }

    public boolean isEnableGeoFencing() {
        return enableGeoFencing;
    }

    public void setEnableGeoFencing(boolean enableGeoFencing) {
        this.enableGeoFencing = enableGeoFencing;
    }

    public boolean isShowMap() {
        return showMap;
    }

    public void setShowMap(boolean showMap) {
        this.showMap = showMap;
    }

    public boolean isEnableDisplayAsBarCode() {
        return enableDisplayAsBarCode;
    }

    public void setEnableDisplayAsBarCode(boolean enableDisplayAsBarCode) {
        this.enableDisplayAsBarCode = enableDisplayAsBarCode;
    }

    public boolean isEnableDisplayAsQRCode() {
        return enableDisplayAsQRCode;
    }

    public void setEnableDisplayAsQRCode(boolean enableDisplayAsQRCode) {
        this.enableDisplayAsQRCode = enableDisplayAsQRCode;
    }

    public boolean isEnableUnicode() {
        return enableUnicode;
    }

    public void setEnableUnicode(boolean enableUnicode) {
        this.enableUnicode = enableUnicode;
    }

    public String getUnicodeFormat() {
        return unicodeFormat;
    }

    public void setUnicodeFormat(String unicodeFormat) {
        this.unicodeFormat = unicodeFormat;
    }

    public String getUnicodeFormatId() {
        return unicodeFormatId;
    }

    public void setUnicodeFormatId(String unicodeFormatId) {
        this.unicodeFormatId = unicodeFormatId;
    }

    public String getTypeOfInterval() {
        return typeOfInterval;
    }

    public void setTypeOfInterval(String typeOfInterval) {
        this.typeOfInterval = typeOfInterval;
    }

    public String getDistanceInMeters() {
        return distanceInMeters;
    }

    public void setDistanceInMeters(String distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public String getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(String timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public String getDistanceAround() {
        return distanceAround;
    }

    public void setDistanceAround(String distanceAround) {
        this.distanceAround = distanceAround;
    }

    public String getNearBy() {
        return nearBy;
    }

    public void setNearBy(String nearBy) {
        this.nearBy = nearBy;
    }

    public String getMaskCharacterType() {
        return maskCharacterType;
    }

    public void setMaskCharacterType(String maskCharacterType) {
        this.maskCharacterType = maskCharacterType;
    }

    public String getNoOfCharactersToMask() {
        return noOfCharactersToMask;
    }

    public void setNoOfCharactersToMask(String noOfCharactersToMask) {
        this.noOfCharactersToMask = noOfCharactersToMask;
    }

    public String getMaskCharacterDirection() {
        return maskCharacterDirection;
    }

    public void setMaskCharacterDirection(String maskCharacterDirection) {
        this.maskCharacterDirection = maskCharacterDirection;
    }

    public String getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(String backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public String getBackGroupImage() {
        return backGroupImage;
    }

    public void setBackGroupImage(String backGroupImage) {
        this.backGroupImage = backGroupImage;
    }

    public boolean isLayoutBackGroundEnable() {
        return isLayoutBackGroundEnable;
    }

    public void setLayoutBackGroundEnable(boolean layoutBackGroundEnable) {
        isLayoutBackGroundEnable = layoutBackGroundEnable;
    }

    public String getLayoutBackGroundColor() {
        return LayoutBackGroundColor;
    }

    public void setLayoutBackGroundColor(String layoutBackGroundColor) {
        LayoutBackGroundColor = layoutBackGroundColor;
    }

    public List<Control_EventObject> getList_OnchangeEvents() {
        return List_OnchangeEvents;
    }

    public void setList_OnchangeEvents(List<Control_EventObject> list_OnchangeEvents) {
        List_OnchangeEvents = list_OnchangeEvents;
    }

    public List<Control_EventObject> getList_OnFocusEvents() {
        return List_OnFocusEvents;
    }

    public void setList_OnFocusEvents(List<Control_EventObject> list_OnFocusEvents) {
        List_OnFocusEvents = list_OnFocusEvents;
    }

    public boolean isEnableUploadAudioFile() {
        return enableUploadAudioFile;
    }

    public void setEnableUploadAudioFile(boolean enableUploadAudioFile) {
        this.enableUploadAudioFile = enableUploadAudioFile;
    }

    public String getRatingItemCount() {
        return ratingItemCount;
    }

    public void setRatingItemCount(String ratingItemCount) {
        this.ratingItemCount = ratingItemCount;
    }

    public String getFilePath() {
        return filePath;
    }
    public String getImageDataType() {
        return ImageDataType;
    }

    public void setImageDataType(String imageDataType) {
        ImageDataType = imageDataType;
    }

    public String getImageData() {
        return ImageData;
    }

    public void setImageData(String imageData) {
        ImageData = imageData;
    }


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isEnableMultipleImages() {
        return enableMultipleImages;
    }

    public void setEnableMultipleImages(boolean enableMultipleImages) {
        this.enableMultipleImages = enableMultipleImages;
    }

    public String getImagesArrangementType() {
        return imagesArrangementType;
    }

    public void setImagesArrangementType(String imagesArrangementType) {
        this.imagesArrangementType = imagesArrangementType;
    }

    public boolean isMakeAsSection() {
        return makeAsSection;
    }

    public void setMakeAsSection(boolean makeAsSection) {
        this.makeAsSection = makeAsSection;
    }

    public boolean isHideDisplayName() {
        return hideDisplayName;
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        this.hideDisplayName = hideDisplayName;
    }

    public boolean isEnableHorizontalScroll() {
        return enableHorizontalScroll;
    }

    public void setEnableHorizontalScroll(boolean enableHorizontalScroll) {
        this.enableHorizontalScroll = enableHorizontalScroll;
    }

    public Control_EventObject getOnChangeEventObject() {
        return onChangeEventObject;
    }

    public void setOnChangeEventObject(Control_EventObject onChangeEventObject) {
        this.onChangeEventObject = onChangeEventObject;
    }

    public Control_EventObject getOnFocusEventObject() {
        return onFocusEventObject;
    }

    public void setOnFocusEventObject(Control_EventObject onFocusEventObject) {
        this.onFocusEventObject = onFocusEventObject;
    }

    public Control_EventObject getOnAddRowEventObject() {
        return onAddRowEventObject;
    }

    public void setOnAddRowEventObject(Control_EventObject onAddRowEventObject) {
        this.onAddRowEventObject = onAddRowEventObject;
    }

    public boolean isOnAddRowEventExists() {
        return onAddRowEventExists;
    }

    public void setOnAddRowEventExists(boolean onAddRowEventExists) {
        this.onAddRowEventExists = onAddRowEventExists;
    }

    public Control_EventObject getOnDeleteRowEventObject() {
        return onDeleteRowEventObject;
    }

    public void setOnDeleteRowEventObject(Control_EventObject onDeleteRowEventObject) {
        this.onDeleteRowEventObject = onDeleteRowEventObject;
    }

    public boolean isOnDeleteRowEventExists() {
        return onDeleteRowEventExists;
    }

    public void setOnDeleteRowEventExists(boolean onDeleteRowEventExists) {
        this.onDeleteRowEventExists = onDeleteRowEventExists;
    }

    public Control_EventObject getOnClickEventObject() {
        return onClickEventObject;
    }

    public void setOnClickEventObject(Control_EventObject onClickEventObject) {
        this.onClickEventObject = onClickEventObject;
    }

    public boolean isOnClickEventExists() {
        return onClickEventExists;
    }

    public void setOnClickEventExists(boolean onClickEventExists) {
        this.onClickEventExists = onClickEventExists;
    }

    public boolean isOnChangeEventExists() {
        return onChangeEventExists;
    }

    public void setOnChangeEventExists(boolean onChangeEventExists) {
        this.onChangeEventExists = onChangeEventExists;
    }

    public boolean isOnFocusEventExists() {
        return onFocusEventExists;
    }

    public void setOnFocusEventExists(boolean onFocusEventExists) {
        this.onFocusEventExists = onFocusEventExists;
    }

    /*Data standard*/

    public String getDataControlStatus() {
        return dataControlStatus;
    }

    public void setDataControlStatus(String dataControlStatus) {
        this.dataControlStatus = dataControlStatus;
    }

    public String getDependentControl() {
        return dependentControl;
    }

    public void setDependentControl(String dependentControl) {
        this.dependentControl = dependentControl;
    }

    public String getDataControlAccessibility() {
        return dataControlAccessibility;
    }

    public void setDataControlAccessibility(String dataControlAccessibility) {
        this.dataControlAccessibility = dataControlAccessibility;
    }

    public String getDataControlTextFileName() {
        return dataControlTextFileName;
    }

    public void setDataControlTextFileName(String dataControlTextFileName) {
        this.dataControlTextFileName = dataControlTextFileName;
    }

    public String getDataControlTextFilePath() {
        return dataControlTextFilePath;
    }

    public void setDataControlTextFilePath(String dataControlTextFilePath) {
        this.dataControlTextFilePath = dataControlTextFilePath;
    }

    public int getDataControlIndex() {
        return dataControlIndex;
    }

    public void setDataControlIndex(int dataControlIndex) {
        this.dataControlIndex = dataControlIndex;
    }

    public String getDataControlName() {
        return dataControlName;
    }

    public void setDataControlName(String dataControlName) {
        this.dataControlName = dataControlName;
    }

    public boolean isEditClicked() {
        return editClicked;
    }

    public void setEditClicked(boolean editClicked) {
        this.editClicked = editClicked;
    }

    public boolean isUserControlBind() {
        return userControlBind;
    }

    public void setUserControlBind(boolean userControlBind) {
        this.userControlBind = userControlBind;
    }


    public String getDataControlLocationType() {
        return dataControlLocationType;
    }

    public void setDataControlLocationType(String dataControlLocationType) {
        this.dataControlLocationType = dataControlLocationType;
    }

    public boolean isEnableUserControlBinding() {
        return enableUserControlBinding;
    }

    public void setEnableUserControlBinding(boolean enableUserControlBinding) {
        this.enableUserControlBinding = enableUserControlBinding;
    }

    public boolean isEnablePostLocationBinding() {
        return enablePostLocationBinding;
    }

    public void setEnablePostLocationBinding(boolean enablePostLocationBinding) {
        this.enablePostLocationBinding = enablePostLocationBinding;
    }

    public String getControlValue() {
        return controlValue;
    }

    public void setControlValue(String controlValue) {
        this.controlValue = controlValue;
    }

    public String getFieldDisplayFormat() {
        return fieldDisplayFormat;
    }

    public void setFieldDisplayFormat(String fieldDisplayFormat) {
        this.fieldDisplayFormat = fieldDisplayFormat;
    }

    public String getAudioData() {
        return audioData;
    }

    public void setAudioData(String audioData) {
        this.audioData = audioData;
    }

    public String getVideoData() {
        return videoData;
    }

    public void setVideoData(String videoData) {
        this.videoData = videoData;
    }

    public String getTypeOfMenu() {
        return typeOfMenu;
    }

    public void setTypeOfMenu(String typeOfMenu) {
        this.typeOfMenu = typeOfMenu;
    }

    public String getNoOfRows() {
        return noOfRows;
    }

    public void setNoOfRows(String noOfRows) {
        this.noOfRows = noOfRows;
    }

    public String getNoofColumns() {
        return noofColumns;
    }

    public void setNoofColumns(String noofColumns) {
        this.noofColumns = noofColumns;
    }

    public boolean isFitToScreenWidth() {
        return fitToScreenWidth;
    }

    public void setFitToScreenWidth(boolean fitToScreenWidth) {
        this.fitToScreenWidth = fitToScreenWidth;
    }

    public boolean isFitToScreenHeight() {
        return fitToScreenHeight;
    }

    public void setFitToScreenHeight(boolean fitToScreenHeight) {
        this.fitToScreenHeight = fitToScreenHeight;
    }

    public int getTypeOfBorder() {
        return typeOfBorder;
    }

    public void setTypeOfBorder(int typeOfBorder) {
        this.typeOfBorder = typeOfBorder;
    }

    public String getBorderHexColor() {
        return borderHexColor;
    }

    public void setBorderHexColor(String borderHexColor) {
        this.borderHexColor = borderHexColor;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public String getBorderMarginLeft() {
        return borderMarginLeft;
    }

    public void setBorderMarginLeft(String borderMarginLeft) {
        this.borderMarginLeft = borderMarginLeft;
    }

    public String getBorderMarginRight() {
        return borderMarginRight;
    }

    public void setBorderMarginRight(String borderMarginRight) {
        this.borderMarginRight = borderMarginRight;
    }

    public String getBorderMarginTop() {
        return borderMarginTop;
    }

    public void setBorderMarginTop(String borderMarginTop) {
        this.borderMarginTop = borderMarginTop;
    }

    public String getBorderMarginBottom() {
        return borderMarginBottom;
    }

    public void setBorderMarginBottom(String borderMarginBottom) {
        this.borderMarginBottom = borderMarginBottom;
    }

    public String getBorderPaddingLeft() {
        return borderPaddingLeft;
    }

    public void setBorderPaddingLeft(String borderPaddingLeft) {
        this.borderPaddingLeft = borderPaddingLeft;
    }

    public String getBorderPaddingRight() {
        return borderPaddingRight;
    }

    public void setBorderPaddingRight(String borderPaddingRight) {
        this.borderPaddingRight = borderPaddingRight;
    }

    public String getBorderPaddingTop() {
        return borderPaddingTop;
    }

    public void setBorderPaddingTop(String borderPaddingTop) {
        this.borderPaddingTop = borderPaddingTop;
    }

    public String getBorderPaddingBottom() {
        return borderPaddingBottom;
    }

    public void setBorderPaddingBottom(String borderPaddingBottom) {
        this.borderPaddingBottom = borderPaddingBottom;
    }

    public String getTypeOfButton() {
        return typeOfButton;
    }

    public void setTypeOfButton(String typeOfButton) {
        this.typeOfButton = typeOfButton;
    }

    public String getButtonIconAlignment() {
        return buttonIconAlignment;
    }

    public void setButtonIconAlignment(String buttonIconAlignment) {
        this.buttonIconAlignment = buttonIconAlignment;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(String buttonColor) {
        this.buttonColor = buttonColor;
    }

    public String getButtonHexColor() {
        return buttonHexColor;
    }

    public void setButtonHexColor(String buttonHexColor) {
        this.buttonHexColor = buttonHexColor;
    }

    public String getButtonRadius() {
        return buttonRadius;
    }

    public void setButtonRadius(String buttonRadius) {
        this.buttonRadius = buttonRadius;
    }

    public List<ControlObject> getMenuControlObjectList() {
        return menuControlObjectList;
    }

    public void setMenuControlObjectList(List<ControlObject> menuControlObjectList) {
        this.menuControlObjectList = menuControlObjectList;
    }

    public String getDataViewer_UI_Pattern() {
        return DataViewer_UI_Pattern;
    }

    public void setDataViewer_UI_Pattern(String dataViewer_UI_Pattern) {
        DataViewer_UI_Pattern = dataViewer_UI_Pattern;
    }

    public String getDataViewer_Shape() {
        return DataViewer_Shape;
    }

    public void setDataViewer_Shape(String dataViewer_Shape) {
        DataViewer_Shape = dataViewer_Shape;
    }

    public String getDataViewer_DefualtImage_path() {
        return DataViewer_DefualtImage_path;
    }

    public void setDataViewer_DefualtImage_path(String dataViewer_DefualtImage_path) {
        DataViewer_DefualtImage_path = dataViewer_DefualtImage_path;
    }

    public String getDataViewer_FrameBG_HexColor() {
        return DataViewer_FrameBG_HexColor;
    }

    public void setDataViewer_FrameBG_HexColor(String dataViewer_FrameBG_HexColor) {
        DataViewer_FrameBG_HexColor = dataViewer_FrameBG_HexColor;
    }

    public String getDataViewer_FrameBG_HexColorTwo() {
        return DataViewer_FrameBG_HexColorTwo;
    }

    public void setDataViewer_FrameBG_HexColorTwo(String dataViewer_FrameBG_HexColorTwo) {
        DataViewer_FrameBG_HexColorTwo = dataViewer_FrameBG_HexColorTwo;
    }

    public String getDataViewer_Header_HexColor() {
        return DataViewer_Header_HexColor;
    }

    public void setDataViewer_Header_HexColor(String dataViewer_Header_HexColor) {
        DataViewer_Header_HexColor = dataViewer_Header_HexColor;
    }

    public String getDataViewer_SubHeader_HexColor() {
        return DataViewer_SubHeader_HexColor;
    }

    public void setDataViewer_SubHeader_HexColor(String dataViewer_SubHeader_HexColor) {
        DataViewer_SubHeader_HexColor = dataViewer_SubHeader_HexColor;
    }

    public String getDataViewer_Description_HexColor() {
        return DataViewer_Description_HexColor;
    }

    public void setDataViewer_Description_HexColor(String dataViewer_Description_HexColor) {
        DataViewer_Description_HexColor = dataViewer_Description_HexColor;
    }

    public String getDataViewer_DateTime_HexColor() {
        return DataViewer_DateTime_HexColor;
    }

    public void setDataViewer_DateTime_HexColor(String dataViewer_DateTime_HexColor) {
        DataViewer_DateTime_HexColor = dataViewer_DateTime_HexColor;
    }

    public void setPanelType(String panelType) {
        this.panelType = panelType;
    }

    public String getPanelType() {
        return panelType;
    }

    public String getSectionBGHexColor() {
        return sectionBGHexColor;
    }

    public void setSectionBGHexColor(String sectionBGHexColor) {
        this.sectionBGHexColor = sectionBGHexColor;
    }

    public int getSectionBGColor() {
        return sectionBGColor;
    }

    public void setSectionBGColor(int sectionBGColor) {
        this.sectionBGColor = sectionBGColor;
    }

    public List<RenderingType> getRenderingTypeList() {
        return renderingTypeList;
    }

    public void setRenderingTypeList(List<RenderingType> renderingTypeList) {
        this.renderingTypeList = renderingTypeList;
    }

    public String getBaseMap() {
        return baseMap;
    }

    public void setBaseMap(String baseMap) {
        this.baseMap = baseMap;
    }

    public String getMapView() {
        return mapView;
    }

    public void setMapView(String mapView) {
        this.mapView = mapView;
    }


    public String getMapViewType() {
        return mapViewType;
    }

    public void setMapViewType(String mapViewType) {
        this.mapViewType = mapViewType;
    }

    public boolean isZoomControls() {
        return zoomControls;
    }

    public void setZoomControls(boolean zoomControls) {
        this.zoomControls = zoomControls;
    }

    public String getMapIcon() {
        return mapIcon;
    }

    public void setMapIcon(String mapIcon) {
        this.mapIcon = mapIcon;
    }

    public String getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(String mapHeight) {
        this.mapHeight = mapHeight;
    }

    public boolean isShowCurrentLocation() {
        return showCurrentLocation;
    }

    public void setShowCurrentLocation(boolean showCurrentLocation) {
        this.showCurrentLocation = showCurrentLocation;
    }

    public List<CalendarEvent> getCalendarEventType() {
        return calendarEventType;
    }

    public void setCalendarEventType(List<CalendarEvent> calendarEventType) {
        this.calendarEventType = calendarEventType;
    }

    public boolean isWeekDays() {
        return isWeekDays;
    }

    public void setWeekDays(boolean weekDays) {
        isWeekDays = weekDays;
    }





    public boolean isEnableFixGridWidth() {
        return enableFixGridWidth;
    }

    public void setEnableFixGridWidth(boolean enableFixGridWidth) {
        this.enableFixGridWidth = enableFixGridWidth;
    }

    public List<GridColumnSettings> getGridColumnSettings() {
        return gridColumnSettings;
    }

    public void setGridColumnSettings(List<GridColumnSettings> gridColumnSettings) {
        this.gridColumnSettings = gridColumnSettings;
    }

    public boolean isGridControl() {
        return isGridControl;
    }

    public void setGridControl(boolean gridControl) {
        isGridControl = gridControl;
    }

    public boolean isHideBorder() {
        return hideBorder;
    }

    public void setHideBorder(boolean hideBorder) {
        this.hideBorder = hideBorder;
    }

    public boolean isHideColumnNames() {
        return hideColumnNames;
    }

    public void setHideColumnNames(boolean hideColumnNames) {
        this.hideColumnNames = hideColumnNames;
    }

    public String getShape() {
        return Shape;
    }

    public void setShape(String shape) {
        Shape = shape;
    }

    /*MultiComment Control*/

    /*Translations*/

    public LinkedHashMap<String, String> getTranslationsMap() {
        return translationsMap;
    }

    public void setTranslationsMap(LinkedHashMap<String, String> translationsMap) {
        this.translationsMap = translationsMap;
    }

    public LinkedHashMap<String, ControlObject> getTranslationsMappingObject() {
        return translationsMappingObject;
    }

    public void setTranslationsMappingObject(LinkedHashMap<String, ControlObject> translationsMappingObject) {
        this.translationsMappingObject = translationsMappingObject;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isShowUsersWithPostName() {
        return showUsersWithPostName;
    }

    public void setShowUsersWithPostName(boolean showUsersWithPostName) {
        this.showUsersWithPostName = showUsersWithPostName;
    }

    public List<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
    }

    public TableSettingSObject_Bean getSubFormtableSettingsObject() {
        return subFormtableSettingsObject;
    }

    public void setSubFormtableSettingsObject(TableSettingSObject_Bean subFormtableSettingsObject) {
        this.subFormtableSettingsObject = subFormtableSettingsObject;
    }

    public List<String> getSubFormList_Table_Columns() {
        return subFormList_Table_Columns;
    }

    public void setSubFormList_Table_Columns(List<String> subFormList_Table_Columns) {
        this.subFormList_Table_Columns = subFormList_Table_Columns;
    }

    public List<QueryFilterField_Bean> getSubFormWhereConditionFields() {
        return subFormWhereConditionFields;
    }

    public void setSubFormWhereConditionFields(List<QueryFilterField_Bean> subFormWhereConditionFields) {
        this.subFormWhereConditionFields = subFormWhereConditionFields;
    }

    public List<QueryFilterField_Bean> getSubFormUpdateFields() {
        return subFormUpdateFields;
    }

    public void setSubFormUpdateFields(List<QueryFilterField_Bean> subFormUpdateFields) {
        this.subFormUpdateFields = subFormUpdateFields;
    }

    public List<QueryFilterField_Bean> getSubFormInsertFields() {
        return subFormInsertFields;
    }

    public void setSubFormInsertFields(List<QueryFilterField_Bean> subFormInsertFields) {
        this.subFormInsertFields = subFormInsertFields;
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


    public PrimaryLayoutModelClass getPrimaryLayoutModelClass() {
        return primaryLayoutModelClass;
    }

    public void setPrimaryLayoutModelClass(PrimaryLayoutModelClass primaryLayoutModelClass) {
        this.primaryLayoutModelClass = primaryLayoutModelClass;
    }

    public boolean isUIFormNeededSubForm() {
        return isUIFormNeededSubForm;
    }

    public void setUIFormNeededSubForm(boolean UIFormNeededSubForm) {
        isUIFormNeededSubForm = UIFormNeededSubForm;
    }

    public String getUiType() {
        return uiType;
    }

    public void setUiType(String uiType) {
        this.uiType = uiType;
    }

    public boolean isSectionUIFormNeeded() {
        return isSectionUIFormNeeded;
    }

    public void setSectionUIFormNeeded(boolean sectionUIFormNeeded) {
        isSectionUIFormNeeded = sectionUIFormNeeded;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public boolean isHideLegends() {
        return hideLegends;
    }

    public void setHideLegends(boolean hideLegends) {
        this.hideLegends = hideLegends;
    }

    public String getChartColorHex() {
        return chartColorHex;
    }

    public void setChartColorHex(String chartColorHex) {
        this.chartColorHex = chartColorHex;
    }

    public int getChartColor() {
        return chartColor;
    }

    public void setChartColor(int chartColor) {
        this.chartColor = chartColor;
    }


    public String getPreFixValue() {
        return preFixValue;
    }

    public void setPreFixValue(String preFixValue) {
        this.preFixValue = preFixValue;
    }

    public String getSuffixValue() {
        return suffixValue;
    }

    public void setSuffixValue(String suffixValue) {
        this.suffixValue = suffixValue;
    }

    public String getSuffix1Value() {
        return suffix1Value;
    }

    public void setSuffix1Value(String suffix1Value) {
        this.suffix1Value = suffix1Value;
    }


    public List<String> getColumnHeaders() {
        return columnHeaders;
    }

    public void setColumnHeaders(List<String> columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

    public boolean isMandatoryTime() {
        return isMandatoryTime;
    }

    public void setMandatoryTime(boolean mandatoryTime) {
        isMandatoryTime = mandatoryTime;
    }

    public String getMandatoryTimeErrorMessage() {
        return mandatoryTimeErrorMessage;
    }

    public void setMandatoryTimeErrorMessage(String mandatoryTimeErrorMessage) {
        this.mandatoryTimeErrorMessage = mandatoryTimeErrorMessage;
    }

    public boolean isBeforeCurrentTime() {
        return isBeforeCurrentTime;
    }

    public void setBeforeCurrentTime(boolean beforeCurrentTime) {
        isBeforeCurrentTime = beforeCurrentTime;
    }

    public String getBeforeCurrentTimeErrorMessage() {
        return beforeCurrentTimeErrorMessage;
    }

    public void setBeforeCurrentTimeErrorMessage(String beforeCurrentTimeErrorMessage) {
        this.beforeCurrentTimeErrorMessage = beforeCurrentTimeErrorMessage;
    }

    public boolean isAfterCurrentTime() {
        return isAfterCurrentTime;
    }

    public void setAfterCurrentTime(boolean afterCurrentTime) {
        isAfterCurrentTime = afterCurrentTime;
    }

    public String getAfterCurrentTimeErrorMessage() {
        return afterCurrentTimeErrorMessage;
    }

    public void setAfterCurrentTimeErrorMessage(String afterCurrentTimeErrorMessage) {
        this.afterCurrentTimeErrorMessage = afterCurrentTimeErrorMessage;
    }

    public boolean isBetweenStartEndTime() {
        return isBetweenStartEndTime;
    }

    public void setBetweenStartEndTime(boolean betweenStartEndTime) {
        isBetweenStartEndTime = betweenStartEndTime;
    }

    public String getBetweenStartTime() {
        return betweenStartTime;
    }

    public void setBetweenStartTime(String betweenStartTime) {
        this.betweenStartTime = betweenStartTime;
    }

    public String getBetweenEndTime() {
        return betweenEndTime;
    }

    public void setBetweenEndTime(String betweenEndTime) {
        this.betweenEndTime = betweenEndTime;
    }

    public String getBetweenStartEndTimeErrorMessage() {
        return betweenStartEndTimeErrorMessage;
    }

    public void setBetweenStartEndTimeErrorMessage(String betweenStartEndTimeErrorMessage) {
        this.betweenStartEndTimeErrorMessage = betweenStartEndTimeErrorMessage;
    }

    public boolean isCurrentTime() {
        return isCurrentTime;
    }

    public void setCurrentTime(boolean currentTime) {
        isCurrentTime = currentTime;
    }

    public boolean isTimeFormat() {
        return isTimeFormat;
    }

    public void setTimeFormat(boolean timeFormat) {
        isTimeFormat = timeFormat;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public boolean isTimeFormatOptions() {
        return isTimeFormatOptions;
    }

    public void setTimeFormatOptions(boolean timeFormatOptions) {
        isTimeFormatOptions = timeFormatOptions;
    }

    public String getTimeFormatOptions() {
        return timeFormatOptions;
    }

    public void setTimeFormatOptions(String timeFormatOptions) {
        this.timeFormatOptions = timeFormatOptions;
    }

    public boolean isHtmlEditorEnabled() {
        return htmlEditorEnabled;
    }

    public void setHtmlEditorEnabled(boolean htmlEditorEnabled) {
        this.htmlEditorEnabled = htmlEditorEnabled;
    }

    public boolean isHtmlViewerEnabled() {
        return htmlViewerEnabled;
    }

    public void setHtmlViewerEnabled(boolean htmlViewerEnabled) {
        this.htmlViewerEnabled = htmlViewerEnabled;
    }

    public String getDisplayNameAlignment() {
        return displayNameAlignment;
    }

    public void setDisplayNameAlignment(String displayNameAlignment) {
        this.displayNameAlignment = displayNameAlignment;
    }

    public String getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(String textAlignment) {
        this.textAlignment = textAlignment;
    }

    public String getBackgroundType() {
        return backgroundType;
    }

    public void setBackgroundType(String backgroundType) {
        this.backgroundType = backgroundType;
    }

    public String getIconAlignment() {
        return iconAlignment;
    }

    public void setIconAlignment(String iconAlignment) {
        this.iconAlignment = iconAlignment;
    }

    public String getRowSelectionType() {
        return rowSelectionType;
    }

    public void setRowSelectionType(String rowSelectionType) {
        this.rowSelectionType = rowSelectionType;
    }

    public UIPrimaryLayoutModelClass getUiPrimaryLayoutModelClass() {
        return uiPrimaryLayoutModelClass;
    }

    public void setUiPrimaryLayoutModelClass(UIPrimaryLayoutModelClass uiPrimaryLayoutModelClass) {
        this.uiPrimaryLayoutModelClass = uiPrimaryLayoutModelClass;
    }

    public int getMinChartoSeearch() {
        return minChartoSeearch;
    }

    public void setMinChartoSeearch(int minChartoSeearch) {
        this.minChartoSeearch = minChartoSeearch;
    }

    public String getSearchKeyAt() {
        return searchKeyAt;
    }

    public void setSearchKeyAt(String searchKeyAt) {
        this.searchKeyAt = searchKeyAt;
    }

    public boolean isLazyLoadingEnabled() {
        return lazyLoadingEnabled;
    }

    public void setLazyLoadingEnabled(boolean lazyLoadingEnabled) {
        this.lazyLoadingEnabled = lazyLoadingEnabled;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public int getCurrentMaxPosition() {
        return currentMaxPosition;
    }

    public void setCurrentMaxPosition(int currentMaxPosition) {
        this.currentMaxPosition = currentMaxPosition;
    }


    public String getSubmitButtonMessage() {
        return SubmitButtonMessage;
    }

    public void setSubmitButtonMessage(String submitButtonMessage) {
        SubmitButtonMessage = submitButtonMessage;
    }

    public String getSubmitButtonMessageDisplayType() {
        return SubmitButtonMessageDisplayType;
    }

    public void setSubmitButtonMessageDisplayType(String submitButtonMessageDisplayType) {
        SubmitButtonMessageDisplayType = submitButtonMessageDisplayType;
    }

    public String getSubmitButtonSuccessMessage() {
        return SubmitButtonSuccessMessage;
    }

    public void setSubmitButtonSuccessMessage(String submitButtonSuccessMessage) {
        SubmitButtonSuccessMessage = submitButtonSuccessMessage;
    }

    public String getSubmitButtonFailMessage() {
        return SubmitButtonFailMessage;
    }

    public void setSubmitButtonFailMessage(String submitButtonFailMessage) {
        SubmitButtonFailMessage = submitButtonFailMessage;
    }

    public boolean isAppClose() {
        return isAppClose;
    }

    public void setAppClose(boolean appClose) {
        isAppClose = appClose;
    }

    public boolean isEnableSubFormDelete() {
        return enableSubFormDelete;
    }

    public void setEnableSubFormDelete(boolean enableSubFormDelete) {
        this.enableSubFormDelete = enableSubFormDelete;
    }

    public boolean isEnableSubFormAddNewRow() {
        return enableSubFormAddNewRow;
    }

    public void setEnableSubFormAddNewRow(boolean enableSubFormAddNewRow) {
        this.enableSubFormAddNewRow = enableSubFormAddNewRow;
    }

    public boolean isEnableScan() {
        return enableScan;
    }

    public void setEnableScan(boolean enableScan) {
        this.enableScan = enableScan;
    }

    public String getProgress_maxvalue() {
        return progress_maxvalue;
    }

    public void setProgress_maxvalue(String progress_maxvalue) {
        this.progress_maxvalue = progress_maxvalue;
    }

    public String getProgress_actualvalue() {
        return progress_actualvalue;
    }

    public void setProgress_actualvalue(String progress_actualvalue) {
        this.progress_actualvalue = progress_actualvalue;
    }

    public boolean isHide_progress_maxvalue() {
        return hide_progress_maxvalue;
    }

    public void setHide_progress_maxvalue(boolean hide_progress_maxvalue) {
        this.hide_progress_maxvalue = hide_progress_maxvalue;
    }

    public boolean isHide_progress_actualvalue() {
        return hide_progress_actualvalue;
    }

    public void setHide_progress_actualvalue(boolean hide_progress_actualvalue) {
        this.hide_progress_actualvalue = hide_progress_actualvalue;
    }

    public String getProgressColorHex() {
        return progressColorHex;
    }

    public void setProgressColorHex(String progressColorHex) {
        this.progressColorHex = progressColorHex;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public boolean isGridWithTwoColumns() {
        return isGridWithTwoColumns;
    }

    public void setGridWithTwoColumns(boolean gridWithTwoColumns) {
        isGridWithTwoColumns = gridWithTwoColumns;
    }

    public String getImageSpecificationType() {
        return ImageSpecificationType;
    }

    public void setImageSpecificationType(String imageSpecificationType) {
        ImageSpecificationType = imageSpecificationType;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public boolean isEnableCollapseOrExpand() {
        return enableCollapseOrExpand;
    }

    public void setEnableCollapseOrExpand(boolean enableCollapseOrExpand) {
        this.enableCollapseOrExpand = enableCollapseOrExpand;
    }

    public String getTimer_hr() {
        return timer_hr;
    }

    public void setTimer_hr(String timer_hr) {
        this.timer_hr = timer_hr;
    }

    public String getTimer_min() {
        return timer_min;
    }

    public void setTimer_min(String timer_min) {
        this.timer_min = timer_min;
    }

    public String getTimer_sec() {
        return timer_sec;
    }

    public void setTimer_sec(String timer_sec) {
        this.timer_sec = timer_sec;
    }

    public String getTimerColorHex() {
        return timerColorHex;
    }

    public void setTimerColorHex(String timerColorHex) {
        this.timerColorHex = timerColorHex;
    }

    public int getTimerColor() {
        return timerColor;
    }

    public void setTimerColor(int timerColor) {
        this.timerColor = timerColor;
    }
    public String getTimerFormatOptions() {
        return timerFormatOptions;
    }

    public void setTimerFormatOptions(String timerFormatOptions) {
        this.timerFormatOptions = timerFormatOptions;
    }

    public boolean isTimerAutoStart() {
        return timerAutoStart;
    }

    public void setTimerAutoStart(boolean timerAutoStart) {
        this.timerAutoStart = timerAutoStart;
    }

    public List<String> getChartColors() {
        return chartColors;
    }

    public void setChartColors(List<String> chartColors) {
        this.chartColors = chartColors;
    }

    public boolean isDataViewer_searchEnabled() {
        return DataViewer_searchEnabled;
    }

    public void setDataViewer_searchEnabled(boolean dataViewer_searchEnabled) {
        DataViewer_searchEnabled = dataViewer_searchEnabled;
    }

    public boolean isDataViewer_HeaderSearchEnabled() {
        return DataViewer_HeaderSearchEnabled;
    }

    public void setDataViewer_HeaderSearchEnabled(boolean dataViewer_HeaderSearchEnabled) {
        DataViewer_HeaderSearchEnabled = dataViewer_HeaderSearchEnabled;
    }

    public boolean isDataViewer_SubHeaderSearchEnabled() {
        return DataViewer_SubHeaderSearchEnabled;
    }

    public void setDataViewer_SubHeaderSearchEnabled(boolean dataViewer_SubHeaderSearchEnabled) {
        DataViewer_SubHeaderSearchEnabled = dataViewer_SubHeaderSearchEnabled;
    }

    public boolean isDataViewer_DescriptionSearchEnabled() {
        return DataViewer_DescriptionSearchEnabled;
    }

    public void setDataViewer_DescriptionSearchEnabled(boolean dataViewer_DescriptionSearchEnabled) {
        DataViewer_DescriptionSearchEnabled = dataViewer_DescriptionSearchEnabled;
    }

    public boolean isDataViewer_CornerSearchEnabled() {
        return DataViewer_CornerSearchEnabled;
    }

    public void setDataViewer_CornerSearchEnabled(boolean dataViewer_CornerSearchEnabled) {
        DataViewer_CornerSearchEnabled = dataViewer_CornerSearchEnabled;
    }

    public boolean isDataTable_EnableSearch() {
        return dataTable_EnableSearch;
    }

    public void setDataTable_EnableSearch(boolean dataTable_EnableSearch) {
        this.dataTable_EnableSearch = dataTable_EnableSearch;
    }

    public List<String> getSearchItemIds() {
        return searchItemIds;
    }

    public void setSearchItemIds(List<String> searchItemIds) {
        this.searchItemIds = searchItemIds;
    }

    public boolean isSearchEnable() {
        return searchEnable;
    }

    public void setSearchEnable(boolean searchEnable) {
        this.searchEnable = searchEnable;
    }

    public boolean isZoomImageEnable() {
        return zoomImageEnable;
    }

    public void setZoomImageEnable(boolean zoomImageEnable) {
        this.zoomImageEnable = zoomImageEnable;
    }
    public String getFilelink() {
        return filelink;
    }

    public void setFilelink(String filelink) {
        this.filelink = filelink;
    }

    public boolean isHide_filelink() {
        return hide_filelink;
    }

    public void setHide_filelink(boolean hide_filelink) {
        this.hide_filelink = hide_filelink;
    }

    public boolean isDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(boolean downloadFile) {
        this.downloadFile = downloadFile;
    }

    public String getCaptureOrientation() {
        return captureOrientation;
    }

    public void setCaptureOrientation(String captureOrientation) {
        this.captureOrientation = captureOrientation;
    }

    public String getTextHexColorBG() {
        return textHexColorBG;
    }

    public void setTextHexColorBG(String textHexColorBG) {
        this.textHexColorBG = textHexColorBG;
    }

    public int getTextColorBG() {
        return textColorBG;
    }

    public void setTextColorBG(int textColorBG) {
        this.textColorBG = textColorBG;
    }

    public boolean isEnableChangeButtonColor() {
        return enableChangeButtonColor;
    }

    public void setEnableChangeButtonColor(boolean enableChangeButtonColor) {
        this.enableChangeButtonColor = enableChangeButtonColor;
    }

    public boolean isEnableChangeButtonColorBG() {
        return enableChangeButtonColorBG;
    }

    public void setEnableChangeButtonColorBG(boolean enableChangeButtonColorBG) {
        this.enableChangeButtonColorBG = enableChangeButtonColorBG;
    }

    public String text;
    private String imagePath;
    private String imageGPS;
    private String selectedDate;
    private Item selectedItem;
    private List<Item> selectedItems;
    private String selectedRating;
    private String voiceRecordPath;
    private String videoRecordPath;
    private String uploadedFilePath;
    private String signaturePath;
    private String selectedTime;
    private String fileLink;
    private String gpsValue;
    private String gpsLat;
    private String gpsLong;
    private List<Item> selectedUser;
    private List<Item> selectedPost;
    private String hrText;
    private String minText;
    private String secText;

    private boolean isItemSelected;

    List<LatLng> latLngListItems;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public List<Item> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Item> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public String getSelectedRating() {
        return selectedRating;
    }

    public void setSelectedRating(String selectedRating) {
        this.selectedRating = selectedRating;
    }

    public String getVoiceRecordPath() {
        return voiceRecordPath;
    }

    public void setVoiceRecordPath(String voiceRecordPath) {
        this.voiceRecordPath = voiceRecordPath;
    }

    public String getVideoRecordPath() {
        return videoRecordPath;
    }

    public void setVideoRecordPath(String videoRecordPath) {
        this.videoRecordPath = videoRecordPath;
    }

    public String getUploadedFilePath() {
        return uploadedFilePath;
    }

    public void setUploadedFilePath(String uploadedFilePath) {
        this.uploadedFilePath = uploadedFilePath;
    }

    public String getSignaturePath() {
        return signaturePath;
    }

    public void setSignaturePath(String signaturePath) {
        this.signaturePath = signaturePath;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getGpsValue() {
        return gpsValue;
    }

    public void setGpsValue(String gpsValue) {
        this.gpsValue = gpsValue;
    }

    public String getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(String gpsLat) {
        this.gpsLat = gpsLat;
    }

    public String getGpsLong() {
        return gpsLong;
    }

    public void setGpsLong(String gpsLong) {
        this.gpsLong = gpsLong;
    }

    public List<Item> getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(List<Item> selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<Item> getSelectedPost() {
        return selectedPost;
    }

    public void setSelectedPost(List<Item> selectedPost) {
        this.selectedPost = selectedPost;
    }

    public String getHrText() {
        return hrText;
    }

    public void setHrText(String hrText) {
        this.hrText = hrText;
    }

    public String getMinText() {
        return minText;
    }

    public void setMinText(String minText) {
        this.minText = minText;
    }

    public String getSecText() {
        return secText;
    }

    public void setSecText(String secText) {
        this.secText = secText;
    }

    public boolean isSectionControl() {
        return sectionControl;
    }

    public void setSectionControl(boolean sectionControl) {
        this.sectionControl = sectionControl;
    }


    public String getImageGPS() {
        return imageGPS;
    }

    public void setImageGPS(String imageGPS) {
        this.imageGPS = imageGPS;
    }

    public boolean isDownloadExcel() {
        return isDownloadExcel;
    }

    public void setDownloadExcel(boolean downloadExcel) {
        isDownloadExcel = downloadExcel;

    }

    public String getDataTable_colWidthType() {
        return dataTable_colWidthType;
    }

    public void setDataTable_colWidthType(String dataTable_colWidthType) {
        this.dataTable_colWidthType = dataTable_colWidthType;
    }

    public String getDataTable_colWidthSize() {
        return dataTable_colWidthSize;
    }

    public void setDataTable_colWidthSize(String dataTable_colWidthSize) {
        this.dataTable_colWidthSize = dataTable_colWidthSize;
    }

    public String getDataTable_colHeightType() {
        return dataTable_colHeightType;
    }

    public void setDataTable_colHeightType(String dataTable_colHeightType) {
        this.dataTable_colHeightType = dataTable_colHeightType;
    }

    public String getDataTable_colHeightSize() {
        return dataTable_colHeightSize;
    }

    public void setDataTable_colHeightSize(String dataTable_colHeightSize) {
        this.dataTable_colHeightSize = dataTable_colHeightSize;
    }

    public String getDataTable_colTextSize() {
        return dataTable_colTextSize;
    }

    public void setDataTable_colTextSize(String dataTable_colTextSize) {
        this.dataTable_colTextSize = dataTable_colTextSize;
    }

    public String getDataTable_colTextStyle() {
        return dataTable_colTextStyle;
    }

    public void setDataTable_colTextStyle(String dataTable_colTextStyle) {
        this.dataTable_colTextStyle = dataTable_colTextStyle;
    }

    public String getDataTable_colTextColor() {
        return dataTable_colTextColor;
    }

    public void setDataTable_colTextColor(String dataTable_colTextColor) {
        this.dataTable_colTextColor = dataTable_colTextColor;
    }

    public String getDataTable_colAlignment() {
        return dataTable_colAlignment;
    }

    public void setDataTable_colAlignment(String dataTable_colAlignment) {
        this.dataTable_colAlignment = dataTable_colAlignment;
    }

    public String getDataTable_colColor() {
        return dataTable_colColor;
    }

    public void setDataTable_colColor(String dataTable_colColor) {
        this.dataTable_colColor = dataTable_colColor;
    }

    public String getDataTable_colBorder() {
        return dataTable_colBorder;
    }

    public void setDataTable_colBorder(String dataTable_colBorder) {
        this.dataTable_colBorder = dataTable_colBorder;
    }

    public String getDataTable_rowHeigthType() {
        return dataTable_rowHeigthType;
    }

    public void setDataTable_rowHeigthType(String dataTable_rowHeigthType) {
        this.dataTable_rowHeigthType = dataTable_rowHeigthType;
    }

    public String getDataTable_rowHeightSize() {
        return dataTable_rowHeightSize;
    }

    public void setDataTable_rowHeightSize(String dataTable_rowHeightSize) {
        this.dataTable_rowHeightSize = dataTable_rowHeightSize;
    }

    public String getDataTable_rowTextSize() {
        return dataTable_rowTextSize;
    }

    public void setDataTable_rowTextSize(String dataTable_rowTextSize) {
        this.dataTable_rowTextSize = dataTable_rowTextSize;
    }

    public String getDataTable_rowTextStyle() {
        return dataTable_rowTextStyle;
    }

    public void setDataTable_rowTextStyle(String dataTable_rowTextStyle) {
        this.dataTable_rowTextStyle = dataTable_rowTextStyle;
    }

    public String getDataTable_rowTextColor() {
        return dataTable_rowTextColor;
    }

    public void setDataTable_rowTextColor(String dataTable_rowTextColor) {
        this.dataTable_rowTextColor = dataTable_rowTextColor;
    }

    public String getDataTable_rowAlignment() {
        return dataTable_rowAlignment;
    }

    public void setDataTable_rowAlignment(String dataTable_rowAlignment) {
        this.dataTable_rowAlignment = dataTable_rowAlignment;
    }

    public String getDataTable_rowColorType() {
        return dataTable_rowColorType;
    }

    public void setDataTable_rowColorType(String dataTable_rowColorType) {
        this.dataTable_rowColorType = dataTable_rowColorType;
    }

    public String getDataTable_rowColor1() {
        return dataTable_rowColor1;
    }

    public void setDataTable_rowColor1(String dataTable_rowColor1) {
        this.dataTable_rowColor1 = dataTable_rowColor1;
    }

    public String getDataTable_rowColor2() {
        return dataTable_rowColor2;
    }

    public void setDataTable_rowColor2(String dataTable_rowColor2) {
        this.dataTable_rowColor2 = dataTable_rowColor2;
    }

    public String getDataTable_BorderType() {
        return dataTable_BorderType;
    }

    public void setDataTable_BorderType(String dataTable_BorderType) {
        this.dataTable_BorderType = dataTable_BorderType;
    }

    public boolean isDataTable_isPaging() {
        return dataTable_isPaging;
    }

    public void setDataTable_isPaging(boolean dataTable_isPaging) {
        this.dataTable_isPaging = dataTable_isPaging;
    }

    public boolean isDownloadPDF() {
        return isDownloadPDF;
    }

    public void setDownloadPDF(boolean downloadPDF) {
        isDownloadPDF = downloadPDF;
    }

    public String getDataTable_BorderColor() {
        return dataTable_BorderColor;
    }

    public void setDataTable_BorderColor(String dataTable_BorderColor) {
        this.dataTable_BorderColor = dataTable_BorderColor;
    }

    public String getDataTable_BorderThickness() {
        return dataTable_BorderThickness;
    }

    public void setDataTable_BorderThickness(String dataTable_BorderThickness) {
        this.dataTable_BorderThickness = dataTable_BorderThickness;
    }

    public String getGridControl_ColHeightType() {
        return gridControl_ColHeightType;
    }

    public void setGridControl_ColHeightType(String gridControl_ColHeightType) {
        this.gridControl_ColHeightType = gridControl_ColHeightType;
    }

    public String getGridControl_ColHeightSize() {
        return gridControl_ColHeightSize;
    }

    public void setGridControl_ColHeightSize(String gridControl_ColHeightSize) {
        this.gridControl_ColHeightSize = gridControl_ColHeightSize;
    }

    public String getGridControl_ColTextSize() {
        return gridControl_ColTextSize;
    }

    public void setGridControl_ColTextSize(String gridControl_ColTextSize) {
        this.gridControl_ColTextSize = gridControl_ColTextSize;
    }

    public String getGridControl_ColTextStyle() {
        return gridControl_ColTextStyle;
    }

    public void setGridControl_ColTextStyle(String gridControl_ColTextStyle) {
        this.gridControl_ColTextStyle = gridControl_ColTextStyle;
    }

    public String getGridControl_ColTextColor() {
        return gridControl_ColTextColor;
    }

    public void setGridControl_ColTextColor(String gridControl_ColTextColor) {
        this.gridControl_ColTextColor = gridControl_ColTextColor;
    }

    public String getGridControl_ColTextAlignment() {
        return gridControl_ColTextAlignment;
    }

    public void setGridControl_ColTextAlignment(String gridControl_ColTextAlignment) {
        this.gridControl_ColTextAlignment = gridControl_ColTextAlignment;
    }

    public String getGridControl_ColColor() {
        return gridControl_ColColor;
    }

    public void setGridControl_ColColor(String gridControl_ColColor) {
        this.gridControl_ColColor = gridControl_ColColor;
    }

    public String getGridControl_ColBorder() {
        return gridControl_ColBorder;
    }

    public void setGridControl_ColBorder(String gridControl_ColBorder) {
        this.gridControl_ColBorder = gridControl_ColBorder;
    }

    public String getGridControl_BorderType() {
        return gridControl_BorderType;
    }

    public void setGridControl_BorderType(String gridControl_BorderType) {
        this.gridControl_BorderType = gridControl_BorderType;
    }

    public String getGridControl_BorderColor() {
        return gridControl_BorderColor;
    }

    public void setGridControl_BorderColor(String gridControl_BorderColor) {
        this.gridControl_BorderColor = gridControl_BorderColor;
    }

    public String getGridControl_BorderThickness() {
        return gridControl_BorderThickness;
    }

    public void setGridControl_BorderThickness(String gridControl_BorderThickness) {
        this.gridControl_BorderThickness = gridControl_BorderThickness;
    }

    public boolean isGridControl_HideAddButton() {
        return gridControl_HideAddButton;
    }

    public void setGridControl_HideAddButton(boolean gridControl_HideAddButton) {
        this.gridControl_HideAddButton = gridControl_HideAddButton;
    }
    public String getGridControl_rowHeigthType() {
        return gridControl_rowHeigthType;
    }

    public void setGridControl_rowHeigthType(String gridControl_rowHeigthType) {
        this.gridControl_rowHeigthType = gridControl_rowHeigthType;
    }

    public String getGridControl_rowHeightSize() {
        return gridControl_rowHeightSize;
    }

    public void setGridControl_rowHeightSize(String gridControl_rowHeightSize) {
        this.gridControl_rowHeightSize = gridControl_rowHeightSize;
    }

    public String getGridControl_rowColorType() {
        return gridControl_rowColorType;
    }

    public void setGridControl_rowColorType(String gridControl_rowColorType) {
        this.gridControl_rowColorType = gridControl_rowColorType;
    }

    public String getGridControl_rowColor1() {
        return gridControl_rowColor1;
    }

    public void setGridControl_rowColor1(String gridControl_rowColor1) {
        this.gridControl_rowColor1 = gridControl_rowColor1;
    }

    public String getGridControl_rowColor2() {
        return gridControl_rowColor2;
    }

    public void setGridControl_rowColor2(String gridControl_rowColor2) {
        this.gridControl_rowColor2 = gridControl_rowColor2;
    }
    public boolean isDataTable_ParticularRowsColoring() {
        return dataTable_ParticularRowsColoring;
    }

    public void setDataTable_ParticularRowsColoring(boolean dataTable_ParticularRowsColoring) {
        this.dataTable_ParticularRowsColoring = dataTable_ParticularRowsColoring;
    }

    public String getDataTable_ParticularRowsColoringIds() {
        return dataTable_ParticularRowsColoringIds;
    }

    public void setDataTable_ParticularRowsColoringIds(String dataTable_ParticularRowsColoringIds) {
        this.dataTable_ParticularRowsColoringIds = dataTable_ParticularRowsColoringIds;
    }

    public String getDataTable_ParticularRowColor() {
        return dataTable_ParticularRowColor;
    }

    public void setDataTable_ParticularRowColor(String dataTable_ParticularRowColor) {
        this.dataTable_ParticularRowColor = dataTable_ParticularRowColor;
    }
    public List<String> getGridColumnsWidths() {
        return gridColumnsWidths;
    }

    public void setGridColumnsWidths(List<String> gridColumnsWidths) {
        this.gridColumnsWidths = gridColumnsWidths;
    }

    public boolean isGridControl_LazyLoading() {
        return gridControl_LazyLoading;
    }

    public void setGridControl_LazyLoading(boolean gridControl_LazyLoading) {
        this.gridControl_LazyLoading = gridControl_LazyLoading;
    }

    public String getGridControl_threshold() {
        return gridControl_threshold;
    }

    public void setGridControl_threshold(String gridControl_threshold) {
        this.gridControl_threshold = gridControl_threshold;
    }

    public boolean isMakeItAsPopup() {
        return makeItAsPopup;
    }

    public void setMakeItAsPopup(boolean makeItAsPopup) {
        this.makeItAsPopup = makeItAsPopup;
    }

    public String getDataViewer_Item_HexColor() {
        return DataViewer_Item_HexColor;
    }

    public void setDataViewer_Item_HexColor(String dataViewer_Item_HexColor) {
        DataViewer_Item_HexColor = dataViewer_Item_HexColor;
    }

    public String getDataViewer_Item_BGHexColor() {
        return DataViewer_Item_BGHexColor;
    }

    public void setDataViewer_Item_BGHexColor(String dataViewer_Item_BGHexColor) {
        DataViewer_Item_BGHexColor = dataViewer_Item_BGHexColor;
    }

    public boolean isOnMapMarkerClickEventExists() {
        return onMapMarkerClickEventExists;
    }

    public void setOnMapMarkerClickEventExists(boolean onMapMarkerClickEventExists) {
        this.onMapMarkerClickEventExists = onMapMarkerClickEventExists;
    }

    public Control_EventObject getOnMapMarkerClickEventObject() {
        return onMapMarkerClickEventObject;
    }

    public void setOnMapMarkerClickEventObject(Control_EventObject onMapMarkerClickEventObject) {
        this.onMapMarkerClickEventObject = onMapMarkerClickEventObject;
    }

    public String getCustomImageFit() {
        return customImageFit;
    }

    public void setCustomImageFit(String customImageFit) {
        this.customImageFit = customImageFit;
    }

    public String getCustomImageRadius() {
        return customImageRadius;
    }

    public void setCustomImageRadius(String customImageRadius) {
        this.customImageRadius = customImageRadius;
    }

    public String getCustomImageURL() {
        return customImageURL;
    }

    public void setCustomImageURL(String customImageURL) {
        this.customImageURL = customImageURL;
    }

    public boolean isItemSelected() {
        return isItemSelected;
    }

    public void setItemSelected(boolean itemSelected) {
        isItemSelected = itemSelected;
    }

    public List<LatLng> getLatLngListItems() {
        return latLngListItems;
    }

    public void setLatLngListItems(List<LatLng> latLngListItems) {
        this.latLngListItems = latLngListItems;
    }
}
