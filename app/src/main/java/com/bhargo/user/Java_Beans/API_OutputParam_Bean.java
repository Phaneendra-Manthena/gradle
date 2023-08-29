package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class API_OutputParam_Bean implements Serializable {

    private String OutParam_Name;
    private String OutParam_Mapped_ID;
    private boolean OutParam_Delete;


    //LanguageSettings
    public List<LanguageMapping> List_OutParam_Languages;
    //AdvancedSettings
    private String OutParam_Mapped_Expression;
    //ImageSettings
    private List<ImageAdvanced_Mapped_Item> List_OutParam_ImageAdvanced_Items;
    private String OutParam_ImageAdvanced_ImageorNot;
    private String OutParam_ImageAdvanced_ImageSource;
    private String OutParam_ImageAdvanced_ConditionColumn;
    private String OutParam_ImageAdvanced_Operator;
    //MarkerSettings
    private String OutParam_MarkerAdvanced_ImageSource;
    private String OutParam_MarkerAdvanced_ConditionColumn;
    private String OutParam_MarkerAdvanced_Operator;
    private List<ImageAdvanced_Mapped_Item> List_OutParam_MarkerAdvanced_Items;

    private String OutParam_Marker_defultImage;
    private String OutParam_Marker_RenderingType;
    private List<String> OutParam_Marker_popupData;
    private List<String> OutParam_Marker_popupImages;


    public API_OutputParam_Bean(String OutParam_Name,String OutParam_Mapped_ID,boolean OutParam_Delete){
        this.OutParam_Name=OutParam_Name;
        this.OutParam_Mapped_ID=OutParam_Mapped_ID;
        this.OutParam_Delete=OutParam_Delete;
    }

    public String getOutParam_Name() {
        return OutParam_Name;
    }

    public void setOutParam_Name(String outParam_Name) {
        OutParam_Name = outParam_Name;
    }


    public String getOutParam_Mapped_ID() {
        return OutParam_Mapped_ID;
    }

    public void setOutParam_Mapped_ID(String outParam_Mapped_ID) {
        OutParam_Mapped_ID = outParam_Mapped_ID;
    }

    public boolean isOutParam_Delete() {
        return OutParam_Delete;
    }

    public void setOutParam_Delete(boolean outParam_Delete) {
        OutParam_Delete = outParam_Delete;
    }


    public List<LanguageMapping> getList_OutParam_Languages() {
        return List_OutParam_Languages;
    }

    public void setList_OutParam_Languages(List<LanguageMapping> list_OutParam_Languages) {
        List_OutParam_Languages = list_OutParam_Languages;
    }

    public String getOutParam_Mapped_Expression() {
        return OutParam_Mapped_Expression;
    }

    public void setOutParam_Mapped_Expression(String outParam_Mapped_Expression) {
        OutParam_Mapped_Expression = outParam_Mapped_Expression;
    }

    public List<ImageAdvanced_Mapped_Item> getList_OutParam_ImageAdvanced_Items() {
        return List_OutParam_ImageAdvanced_Items;
    }

    public void setList_OutParam_ImageAdvanced_Items(List<ImageAdvanced_Mapped_Item> list_OutParam_ImageAdvanced_Items) {
        List_OutParam_ImageAdvanced_Items = list_OutParam_ImageAdvanced_Items;
    }

    public String getOutParam_ImageAdvanced_ImageorNot() {
        return OutParam_ImageAdvanced_ImageorNot;
    }

    public void setOutParam_ImageAdvanced_ImageorNot(String outParam_ImageAdvanced_ImageorNot) {
        OutParam_ImageAdvanced_ImageorNot = outParam_ImageAdvanced_ImageorNot;
    }

    public String getOutParam_ImageAdvanced_ImageSource() {
        return OutParam_ImageAdvanced_ImageSource;
    }

    public void setOutParam_ImageAdvanced_ImageSource(String outParam_ImageAdvanced_ImageSource) {
        OutParam_ImageAdvanced_ImageSource = outParam_ImageAdvanced_ImageSource;
    }

    public String getOutParam_ImageAdvanced_ConditionColumn() {
        return OutParam_ImageAdvanced_ConditionColumn;
    }

    public void setOutParam_ImageAdvanced_ConditionColumn(String outParam_ImageAdvanced_ConditionColumn) {
        OutParam_ImageAdvanced_ConditionColumn = outParam_ImageAdvanced_ConditionColumn;
    }

    public String getOutParam_ImageAdvanced_Operator() {
        return OutParam_ImageAdvanced_Operator;
    }

    public void setOutParam_ImageAdvanced_Operator(String outParam_ImageAdvanced_Operator) {
        OutParam_ImageAdvanced_Operator = outParam_ImageAdvanced_Operator;
    }

    public List<ImageAdvanced_Mapped_Item> getList_OutParam_MarkerAdvanced_Items() {
        return List_OutParam_MarkerAdvanced_Items;
    }

    public void setList_OutParam_MarkerAdvanced_Items(List<ImageAdvanced_Mapped_Item> list_OutParam_MarkerAdvanced_Items) {
        List_OutParam_MarkerAdvanced_Items = list_OutParam_MarkerAdvanced_Items;
    }

    public String getOutParam_MarkerAdvanced_ConditionColumn() {
        return OutParam_MarkerAdvanced_ConditionColumn;
    }

    public void setOutParam_MarkerAdvanced_ConditionColumn(String outParam_MarkerAdvanced_ConditionColumn) {
        OutParam_MarkerAdvanced_ConditionColumn = outParam_MarkerAdvanced_ConditionColumn;
    }

    public String getOutParam_MarkerAdvanced_ImageSource() {
        return OutParam_MarkerAdvanced_ImageSource;
    }

    public void setOutParam_MarkerAdvanced_ImageSource(String outParam_MarkerAdvanced_ImageSource) {
        OutParam_MarkerAdvanced_ImageSource = outParam_MarkerAdvanced_ImageSource;
    }

    public String getOutParam_MarkerAdvanced_Operator() {
        return OutParam_MarkerAdvanced_Operator;
    }

    public void setOutParam_MarkerAdvanced_Operator(String outParam_MarkerAdvanced_Operator) {
        OutParam_MarkerAdvanced_Operator = outParam_MarkerAdvanced_Operator;
    }

    public String getOutParam_Marker_defultImage() {
        return OutParam_Marker_defultImage;
    }

    public void setOutParam_Marker_defultImage(String outParam_Marker_defultImage) {
        OutParam_Marker_defultImage = outParam_Marker_defultImage;
    }

    public String getOutParam_Marker_RenderingType() {
        return OutParam_Marker_RenderingType;
    }

    public void setOutParam_Marker_RenderingType(String outParam_Marker_RenderingType) {
        OutParam_Marker_RenderingType = outParam_Marker_RenderingType;
    }

    public List<String> getOutParam_Marker_popupData() {
        return OutParam_Marker_popupData;
    }

    public void setOutParam_Marker_popupData(List<String> outParam_Marker_popupData) {
        OutParam_Marker_popupData = outParam_Marker_popupData;
    }

    public List<String> getOutParam_Marker_popupImages() {
        return OutParam_Marker_popupImages;
    }

    public void setOutParam_Marker_popupImages(List<String> outParam_Marker_popupImages) {
        OutParam_Marker_popupImages = outParam_Marker_popupImages;
    }
}
