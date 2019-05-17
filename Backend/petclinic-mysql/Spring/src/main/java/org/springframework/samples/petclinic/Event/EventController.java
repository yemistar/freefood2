package org.springframework.samples.petclinic.Event;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.ImportantEmailObjClasses.Date;
import org.springframework.samples.petclinic.ImportantEmailObjClasses.EventOut;
import org.springframework.samples.petclinic.TestRelationDB.Country;
import org.springframework.samples.petclinic.nlpcore.Listobj;
import org.springframework.samples.petclinic.nlpcore.NerExp;
import org.springframework.samples.petclinic.nlpcore.NlpFunctions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.CoreMap;

@RestController
public class EventController {
	
	   @Autowired
	    EventRepository eventRepository;
	   @Autowired
	   NerExp nerexp;
	   @Autowired
	   NlpFunctions nlpfunction;
	   @Autowired
	   EventService eventservice;
	   @Autowired
	   Event2Service event2Service;
	   @Autowired
	   Event2Repository event2Repository;
	   
	    private final Logger logger = LoggerFactory.getLogger(EventController.class);
	    

	    @RequestMapping(method = RequestMethod.POST, path = "/freefood/email")
	    public String saveOwner(@RequestBody String text) {
	     String see=nerexp.getRawNLP(text);
	        return see;
	    }
	    
	    @RequestMapping(method = RequestMethod.POST, path = "/freefood/saveemail")
	    public int saveEvent(@RequestBody String text) {
	    	event2Repository.deleteAll();

	    	event2Service.createEvent2(text);
	     //int isgood=eventservice.createEvent(text);
	        return 1;
	    }
	    
	    @RequestMapping(method = RequestMethod.GET, path = "/freefood/emailtest")
	    public String getTokingword() {
	    	nerexp.createEvent();
	    	
	     String see=nerexp.getencode();
	        return see;
	    }
	    
	    @RequestMapping(method = RequestMethod.GET, path = "/freefood/emailSen")
	    public String getSentence() {
	    	List<CoreMap> test=nerexp.getSEN();
	    
	        return test.toString();
	    }
	    
	    @RequestMapping(method = RequestMethod.GET, path = "/freefood/emailSub")
	    public List<Date> getSubj() {
	    	List<Date> test=nerexp.getTime();
	    
	        return test;
	    }
	    
	    @RequestMapping(method = RequestMethod.GET, path = "/freefood/email")
	    public List<String> getDate() {
	     //Date see=(Date) nerexp.getTime();
	        return nerexp.getWord();
	    }
	    @RequestMapping(method = RequestMethod.GET, path = "/freefood/emaileventout")
	    public List<EventOut> getWord() {
	     List<EventOut> see=nlpfunction.getAllEventOut();
	        return see;
	    }

	    @RequestMapping(method = RequestMethod.GET, path = "/freefood/events")
	    public List<Event> getAllOwners() {
	        logger.info("Event","Entered into Controller Layer");
	        List<Event> results = eventRepository.findAll();
	        logger.info("Event","Number of Records Fetched:" + results.size());
	        return results;
	    }

	    @RequestMapping(method=RequestMethod.DELETE, path="/freefood/email/{Id}")
		public String deleteEvent(@PathVariable("Id") int id) {
	    	int eventid=eventservice.deleteEvent(id);
	    	if(eventid>0) {
	    		return "event"+eventid+" deleted";
	    	}
			return "event Not found";
		}
	    
	    @RequestMapping(method=RequestMethod.DELETE, path="/freefood/deleteemail")
		public String deleteAllEvent() {
	    	eventservice.deleteAllEvent();
			return "event deleted";
		}

	@RequestMapping(method = RequestMethod.GET,path = "event2test/test")
	public List<Event2> getCountry(){
		List<Event2> event2s=event2Repository.findAll();
		return event2s;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/event2/saveemail")
	public int saveEvent2(@RequestBody String text) {
		//event2Repository.deleteAll();

		int isgood=event2Service.createEvent2(text);
		return isgood;
	}


}
