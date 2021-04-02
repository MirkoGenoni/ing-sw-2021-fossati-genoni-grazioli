package it.polimi.ingsw.Model.Market;

/**
 * Enumeration of the marble, the Market could use six type of marble.
 * This Enumeration is used by the Market to manage the position of each marble
 * and to return the type of marble choose by the player.
 *
 * @author Stefano Fossati
 */

public enum Marble {
    /**
     *
     */
    FAITH("faith"),

    /**
     *
     */
    GOLD("gold"),

    /**
     *
     */
    NOTHING("nothing"),

    /**
     *
     */
    SERVANT("servant"),

    /**
     *
     */
    SHIELD("shield"),

    /**
     *
     */
    STONE("stone");


    private String marbleType;

    Marble(String marbleType){
        this.marbleType = marbleType;

    }

    /**
     * Getter that returns the type of the marble
     * @return The type of the marble
     */
    public String getType(){
        return this.marbleType;
    }
}
