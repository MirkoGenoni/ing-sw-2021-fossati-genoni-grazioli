package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class represents the controller of the player name scene of the GUI application. Implements GUIController and Initializable interface.
 * @see GUIController
 * @see Initializable
 *
 * @author Stefano Fossati
 */
public class PlayerNameController implements GUIController, Initializable {
    private GUI gui;
    private ArrayList<Button> buttonNumPlayer;

    @FXML private Button buttonName;
    @FXML private Button buttonRoom;
    @FXML private TextField textName;
    @FXML private Label text;
    @FXML private Label message;
    @FXML private CheckBox booleanRoom;

    @FXML private Button numPlayer1;
    @FXML private Button numPlayer2;
    @FXML private Button numPlayer3;
    @FXML private Button numPlayer4;


    /**
     * Sets the visibility of some graphics element use to select the room for join or start the game.
     * @param message The message to show in the scene.
     */
    public void arriveRoomPlayer(String message){
        text.setText(message);
        buttonRoom.setOpacity(1);
        buttonRoom.setDisable(false);
        booleanRoom.setOpacity(1);
        booleanRoom.setDisable(false);
    }

    /**
     * Sets the visibility of some graphics element use to write the name of the player.
     * @param message The message to show in the scene.
     */
    public void arriveNamePlayer(String message){
        buttonRoom.setOpacity(0);
        booleanRoom.setOpacity(0);
        booleanRoom.setDisable(true);
        buttonRoom.setDisable(true);
        text.setText(message);
        buttonName.setOpacity(1);
        buttonName.setDisable(false);
    }

    /**
     * Sets the visibility of some graphics element use to set the number of player of the match in which the user is joined.
     * @param message The message to show in the scene.
     */
    public void arriveNumPlayer(String message){
        text.setText(message);
        buttonNumPlayer.forEach(but -> but.setDisable(false));
        buttonNumPlayer.forEach(but -> but.setOpacity(1));

    }

    //------------------------------
    // METHODS FOR GRAPHIC ELEMENTS
    //------------------------------

    /**
     * Parses the room information that the user have typed.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void chooseRoom(ActionEvent actionEvent) {
        int room = Integer.parseInt(textName.getText());
        textName.clear();
        gui.getConnectionToServer().sendRoom(room, booleanRoom.isSelected());
    }

    /**
     * Saves if the user have selected a new room or not
     * @param actionEvent The event of the type ActionEvent.
     */
    public void selected(ActionEvent actionEvent) {
        if(booleanRoom.isSelected()){
            booleanRoom.setSelected(true);
        }else{
            booleanRoom.setSelected(false);
        }
    }

    /**
     * Parses the name of the player and saves it in the GUI application.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void sendName(ActionEvent actionEvent) {
        String playerName = textName.getText();
        textName.clear();
        gui.setNamePlayer(playerName);
        gui.getConnectionToServer().sendPlayerName(playerName);
        gui.getConnectionToServer().setPlayerName(playerName);
        text.setText("Wait other players");
        textName.setOpacity(0);
        textName.setDisable(true);
        buttonName.setOpacity(0);
        buttonName.setDisable(true);

    }


    /**
     * Sends the numbaer of player selected by the user to the connection with the server.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void sendNumPlayer(ActionEvent actionEvent) {
        for(int i=1; i<5; i++){
            if(((Button)actionEvent.getSource()).getId().equals("numPlayer"+i)){
                sendNum(i);
            }
        }
    }


    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text.setText("Choose your nickname");
        buttonNumPlayer = new ArrayList<>(List.of(numPlayer1, numPlayer2, numPlayer3, numPlayer4));
    }

    /**
     * Passes the number of player choose by the user to the connection with the server.
     * @param num The number of player choose by the user.
     */
    private void sendNum(int num){
        gui.getConnectionToServer().sendNumPlayer(num);
        buttonNumPlayer.forEach(but -> but.setOpacity(0));
        buttonNumPlayer.forEach(but -> but.setDisable(true));
        text.setText("Wait other players");
    }



}
