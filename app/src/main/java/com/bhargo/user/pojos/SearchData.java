package com.bhargo.user.pojos;

public class SearchData {

    private String Value;
    private String Id;

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    @Override
    public String toString() {
        return Value ;
    }
}
