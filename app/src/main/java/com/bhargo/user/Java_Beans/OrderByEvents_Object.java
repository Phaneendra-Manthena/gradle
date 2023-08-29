package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class OrderByEvents_Object implements Serializable {

    private String ActionType;//WithCondetion or WithoutCondetion
    private int positionInEvent=-1;

    private ActionWithoutCondition_Bean ActionWithoutCondition;
    private ActionWithCondition_Bean ActionWithCondition;


    public String getActionType() {
        return ActionType;
    }

    public void setActionType(String actionType) {
        ActionType = actionType;
    }

    public int getPositionInEvent() {
        return positionInEvent;
    }

    public void setPositionInEvent(int positionInEvent) {
        this.positionInEvent = positionInEvent;
    }

    public ActionWithoutCondition_Bean getActionWithoutCondition() {
        return ActionWithoutCondition;
    }

    public void setActionWithoutCondition(ActionWithoutCondition_Bean actionWithoutCondition) {
        ActionWithoutCondition = actionWithoutCondition;
    }

    public ActionWithCondition_Bean getActionWithCondition() {
        return ActionWithCondition;
    }

    public void setActionWithCondition(ActionWithCondition_Bean actionWithCondition) {
        ActionWithCondition = actionWithCondition;
    }
}
