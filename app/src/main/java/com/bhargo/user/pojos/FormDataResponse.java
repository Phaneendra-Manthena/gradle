package com.bhargo.user.pojos;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class FormDataResponse {

String Status;
String Message;
String Sno;
    List<ResultsModel> Results;

    @SerializedName("Ref_TransID")
    String Ref_TransID;

    private List<JSONObject> Ref_AutoID;


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSno() {
        return Sno;
    }

    public void setSno(String sno) {
        Sno = sno;
    }

    public List<ResultsModel> getResults() {
        return Results;
    }

    public void setResults(List<ResultsModel> results) {
        Results = results;
    }

    public String getRef_TransID() {
        return Ref_TransID;
    }

    public void setRef_TransID(String ref_TransID) {
        Ref_TransID = ref_TransID;
    }

    public List<JSONObject> getRef_AutoID() {
        return Ref_AutoID;
    }

    public void setRef_AutoID(List<JSONObject> ref_AutoID) {
        Ref_AutoID = ref_AutoID;
    }
}
