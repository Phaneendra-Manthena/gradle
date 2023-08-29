package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class RenderingType implements Serializable {

    String type;
    List<String> item;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getItem() {
        return item;
    }

    public void setItem(List<String> item) {
        this.item = item;
    }
}
