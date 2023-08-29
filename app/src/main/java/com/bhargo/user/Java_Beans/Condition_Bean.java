package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class Condition_Bean implements Serializable {

    private String value;
    private String condition;
    private String target;
    private String conditionType;//Advanced,Normal
    private String advancedCondition;
    private String valueType;

    private String valueControlType;
    private String targetControlType;

    private List<String> valueItemsList;
    private List<String> targetItemsList;
    private String GPSRadius;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public String getAdvancedCondition() {
        return advancedCondition;
    }

    public void setAdvancedCondition(String advancedCondition) {
        this.advancedCondition = advancedCondition;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public List<String> getValueItemsList() {
        return valueItemsList;
    }

    public void setValueItemsList(List<String> valueItemsList) {
        this.valueItemsList = valueItemsList;
    }

    public List<String> getTargetItemsList() {
        return targetItemsList;
    }

    public void setTargetItemsList(List<String> targetItemsList) {
        this.targetItemsList = targetItemsList;
    }

    public String getValueControlType() {
        return valueControlType;
    }

    public void setValueControlType(String valueControlType) {
        this.valueControlType = valueControlType;
    }

    public String getTargetControlType() {
        return targetControlType;
    }

    public void setTargetControlType(String targetControlType) {
        this.targetControlType = targetControlType;
    }

    public String getGPSRadius() {
        return GPSRadius;
    }

    public void setGPSRadius(String GPSRadius) {
        this.GPSRadius = GPSRadius;
    }
}
