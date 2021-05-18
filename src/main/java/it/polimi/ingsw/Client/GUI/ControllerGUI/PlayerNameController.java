package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PlayerNameController implements GUIController{
    private GUI gui;

    @FXML Button buttonName;
    @FXML Button buttonNum;
    @FXML TextField textName;
    @FXML Label nickname;
    @FXML Label numPlayer;

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }


    public Label getNumPlayer() {
        return numPlayer;
    }




    public void sendName(ActionEvent actionEvent) {
        System.out.println("ok");
        String playerName = textName.getText();
        textName.clear();
        gui.setNamePlayer(playerName);
        gui.getConnectionToServer().sendNewPlayerName(playerName);
        gui.getConnectionToServer().setPlayerName(playerName);
    }

    public void arriveNumPlayer(){
        System.out.println("chiamato");
        buttonName.setOpacity(0);
        buttonName.setDisable(true);
        nickname.setOpacity(0);
        numPlayer.setOpacity(1);
        buttonNum.setDisable(false);
        buttonNum.setOpacity(1);

    }

    public void sendNum(ActionEvent actionEvent) {
        int num = Integer.parseInt(textName.getText());
        textName.clear();
        gui.getConnectionToServer().sendNumPlayer(num);
    }
}
