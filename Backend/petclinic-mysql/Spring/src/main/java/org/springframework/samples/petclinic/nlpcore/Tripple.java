package org.springframework.samples.petclinic.nlpcore;

public class Tripple {



    private String NER;
    private String POS;
    private String WORD;

    public Tripple(String NER, String POS, String WORD) {
        this.NER = NER;
        this.POS = POS;
        this.WORD = WORD;
    }

    public String getNER() {
        return NER;
    }

    public String getPOS() {
        return POS;
    }

    public String getWORD() {
        return WORD;
    }
}
