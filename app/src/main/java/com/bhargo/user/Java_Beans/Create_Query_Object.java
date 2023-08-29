package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Create_Query_Object implements Serializable {


    String Query_Name, Query_Title, Query_Icon_url,Query_Source,Query_SourceValue,Query_Description,Query_Source_Type;
    boolean Query_OffLine,Query_Edit_rowItem,Query_Delete_rowItem;
    String Query_Index_Heading,Query_FinalForm_Heading;
    String FormQuery_Name,Form_Order;
    private boolean lazyLoadingEnabled;
    private String lazyLoadingThreshold;
    //=========if_Typeis_Form====================

    List<QuerySelectField_Bean> List_Form_DisplayFields=new ArrayList<QuerySelectField_Bean>();

    //===========Index_Columns=====
    List<String> List_Index_Columns=new ArrayList<String>();
    List<QueryIndexField_Bean> List_Query_Index_Fields=new ArrayList<>();

    //=========If_Typeis_APIorQuery============
    private List<QueryFilterField_Bean> List_FormAPIQuery_FilterFields=new ArrayList<QueryFilterField_Bean>();

    //===========OrderBy_Columns=====
    List<String> List_OrderBy_Columns=new ArrayList<String>();

    //===Variables====
    private List<Variable_Bean> List_Varibles=new ArrayList<Variable_Bean>();

    /*Translations*/
    private LinkedHashMap<String, String> translatedAppTitleMap;
    private LinkedHashMap<String, String> translatedAppNamesMap;
    private LinkedHashMap<String, String> translatedAppDescriptionsMap;

    public LinkedHashMap<String, String> getTranslatedAppTitleMap() {
        return translatedAppTitleMap;
    }

    public void setTranslatedAppTitleMap(LinkedHashMap<String, String> translatedAppTitleMap) {
        this.translatedAppTitleMap = translatedAppTitleMap;
    }

    public LinkedHashMap<String, String> getTranslatedAppNamesMap() {
        return translatedAppNamesMap;
    }

    public void setTranslatedAppNamesMap(LinkedHashMap<String, String> translatedAppNamesMap) {
        this.translatedAppNamesMap = translatedAppNamesMap;
    }

    public LinkedHashMap<String, String> getTranslatedAppDescriptionsMap() {
        return translatedAppDescriptionsMap;
    }

    public void setTranslatedAppDescriptionsMap(LinkedHashMap<String, String> translatedAppDescriptionsMap) {
        this.translatedAppDescriptionsMap = translatedAppDescriptionsMap;
    }

    public String getQuery_Name() {
        return Query_Name;
    }

    public void setQuery_Name(String query_Name) {
        Query_Name = query_Name;
    }

    public String getQuery_Description() {
        return Query_Description;
    }

    public void setQuery_Description(String query_Description) {
        Query_Description = query_Description;
    }

    public String getForm_Order() {
        return Form_Order;
    }

    public void setForm_Order(String form_Order) {
        Form_Order = form_Order;
    }

    public String getQuery_Title() {
        return Query_Title;
    }

    public void setQuery_Title(String query_Title) {
        Query_Title = query_Title;
    }

    public String getQuery_Icon_url() {
        return Query_Icon_url;
    }

    public void setQuery_Icon_url(String query_Icon_url) {
        Query_Icon_url = query_Icon_url;
    }


    public String getQuery_Source() {
        return Query_Source;
    }

    public void setQuery_Source(String query_Source) {
        Query_Source = query_Source;
    }

    public String getQuery_SourceValue() {
        return Query_SourceValue;
    }

    public void setQuery_SourceValue(String query_SourceValue) {
        Query_SourceValue = query_SourceValue;
    }

    public String getFormQuery_Name() {
        return FormQuery_Name;
    }

    public void setFormQuery_Name(String formQuery_Name) {
        FormQuery_Name = formQuery_Name;
    }


    public boolean isQuery_OffLine() {
        return Query_OffLine;
    }

    public void setQuery_OffLine(boolean query_OffLine) {
        Query_OffLine = query_OffLine;
    }

    public boolean isQuery_Edit_rowItem() {
        return Query_Edit_rowItem;
    }

    public void setQuery_Edit_rowItem(boolean query_Edit_rowItem) {
        Query_Edit_rowItem = query_Edit_rowItem;
    }

    public boolean isQuery_Delete_rowItem() {
        return Query_Delete_rowItem;
    }

    public void setQuery_Delete_rowItem(boolean query_Delete_rowItem) {
        Query_Delete_rowItem = query_Delete_rowItem;
    }

    public String getQuery_Index_Heading() {
        return Query_Index_Heading;
    }

    public void setQuery_Index_Heading(String query_Index_Heading) {
        Query_Index_Heading = query_Index_Heading;
    }

    public String getQuery_FinalForm_Heading() {
        return Query_FinalForm_Heading;
    }

    public void setQuery_FinalForm_Heading(String query_FinalForm_Heading) {
        Query_FinalForm_Heading = query_FinalForm_Heading;
    }



    public List<QuerySelectField_Bean> getList_Form_DisplayFields() {
        return List_Form_DisplayFields;
    }

    public void setList_Form_DisplayFields(List<QuerySelectField_Bean> list_Form_DisplayFields) {
        List_Form_DisplayFields = list_Form_DisplayFields;
    }

    public List<String> getList_Index_Columns() {
        return List_Index_Columns;
    }

    public void setList_Index_Columns(List<String> list_Index_Columns) {
        List_Index_Columns=list_Index_Columns;
    }


    public List<QueryFilterField_Bean> getList_FormAPIQuery_FilterFields() {
        return List_FormAPIQuery_FilterFields;
    }

    public void setList_FormAPIQuery_FilterFields(List<QueryFilterField_Bean> list_FormAPIQuery_FilterFields) {
        List_FormAPIQuery_FilterFields = list_FormAPIQuery_FilterFields;
    }

    public List<String> getList_OrderBy_Columns() {
        return List_OrderBy_Columns;
    }

    public void setList_OrderBy_Columns(List<String> list_OrderBy_Columns) {
        List_OrderBy_Columns = list_OrderBy_Columns;
    }

    public String getQuery_Source_Type() {
        return Query_Source_Type;
    }

    public void setQuery_Source_Type(String query_Source_Type) {
        Query_Source_Type = query_Source_Type;
    }
    public List<Variable_Bean> getList_Varibles() {
        return List_Varibles;
    }

    public void setList_Varibles(List<Variable_Bean> list_Varibles) {
        List_Varibles = list_Varibles;
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

    public List<QueryIndexField_Bean> getList_Query_Index_Fields() {
        return List_Query_Index_Fields;
    }

    public void setList_Query_Index_Fields(List<QueryIndexField_Bean> list_Query_Index_Fields) {
        List_Query_Index_Fields = list_Query_Index_Fields;
    }


}
