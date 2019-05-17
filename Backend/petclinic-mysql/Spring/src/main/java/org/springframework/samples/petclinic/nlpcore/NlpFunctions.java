package org.springframework.samples.petclinic.nlpcore;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.Event.*;
import org.springframework.samples.petclinic.ImportantEmailObjClasses.Date;
import org.springframework.samples.petclinic.ImportantEmailObjClasses.EventOut;
import org.springframework.samples.petclinic.ImportantEmailObjClasses.Places;
import org.springframework.stereotype.Service;

@Service
public class NlpFunctions {
	
	//get stuff from the back end
	// display it
	 private final Logger logger = LoggerFactory.getLogger(NlpFunctions.class);
	
	@Autowired
    EventRepository eventRepository;
	
	
	/**
	 * 
	 * @param id of the event in the database
	 * @return the eventout obj of the event
	 */
	public EventOut getEventById(int id) {
		logger.debug("NlpF", "Start of getevent method");
		
		Optional<Event> event =eventRepository.findById(id);
		
		Event eventout=event.get();
		
		List<String> word=new ArrayList<>();
		List<String> pos=new ArrayList<>();
		List<String> ner=new ArrayList<>();
		
		List<String> word1=new ArrayList<>();
		List<String> pos1=new ArrayList<>();
		List<String> ner1=new ArrayList<>();
		
		deCode(eventout.getimportantInfo(),word,pos,ner);
		List<Date> date=getTime(word,pos,ner);
		List<Places> place=getLocation(word,pos,ner);
		String organization=getORG(word,pos,ner);
		
		deCode(eventout.getFoodSen(),word1,pos1,ner1);
		String foodsen="";
		for(String s:word1) {
			foodsen+=" "+s;
		}
		word1.clear();
		pos1.clear();
		ner1.clear();
		deCode(eventout.getOrganizationSen(),word1,pos1,ner1);
		
		String orgsen="";
		for(String s:word1) {
			orgsen+=" "+s;
		}
		
		EventOut out=new EventOut(date,place,organization,foodsen,orgsen);
		return out;
	}
	
	/**
	 * 
	 * @return all the event in the database
	 */
	public List<EventOut> getAllEventOut(){//need fixing
		
		List<EventOut> eventout=new ArrayList<>();
		List<Event> temp =eventRepository.findAll();
		
		List<String> word=new ArrayList<>();
		List<String> pos=new ArrayList<>();
		List<String> ner=new ArrayList<>();
		
		List<String> word1=new ArrayList<>();
		List<String> pos1=new ArrayList<>();
		List<String> ner1=new ArrayList<>();
		for(Event e:temp) {
			//System.out.println("NULL STUFF: "+e.getimportantInfo());
			//if()
			deCode(e.getimportantInfo(),word,pos,ner);
			List<Date> date=getTime(word,pos,ner);
			deCode(e.getOrganizationSen(),word,pos,ner);
			word.clear();
			pos.clear();
			ner.clear();
			List<Places> place=getLocation(word,pos,ner);
			String organization=getORG(word,pos,ner);
			
			deCode(e.getFoodSen(),word1,pos1,ner1);
			String foodsen="";
			for(String s:word1) {
				foodsen+=" "+s;
			}
			word1.clear();
			pos1.clear();
			ner1.clear();
			deCode(e.getOrganizationSen(),word1,pos1,ner1);
			
			String orgsen="";
			for(String s:word1) {
				orgsen+=" "+s;
			}
			
			eventout.add(new EventOut(date,place,organization,foodsen,orgsen));
		}

		return eventout;
	}
	/**
	 * 
	 * @param code The encoded string
	 * @param word  the array  the token word will be store
	 * @param pos   the array  the  parts of speech for the token word will be store
	 * @param ner   the array  the  named entity recognition for the token word will be store
	 */
	private void deCode(String code, List<String> word,List<String> pos, List<String> ner) {

		//System.out.println("code:"+code);
		//gets the words encoded from the string code
		List<String> decodedWords=makeword(code);
		
		int t=0;
		for( int j=0; j<decodedWords.size()-3;j+=3) {
			t=j;
			//the token word
			word.add(decodedWords.get(t));
			t++;
			//the token parts of speech for the word
			//System.out.println("T and size"+ t+" "+decodedWords.size() +decodedWords.get(t));
			pos.add(decodedWords.get(t));
			t++;
			//the token named entity recognition for the word
			ner.add(decodedWords.get(t));
				
		}
	}
	
