package org.springframework.samples.petclinic.nlpcore;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;


public class Listobj {
	
	private static ArrayList<String> WORDS;
    private static ArrayList<String> POS;
    private static ArrayList<String> NER;
    
    static {
    	if(WORDS==null) {
    		WORDS=new ArrayList<String>();
    	}
    	
    	if(POS==null) {
    		POS=new ArrayList<String>();
    	}
    	
    	if(NER==null) {
    		NER=new ArrayList<String>();
    	}
    }
    
    public static List<String> getWordInstance(){
    	if(WORDS==null) {
    		WORDS=new ArrayList<String>();
    	}
    	return WORDS;
    }
    
    public static List<String> getPOSInstance(){
    	if(POS==null) {
    		POS=new ArrayList<String>();
    	}
    	return POS;
    }
    
    public static List<String> getNERInstance(){
    	if(NER==null) {
    		NER=new ArrayList<String>();
    	}
    	return NER;
    }
    public static void setWORD(List<String> set){
    	if(WORDS==null) {
    		getWordInstance();
    	}
    	WORDS.addAll(set);
    	
    }
    
    public static List<String> getWord(){
    	return WORDS;
    }
    
    public static void setPOS(List<String> set){
    	POS.addAll(set);
    	
    }
    
    public static void setNER(List<String> set){
    	NER.addAll(set);
    	
    }
    
    
	public static String DATE="DATE";
	public static String TIME="TIME";
	public static String NUMBER="NUMBER";
	public static String ORGANIZATION="ORGANIZATION";
	public static String NNP="NNP";
	public static String NN="NN";
	public static String NNS="NNS";
	public static String NNPS="NNPS";
	public static String IN="IN";
	public static String LOCATION="LOCATION";
	public static String TITLE="TITLE";


	


	

}
