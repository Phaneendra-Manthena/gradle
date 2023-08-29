package com.bhargo.user.realm;

import java.io.Serializable;
//

public class JSONKeyValueType implements Serializable {

    String key;
    String value;
    String type;
    boolean isStrArray;

    public boolean isStrArray() {
        return isStrArray;
    }

    public void setStrArray(boolean strArray) {
        isStrArray = strArray;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
