package org.springframework.samples.petclinic.TestRelationDB;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Country {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer Id;

    private String name;

    private String capital;

    private String currency;

    @OneToMany(cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    mappedBy = "country")
    private Set<State> stateSet=new HashSet<>();



    public Country(){

    }

    public Country(String name, String capital, String currency) {
        this.name = name;
        this.capital = capital;
        this.currency = currency;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Set<State> getStateSet() {
        return stateSet;
    }

    public void setStateSet(Set<State> stateSet) {
        this.stateSet.addAll(stateSet);
    }

    @Override
    public String toString() {
        String result=String.format("Country[id=%d, name='%s]%n",Id,name);
        if(stateSet.size()>0){
            for(State s:stateSet){
                result+=String.format("State[id=%d, name='%s ,age='%s' ]%n",
                    s.getID(),s.getName(),s.getAge());
            }
        }
        return result;
    }
}
