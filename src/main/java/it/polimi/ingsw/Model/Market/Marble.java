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
     *   This enum represent the faith marble, it is red.
     */
    FAITH("faith"),

    /**
     *  This enum represent the gold marble, it is yellow.
     */
    COIN("coin"),

    /**
     *  This enum represent the nothing marble, it is white.
     */
    NOTHING("nothing"),

    /**
     *  This enum represent the servant marble, it is purple.
     */
    SERVANT("servant"),

    /**
     *  This enum represent the shield marble, it is blue,
     */
    SHIELD("shield"),

    /**
     *  This enum represent the stone marble, is is gray.
     */
    STONE("stone");


    private final String marbleType;

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
