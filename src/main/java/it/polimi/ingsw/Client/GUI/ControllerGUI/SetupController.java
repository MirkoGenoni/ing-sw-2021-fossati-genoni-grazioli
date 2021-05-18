package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.ConnectionToServer;
import it.polimi.ingsw.Client.GUI.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class SetupController implements GUIController, Initializable {
    private GUI gui;

    @FXML AnchorPane anchorPane;
    @FXML ImageView image;
    @FXML TextField IPaddress;
    @FXML TextField portNumber;
    @FXML Button startButton;

    public void connect(ActionEvent actionEvent) {
        try {
            Socket socket = new Socket(IPaddress.getText(), Integer.parseInt(portNumber.getText()));
            gui.setConnectionToServer(new ConnectionToServer(socket));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
