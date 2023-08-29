package com.bhargo.user.pojos;

import java.io.Serializable;

public class RequestSaveFilePojo implements Serializable {

    int jarryPos;
    int subJarryPos;
    int innerSubJarryPos;
    String filePath;
    String serverFilePath;
    String key;

    public int getInnerSubJarryPos() {
        return innerSubJarryPos;
    }

    public void setInnerSubJarryPos(int innerSubJarryPos) {
        this.innerSubJarryPos = innerSubJarryPos;
    }

    public int getSubJarryPos() {
        return subJarryPos;
    }

    public void setSubJarryPos(int subJarryPos) {
        this.subJarryPos = subJarryPos;
    }

    public String getServerFilePath() {
        return serverFilePath;
    }

    public void setServerFilePath(String serverFilePath) {
        this.serverFilePath = serverFilePath;
    }

    public int getJarryPos() {
        return jarryPos;
    }

    public void setJarryPos(int jarryPos) {
        this.jarryPos = jarryPos;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
