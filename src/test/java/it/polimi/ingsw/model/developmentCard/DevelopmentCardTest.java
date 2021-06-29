package it.polimi.ingsw.model.developmentCard;

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

    @Test
    public void getCardID() {assertEquals("DevG_11", testCard.getCardID());
    }

    @Test
    public void getColor() {
        assertEquals(testCard.getColor(), CardColor.GREEN);
    }

    @Test
    public void getLevel() {
        assertEquals(testCard.getLevel(), 1);
    }

    @Test
    public void getCost() {
        Map<Resource, Integer> testCost = testCard.getCost();
        assertEquals(testCost.toString(), "{SHIELD=2}");
    }

    @Test
    public void getVictorypoint() {
        assertEquals(testCard.getVictoryPoint(),1);

    }

    @Test
    public void getMaterialRequired() {
        Map<Resource, Integer> testCost = testCard.getMaterialRequired();
        assertEquals(testCost.toString(), "{COIN=1}");
    }

    @Test
    public void getProductionResult() {

        Map<ProductedMaterials, Integer> testCost = testCard.getProductionResult();
        assertEquals(testCost.toString(), "{FAITHPOINT=1}");
    }


}