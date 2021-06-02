package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import it.polimi.ingsw.Model.Resource.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class InitialResourcesController implements GUIController{
    private GUI gui;
    private ArrayList<Resource> resourcesChoose = new ArrayList<>();
    private int numInitialResources;

    @FXML Label coinLabel;
    @FXML Label stoneLabel;
    @FXML Label shieldLabel;
    @FXML Label servantLabel;

    public void arriveInitialResources(int num){
        this.numInitialResources = num;
        coinLabel.setText("X 0");
        stoneLabel.setText("X 0");
        servantLabel.setText("X 0");
        shieldLabel.setText("X 0");
    }

    public void orderInitialResources(ActionEvent actionEvent) {
        if(resourcesChoose.size()==numInitialResources){
            gui.changeScene("newDepositView");
            NewDepositController controller = (NewDepositController) gui.getCurrentController();
            ArrayList<Resource> emptyDeposit = new ArrayList<>();
            emptyDeposit.add(null);
            emptyDeposit.add(null);
            emptyDeposit.add(null);
            emptyDeposit.add(null);
            emptyDeposit.add(null);
            emptyDeposit.add(null);
            controller.drawDeposit(emptyDeposit, resourcesChoose, true);
        }
    }

    public void selected(MouseEvent mouseEvent) {
        String id = ((ImageView) mouseEvent.getSource()).getId();
        resourcesChoose.add(Resource.valueOf(id.toUpperCase()));
        if(resourcesChoose.size()>numInitialResources){
            resourcesChoose.clear();
        }
        updateLabel();
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    private void updateLabel(){
        int coin = 0;
        int shield = 0;
        int servant = 0;
        int stone = 0;
        for(int i=0; i<resourcesChoose.size(); i++){
            if(resourcesChoose.get(i).equals(Resource.COIN)){
                coin++;
            }else if(resourcesChoose.get(i).equals(Resource.STONE)){
                stone++;
            }else if(resourcesChoose.get(i).equals(Resource.SHIELD)){
                shield++;
            }else if(resourcesChoose.get(i).equals(Resource.SERVANT)){
                servant++;
            }
        }
        coinLabel.setText("X " + coin);
        stoneLabel.setText("X " + stone);
        servantLabel.setText("X " + servant);
        shieldLabel.setText("X " + shield);


    }


}
