package it.polimi.ingsw.Model.FaithTrack;

import org.junit.Test;

import static org.junit.Assert.*;

public class FaithTrackTest {

    @Test
    public void testForwardPos() {
        FaithTrack testTrack = new FaithTrack();

        for (int i = 0; i<7; i++){
            testTrack.forwardPos(0);
        }
        for (int i = 0; i<7; i++){
            testTrack.forwardPos(3);
        }

        assertTrue(testTrack.forwardPos(0)); //return true because is popeSpace
        assertFalse(testTrack.forwardPos(3));//return false because is not popeSpace anymore


    }

    @Test
    public void testGetPosition() {
        FaithTrack testTrack = new FaithTrack();

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
        FaithTrack testTrack = new FaithTrack();

        for (int i = 0; i<4; i++){
            testTrack.forwardPos(0);
        }
        for (int i = 0; i<19; i++){
            testTrack.forwardPos(3);
        }

        assertTrue(testTrack.checkVaticanReportSection(0,0));//return true because is in that vaticanSection
        assertFalse(testTrack.checkVaticanReportSection(0, 1)); // return true because is NOT in that vaticanSection
        assertTrue(testTrack.checkVaticanReportSection(3, 3)); //return true because is in that vaticanSection
        assertFalse(testTrack.checkVaticanReportSection(3, 2)); // return true because is NOT in that vaticanSection



    }



//THIS IS A TEST FOR A PRIVATE METOD, IT ISN'T PUBBLIC, BUT MAYBE...


   /* @Test
    public void getVaticanReportSection() {
        FaithTrack testTrack = new FaithTrack();

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

        assertEquals(0, testTrack.GetPlayerInVaticanReportSection(0));
        assertEquals(1, testTrack.GetPlayerInVaticanReportSection(1));
        assertEquals(2, testTrack.GetPlayerInVaticanReportSection(2));
        assertEquals(3, testTrack.GetPlayerInVaticanReportSection(3));

    }*/

}