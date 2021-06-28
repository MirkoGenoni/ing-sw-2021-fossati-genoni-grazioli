package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.LeaderCardToClient;
import it.polimi.ingsw.Model.Market.Marble;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.effect.DropShadow;
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
public class MarketController implements GUIController, Initializable{
    private GUI gui;
    private Map<String, Image> marble = new HashMap<>();
    private ArrayList<ImageView> leaderEffect;

    private ArrayList<LeaderCardToClient> leaderCard;



    @FXML private GridPane gridMarble;
    @FXML private ImageView outMarble;
    @FXML private ImageView leaderCardEffect0;
    @FXML private ImageView leaderCardEffect1;



    /**
     * Draws the structure of the market and manage the leader card active of the player.
     * @param marketState The state of the grid of the market
     * @param marbleOut The marble out of the market
     * @param leaderCardActive The leader card active of the player.
     */
    public void drawMarket(ArrayList<Marble> marketState, Marble marbleOut, ArrayList<LeaderCardToClient> leaderCardActive){
        this.leaderCard = new ArrayList<>(leaderCardActive);
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
        if(leaderCardActive.size()!=0){
            checkLeaderCardMarketWhiteChange(leaderCardActive);
        }

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

    public void selectLeaderEffect(MouseEvent mouseEvent) {
        for(int i=0; i<2; i++){
            if(((ImageView)mouseEvent.getSource()).getId().equals("leaderCardEffect"+i)){
                if(((ImageView)mouseEvent.getSource()).getEffect()==null){
                    ((ImageView)mouseEvent.getSource()).setEffect(new DropShadow());
                }else{
                    ((ImageView)mouseEvent.getSource()).setEffect(null);
                }
            }
        }
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
        marble = gui.getMarblesGraphic();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leaderEffect = new ArrayList<>(List.of(leaderCardEffect0, leaderCardEffect1));
    }

    /**
     * Sends the information to the connection with the server.
     * @param line The number line choose by the user.
     */
    private void sendChooseLine(int line){
        ArrayList<Boolean> tmpEffect = new ArrayList<>();

        if(leaderCard.size()!=0){
            int k=0;
            int z=0;
            for(int i=0; i<leaderCard.size(); i++){
                if(leaderCard.get(i).getEffect().equals("marketWhiteChange")){
                    if(leaderEffect.get(z).getEffect()==null){
                        tmpEffect.add(false);
                    }else{
                        tmpEffect.add(true);
                        k++;
                    }
                    z++;
                }else{
                    tmpEffect.add(false);
                }
            }
            if((z>=1 && k==1) || (z==0 && k==0)){
                if(tmpEffect.size()==leaderCard.size()){
                    System.out.println("OK");
                }
                gui.getConnectionToServer().sendChooseLine(line, tmpEffect);
                leaderEffect.forEach(leader -> leader.setEffect(null));
                leaderEffect.forEach(leader -> leader.setDisable(true));
                leaderEffect.forEach(leader -> leader.setImage(null));
                leaderCard.clear();
            }else{
                gui.showAlert(Alert.AlertType.ERROR, "You have to choose one of the leader card market white change effect");
            }

        }
    }

    /**
     * Draws the leader cards market withe change that the player has active.
     * @param leaderCardsActive The leader card active of the player.
     */
    private void checkLeaderCardMarketWhiteChange(ArrayList<LeaderCardToClient> leaderCardsActive){
        int z=0;
        for(int i = 0; i< leaderCardsActive.size(); i++){
            if(leaderCardsActive.get(i).getEffect().equals("marketWhiteChange")){
                String input = "/graphics/leaderCardEffect/" + leaderCardsActive.get(i).getNameCard() + ".png";
                try{
                    Image tmpI = new Image(Objects.requireNonNull(getClass().getResourceAsStream(input)));
                    leaderEffect.get(z).setImage(tmpI);
                } catch (NullPointerException e) {
                    System.out.println("leader card file not found, address " + input);
                    e.printStackTrace();
                }
                leaderEffect.get(z).setDisable(false);
                z++;
            }
        }
    }

}
