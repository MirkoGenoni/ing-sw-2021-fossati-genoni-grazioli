package it.polimi.ingsw.Events.ServerToClient.SupportClass;

import it.polimi.ingsw.Events.ServerToClient.SendLeaderCardToClient;
import it.polimi.ingsw.Model.Resource.Resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class PlayerInformationToClient implements Serializable {
    private final String playerNameSend;
    private final ArrayList<Resource> deposit;
    private final Map<Resource, Integer> strongBox;
    private final ArrayList<SendLeaderCardToClient> leaderCardActive;
    private final ArrayList<DevelopmentCardToClient> developmentCardPlayer;
    private final ArrayList<Integer> popeFavorTiles;
    private final int faithMarkerPosition;

    public PlayerInformationToClient(String playerNameSend, ArrayList<Resource> deposit, Map<Resource, Integer> strongBox,
                                     ArrayList<SendLeaderCardToClient> leaderCardActive, ArrayList<DevelopmentCardToClient> developmentCardPlayer,
                                     ArrayList<Integer> popeFavorTiles, int faithMarkerPosition) {
        this.playerNameSend = playerNameSend;
        this.deposit = deposit;
        this.strongBox = strongBox;
        this.leaderCardActive = leaderCardActive;
        this.developmentCardPlayer = developmentCardPlayer;
        this.popeFavorTiles = popeFavorTiles;
        this.faithMarkerPosition = faithMarkerPosition;
    }

    public String getPlayerNameSend() {
        return playerNameSend;
    }

    public ArrayList<Resource> getDeposit() {
        return deposit;
    }

    public Map<Resource, Integer> getStrongBox() {
        return strongBox;
    }

    public ArrayList<SendLeaderCardToClient> getLeaderCardActive() {
        return leaderCardActive;
    }

    public ArrayList<DevelopmentCardToClient> getDevelopmentCardPlayer() {
        return developmentCardPlayer;
    }

    public ArrayList<Integer> getPopeFavorTiles() {
        return popeFavorTiles;
    }

    public int getFaithMarkerPosition() {
        return faithMarkerPosition;
    }

}
