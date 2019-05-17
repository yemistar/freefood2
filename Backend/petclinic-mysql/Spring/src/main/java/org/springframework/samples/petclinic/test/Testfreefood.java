package org.springframework.samples.petclinic.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.samples.petclinic.Event.Event;
import org.springframework.samples.petclinic.Event.EventRepository;
import org.springframework.samples.petclinic.Event.EventService;
import org.springframework.samples.petclinic.nlpcore.NlpFunctions;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Owners;

import java.security.acl.Owner;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class Testfreefood {

    @InjectMocks
    EventService eventserv;

    @Mock
    OwnerRepository repo;
    @Mock
    EventRepository eventrepo;
    @Mock
    NlpFunctions nlpFunctions;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAccountByIdTest() {
        when(repo.findById(1)).thenReturn(Optional.of(new Owners("Yemi", "Abass", "yemigold11@gmail.com", "cpre", "yemigold")));

        Optional<Owners> acct = repo.findById(1);
       Owners temp= acct.get();

        assertEquals("Yemi", temp.getFirstName());
        assertEquals("Abass", temp.getLastName());
        assertEquals("yemigold11@gmail.com", temp.getEmail());
        assertEquals("cpre", temp.getMajor());
        assertEquals("yemigold", temp.getUserName());
    }

    @Test
    public void getEventOutTest(){

    }

    @Test
    public void getEventByIdTest(){
        when(eventrepo.findById(1))
                .thenReturn(
                        Optional.of(new Event(
                                "need update",
                                "what",
                                "need update",
                                "<What|WP|O><are|VBP|O><you|PRP|O><doing|VBG|O><for|IN|O><lunch|NN|O><next|JJ|DATE><Wednesday|NNP|DATE><April|NNP|DATE><3rd|VBD|DATE><@|SYM|O><12:10-1pm|CD|TIME><?|.|O>",
                                "<SAVE|VB|O><THE|DT|O><DATE|NN|O><for|IN|O><the|DT|O><MLO|NNP|ORGANIZATION><Real|NNP|ORGANIZATION><Talk|NN|ORGANIZATION><Luncheon|NNP|ORGANIZATION><.|.|O><SAVE|VB|O><THE|DT|O><DATE|NN|O><for|IN|O><the|DT|O><MLO|NNP|ORGANIZATION><Real|NNP|ORGANIZATION><Talk|NN|ORGANIZATION><Luncheon|NNP|ORGANIZATION><.|.|O><SAVE|VB|O><THE|DT|O><DATE|NN|O><for|IN|O><the|DT|O><MLO|NNP|ORGANIZATION><Real|NNP|ORGANIZATION><Talk|NN|ORGANIZATION><Luncheon|NNP|ORGANIZATION><.|.|O><SAVE|VB|O><THE|DT|O><DATE|NN|O><for|IN|O><the|DT|O><MLO|NNP|ORGANIZATION><Real|NNP|ORGANIZATION><Talk|NN|ORGANIZATION><Luncheon|NNP|ORGANIZATION><.|.|O>")));

        Optional<Event> temp=eventrepo.findById(1);
        Event temp2=temp.get();

        assertEquals("need update", temp2.getorganName());
        assertEquals("what", temp2.getimportantInfo());
        assertEquals("<What|WP|O><are|VBP|O><you|PRP|O><doing|VBG|O><for|IN|O><lunch|NN|O><next|JJ|DATE><Wednesday|NNP|DATE><April|NNP|DATE><3rd|VBD|DATE><@|SYM|O><12:10-1pm|CD|TIME><?|.|O>", temp2.getFoodSen());
        assertEquals("what", temp2.getimportantInfo());
    }

    @Test
    public void getEventAllOutTest(){
        Event temp=new Event(
                "need update",
                "what",
                "need update",
                "<What|WP|O><are|VBP|O><you|PRP|O><doing|VBG|O><for|IN|O><lunch|NN|O><next|JJ|DATE><Wednesday|NNP|DATE><April|NNP|DATE><3rd|VBD|DATE><@|SYM|O><12:10-1pm|CD|TIME><?|.|O>",
                "<SAVE|VB|O><THE|DT|O><DATE|NN|O><for|IN|O><the|DT|O><MLO|NNP|ORGANIZATION><Real|NNP|ORGANIZATION><Talk|NN|ORGANIZATION><Luncheon|NNP|ORGANIZATION><.|.|O><SAVE|VB|O><THE|DT|O><DATE|NN|O><for|IN|O><the|DT|O><MLO|NNP|ORGANIZATION><Real|NNP|ORGANIZATION><Talk|NN|ORGANIZATION><Luncheon|NNP|ORGANIZATION><.|.|O><SAVE|VB|O><THE|DT|O><DATE|NN|O><for|IN|O><the|DT|O><MLO|NNP|ORGANIZATION><Real|NNP|ORGANIZATION><Talk|NN|ORGANIZATION><Luncheon|NNP|ORGANIZATION><.|.|O><SAVE|VB|O><THE|DT|O><DATE|NN|O><for|IN|O><the|DT|O><MLO|NNP|ORGANIZATION><Real|NNP|ORGANIZATION><Talk|NN|ORGANIZATION><Luncheon|NNP|ORGANIZATION><.|.|O>");



    }

    @Test
    public void deleteventTest() {
        Event temp = new Event(
                "need update",
                "what",
                "need update",
                "<What|WP|O><are|VBP|O><you|PRP|O><doing|VBG|O><for|IN|O><lunch|NN|O><next|JJ|DATE><Wednesday|NNP|DATE><April|NNP|DATE><3rd|VBD|DATE><@|SYM|O><12:10-1pm|CD|TIME><?|.|O>",
                "<SAVE|VB|O><THE|DT|O><DATE|NN|O><for|IN|O><the|DT|O><MLO|NNP|ORGANIZATION><Real|NNP|ORGANIZATION><Talk|NN|ORGANIZATION><Luncheon|NNP|ORGANIZATION><.|.|O><SAVE|VB|O><THE|DT|O><DATE|NN|O><for|IN|O><the|DT|O><MLO|NNP|ORGANIZATION><Real|NNP|ORGANIZATION><Talk|NN|ORGANIZATION><Luncheon|NNP|ORGANIZATION><.|.|O><SAVE|VB|O><THE|DT|O><DATE|NN|O><for|IN|O><the|DT|O><MLO|NNP|ORGANIZATION><Real|NNP|ORGANIZATION><Talk|NN|ORGANIZATION><Luncheon|NNP|ORGANIZATION><.|.|O><SAVE|VB|O><THE|DT|O><DATE|NN|O><for|IN|O><the|DT|O><MLO|NNP|ORGANIZATION><Real|NNP|ORGANIZATION><Talk|NN|ORGANIZATION><Luncheon|NNP|ORGANIZATION><.|.|O>");


    }
}
