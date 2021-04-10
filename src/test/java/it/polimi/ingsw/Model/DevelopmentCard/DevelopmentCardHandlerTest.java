package it.polimi.ingsw.Model.DevelopmentCard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import it.polimi.ingsw.Model.Exceptions.DevelopmentCardException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static org.junit.Assert.*;

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

    @Test
    public void getActiveDevelopmentCard() {
        ArrayList <DevelopmentCard> actives = handler.getActiveDevelopmentCard();

        assertEquals(actives.get(0), cardCollection.get(0));
        assertNull(actives.get(1)); //if i don't have an active card it returns null in that position
        assertEquals(actives.get(2), cardCollection.get(1));
        assertEquals(actives.size(),3);
    }

    @Test
    public void getDevelopmentCardColl() {
        ArrayList <DevelopmentCard> collection = handler.getDevelopmentCardColl();

        assertEquals(2, collection.size());
        assertEquals(collection.get(0), cardCollection.get(0));
        assertEquals(collection.get(1), cardCollection.get(1));

    }

    @Test
    public void setActiveDevelopmentCard() {
        try {
            handler.setActiveDevelopmentCard(cardCollection.get(2), 2);
            assertEquals(handler.getActiveDevelopmentCard().get(2), cardCollection.get(2));
        }
        catch (DevelopmentCardException e){
            fail();
        }
    }

    @Test
    public void CheckBoughtable() {
        int position;
        ArrayList<Integer> bouthablePositions = handler.checkBoughtable(1);
        position = bouthablePositions.get(0);
        assertEquals(position,1);

        //handler.setActiveDevelopmentCard(cardCollection.get(2),2); //level 1 in every position
        //bouthablePositions = handler.checkBoughtable(1); //can't buy an other card

    }

    @Test
    public void checkDevelopmentCard() throws Exception{
        ArrayList<CardColor> colors = new ArrayList<>();
        ArrayList<Integer> levels = new ArrayList<>();
        colors.add(CardColor.GREEN);
        colors.add(CardColor.PURPLE);
        levels.add(1);
        levels.add(1);

        assertTrue(handler.checkDevelopmentCard(colors, levels));

        colors.add(CardColor.BLUE);
        levels.add(3);
        assertFalse(handler.checkDevelopmentCard(colors,levels));

        handler.setActiveDevelopmentCard(cardCollection.get(5), 1);
        assertTrue(handler.checkDevelopmentCard(colors,levels));

        colors.clear();
        levels.clear();
        colors.add(CardColor.GREEN);
        colors.add(CardColor.GREEN);
        levels.add(1);
        levels.add(1);
        assertFalse(handler.checkDevelopmentCard(colors,levels)); //duplicates doesn't count

    }
}