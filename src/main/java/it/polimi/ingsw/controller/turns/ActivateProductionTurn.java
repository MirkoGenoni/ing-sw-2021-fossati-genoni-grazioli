package it.polimi.ingsw.controller.turns;

import it.polimi.ingsw.controller.ControllerToModel;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.developmentcard.ProductedMaterials;
import it.polimi.ingsw.model.exceptions.LeaderCardException;
import it.polimi.ingsw.model.exceptions.ResourceException;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.gameboard.Gameboard;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * class to manage the turn to activate productions
 */
public class ActivateProductionTurn {
    private final ControllerToModel controllerToModel;

    /**
     * constructor of the class
     * @param controllerToModel controller to model that manages the game for players
     */
    public ActivateProductionTurn(ControllerToModel controllerToModel) {
        this.controllerToModel = controllerToModel;
    }

    /**
     * Method that manage the production turn
     * @param useBaseProduction boolean that specify if the player want to use the base production
     * @param resourceRequested1 if the player want to use the base production indicates the first resource to use to develop
     * @param resourceRequested2 if the player want to use the base production indicates the second resource to use to develop
     * @param resourceGranted if the player want to use the base production indicates resource granted from the base production
     * @param useLeaders Array of Boolean that specify if the player want to use leaders productions, the boolean is in the same
     *                   position of the leader the player want to use
     * @param materialLeaders The material the player want from the leader production, the resource is in the same
     *      *                   position of the leader the player want to use
     * @param useDevelop Array of Boolean that specify if the player want to use a production card, the boolean is in the same
     *      *                   position of the production card the player want to use
     * @return true if the played turn is consistent, so if the player produces something
     */
    public boolean productionsActivation(boolean useBaseProduction, Resource resourceRequested1, Resource resourceRequested2,
                                    ProductedMaterials resourceGranted, ArrayList<Boolean> useLeaders, ArrayList<Resource> materialLeaders,
                                    ArrayList<Boolean> useDevelop){
        int currentPlayerIndex = controllerToModel.getCurrentPlayerIndex();
        Player activePlayer = controllerToModel.getPlayers()[currentPlayerIndex];
        Gameboard actualPlayerBoard = activePlayer.getPlayerBoard();

        Map<Resource,Integer> materialRequested = new HashMap<>();
        for(Resource r : Resource.values())
            materialRequested.put(r,0);

        Map<ProductedMaterials, Integer> materialGranted = new HashMap<>();
        for(ProductedMaterials p : ProductedMaterials.values())
            materialGranted.put(p,0);

        if(useBaseProduction){
            materialRequested.put(resourceRequested1,materialRequested.get(resourceRequested1)+1);
            materialRequested.put(resourceRequested2,materialRequested.get(resourceRequested2)+1);
            materialGranted.put(resourceGranted, materialGranted.get(resourceGranted)+1);
        }

        if(useLeaders.contains(true)) {
            leaderCardProduction(useLeaders, materialLeaders, materialRequested, materialGranted, activePlayer);
        }

        if(useDevelop.contains(true)) {
            developmentCardProduction(useDevelop, materialRequested, materialGranted, activePlayer);
        }


        boolean valid = false;
        for(Resource r : materialRequested.keySet()){
            if(materialRequested.get(r)!=0){
                valid=true;
            }
        }
        if(actualPlayerBoard.getResourceHandler().checkMaterials(materialRequested) && valid){
            try {
                actualPlayerBoard.getResourceHandler().takeMaterials(materialRequested);
            } catch (ResourceException e) {
                e.printStackTrace();
            }

            Map<Resource, Integer> materialForStrongBox = new HashMap<>();

            for(Resource r : Resource.values())
                materialForStrongBox.put(r, materialGranted.get(ProductedMaterials.valueOf(r.name())));

            actualPlayerBoard.getResourceHandler().addMaterialStrongbox(materialForStrongBox);
            int faithPoints = materialGranted.get(ProductedMaterials.FAITHPOINT);
            for(int i=0; i<faithPoints; i++){
                if(controllerToModel.getGame().getPlayersFaithTrack().forwardPos(currentPlayerIndex)){
                    controllerToModel.controlPlayerPath(currentPlayerIndex, controllerToModel.getGame().getPlayersFaithTrack().getSection(currentPlayerIndex));
                }
            }
            System.out.println("Risorse aggiunte correttamente alla strongbox");
            System.out.println(controllerToModel.getPlayers()[currentPlayerIndex].getName()+ " avanza di "+ faithPoints+ " punti fede");

            //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendNotify("Risorse aggiunte correttamente alla strongbox");
            controllerToModel.getConnections().get(activePlayer.getName()).sendNotify("Risorse aggiunte correttamente alla strongbox");
            //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendNotify("Avanzi di "+ faithPoints + " punti fede");
            controllerToModel.getConnections().get(activePlayer.getName()).sendNotify("Avanzi di "+ faithPoints + " punti fede");
        }else if(!actualPlayerBoard.getResourceHandler().checkMaterials(materialRequested)){
            System.out.println("non ci sono abbastanza risorse");
            //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendTurnReselection("Not enough resources");
            controllerToModel.getConnections().get(activePlayer.getName()).sendTurnReselection("Not enough resources");
            return false;
        }else if(!valid){
            System.out.println("non hai selezionato nulla");
            //controllerToModel.getConnectionsToClient().get(currentPlayerIndex).sendTurnReselection("No productions selected");
            controllerToModel.getConnections().get(activePlayer.getName()).sendTurnReselection("No productions selected");
            return false;
        }
        return true;
    }

