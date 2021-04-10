package it.polimi.ingsw.Model.LeaderCard;

import it.polimi.ingsw.Model.Exceptions.LeaderCardException;

import java.util.ArrayList;

/**
 * This class manages leader cards of a player.
 *
 * @author Stefano Fossati
 */

public class LeaderCardHandler{
    private ArrayList<LeaderCard> leaderCardsAvailable;
    private ArrayList<LeaderCard> leaderCardsActive = new ArrayList<>();

    /**
     * Initializes the leader card available of a player. The first cards are given by the game.
     * @param leaderCardGiven The first cards given by the game.
     * @exception LeaderCardException if the ArrayList leader card given is empty or null.
     */
    public LeaderCardHandler(ArrayList<LeaderCard> leaderCardGiven) throws LeaderCardException {
        if(leaderCardGiven != null && leaderCardGiven.size()!=0){
            this.leaderCardsAvailable = leaderCardGiven;
        }else{
            throw new LeaderCardException("leader card given is null");
        }
    }

    /**
     * This method remove the leader cards at the beginning of the game.
     * @param selected1 The first card that the player want to discard.
     * @param selected2 The second card that the player want to discard.
     * @exception LeaderCardException if the selection of the leader cards is wrong or the leader cards selected doesn't exist.
     */
    public void removeInitialLeaderCard( int selected1, int selected2) throws LeaderCardException {
        int card1;
        int card2;
        if(selected1<selected2){ // The order to remove the card must be decreasing to remove the cards correctly
            card1 = selected2;
            card2 = selected1;
        }else if(selected1>selected2){
            card1 = selected1;
            card2 = selected2;
        }else{
            throw new LeaderCardException("Invalid card selection");
        }

        if(selected1 >= leaderCardsAvailable.size() && selected2 >= leaderCardsAvailable.size()){
            throw new LeaderCardException("Invalid cards selection");
        }else if(selected1 >= leaderCardsAvailable.size()){
            throw new LeaderCardException("Invalid first card selection");
        }
        else if(selected2 >= leaderCardsAvailable.size()){
            throw new LeaderCardException("Invalid second card selection");
        }
        else{
            leaderCardsAvailable.remove(card1);
            leaderCardsAvailable.remove(card2);
        }
    }

    /**
     * Getter of the leader cards available of the player.
     * @return All the leader cards available of the player.
     * @exception LeaderCardException if the ArrayList of leader cards available is empty;
     */

    public ArrayList<LeaderCard> getLeaderCardsAvailable() throws LeaderCardException {
        if(leaderCardsAvailable!=null && leaderCardsAvailable.size()!=0){
            return new ArrayList<>(leaderCardsAvailable);
        }else{
            throw new LeaderCardException("you haven't leader card available");
        }
    }

    /**
     * Getter of the active leader cards of the player.
     * @return All the active leader cards of the player.
     * @exception LeaderCardException if the ArrayList of leader cards active is empty.
     */

    public ArrayList<LeaderCard> getLeaderCardsActive() throws LeaderCardException {
        if(leaderCardsActive !=null && leaderCardsActive.size()!=0){
            return new ArrayList<>(leaderCardsActive);
        }else{
            throw new LeaderCardException("you haven't leader card active");
        }
    }

    /**
     * Getter of the special ability of the active leader card that the player want to use.
     * @param selected The leader card that the player want to use.
     * @return The special ability of the leader card that the player want to use.
     * @exception LeaderCardException if the ArrayList of the leader card active selected is null or doesn't exist.
     */

    public SpecialAbility getLeaderActivePower(int selected) throws LeaderCardException {
        if(selected < leaderCardsActive.size() && leaderCardsActive.get(selected)!=null){
            return getLeaderCardsActive().get(selected).getSpecialAbility();
        }else{
            throw new LeaderCardException("Invalid active leader card selection");
        }
    }

    /**
     * This method represents the choose of the player of activate a leader card.
     * The leader cards could be activate only form the ArrayList leader card available.
     * This method move the leader card selected from the ArrayList leaderCardAvailable to the ArrayList leaderCardActive.
     * This method moves the leader card choose by the player from the leader cards available to the active leader cards.
     * @param selected The leader card that the player want to activate.
     * @exception LeaderCardException if the leader card available selected doesn't exist or is null.
     */

    public void activateLeaderCard(int selected) throws LeaderCardException {
        if(selected < leaderCardsAvailable.size() && leaderCardsAvailable.get(selected)!=null){
            LeaderCard tmp = leaderCardsAvailable.remove(selected);
            leaderCardsActive.add(tmp);
        }else{
            throw new LeaderCardException("Invalid available leader card selection");
        }
    }

    /**
     * This method discard the leader card that the player want to discard for get a faith point.
     * The leader cards could be discard only from the ArrayList leader card available.
     * This method is called during the game and the player could discards only from the leader cards available, like from the game's rules.
     * @param selected The leader card available that the player want to discard.
     * @exception LeaderCardException if the leader card available selected doesn't exist or is null;
     */

    public void discardLeaderCard( int selected) throws LeaderCardException {
        if(selected < leaderCardsAvailable.size() && leaderCardsAvailable.get(selected)!=null){
            leaderCardsAvailable.remove(selected);
        }else{
            throw new LeaderCardException("Invalid available leader card selection, card selected isn't available");
        }
    }
}
