package com.bhargo.user.pojos;

import java.io.Serializable;

public class ELGetUserDistributionRequestData implements Serializable {

    String UserId;
    String PostId;

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
}
