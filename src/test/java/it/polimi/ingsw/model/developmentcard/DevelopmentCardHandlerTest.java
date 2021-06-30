package it.polimi.ingsw.model.developmentcard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import it.polimi.ingsw.model.exceptions.DevelopmentCardException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * this class tests the development card handler and its behaviour
 * These test uses an ArrayList of DevelopmentCards created from the DevelopmentCardTest.json to create the DevelopmentCard.
 * @author davide grazioli
 */
public class DevelopmentCardHandlerTest {

    DevelopmentCardHandler handler = new DevelopmentCardHandler();
    ArrayList<DevelopmentCard> cardCollection = new ArrayList<>();
    DevelopmentCard testCard;

    @Before
    public void setUp() throws Exception {

        Gson testGson = new GsonBuilder().create();
        JsonStreamParser testParser;
        try {
            testParser = new JsonStreamParser(new InputStreamReader(new FileInputStream
                    ("src/test/java/it/polimi/ingsw/Model/DevelopmentCard/DevelopmentCardsTest.json"), "UTF-8"));

            while (testParser.hasNext()) {
                JsonElement testElem = testParser.next();
                if (testElem.isJsonObject()) {
                    testCard = testGson.fromJson(testElem, DevelopmentCard.class);
                }
                cardCollection.add(testCard);
            }
        }
        catch (FileNotFoundException e) {
            fail(); //test Failed
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            fail();
        }

        handler.setActiveDevelopmentCard(cardCollection.get(0),0);
        handler.setActiveDevelopmentCard(cardCollection.get(1),2);

    }
    /**
     * This test checks the right behaviour of the getActiveDevelopmentCard method.
     */
    @Test
    public void getActiveDevelopmentCard() {
        ArrayList <DevelopmentCard> actives = handler.getActiveDevelopmentCard();

        assertEquals(actives.get(0), cardCollection.get(0));
        assertNull(actives.get(1)); //if i don't have an active card it returns null in that position
        assertEquals(actives.get(2), cardCollection.get(1));
        assertEquals(actives.size(),3);

    }
    /**
     * This test checks the right behaviour of the getDevelopmentCardCollection method.
     * It verifies also the exceptions of the method.
     */
    @Test
    public void getDevelopmentCardColl() {
        ArrayList <DevelopmentCard> collection = handler.getDevelopmentCardColl();

        assertEquals(2, collection.size());
        assertEquals(collection.get(0), cardCollection.get(0));
        assertEquals(collection.get(1), cardCollection.get(1));

    }
    /**
     * This test checks the right behaviour of the setActiveDevelopmentCards method.
     * It verifies also the exceptions of the method.
     */
    @Test
    public void setActiveDevelopmentCard() {
        try {
            handler.setActiveDevelopmentCard(cardCollection.get(2), 2);
            assertEquals(handler.getActiveDevelopmentCard().get(2), cardCollection.get(2));

            handler.setActiveDevelopmentCard(cardCollection.get(3),4); //non valid position
        }
        catch (DevelopmentCardException e){
            assertEquals(e.getMessage(), "Can't add a card out of gameboard spaces");
        }
    }

