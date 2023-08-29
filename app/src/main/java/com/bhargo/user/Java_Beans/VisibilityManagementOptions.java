package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class VisibilityManagementOptions implements Serializable {

    List<String> VisibilityOnColumns;
    List<String> VisibilityOffColumns;
    List<String> enableColumns;
    List<String> disableColumns;

    public List<String> getVisibilityOnColumns() {
        return VisibilityOnColumns;
    }

    public void setVisibilityOnColumns(List<String> visibilityOnColumns) {
        VisibilityOnColumns = visibilityOnColumns;
    }

    public List<String> getVisibilityOffColumns() {
        return VisibilityOffColumns;
    }

    public void setVisibilityOffColumns(List<String> visibilityOffColumns) {
        VisibilityOffColumns = visibilityOffColumns;
    }

    public List<String> getEnableColumns() {
        return enableColumns;
    }

    public void setEnableColumns(List<String> enableColumns) {
        this.enableColumns = enableColumns;
    }

    public List<String> getDisableColumns() {
        return disableColumns;
    }

    public void setDisableColumns(List<String> disableColumns) {
        this.disableColumns = disableColumns;
    }
}
