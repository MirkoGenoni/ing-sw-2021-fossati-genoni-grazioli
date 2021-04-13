package it.polimi.ingsw.Model.Market;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the marble enum class
 *
 * @author Stefano Fossati
 */

public class MarbleTest {

    @Before
    public void setUp() throws Exception {
    }

    /**
     * This test verifies the right return of the method getType
     */
    @Test
    public void getType() {
        Marble nothing = Marble.NOTHING;
        assertEquals("nothing", nothing.getType());
        Marble shield = Marble.SHIELD;
        assertEquals("shield", shield.getType());
        Marble servant = Marble.SERVANT;
        assertEquals("servant", servant.getType());
        Marble stone = Marble.STONE;
        assertEquals("stone", stone.getType());
        Marble coin = Marble.COIN;
        assertEquals("coin", coin.getType());
        Marble faith = Marble.FAITH;
        assertEquals("faith", faith.getType());
    }
}