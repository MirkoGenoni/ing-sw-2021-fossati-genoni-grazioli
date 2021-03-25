package it.polimi.ingsw.Model.FaithTrack;

/**
 * Class Faithtrack
 * @author Davide
 */
public class FaithTrack {

    private Box[] track = new Box[25];
    private int[] faithMarkersPosition = new int[4];
    private int[] playerInVaticanReportSection = new int[4];

    /**
     * Constructor of the class
     */
    public FaithTrack() {
        //creating all boxes with their points
        for (int i = 0; i <= 24; i++) {
            if (i <= 2)
                track[i] = new Box(0);
            else if (i <= 5)
                track[i] = new Box(1);
            else if (i <= 8)
                track[i] = new Box(2);
            else if (i <= 11)
                track[i] = new Box(4);
            else if (i <= 14)
                track[i] = new Box(6);
            else if (i <= 17)
                track[i] = new Box(9);
            else if (i <= 20)
                track[i] = new Box(12);
            else if (i <= 23)
                track[i] = new Box(16);
            else
                track[i] = new Box(20);
        }
        //setting popeSpaces
        track[8].setPopeSpace(true);
        track[16].setPopeSpace(true);
        track[24].setPopeSpace(true);

        //setting zero markerPos e report section at the beginning
        for (int i = 0; i < 4; i++) {
            faithMarkersPosition[i] = 0;
            playerInVaticanReportSection[i] = 0;
        }
    }

    /**
     * @param numPlayer number of the player who move himself forward
     * @return true if the player go on a popeSpace section
     */
    public boolean forwardPos(int numPlayer) {   // ?? it would be necessary to error-control if numPlayer is valid  ??

        //move your mark
        if (faithMarkersPosition[numPlayer] < 24)
            faithMarkersPosition[numPlayer]++;


        int actualPosition = faithMarkersPosition[numPlayer]; //make the following code more legible

        //update your vaticanSection indicator
        if (actualPosition >= 5 && actualPosition <= 8)
            playerInVaticanReportSection[numPlayer] = 1;
        else if (actualPosition >= 12 && actualPosition <= 16)
            playerInVaticanReportSection[numPlayer] = 2;
        else if (actualPosition >= 19 && actualPosition <= 24)
            playerInVaticanReportSection[numPlayer] = 3;

        //if i am in a popeSpace i have to disable it and notice
        if (track[actualPosition].getPopeSpace()) {
            track[actualPosition].setPopeSpace(false);
            return true;
        } else
            return false;


    }

    /**
     * @param numPlayer number of the player which I want to know the position
     * @return the position of the specified player on the FaithPath
     */
    public int getPosition(int numPlayer) {

        return faithMarkersPosition[numPlayer];

    }


    /**
     *
     * @param numPlayer number of the player to pharse with popeSection
     * @param section section of popeSection to pharse with the player
     * @return true if the player is in THAT section ()
     */
    public boolean checkPlayerInVaticanReportSection(int numPlayer, int section){
        return playerInVaticanReportSection[numPlayer]==section;
    }


    /**
     * @param numPlayer number of the player i want to know in which VaticanSection is
     * @return the number of the vatican section where numPlayer is
     */
    private int getPlayerInVaticanReportSection(int numPlayer) {

        return playerInVaticanReportSection[numPlayer];
    }

}








