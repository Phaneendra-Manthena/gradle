package com.bhargo.user.pojos;

import java.util.List;

public class RefreshELearning {

    public String UserId;
    public String PostId;
    public List<EL_GetUserDistributions> getUserDistributionsResponseList;

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

    public List<EL_GetUserDistributions> getGetUserDistributionsResponseList() {
        return getUserDistributionsResponseList;
    }

    public void setGetUserDistributionsResponseList(List<EL_GetUserDistributions> getUserDistributionsResponseList) {
        this.getUserDistributionsResponseList = getUserDistributionsResponseList;
    }
}
