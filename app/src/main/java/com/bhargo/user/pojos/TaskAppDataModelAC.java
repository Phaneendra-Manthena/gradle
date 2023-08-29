package com.bhargo.user.pojos;

import java.io.Serializable;

public class TaskAppDataModelAC implements Serializable {

    String ACId;
    String ACName;
    String ACDes;
    String ACImagePath;

    public String getACId() {
        return ACId;
    }

    public void setACId(String ACId) {
        this.ACId = ACId;
    }

    public String getACName() {
        return ACName;
    }

    public void setACName(String ACName) {
        this.ACName = ACName;
    }

    public String getACDes() {
        return ACDes;
    }

    public void setACDes(String ACDes) {
        this.ACDes = ACDes;
    }

    public String getACImagePath() {
        return ACImagePath;
    }

    public void setACImagePath(String ACImagePath) {
        this.ACImagePath = ACImagePath;
    }
}
