package com.bhargo.user.pojos;

import java.util.List;

public class AppDetailsAdvancedInput {

    String OrgId;
    String UserId;
    String CreatedBy;
    String PostId;
    String PageName;
    String FetchData;
    String SubmittedUserPostID;
    String LazyLoading = "False";
    String Range = "1-15";
    String Threshold = "15";
    String LazyOrderKey = "";
    String OrderbyStatus;
    String OrderByColumns;
    String OrderByType;

    /*For Search*/
    String SearchKeyword;
    String SearchColumns;
    /*For Search*/
    List<FilterColumns> filterColumns;
    List<FilterSubFormColumns> FilterSubFormColumns;

    public List<com.bhargo.user.pojos.FilterSubFormColumns> getFilterSubFormColumns() {
        return FilterSubFormColumns;
    }

    public void setFilterSubFormColumns(List<com.bhargo.user.pojos.FilterSubFormColumns> filterSubFormColumns) {
        FilterSubFormColumns = filterSubFormColumns;
    }

    public List<FilterColumns> getFilterColumns() {
        return filterColumns;
    }

    public void setFilterColumns(List<FilterColumns> filterColumns) {
        this.filterColumns = filterColumns;
    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }

    public String getPageName() {
        return PageName;
    }

    public void setPageName(String pageName) {
        PageName = pageName;
    }

    public String getFetchData() {
        return FetchData;
    }

    public void setFetchData(String fetchData) {
        FetchData = fetchData;
    }

    public String getSubmittedUserPostID() {
        return SubmittedUserPostID;
    }

    public void setSubmittedUserPostID(String submittedUserPostID) {
        SubmittedUserPostID = submittedUserPostID;
    }

    public String getLazyLoading() {
        return LazyLoading;
    }

    public void setLazyLoading(String lazyLoading) {
        LazyLoading = lazyLoading;
    }

    public String getRange() {
        return Range;
    }

    public void setRange(String range) {
        Range = range;
    }

    public String getThreshold() {
        return Threshold;
    }

    public void setThreshold(String threshold) {
        Threshold = threshold;
    }

    public String getLazyOrderKey() {
        return LazyOrderKey;
    }

    public void setLazyOrderKey(String lazyOrderKey) {
        LazyOrderKey = lazyOrderKey;
    }

    public String getOrderbyStatus() {
        return OrderbyStatus;
    }

    public void setOrderbyStatus(String orderbyStatus) {
        OrderbyStatus = orderbyStatus;
    }

    public String getOrderByColumns() {
        return OrderByColumns;
    }

    public void setOrderByColumns(String orderByColumns) {
        OrderByColumns = orderByColumns;
    }

    public String getOrderByType() {
        return OrderByType;
    }

    public void setOrderByType(String orderByType) {
        OrderByType = orderByType;
    }

    public String getSearchKeyword() {
        return SearchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        SearchKeyword = searchKeyword;
    }

    public String getSearchColumns() {
        return SearchColumns;
    }

    public void setSearchColumns(String searchColumns) {
        SearchColumns = searchColumns;
    }
}
