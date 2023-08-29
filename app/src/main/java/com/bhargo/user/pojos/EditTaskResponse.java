package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.List;

public class EditTaskResponse implements Serializable {

    String Status;
//    List<TaskAppDataModel> TaskAppData;
//    List<TaskContentDataModel> TaskContantData;
    List<SpinnerAppsDataModel> TaskAppData;
    List<ContentInfoModel> TaskContantData;
    List<TaskDepGroupDataModel> TaskDepGroupData;
    List<TaskDepEmpDataModel> TaskDepEmpData;
    List<TaskDataModel> TaskData;


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<SpinnerAppsDataModel> getTaskAppData() {
        return TaskAppData;
    }

    public void setTaskAppData(List<SpinnerAppsDataModel> taskAppData) {
        TaskAppData = taskAppData;
    }

    public List<ContentInfoModel> getTaskContantData() {
        return TaskContantData;
    }

    public void setTaskContantData(List<ContentInfoModel> taskContantData) {
        TaskContantData = taskContantData;
    }

    public List<TaskDepGroupDataModel> getTaskDepGroupData() {
        return TaskDepGroupData;
    }

    public void setTaskDepGroupData(List<TaskDepGroupDataModel> taskDepGroupData) {
        TaskDepGroupData = taskDepGroupData;
    }

    public List<TaskDepEmpDataModel> getTaskDepEmpData() {
        return TaskDepEmpData;
    }

    public void setTaskDepEmpData(List<TaskDepEmpDataModel> taskDepEmpData) {
        TaskDepEmpData = taskDepEmpData;
    }

    public List<TaskDataModel> getTaskData() {
        return TaskData;
    }

    public void setTaskData(List<TaskDataModel> taskData) {
        TaskData = taskData;
    }
}
