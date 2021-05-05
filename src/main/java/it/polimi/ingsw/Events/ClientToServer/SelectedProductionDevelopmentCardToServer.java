package it.polimi.ingsw.Events.ClientToServer;

import it.polimi.ingsw.Model.DevelopmentCard.ProductedMaterials;
import it.polimi.ingsw.Model.Resource.Resource;

import java.util.ArrayList;

public class SelectedProductionDevelopmentCardToServer extends EventToServer{

    private final boolean useBaseProduction;
    private final Resource resourceRequested1;
    private final Resource resourceRequested2;
    private final ProductedMaterials resourceGranted;
    private final ArrayList<Boolean> useLeaders;
    private final ArrayList<Resource> materialLeaders;
    private final ArrayList<Boolean> useDevelop;
    private final String playerName;

    public SelectedProductionDevelopmentCardToServer(boolean useBaseProduction, Resource resourceRequested1, Resource resourceRequested2,
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

    public boolean isUseBaseProduction() {
        return useBaseProduction;
    }

    public Resource getResourceRequested1() {
        return resourceRequested1;
    }

    public Resource getResourceRequested2() {
        return resourceRequested2;
    }

    public ProductedMaterials getResourceGranted() {
        return resourceGranted;
    }

    public ArrayList<Boolean> getUseLeaders() {
        return useLeaders;
    }

    public ArrayList<Resource> getMaterialLeaders() {
        return materialLeaders;
    }

    public ArrayList<Boolean> getUseDevelop() {
        return useDevelop;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void acceptServerVisitor(EventToServerVisitor visitor) {
        visitor.visit(this);
    }
}
