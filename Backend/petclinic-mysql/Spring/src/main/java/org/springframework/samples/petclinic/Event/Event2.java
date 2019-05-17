package org.springframework.samples.petclinic.Event;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "event2")
public class Event2 {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer Id;

    @Column(name = "EventName", length = 1000000 )
    @NotFound(action = NotFoundAction.IGNORE)
    private String EventName;

    @Column(name = "location")
    @NotFound(action = NotFoundAction.IGNORE)
    private String location;

    @Column(name = "FoodSentence",length = 1000000)
    @NotFound(action = NotFoundAction.IGNORE)
    private String FoodSentence;
    @Column(name = "email",length = 1000000)
    @NotFound(action = NotFoundAction.IGNORE)
    private String email;

    @JsonManagedReference
    @OneToMany(targetEntity = EventInfo.class,cascade = CascadeType.ALL,mappedBy = "event2",fetch = FetchType.LAZY)
    private Set<EventInfo> eventInfoSet=new HashSet<>();

    public Event2(){

    }


    /**
     *
     * @param eventName
     * @param location
     * @param foodSentence
     * @param email
     */
    public Event2(String eventName, String location, String foodSentence, String email) {
        EventName = eventName;
        this.location = location;
        this.FoodSentence = foodSentence;
        this.email = email;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFood() {
        return FoodSentence;
    }

    public void setFood(String food) {
        FoodSentence = food;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Set<EventInfo> getEventInfoSet() {
        return eventInfoSet;
    }

    public void setEventInfoSet(Set<EventInfo> eventInfoSet) {
        this.eventInfoSet.clear();
        this.eventInfoSet.addAll(eventInfoSet);
    }

    @Override
    public String toString() {
        String result=String.format("Event[id:%d, EventName:'%s', Location:'%s', FoodSentence:'%s',Email:'%s']%n",Id,EventName,location,FoodSentence,email);
        if(eventInfoSet.size()>0){
            for(EventInfo s:eventInfoSet){
                result+=s.toString();
                result+="/n";
            }
        }
        return result;
    }
}
