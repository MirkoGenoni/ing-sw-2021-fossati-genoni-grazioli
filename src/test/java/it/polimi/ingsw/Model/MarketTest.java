package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Resource.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Class MarketTest tests Market class.
 *
 * @author Stefano Fossati
 */


public class MarketTest {

    Market testMarket;
    Resource testOutMarble;
    Resource[] testRow2;     // test of the row number 2 (line 2) like the scheme in class Market
    Resource[] testCol3;     // test of the column number 3 (line 6) like the scheme in the class Market
    ArrayList<Resource> testLineChoose;


    @Before
    public void setUp() throws Exception{
        testMarket = new Market();
        testLineChoose = new ArrayList<Resource>();
        testRow2 = new Resource[4];
        testCol3 = new Resource[3];
    }

    /**
     * the test check the right movement of the marble and the ArrayList of Resource that the method chooseLine return
     * @throws Exception
     */

    @Test
    public void testChooseLine() throws Exception {

        // this part tests the row 2 (line 2) fo the Market grid
        testOutMarble = testMarket.getOutMarble();
        for(int i=0; i<4; i++){
            testRow2[i] = testMarket.getGrid().get(i+4);
        }
        testLineChoose = testMarket.chooseLine(2);

        assertEquals(testMarket.getGrid().get(4), testOutMarble);
        for(int i=1; i<4; i++){
            assertEquals(testMarket.getGrid().get(4+i), testRow2[i-1]);
        }
        assertEquals(testMarket.getOutMarble(), testRow2[3]);

        for(int i=0; i<4; i++){
            assertEquals(testLineChoose.get(i).getMaterialType(), testRow2[i].getMaterialType());
        }

        // this part tests the column 3 (line 6) fo the Market grid
        testOutMarble = testMarket.getOutMarble();
        for(int i=0; i<3; i++){
            testCol3[i] = testMarket.getGrid().get(2+4*i);
        }

        testLineChoose = testMarket.chooseLine(6);
        for(int i=1; i<3; i++){
            assertEquals(testMarket.getGrid().get(2+4*i), testCol3[i-1]);
        }
        assertEquals(testMarket.getOutMarble(), testCol3[2]);

        for(int i=0; i<3; i++){
            assertEquals(testLineChoose.get(i).getMaterialType(), testCol3[i].getMaterialType());
        }

    }

    @Test
    public void testGetGrid() {

    }

    @Test
    public void testGetOutMarble() {
    }
}
