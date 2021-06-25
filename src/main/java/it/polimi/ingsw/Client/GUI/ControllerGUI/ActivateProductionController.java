package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.LeaderCardToClient;
import it.polimi.ingsw.Model.DevelopmentCard.ProductedMaterials;
import it.polimi.ingsw.Model.Resource.Resource;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.*;

/**
 * This class represents the controller of the activate production scene of the GUI application. Implements GUIController and Initializable interface.
 * @see GUIController
 * @see Initializable
 *
 * @author Stefano Fossati
 */
public class ActivateProductionController implements GUIController, Initializable {
    private GUI gui;

    private Map <String,Image> resources;

    private ArrayList<ImageView> devProduction;
    private ArrayList<ImageView> selectionDev;

    private ArrayList<ImageView> leadAdditionalProduction;
    private ArrayList<ImageView> selectionLead;
    private ArrayList<ImageView> resourceLead;

    private Map<String, Integer> totalResources;

    private ArrayList<Integer> devSelected = new ArrayList<>();
    private Boolean[] leaderSelected;
    private ArrayList<Integer> leadSelected = new ArrayList<>();


    @FXML private ImageView dev0;
    @FXML private ImageView dev1;
    @FXML private ImageView dev2;

    @FXML private ImageView dev0sel;
    @FXML private ImageView dev1sel;
    @FXML private ImageView dev2sel;

    @FXML private ImageView leader0;
    @FXML private ImageView leader1;

    @FXML private ImageView lead0sel;
    @FXML private ImageView lead1sel;

    @FXML private ImageView resourceLead0;
    @FXML private ImageView resourceLead1;

    @FXML private Label coin;
    @FXML private Label stone;
    @FXML private Label shield;
    @FXML private Label servant;

    @FXML ImageView resource1;
    @FXML ImageView resource2;
    @FXML ImageView resourceProduced;

