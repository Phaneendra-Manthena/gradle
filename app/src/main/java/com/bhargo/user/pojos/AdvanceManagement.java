package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.List;

public class AdvanceManagement implements Serializable {


    List<String> indexPageColumns;
    String indexPageColumnsOrder;
    String indexPageColumnsOrderList;
    List<String> viewPageColumnsOrder;

    private boolean enableViewData;
    private boolean enableEditData;
    private boolean enableDeleteData;
    private boolean addNewRecord;
    private boolean allowOnlyOneRecord;

    private String captionForAdd;
    private String fetchData;
    private boolean lazyLoadingEnabled;
    private String lazyLoadingThreshold;
    private boolean skipIndexPage;
    List<String> previewColumns;
    List<EditOrViewColumns> editColumns;


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

    public boolean isAllowOnlyOneRecord() {
        return allowOnlyOneRecord;
    }

    public void setAllowOnlyOneRecord(boolean allowOnlyOneRecord) {
        this.allowOnlyOneRecord = allowOnlyOneRecord;
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



    public List<String> getIndexPageColumns() {
        return indexPageColumns;
    }

    public void setIndexPageColumns(List<String> indexPageColumns) {
        this.indexPageColumns = indexPageColumns;
    }

    public String getIndexPageColumnsOrder() {
        return indexPageColumnsOrder;
    }

    public void setIndexPageColumnsOrder(String indexPageColumnsOrder) {
        this.indexPageColumnsOrder = indexPageColumnsOrder;
    }

    public String getIndexPageColumnsOrderList() {
        return indexPageColumnsOrderList;
    }

    public void setIndexPageColumnsOrderList(String indexPageColumnsOrderList) {
        this.indexPageColumnsOrderList = indexPageColumnsOrderList;
    }

    public List<String> getViewPageColumnsOrder() {
        return viewPageColumnsOrder;
    }

    public void setViewPageColumnsOrder(List<String> viewPageColumnsOrder) {
        this.viewPageColumnsOrder = viewPageColumnsOrder;
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

    public boolean isSkipIndexPage() {
        return skipIndexPage;
    }

    public void setSkipIndexPage(boolean skipIndexPage) {
        this.skipIndexPage = skipIndexPage;
    }

    public List<String> getPreviewColumns() {
        return previewColumns;
    }

    public void setPreviewColumns(List<String> previewColumns) {
        this.previewColumns = previewColumns;
    }

    public List<EditOrViewColumns> getEditColumns() {
        return editColumns;
    }

    public void setEditColumns(List<EditOrViewColumns> editColumns) {
        this.editColumns = editColumns;
    }
}
