package com.example.EventObj;

import java.util.List;

public class Event2 {

    private String location;
    private String email;
    private List<EventInfo> eventInfos;
    private int id;
    private String foodSentence;
    private String eventName;

    public Event2(String location, String email, List<EventInfo> eventInfos, int id, String foodSentence, String eventName) {
        this.location = location;
        this.email = email;
        this.eventInfos = eventInfos;
        this.id = id;
        this.foodSentence = foodSentence;
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<EventInfo> getEventInfos() {
        return eventInfos;
    }

    public void setEventInfos(List<EventInfo> eventInfos) {
        this.eventInfos = eventInfos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodSentence() {
        return foodSentence;
    }

    public void setFoodSentence(String foodSentence) {
        this.foodSentence = foodSentence;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
