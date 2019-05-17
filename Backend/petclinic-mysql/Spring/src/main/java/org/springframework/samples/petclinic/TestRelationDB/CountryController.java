package org.springframework.samples.petclinic.TestRelationDB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class CountryController {

    private final Logger logger= LoggerFactory.getLogger(CountryController.class);
    @Autowired
    CountryRepository countryRepository;

    @RequestMapping(method = RequestMethod.POST,path = "testCountry/makeTest")
    public int makeFakeCountry(){
        Country c=new Country("Nigeria","Abduja","Ninrar");
        State state=new State("Lagos","10");
        state.setCountry(c);
        State state1=new State("Lagos1","20");
        state1.setCountry(c);
        State state2=new State("Lagos2","30");
        state2.setCountry(c);
        State state3=new State("Lagos3","40");
        state3.setCountry(c);
        Set<State> stateSet=new HashSet<>();
        stateSet.add(state);
        stateSet.add(state1);
        stateSet.add(state2);
        stateSet.add(state3);
        c.setStateSet(stateSet);
        logger.info("SetState",""+stateSet.size());
        System.out.println("Setstate: "+""+stateSet.size());
        System.out.println("Setstate: "+""+c.getStateSet().size());

        logger.info("country setState",""+c.getStateSet().size());
        countryRepository.save(c);
       return c.getId();

    }
    @RequestMapping(method = RequestMethod.GET,path = "testCountry/getC")
    public Country getCountry(){
       List<Country> countryList=countryRepository.findAll();
       return countryList.get(0);
    }

    @RequestMapping(method = RequestMethod.GET,path = "testCountry/getSize")
    public int getSize(){
        List<Country> countryList=countryRepository.findAll();
        return countryList.size();
    }

    @RequestMapping(method = RequestMethod.GET,path = "testCountry/getSL")
    public String getCountryList(){
        List<Country> countryList=countryRepository.findAll();
        return countryList.get(5).toString();
    }

    @RequestMapping(method = RequestMethod.GET,path = "testCountry/getS")
    public Object[] getState(){
        List<Country> countryList=countryRepository.findAll();
        Set<State> stateSet=countryList.get(0).getStateSet();
        return stateSet.toArray();
    }
}
