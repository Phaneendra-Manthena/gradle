package com.bhargo.user.pojos;

import java.io.Serializable;

public class CreateTaskResponse implements Serializable {
    String Status;
    String Message;
//    List<EmpIdsModel> EmpIds;

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

//    public List<EmpIdsModel> getEmpIds() {
//        return EmpIds;
//    }
//
//    public void setEmpIds(List<EmpIdsModel> empIds) {
//        EmpIds = empIds;
//    }
}
