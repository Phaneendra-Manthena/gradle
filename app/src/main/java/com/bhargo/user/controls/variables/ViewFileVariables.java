package com.bhargo.user.controls.variables;

/*fileLink,visible,enable,clear,displayName,displayNameVisibility,hint,
             hideFileLink,downloadFile,
             textSize,textStyle,textColor*/
public interface ViewFileVariables {

    String getFileLink();

    void setFileLink(String fileLink);

    boolean getVisibility();

    void setVisibility(boolean visibility);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void clean();

    void showMessageBelowControl(String msg);
}
