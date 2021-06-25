package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.DevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.PlayerInformationToClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class represents the controller of the select development card space scene of the GUI application. Implements GUIController and Initializable interface.
 * @see GUIController
 * @see Initializable
 *
 * @author Stefano Fossati
 */
public class SelectDevelopmentSpaceController implements GUIController, Initializable {
    private GUI gui;
    private ArrayList<ImageView> developSpacePlayer = new ArrayList<>();
    private ArrayList<Integer> spaceSelected = new ArrayList<>();
    private ArrayList<ImageView> selection = new ArrayList<>();

    private ArrayList<Boolean> permittedSpace;

    @FXML private ImageView space0;
    @FXML private ImageView space1;
    @FXML private ImageView space2;
    @FXML private AnchorPane anchorPane;
    @FXML private Label error;
    @FXML private ImageView selection1;
    @FXML private ImageView selection0;
    @FXML private ImageView selection2;

    /**
     * Displays the development card of the player in the right position of the player board.
     * @param permittedSpace The spaces in which the player could put the development card that has bought.
     */
    public void drawDevPlayer(ArrayList<Boolean> permittedSpace){
        this.permittedSpace = permittedSpace;
        PlayerInformationToClient player = gui.getLastTurn().getPlayers().get(gui.getNamePlayer());
        ArrayList<DevelopmentCardToClient> devPlayer = player.getDevelopmentCardPlayer();
        for(int i=0; i<devPlayer.size(); i++){
            if(devPlayer.get(i)!=null){
                developSpacePlayer.get(i).setImage(gui.getDevelopmentCardsGraphic().get(devPlayer.get(i).getCardID()));
            }
            developSpacePlayer.get(i).setEffect(null);
            developSpacePlayer.get(i).setOnMouseClicked(selectDevelopmentSpace);
        }

    }

    /**
     * Sends the position in which the player choose to put the development card that has bought to the connection with the server.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void sendSpaceDevelopment(ActionEvent actionEvent) {
        if(spaceSelected.size()>1){
            error.setText("Select Only One Space");
        }else if(spaceSelected.size()==0){
            error.setText("Select One Space");
        }else if(spaceSelected.size() == 1 && permittedSpace.get(spaceSelected.get(0))){

            gui.changeScene("playerView");
            PlayerViewController controller = (PlayerViewController) gui.getCurrentController();
            controller.tabTurnNotActive(true);
            gui.getConnectionToServer().sendSelectedDevelopmentCardSpace(spaceSelected.get(0));
            spaceSelected.clear();
            developSpacePlayer.forEach(d -> d.setImage(null));
            selection.forEach(s -> s.setOpacity(0));
        }else{
            error.setText("You can't put the card in this position");
        }
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        developSpacePlayer.add(space0);
        developSpacePlayer.add(space1);
        developSpacePlayer.add(space2);

        selection.add(selection0);
        selection.add(selection1);
        selection.add(selection2);
    }

    /**
     * Event for the managing of the development card space selection.
     */
    private EventHandler<MouseEvent> selectDevelopmentSpace = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            int i = -1;
            switch (((ImageView) mouseEvent.getSource()).getId()){
                case "space0":
                    i=0;
                    break;
                case "space1":
                    i=1;
                    break;
                case "space2":
                    i=2;
                    break;
            }
            Integer k = i;
            if(!spaceSelected.contains(i)){
                spaceSelected.add(k);
                selection.get(i).setOpacity(0.65);
                developSpacePlayer.get(i).setEffect(new DropShadow());
            }else{
                spaceSelected.remove(k);
                selection.get(i).setOpacity(0);
                developSpacePlayer.get(i).setEffect(null);
            }
        }
    };
}
