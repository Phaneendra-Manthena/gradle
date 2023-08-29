package com.bhargo.user.controls.variables;

/*selectedRating,visible,enable,clear,displayName,displayNameVisibility,hint,defaultRating,
               NoOfItems,
               disableCount,ratingType,customItemNames,customItemNamesList
               mandatory,mandatoryErrorMessage,
               textSize,textStyle,textColor*/
public interface RatingVariables {
    String getSelectedRating();

    void setSelectedRating(String rating);

    boolean getVisibility();

    void setVisibility(boolean visibility);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void clean();

    void showMessageBelowControl(String msg);
}
