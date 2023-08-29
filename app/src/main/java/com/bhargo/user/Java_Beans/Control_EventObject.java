package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class Control_EventObject implements Serializable {

    private String Event_Name;//Action_or_condetion
    private String Action_Type;//WithCondetion or WithoutCondetion
    private int Event_pos=-1;

    private ActionWithoutCondition_Bean ActionwithoutCondetion;
    private ActionWithCondition_Bean ActionwithCondetion;

    private List<ActionWithoutCondition_Bean> actionWithOutConditionList;
    private List<ActionWithCondition_Bean> actionWithConditionList;

    private boolean eventLoaded;
    private boolean submitClicked;

    private String submitButtonDisplayName;
    private boolean enableChangeButtonColor;
    private boolean enableButtonTextFontSize;
    private String textSize;
    private String textHexColor;
    private int textColor;



    public Control_EventObject() {
    }

    public Control_EventObject(String event_Name, String event_Type, int event_pos, ActionWithoutCondition_Bean actionwithoutCondetion, ActionWithCondition_Bean actionwithCondetion) {
        Event_Name = event_Name;
        Action_Type = event_Type;
        Event_pos = event_pos;
        ActionwithoutCondetion = actionwithoutCondetion;
        ActionwithCondetion = actionwithCondetion;
    }

    public String getEvent_Name() {
        return Event_Name;
    }

    public void setEvent_Name(String event_Name) {
        Event_Name = event_Name;
    }

    public String getAction_Type() {
        return Action_Type;
    }

    public void setAction_Type(String Action_Type) {
        Action_Type = Action_Type;
    }

    public int getEvent_pos() {
        return Event_pos;
    }

    public void setEvent_pos(int event_pos) {
        Event_pos = event_pos;
    }

    public ActionWithoutCondition_Bean getActionwithoutCondetion() {
        return ActionwithoutCondetion;
    }

    public void setActionwithoutCondetion(ActionWithoutCondition_Bean actionwithoutCondetion) {
        ActionwithoutCondetion = actionwithoutCondetion;
    }

    public ActionWithCondition_Bean getActionwithCondetion() {
        return ActionwithCondetion;
    }

    public void setActionwithCondetion(ActionWithCondition_Bean actionwithCondetion) {
        ActionwithCondetion = actionwithCondetion;
    }

    public List<ActionWithoutCondition_Bean> getActionWithOutConditionList() {
        return actionWithOutConditionList;
    }

    public void setActionWithOutConditionList(List<ActionWithoutCondition_Bean> actionWithOutConditionList) {
        this.actionWithOutConditionList = actionWithOutConditionList;
    }

    public List<ActionWithCondition_Bean> getActionWithConditionList() {
        return actionWithConditionList;
    }

    public void setActionWithConditionList(List<ActionWithCondition_Bean> actionWithConditionList) {
        this.actionWithConditionList = actionWithConditionList;
    }

    public boolean isEventLoaded() {
        return eventLoaded;
    }

    public void setEventLoaded(boolean eventLoaded) {
        this.eventLoaded = eventLoaded;
    }

    public boolean isSubmitClicked() {
        return submitClicked;
    }

    public void setSubmitClicked(boolean submitClicked) {
        this.submitClicked = submitClicked;
    }

    public String getSubmitButtonDisplayName() {
        return submitButtonDisplayName;
    }

    public void setSubmitButtonDisplayName(String submitButtonDisplayName) {
        this.submitButtonDisplayName = submitButtonDisplayName;
    }

    public boolean isEnableChangeButtonColor() {
        return enableChangeButtonColor;
    }

    public void setEnableChangeButtonColor(boolean enableChangeButtonColor) {
        this.enableChangeButtonColor = enableChangeButtonColor;
    }

    public boolean isEnableButtonTextFontSize() {
        return enableButtonTextFontSize;
    }

    public void setEnableButtonTextFontSize(boolean enableButtonTextFontSize) {
        this.enableButtonTextFontSize = enableButtonTextFontSize;
    }

    public String getTextSize() {
        return textSize;
    }

    public void setTextSize(String textSize) {
        this.textSize = textSize;
    }

    public String getTextHexColor() {
        return textHexColor;
    }

    public void setTextHexColor(String textHexColor) {
        this.textHexColor = textHexColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
