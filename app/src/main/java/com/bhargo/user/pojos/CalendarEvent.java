package com.bhargo.user.pojos;

import java.io.Serializable;

public class CalendarEvent implements Serializable {

    String eventType;
    String eventDates;
    String eventMessage;

    public CalendarEvent() {
    }

    public CalendarEvent(String eventType, String eventDates, String eventMessage) {
        this.eventType = eventType;
        this.eventDates = eventDates;
        this.eventMessage = eventMessage;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDates() {
        return eventDates;
    }

    public void setEventDates(String eventDates) {
        this.eventDates = eventDates;
    }

    public String getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }

}
