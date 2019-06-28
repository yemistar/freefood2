package org.springframework.samples.petclinic.nlpcore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.samples.petclinic.Event.EventInfo;
import org.springframework.stereotype.Service;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * 
 * @author Opeyemi Abass
 * 
 * Parse through the email and tag  each words 
 *
 */
@Service 
public class ParseEmail {

	 private final Logger logger = LoggerFactory.getLogger(ParseEmail.class);
	static StanfordCoreNLP stanfordcorenlp=Pipeline.getInstance();
	
	static HashMap<Integer,List<Tripple>> map=new HashMap<>();

    static List<EventInfo> test= new ArrayList<>();

	
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
	private static  List<CoreMap> Food_Sen;
	


	private static List<CoreMap> Organiz_Sen;
	
	private List<CoreMap> Place_Sen;

	private int Sentence_Postion=-1;
	
    
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
		//DaysList.add("NEXT");
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
	 * @return  array list containing part of speech for each words in the email
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
	 * @return arraylist containing name entity recognition for each words in the email
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
	public void getALL(String text,List<String>wordToken, List<String>wordPOS, List<String>wordNER){
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

	public void getALL2(String text){
		Annotation document = new Annotation(text);
		stanfordcorenlp.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        EventInfo eventInfo= new EventInfo();
        eventInfo.setRoomNUmber("TEST");
        test.add(eventInfo);

        boolean foundNER=false;
		int count=0;



		for(CoreMap sentence: sentences) {
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods
            Sentence_Postion++;



			List<Tripple> tripples=new ArrayList<>();
			for (CoreLabel token: sentence.get(TokensAnnotation.class)) {


				// this is the text of the token
				String word = token.get(TextAnnotation.class);

				// this is the POS tag of the token
				String pos = token.get(PartOfSpeechAnnotation.class);


				// this is the NER label of the token
				String ner = token.get(NamedEntityTagAnnotation.class);
				//System.out.println(word +" --- "+ner);
				if(ner.equals("DATE") || ner.equals("TIME")||pos.equals("CD") ){
					foundNER=true;
                    System.out.println(word +" --- "+ner);
				    SetEventInfo(count,ner,pos,word);
					tripples.add(new Tripple(ner,pos,word));
				}



			}//end of forloop
			map.put(count,tripples);
			if(foundNER){
				count++;
				foundNER=false;
			}

		}
        Sentence_Postion=-1;

	}


    /**
     *
     * @param count
     * @param ner
     * @param pos
     * @param word
     */

	void SetEventInfo(int count, String ner, String pos, String word){

	    System.out.println("Length=="+count);

	    switch (ner){
            case "DATE":

                updateList(count,word);

                break;

			case "TIME": case "CD":
				boolean ispostgood=getListPost(count);
				int isTime=4;
				if(ispostgood){
					isPosNull(word,isTime,count);
				}else{
					Pos_is_Null(word,isTime,count);
				}

                break;
        }
    }

    private int Month_or_day(String word){
	    if(List_Month.contains(word) || List_Month.contains(word.toUpperCase())){
	        return 1;
        }else if(DaysList.contains(word) || DaysList.contains(word.toUpperCase())){
	        return 2;
        }
	    return 3;
    }
    private boolean getListPost(int count){
		if(test.size()==count+1){
			return true;
		}
		return false;
	}

	private void isPosNull(String word, int type,int count){
		final int isMonth=1;
		final int isDay=2;
		final int isDateOfMonth=3;
		final int isTime=4;
		switch (type){

			case isMonth :
				if(test.get(count).getMonth()==null){
					test.get(count).setMonth(word);
				}else {
					String temp=test.get(count).getMonth();
					temp+=" "+word;
					test.get(count).setMonth(temp);
				}
				break;

			case isDay:
				if(test.get(count).getDay()==null){
					test.get(count).setDay(word);
				}else {
					String temp=test.get(count).getDay();
					temp+=" "+word;
					test.get(count).setDay(temp);
				}
				break;

			case isDateOfMonth:
				if(test.get(count).getDateOfMonth()==null){
					test.get(count).setDateOfMonth(word);
				}else {
					String temp=test.get(count).getDateOfMonth();
					temp+=" "+word;
					test.get(count).setDateOfMonth(temp);
				}
				break;
			case isTime:
				if(test.get(count).getTime()==null){
					test.get(count).setTime(word);
				}else {
					String temp=test.get(count).getTime();
					temp+=" "+word;
					test.get(count).setTime(temp);
				}
				break;


		}
	}
	private void Pos_is_Null(String word, int type,int count){
		final int isMonth=1;
		final int isDay=2;
		final int isDateOfMonth=3;
		final int isTime=4;
		switch (type){

			case isMonth :
				EventInfo month =new EventInfo();
				month.setMonth(word);
				test.add(count,month);
				break;

			case isDay:
				EventInfo day =new EventInfo();
				day.setDay(word);
				test.add(count,day);
				break;

			case isDateOfMonth:
				EventInfo dateofmonth =new EventInfo();
				dateofmonth.setDateOfMonth(word);
				test.add(count,dateofmonth);
				break;
			case isTime :
				EventInfo time =new EventInfo();
				time.setTime(word);
				test.add(count,time);
				break;

		}
	}

    private void updateList(int count,String word){

		boolean ispostgood=getListPost(count);
		int isWhat=Month_or_day(word);
		if(ispostgood){
			isPosNull(word,isWhat,count);
		}else{
			Pos_is_Null(word,isWhat,count);
		}
	}
	
	/**
	 * 
	 * @param wordToken
	 * @param wordPOS
	 * @param wordNER
	 */
	public  void getALLEmailInfo(List<String>wordToken, List<String>wordPOS, List<String>wordNER){
		String fake=getCurrentemail();
		//then pass it into document
		Annotation document = new Annotation(fake);
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
	
	private String getCurrentemail() {
		//get the latest email and return it as a string
		return null;
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
	
	public List<CoreMap> getFood_Sen() {
		return Food_Sen;
	}

	public List<CoreMap> getOrganiz_Sen() {
		return Organiz_Sen;
	}

	public List<EventInfo> getTest(){
	    return test;
    }

	public static HashMap<Integer, List<Tripple>> getMap() {
		return map;
	}

	public void resetFood_Sen() {
		Food_Sen.clear();
	}
	
	public void reSetOrganiz_Sen() {
		Organiz_Sen.clear();
	}



}
