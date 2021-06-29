package it.polimi.ingsw.client.gui.controllerGui;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.events.serverToClient.supportClass.LeaderCardToClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * This class represents the controller of the initial leader card scene of the GUI application. Implements GUIController and Initializable interface.
 * @see GUIController
 * @see Initializable
 *
 * @author Stefano Fossati
 */
public class InitialLeaderController implements GUIController, Initializable {
    private GUI gui;
    private ArrayList<ImageView> leader;
    private ArrayList<Integer> selectedLeader = new ArrayList<>();

    private Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML private ImageView leader0;
    @FXML private ImageView leader1;
    @FXML private ImageView leader2;
    @FXML private ImageView leader3;
    @FXML private Button done;
    @FXML private Label waitNotify;

    /**
     * Draws the leader cards on the scene.
     * @param leaderCard The leader cards
     */
    public void drawLeader(ArrayList<LeaderCardToClient> leaderCard){
        for(int i=0; i<leaderCard.size(); i++){
            leader.get(i).setImage(gui.getLeaderCardsGraphic().get(leaderCard.get(i).getNameCard()));
            leader.get(i).setOnMouseClicked(action);
        }
    }

    /**
     * Sends the leader card choose by the user to the connection with the server.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void done(ActionEvent actionEvent) {
        if(selectedLeader.size()>2){
            gui.showAlert(Alert.AlertType.ERROR, "You selects more then two cards to discard");
        }else if(selectedLeader.size()<2){
            gui.showAlert(Alert.AlertType.ERROR, "You selects less then two cards to discard");
        }else if(selectedLeader.size()==2){
            gui.getConnectionToServer().sendDiscardInitialLeaderCards(selectedLeader.get(0), selectedLeader.get(1));
            done.setDisable(true);
            done.setVisible(false);
            waitNotify.setVisible(true);
            leader.forEach(leader -> leader.setDisable(true));
            leader.remove((int)selectedLeader.get(1));
            leader.remove((int)selectedLeader.get(0));
            gui.setLeaderInHand(new ArrayList<Image>(List.of(leader.get(0).getImage(), leader.get(1).getImage())));


        }
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leader = new ArrayList<>(List.of(leader0, leader1, leader2, leader3));
    }

    /**
     * Event for the managing of the selection of the leader cards to discard.
     */
    private EventHandler<MouseEvent> action = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent mouseEvent) {
            Integer i = -1;
            switch (((ImageView) mouseEvent.getSource()).getId()){
                case "leader0":
                    i=0;
                    break;
                case "leader1":
                    i=1;
                    break;
                case "leader2":
                    i=2;
                    break;
                case "leader3":
                    i=3;
                    break;
            }
            int k = i;
            if(!selectedLeader.contains(i)){
                selectedLeader.add(i);
                leader.get(k).setEffect(new DropShadow());
            }else{
                selectedLeader.remove(i);
                leader.get(k).setEffect(null);
            }
        }
    };
}
