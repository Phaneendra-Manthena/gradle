package com.bhargo.user.pojos;



public class AutoPlaceModel {

    private String id;
    private String placeId;
    private String placeName;
    private String LatLng;


    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString(){

        return  placeName;
    }
}
