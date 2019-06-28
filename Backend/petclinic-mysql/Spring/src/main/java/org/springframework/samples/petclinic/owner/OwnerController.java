/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.Event.EventInfo;
import org.springframework.samples.petclinic.nlpcore.ParseEmail;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



import java.util.List;
import java.util.Optional;


/**
 * 
 * @author Opeyemi Abass
 * @author Connor Wehr
 * 
 */
@RestController
class OwnerController {

    @Autowired
    OwnerRepository ownersRepository;

    @Autowired
    ParseEmail parseEmail;

    private final Logger logger = LoggerFactory.getLogger(OwnerController.class);
    
    /***
     * 
     * @param owner
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/freefood/newUser")
    public String saveOwner(@RequestBody Owners owner) {
        ownersRepository.save(owner);
//        JSONObject obj = new JSONObject();
//        try {
//			obj.put("result", owner.getUserName());
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

        return "New Owner "+ owner.getUserName() + " Saved";
       
    }

    @RequestMapping(method = RequestMethod.GET, path = "/freefood/users")
    public List<Owners> getAllOwners() {
        logger.info("Entered into Controller Layer");
        List<Owners> results = ownersRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/freefood/{ownerId}")
    public Optional<Owners> findOwnerById(@PathVariable("ownerId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Owners> results = ownersRepository.findById(id);
        return results;
    }
    
    @RequestMapping(method=RequestMethod.DELETE, path="/freefood/{ownerId}")
	public String deleteEvent(@PathVariable("ownerId") int id) {
    	Optional<Owners> out=ownersRepository.findById(id);
    	if(out.isPresent()) {
    		
    		ownersRepository.deleteById(id);
    		return "User deleted";
    	}
		return "event Not found";
	}
    
    @RequestMapping(method=RequestMethod.POST, path="/freefood/update_user")
	public String updateEvent( @RequestBody Owners owner) {
		
    	ownersRepository.save(owner);
		return "user update";
	}

    @RequestMapping(method=RequestMethod.POST, path="/freefood/test")
    public List<EventInfo> test(@RequestBody String text) {
        parseEmail.getALL2(text);

        return parseEmail.getTest();
    }

}
