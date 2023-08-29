package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class UserGroup implements Serializable {

    private String id;
    private String name;

    public UserGroup(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
