package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import it.polimi.ingsw.Model.Lorenzo.SoloAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LorenzoViewController implements GUIController{
    private GUI gui;
    private int lorenzoPosition;

    @FXML ImageView soloAction;

    public void drawSoloAction(SoloAction token, int lorenzoPosition){
        this.lorenzoPosition = lorenzoPosition;
        try{
            FileInputStream input = new FileInputStream("src/main/resources/graphics/soloActionToken/" + token.name().toLowerCase() + ".png");
            soloAction.setImage(new Image(input));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void done(ActionEvent actionEvent) {
        gui.getConnectionToServer().sendReplayLorenzoAction();
        gui.changeScene("playerView");
        PlayerViewController controller = (PlayerViewController) gui.getCurrentController();
        controller.lorenzoFaith(lorenzoPosition);
        controller.tabTurnNotActive(true);
    }
}
