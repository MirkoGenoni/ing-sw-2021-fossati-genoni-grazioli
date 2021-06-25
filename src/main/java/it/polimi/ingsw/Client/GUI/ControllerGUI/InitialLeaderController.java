package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.LeaderCardToClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
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

    @FXML private ImageView leader0;
    @FXML private ImageView leader1;
    @FXML private ImageView leader2;
    @FXML private ImageView leader3;
    @FXML private Button done;

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
            //error TODO
        }else if(selectedLeader.size()<2){
            // error TODO
        }else if(selectedLeader.size()==2){
            gui.getConnectionToServer().sendDiscardInitialLeaderCards(selectedLeader.get(0), selectedLeader.get(1));
            done.setDisable(true);
            done.setOpacity(0);
            //wait other player
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
