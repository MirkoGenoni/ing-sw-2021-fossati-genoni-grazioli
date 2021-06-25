package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.LeaderCardToClient;
import it.polimi.ingsw.Model.Market.Marble;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;


/**
 * This class represents the controller of the market scene of the GUI application. Implements GUIController interface.
 * @see GUIController
 *
 * @author Stefano Fossati
 */
public class MarketController implements GUIController{
    private GUI gui;
    private Map<String, Image> marble = new HashMap<>();

    //tmp
    private ArrayList<LeaderCardToClient> leaderCardsActive;

    @FXML private GridPane gridMarble;
    @FXML private ImageView outMarble;


    /**
     * Draws the structure of the market and manage the leader card active of the player.
     * @param marketState The state of the grid of the market
     * @param marbleOut The marble out of the market
     * @param leaderCardActive The leader card active of the player.
     */
    public void drawMarket(ArrayList<Marble> marketState, Marble marbleOut, ArrayList<LeaderCardToClient> leaderCardActive){
        this.leaderCardsActive = leaderCardActive;
        int k=0;
        int z=0;
        for(int i= 0; i<marketState.size(); i++){
            if(i==4 || i==8){
                z++;
                k=0;
            }
            ImageView tmpI = new ImageView(marble.get(marketState.get(i).name().toLowerCase()));
            tmpI.setFitHeight(100);
            tmpI.setFitWidth(100);
            gridMarble.add(tmpI, k, z);
            k++;
        }
        outMarble.setFitHeight(100);
        outMarble.setFitWidth(100);
        outMarble.setImage(marble.get(marbleOut.name().toLowerCase()));
    }

    /**
     * Turns back to the player view scene.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void back(ActionEvent actionEvent) {
        gui.changeScene("playerView");
    }

    /**
     * Selects the line choose by the user.
     * @param mouseEvent The event of the type MouseEvent.
     */
    public void send(MouseEvent mouseEvent) {
        for(int i=1; i<8; i++){
            if(((ImageView)mouseEvent.getSource()).getId().equals("send"+i)){
                sendChooseLine(i);
            }
        }
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
        marble = gui.getMarblesGraphic();
    }

    /**
     * Sends the information to the connection with the server.
     * @param line The number line choose by the user.
     */
    private void sendChooseLine(int line){
        ArrayList<Boolean> tmp = new ArrayList<>();
        tmp.add(false);
        tmp.add(false);
        gui.getConnectionToServer().sendChooseLine(line, tmp);
    }

    //TODO da implementare controllo
    private void checkLeaderCardMarketWhiteChange(){
        for(int i = 0; i< leaderCardsActive.size(); i++){
            if(leaderCardsActive.get(i).getEffect().equals("marketWhiteChange")){

            }
        }
    }


}
