package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class SMSGateways_InputDetails_Bean implements Serializable {

    String KeyName, KeyValue, Purpose, Optional, Static;

    public String getKeyName() {
        return KeyName;
    }

    public void setKeyName(String keyName) {
        KeyName = keyName;
    }

    public String getKeyValue() {
        return KeyValue;
    }

    public void setKeyValue(String keyValue) {
        KeyValue = keyValue;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        Purpose = purpose;
    }

    public String getOptional() {
        return Optional;
    }

    public void setOptional(String optional) {
        Optional = optional;
    }

    public String getStatic() {
        return Static;
    }

    public void setStatic(String aStatic) {
        Static = aStatic;
    }

}
