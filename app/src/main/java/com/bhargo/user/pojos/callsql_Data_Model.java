package com.bhargo.user.pojos;

import com.google.gson.annotations.SerializedName;

public class callsql_Data_Model {

    @SerializedName("Status")
    public String Status;

    @SerializedName("InParameters")
    public String InParameters;
    @SerializedName("optParameters")
    public String optParameters;
    @SerializedName("OutParameters")
    public String OutParameters;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getInParameters() {
        return InParameters;
    }

    public void setInParameters(String inParameters) {
        InParameters = inParameters;
    }

    public String getOptParameters() {
        return optParameters;
    }

    public void setOptParameters(String optParameters) {
        this.optParameters = optParameters;
    }

    public String getOutParameters() {
        return OutParameters;
    }

    public void setOutParameters(String outParameters) {
        OutParameters = outParameters;
    }
}
