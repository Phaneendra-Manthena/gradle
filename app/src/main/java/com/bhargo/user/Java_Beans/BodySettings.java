package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class BodySettings implements Serializable {


    public LeftLayoutSettings leftLayoutSettings=new LeftLayoutSettings();
    public RightLayoutSettings rightLayoutSettings=new RightLayoutSettings();

    public LeftLayoutSettings getLeftLayoutSettings() {
        return leftLayoutSettings;
    }

    public void setLeftLayoutSettings(LeftLayoutSettings leftLayoutSettings) {
        this.leftLayoutSettings = leftLayoutSettings;
    }

    public RightLayoutSettings getRightLayoutSettings() {
        return rightLayoutSettings;
    }

    public void setRightLayoutSettings(RightLayoutSettings rightLayoutSettings) {
        this.rightLayoutSettings = rightLayoutSettings;
    }
}
