package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

public class APIOutputDetails implements Serializable {

    LinkedHashMap<String, List<String>> OutputData;
    List<String> l_tableNames;
    List<String> l_ColNames;
    String filterTableName;

    public LinkedHashMap<String, List<String>> getOutputData() {
        return OutputData;
    }

    public void setOutputData(LinkedHashMap<String, List<String>> outputData) {
        OutputData = outputData;
    }

    public List<String> getL_tableNames() {
        return l_tableNames;
    }

    public void setL_tableNames(List<String> l_tableNames) {
        this.l_tableNames = l_tableNames;
    }

    public List<String> getL_ColNames() {
        return l_ColNames;
    }

    public void setL_ColNames(List<String> l_ColNames) {
        this.l_ColNames = l_ColNames;
    }

    public String getFilterTableName() {
        return filterTableName;
    }

    public void setFilterTableName(String filterTableName) {
        this.filterTableName = filterTableName;
    }


}
