package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class Variable_Bean implements Serializable {

    private String Variable_Name;
    private String Variable_Type;//Single/Multiple
    private String Variable_Mapped_ID;
    private String Variable_singleValue="";
    private List<String> Variable_multiValue;

    public Variable_Bean(String Variable_Name, String Variable_Type, String Variable_Mapped_ID){
        this.Variable_Name=Variable_Name;
        this.Variable_Type=Variable_Type;
        this.Variable_Mapped_ID=Variable_Mapped_ID;
    }
    public boolean isOffline_Variable() {
        return Offline_Variable;
    }

    public void setOffline_Variable(boolean offline_Variable) {
        Offline_Variable = offline_Variable;
    }

    private boolean Offline_Variable;

    public String getVariable_Name() {
        return Variable_Name;
    }

    public void setVariable_Name(String variable_Name) {
        Variable_Name = variable_Name;
    }

    public String getVariable_Type() {
        return Variable_Type;
    }

    public void setVariable_Type(String variable_Type) {
        Variable_Type = variable_Type;
    }

    public String getVariable_Mapped_ID() {
        return Variable_Mapped_ID;
    }

    public void setVariable_Mapped_ID(String variable_Mapped_ID) {
        Variable_Mapped_ID = variable_Mapped_ID;
    }

    public String getVariable_singleValue() {
        return Variable_singleValue;
    }

    public void setVariable_singleValue(String variable_singleValue) {
        Variable_singleValue = variable_singleValue;
    }

    public List<String> getVariable_multiValue() {
        return Variable_multiValue;
    }

    public void setVariable_multiValue(List<String> variable_multiValue) {
        Variable_multiValue = variable_multiValue;
    }
}
