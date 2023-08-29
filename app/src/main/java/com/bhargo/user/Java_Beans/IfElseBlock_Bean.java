package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class IfElseBlock_Bean implements Serializable {

    private int elseIfPosition=-1;
    private List<Condition_Bean> conditionsList;
    private String conditionType;
    private List<ActionWithoutCondition_Bean> actionsList;
    private boolean expressionExists;
    private String advancedCondition;

    public int getElseIfPosition() {
        return elseIfPosition;
    }

    public void setElseIfPosition(int elseIfPosition) {
        this.elseIfPosition = elseIfPosition;
    }

    public List<Condition_Bean> getConditionsList() {
        return conditionsList;
    }

    public void setConditionsList(List<Condition_Bean> conditionsList) {
        this.conditionsList = conditionsList;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public List<ActionWithoutCondition_Bean> getActionsList() {
        return actionsList;
    }

    public void setActionsList(List<ActionWithoutCondition_Bean> actionsList) {
        this.actionsList = actionsList;
    }

    public boolean isExpressionExists() {
        return expressionExists;
    }

    public void setExpressionExists(boolean expressionExists) {
        this.expressionExists = expressionExists;
    }

    public String getAdvancedCondition() {
        return advancedCondition;
    }

    public void setAdvancedCondition(String advancedCondition) {
        this.advancedCondition = advancedCondition;
    }
}
