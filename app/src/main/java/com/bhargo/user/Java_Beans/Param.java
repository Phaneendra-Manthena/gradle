package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class Param implements Serializable {

    private String type;//general/options/display Property
    private String value;//selected Property(ex:display name ,hint)
    private String name;//expression or static
    private String text;//actual value

    public Param() {
    }

    public Param(String type, String value, String name, String text) {
        this.type = type;
        this.value = value;
        this.name = name;
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