	/**
	 * 
	 * @param code the encoded string
	 * @return the decoded list containing the text store in the code 
	 */
	private List<String> makeword(String code){
		
		char[] codeCharArr=code.toCharArray();
		
		ArrayList<String> decodedWords=new ArrayList<String>();
		
		//size of the  input string
		int size=code.length();
		int i=0;
		while(i<size) {
				if(codeCharArr[i]=='<') {
					i++;
				}
				String codetext="";
				//checks for  the < and > 
				while(codeCharArr[i]!='>' && codeCharArr[i]!='<') {
					//gets the text between the <xxx| or |xxx| or |xxx >
					while(codeCharArr[i]!='|' && codeCharArr[i]!='>') {
						codetext+=codeCharArr[i];
						i++;
					}//end of while
					
					//checks to make sure it gets all the text in between
					if(codeCharArr[i]=='|' || codeCharArr[i]=='>') {
						decodedWords.add(codetext);
						codetext="";
					}//end of if
					i++;
					
					
					if(i>=size) {
						break;
					}//end of if
				}//end of while
			
			i++;
			
		}//end of while
		return decodedWords;
	}
	
	
	/**
	 * This get an array List of words and get the 
	 * Time the event is happen, the day, month 
	 * and the date of the month it is happening
	 * 
	 * @return Date object
	 */
	private List<Date>  getTime(List<String> WORDS, List<String> POS, List<String> NER) {
		
		StringBuilder date= new StringBuilder();
		
		//The time
		String Time="";
		
		//The Day
		String Day = "";
		
		//The Month
		String Month="";
		
		//The date of the month
		String dateOfMonth="";
		
		//Array that store all the date in the Email
		List<Date> see = new ArrayList<Date>();
		//Date see2=null;
		String day;
		
		//for checking if the word has Th
		CharSequence test="TH";
		
		//for checking if the word has th
		CharSequence test2="th";
		
		//for checking if the word has :
		CharSequence test3=":";
		
		//for checking if the word ends with pm
		CharSequence pm="pm";
		
		//for checking if the word ends with am
		CharSequence am="am";
		CharSequence d="day";
		CharSequence D="DAY";
		
		// This is to know when to add a date,
		// to the data array if the count is 4 
		// it will add a date and go back to 0
		int count=0;
		//System.out.println("getdate : testing word size "+WORDS.size());
		for(int i=0; i<WORDS.size(); i++) {
//			System.out.println("getdate : testing word element "+WORDS.get(i));
//			System.out.println("getdate : testing ner element "+NER.get(i));
//			System.out.println("getdate : testing pos element "+POS.get(i));
			if(NER.get(i).equals(Listobj.DATE) || NER.get(i).equals(Listobj.TIME)) {
				
				if(NER.get(i).equals(Listobj.DATE) || POS.get(i).equals(Listobj.NNP)) {
					String temp=WORDS.get(i);
					
					
					if(NerExp.DaysList.contains(temp.toUpperCase()) ||NerExp.DaysList.contains(temp.toLowerCase())  /*&&(temp.contains(d) || temp.contains(D))*/) {
						Day=temp;
						count++;
					}else if(NerExp.List_Month.contains(temp.toUpperCase())) {
						Month=temp; 
						count++;
					}else if((temp.contains(test2) || temp.contains(test)) || temp.matches("^d+$th") || temp.matches("^[+-]?\\d+$") || temp.matches(".*\\d+.*")||(temp.contains(test3) || temp.contains(am) || temp.contains(pm))) {
						dateOfMonth=temp;
						count++;
					}
					
				} else if(NER.get(i).equals(Listobj.TIME)) {
					
					String temp=WORDS.get(i);
					if(temp.matches("^d+$th") || temp.matches("^[+-]?\\d+$") || temp.matches(".*\\d+.*")||(temp.contains(test3) || temp.contains(am) || temp.contains(pm))) {
						Time=temp;
						if(i+1<WORDS.size() && (WORDS.get(i).equals("pm") ||WORDS.get(i).equals("am") || WORDS.get(i).equals("PM") ||WORDS.get(i).equals("AM"))) {
							Time+=WORDS.get(i+1);
						}
						count++;
					}
				}
			}
			if(count==4) {
				if(Day.equals("")){
					Day="Empty";
				}
				see.add(new Date(Day,Month,dateOfMonth,Time));
				//System.out.println("getdate : testing see element "+see.size());
				count=0;
			}
			
		
		}
		return see;
		//return date.toString();
		
	}

