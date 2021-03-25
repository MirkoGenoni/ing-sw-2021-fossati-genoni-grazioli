package it.polimi.ingsw.Model.FaithTrack;

import org.junit.Test;

import static org.junit.Assert.*;
public class BoxTest {

    @Test
    public void testGetPointPos() {
        Box myBox = null;
        myBox = new Box(5);

        assertEquals(5, myBox.getPointPos());
    }

    @Test
    public void testGetPopeSpace() {
        Box myBox = null;
        myBox = new Box(3);

        assertEquals(false, myBox.getPopeSpace());
        myBox.setPopeSpace(true);
        assertEquals(true, myBox.getPopeSpace());

    }

    @Test
    public void testSetPopeSpace() {
        Box myBox = null;
        myBox = new Box(5);
        myBox.setPopeSpace(true);

        assertEquals(true, myBox.getPopeSpace());
    }
}