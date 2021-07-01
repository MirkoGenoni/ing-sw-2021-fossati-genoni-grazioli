package it.polimi.ingsw.client.gui.controllergui;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.events.servertoclient.supportclass.LeaderCardToClient;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class represents the controller of the leader card scene of the GUI application. Implements GUIController and Initializable interface.
 * @see GUIController
 * @see Initializable
 *
 * @author Stefano Fossati
 */
public class LeaderCardController implements GUIController, Initializable {
    private GUI gui;

    private ArrayList<ImageView> leaderCard;
    private ArrayList<Label> actionLeader;

    private ArrayList<Integer> selectedLeaderToDiscard = new ArrayList<>();
    private ArrayList<Integer> selectedLeaderToActivate = new ArrayList<>();

    //tmp
    private boolean isFinal;


    @FXML private ImageView leader0;
    @FXML private ImageView leader1;
    @FXML private Label actionLeader0;
    @FXML private Label actionLeader1;

    /**
     * Draws the leader cards in the scene.
     * @param leaderCardAvailable The leader cards.
     */
    public void drawLeader(ArrayList<LeaderCardToClient> leaderCardAvailable, boolean isFinal){
        this.isFinal = isFinal;
        for(int i = 0; i< leaderCardAvailable.size(); i++){
            leaderCard.get(i).setImage(gui.getLeaderCardsGraphic().get(leaderCardAvailable.get(i).getNameCard()));
            leaderCard.get(i).setOnMouseClicked(selectLeaderCard);
            leaderCard.get(i).setEffect(null);
        }

        if(leaderCardAvailable.size()==1){
            leaderCard.get(1).setImage(null);
            leaderCard.get(1).setDisable(true);
            leaderCard.get(1).setEffect(null);
        }
    }


    /**
     * Sends the action that done the user on the leader card to the connection with the server.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void done(ActionEvent actionEvent) {
        ArrayList<Image> leaderCardToDraw = new ArrayList<>();
        ArrayList<Integer> sendSelectedLeaderCard = new ArrayList<>();
        for(int i =0; i<leaderCard.size(); i++){
            if(leaderCard.get(i).getImage()!=null){
                Integer k = i;
                if(selectedLeaderToActivate.contains(k)){
                    leaderCardToDraw.add(leaderCard.get(i).getImage());
                    sendSelectedLeaderCard.add(1);
                }else if(selectedLeaderToDiscard.contains(k)){
                    sendSelectedLeaderCard.add(2);
                }else{
                    sendSelectedLeaderCard.add(0);
                    leaderCardToDraw.add(leaderCard.get(i).getImage());
                }
            }
        }
        leaderCard.forEach(led -> led.setImage(null));
        selectedLeaderToDiscard.clear();
        selectedLeaderToActivate.clear();
        actionLeader.forEach(action -> action.setText(""));
        gui.changeScene("playerView");
        PlayerViewController controller = (PlayerViewController) gui.getCurrentController();
        controller.tabTurnNotActive(true);
        gui.setLeaderInHand(leaderCardToDraw);
        gui.getConnectionToServer().sendLeaderCardActions(sendSelectedLeaderCard, isFinal);
    }



    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leaderCard = new ArrayList<>(List.of(leader0, leader1));
        actionLeader = new ArrayList<>(List.of(actionLeader0, actionLeader1));
    }

    /**
     * Event for the managing of the leader card action.
     */
    private EventHandler<MouseEvent> selectLeaderCard = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            int i=-1;
            switch (((ImageView) mouseEvent.getSource()).getId()){
                case "leader0":
                    i =0;
                    break;
                case "leader1":
                    i=1;
                    break;
            }
            Integer k = i;
            if(selectedLeaderToActivate.contains(k)){
                selectedLeaderToActivate.remove(k);
                selectedLeaderToDiscard.add(k);
                actionLeader.get(i).setText("DISCARD");
            }else if(selectedLeaderToDiscard.contains(k)){
                selectedLeaderToDiscard.remove(k);
                actionLeader.get(i).setText("");
                leaderCard.get(i).setEffect(null);
            }else{
                selectedLeaderToActivate.add(k);
                actionLeader.get(i).setText("ACTIVATE");
                leaderCard.get(i).setEffect(new DropShadow());
            }
        }
    };
}
