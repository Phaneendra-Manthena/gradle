package com.bhargo.user.pojos;

import java.util.List;

public class TasksPriorityResponse {

    String Status;
    List<PriorityDataResponse> PriorityData;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<PriorityDataResponse> getPriorityData() {
        return PriorityData;
    }

    public void setPriorityData(List<PriorityDataResponse> priorityData) {
        PriorityData = priorityData;
    }
}
