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

public class PlayerNameController implements GUIController, Initializable {
    private GUI gui;

    @FXML Button buttonName;
    @FXML Button buttonRoom;
    @FXML TextField textName;
    @FXML Label text;
    @FXML Label message;
    @FXML CheckBox booleanRoom;

    @FXML Button numPlayer1;
    @FXML Button numPlayer2;
    @FXML Button numPlayer3;
    @FXML Button numPlayer4;

    public void arriveRoomPlayer(String message){
        text.setText(message);
        buttonRoom.setOpacity(1);
        buttonRoom.setDisable(false);
        booleanRoom.setOpacity(1);
        booleanRoom.setDisable(false);
    }

    public void arriveNamePlayer(String message){
        buttonRoom.setOpacity(0);
        booleanRoom.setOpacity(0);
        booleanRoom.setDisable(true);
        buttonRoom.setDisable(true);
        text.setText(message);
        buttonName.setOpacity(1);
        buttonName.setDisable(false);
    }

    public void arriveNumPlayer(String message){
        buttonName.setOpacity(0);
        buttonName.setDisable(true);
        text.setText(message);
        textName.setDisable(true);
        textName.setOpacity(0);
        ArrayList<Button> tmp = new ArrayList<>(List.of(numPlayer1, numPlayer2, numPlayer3, numPlayer4));
        tmp.forEach(but -> but.setDisable(false));
        tmp.forEach(but -> but.setOpacity(1));

    }

    public void sendName(ActionEvent actionEvent) {
        String playerName = textName.getText();
        textName.clear();
        gui.setNamePlayer(playerName);
        gui.getConnectionToServer().sendPlayerName(playerName);
        gui.getConnectionToServer().setPlayerName(playerName);
    }

    public void chooseRoom(ActionEvent actionEvent) {
        int room = Integer.parseInt(textName.getText());
        textName.clear();
        gui.getConnectionToServer().sendRoom(room, booleanRoom.isSelected());
    }

    public void selected(ActionEvent actionEvent) {
        if(booleanRoom.isSelected()){
            booleanRoom.setSelected(true);
        }else{
            booleanRoom.setSelected(false);
        }
    }


    public void sendOne(ActionEvent actionEvent) {
        sendNum(1);
    }

    public void sendTwo(ActionEvent actionEvent) {
        sendNum(2);
    }

    public void sendThree(ActionEvent actionEvent) {
        sendNum(3);
    }

    public void sendFour(ActionEvent actionEvent) {
        sendNum(4);
    }


    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text.setText("Choose your nickname");
    }

    private void sendNum(int num){
        gui.getConnectionToServer().sendNumPlayer(num);
    }



}
