package com.bhargo.user.controls.variables;

/*text,visible,enable,clear,displayName,displayNameVisibility,hint,
                textHr,textMin,textSec,
                displayFormat,displayFormatType,autoStart
                textSize,textStyle,textColor,timerColor*/
public interface TimerVaiables {
    String getTimerValue();

    void setTimerValue(String timerValue);


    boolean getVisibility();

    void setVisibility(boolean visibility);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void clean();

    void showMessageBelowControl(String msg);

}
