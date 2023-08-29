package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class Axis implements Serializable {

    private String value;
    private String label="";

    public Axis() {
    }

    public Axis(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
