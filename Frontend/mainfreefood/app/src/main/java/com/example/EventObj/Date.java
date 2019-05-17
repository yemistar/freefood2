package com.example.EventObj;

public class Date{


    String day;
    String month;
    String dateOfMonth;
    String timeOfDay;

    /**
     *
     * @param day
     * @param month
     * @param dateOfMonth
     * @param timeOfDay
     */
    public Date(String day, String month, String dateOfMonth, String timeOfDay) {
        this.day = day;
        this.month = month;
        this.dateOfMonth = dateOfMonth;
        this.timeOfDay = timeOfDay;
    }

    /**
     *
     * @return day
     */
    public String getDay() {
        return day;
    }

    /**
     *
     * @return month
     */
    public String getMonth() {
        return month;
    }

    /**
     *
     * @return dateOfmonth
     */
    public String getDateOfMonth() {
        return dateOfMonth;
    }

    /**
     *
     * @return time of the day
     */
    public String getTimeOfDay() {
        return timeOfDay;
    }
}
