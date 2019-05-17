package com.example.EventObj;

public class Place{


    String buliding;
    String roomNumber;
    String floorNumber;

    /**
     *
     * @param buliding
     * @param roomNumber
     * @param floorNumber
     */
    public Place(String buliding, String roomNumber, String floorNumber) {
        this.buliding = buliding;
        this.roomNumber = roomNumber;
        this.floorNumber = floorNumber;
    }

    /**
     *
     * @return buliding
     */
    public String getBuliding() {
        return buliding;
    }

    /**
     *
     * @return roomNumber
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     *
     * @return floorNumber
     */
    public String getFloorNumber() {
        return floorNumber;
    }
}