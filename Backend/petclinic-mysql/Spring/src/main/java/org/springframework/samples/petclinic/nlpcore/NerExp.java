package org.springframework.samples.petclinic.nlpcore;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.Event.Event;
import org.springframework.samples.petclinic.Event.EventRepository;
import org.springframework.samples.petclinic.ImportantEmailObjClasses.Date;
import org.springframework.stereotype.Service;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.CoreMap;

@Service
public class NerExp {
	@Autowired
    EventRepository eventRepository;
	 private final Logger logger = LoggerFactory.getLogger(NerExp.class);
	static StanfordCoreNLP stanfordcorenlp=Pipeline.getInstance();
	
	
	
	//Annotation document = new Annotation(text);
	private static StringBuilder exp;
	
	//Stores individual words in  email text
	private static ArrayList<String> WORDS;
	
	//Stores the PartOfSpeach tag for each words
    private static ArrayList<String> POS;
    
    //Stores the NameEnityOrginzation tag for each words
    private static ArrayList<String> NER;
    
    //Stores the List of month in a year
     static ArrayList<String> List_Month; 
    
    //Store name of Buildings 
	 static ArrayList<String> BulidingList;
	
	//Store name of days 
	static ArrayList<String> DaysList;
	
	//Store the Sentence that potential has the 
	//time and place
	private static List<CoreMap> Food_Sen;
	
	private static List<CoreMap> Organiz_Sen;
	
	private static List<CoreMap> Place_Sen;
	private static String encode="";
    
    static {
    	if(WORDS==null) {
    		WORDS=new ArrayList<String>();
    		List_Month=new ArrayList<String>();
    		BulidingList=new ArrayList<String>();
    		DaysList=new ArrayList<String>();
    		Organiz_Sen=new ArrayList<>();
    		//Place_Sen=new ArrayList<>();
    		
    		exp=new StringBuilder();
    	
    		Food_Sen= new ArrayList<>();
    		setDays();
    		setMonth();
    		setBulidings();
    	}
    	
    	if(POS==null) {
    		POS=new ArrayList<String>();
    	}
    	
    	if(NER==null) {
    		NER=new ArrayList<String>();
    	}
    }
	
	public static void setMonth() {
		List_Month.add("JANUARY");
		List_Month.add("FEBRUARY");
		List_Month.add("MARCH");
		List_Month.add("APRIL");
		List_Month.add("MAY");
		List_Month.add("JUNE");
		List_Month.add("JULY");
		List_Month.add("AUGUST");
		List_Month.add("SEPTEMBER");
		List_Month.add("OCTOBER");
		List_Month.add("NOVEMBER");
		List_Month.add("DECEMBER");
	}

	public static void setBulidings() {
		BulidingList.add("COVER");
		BulidingList.add("DURHAM");
		BulidingList.add("GILMAN");
		BulidingList.add("HOOVER");
		BulidingList.add("HOWE");
		BulidingList.add("MARSTON");
		BulidingList.add("TOWN");
		BulidingList.add("SWEENEY");
		BulidingList.add("CARVER");
		BulidingList.add("PEARSON");
	}
	
	public static void setDays() {
		DaysList.add("MONDAY");
		DaysList.add("TUESDAY");
		DaysList.add("WEDNESDAY");
		DaysList.add("THURSDAY");
		DaysList.add("FRIDAY");
		DaysList.add("SATURDAY");
		DaysList.add("SUNDAY");
	}

	
	/**
	 * 
	 * @param text
	 * @return an Array list of the words 
	 */
	public List<String> getTokenWords(String text){
		Annotation document = new Annotation(text);
		stanfordcorenlp.annotate(document);
		List<String> Token= new ArrayList<String>();
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		
		
		for(CoreMap sentence: sentences) {
			for(CoreLabel token: sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class);
				Token.add(word);
			}
		}
		
