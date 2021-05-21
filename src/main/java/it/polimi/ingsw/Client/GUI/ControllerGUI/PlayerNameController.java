package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerNameController implements GUIController, Initializable {
    private GUI gui;

    @FXML Button buttonName;
    @FXML Button buttonNum;
    @FXML TextField textName;
    @FXML Label text;

    public void sendName(ActionEvent actionEvent) {
        String playerName = textName.getText();
        textName.clear();
        gui.setNamePlayer(playerName);
        gui.getConnectionToServer().sendNewPlayerName(playerName);
        gui.getConnectionToServer().setPlayerName(playerName);
    }

    public void arriveNumPlayer(){
        buttonName.setOpacity(0);
        buttonName.setDisable(true);
        text.setText("You are the first player!!! Insert number of players");
        buttonNum.setDisable(false);
        buttonNum.setOpacity(1);

    }

    public void sendNum(ActionEvent actionEvent) {
        int num = Integer.parseInt(textName.getText());
        textName.clear();
        gui.getConnectionToServer().sendNumPlayer(num);
    }


    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text.setText("Choose your nickname");
    }
}
