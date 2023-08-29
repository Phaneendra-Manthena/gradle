package com.bhargo.user.pojos;

import java.util.List;

public class RefreshMain {

    public String UserID;
    public String OrgId;
    public String PostID;
    public List<AppDetailsMArr> QueryFormArr;
    public List<AppDetailsMArr> AppArr;
    public List<DataControlsMArr> DataControlsArr;
    public List<APIDetailsMArr> APIArr;
//    public List<AppDetailsMArr> DashBoardArr;
    public List<DashBoardArr> DashBoardArr;
    public List<ReportsMArr> ReportArr;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public List<AppDetailsMArr> getAppArr() {
        return AppArr;
    }

    public void setAppArr(List<AppDetailsMArr> appArr) {
        AppArr = appArr;
    }

    public List<DataControlsMArr> getDataControlsArr() {
        return DataControlsArr;
    }

    public void setDataControlsArr(List<DataControlsMArr> dataControlsArr) {
        DataControlsArr = dataControlsArr;
    }

    public List<APIDetailsMArr> getAPIArr() {
        return APIArr;
    }

    public void setAPIArr(List<APIDetailsMArr> APIArr) {
        this.APIArr = APIArr;
    }

    public List<AppDetailsMArr> getQueryFormArr() {
        return QueryFormArr;
    }

    public void setQueryFormArr(List<AppDetailsMArr> queryFormArr) {
        QueryFormArr = queryFormArr;
    }

    public List<com.bhargo.user.pojos.DashBoardArr> getDashBoardArr() {
        return DashBoardArr;
    }

    public void setDashBoardArr(List<com.bhargo.user.pojos.DashBoardArr> dashBoardArr) {
        DashBoardArr = dashBoardArr;
    }

    public List<ReportsMArr> getReportArr() {
        return ReportArr;
    }

    public void setReportArr(List<ReportsMArr> reportArr) {
        ReportArr = reportArr;
    }

    public String getPostID() {
        return PostID;
    }

    public void setPostID(String postID) {
        PostID = postID;
    }
}
