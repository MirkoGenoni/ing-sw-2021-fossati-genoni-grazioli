package it.polimi.ingsw.Model.GameBoard;

import it.polimi.ingsw.Model.LeaderCard.LeaderCard;
import it.polimi.ingsw.Model.LeaderCard.SpecialAbility;

import java.util.ArrayList;

/**
 * This class manages leader cards of a player.
 *
 * @author Stefano Fossati
 */

public class LeaderCardHandler {
    private ArrayList<LeaderCard> leaderCardAvailable;
    private ArrayList<LeaderCard> activeLeaderPower;

    /**
     * Initializes the leader card available of a player. The first cards are given by the game.
     * @param leaderCardGiven The first cards given by the game.
     */
    public LeaderCardHandler(ArrayList<LeaderCard> leaderCardGiven){
        this.leaderCardAvailable = leaderCardGiven;
    }

    /**
     * This method remove the leader cards at the beginning of the game.
     * @param selected1 The first card that the player want to discard.
     * @param selected2 The second card that the player want to discard.
     */

    public void removeInitialLeaderCard( int selected1, int selected2) {
        leaderCardAvailable.remove(selected1);
        leaderCardAvailable.remove(selected2);
    }

    /**
     * Getter of the leader cards available of the player.
     * @return All the leader cards available of the player.
     */

    public ArrayList<LeaderCard> getLeaderCardAvailable(){
        return leaderCardAvailable;
    }

    /**
     * Getter of the active leader cards of the player.
     * @return All the active leader cards of the player.
     */

    public ArrayList<LeaderCard> getActiveLeaderPower(){
        return activeLeaderPower;
    }

    /**
     * Getter of the special ability of the leader card that the player want to use.
     * @param selected The leader card that the player want to use.
     * @return The special ability of the leader card that the player want to use.
     */

    public SpecialAbility getLeaderActivePower(int selected){
        return activeLeaderPower.get(selected).getSpecialAbility();
    }

    /**
     * This method represents the choose of the player of activate a leader card.
     * This method moves the leader card choose by the player from the leader cards available to the active leader cards.
     * @param selected The leader card that the player want to activate.
     */

    public void setLeaderActivePower(int selected){
        LeaderCard tmp = leaderCardAvailable.remove(selected);
        activeLeaderPower.add(tmp);
    }

    /**
     * This method discard the leader card that the player want to discard for get a faith point.
     * This method is called during the game and the player could discards only from the leader cards available, like from the game's rules.
     * @param selected The leader card available that the player want to discard.
     */

    public void discardLeaderCard( int selected){
        leaderCardAvailable.remove(selected);
    }
}
