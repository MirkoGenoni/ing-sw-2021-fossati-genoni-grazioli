package it.polimi.ingsw.Model.LeaderCard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class tests the LeaderCardHandler class.
 * These test use an ArrayList of LeaderCard created from the LeaderCardsTest.json to create the LeaderCardHandler.
 *
 * @author Stefano Fossati
 */
public class LeaderCardHandlerTest {
    LeaderCardHandler testLeaderCardHandler;
    ArrayList<LeaderCard> testLeaderCards;
    SpecialAbility testSpecialAbility;
    JsonStreamParser testParser;
    Gson testGson;

    @Before
    public void setUp() throws Exception {
        testLeaderCards = new ArrayList<>();
        testGson = new GsonBuilder().create();
        testParser = new JsonStreamParser(new InputStreamReader(
                new FileInputStream("src/test/java/it/polimi/ingsw/Model/LeaderCard/LeaderCardsTest.json"), "UTF-8"));

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
        assertEquals(4, testLeaderCards.size());
        testLeaderCardHandler = new LeaderCardHandler(testLeaderCards);
    }

    /**
     * This test checks the right working of the LeaderCardException in the constructor of the LeaderCardHandler class.
     */
    @Test
    public void LeaderCardHandler(){
        ArrayList<LeaderCard> testA = new ArrayList<>();
        try {
            new LeaderCardHandler(testA);
        } catch (LeaderCardException e) {
            assertEquals( "leader card given is null", e.getMessage());
        }
    }

    /**
     * This test checks the right working of the removeInitialLeaderCard method.
     * It verifies also the exceptions of the method.
     */
    @Test
    public void removeInitialLeaderCard() throws LeaderCardException {
        try{
            testLeaderCardHandler.removeInitialLeaderCard(2,2);
        } catch (LeaderCardException e) {
            assertEquals("Invalid card selection", e.getMessage());
        }

        try{
            testLeaderCardHandler.removeInitialLeaderCard(4,6);
        }catch (LeaderCardException e){
            assertEquals("Invalid cards selection", e.getMessage());
        }

        try{
            testLeaderCardHandler.removeInitialLeaderCard(4,2);
        }catch (LeaderCardException e){
            assertEquals("Invalid first card selection", e.getMessage());
        }

        try{
            testLeaderCardHandler.removeInitialLeaderCard(2,4);
        }catch (LeaderCardException e){
            assertEquals("Invalid second card selection", e.getMessage());
        }


        testLeaderCardHandler.removeInitialLeaderCard(3,2);
        assertEquals("leaderCard1", testLeaderCardHandler.getLeaderCardsAvailable().get(0).getName());
        assertEquals("leaderCard2", testLeaderCardHandler.getLeaderCardsAvailable().get(1).getName());

    }

    /**
     * This test checks te right working of the getter of the leader cards available.
     * It also verifies the exceptions of the method.
     */
    @Test
    public void getLeaderCardsAvailable() throws LeaderCardException {
        testLeaderCardHandler.removeInitialLeaderCard(1, 0);
        assertEquals("leaderCard3", testLeaderCardHandler.getLeaderCardsAvailable().get(0).getName());
        assertEquals("leaderCard4", testLeaderCardHandler.getLeaderCardsAvailable().get(1).getName());
        ArrayList<LeaderCard> testLeaderCardAvailable = testLeaderCardHandler.getLeaderCardsAvailable();
        assertEquals(testLeaderCardAvailable.get(0), testLeaderCardHandler.getLeaderCardsAvailable().get(0));
        assertEquals(testLeaderCardAvailable.get(1), testLeaderCardHandler.getLeaderCardsAvailable().get(1));
        testLeaderCardHandler.discardLeaderCard(1);
        testLeaderCardHandler.activateLeaderCard(0);

        try{
            testLeaderCardHandler.getLeaderCardsAvailable();
        }catch (LeaderCardException e){
            assertEquals("you haven't leader card available", e.getMessage());
        }
    }

    /**
     * This test checks te right working of the getter of the active leader cards.
     * It also verifies the exception of the method.
     */
    @Test
    public void getLeaderCardsActive() throws LeaderCardException {
        LeaderCard testLeaderCard;
        testLeaderCardHandler.removeInitialLeaderCard(0, 2);
        assertEquals("leaderCard2", testLeaderCardHandler.getLeaderCardsAvailable().get(0).getName());
        assertEquals("leaderCard4", testLeaderCardHandler.getLeaderCardsAvailable().get(1).getName());
        try{
            testLeaderCardHandler.getLeaderCardsActive();
        }catch (LeaderCardException e){
            assertEquals("you haven't leader card active", e.getMessage());
        }
        testLeaderCard = testLeaderCardHandler.getLeaderCardsAvailable().get(1);
        testLeaderCardHandler.activateLeaderCard(1);
        assertEquals(testLeaderCard, testLeaderCardHandler.getLeaderCardsActive().get(0));
        assertEquals("leaderCard4", testLeaderCardHandler.getLeaderCardsActive().get(0).getName());
        testLeaderCard = testLeaderCardHandler.getLeaderCardsAvailable().get(0);
        testLeaderCardHandler.activateLeaderCard(0);
        assertEquals(testLeaderCard, testLeaderCardHandler.getLeaderCardsActive().get(1));
        assertEquals("leaderCard2", testLeaderCardHandler.getLeaderCardsActive().get(1).getName());
    }