    /**
     * Displays the development cars of the player, the leader cards additional production and the sum of all resources of the player.
     * @param devPlayer The developments card available of the player.
     * @param deposit The deposit of the player.
     * @param strongBox The strongbox of the player.
     * @param leaderCardsActive The leader cards active of the player.
     */
    public void drawProduction(ArrayList<Image> devPlayer, ArrayList<Resource> deposit, Map<Resource, Integer> strongBox, ArrayList<LeaderCardToClient> leaderCardsActive){

        this.totalResources = gui.calculateTotalResources(deposit, strongBox);

        coin.setText("X " + totalResources.get("coin"));
        stone.setText("X " + totalResources.get("stone"));
        shield.setText("X " + totalResources.get("shield"));
        servant.setText("X " + totalResources.get("servant"));

        //draw development
        for(int i=0; i<devPlayer.size(); i++){
            devProduction.get(i).setImage(devPlayer.get(i));
            if(devPlayer.get(i)!=null){
                devProduction.get(i).setOnMouseClicked(selectDevelopmentProduction);
            }
        }

        //draw leader additional production
        leaderSelected = new Boolean[leaderCardsActive.size()];
        int k=0;
        for(int i=0; i<leaderCardsActive.size(); i++){
            if(leaderCardsActive.get(i).getEffect().equals("additionalProduction")){
                leadAdditionalProduction.get(k).setImage(gui.getLeaderCardsGraphic().get(leaderCardsActive.get(i).getNameCard()));
                leadAdditionalProduction.get(k).setOnMouseClicked(selectLeaderAdditionalProduction);
                resourceLead.get(k).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        changeResources((ImageView) mouseEvent.getSource());
                    }
                });
                k++;
            }else{
                leaderSelected[i] = false;
            }

        }
    }

    //------------------------------
    // METHODS FOR GRAPHIC ELEMENTS
    //------------------------------

    /**
     * Creates all the structure that represents the production that the player choose to activate and send them to the connection with the server. Finally change the scene into the player view scene.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void done(ActionEvent actionEvent) {
        //resources
        boolean baseProduction = false;
        Resource resourceChoose1 = null;
        Resource resourceChoose2 = null;
        Resource resourceProducedChoose = null;
        ProductedMaterials resourceGranted = null;
        if(resource1.getImage()!=null && resource2.getImage()!=null && resourceProduced.getImage()!=null){
            for(Resource r : Resource.values()){
                if(resource1.getImage().equals(resources.get(r.name().toLowerCase()))){
                    resourceChoose1 = r;
                }
                if(resource2.getImage().equals(resources.get(r.name().toLowerCase()))){
                    resourceChoose2 = r;
                }
                if(resourceProduced.getImage().equals(resources.get(r.name().toLowerCase()))){
                    resourceProducedChoose = r;
                }
            }
            resourceGranted = ProductedMaterials.valueOf(resourceProducedChoose.name());
            baseProduction = true;
        }

        // development card
        ArrayList<Boolean> useDevelop = new ArrayList<>();
        for( Integer i=0; i<3; i++){
            if(devSelected.contains(i)){
                useDevelop.add(true);
            }else{
                useDevelop.add(false);
            }
        }

        //leader card
        int k=0;
        for(int i=0; i<leaderSelected.length; i++){
            if(leaderSelected[i]==null || leaderSelected[i]==true){
                if(leadSelected.contains((Integer) k)){
                    leaderSelected[i] = true;
                }else{
                    leaderSelected[i] = false;
                }
                k++;
            }
        }
        ArrayList<Boolean> leaderTmp = new ArrayList<Boolean>(Arrays.asList(leaderSelected));

        //Resources Leader
        ArrayList<Resource> leaderRtmp = new ArrayList<>();
        for(int i=0; i<resourceLead.size(); i++){
            if(resourceLead.get(i).getImage()!=null && leadSelected.contains((Integer)i)){
                for(Resource r : Resource.values()){
                    if(resourceLead.get(i).getImage().equals(resources.get(r.name().toLowerCase()))){
                        leaderRtmp.add(r);
                    }
                }
            }
        }

        gui.changeScene("playerView");
        PlayerViewController controller = (PlayerViewController) gui.getCurrentController();
        controller.tabTurnNotActive(true);
        gui.getConnectionToServer().sendSelectedProductionDevelopmentCard(baseProduction, resourceChoose1, resourceChoose2, resourceGranted, leaderTmp, leaderRtmp, useDevelop);
        clear();
    }

    /**
     * Changes the resources in the base power production.
     * @param mouseEvent The event of the type ActionEvent.
     */
    public void change(MouseEvent mouseEvent) {
        changeResources((ImageView)mouseEvent.getSource());
    }

    /**
     * Changes the scene into the player view scene.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void back(ActionEvent actionEvent) {
        gui.changeScene("playerView");
        clear();
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
        resources = gui.getResourcesGraphic();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        devProduction = new ArrayList<>(List.of(dev0, dev1, dev2));
        selectionDev = new ArrayList<>(List.of(dev0sel, dev1sel, dev2sel));
        leadAdditionalProduction = new ArrayList<>(List.of(leader0, leader1));
        selectionLead = new ArrayList<>(List.of(lead0sel, lead1sel));
        resourceLead = new ArrayList<>(List.of(resourceLead0, resourceLead1));
    }

    /**
     * Event for the managing of the development card selection.
     */
    private EventHandler<MouseEvent> selectDevelopmentProduction = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            int i=-1;
            switch (((ImageView) mouseEvent.getSource()).getId()){
                case "dev0":
                    i=0;
                    break;
                case "dev1":
                    i=1;
                    break;
                case "dev2":
                    i=2;
                    break;
            }
            selection(i, devSelected, devProduction, selectionDev);
        }
    };
    /**
     * Event for the managing of the leader card additional production selection.
     */
    private EventHandler<MouseEvent> selectLeaderAdditionalProduction = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            //if(((ImageView) mouseEvent.getSource()).getImage()!=null){ }
            int i = -1;
            switch(((ImageView) mouseEvent.getSource()).getId()){
                case "leader0":
                    i=0;
                    break;
                case "leader1":
                    i=1;
                    break;
            }
            selection(i, leadSelected, leadAdditionalProduction, selectionLead);
        }
    };

    /**
     * Manages the structures use for the selection of the development card production choose by the player.
     * @param i The number of the position of the development card.
     * @param selected The list of development cards selected.
     * @param production The list of development card images selected.
     * @param selection The list of images of the development card selector.
     */
    private void selection(int i, ArrayList<Integer> selected, ArrayList<ImageView> production, ArrayList<ImageView> selection){
        if(selected.contains((Integer) i)){
            selected.remove((Integer) i);
            production.get(i).setEffect(null);
            selection.get(i).setOpacity(0);
        }else{
            selected.add((Integer) i);
            production.get(i).setEffect(new DropShadow());
            selection.get(i).setOpacity(0.65);
        }
    }

    /**
     * Clears all the structures use for the manage the development card space selection.
     */
    private void clear(){
        devSelected.clear();
        devProduction.forEach(dev -> dev.setImage(null));
        devProduction.forEach(dev -> dev.setEffect(null));
        selectionDev.forEach(devI -> devI.setOpacity(0));
        resource1.setImage(null);
        resource2.setImage(null);
        resourceProduced.setImage(null);
        for(String s : totalResources.keySet()){
            totalResources.put(s, 0);
        }
        leadSelected.clear();
        leadAdditionalProduction.forEach(lead -> lead.setImage(null));
        leadAdditionalProduction.forEach(lead -> lead.setEffect(null));
        selectionLead.forEach(leadI -> leadI.setOpacity(0));
        resourceLead.forEach(res -> res.setImage(null));
    }

    /**
     * Changes the resources of the base power production.
     * @param resource The current resource of the base power production element.
     */
    private void changeResources(ImageView resource){
        if(resource.getImage()==null){
            resource.setImage(resources.get("coin"));
        }else if(resource.getImage().equals(resources.get("coin"))){
            resource.setImage(resources.get("stone"));
        }else if(resource.getImage().equals(resources.get("stone"))){
            resource.setImage(resources.get("shield"));
        }else if(resource.getImage().equals(resources.get("shield"))){
            resource.setImage(resources.get("servant"));
        }else if(resource.getImage().equals(resources.get("servant"))){
            resource.setImage(null);
        }
    }


}
