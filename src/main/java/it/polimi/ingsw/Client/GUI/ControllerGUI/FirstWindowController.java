package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class FirstWindowController implements GUIController{
    private GUI gui;

    @FXML private Button goButton;
    @FXML private ImageView load;

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

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void go(ActionEvent actionEvent) {
        gui.changeScene("setup");
    }
}
