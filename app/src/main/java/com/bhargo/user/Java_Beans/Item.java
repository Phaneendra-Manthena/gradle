package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class Item  implements Serializable {

    private String id;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Item(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Item() {
    }
    /*@Override
    public int compare(Item item, Item t1) {
        return item.getValue().compareTo(t1.getValue());
    }*/
}
