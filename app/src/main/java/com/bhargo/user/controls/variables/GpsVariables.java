package com.bhargo.user.controls.variables;

/*gpsPoint,lat,long,visible,enable,clear,displayName,displayNameVisibility,hint,
               locationModeType,accuracy,gpsType,typeOfInterval,distanceInterval,timeInterval
               mandatory,mandatoryErrorMessage,saveLatitudeAndLongitudeInSeparateCol,showMap
                textSize,textStyle,textColor (UI Missing)*/
public interface GpsVariables {
    String getGpsPoint();

    void setGpsPoint(String gpsPoint);

    String getLatitude();

    void setLatitude(String latitude);

    String getLongitude();

    void setLongitude(String longitude);

    boolean getVisibility();

    void setVisibility(boolean visibility);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void clean();

    void showMessageBelowControl(String msg);

}
