package com.bhargo.user.controls.variables;

/*selectedTime,visible,enable,clear,displayName,displayNameVisibility,hint
                mandatory,mandatoryErrorMessage,beforeCurrentTime,afterCurrentTime,
                betweenStartAndEndTime,startTime,endTime,
                currentTime,timeFormat,timeFormatType,optionFormat,optionFormatType
                  textSize,textStyle,textColor (UI Missing)*/
public interface TimeVariables {
    String getSelectedTime();

    void setSelectedTime(String time);

    boolean getVisibility();

    void setVisibility(boolean visibility);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void clean();

    void showMessageBelowControl(String msg);

}
