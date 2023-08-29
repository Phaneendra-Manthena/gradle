package com.bhargo.user.pojos;

import java.io.Serializable;

public class UserAssessmentReportRequest implements Serializable {

    String UserId;
    String PostId;
    String DistributionId;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }

    public String getDistributionId() {
        return DistributionId;
    }

    public void setDistributionId(String distributionId) {
        DistributionId = distributionId;
    }
}
