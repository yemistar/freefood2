package org.springframework.samples.petclinic.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.nlpcore.ParseEmail;
import org.springframework.stereotype.Service;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
@Service 
public class  EventService {
	
	@Autowired
    EventRepository eventRepository;
	@Autowired
	ParseEmail parseemail;
	

	public int createEvent(String text) {
		 ArrayList<String> word=new ArrayList<String>();
	     ArrayList<String> pos=new ArrayList<String>();
	     ArrayList<String> ner=new ArrayList<String>();
		parseemail.getALL(text, word, pos, ner);
		int response=createEvent(word,pos,ner,parseemail.getFood_Sen(),parseemail.getOrganiz_Sen());
		parseemail.resetFood_Sen();
		parseemail.reSetOrganiz_Sen();
		return response;
	}
	
	public void updateEvent() {
		
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public int deleteEvent(int id) {
		
		Optional<Event> temp=eventRepository.findById(id);
		if(temp.isPresent()) {
			Event temp2=temp.get();
			int ID=temp2.getId();
			eventRepository.deleteById(id);
			return ID;
		}
		return -1;
	}
	
	public void deleteAllEvent() {
		
		eventRepository.deleteAll();
	}

	
	/**
	 * 
	 * @param word
	 * @param pos
	 * @param ner
	 * @param Food_Sen
	 * @param Organiz_Sen
	 * @return
	 */
	private int createEvent(List<String> word, List<String> pos,List<String> ner,List<CoreMap> Food_Sen,
			List<CoreMap> Organiz_Sen) {
		//2 The words that gives info about the event
		String importantEventInfo=enCode2(word,pos,ner);
		//4 The sentence that has food, date, time  or location in it
		String food_date_time_loc_Sen=enCode(Food_Sen);
		//5 The sentence that has the organization
		String organizationSen=enCode(Organiz_Sen);
		//1
		String organName="need update";
		//3
		String place="need update";
//		System.out.println("Food: "+food_date_time_loc_Sen);
//		System.out.println("Place: "+place);
//		System.out.println("ImportantEventInfo: "+importantEventInfo);
//		System.out.println("orgainzationSen: "+organizationSen);
		//System.out.println(organizationSen);

		Event temp=new Event(organName,importantEventInfo,place,food_date_time_loc_Sen, organizationSen);
		eventRepository.save(temp);
		word.clear();
		pos.clear();
		ner.clear();
		return temp.getId();
	}
	
	
	/**
	 * 
	 * @param element This is what is  encoded to a string
	 * to be stored in Event data base
	 * @return string object 
	 */
	private String enCode(List<CoreMap> element) {
		StringBuilder foodSenWord=new StringBuilder();
		for(int i=0; i<element.size(); i++) {
			
			for(CoreLabel token: element.get(i).get(TokensAnnotation.class)){
				String word = token.get(TextAnnotation.class);
				String pos = token.get(PartOfSpeechAnnotation.class);
				String ne = token.get(NamedEntityTagAnnotation.class);
						foodSenWord.append("<");
						foodSenWord.append(word);
						foodSenWord.append("|");
						foodSenWord.append(pos);
						foodSenWord.append("|");
						foodSenWord.append(ne);
						foodSenWord.append(">");
			}
		}
		return foodSenWord.toString();
	}
	
	/**
	 * 
	 * @param word
	 * @param pos
	 * @param ner
	 * @return
	 */
	private String enCode2(List<String> word,List<String> pos,List<String> ner) {

		StringBuilder encode=new StringBuilder();
		for(int i=0; i<word.size(); i++) {
			encode.append("<");
			encode.append(word.get(i));
			encode.append("|");
			encode.append(pos.get(i));
			encode.append("|");
			encode.append(ner.get(i));
			encode.append(">");
		}
		return encode.toString();
	}


    
}