	/**
	 * 
	 * @param word
	 * @param pos
	 * @param ner
	 * @return The name of the building and the room number
	 */
	private List<Places> getLocation( List<String> word,List<String> pos, List<String> ner) {
		List<Places> places= new ArrayList<>();
		String bName="";
		String roomNum="";
		int count=0;
		for(int i=0; i<word.size(); i++) {
			if(ner.get(i).equals(Listobj.LOCATION) || ner.get(i).equals(Listobj.TITLE)) {
				bName=word.get(i);
				count++;
				if(i+1<word.size() && (ner.get(i+1).equals(Listobj.NUMBER) || ner.get(i+1).equals(Listobj.DATE))) {
					roomNum=word.get(i+1);
					count++;
				}
				i++;
				if(count==2) {
					places.add(new Places(bName,roomNum));
					count=0;
				}
				
			}
		}
		return places;
		
	}
	
	/**
	 * Gets the organization in the email text
	 * @return the organization
	 */
	public String getORG(List<String> word,List<String> pos, List<String> ner) {
		StringBuilder org= new StringBuilder();
		for(int i=0; i<word.size(); i++) {
			if(ner.get(i).equals(Listobj.ORGANIZATION) && pos.get(i).equals(Listobj.NNP)) {
				 org.append(word.get(i));
				 org.append(" ");
				
			}
		}
		return org.toString();
	}


	public List<Date>  getTime2(List<String> WORDS, List<String> POS, List<String> NER, Event2 event2) {

		Set<EventInfo> eventOutSet=new HashSet<>();
		StringBuilder date= new StringBuilder();

		//The time
		String Time="";

		//The Day
		String Day = "";

		//The Month
		String Month="";

		//The date of the month
		String dateOfMonth="";

		//Array that store all the date in the Email
		List<Date> see = new ArrayList<Date>();
		//Date see2=null;
		String day;

		//for checking if the word has Th
		CharSequence test="TH";

		//for checking if the word has th
		CharSequence test2="th";

		//for checking if the word has :
		CharSequence test3=":";

		//for checking if the word ends with pm
		CharSequence pm="pm";

		//for checking if the word ends with am
		CharSequence am="am";
		CharSequence d="day";
		CharSequence D="DAY";

		// This is to know when to add a date,
		// to the data array if the count is 4
		// it will add a date and go back to 0
		int count=0;
		//System.out.println("getdate : testing word size "+WORDS.size());
		for(int i=0; i<WORDS.size(); i++) {
//			System.out.println("getdate : testing word element "+WORDS.get(i));
//			System.out.println("getdate : testing ner element "+NER.get(i));
//			System.out.println("getdate : testing pos element "+POS.get(i));
			if(NER.get(i).equals(Listobj.DATE) || NER.get(i).equals(Listobj.TIME)) {

				if(NER.get(i).equals(Listobj.DATE) || POS.get(i).equals(Listobj.NNP)) {
					String temp=WORDS.get(i);


					if(NerExp.DaysList.contains(temp.toUpperCase()) ||NerExp.DaysList.contains(temp.toLowerCase())  /*&&(temp.contains(d) || temp.contains(D))*/) {
						Day=temp;
						count++;
					}else if(NerExp.List_Month.contains(temp.toUpperCase())) {
						Month=temp;
						count++;
					}else if((temp.contains(test2) || temp.contains(test)) || temp.matches("^d+$th") || temp.matches("^[+-]?\\d+$") || temp.matches(".*\\d+.*")||(temp.contains(test3) || temp.contains(am) || temp.contains(pm))) {
						dateOfMonth=temp;
						count++;
					}

				} else if(NER.get(i).equals(Listobj.TIME)) {

					String temp=WORDS.get(i);
					if(temp.matches("^d+$th") || temp.matches("^[+-]?\\d+$") || temp.matches(".*\\d+.*")||(temp.contains(test3) || temp.contains(am) || temp.contains(pm))) {
						Time=temp;
						if(i+1<WORDS.size() && (WORDS.get(i).equals("pm") ||WORDS.get(i).equals("am") || WORDS.get(i).equals("PM") ||WORDS.get(i).equals("AM"))) {
							Time+=WORDS.get(i+1);
						}
						count++;
					}
				}
			}
			if(count==4) {
				if(Day.equals("")){
					Day="Empty";
				}
				EventInfo eventInfo=new EventInfo();
				eventInfo.setDay(Day);
				eventInfo.setMonth(Month);
				eventInfo.setDateOfMonth(dateOfMonth);
				eventInfo.setTime(Time);
				eventOutSet.add(eventInfo);
				eventInfo.setEvent2(event2);

				//see.add(new Date(Day,Month,dateOfMonth,Time));
				//System.out.println("getdate : testing see element "+see.size());
				count=0;
			}


		}
		event2.setEventInfoSet(eventOutSet);
		return see;
		//return date.toString();

	}

}
