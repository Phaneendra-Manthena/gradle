package com.bhargo.user.controls.variables;

/*url,visible,enable,clear,displayName,displayNameVisibility,hint
               disableClick,hideURL,urlPlaceholderText
               textSize,textStyle,textColor (UI Missing)*/
public interface UrlLinkVariables {
    String getURLLink();

    void setURLLink(String urllink);

    boolean getVisibility();

    void setVisibility(boolean visibility);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void clean();

    void showMessageBelowControl(String msg);
}
