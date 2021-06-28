package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import it.polimi.ingsw.Model.Lorenzo.SoloAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * This class represents the controller of the lorenzo scene of the GUI application. Implements GUIController interface.
 * @see GUIController
 *
 * @author Stefano Fossati
 */
public class LorenzoController implements GUIController{
    private GUI gui;
    private int lorenzoPosition;

    @FXML private ImageView soloAction;

    /**
     * Draws the token that Lorenzo has draw and updates the position of lorenzo in the faith track.
     * @param token The token draws by Lorenzo.
     * @param lorenzoPosition The new position of Lorenzo in the faith track.
     */
    public void drawSoloAction(SoloAction token, int lorenzoPosition){
        this.lorenzoPosition = lorenzoPosition;

        String input = "/graphics/soloActionToken/"+ token.name().toLowerCase() + ".png";
        try{
            Image tmpI = new Image(Objects.requireNonNull(getClass().getResourceAsStream(input)));
            soloAction.setImage(tmpI);
        } catch (NullPointerException e) {
            System.out.println("leader card file not found, address " + input);
            e.printStackTrace();
        }
    }

    /**
     * Confirm that the user sees the Lorenzo action and updates the marker position of Lorenzo in the player view scene.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void done(ActionEvent actionEvent) {
        gui.getConnectionToServer().sendReplayLorenzoAction();
        gui.changeScene("playerView");
        PlayerViewController controller = (PlayerViewController) gui.getCurrentController();
        controller.lorenzoFaith(lorenzoPosition);
        controller.tabTurnNotActive(true);
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }


}
