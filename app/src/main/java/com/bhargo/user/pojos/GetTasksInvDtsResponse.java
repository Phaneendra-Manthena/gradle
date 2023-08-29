package com.bhargo.user.pojos;

import java.util.List;

public class GetTasksInvDtsResponse {

    String Status;
    List<TaskAppDataModelAC> TaskAppData;
    List<TaskCmtDataModel> TaskCmtData;
    List<TaskContentDataModelAC> TaskContantData;
    List<TaskDataModelAC> TaskData;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<TaskAppDataModelAC> getTaskAppData() {
        return TaskAppData;
    }

    public void setTaskAppData(List<TaskAppDataModelAC> taskAppData) {
        TaskAppData = taskAppData;
    }

    public List<TaskCmtDataModel> getTaskCmtData() {
        return TaskCmtData;
    }

    public void setTaskCmtData(List<TaskCmtDataModel> taskCmtData) {
        TaskCmtData = taskCmtData;
    }

    public List<TaskContentDataModelAC> getTaskContantData() {
        return TaskContantData;
    }

    public void setTaskContantData(List<TaskContentDataModelAC> taskContantData) {
        TaskContantData = taskContantData;
    }

    public List<TaskDataModelAC> getTaskData() {
        return TaskData;
    }

    public void setTaskData(List<TaskDataModelAC> taskData) {
        TaskData = taskData;
    }
}