		return Token;
	}
	/**
	 * 
	 * @param text
	 * @return
	 */
	public List<String> getPartOfSpeachWords(String text){
		Annotation document = new Annotation(text);
		stanfordcorenlp.annotate(document);
		List<String> PofS= new ArrayList<String>();
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		
		for(CoreMap sentence: sentences) {
			for(CoreLabel token: sentence.get(TokensAnnotation.class)) {
				String word = token.get(PartOfSpeechAnnotation.class);
				PofS.add(word);
			}
		}
		
		return PofS;
	}
	
	/**
	 * 
	 * @param text
	 * @return
	 */
	public List<String> getNamedEntityTaghWords(String text){
		Annotation document = new Annotation(text);
		stanfordcorenlp.annotate(document);
		List<String> NER= new ArrayList<String>();
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		
		for(CoreMap sentence: sentences) {
			for(CoreLabel token: sentence.get(TokensAnnotation.class)) {
				String word = token.get(NamedEntityTagAnnotation.class);
				NER.add(word);
			}
		}
		
		return NER;
	}
	
	/**
	 * 
	 * @param text
	 * @param wordToken
	 * @param wordPOS
	 * @param wordNER
	 */
	public static void getALL(String text,List<String>wordToken, List<String>wordPOS, List<String>wordNER){
		Annotation document = new Annotation(text);
		stanfordcorenlp.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		int expcount=0;
		int count=0;
		for(CoreMap sentence: sentences) {
			  // traversing the words in the current sentence
			  // a CoreLabel is a CoreMap with additional token-specific methods
			count=0;
			expcount++;
			  for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
			    // this is the text of the token
			    String word = token.get(TextAnnotation.class);
			    wordToken.add(word);
			    // this is the POS tag of the token
			    String pos = token.get(PartOfSpeechAnnotation.class);
			    wordPOS.add(pos);
			    // this is the NER label of the token
			    String ne = token.get(NamedEntityTagAnnotation.class);
			    wordNER.add(ne);
			    if(ne.equals(Listobj.DATE) ||ne.equals(Listobj.TIME) || ne.equals(Listobj.LOCATION)) {
			    	count++;
			    }
			    if(ne.equals(Listobj.ORGANIZATION) && expcount<5) {
			    	Organiz_Sen.add(sentence);
			    }
			    
			    if(count>=3 && Food_Sen.contains(sentence)==false) {
			    	Food_Sen.add(sentence);
			    	count=0;
			    }
			   
			  }//end of forloop
		}
		
	}

	public String getRawNLP( String text) {
		 ArrayList<String> Words=new ArrayList<String>();
	     ArrayList<String> pos=new ArrayList<String>();
	     ArrayList<String> ner=new ArrayList<String>();
		
	     getALL(text,Words,pos,ner);

    	StringBuilder bulid =new StringBuilder(); 
    	for(int i=0; i<Words.size(); i++) {
    		if(pos.get(i).equals(Listobj.NNP) ||ner.get(i).equals(Listobj.NUMBER) 
       				|| ner.get(i).equals(Listobj.NUMBER) ||ner.get(i).equals(Listobj.DATE)
       				|| ner.get(i).equals(Listobj.TIME) || ner.get(i).equals(Listobj.ORGANIZATION) ) {
       			
    			bulid.append(Words.get(i));
    			bulid.append(" ");
       			WORDS.add(Words.get(i));
       			POS.add(pos.get(i));
       			NER.add(ner.get(i));
       			
       		}
    	}
    	
    	return bulid.toString();
    }
	
	

	/**
	 * Gets the organization in the email text
	 * @return the organization
	 */
	public String getORG() {
		StringBuilder org= new StringBuilder();
		for(int i=0; i<WORDS.size(); i++) {
			if(NER.get(i).equals(Listobj.ORGANIZATION) && POS.get(i).equals(Listobj.NNP)) {
				 org.append(WORDS.get(i));
				 org.append(" ");
				
			}
		}
		return org.toString();
	}
	
	/**
	 * This get an array List of words and get the 
	 * Time the event is happen, the day, month 
	 * and the date of the month it is happening
	 * 
	 * @return Date object
	 */
	public List<Date>  getTime() {
		logger.error("WORD", WORDS.get(0));
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
		
		for(int i=0; i<WORDS.size(); i++) {
			if(NER.get(i).equals(Listobj.DATE) || NER.get(i).equals(Listobj.TIME)) {
				exp.append(WORDS.get(i));
				exp.append(" ");
				exp.append(NER.get(i));
				exp.append(" |");
				if(NER.get(i).equals(Listobj.DATE) || POS.get(i).equals(Listobj.NNP)) {
					String temp=WORDS.get(i);
					
					
					if(DaysList.contains(temp.toUpperCase()) ||DaysList.contains(temp.toLowerCase())  /*&&(temp.contains(d) || temp.contains(D))*/) {
						Day=temp;
						count++;
					}else if(List_Month.contains(temp.toUpperCase())) {
						Month=temp; 
						count++;
					}else if((temp.contains(test2) || temp.contains(test)) ) {
						dateOfMonth=temp;
						count++;
					} 
					
				} else if(NER.get(i).equals(Listobj.TIME)) {
					
					String temp=WORDS.get(i);
					if(temp.matches("^d+$th") || temp.matches("^[+-]?\\d+$") || temp.matches(".*\\d+.*")||(temp.contains(test3) || temp.contains(am) || temp.contains(pm))) {
						Time=temp;
						count++;
					}
				}
			}
			if(count==4) {
				see.add(new Date(Day,Month,dateOfMonth,Time));
				count=0;
			}
			
		
		}
		return see;
		//return date.toString();
		
	}

	
	/**
	 * 
	 * @return the time, date,location and organization 
	 * as a one string obj
	 */
	public String getTime2() {
		StringBuilder temp=new StringBuilder();
		String Date="";
		String Time="";
		String Organization="";
		String Location="";
		ArrayList<String> test=new ArrayList<>();
		for(int i=0; i<WORDS.size(); i++) {
			if(NER.get(i).equals(Listobj.TIME)) {
				test.add(WORDS.get(i));
				Time+=" "+WORDS.get(i);
			}else if(NER.get(i).equals(Listobj.DATE)) {
				Date+=" "+WORDS.get(i);
			}else if(NER.get(i).equals(Listobj.ORGANIZATION)) {
				Organization+=" "+WORDS.get(i);
			}else if(NER.get(i).equals(Listobj.LOCATION)) {
				Location+=" "+WORDS.get(i);
			}
		}
		Date out=new Date(Date,Time,Organization,Location);
		temp.append("Date: "+Date);
		temp.append(" Time:"+Time);
		temp.append(" Organization:"+Organization);
		temp.append(" Location:"+Location);
		return temp.toString();
		//return out;
	}
	
	/**
	 * 
	 * @return the location in the email text
	 */
	public String getLocation() {
		String Location="";
		for(int i=0; i<WORDS.size(); i++) {
			if(NER.get(i).equals(Listobj.LOCATION)) {
				if(i-1>=0 && POS.get(i).equals(Listobj.IN)) {
					Location+=" "+WORDS.get(i);
				}
				Location+=" "+WORDS.get(i);
			}
		}
		return Location;
	}
	
	/**
	 * creates and store event to the data base
	 */
	public int createEvent() {
		String importantInfo=enCode2(WORDS,POS,NER);
		encode=importantInfo;
		String food=enCode(Food_Sen);
		String organization=enCode(Organiz_Sen);
		String organName="need update";
		String place="need update";
		//need to be refractored @TODO 
		Event temp=new Event(organName,importantInfo,place,food, organization);
		eventRepository.save(temp);
		WORDS.clear();
		POS.clear();
		NER.clear();
		return temp.getId();
		//Event temp2=eventRepository.getOne(1 );
		
	}
	
	
	
	public List<Date> getdate(){
		List<Date> test=Time.getTime(WORDS, NER, POS);
		return test;
	}
	
	/**
	 * 
	 * @param element This is what is  encoded to a string
	 * to be stored in Event data base
	 * @return string object 
	 */
	public String enCode(List<CoreMap> element) {
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
	public String enCode2(List<String> word,List<String> pos,List<String> ner) {

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

	/**
	 * 
	 * @param code The encoded string
	 * @param word  the array  the token word will be store
	 * @param pos   the array  the  parts of speech for the token word will be store
	 * @param ner   the array  the  named entity recognition for the token word will be store
	 */
	public void deCode(String code, List<String> word,List<String> pos, List<String> ner) {
		
		//gets the words encoded from the string code
		List<String> decodedWords=makeword(code);
		
		int t=0;
		for( int j=0; j<decodedWords.size();j+=3) {
			t=j;
			//the token word
			word.add(decodedWords.get(t));
			t++;
			//the token parts of speech for the word
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
	
	
	
	
	
	
	 public  List<String> getWord(){
	    	return WORDS;
	    }
	 
	 public static List<String> getNER(){
	    	return NER;
	    }
	 
	 public static List<String> getPOS(){
	    	return POS;
	    }
	 
	 public List<CoreMap> getSEN(){
	    	return Food_Sen;
	    }

	public String getSubject() {
		
		return exp.toString();
	}
	
public String getencode() {
		
		return encode;
	}

	

}


