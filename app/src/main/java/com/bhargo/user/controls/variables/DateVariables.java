package com.bhargo.user.controls.variables;

/*
Date        :   selectedDate,visible,enable,clear,displayName,displayNameVisibility,hint,defaultValue,
                currentDateAsDefault
                mandatory,mandatoryErrorMessage,uniqueField,uniqueFieldErrorMessage,
                betweenStartAndEndDate,startDateValue,EndDateValue,beforeCurrentDate,afterCurrentDate
                getYearFromSelection,getMonthFromSelection,getDayFromSelection,getDateFromSelection,
                textSize,textStyle,textColor (UI Missing)*/
public interface DateVariables {

    String getSelectedDate();

    void setSelectedDate(String date);

    boolean getVisibility();

    void setVisibility(boolean visibility);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void clean();

    void showMessageBelowControl(String msg);

}
