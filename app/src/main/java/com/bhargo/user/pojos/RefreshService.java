package com.bhargo.user.pojos;

import java.util.List;

public class RefreshService {

    public String Status;
    public String Message;

    public List<AppDetails> QueryName;
    public List<AppDetails> PageName;
    public List<DataControls> datacontrol;
    public List<APIDetails> apiname;
    public List<AppDetails> DashBoard;
    public List<AppDetails> Report;
//    public List<DashBoardDetails> DashBoard;
//    public List<ReportDetails> Report;


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
//
    public List<AppDetails> getPageName() {
        return PageName;
    }

    public void setPageName(List<AppDetails> pageName) {
        PageName = pageName;
    }

    public List<DataControls> getDatacontrol() {
        return datacontrol;
    }

    public void setDatacontrol(List<DataControls> datacontrol) {
        this.datacontrol = datacontrol;
    }

    public List<APIDetails> getApiname() {
        return apiname;
    }

    public void setApiname(List<APIDetails> apiname) {
        this.apiname = apiname;
    }

    public List<AppDetails> getQueryName() {
        return QueryName;
    }

    public void setQueryName(List<AppDetails> queryName) {
        QueryName = queryName;
    }

    public List<AppDetails> getDashBoard() {
        return DashBoard;
    }

    public void setDashBoard(List<AppDetails> dashBoard) {
        DashBoard = dashBoard;
    }

    public List<AppDetails> getReport() {
        return Report;
    }

    public void setReport(List<AppDetails> report) {
        Report = report;
    }
}