    /**
     * This test checks the right working of the getter of the active leader card power selected.
     * It also verifies the exception of the method.
     */
    @Test
    public void getLeaderActivePower() throws LeaderCardException {
        LeaderCard testLeaderCard;
        testLeaderCardHandler.removeInitialLeaderCard(1,2);
        assertEquals("leaderCard1", testLeaderCardHandler.getLeaderCardsAvailable().get(0).getName());
        assertEquals("leaderCard4", testLeaderCardHandler.getLeaderCardsAvailable().get(1).getName());
        try{
            testLeaderCardHandler.getLeaderCardsActive();
        }catch (LeaderCardException e){
            assertEquals("you haven't leader card active", e.getMessage());
        }
        testLeaderCard = testLeaderCardHandler.getLeaderCardsAvailable().get(0);
        testLeaderCardHandler.activateLeaderCard(0);
        assertEquals(testLeaderCard.getSpecialAbility(), testLeaderCardHandler.getLeaderActivePower(0));
        testLeaderCard = testLeaderCardHandler.getLeaderCardsAvailable().get(0);
        testLeaderCardHandler.activateLeaderCard(0);
        assertEquals(testLeaderCard.getSpecialAbility(), testLeaderCardHandler.getLeaderActivePower(1));
        assertEquals("additionalProduction", testLeaderCardHandler.getLeaderActivePower(0).getEffect());
        assertEquals("marketWhiteChange", testLeaderCardHandler.getLeaderActivePower(1).getEffect());
        try{
            testLeaderCardHandler.getLeaderActivePower(4);
        }catch (LeaderCardException e){
            assertEquals("Invalid active leader card selection", e.getMessage());
        }
    }

    /**
     * This test checks the right working of the method activate leader power.
     * It also verifies the exception of the method.
     */
    @Test
    public void activateLeaderCard() throws LeaderCardException {
        LeaderCard testLeaderCard;
        testLeaderCard = testLeaderCardHandler.getLeaderCardsAvailable().get(3);
        testLeaderCardHandler.activateLeaderCard(3);
        assertEquals(testLeaderCard, testLeaderCardHandler.getLeaderCardsActive().get(0));
        assertEquals("leaderCard4", testLeaderCardHandler.getLeaderCardsActive().get(0).getName());
        testLeaderCard = testLeaderCardHandler.getLeaderCardsAvailable().get(0);
        testLeaderCardHandler.activateLeaderCard(0);
        assertEquals(testLeaderCard, testLeaderCardHandler.getLeaderCardsActive().get(1));
        assertEquals("leaderCard1", testLeaderCardHandler.getLeaderCardsActive().get(1).getName());
        try{
            testLeaderCardHandler.activateLeaderCard(3);
        }catch (LeaderCardException e){
            assertEquals("Invalid available leader card selection", e.getMessage());
        }
        testLeaderCard = testLeaderCardHandler.getLeaderCardsAvailable().get(1);
        testLeaderCardHandler.activateLeaderCard(1);
        assertEquals(testLeaderCard, testLeaderCardHandler.getLeaderCardsActive().get(2));
        assertEquals("leaderCard3", testLeaderCardHandler.getLeaderCardsActive().get(2).getName());
        testLeaderCard = testLeaderCardHandler.getLeaderCardsAvailable().get(0);
        testLeaderCardHandler.activateLeaderCard(0);
        assertEquals(testLeaderCard, testLeaderCardHandler.getLeaderCardsActive().get(3));
        assertEquals("leaderCard2", testLeaderCardHandler.getLeaderCardsActive().get(3).getName());
        try{
            testLeaderCardHandler.activateLeaderCard(0);
        }catch (LeaderCardException e){
            assertEquals("Invalid available leader card selection", e.getMessage());
        }

    }

    /**
     * This test checks the right working of the method discardLeaderCard.
     * It verifies also the exception of the method.
     */
    @Test
    public void discardLeaderCard() throws LeaderCardException {
        testLeaderCardHandler.removeInitialLeaderCard(0,3);
        assertEquals("leaderCard2", testLeaderCardHandler.getLeaderCardsAvailable().get(0).getName());
        assertEquals("leaderCard3", testLeaderCardHandler.getLeaderCardsAvailable().get(1).getName());
        try{
            testLeaderCardHandler.discardLeaderCard(3);
        }catch (LeaderCardException e){
            assertEquals("Invalid available leader card selection, card selected isn't available", e.getMessage());
        }
        testLeaderCardHandler.discardLeaderCard(1);
        assertEquals(1, testLeaderCardHandler.getLeaderCardsAvailable().size());
        assertEquals("leaderCard2", testLeaderCardHandler.getLeaderCardsAvailable().get(0).getName());
        testLeaderCardHandler.discardLeaderCard(0);
        try{
            testLeaderCardHandler.getLeaderCardsAvailable();
        }catch (LeaderCardException e){
            assertEquals("you haven't leader card available", e.getMessage());
        }
        try {
            testLeaderCardHandler.getLeaderCardsActive();
        }catch (LeaderCardException e){
            assertEquals("you haven't leader card active", e.getMessage());
        }
    }
}