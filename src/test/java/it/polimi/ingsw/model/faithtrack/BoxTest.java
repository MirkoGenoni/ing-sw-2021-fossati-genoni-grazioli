package it.polimi.ingsw.model.faithtrack;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * This class tests the Box class.
 * These tests test the correct initialize of the boxes that compose the FaithTrack
 * @author davide grazioli
 */
public class BoxTest {

    /**
     * This test checks the right behaviour of the testGetPointPos method.
     */
    @Test
    public void testGetPointPos() {
        Box myBox = null;
        myBox = new Box(5);

        assertEquals(5, myBox.getPointPosition());
    }
    /**
     * This test checks the right behaviour of the testGetPopeSpace method.
     */
    @Test
    public void testGetPopeSpace() {
        Box myBox = null;
        myBox = new Box(3);

        assertEquals(false, myBox.getPopeSpace());
        myBox.setPopeSpace(true);
        assertEquals(true, myBox.getPopeSpace());

    }
    /**
     * This test checks the right behaviour of the testSetPopeSpace method.
     */
    @Test
    public void testSetPopeSpace() {
        Box myBox = null;
        myBox = new Box(5);
        myBox.setPopeSpace(true);

        assertEquals(true, myBox.getPopeSpace());
    }
}