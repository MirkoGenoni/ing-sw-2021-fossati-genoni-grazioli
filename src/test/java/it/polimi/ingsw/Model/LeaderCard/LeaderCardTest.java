package it.polimi.ingsw.Model.LeaderCard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import it.polimi.ingsw.Model.Resource.Resource;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class tests the LeaderCard class.
 * These test use an ArrayList of LeaderCard created from the LeaderCardsTest.json
 *
 * @author Stefano Fossati
 */
public class LeaderCardTest {
    ArrayList<LeaderCard> testLeaderCards;
    SpecialAbility testSpecialAbilityMarketWhiteChange;
    SpecialAbility testSpecialAbilityCostLess;
    SpecialAbility testSpecialAbilityAdditionalProduction;
    SpecialAbility testSpecialAbilityBiggerDeposit;
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
                        testSpecialAbilityAdditionalProduction = testGson.fromJson(testElem.getAsJsonObject().get("specialAbility"), AdditionalProduction.class);
                        testLeaderCards.add(new LeaderCard(testName, testSpecialAbilityAdditionalProduction));
                        break;
                    }
                    case "biggerDeposit": {
                        testSpecialAbilityBiggerDeposit = testGson.fromJson(testElem.getAsJsonObject().get("specialAbility"), BiggerDeposit.class);
                        testLeaderCards.add(new LeaderCard(testName, testSpecialAbilityBiggerDeposit));
                        break;
                    }
                    case "costLess": {
                        testSpecialAbilityCostLess = testGson.fromJson(testElem.getAsJsonObject().get("specialAbility"), CostLess.class);
                        testLeaderCards.add(new LeaderCard(testName, testSpecialAbilityCostLess));
                        break;
                    }
                    case "marketWhiteChange": {
                        testSpecialAbilityMarketWhiteChange = testGson.fromJson(testElem.getAsJsonObject().get("specialAbility"), MarketWhiteChange.class);
                        testLeaderCards.add(new LeaderCard(testName, testSpecialAbilityMarketWhiteChange));
                        break;
                    }
                }
            }
        }
        assertEquals(4, testLeaderCards.size());
    }

    /**
     * This tests checks the right return of the method getName of the LeaderCard class.
     */
    @Test
    public void getName() {
        assertEquals("leaderCard1", testLeaderCards.get(0).getName());
        assertEquals("leaderCard2", testLeaderCards.get(1).getName());
        assertEquals("leaderCard3", testLeaderCards.get(2).getName());
        assertEquals("leaderCard4", testLeaderCards.get(3).getName());
    }

    /**
     * This tests checks the right return of the method getSpecialAbility of the LeaderCard class.
     */
    @Test
    public void getSpecialAbility() {
        assertEquals(testSpecialAbilityAdditionalProduction, testLeaderCards.get(0).getSpecialAbility());
        assertEquals(testSpecialAbilityBiggerDeposit, testLeaderCards.get(1).getSpecialAbility());
        assertEquals(testSpecialAbilityCostLess, testLeaderCards.get(2).getSpecialAbility());
        assertEquals(testSpecialAbilityMarketWhiteChange, testLeaderCards.get(3).getSpecialAbility());
    }

    @Test
    public void testGetEffect(){
        assertEquals("additionalProduction", testLeaderCards.get(0).getSpecialAbility().getEffect());
        assertEquals("biggerDeposit", testLeaderCards.get(1).getSpecialAbility().getEffect());
        assertEquals("costLess", testLeaderCards.get(2).getSpecialAbility().getEffect());
        assertEquals("marketWhiteChange", testLeaderCards.get(3).getSpecialAbility().getEffect());
    }

    /**
     * This tests checks the right return of the method getVictoryPoint of the SpecialAbility of the LeaderCard class.
     */
    @Test
    public void testVictoryPoints(){
        assertEquals(4, testLeaderCards.get(0).getSpecialAbility().getVictoryPoints());
        assertEquals(3, testLeaderCards.get(1).getSpecialAbility().getVictoryPoints());
        assertEquals(2, testLeaderCards.get(2).getSpecialAbility().getVictoryPoints());
        assertEquals(5, testLeaderCards.get(3).getSpecialAbility().getVictoryPoints());
    }

    /**
     * This tests checks the right return of the method getVictoryPoint of the SpecialAbility of the LeaderCard class.
     */
    @Test
    public void testGetRequirements(){
        ArrayList<String> testArrayList = new ArrayList<>();
        testArrayList.add("BLUE");
        assertEquals(testArrayList, testLeaderCards.get(0).getSpecialAbility().getRequirements());
        testArrayList.clear();
        testArrayList.add("SHIELD");
        assertEquals(testArrayList, testLeaderCards.get(1).getSpecialAbility().getRequirements());
        testArrayList.clear();
        testArrayList.add("GREEN");
        testArrayList.add("BLUE");
        assertEquals(testArrayList, testLeaderCards.get(2).getSpecialAbility().getRequirements());
        testArrayList.clear();
        testArrayList.add("PURPLE");
        testArrayList.add("PURPLE");
        testArrayList.add("GREEN");
        assertEquals(testArrayList, testLeaderCards.get(3).getSpecialAbility().getRequirements());
    }

    /**
     * This tests checks the right return of the method getMaterialType of the SpecialAbility of the LeaderCard class.
     */
    @Test
    public void testGetMaterialType(){
        assertEquals(Resource.SERVANT, testLeaderCards.get(0).getSpecialAbility().getMaterialType());
        assertEquals(Resource.COIN, testLeaderCards.get(1).getSpecialAbility().getMaterialType());
        assertEquals(Resource.STONE, testLeaderCards.get(2).getSpecialAbility().getMaterialType());
        assertEquals(Resource.COIN, testLeaderCards.get(3).getSpecialAbility().getMaterialType());
    }
}