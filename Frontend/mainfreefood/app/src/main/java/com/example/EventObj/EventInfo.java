package com.example.EventObj;

public class EventInfo {
    private  String location;
    private  String day;
    private String dateOfMonth;
    private String month;
    private String time;
    private String roomNumber;

    private int id;

    public EventInfo(String location, String day, String dateOfMonth, String month, String time, String roomNumber, int id) {
        this.location = location;
        this.day = day;
        this.dateOfMonth = dateOfMonth;
        this.month = month;
        this.time = time;
        this.roomNumber = roomNumber;
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDateOfMonth() {
        return dateOfMonth;
    }

    public void setDateOfMonth(String dateOfMonth) {
        this.dateOfMonth = dateOfMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
