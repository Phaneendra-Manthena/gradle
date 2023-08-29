package com.bhargo.user.pojos;

import java.io.Serializable;

public class MatchTheFollowingModel implements Serializable {

    public String PartA;
    public String PartB;

    public String getPartA() {
        return PartA;
    }

    public void setPartA(String partA) {
        PartA = partA;
    }

    public String getPartB() {
        return PartB;
    }

    public void setPartB(String partB) {
        PartB = partB;
    }
}
