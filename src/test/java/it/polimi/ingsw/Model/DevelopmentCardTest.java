package it.polimi.ingsw.Model;

import org.junit.Before;
import org.junit.Test;


import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.*;

public class DevelopmentCardTest {

    String testColor = "green";
    int testLevel = 3;
    Map<String, Integer> testCost = new HashMap<>();
    int testVictoryPoint = 8;
    Map<String, Integer> testMaterialRequired = new HashMap<>();
    Map<String, Integer> testProductionResult = new HashMap<>();
    DevelopmentCard provaCarta;


    @Before
    public void setUp(){
        testCost.put("coin", 4);
        testCost.put("stone", 7);

        testMaterialRequired.put("coin",5);
        testMaterialRequired.put("servant", 6);
        testMaterialRequired.put("shield", 1);

        testProductionResult.put("stone", 4);
        testProductionResult.put("faithPoint", 2);

        provaCarta = new DevelopmentCard(testColor,testLevel,testCost,testVictoryPoint,
                                            testMaterialRequired,testProductionResult);

    }

    @Test
    public void getColor() {
        assertEquals(provaCarta.getColor(), testColor);
    }

    @Test
    public void getLevel() {
        assertEquals(provaCarta.getLevel(), testLevel);
    }

    @Test
    public void getCost() {
        Set <String> allKeys = provaCarta.getCost().keySet(); //give all the keys of provaCarta

        for (String s : allKeys){
            assertEquals(testCost.get(s), provaCarta.getCost().get(s));
        }

        allKeys = testCost.keySet(); //give all the keys of Test

        for (String s : allKeys){
            assertEquals(testCost.get(s), provaCarta.getCost().get(s));
        }

    }

    @Test
    public void getVictorypoint() {
        assertEquals(provaCarta.getVictoryPoint(), testVictoryPoint);
    }

    @Test
    public void getMaterialRequired() {

        Set <String> allKeys = provaCarta.getMaterialRequired().keySet(); //give all the keys of provaCarta

        for (String s : allKeys){
            assertEquals(testMaterialRequired.get(s), provaCarta.getMaterialRequired().get(s));
        }

        allKeys = testMaterialRequired.keySet(); //give all the keys of Test

        for (String s : allKeys){
            assertEquals(testMaterialRequired.get(s), provaCarta.getMaterialRequired().get(s));
        }

    }

    @Test
    public void getProductionResult() {
        Set <String> allKeys = provaCarta.getProductionResult().keySet(); //give all the keys of provaCarta

        for (String s : allKeys){
            assertEquals(testProductionResult.get(s), provaCarta.getProductionResult().get(s));
        }

        allKeys = testProductionResult.keySet(); //give all the keys of Test

        for (String s : allKeys){
            assertEquals(testProductionResult.get(s), provaCarta.getProductionResult().get(s));
        }
    }
}