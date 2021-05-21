package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.DevelopmentCardToClient;
import it.polimi.ingsw.Model.DevelopmentCard.ProductedMaterials;
import it.polimi.ingsw.Model.Resource.Resource;
import javafx.beans.binding.BooleanExpression;
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

public class ActivateProductionController implements GUIController, Initializable {
    private GUI gui;

    Map <String,Image> resources;

    ArrayList<ImageView> devProduction;
    ArrayList<ImageView> selectionDev;

    Map<String, Integer> totalResources = new HashMap<>();

    ArrayList<Integer> devSelected = new ArrayList<>();


    @FXML ImageView dev0;
    @FXML ImageView dev1;
    @FXML ImageView dev2;

    @FXML ImageView dev0sel;
    @FXML ImageView dev1sel;
    @FXML ImageView dev2sel;

    @FXML Label coin;
    @FXML Label stone;
    @FXML Label shield;
    @FXML Label servant;

    @FXML ImageView resource1;
    @FXML ImageView resource2;
    @FXML ImageView resourceProduced;


    public void drawProduction(ArrayList<Image> devPlayer, ArrayList<Resource> deposit, Map<Resource, Integer> strongBox){

        // calculate total resources
        for(Resource r : strongBox.keySet()){
            totalResources.put(r.name().toLowerCase(), strongBox.get(r));
        }
        for(Resource r : deposit){
            if(r!=null){
                totalResources.put(r.name().toLowerCase(), totalResources.get(r.name().toLowerCase()) + 1);
            }
        }
        //TODO creare metodo

        coin.setText("X " + totalResources.get("coin"));
        stone.setText("X " + totalResources.get("stone"));
        shield.setText("X " + totalResources.get("shield"));
        servant.setText("X " + totalResources.get("servant"));

        //draw development
        for(int i=0; i<devPlayer.size(); i++){
            devProduction.get(i).setImage(devPlayer.get(i));
            if(devPlayer.get(i)!=null){
                devProduction.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
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
                        Integer k = i;
                        if(devSelected.contains(k)){
                            devSelected.remove(k);
                            devProduction.get(i).setEffect(null);
                            selectionDev.get(i).setOpacity(0);

                        }else{
                            devSelected.add(k);
                            devProduction.get(i).setEffect(new DropShadow());
                            selectionDev.get(i).setOpacity(0.65);
                        }
                    }
                });
            }
        }






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

        ArrayList<Boolean> leaderTmp = new ArrayList<>();
        ArrayList<Resource> leaderRtmp = new ArrayList<>();
        gui.changeScene("playerView");
        PlayerViewController controller = (PlayerViewController) gui.getCurrentController();
        controller.tabTurnNotActive(true);
        gui.getConnectionToServer().sendSelectedProductionDevelopmentCard(baseProduction, resourceChoose1, resourceChoose2, resourceGranted, leaderTmp, leaderRtmp, useDevelop);
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

    }

    public void change(MouseEvent mouseEvent) {
        changeResources((ImageView)mouseEvent.getSource());
    }
}
