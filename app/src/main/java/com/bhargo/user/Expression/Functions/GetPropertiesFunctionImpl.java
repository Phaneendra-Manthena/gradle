package com.bhargo.user.Expression.Functions;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CAMERA;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DYNAMIC_LABEL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SUBFORM;

import com.bhargo.user.Expression.Interfaces.GetPropertiesFunction;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.PropertiesNames;

import java.util.List;

public class GetPropertiesFunctionImpl implements GetPropertiesFunction {

    public static final String TAG = "GetPropertiesFunctionImpl";

    @Override
    public String getPropertyValue(String controlNameGP, String propertyName) {

        String strGetPropertyValue = "";
        if (AppConstants.CurrentAppObject != null && AppConstants.CurrentAppObject.getControls_list() != null) {
            List<ControlObject> list_Control = AppConstants.CurrentAppObject.getControls_list();
            if (list_Control != null && list_Control.size() > 0) {
                for (int i = 0; i < list_Control.size(); i++) {
                    ControlObject controlObject = list_Control.get(i);
                    if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SUBFORM)
                            || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)
                            || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SECTION)) {
                        List<ControlObject> advancedControlObjectList = controlObject.getSubFormControlList();
                        if (advancedControlObjectList != null && advancedControlObjectList.size() > 0) {
                            for (int j = 0; j < advancedControlObjectList.size(); j++) {
                                ControlObject advancedControlObject = advancedControlObjectList.get(i);
                                if (advancedControlObject.getControlName().equalsIgnoreCase(controlNameGP)) {
                                    strGetPropertyValue = propertyNameTypes(advancedControlObject, propertyName);
                                }
                            }
                        }
                    } else if (controlObject.getControlName().equalsIgnoreCase(controlNameGP)) {
                        strGetPropertyValue = propertyNameTypes(controlObject, propertyName);
                    }
                }
            }
        }
