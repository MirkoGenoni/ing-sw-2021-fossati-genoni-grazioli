package it.polimi.ingsw.client.gui.controllergui;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.resource.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;


/**
 * This class represents the controller of the player name scene of the GUI application. Implements GUIController interface.
 * @see GUIController
 *
 * @author Stefano Fossati
 */
public class InitialResourcesController implements GUIController{
    private GUI gui;
    private ArrayList<Resource> resourcesChoose = new ArrayList<>();
    private int numInitialResources;

    @FXML private Label coinLabel;
    @FXML private Label stoneLabel;
    @FXML private Label shieldLabel;
    @FXML private Label servantLabel;

    @FXML private Label textLabel;

    /**
     * Selects the number of initial resources that the player has to choose.
     * @param num The number of resources that the payer has to choose.
     */
    public void arriveInitialResources(int num){
        this.numInitialResources = num;
        coinLabel.setText("X 0");
        stoneLabel.setText("X 0");
        servantLabel.setText("X 0");
        shieldLabel.setText("X 0");

        textLabel.setText("Choose " + num + " initial resources");
    }

    /**
     * Changes scene into new deposit scene with the initial resources choose by the player.
     * @param actionEvent The event of the type ActionEvent.
     */
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
            controller.drawDeposit(emptyDeposit, resourcesChoose, false, null, null, null, true);
        }
    }

    /**
     * Manages the selection of initial resources by the player.
     * @param mouseEvent The event of the type MouseEvent.
     */
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

    /**
     * Updates the number of resources choose by the player.
     */
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
