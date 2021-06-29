package it.polimi.ingsw.client.gui.controllerGui;

import it.polimi.ingsw.client.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * This class represents the controller of the first windows scene of the GUI. Implements GUIController interface.
 * @see GUIController
 *
 * @author Stefano Fossati
 */
public class FirstWindowController implements GUIController{
    private GUI gui;

    @FXML private Button goButton;
    @FXML private ImageView load;

    /**
     * Starts the presentation of the initial graphic of the game.
     */
    public void showPresentation(){
        try{
            ImageView tmp = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/graphics/icons/arrowO.png"))));
            tmp.setPreserveRatio(true);
            tmp.setFitHeight(35);
            goButton.setGraphic(tmp);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            goButton.setVisible(true);
            load.setVisible(false);
        });
        thread.start();

    }

    /**
     * Displays the setup scene.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void go(ActionEvent actionEvent) {
        gui.changeScene("setup");
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }


}
