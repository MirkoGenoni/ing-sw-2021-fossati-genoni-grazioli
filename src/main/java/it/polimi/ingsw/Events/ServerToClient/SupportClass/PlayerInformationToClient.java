package it.polimi.ingsw.Events.ServerToClient.SupportClass;

import it.polimi.ingsw.Model.Resource.Resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class represents the structure of the player information to send to the client.
 * @see Serializable
 *
 * @author Stefano Fossati
 */
public class PlayerInformationToClient implements Serializable {
    private final String playerNameSend;
    private final ArrayList<Resource> deposit;
    private final Map<Resource, Integer> strongBox;
    private final ArrayList<Resource> additionalDepositType;
    private final ArrayList<Resource> additionalDeposit;
    private final ArrayList<LeaderCardToClient> leaderCardActive;
    private final ArrayList<DevelopmentCardToClient> developmentCardPlayer;
    private final ArrayList<Integer> popeFavorTiles;
    private final int faithMarkerPosition;

    /**
     * Constructs the structure of the player information.
     * @param playerNameSend The player name.
     * @param deposit The deposit state of the player.
     * @param strongBox The strongbox of the player.
     * @param additionalDepositType The type of additional deposit that the player has.
     * @param additionalDeposit The state of the additional deposit of the player.
     * @param leaderCardActive The leader cards that the player have active.
     * @param developmentCardPlayer The development cards of the player.
     * @param popeFavorTiles The pope favor tiles of the player.
     * @param faithMarkerPosition The position of the player in the faith track.
     */
    public PlayerInformationToClient(String playerNameSend, ArrayList<Resource> deposit, Map<Resource, Integer> strongBox, ArrayList<Resource> additionalDepositType,
                                     ArrayList<Resource> additionalDeposit, ArrayList<LeaderCardToClient> leaderCardActive,
                                     ArrayList<DevelopmentCardToClient> developmentCardPlayer, ArrayList<Integer> popeFavorTiles, int faithMarkerPosition) {
        this.playerNameSend = playerNameSend;
        this.deposit = deposit;
        this.strongBox = strongBox;
        this.leaderCardActive = leaderCardActive;
        this.developmentCardPlayer = developmentCardPlayer;
        this.popeFavorTiles = popeFavorTiles;
        this.faithMarkerPosition = faithMarkerPosition;
        this.additionalDepositType = additionalDepositType;
        this.additionalDeposit = additionalDeposit;
    }

    /**
     * Getter that returns the player name.
     * @return The player name.
     */
    public String getPlayerNameSend() {
        return playerNameSend;
    }

    /**
     * Getter that returns the deposit state of the player.
     * @return The deposit state of the player.
     */
    public ArrayList<Resource> getDeposit() {
        return deposit;
    }

    /**
     * Getter that returns the strongbox of the player.
     * @return The strongbox of the player.
     */
    public Map<Resource, Integer> getStrongBox() {
        return strongBox;
    }

    /**
     * Getter that returns the type of additional deposit that the player has.
     * @return The type of additional deposit that the player has.
     */
    public ArrayList<Resource> getAdditionalDepositType() {
        return additionalDepositType;
    }

    /**
     * Getter that returns the state of the additional deposit of the player.
     * @return The state of the additional deposit of the player.
     */
    public ArrayList<Resource> getAdditionalDeposit() {
        return additionalDeposit;
    }

    /**
     * Getter that returns the leader cards that the player have active.
     * @return The leader cards that the player have active.
     */
    public ArrayList<LeaderCardToClient> getLeaderCardActive() {
        return leaderCardActive;
    }

    /**
     * Getter that returns the development cards of the player.
     * @return The development cards of the player.
     */
    public ArrayList<DevelopmentCardToClient> getDevelopmentCardPlayer() {
        return developmentCardPlayer;
    }

    /**
     * Getter that returns the pope favor tiles of the player.
     * @return The pope favor tiles of the player.
     */
    public ArrayList<Integer> getPopeFavorTiles() {
        return popeFavorTiles;
    }

    /**
     * Getter that returns the position of the player in the faith track.
     * @return The position of the player in the faith track.
     */
    public int getFaithMarkerPosition() {
        return faithMarkerPosition;
    }
}
