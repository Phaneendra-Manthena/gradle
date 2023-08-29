package com.bhargo.user.pojos;

import com.google.gson.annotations.SerializedName;

public class QueryGetData {

    @SerializedName("Status")
    private String Status;

    @SerializedName("Message")
    private String Message;


   /* @SerializedName("output")
    private List<outPut> outPutList;

    public class outPut{

        private JSONObject jsonObject;
    }
*/
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

   /* public List<outPut> getOutPutList() {
        return outPutList;
    }

    public void setOutPutList(List<outPut> outPutList) {
        this.outPutList = outPutList;
    }*/
}
