package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class ActionWithCondition_Bean implements Serializable {

    private int actionPos=-1;
    private int positionInEvent=-1;
    private String actionWithConditionName;
    private IfElseBlock_Bean ifBlock;
    private List<IfElseBlock_Bean> elseBlockList;
    private IfElseBlock_Bean elseBlock;

    public boolean Active=true;

    public int getPositionInEvent() {
        return positionInEvent;
    }

    public void setPositionInEvent(int positionInEvent) {
        this.positionInEvent = positionInEvent;
    }

    public int getActionPos() {
        return actionPos;
    }

    public void setActionPos(int actionPos) {
        this.actionPos = actionPos;
    }

    public String getActionWithConditionName() {
        return actionWithConditionName;
    }

    public void setActionWithConditionName(String actionWithConditionName) {
        this.actionWithConditionName = actionWithConditionName;
    }

    public IfElseBlock_Bean getIfBlock() {
        return ifBlock;
    }

    public void setIfBlock(IfElseBlock_Bean ifBlock) {
        this.ifBlock = ifBlock;
    }

    public List<IfElseBlock_Bean> getElseBlockList() {
        return elseBlockList;
    }

    public void setElseBlockList(List<IfElseBlock_Bean> elseBlockList) {
        this.elseBlockList = elseBlockList;
    }

    public IfElseBlock_Bean getElseBlock() {
        return elseBlock;
    }

    public void setElseBlock(IfElseBlock_Bean elseBlock) {
        this.elseBlock = elseBlock;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }
}
