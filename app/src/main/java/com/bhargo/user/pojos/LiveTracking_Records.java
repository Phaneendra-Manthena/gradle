package com.bhargo.user.pojos;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

public class LiveTracking_Records implements Serializable {

    public String LiveStatus;
    List<LatLng> list_latlong;

    public String getLiveStatus() {
        return LiveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        LiveStatus = liveStatus;
    }

    public List<LatLng> getList_latlong() {
        return list_latlong;
    }

    public void setList_latlong(List<LatLng> list_latlong) {
        this.list_latlong = list_latlong;
    }
}
