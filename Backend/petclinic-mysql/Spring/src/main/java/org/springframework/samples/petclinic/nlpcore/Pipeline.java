package org.springframework.samples.petclinic.nlpcore;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
@Service
public class Pipeline {
	private static Properties props ;
	private static String propertiesName="tokenize, ssplit, pos, lemma, ner";
	private static StanfordCoreNLP stanfordcorenlp;
	
	private Pipeline() {
		
	}
	
	static{
		props=new Properties();
		props.setProperty("annotators", propertiesName);
	}

	public static StanfordCoreNLP getInstance() {
		if(stanfordcorenlp==null) {
			stanfordcorenlp= new StanfordCoreNLP(props);
		}
		return stanfordcorenlp;
	}

}