//        Log.d(TAG, "getterPropertyValuesTest: "+strGetPropertyValue);
        return strGetPropertyValue;
    }

    public String propertyNameTypes(ControlObject controlObject, String propertyName) {
        String strGetPropertyValue = null;
        switch (propertyName) {
            case PropertiesNames.DISPLAY_NAME:
                strGetPropertyValue = controlObject.getDisplayName();
                break;
            case PropertiesNames.HINT:
                strGetPropertyValue = controlObject.getHint();
                break;
            case PropertiesNames.HTML_EDITOR:
                strGetPropertyValue = String.valueOf(controlObject.isHtmlEditorEnabled());
                break;
            case PropertiesNames.HTML_VIEWER:
                strGetPropertyValue = String.valueOf(controlObject.isHtmlViewerEnabled());
                break;
            case PropertiesNames.MIN_VALUE:
                strGetPropertyValue = controlObject.getMinValue();
                break;
            case PropertiesNames.MAX_VALUE:
                strGetPropertyValue = controlObject.getMaxValue();
                break;
            case PropertiesNames.ORIENTATION:
                if (controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_CAMERA)) {
                    strGetPropertyValue = controlObject.getCaptureOrientation();
                }else if (controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_SUBFORM)) {
                    strGetPropertyValue = controlObject.getDisplayOrientation();
                }
                break;
            case PropertiesNames.HIDE_DISPLAY_NAME:
                strGetPropertyValue = String.valueOf(controlObject.isHideDisplayName());
                break;
            case PropertiesNames.BAR_CODE:
                if (controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_DYNAMIC_LABEL)) {
                    strGetPropertyValue = String.valueOf(controlObject.isEnableDisplayAsBarCode());
                } else {
                    strGetPropertyValue = String.valueOf(controlObject.isReadFromBarcode());
                }
            case PropertiesNames.QR_CODE:
                if (controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_DYNAMIC_LABEL)) {
                    strGetPropertyValue = String.valueOf(controlObject.isEnableDisplayAsQRCode());
                } else {
                    strGetPropertyValue = String.valueOf(controlObject.isReadFromQRCode());
                }
                break;
            case PropertiesNames.GEO_LOCATION:
                strGetPropertyValue = String.valueOf(controlObject.isGoogleLocationSearch());
                break;
            case PropertiesNames.CURRENT_LOCATION:
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_MAP)) {
                    strGetPropertyValue = String.valueOf(controlObject.isShowCurrentLocation());
                } else {
                    strGetPropertyValue = String.valueOf(controlObject.isCurrentLocation());
                }
                break;
            case PropertiesNames.VOICE_INPUT:
                strGetPropertyValue = String.valueOf(controlObject.isVoiceText());
                break;
            case PropertiesNames.INVISIBLE:
                strGetPropertyValue = String.valueOf(controlObject.isInvisible());
                break;
            case PropertiesNames.MASK_CHAR:
                strGetPropertyValue = String.valueOf(controlObject.isEnableMaskCharacters());
                break;
            case PropertiesNames.MASK_CHAR_COUNT:
                strGetPropertyValue = controlObject.getNoOfCharactersToMask();
                break;
            case PropertiesNames.MASK_CHAR_TYPE:
                strGetPropertyValue = controlObject.getMaskCharacterType();
                break;
            case PropertiesNames.READ_ONLY:
                strGetPropertyValue = String.valueOf(controlObject.isReadOnly());
                break;
            case PropertiesNames.BACK_GROUND:
                strGetPropertyValue = String.valueOf(controlObject.isLayoutBackGroundEnable());
                break;
            case PropertiesNames.BACK_GROUND_COLOR:
                strGetPropertyValue = controlObject.getLayoutBackGroundColor();
                break;
            case PropertiesNames.IMAGE_WITH_GPS:
                strGetPropertyValue = String.valueOf(controlObject.isEnableImageWithGps());
                break;
            case PropertiesNames.ZOOM_CTRL:
                strGetPropertyValue = String.valueOf(controlObject.isZoomImageEnable());
                break;
            case PropertiesNames.CAPTURE:
                strGetPropertyValue = String.valueOf(controlObject.isEnableCapture());
                break;
            case PropertiesNames.CAMERA:
                strGetPropertyValue = String.valueOf(controlObject.isCaptureFromCamera());
                break;
            case PropertiesNames.FILE_BROWSING:
                strGetPropertyValue = String.valueOf(controlObject.isCaptureFromFile());
                break;
            case PropertiesNames.ENABLE_SCAN:
                strGetPropertyValue = String.valueOf(controlObject.isEnableScan());
                break;
            case PropertiesNames.GET_YEAR_FROM_SELECTION:
                strGetPropertyValue = String.valueOf(controlObject.isGetYearFromSelection());
                break;
            case PropertiesNames.GET_MONTH_FROM_SELECTION:
                strGetPropertyValue = String.valueOf(controlObject.isGetMonthFromSelection());
                break;
            case PropertiesNames.GET_DAY_FROM_SELECTION:
                strGetPropertyValue = String.valueOf(controlObject.isGetDayFromSelection());
                break;
            case PropertiesNames.GET_DATE_FROM_SELECTION:
                strGetPropertyValue = String.valueOf(controlObject.isGetDateFromSelection());
                break;
            case PropertiesNames.SORT_BY_ALPHABETS:
                strGetPropertyValue = String.valueOf(controlObject.isEnableSortByAlphabeticalOrder());
                break;
            case PropertiesNames.ALLOW_OTHER_CHOICES:
                strGetPropertyValue = String.valueOf(controlObject.isEnableAllowOtherChoice());
                break;
            case PropertiesNames.HORIZONTAL_ALIGNMENT:
                strGetPropertyValue = String.valueOf(controlObject.isEnableHorizontalAlignment());
                break;
            case PropertiesNames.FONT_SIZE:
                strGetPropertyValue = controlObject.getTextSize();
                break;
            case PropertiesNames.FONT_STYLE:
                strGetPropertyValue = controlObject.getTextStyle();
                break;
            case PropertiesNames.FONT_COLOR:
                strGetPropertyValue = controlObject.getTextHexColor();
                break;
            case PropertiesNames.DISABLE_COUNT:
                strGetPropertyValue = String.valueOf(controlObject.isDisableRatingCount());
                break;
            case PropertiesNames.RATING_TYPE_VALUE:
                strGetPropertyValue = controlObject.getRatingType();
                break;
            case PropertiesNames.RATING_ITEM_NAMES:
                strGetPropertyValue = String.valueOf(controlObject.isCustomItemNames());
                break;
            case PropertiesNames.AUDIO_FORMAT:
                strGetPropertyValue = String.valueOf(controlObject.isEnableAudioFormat());
                break;
            case PropertiesNames.AUDIO_FORMAT_VALUE:
                strGetPropertyValue = controlObject.getAudioFormat();
                break;
            case PropertiesNames.UPLOAD_AUDIO_FILE:
                strGetPropertyValue = String.valueOf(controlObject.isEnableUploadAudioFile());
                break;
            case PropertiesNames.VIDEO_FORMAT:
                strGetPropertyValue = String.valueOf(controlObject.isEnableVideoFormat());
                break;
            case PropertiesNames.VIDEO_FORMAT_VALUE:
                strGetPropertyValue = controlObject.getVideoFormat();
                break;
            case PropertiesNames.UPLOAD_VIDEO_FILE:
                strGetPropertyValue = String.valueOf(controlObject.isEnableUploadVideoFile());
                break;
            case PropertiesNames.STAY_AWAKE:
                strGetPropertyValue = String.valueOf(controlObject.isEnableStayAwake());
                break;
            case PropertiesNames.PLAY_BACKGROUND:
                strGetPropertyValue = String.valueOf(controlObject.isEnablePlayBackground());
                break;
            case PropertiesNames.UPLOAD_IMAGE:
                strGetPropertyValue = String.valueOf(controlObject.isEnableUploadSignature());
                break;
            case PropertiesNames.SIGNATURE_ON_SCREEN:
                strGetPropertyValue = String.valueOf(controlObject.isEnableSignatureOnScreen());
                break;
            case PropertiesNames.DISABLE_CLICK:
                strGetPropertyValue = String.valueOf(controlObject.isDisableClick());
                break;
            case PropertiesNames.HIDE_URL:
                strGetPropertyValue = String.valueOf(controlObject.isHideURL());
                break;
            case PropertiesNames.DECIMAL_CHARACTERS:
                strGetPropertyValue = String.valueOf(controlObject.isEnableDecimalCharacters());
                break;
            case PropertiesNames.DECIMAL_CHARACTERS_VALUE:
                strGetPropertyValue = String.valueOf(controlObject.getCharactersAfterDecimal());
                break;
            case PropertiesNames.ENCRYPT:
                strGetPropertyValue = String.valueOf(controlObject.isEnableEncryption());
                break;
            case PropertiesNames.SHOW_HIDE_PASSWORD:
                strGetPropertyValue = String.valueOf(controlObject.isEnableShowOrHideOption());
                break;
            case PropertiesNames.HIDE_FILE_LINK:
                strGetPropertyValue = String.valueOf(controlObject.isHide_filelink());
                break;
            case PropertiesNames.DOWNLOAD_FILE:
                strGetPropertyValue = String.valueOf(controlObject.isDownloadFile());
                break;
            case PropertiesNames.SELECT_CURRENCY:
                strGetPropertyValue = String.valueOf(controlObject.isEnableCurrencySelection());
                break;
            case PropertiesNames.SELECT_CURRENCY_VALUE:
                strGetPropertyValue = String.valueOf(controlObject.getCurrency());
                break;
            case PropertiesNames.STRIKE_TEXT:
                strGetPropertyValue = String.valueOf(controlObject.getStrikeText());
                break;
            case PropertiesNames.ENABLE_UNICODE:
                strGetPropertyValue = String.valueOf(controlObject.isEnableUnicode());
                break;
            case PropertiesNames.UNICODE_VALUE:
                strGetPropertyValue = String.valueOf(controlObject.getUnicodeFormatId());
                break;

            case PropertiesNames.BUTTON_WITH_ICON:
                strGetPropertyValue = String.valueOf(controlObject.getIconPath());
                break;
            case PropertiesNames.BUTTON_COLOR:
                strGetPropertyValue = String.valueOf(controlObject.getButtonColor());
                break;
            case PropertiesNames.ALIGNMENT:
                strGetPropertyValue = String.valueOf(controlObject.getIconAlignment());
                break;
            case PropertiesNames.BUTTON_TYPE:
                strGetPropertyValue = String.valueOf(controlObject.getTypeOfButton());
                break;
            case PropertiesNames.BUTTON_SHAPE:
                strGetPropertyValue = String.valueOf(controlObject.getShape());
                break;
            case PropertiesNames.EXTENSIONS_NAMES_LIST:
                strGetPropertyValue = String.valueOf(controlObject.getExtensionsListNames());
                break;
            case PropertiesNames.ENABLE_EXTENSIONS:
                strGetPropertyValue = String.valueOf(controlObject.getEnabledExtensions());
                break;
            case PropertiesNames.URL_PLACEHOLDER:
                strGetPropertyValue = String.valueOf(controlObject.getUrlPlaceholderText());
                break;
            case PropertiesNames.SAVE_LATLONG_SEPARATE_COL:
                strGetPropertyValue = String.valueOf(controlObject.isEnableSavingLatitudeAndLongitudeInSeparateColumns());
                break;
            case PropertiesNames.SHOW_MAP:
                strGetPropertyValue = String.valueOf(controlObject.isShowMap());
                break;
            case PropertiesNames.LOCATION_FORMAT_TYPE:
                strGetPropertyValue = String.valueOf(controlObject.getLocationFormat());
                break;
            case PropertiesNames.LOCATION_FORMAT:
                strGetPropertyValue = String.valueOf(controlObject.isEnableLocationFormatting());
                break;
            case PropertiesNames.INTERVAL_TIME:
                strGetPropertyValue = String.valueOf(controlObject.getTimeInMinutes());
                break;
            case PropertiesNames.INTERVAL_DISTANCE:
                strGetPropertyValue = String.valueOf(controlObject.getDistanceInMeters());
                break;
            case PropertiesNames.INTERVAL_TYPE:
                strGetPropertyValue = String.valueOf(controlObject.getTypeOfInterval());
                break;
            case PropertiesNames.GPS_TYPE:
                strGetPropertyValue = String.valueOf(controlObject.getGpsType());
                break;
            case PropertiesNames.LOCATION_ACCURACY:
                strGetPropertyValue = String.valueOf(controlObject.getAccuracy());
                break;
            case PropertiesNames.LOCATION_MODE:
                strGetPropertyValue = String.valueOf(controlObject.getLocationMode());
                break;
            case PropertiesNames.TIME_OPTION_FORMAT_TYPE:
                if(controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_TIME)) {
                    strGetPropertyValue = String.valueOf(controlObject.getTimeFormatOptions());
                }else if(controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_COUNT_DOWN_TIMER)) {
                    strGetPropertyValue = String.valueOf(controlObject.getTimerFormatOptions());
                }else if(controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_COUNT_UP_TIMER)) {
                    strGetPropertyValue = String.valueOf(controlObject.getTimerFormatOptions());
                }
                break;
            case PropertiesNames.TIME_OPTION_FORMAT:
                strGetPropertyValue = String.valueOf(controlObject.isTimeFormatOptions());
                break;
            case PropertiesNames.TIME_FORMAT_TYPE:
                strGetPropertyValue = controlObject.getTimeFormat();
                break;
            case PropertiesNames.TIME_FORMAT:
                strGetPropertyValue = String.valueOf(controlObject.isTimeFormat());
                break;
            case PropertiesNames.CURRENT_TIME:
                strGetPropertyValue = String.valueOf(controlObject.isCurrentTime());
                break;
            case PropertiesNames.TIMER_COLOR:
                strGetPropertyValue = String.valueOf(controlObject.getTimerColor());
                break;
            case PropertiesNames.AUTOSTART:
                strGetPropertyValue = String.valueOf(controlObject.isTimerAutoStart());
                break;
