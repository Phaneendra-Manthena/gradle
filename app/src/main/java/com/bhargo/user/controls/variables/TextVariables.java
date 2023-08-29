package com.bhargo.user.controls.variables;


//TextInput,NumericInput,Phone,Email,LargeInput,Percentage,Decimal,Password,Currency,DynamicLabel,Button
public interface TextVariables {
    //text,visible,enable,clear
    int VISIBLE = 0;
    int GONE = 8;
    String getTextValue();

    void setTextValue(String value);

    boolean getVisibility();

    void setVisibility(boolean visibility);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void clean();

    void enableHTMLEditor(boolean enabled);

    void enableHTMLViewer(boolean enabled);

    boolean isHTMLViewerEnabled();

    boolean isHTMLEditorEnabled();

    void showMessageBelowControl(String msg);


}
