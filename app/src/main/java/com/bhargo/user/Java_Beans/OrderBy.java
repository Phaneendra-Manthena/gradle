package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderBy implements Serializable {

    private List<String> columnsList= new ArrayList<>();
    private String order="Asc";

    public List<String> getColumnsList() {
        return columnsList;
    }

    public void setColumnsList(List<String> columnsList) {
        this.columnsList = columnsList;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
