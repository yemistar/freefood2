package org.springframework.samples.petclinic.Event;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "eventInfo")
public class EventInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    private String Location;

    private String Month;

    private String Day;

    private String Time;

    private String RoomNUmber;

    private String DateOfMonth;


    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(columnDefinition = "event2_id")
    private Event2 event2;



    public Event2 getEvent2() {
        return event2;
    }

    public void setEvent2(Event2 event2) {
        this.event2 = event2;
    }

    public EventInfo(){

    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getRoomNUmber() {
        return RoomNUmber;
    }

    public void setRoomNUmber(String roomNUmber) {
        RoomNUmber = roomNUmber;
    }

    public String getDateOfMonth() {
        return DateOfMonth;
    }

    public void setDateOfMonth(String dateOfMonth) {
        DateOfMonth = dateOfMonth;
    }

    @Override
    public String toString() {
        String result=String.format("EventInfo[ID:%d, Month:'%s', Day:'%s', Time:'%s', RoomNumber:'%s']",ID,Month,Day,Time,RoomNUmber);
        return result;
    }
}
