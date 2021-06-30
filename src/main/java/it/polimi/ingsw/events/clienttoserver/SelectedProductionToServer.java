package it.polimi.ingsw.events.clienttoserver;

import it.polimi.ingsw.model.developmentcard.ProductedMaterials;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * This class represents the event of the selection of the power production turn to send to the server .
 *
 * @author Stefano Fossati.
 */
public class SelectedProductionToServer extends EventToServer{

    private final boolean useBaseProduction;
    private final Resource resourceRequested1;
    private final Resource resourceRequested2;
    private final ProductedMaterials resourceGranted;
    private final ArrayList<Boolean> useLeaders;
    private final ArrayList<Resource> materialLeaders;
    private final ArrayList<Boolean> useDevelop;
    private final String playerName;

    /**
     * Constructs the event.
     * @param useBaseProduction Is true if the player selected the base production power, else is false.
     * @param resourceRequested1 The first material choose by the player to use for the base production power.
     * @param resourceRequested2 The second material choose by the player to use for the base production power.
     * @param resourceGranted The resource that the player wants to generate from the base production power.
     * @param useLeaders the position of the leader card is true if the player wants to use a leader card additional production, else false. All the leader card that are not additional production are set on false in this ArrayList.
     * @param materialLeaders The materials choose by the player to use for the additional production power of the leader cards.
     * @param useDevelop The development card that the player decides to activate.
     * @param playerName The name of the player that sends the event.
     */
    public SelectedProductionToServer(boolean useBaseProduction, Resource resourceRequested1, Resource resourceRequested2,
                                      ProductedMaterials resourceGranted, ArrayList<Boolean> useLeaders, ArrayList<Resource> materialLeaders,
                                      ArrayList<Boolean> useDevelop, String playerName) {
        this.useBaseProduction = useBaseProduction;
        this.resourceRequested1 = resourceRequested1;
        this.resourceRequested2 = resourceRequested2;
        this.resourceGranted = resourceGranted;
        this.useLeaders = useLeaders;
        this.materialLeaders = materialLeaders;
        this.useDevelop = useDevelop;
        this.playerName = playerName;
    }

    /**
     * Getter of the boolean that indicates if the player choose to activate the base production power or not.
     * @return The  boolean that indicates if the player choose to activate the base production power or not.
     */
    public boolean isUseBaseProduction() {
        return useBaseProduction;
    }

    /**
     * Getter of the first material choose by the player to use for the base production power.
     * @return The first material choose by the player to use for the base production power.
     */
    public Resource getResourceRequested1() {
        return resourceRequested1;
    }

    /**
     * Getter of the second material choose by the player to use for the base production power.
     * @return The second material choose by the player to use for the base production power.
     */
    public Resource getResourceRequested2() {
        return resourceRequested2;
    }

    /**
     * Getter of the resource that the player wants to generate from the base production power.
     * @return The resource that the player wants to generate from the base production power.
     */
    public ProductedMaterials getResourceGranted() {
        return resourceGranted;
    }

    /**
     * Getter of the ArrayList of boolean that indicates if the player wants to activate the leader card additional production. All other type of leader card are set of false.
     * @return The resource that the player wants to generate from the base production power.
     */
    public ArrayList<Boolean> getUseLeaders() {
        return useLeaders;
    }

    /**
     * Getter of the materials choose by the player to use for the additional production power of the leader cards.
     * @return The materials choose by the player to use for the additional production power of the leader cards.
     */
    public ArrayList<Resource> getMaterialLeaders() {
        return materialLeaders;
    }

    /**
     * Getter of the development card that the player decides to activate.
     * @return The development card that the player decides to activate.
     */
    public ArrayList<Boolean> getUseDevelop() {
        return useDevelop;
    }

    /**
     * Getter of the name of the player that sends the event.
     * @return The name of the player that sends the event.
     */
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
