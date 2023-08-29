package com.bhargo.user.pojos;

import com.bhargo.user.Java_Beans.ImageAdvanced_Mapped_Item;

import java.io.Serializable;
import java.util.List;

public class DataViewerModelClass implements Serializable {

    public String cornerText;
    public String heading;
    public String subHeading;
    public String DateandTime;
    List<String> description;
    public String ProfileImage_Path;
    public String Image_Path;
    public String AdvanceImage_Source;
    public String AdvanceImage_ConditionColumn;

    public List<ImageAdvanced_Mapped_Item> List_Image_Path;
    public String Video_Path;
    public String Audio_Path;
    public String postUrls;
    public String gpsValue;
    private boolean nullObject;

    //--EV--//
    public String distance;
    public String workingHours;
    public String itemOneCount;
    public String itemTwoCount;
    public String rating;
    private String source_icon;
    private String source_name;
    private String source_time;
    private String news_type;
    public String itemName;
    public String watsAppNo;
    public String dailNo;
//--EV--//
    private String dv_trans_id;
    private boolean selected;

    private String itemValue;

    public String getCornerText() {
        return cornerText;
    }

    public void setCornerText(String cornerText) {
        this.cornerText = cornerText;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getDateandTime() {
        return DateandTime;
    }

    public void setDateandTime(String dateandTime) {
        DateandTime = dateandTime;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public String getProfileImage_Path() {
        return ProfileImage_Path;
    }

    public void setProfileImage_Path(String profileImage_Path) {
        ProfileImage_Path = profileImage_Path;
    }

    public String getImage_Path() {
        return Image_Path;
    }

    public void setImage_Path(String image_Path) {
        Image_Path = image_Path;
    }

    public String getVideo_Path() {
        return Video_Path;
    }

    public void setVideo_Path(String video_Path) {
        Video_Path = video_Path;
    }

    public String getAudio_Path() {
        return Audio_Path;
    }

    public void setAudio_Path(String audio_Path) {
        Audio_Path = audio_Path;
    }

    public String getPostUrls() {
        return postUrls;
    }

    public void setPostUrls(String postUrls) {
        this.postUrls = postUrls;
    }

    public String getGpsValue() {
        return gpsValue;
    }

    public void setGpsValue(String gpsValue) {
        this.gpsValue = gpsValue;
    }

    public String getAdvanceImage_Source() {
        return AdvanceImage_Source;
    }

    public void setAdvanceImage_Source(String advanceImage_Source) {
        AdvanceImage_Source = advanceImage_Source;
    }

    public String getAdvanceImage_ConditionColumn() {
        return AdvanceImage_ConditionColumn;
    }

    public void setAdvanceImage_ConditionColumn(String advanceImage_ConditionColumn) {
        AdvanceImage_ConditionColumn = advanceImage_ConditionColumn;
    }

    public List<ImageAdvanced_Mapped_Item> getList_Image_Path() {
        return List_Image_Path;
    }

    public void setList_Image_Path(List<ImageAdvanced_Mapped_Item> list_Image_Path) {
        List_Image_Path = list_Image_Path;
    }

    public boolean isNullObject() {
        return nullObject;
    }

    public void setNullObject(boolean nullObject) {
        this.nullObject = nullObject;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getItemOneCount() {
        return itemOneCount;
    }

    public void setItemOneCount(String itemOneCount) {
        this.itemOneCount = itemOneCount;
    }

    public String getItemTwoCount() {
        return itemTwoCount;
    }

    public void setItemTwoCount(String itemTwoCount) {
        this.itemTwoCount = itemTwoCount;
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

    public String getDv_trans_id() {
        return dv_trans_id;
    }

    public void setDv_trans_id(String dv_trans_id) {
        this.dv_trans_id = dv_trans_id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }
}
