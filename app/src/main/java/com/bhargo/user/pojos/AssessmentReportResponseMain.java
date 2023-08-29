package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.List;

public class AssessmentReportResponseMain implements Serializable {

    String Status;
    List<GetUserAssessmentReportsResponse> UserAssessmentReportDetails;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<GetUserAssessmentReportsResponse> getUserAssessmentReportDetails() {
        return UserAssessmentReportDetails;
    }

    public void setUserAssessmentReportDetails(List<GetUserAssessmentReportsResponse> userAssessmentReportDetails) {
        UserAssessmentReportDetails = userAssessmentReportDetails;
    }
}
