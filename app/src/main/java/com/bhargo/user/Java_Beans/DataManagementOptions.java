package com.bhargo.user.Java_Beans;

import com.bhargo.user.pojos.EditOrViewColumns;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataManagementOptions implements Serializable {
    List<EditOrViewColumns> previewColumns;//remove
    List<String> viewPageColumnsOrder;//remove

    private  IndexPageDetails indexPageDetails=new IndexPageDetails();
    private  DetailedPageDetails detailedPageDetails=new DetailedPageDetails();
    private boolean enableViewData;
    private boolean enableEditData;
    private boolean enableDeleteData;
    private boolean addNewRecord=true;
    private String recordInsertionType;
    private String captionForAdd;
    private String fetchData;
    private boolean skipIndexPage;
    String indexPageColumnsOrder;
    String Order;

    List<String> detailedPageColumns;
    List<EditOrViewColumns> editColumns;
    private boolean lazyLoadingEnabled;
    private String lazyLoadingThreshold;
    private int reportDisplayType=0;
    private List<API_InputParam_Bean> filterColumns;
    public List<String> List_Table_Columns;
    private String subFormInMainForm ="";


    public List<EditOrViewColumns> getPreviewColumns() {
        return previewColumns;
    }

    public void setPreviewColumns(List<EditOrViewColumns> previewColumns) {
        this.previewColumns = previewColumns;
    }

    public List<String> getViewPageColumnsOrder() {
        return viewPageColumnsOrder;
    }

    public void setViewPageColumnsOrder(List<String> viewPageColumnsOrder) {
        this.viewPageColumnsOrder = viewPageColumnsOrder;
    }



    public IndexPageDetails getIndexPageDetails() {
        return indexPageDetails;
    }

    public void setIndexPageDetails(IndexPageDetails indexPageDetails) {
        this.indexPageDetails = indexPageDetails;
    }

    public DetailedPageDetails getDetailedPageDetails() {
        return detailedPageDetails;
    }

    public void setDetailedPageDetails(DetailedPageDetails detailedPageDetails) {
        this.detailedPageDetails = detailedPageDetails;
    }



    public boolean isEnableViewData() {
        return enableViewData;
    }

    public void setEnableViewData(boolean enableViewData) {
        this.enableViewData = enableViewData;
    }

    public boolean isEnableEditData() {
        return enableEditData;
    }

    public void setEnableEditData(boolean enableEditData) {
        this.enableEditData = enableEditData;
    }

    public boolean isEnableDeleteData() {
        return enableDeleteData;
    }

    public void setEnableDeleteData(boolean enableDeleteData) {
        this.enableDeleteData = enableDeleteData;
    }

    public boolean isAddNewRecord() {
        return addNewRecord;
    }

    public void setAddNewRecord(boolean addNewRecord) {
        this.addNewRecord = addNewRecord;
    }

    public String getRecordInsertionType() {
        return recordInsertionType;
    }

    public void setRecordInsertionType(String recordInsertionType) {
        this.recordInsertionType = recordInsertionType;
    }

    public String getCaptionForAdd() {
        return captionForAdd;
    }

    public void setCaptionForAdd(String captionForAdd) {
        this.captionForAdd = captionForAdd;
    }

    public String getFetchData() {
        return fetchData;
    }

    public void setFetchData(String fetchData) {
        this.fetchData = fetchData;
    }

    public boolean isSkipIndexPage() {
        return skipIndexPage;
    }

    public void setSkipIndexPage(boolean skipIndexPage) {
        this.skipIndexPage = skipIndexPage;
    }



    public String getIndexPageColumnsOrder() {
        return indexPageColumnsOrder;
    }

    public void setIndexPageColumnsOrder(String indexPageColumnsOrder) {
        this.indexPageColumnsOrder = indexPageColumnsOrder;
    }

    public String getOrder() {
        return Order;
    }

    public void setOrder(String order) {
        Order = order;
    }


    public List<String> getDetailedPageColumns() {
        return detailedPageColumns;
    }

    public void setDetailedPageColumns(List<String> detailedPageColumns) {
        this.detailedPageColumns = detailedPageColumns;
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

    public int getReportDisplayType() {
        return reportDisplayType;
    }

    public void setReportDisplayType(int reportDisplayType) {
        this.reportDisplayType = reportDisplayType;
    }

    public List<API_InputParam_Bean> getFilterColumns() {
        return filterColumns;
    }

    public void setFilterColumns(List<API_InputParam_Bean> filterColumns) {
        this.filterColumns = filterColumns;
    }

    public List<EditOrViewColumns> getEditColumns() {
        return editColumns;
    }

    public void setEditColumns(List<EditOrViewColumns> editColumns) {
        this.editColumns = editColumns;
    }

    public List<String> getList_Table_Columns() {
        return List_Table_Columns;
    }

    public void setList_Table_Columns(List<String> list_Table_Columns) {
        List_Table_Columns = list_Table_Columns;
    }

    public String getSubFormInMainForm() {
        return subFormInMainForm;
    }

    public void setSubFormInMainForm(String subFormInMainForm) {
        this.subFormInMainForm = subFormInMainForm;
    }

}
