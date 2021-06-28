package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.ConnectionToServer;
import it.polimi.ingsw.Client.GUI.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.Socket;


/**
 * This class represents the controller of the setup scene of the GUI. Implements GUIController.
 * @see GUIController
 *
 * @author Stefano Fossati
 */
public class SetupController implements GUIController{
    private GUI gui;

    @FXML private TextField IPaddress;
    @FXML private TextField portNumber;

    /**
     * Creates a socket from inputs of the user in the TextField elements in the scene.
     * @param actionEvent The event of type ActionEvent.
     */
    public void connect(ActionEvent actionEvent) {
        try {
            Socket socket = new Socket(IPaddress.getText(), Integer.parseInt(portNumber.getText()));
            gui.setConnectionToServer(new ConnectionToServer(socket));
        } catch (IOException e) {
            gui.showAlert(Alert.AlertType.ERROR, "IP address or port not valid");
        }

    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}
