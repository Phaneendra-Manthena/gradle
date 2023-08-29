package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class LanguageMapping implements Serializable {
    private String OutParam_Lang_Name;
    private String OutParam_Lang_Mapped;

    public String getOutParam_Lang_Name() {
        return OutParam_Lang_Name;
    }

    public void setOutParam_Lang_Name(String outParam_Lang_Name) {
        OutParam_Lang_Name = outParam_Lang_Name;
    }

    public String getOutParam_Lang_Mapped() {
        return OutParam_Lang_Mapped;
    }

    public void setOutParam_Lang_Mapped(String outParam_Lang_Mapped) {
        OutParam_Lang_Mapped = outParam_Lang_Mapped;
    }
}