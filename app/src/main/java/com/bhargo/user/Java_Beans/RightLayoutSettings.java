package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class RightLayoutSettings implements Serializable {

    private Boolean isRightLayout;
    private List<String> tableColumns;

    public Boolean getRightLayout() {
        return isRightLayout;
    }

    public void setRightLayout(Boolean rightLayout) {
        isRightLayout = rightLayout;
    }

    public List<String> getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(List<String> tableColumns) {
        this.tableColumns = tableColumns;
    }
}