    /**
     * private method to manage the production by the leader cards
     * @param useLeaders Array of Boolean that specify if the player want to use leaders productions, the boolean is in the same
     *      *                   position of the leader the player want to use
     * @param materialLeaders The material the player want from the leader production, the resource is in the same
     *      *                   position of the leader the player want to use
     * @param materialRequested Map of the amount of resource the player must have to develop which the relative
     * @param materialGranted Map of the amount of resource the player will have if is able to develop
     * @param activePlayer The player who's playing the turn
     */
    private void leaderCardProduction(ArrayList<Boolean> useLeaders, ArrayList<Resource> materialLeaders, Map<Resource,Integer> materialRequested,
                                      Map<ProductedMaterials, Integer> materialGranted, Player activePlayer){

        ArrayList<ProductedMaterials> productedByLeader = new ArrayList<>();


        try {
            ArrayList<LeaderCard> activeLeaders = activePlayer.getPlayerBoard().getLeaderCardHandler().getLeaderCardsActive();
            int j=0;

            for(int i=0; i<activeLeaders.size(); i++) { //TRANSFORM MATERIAL LEADER --> RESOURCE IN PRODUCTEDMATERIALS
                if(activeLeaders.get(i).getSpecialAbility().getEffect().equals("additionalProduction") && useLeaders.get(i) &&
                        materialLeaders!=null && materialLeaders.size() > j && materialLeaders.get(j)!=null){
                    productedByLeader.add(ProductedMaterials.valueOf(materialLeaders.get(j).name()));
                    j++;
                } else{
                    productedByLeader.add(null);
                }
            }


            for (int i = 0; i < activeLeaders.size(); i++) {
                if (useLeaders.get(i) && productedByLeader.get(i)!=null) {
                    if(activeLeaders.get(i).getSpecialAbility().getEffect().equals("additionalProduction")) {
                        Resource requestFromLeader = activeLeaders.get(i).getSpecialAbility().getMaterialType();
                        materialRequested.put(requestFromLeader, materialRequested.get(requestFromLeader) + 1);
                        materialGranted.put(productedByLeader.get(i),materialGranted.get(productedByLeader.get(i))+1);
                        materialGranted.put(ProductedMaterials.FAITHPOINT, materialGranted.get(ProductedMaterials.FAITHPOINT)+1);
                    }
                    else{
                        System.out.println("leader "+i+" not additionalProduction");
                    }
                }
            }

        }catch (LeaderCardException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * private method to manage the production by development cards
     * @param useDevelop Array of Boolean that specify if the player want to use a production card, the boolean is in the same
     *      *      *                 position of the production card the player want to use
     * @param materialRequested Map of the amount of resource the player must have to develop which the relative
     * @param materialGranted Map of the amount of resource the player will have if is able to develop
     * @param activePlayer The player who's playing the turn
     */
    private void developmentCardProduction(ArrayList<Boolean> useDevelop, Map<Resource,Integer> materialRequested, Map<ProductedMaterials,
                                            Integer> materialGranted, Player activePlayer){
        ArrayList<DevelopmentCard> activeDevelopment = activePlayer.getPlayerBoard().getDevelopmentCardHandler().getActiveDevelopmentCard();

        for(int i=0; i < activeDevelopment.size(); i++){
            if (activeDevelopment.get(i)!=null && useDevelop.get(i)){
                System.out.println("Active prod number "+ i +" ?");

                Map<Resource,Integer> mapRequest = activeDevelopment.get(i).getMaterialRequired();
                for(Resource r : mapRequest.keySet())
                    materialRequested.put(r,materialRequested.get(r) + mapRequest.get(r));

                Map<ProductedMaterials,Integer> mapProd = activeDevelopment.get(i).getProductionResult();
                for(ProductedMaterials prod : mapProd.keySet())
                    materialGranted.put(prod, materialGranted.get(prod) + mapProd.get(prod));
            }
            else {
                System.out.println("Have not active DevelopmentCard in space " + i);
            }
        }
    }

}
