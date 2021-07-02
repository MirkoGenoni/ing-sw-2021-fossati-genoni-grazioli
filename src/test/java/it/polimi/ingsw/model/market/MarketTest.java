package it.polimi.ingsw.model.market;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class tests Market class.
 *
 * @author Stefano Fossati
 */

public class MarketTest {
    Market testMarket;
    Marble testOutMarble;
    ArrayList<Marble> testLineChoose;
    Marble[] testRow2;      // test of the row number 2 (line 2) like the scheme in class Market
    Marble[] testCol3;      // test of the column number 3 (line 6) like the scheme in the class Market

    @Before
    public void setUp() throws Exception {
        testMarket = new Market();
        testLineChoose = new ArrayList<Marble>();
        testRow2 = new Marble[4];
        testCol3 = new Marble[3];
    }

    /**
     * This test checks the right movement of marbles in the grid of the market
     * and that the method returns the right elements of the line choose.
     * This test verifies the behavior of line and row;
     */

    @Test
    public void chooseLine() {
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
            assertEquals(testLineChoose.get(i).getType(), testRow2[i].getType());
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
            assertEquals(testLineChoose.get(i).getType(), testCol3[i].getType());
        }
    }
}