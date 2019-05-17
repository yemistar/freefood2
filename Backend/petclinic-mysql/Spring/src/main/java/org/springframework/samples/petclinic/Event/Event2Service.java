package org.springframework.samples.petclinic.Event;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.nlpcore.NlpFunctions;
import org.springframework.samples.petclinic.nlpcore.ParseEmail;
import org.springframework.samples.petclinic.nlpcore.Tripple;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class Event2Service {

    @Autowired
    Event2Repository event2Repository;

    @Autowired
    NlpFunctions nlpFunctions;

    @Autowired
    ParseEmail parseemail;

    public int createEvent2(String text){

        Event2 event2=new Event2();
        ArrayList<String> word=new ArrayList<String>();

        ArrayList<String> pos=new ArrayList<String>();

        ArrayList<String> ner=new ArrayList<String>();

        parseemail.getALL(text, word, pos, ner);

        nlpFunctions.getTime2(word,pos,ner,event2);
        String eventName=nlpFunctions.getORG(word,pos,ner);
        event2.setFood(parseemail.getFood_Sen().toString());
        event2.setEmail(parseemail.getOrganiz_Sen().toString());
        event2.setEventName(eventName);

        event2Repository.save(event2);
        parseemail.resetFood_Sen();
        parseemail.reSetOrganiz_Sen();
        return event2.getId();

    }


}
