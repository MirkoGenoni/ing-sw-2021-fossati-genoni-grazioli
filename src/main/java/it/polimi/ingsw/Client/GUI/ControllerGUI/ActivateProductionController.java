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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;


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


    @FXML ImageView dev0;
    @FXML ImageView dev1;
    @FXML ImageView dev2;

    @FXML ImageView dev0sel;
    @FXML ImageView dev1sel;
    @FXML ImageView dev2sel;

    @FXML ImageView leader0;
    @FXML ImageView leader1;

    @FXML ImageView lead0sel;
    @FXML ImageView lead1sel;

    @FXML ImageView resourceLead0;
    @FXML ImageView resourceLead1;


    @FXML Label coin;
    @FXML Label stone;
    @FXML Label shield;
    @FXML Label servant;

    @FXML ImageView resource1;
    @FXML ImageView resource2;
    @FXML ImageView resourceProduced;


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
                try{
                    FileInputStream input = new FileInputStream("src/main/resources/graphics/leaderCard/" + leaderCardsActive.get(i).getNameCard() + ".png");
                    leadAdditionalProduction.get(k).setImage(new Image(input));
                    leadAdditionalProduction.get(k).setOnMouseClicked(selectLeaderAdditionalProduction);
                    resourceLead.get(k).setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            changeResources((ImageView) mouseEvent.getSource());
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                k++;
            }else{
                leaderSelected[i] = false;
            }

        }
    }






    public void done(ActionEvent actionEvent) {
        //risorse
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

        // development
        ArrayList<Boolean> useDevelop = new ArrayList<>();
        for( Integer i=0; i<3; i++){
            if(devSelected.contains(i)){
                useDevelop.add(true);
            }else{
                useDevelop.add(false);
            }
        }

        //leader
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

    public void change(MouseEvent mouseEvent) {
        changeResources((ImageView)mouseEvent.getSource());
    }

    public void back(ActionEvent actionEvent) {
        gui.changeScene("playerView");
        clear();
    }

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

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
        resources = gui.getResources();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        devProduction = new ArrayList<>(List.of(dev0, dev1, dev2));
        selectionDev = new ArrayList<>(List.of(dev0sel, dev1sel, dev2sel));
        leadAdditionalProduction = new ArrayList<>(List.of(leader0, leader1));
        selectionLead = new ArrayList<>(List.of(lead0sel, lead1sel));
        resourceLead = new ArrayList<>(List.of(resourceLead0, resourceLead1));
    }
}