    /**
     * This test checks the right behaviour of the CheckBoughtable method verifing if a player could buy a card
     * It verifies also the exceptions of the method.
     */
    @Test
    public void CheckBoughtable(){

        ArrayList<Boolean> checkReturned = handler.checkBoughtable(cardCollection.get(2).getLevel()); //level = 1
        assertFalse(checkReturned.get(0));
        assertTrue(checkReturned.get(1)); //can put level 1 card only in the first position
        assertFalse(checkReturned.get(2));

        try {

            handler.setActiveDevelopmentCard(cardCollection.get(2), 1);
            checkReturned = handler.checkBoughtable(cardCollection.get(3).getLevel()); //level = 1
            for (Boolean condition : checkReturned)
                assertFalse(condition); //can't buy an other level 1 card

            checkReturned = handler.checkBoughtable(cardCollection.get(4).getLevel()); //level = 2
            for (Boolean condition : checkReturned)
                assertTrue(condition); //can put a level 2 card everywhere


            handler.setActiveDevelopmentCard(cardCollection.get(4), 2); //set level 2 card active
            checkReturned = handler.checkBoughtable(cardCollection.get(5).getLevel()); //level = 3
            assertFalse(checkReturned.get(0));
            assertFalse(checkReturned.get(1));
            assertTrue(checkReturned.get(2));//can put level 3 card only in the third position
        }catch (DevelopmentCardException e){
            fail();
        }

    }
    /**
     * This test checks the right behaviour of the checkDevelopmentCard method.
     * It verifies also the exceptions of the method.
     */
    @Test
    public void checkDevelopmentCard(){
        ArrayList<CardColor> colors = new ArrayList<>();
        ArrayList<Integer> levels = new ArrayList<>();

        try {
            colors.add(CardColor.GREEN);
            colors.add(CardColor.PURPLE);
            levels.add(1);
            levels.add(1);

            assertTrue(handler.checkDevelopmentCard(colors, levels));

            colors.add(CardColor.BLUE);
            levels.add(3);
            assertFalse(handler.checkDevelopmentCard(colors, levels));

            handler.setActiveDevelopmentCard(cardCollection.get(5), 1);
            assertTrue(handler.checkDevelopmentCard(colors, levels));

            colors.clear();
            levels.clear();
            colors.add(CardColor.GREEN);
            colors.add(CardColor.GREEN);
            levels.add(1);
            levels.add(1);
            assertFalse(handler.checkDevelopmentCard(colors, levels)); //duplicates doesn't count

        }catch (DevelopmentCardException e){
            fail(); //impossible
        }


        try{
            handler.checkDevelopmentCard(null,null);
        }catch (DevelopmentCardException e){
            assertEquals(e.getMessage(), "Can't process check because of formatting error");
        }

        try{
            colors.clear();
            levels.clear();
            colors.add(CardColor.BLUE);
            levels.add(1);
            levels.add(2);
            handler.checkDevelopmentCard(colors,levels);
        }catch (DevelopmentCardException e){
            assertEquals(e.getMessage(), "Can't process check because of formatting error");
        }

    }
    /**
     * This test checks the right behaviour of the checkCountDevelopmentCard method verifying if return the correct
     * amount of developmentCards
     */
    @Test
    public void checkCountDevelopmentCard() {
        try {
            //1 green & 1 purple already activated in SetUp
            handler.setActiveDevelopmentCard(cardCollection.get(4), 2);//green
            handler.setActiveDevelopmentCard(cardCollection.get(5),1);//blue
            handler.setActiveDevelopmentCard(cardCollection.get(2), 1); //blue
            handler.setActiveDevelopmentCard(cardCollection.get(3),0); //yellow

            assertEquals(2, handler.checkCountDevelopmentCard(CardColor.GREEN));
            assertEquals(2,handler.checkCountDevelopmentCard(CardColor.BLUE));
            assertEquals(1,handler.checkCountDevelopmentCard(CardColor.PURPLE));
            assertEquals(1,handler.checkCountDevelopmentCard(CardColor.YELLOW));



        } catch (DevelopmentCardException e) {
            e.printStackTrace();
            fail();
        }

    }
    /**
     * This test checks the right behaviour of the checkCountDevelopmentCard method verifying the
     * amount of developmentCards
     */
    @Test
    public void checkCountDevelopmentCard1() { //check with also quantity in input, return boolean
        try {
            //1 green & 1 purple already activated in SetUp
            handler.setActiveDevelopmentCard(cardCollection.get(4), 2);//green
            handler.setActiveDevelopmentCard(cardCollection.get(5),1);//blue
            handler.setActiveDevelopmentCard(cardCollection.get(2), 1); //blue
            handler.setActiveDevelopmentCard(cardCollection.get(3),0); //yellow
            //total: green 2 || blue 2 || purple 1 || yellow 1

            assertTrue(handler.checkCountDevelopmentCard(CardColor.GREEN,2));
            assertTrue(handler.checkCountDevelopmentCard(CardColor.GREEN,1)); //true because has at least 1 card
            assertTrue(handler.checkCountDevelopmentCard(CardColor.BLUE,2));
            assertTrue(handler.checkCountDevelopmentCard(CardColor.BLUE,1)); //true because has at least 1 card
            assertTrue(handler.checkCountDevelopmentCard(CardColor.PURPLE,1));
            assertTrue(handler.checkCountDevelopmentCard(CardColor.YELLOW,1));
            assertFalse(handler.checkCountDevelopmentCard(CardColor.YELLOW,2));
            assertFalse(handler.checkCountDevelopmentCard(CardColor.GREEN,3));


        } catch (DevelopmentCardException e) {
            e.printStackTrace();
            fail();
        }
    }
}