//            case PropertiesNames.TIMER_DISPLAY_FORMAT_TYPE:
//                strGetPropertyValue = controlObject.getTimerFormatOptions();
//                break;
            case PropertiesNames.TIMER_DISPLAY_FORMAT:

                break;
            case PropertiesNames.MAKE_IT_AS_SECTION:
                strGetPropertyValue = String.valueOf(controlObject.isMakeAsSection());
                break;
            case PropertiesNames.PARTIAL_TYPE:
                strGetPropertyValue = controlObject.getMaskCharacterDirection();
                break;
            case PropertiesNames.MULTIPLE_IMAGES:
                strGetPropertyValue = String.valueOf(controlObject.isEnableMultipleImages());
                break;
            case PropertiesNames.IMAGES_COUNT:

                break;
            case PropertiesNames.SINGLE_IMAGE_PATH:

                break;
            case PropertiesNames.IMAGES_DISPLAY_TYPE:
                strGetPropertyValue = controlObject.getImagesArrangementType();
                break;
            case PropertiesNames.IMAGES_LIST:

                break;
            case PropertiesNames.GRID_WITH_TWO_COLUMNS:
                strGetPropertyValue = String.valueOf(controlObject.isGridWithTwoColumns());
                break;
            case PropertiesNames.SEARCH_ITEMS:
                strGetPropertyValue = String.valueOf(controlObject.isSearchEnable());
                break;
            case PropertiesNames.NAME_OF_BUTTON_VALUE:

                break;
            case PropertiesNames.EXPAND_COLLAPSE:
                strGetPropertyValue = String.valueOf(controlObject.isEnableCollapseOrExpand());
                break;
            case PropertiesNames.USER_WITH_POST_NAME:
                strGetPropertyValue = String.valueOf(controlObject.isShowUsersWithPostName());
                break;
            case PropertiesNames.USER_TYPE:
                strGetPropertyValue = controlObject.getUserType();
                break;
            case PropertiesNames.USER_GROUP:
                strGetPropertyValue = String.valueOf(controlObject.getGroups());
                break;
            case PropertiesNames.SEARCH_KEY_AT:
                strGetPropertyValue = controlObject.getSearchKeyAt();
                break;
            case PropertiesNames.DEFAULT_VALUE:
                strGetPropertyValue = controlObject.getDefaultValue();
                break;
            case PropertiesNames.HEADER_WIDTH_TYPE:
                strGetPropertyValue = controlObject.getDataTable_colWidthType();
                break;
            case PropertiesNames.HEADER_HEIGHT_TYPE:
                strGetPropertyValue = controlObject.getDataTable_colHeightType();
                break;
            case PropertiesNames.HEADER_WIDTH_FIXED_SIZE:
                strGetPropertyValue = controlObject.getDataTable_colWidthSize();
                break;
            case PropertiesNames.HEADER_HEIGHT_FIXED_SIZE:
                strGetPropertyValue = controlObject.getDataTable_colHeightSize();
                break;
            case PropertiesNames.HIDE_COL_NAMES:
                strGetPropertyValue = String.valueOf(controlObject.isHideColumnNames());
                break;
            case PropertiesNames.COL_TEXT_SIZE:
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                    strGetPropertyValue = controlObject.getGridControl_ColTextSize();
                } else {
                    strGetPropertyValue = controlObject.getDataTable_colTextSize();
                }
                break;
            case PropertiesNames.COL_TEXT_STYLE:
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                    strGetPropertyValue = controlObject.getGridControl_ColTextStyle();
                } else {
                    strGetPropertyValue = controlObject.getDataTable_colTextStyle();
                }
                break;

            case PropertiesNames.COL_TEXT_COLOR:
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                    strGetPropertyValue = controlObject.getGridControl_ColTextColor();
                } else {
                    strGetPropertyValue = controlObject.getDataTable_colTextColor();
                }
                break;
            case PropertiesNames.COL_TEXT_ALIGNMENT:
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                    strGetPropertyValue = controlObject.getGridControl_ColTextAlignment();
                } else {
                    strGetPropertyValue = controlObject.getDataTable_colAlignment();
                }
                break;
            case PropertiesNames.COL_COLOR:
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                    strGetPropertyValue = controlObject.getGridControl_ColColor();
                } else {
                    strGetPropertyValue = controlObject.getDataTable_colColor();
                }
                break;
            case PropertiesNames.COL_BORDER:
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                    strGetPropertyValue = controlObject.getGridControl_ColBorder();
                } else {
                    strGetPropertyValue = controlObject.getDataTable_colBorder();
                }
                break;
            case PropertiesNames.PAGING:
                strGetPropertyValue = String.valueOf(controlObject.isDataTable_isPaging());
                break;
            case PropertiesNames.DOWNLOAD_AS_EXCEL:
                strGetPropertyValue = String.valueOf(controlObject.isDownloadExcel());
                break;
            case PropertiesNames.DOWNLOAD_AS_PDF:
                strGetPropertyValue = String.valueOf(controlObject.isDownloadPDF());
                break;
            case PropertiesNames.COLUMN_SETTINGS_LIST:
                strGetPropertyValue = String.valueOf(controlObject.getGridColumnSettings());
                break;
            case PropertiesNames.COLUMN_HEIGHT_TYPE:
                strGetPropertyValue = controlObject.getGridControl_ColHeightType();
                break;
            case PropertiesNames.COLUMN_HEIGHT_FIXED_SIZE:
                strGetPropertyValue = controlObject.getGridControl_ColHeightSize();
                break;
            case PropertiesNames.HIDE_HEADER_COL:
                strGetPropertyValue = String.valueOf(controlObject.isHideColumnNames());
                break;
            case PropertiesNames.ROW_HEIGHT_TYPE:
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                    strGetPropertyValue = controlObject.getGridControl_rowHeigthType();
                } else {
                    strGetPropertyValue = controlObject.getDataTable_rowHeigthType();
                }
                break;
            case PropertiesNames.ROW_HEIGHT_FIXED_SIZE:
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                    strGetPropertyValue = controlObject.getGridControl_rowHeightSize();
                } else {
                    strGetPropertyValue = controlObject.getDataTable_rowHeightSize();
                }
                break;
            case PropertiesNames.ROW_TEXT_SIZE:
                strGetPropertyValue = controlObject.getDataTable_rowTextSize();
                break;
            case PropertiesNames.ROW_TEXT_STYLE:
                strGetPropertyValue = controlObject.getDataTable_rowTextStyle();
                break;
            case PropertiesNames.ROW_TEXT_COLOR:
                strGetPropertyValue = controlObject.getDataTable_rowTextColor();
                break;
            case PropertiesNames.ROW_TEXT_ALIGNMENT:
                strGetPropertyValue = controlObject.getDataTable_rowAlignment();
                break;
            case PropertiesNames.ROW_COLOR_1:
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                    strGetPropertyValue = controlObject.getGridControl_rowColor1();
                } else {
                    strGetPropertyValue = controlObject.getDataTable_rowColor1();
                }
                break;
            case PropertiesNames.ROW_COLOR_2:
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                    strGetPropertyValue = controlObject.getGridControl_rowColor2();
                } else {
                    strGetPropertyValue = controlObject.getDataTable_rowColor2();
                }
                break;
            case PropertiesNames.ROW_PARTICULAR_COLORING:
                strGetPropertyValue = String.valueOf(controlObject.isDataTable_ParticularRowsColoring());
                break;
            case PropertiesNames.ROW_PARTICULAR_COLOR_IDS:
                strGetPropertyValue = controlObject.getDataTable_ParticularRowsColoringIds();
                break;
            case PropertiesNames.ROW_PARTICULAR_COLOR:
                strGetPropertyValue = controlObject.getDataTable_ParticularRowColor();
                break;
            case PropertiesNames.CONTROL_BORDER_TYPE:
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                    strGetPropertyValue = controlObject.getGridControl_BorderType();
                }else{
                    strGetPropertyValue = controlObject.getDataTable_BorderType();
                }
                break;
            case PropertiesNames.CONTROL_BORDER_COLOR:
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                strGetPropertyValue = controlObject.getGridControl_BorderColor();
                }else {
                    strGetPropertyValue = controlObject.getDataTable_BorderColor();
                }
                break;
            case PropertiesNames.CONTROL_BORDER_THICKNESS:
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                strGetPropertyValue = controlObject.getGridControl_BorderThickness();
                }else {
                    strGetPropertyValue = controlObject.getDataTable_BorderThickness();
                }
                break;
            case PropertiesNames.HIDE_DELETE_COLUMN:
                strGetPropertyValue = String.valueOf(controlObject.isGridControl_HideDeleteButton());
                break;
            case PropertiesNames.HORIZONTAL_SCROLL:
                strGetPropertyValue = String.valueOf(controlObject.isEnableHorizontalScroll());
                break;
            case PropertiesNames.THRESHOLD:
                strGetPropertyValue = controlObject.getThreshold();
                break;
            case PropertiesNames.LAZY_LOADING:
                strGetPropertyValue = String.valueOf(controlObject.isLazyLoadingEnabled());
                break;
            case PropertiesNames.SEARCH_BASED_ON:
                strGetPropertyValue = String.valueOf(controlObject.isDataViewer_searchEnabled());
                break;
            case PropertiesNames.SEARCH_HEADER:
                strGetPropertyValue = String.valueOf(controlObject.isDataViewer_HeaderSearchEnabled());
                break;
            case PropertiesNames.SEARCH_DESCRIPTION:
                strGetPropertyValue = String.valueOf(controlObject.isDataViewer_DescriptionSearchEnabled());
                break;
            case PropertiesNames.UI_PATTERN:
                strGetPropertyValue = controlObject.getDataViewer_UI_Pattern();
                break;
            case PropertiesNames.DATA_VIEWER_SHAPE:
                strGetPropertyValue = controlObject.getDataViewer_Shape();
                break;
            case PropertiesNames.DEFAULT_IMAGE_PATH:
                strGetPropertyValue = controlObject.getDataViewer_DefualtImage_path();
                break;
            case PropertiesNames.PROFILE_IMAGE_PATH:
                strGetPropertyValue = controlObject.getDataViewer_DefualtImage_path();
                break;
            case PropertiesNames.FRAME_BG_HEX_COLOR:
                strGetPropertyValue = controlObject.getDataViewer_FrameBG_HexColor();
                break;
            case PropertiesNames.HEADER_HEX_COLOR:
                strGetPropertyValue = controlObject.getDataViewer_Header_HexColor();
                break;
            case PropertiesNames.SUB_HEADER_HEX_COLOR:
                strGetPropertyValue = controlObject.getDataViewer_SubHeader_HexColor();
                break;
            case PropertiesNames.DESCRIPTION_HEX_COLOR:
                strGetPropertyValue = controlObject.getDataViewer_Description_HexColor();
                break;
            case PropertiesNames.DATE_TIME_HEX_COLOR:
                strGetPropertyValue = controlObject.getDataViewer_DateTime_HexColor();
                break;
            case PropertiesNames.WEEK_DAYS:
                strGetPropertyValue = String.valueOf(controlObject.isWeekDays());
                break;
            case PropertiesNames.MAP_VIEW:
                strGetPropertyValue = controlObject.getMapView();
                break;
            case PropertiesNames.MAP_VIEW_TYPE:
                strGetPropertyValue = controlObject.getMapViewType();
                break;
            case PropertiesNames.MAP_HEIGHT:
                strGetPropertyValue = controlObject.getMapHeight();
                break;
            case PropertiesNames.BASE_MAP:
                strGetPropertyValue = controlObject.getBaseMap();
                break;
            case PropertiesNames.CUSTOM_MAP_ICON:
                strGetPropertyValue = controlObject.getMapIcon();
                break;
            case PropertiesNames.ZOOM_CONTROLS:
                strGetPropertyValue = String.valueOf(controlObject.isZoomControls());
                break;
            case PropertiesNames.PROGRESS_MAX_VALUE:
                strGetPropertyValue = controlObject.getProgress_maxvalue();
                break;
            case PropertiesNames.PROGRESS_ACTUAL_VALUE:
                strGetPropertyValue = controlObject.getProgress_actualvalue();
                break;
            case PropertiesNames.PROGRESS_HIDE_MAX_VALUE:
                strGetPropertyValue = String.valueOf(controlObject.isHide_progress_maxvalue());
                break;
            case PropertiesNames.PROGRESS_HIDE_ACTUAL_VALUE:
                strGetPropertyValue = String.valueOf(controlObject.isHide_progress_actualvalue());
                break;
            case PropertiesNames.PROGRESS_HEX_COLOR:
                strGetPropertyValue = controlObject.getProgressColorHex();
                break;
            case PropertiesNames.USER_CONTROL_BINDING:
                strGetPropertyValue = String.valueOf(controlObject.isEnableUserControlBinding());
                break;
            case PropertiesNames.POST_LOCATION_BINDING:
                strGetPropertyValue = String.valueOf(controlObject.isEnablePostLocationBinding());
                break;
            case PropertiesNames.MAKE_IT_AS_POPUP:
                strGetPropertyValue = String.valueOf(controlObject.isMakeItAsPopup());
                break;
            case PropertiesNames.CHECKED_VALUE:
                strGetPropertyValue = String.valueOf(controlObject.getCheckbox_CheckedValue());
                break;
            case PropertiesNames.UNCHECKED_VALUE:
                strGetPropertyValue = String.valueOf(controlObject.getCheckbox_unCheckedValue());
                break;
        }
        return strGetPropertyValue;
    }

}
