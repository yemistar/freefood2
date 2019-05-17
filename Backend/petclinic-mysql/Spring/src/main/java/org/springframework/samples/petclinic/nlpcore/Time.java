package org.springframework.samples.petclinic.nlpcore;

import java.util.ArrayList;
import java.util.List;

import org.springframework.samples.petclinic.ImportantEmailObjClasses.Date;

public class Time {
	
	/**
	 * This get an array List of words and get the 
	 * Time the event is happen, the day, month 
	 * and the date of the month it is happening
	 * 
	 * @return Date object
	 */
	public static List<Date>  getTime(List<String> WORDS,List<String> NER,List<String> POS) {
		
		List<String> word=WORDS;
		List<String> ner=NER;
		List<String> pos=POS;
		StringBuilder date= new StringBuilder();
		
		//The time
		String Time="";
		
		//The Day
		String Day = "";
		
		//The Month
		String Month="";
		
		//The date of the day
		String dateOfDay="";
		
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
		
		// This is to know when to add a date,
		// to the data array if the count is 4 
		// it will add a date and go back to 0
		int count=0;
		for(int i=0; i<WORDS.size(); i++) {
			if(!NER.get(i).equals(Listobj.DATE)) {
				word.remove(i);
				pos.remove(i);
				ner.remove(i);
			}
		}
		
		for(int i=0; i<word.size(); i++) {
			String temp=word.get(i);
			if(NerExp.DaysList.contains(temp)) {
				Day+=temp;
			}else if(NerExp.List_Month.contains(temp)) {
				Month+=temp;
			}else if(temp.matches("^\\d+$[Tt][Hh]")|| temp.matches("\\d") || ner.get(i).equals(Listobj.NUMBER)) {
				dateOfDay+=temp;
			}
		}
		see.add(new Date(Day, Month, dateOfDay, Time));

		return see;
		//return date.toString();
		
	}

	/**
	 * 
	 * @return the time, date,location and organization 
	 * as a one string obj
	 */
	public String getTime2(List<String> WORDS,List<String> NER,List<String> POS) {
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
	}

}
