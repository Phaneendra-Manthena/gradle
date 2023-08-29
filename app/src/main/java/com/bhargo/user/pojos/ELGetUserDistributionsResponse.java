package com.bhargo.user.pojos;

import java.util.List;

public class ELGetUserDistributionsResponse {

    String Status;
    List<GetUserDistributionsResponse> UserDistributions;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<GetUserDistributionsResponse> getUserDistributions() {
        return UserDistributions;
    }

    public void setUserDistributions(List<GetUserDistributionsResponse> userDistributions) {
        UserDistributions = userDistributions;
    }
}
