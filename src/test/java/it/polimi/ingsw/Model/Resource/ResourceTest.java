package it.polimi.ingsw.Model.Resource;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceTest {

    /**
     * This test creates every resource using their constructors and then verifies if the resource built returns the
     * correct string
     */
    @Test
    public void testGetMaterialType() {
        Resource nothing = new Nothing();
        assertEquals("nothing", nothing.getMaterialType());
        Resource coin = new Coin();
        assertEquals("coin", coin.getMaterialType());
        Resource faith = new Faith();
        assertEquals("faith", faith.getMaterialType());
        Resource servant = new Servant();
        assertEquals("servant", servant.getMaterialType());
        Resource shield = new Shield();
        assertEquals("shield", shield.getMaterialType());
        Resource stone = new Stone();
        assertEquals("stone", stone.getMaterialType());
    }
}