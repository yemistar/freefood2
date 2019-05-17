package com.test.testnlp.basepackage;

import java.util.List;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class NerExp {
	public static void main(String args[]) {
		StanfordCoreNLP test=Pipeline.getInstance();
		
		String text="Hello, my name is yemi abass and I like coding and i live in New York";
		Annotation document = new Annotation(text);
		CoreDocument coredocunment= new CoreDocument(text);
	
		test.annotate(document);
		
		//List<CoreLabel> corelabel=coredocunment.tokens();
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		
		for(CoreMap sentence: sentences) {
			  // traversing the words in the current sentence
			  // a CoreLabel is a CoreMap with additional token-specific methods
			  for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
			    // this is the text of the token
			    String word = token.get(TextAnnotation.class);
			    // this is the POS tag of the token
			    String pos = token.get(PartOfSpeechAnnotation.class);
			    // this is the NER label of the token
			    String ne = token.get(NamedEntityTagAnnotation.class);
			    System.out.println(String.format("Print: word: [%s] pos: [%s] ne: [%s]",word, pos, ne));
			  }
		 System.out.println("---------------------------------------");
	}
	}
	
}
