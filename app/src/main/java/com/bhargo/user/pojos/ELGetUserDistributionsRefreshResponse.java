package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.List;

public class ELGetUserDistributionsRefreshResponse implements Serializable {

    String Status;
    List<GetUserDistributionsResponse> NewRegistrations;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<GetUserDistributionsResponse> getNewRegistrations() {
        return NewRegistrations;
    }

    public void setNewRegistrations(List<GetUserDistributionsResponse> newRegistrations) {
        NewRegistrations = newRegistrations;
    }
}
