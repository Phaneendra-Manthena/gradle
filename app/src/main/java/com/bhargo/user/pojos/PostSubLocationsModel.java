package com.bhargo.user.pojos;

import java.io.Serializable;

public class PostSubLocationsModel implements Serializable {

    String ControlName;
    String Value;
    String Text;

    public String getControlName() {
        return ControlName;
    }

    public void setControlName(String controlName) {
        ControlName = controlName;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
