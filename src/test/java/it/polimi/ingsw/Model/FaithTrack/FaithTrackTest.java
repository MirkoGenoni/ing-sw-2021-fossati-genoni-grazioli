package it.polimi.ingsw.Model.FaithTrack;

import org.junit.Test;

import static org.junit.Assert.*;

public class FaithTrackTest {

    @Test
    public void testForwardPos() {
        FaithTrack testTrack = new FaithTrack(4);

        for (int i = 0; i<7; i++){
            testTrack.forwardPos(0);
        }
        for (int i = 0; i<7; i++){
            testTrack.forwardPos(3);
        }

        assertTrue(testTrack.forwardPos(0)); //return true because is popeSpace
        assertFalse(testTrack.forwardPos(3));//return false because is not popeSpace anymore

        for (int i = 0; i<7; i++){
            testTrack.forwardPos(0);
        }
        for (int i = 0; i<7; i++){
            testTrack.forwardPos(3);
        }
        assertEquals(15, testTrack.getPosition(0));
        assertTrue(testTrack.forwardPos(3));//return true because is popeSpace
        assertFalse(testTrack.forwardPos(0));//return false because is not popeSpace anymore
        assertEquals(16, testTrack.getPosition(3));

    }

    @Test
    public void testGetPosition() {
        FaithTrack testTrack = new FaithTrack(4);

        assertEquals(0, testTrack.getPosition(0));
        assertEquals(0, testTrack.getPosition(1));
        assertEquals(0, testTrack.getPosition(2));
        assertEquals(0, testTrack.getPosition(3));

        for (int i = 0; i<10; i++){
            testTrack.forwardPos(0);
        }
        for (int i = 0; i<18; i++){
            testTrack.forwardPos(1);
        }
        for (int i = 0; i<24; i++){
            testTrack.forwardPos(2);
        }
        for (int i = 0; i<5; i++){
            testTrack.forwardPos(3);
        }

        assertEquals(10, testTrack.getPosition(0));
        assertEquals(18, testTrack.getPosition(1));
        assertEquals(24, testTrack.getPosition(2));
        assertEquals(5, testTrack.getPosition(3));

    }

    @Test
    public void testCheckVaticanReportSection() {
        FaithTrack testTrack = new FaithTrack(4);

        for (int i = 0; i<4; i++){
            testTrack.forwardPos(0);
        }
        for (int i = 0; i<19; i++){
            testTrack.forwardPos(3);
        }

        assertTrue(testTrack.checkPlayerInVaticanReportSection(0,0));//return true because is in that vaticanSection
        assertFalse(testTrack.checkPlayerInVaticanReportSection(0, 1)); // return true because is NOT in that vaticanSection
        assertTrue(testTrack.checkPlayerInVaticanReportSection(3, 3)); //return true because is in that vaticanSection
        assertFalse(testTrack.checkPlayerInVaticanReportSection(3, 2)); // return true because is NOT in that vaticanSection



    }


    @Test
    public void getVaticanReportSection() {
        FaithTrack testTrack = new FaithTrack(4);

        for (int i = 0; i<4; i++){
            testTrack.forwardPos(0);
        }
        for (int i = 0; i<5; i++){
            testTrack.forwardPos(1);
        }
        for (int i = 0; i<17; i++){
            testTrack.forwardPos(2);
        }
        for (int i = 0; i<19; i++){
            testTrack.forwardPos(3);
        }

        assertEquals(0, testTrack.getSection(0));
        assertEquals(1, testTrack.getSection(1));
        assertEquals(2, testTrack.getSection(2));
        assertEquals(3, testTrack.getSection(3));

    }

}