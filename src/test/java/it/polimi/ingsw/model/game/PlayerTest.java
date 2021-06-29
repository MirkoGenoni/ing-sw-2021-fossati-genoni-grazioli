package it.polimi.ingsw.model.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import it.polimi.ingsw.model.exceptions.LeaderCardException;
import it.polimi.ingsw.model.exceptions.StartGameException;
import it.polimi.ingsw.model.leaderCard.*;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {

    Player davidePlayer = new Player("Davide");
    Player stePlayer = new Player("Stefano");
    Player mirkoPlayer = new Player("Mirko");

    ArrayList<LeaderCard> testLeaderCards = new ArrayList<>();
    SpecialAbility testSpecialAbility;
    JsonStreamParser testParser;
    Gson testGson;


    @Before
    public void setUp() {

        testGson = new GsonBuilder().create();
        try {
            testParser = new JsonStreamParser(new InputStreamReader(
                    new FileInputStream("src/test/java/it/polimi/ingsw/Model/LeaderCard/LeaderCardsTest.json"), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        while (testParser.hasNext()){
            JsonElement testElem = testParser.next();
            if(testElem.isJsonObject()){
                String testName = testElem.getAsJsonObject().get("name").getAsString();
                String testNameSpecialAbility = testElem.getAsJsonObject().get("specialAbility").getAsJsonObject().get("effect").getAsString();
                switch (testNameSpecialAbility) {
                    case "additionalProduction": {
                        testSpecialAbility = testGson.fromJson(testElem.getAsJsonObject().get("specialAbility"), AdditionalProduction.class);
                        testLeaderCards.add(new LeaderCard(testName, testSpecialAbility));
                        break;
                    }
                    case "biggerDeposit": {
                        testSpecialAbility = testGson.fromJson(testElem.getAsJsonObject().get("specialAbility"), BiggerDeposit.class);
                        testLeaderCards.add(new LeaderCard(testName, testSpecialAbility));
                        break;
                    }
                    case "costLess": {
                        testSpecialAbility = testGson.fromJson(testElem.getAsJsonObject().get("specialAbility"), CostLess.class);
                        testLeaderCards.add(new LeaderCard(testName, testSpecialAbility));
                        break;
                    }
                    case "marketWhiteChange": {
                        testSpecialAbility = testGson.fromJson(testElem.getAsJsonObject().get("specialAbility"), MarketWhiteChange.class);
                        testLeaderCards.add(new LeaderCard(testName, testSpecialAbility));
                        break;
                    }
                }
            }
        }
    }


    @Test
    public void getName() {
        assertEquals(davidePlayer.getName(), "Davide");
        assertEquals(stePlayer.getName(), "Stefano");
        assertEquals(mirkoPlayer.getName(), "Mirko");
    }

    @Test
    public void getPlayerBoard() {
        try {
            davidePlayer.createGameBoard(testLeaderCards); //valid array
        } catch (StartGameException e) {
            fail();
        }
        assertNotNull(davidePlayer.getPlayerBoard());
        try {
            assertEquals(davidePlayer.getPlayerBoard().getLeaderCardHandler().getLeaderCardsAvailable().size(), testLeaderCards.size());

        } catch (LeaderCardException e) {
            fail();
        }


        ArrayList<LeaderCard> emptyArray = new ArrayList<>();
        try {
            stePlayer.createGameBoard(emptyArray);
        } catch (StartGameException e) {
            assertEquals("leader card given is null", e.getMessage());
        }

        ArrayList<LeaderCard> nullArray = null;
        try {
            mirkoPlayer.createGameBoard(null);
        } catch (StartGameException e) {
            assertEquals("leader card given is null", e.getMessage());
        }


    }

}