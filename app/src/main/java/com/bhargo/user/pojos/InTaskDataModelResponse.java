package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.List;

public class InTaskDataModelResponse implements Serializable {

    String Status;
    List<InTaskDataModel> InTaskData;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<InTaskDataModel> getInTaskData() {
        return InTaskData;
    }

    public void setInTaskData(List<InTaskDataModel> inTaskData) {
        InTaskData = inTaskData;
    }
}
