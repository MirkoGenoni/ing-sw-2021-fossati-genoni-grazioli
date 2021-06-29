package it.polimi.ingsw.model.faithTrack;

/**
 * Class Faithtrack
 * @author Davide
 */
public class FaithTrack {

    private Box[] track = new Box[25];
    private int[] faithMarkersPosition;
    private int[] playerInVaticanReportSection;

    /**
     * Constructor of the class
     * @param numPlayer specify how many players are going to play and the constructor instantiates the correct number of the Markers in Path
     */
    public FaithTrack(int numPlayer) {

        faithMarkersPosition = new int[numPlayer];
        playerInVaticanReportSection = new int[numPlayer];

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
        for (int i = 0; i < numPlayer; i++) {
            faithMarkersPosition[i] = 0;
            playerInVaticanReportSection[i] = 0;
        }
    }

    /**
     * Getter of the box of the faith track
     * @return the box of the faith track
     */
    public Box[] getTrack() {
        return track;
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
    public int getSection(int numPlayer) {

        return playerInVaticanReportSection[numPlayer];
    }

}








