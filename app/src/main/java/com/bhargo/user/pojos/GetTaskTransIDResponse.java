package com.bhargo.user.pojos;

import java.util.List;

public class GetTaskTransIDResponse {

    String Status;
    List<TaskTransDataModel> TaskTransData;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<TaskTransDataModel> getTaskTransData() {
        return TaskTransData;
    }

    public void setTaskTransData(List<TaskTransDataModel> taskTransData) {
        TaskTransData = taskTransData;
    }
}
