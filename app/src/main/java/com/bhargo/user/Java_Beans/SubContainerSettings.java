package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class SubContainerSettings implements Serializable {

    private String subControlName;
    private Integer subContainerTemplateId;
    private List<String> subContainerTableColumns;

    SubContainerHeaderSettings subContainerHeaderSettings;
    SubContainerBodySettings subContainerBodySettings;

    public String getSubControlName() {
        return subControlName;
    }

    public void setSubControlName(String subControlName) {
        this.subControlName = subControlName;
    }

    public Integer getSubContainerTemplateId() {
        return subContainerTemplateId;
    }

    public void setSubContainerTemplateId(Integer subContainerTemplateId) {
        this.subContainerTemplateId = subContainerTemplateId;
    }

    public List<String> getSubContainerTableColumns() {
        return subContainerTableColumns;
    }

    public void setSubContainerTableColumns(List<String> subContainerTableColumns) {
        this.subContainerTableColumns = subContainerTableColumns;
    }

    public SubContainerHeaderSettings getSubContainerHeaderSettings() {
        return subContainerHeaderSettings;
    }

    public void setSubContainerHeaderSettings(SubContainerHeaderSettings subContainerHeaderSettings) {
        this.subContainerHeaderSettings = subContainerHeaderSettings;
    }

    public SubContainerBodySettings getSubContainerBodySettings() {
        return subContainerBodySettings;
    }

    public void setSubContainerBodySettings(SubContainerBodySettings subContainerBodySettings) {
        this.subContainerBodySettings = subContainerBodySettings;
    }
}
