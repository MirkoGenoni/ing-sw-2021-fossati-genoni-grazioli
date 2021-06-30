package it.polimi.ingsw.model.developmentcard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import it.polimi.ingsw.model.resource.Resource;
import org.junit.Before;
import org.junit.Test;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * This class tests the DevelopmentCard class.
 * These test uses an ArrayList of DevelopmentCards created from the DevelopmentCardTest.json to create the DevelopmentCard.
 * This tests check if the parsing of the card from Json happen correctly
 * @author davide grazioli
 */
public class DevelopmentCardTest {

DevelopmentCard testCard = null;

    @Before
    public void setUp() {

        Gson testGson = new GsonBuilder().create();
        JsonStreamParser testParser;
        try {
            testParser = new JsonStreamParser(new InputStreamReader(new FileInputStream
                    ("src/test/java/it/polimi/ingsw/Model/DevelopmentCard/DevelopmentCardsTest.json"), "UTF-8"));

                JsonElement testElem = testParser.next();
                if (testElem.isJsonObject()) {
                    testCard = testGson.fromJson(testElem, DevelopmentCard.class);
                }

        }
        catch (FileNotFoundException e) {
            fail(); //test Failed
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            fail();
        }

    }
    /**
     * This test checks the right behaviour of the getCardID method.
     */
    @Test
    public void getCardID() {assertEquals("DevG_11", testCard.getCardID());
    }
    /**
     * This test checks the right behaviour of the getColor method.
     */
    @Test
    public void getColor() {
        assertEquals(testCard.getColor(), CardColor.GREEN);
    }
    /**
     * This test checks the right behaviour of the getLevel method.
     */
    @Test
    public void getLevel() {
        assertEquals(testCard.getLevel(), 1);
    }
    /**
     * This test checks the right behaviour of the getCost method.
     */
    @Test
    public void getCost() {
        Map<Resource, Integer> testCost = testCard.getCost();
        assertEquals(testCost.toString(), "{SHIELD=2}");
    }
    /**
     * This test checks the right behaviour of the getVictorypoint method.
     */
    @Test
    public void getVictorypoint() {
        assertEquals(testCard.getVictoryPoint(),1);

    }
    /**
     * This test checks the right behaviour of the getMaterialRequired method checking if the map it's correct
     */
    @Test
    public void getMaterialRequired() {
        Map<Resource, Integer> testCost = testCard.getMaterialRequired();
        assertEquals(testCost.toString(), "{COIN=1}");
    }
    /**
     * This test checks the right behaviour of the getProductionResult method checking if the map it's correct
     */
    @Test
    public void getProductionResult() {

        Map<ProductedMaterials, Integer> testCost = testCard.getProductionResult();
        assertEquals(testCost.toString(), "{FAITHPOINT=1}");
    }


}