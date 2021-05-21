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

public class MarketViewController implements GUIController, Initializable {
    private GUI gui;
    private Map<String, Image> marble = new HashMap<>();

    @FXML GridPane gridMarble;
    @FXML ImageView outMarble;

    //tmp
    private ArrayList<LeaderCardToClient> leaderCardsActive;

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


    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
        marble = gui.getMarble();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void sendCol4(MouseEvent mouseEvent) {
        sendChooseLine(4);
    }

    public void sendCol5(MouseEvent mouseEvent) {
        sendChooseLine(5);
    }

    public void sendCol6(MouseEvent mouseEvent) {
        sendChooseLine(6);
    }

    public void sendCol7(MouseEvent mouseEvent) {
        sendChooseLine(7);
    }

    public void sendRow1(MouseEvent mouseEvent) {
        sendChooseLine(1);
    }

    public void sendRow2(MouseEvent mouseEvent) {
        sendChooseLine(2);
    }

    public void sendRow3(MouseEvent mouseEvent) {
        sendChooseLine(3);
    }

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

    public void back(ActionEvent actionEvent) {
        gui.changeScene("playerView");
    }
}
