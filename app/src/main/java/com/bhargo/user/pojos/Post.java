package com.bhargo.user.pojos;

public class Post {

    public String postName;
    public String postId;
    public Boolean isSelected;

    public Post() {
    }

    public Post(String postName, String postId, Boolean isSelected) {
        this.postName = postName;
        this.postId = postId;
        this.isSelected = isSelected;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
