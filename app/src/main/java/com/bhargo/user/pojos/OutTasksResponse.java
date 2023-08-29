package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.List;

public class OutTasksResponse implements Serializable {

    String Status;
    List<OutTaskDataModel> OutTaskData;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<OutTaskDataModel> getOutTaskData() {
        return OutTaskData;
    }

    public void setOutTaskData(List<OutTaskDataModel> outTaskData) {
        OutTaskData = outTaskData;
    }
}
