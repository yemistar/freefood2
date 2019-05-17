package com.example.EventObj;

import java.util.List;

public class Event {




    List<Date> date;
    List<Place> place;
    String Organization;
    String foodSentence;

    /**
     *
     * @param date
     * @param place
     * @param organization
     * @param foodSentence
     */
    public Event(List<Date> date, List<Place> place, String organization, String foodSentence) {
        this.date = date;
        this.place = place;
        Organization = organization;
        this.foodSentence = foodSentence;
    }

    /**
     *
     * @param date
     */
    public void setDate(List<Date> date) {
        this.date = date;
    }

    /**
     *
     * @param place
     */
    public void setPlace(List<Place> place) {
        this.place = place;
    }

    /**
     *
     * @param organization
     */
    public void setOrganization(String organization) {
        Organization = organization;
    }

    /**
     *
     * @param foodSentence
     */
    public void setFoodSentence(String foodSentence) {
        this.foodSentence = foodSentence;
    }

    /**
     *
     * @return date list
     */
    public List<Date> getDate() {
        return date;
    }

    /**
     *
     * @return place list
     */
    public List<Place> getPlace() {
        return place;
    }

    /**
     *
     * @return Organization
     */
    public String getOrganization() {
        return Organization;
    }

    /**
     *
     * @return foodSentence
     */
    public String getFoodSentence() {
        return foodSentence;
    }
}
