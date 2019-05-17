package com.test.testnlpApi;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ie.util.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.*;
import edu.stanford.nlp.trees.*;
import java.util.*;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ie.util.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.CoreMap;


import java.util.*;

public class App {
    static ArrayList<String> Words=new ArrayList<String>();
    static ArrayList<String> POS=new ArrayList<String>();
    static ArrayList<String> Name_Enity_Reconzation=new ArrayList<String>();
    private ArrayList<String> List_Month=new ArrayList<String>();
    public static ArrayList<String> List_Month1=new ArrayList<String>();
    
    
  
    
    static String DATE="DATE";
	String TIME="TIME";
	String NUMBER="NUMBER";
	String ORGANIZATION="ORGANIZATION";
	String NNP="NNP";
	String NN="NN";
	String NNS="NNS";
	String NNPS="NNPS";
    
    public static String text = "Greetings!  I hope you are staying warm as we settle into the spring semester.  \n" + 
    		"\n" + 
    		" \n" + 
    		"\n" + 
    		"Are you ready to meet new students and reconnect with the old?\n" + 
    		"\n" + 
    		" \n" + 
    		"\n" + 
    		"Do you want to network with our alumni from Honeywell?\n" + 
    		"\n" + 
    		" \n" + 
    		"\n" + 
    		"Join us for the 1st Multicultural Student Networking LUNCHEON of the SPRING Semester!\n" + 
    		"\n" + 
    		" \n" + 
    		"\n" + 
    		"Wednesday, February 6th @ 12:10-1pm\n" + 
    		"\n" + 
    		"3155 Marston Hall\n" + 
    		"\n" + 
    		" \n" + 
    		"\n" + 
    		"FREE LUNCH!\n" + 
    		"\n" + 
    		"Great conversation!!\n" + 
    		"\n" + 
    		"Awesome opportunity with industry professionals!\n" + 
    		"\n" + 
    		" \n" + 
    		"\n" + 
    		"Reply to this email by Monday, February 4th at 12pm (or TODAY) if you will be able to join us so that I can order enough food!\n" + 
    		"\n" + 
    		" \n" + 
    		"\n" + 
    		"Space is limited!";
    public static void main(String[] args) {


        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);


        // read some text in the text variable
       // String text = "What is the weather in lagos at this time and what food ? pizza";
  

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                Words.add(word);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                POS.add(pos);
                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                Name_Enity_Reconzation.add(ne);

                System.out.println(String.format("Print: word: [%s] pos: [%s] ne: [%s]",word, pos, ne));
            }

        }
        System.out.println(test());
        System.out.println(Date());
    }
    public static String test() {
    	String DATE="DATE";
    	String TIME="TIME";
    	String NUMBER="NUMBER";
    	String ORGANIZATION="ORGANIZATION";
    	String NNP="NNP";
    	String NN="NN";
    	String NNS="NNS";
    	String NNPS="NNPS";
    	
    	
    	StringBuilder bulid =new StringBuilder(); 
    	for(int i=0; i<Words.size(); i++) {
    		if(POS.get(i).equals(NNP) ||Name_Enity_Reconzation.get(i).equals(NUMBER) 
    				|| Name_Enity_Reconzation.get(i).equals(NUMBER) ||Name_Enity_Reconzation.get(i).equals(DATE)
    				|| Name_Enity_Reconzation.get(i).equals(TIME) || Name_Enity_Reconzation.get(i).equals(ORGANIZATION) ) {
    			bulid.append(Words.get(i));
    			bulid.append(" -");
    		}
    	}
    	return bulid.toString();
    }

    public static String Date() {
    	StringBuilder bulid = new StringBuilder();
    	for(int i=0; i<Words.size(); i++) {
    		if(Name_Enity_Reconzation.get(i).equals(DATE) ) {
    			bulid.append(Words.get(i));
    			bulid.append(" -");
    		}
    	}
    	return bulid.toString();
    	
    }
    public  void init_List_Month() {
    	List_Month.add("January");
    	List_Month.add("February");
    	List_Month.add("March");
    	List_Month.add("April");
    	List_Month.add("May");
    	List_Month.add("June");
    	List_Month.add("July");
    	List_Month.add("August");
    	List_Month.add("September");
    	List_Month.add("October");
    	List_Month.add("November");
    	List_Month.add("December");
    	
    	
    }

    

}
