package com.bhargo.user.pojos;

import java.util.List;

public class GetDataControlsDetails {

    String UserID;
    String OrgID;
    String PostID;
    List<PostSubLocationsModel> PostSubLocations;
    private  List<DataControls>  DataControls;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getOrgID() {
        return OrgID;
    }

    public void setOrgID(String orgID) {
        OrgID = orgID;
    }

    public String getPostID() {
        return PostID;
    }

    public void setPostID(String postID) {
        PostID = postID;
    }


    public List<com.bhargo.user.pojos.DataControls> getDataControls() {
        return DataControls;
    }

    public void setDataControls(List<com.bhargo.user.pojos.DataControls> dataControls) {
        DataControls = dataControls;
    }


    public List<PostSubLocationsModel> getPostSubLocations() {
        return PostSubLocations;
    }

    public void setPostSubLocations(List<PostSubLocationsModel> postSubLocations) {
        PostSubLocations = postSubLocations;
    }
}
