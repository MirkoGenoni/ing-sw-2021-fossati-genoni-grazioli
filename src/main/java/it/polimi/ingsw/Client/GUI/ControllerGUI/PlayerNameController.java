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
import java.util.ResourceBundle;

public class PlayerNameController implements GUIController, Initializable {
    private GUI gui;

    @FXML Button buttonName;
    @FXML Button buttonNum;
    @FXML Button buttonRoom;
    @FXML TextField textName;
    @FXML Label text;
    @FXML Label message;
    @FXML CheckBox booleanRoom;

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
        buttonNum.setDisable(false);
        buttonNum.setOpacity(1);

    }



    public void sendName(ActionEvent actionEvent) {
        String playerName = textName.getText();
        textName.clear();
        gui.setNamePlayer(playerName);
        gui.getConnectionToServer().sendPlayerName(playerName);
        gui.getConnectionToServer().setPlayerName(playerName);
    }


    public void sendNum(ActionEvent actionEvent) {
        int num = Integer.parseInt(textName.getText());
        textName.clear();
        gui.getConnectionToServer().sendNumPlayer(num);
    }

    public void chooseRoom(ActionEvent actionEvent) {
        int room = Integer.parseInt(textName.getText());
        textName.clear();
        gui.getConnectionToServer().sendRoom(room, booleanRoom.isSelected());
    }


    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text.setText("Choose your nickname");
    }


    public void selected(ActionEvent actionEvent) {
        if(booleanRoom.isSelected()){
            booleanRoom.setSelected(true);
        }else{
            booleanRoom.setSelected(false);
        }
    }
}
