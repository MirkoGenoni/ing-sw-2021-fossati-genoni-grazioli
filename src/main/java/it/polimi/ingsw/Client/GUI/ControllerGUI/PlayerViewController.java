package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import it.polimi.ingsw.Events.ServerToClient.NewTurnToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.DevelopmentCardToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.MarketToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.PlayerInformationToClient;
import it.polimi.ingsw.Model.Resource.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PlayerViewController implements GUIController, Initializable {
    private GUI gui;

    private int faithPosition;
    private int faithLorenzoPosition;

    private Map<String, Image> marketMarble;
    private Map<String, Image> resources;
    private ArrayList<ImageView> deposit;
    private NewTurnToClient turnTmp;


    private ArrayList<ImageView> devCard = new ArrayList<>();
    private ArrayList<ImageView> devCardPlayer;
    private ArrayList<ImageView> leaderCardPlayer;
    private ArrayList<Label> leaderText;
    private ArrayList<ImageView> popeFavorTiles;


    @FXML Label name;
    // deposit player
    @FXML ImageView deposit1;
    @FXML ImageView deposit2;
    @FXML ImageView deposit3;
    @FXML ImageView deposit4;
    @FXML ImageView deposit5;
    @FXML ImageView deposit6;
    // development of the player
    @FXML ImageView devPlayer0;
    @FXML ImageView devPlayer1;
    @FXML ImageView devPlayer2;
    // strongbox player
    @FXML Label coin;
    @FXML Label stone;
    @FXML Label shield;
    @FXML Label servant;
    // leader of the player
    @FXML ImageView leaderPlayer0;
    @FXML ImageView leaderPlayer1;
    @FXML Label leaderText0;
    @FXML Label leaderText1;
    // pope space
    @FXML ImageView pope0;
    @FXML ImageView pope1;
    @FXML ImageView pope2;
    //faith
    @FXML ImageView faith0;
    @FXML ImageView lorenzoFaith;


    // table
    @FXML GridPane gridMarket;
    @FXML ImageView marbleOut;
    @FXML GridPane developmentGrid;
    @FXML AnchorPane playerBoardPane;
    @FXML AnchorPane leaderPane;
    @FXML Button showLeader;
    @FXML Button hideLeader;

    @FXML AnchorPane tabTurn;



    public void updatePlayerBoard(ArrayList<PlayerInformationToClient> players){
        turnTmp = gui.getLastTurn();
        PlayerInformationToClient player = players.get(findPlayerIndex());
        gui.setLastInformation(player);

        name.setText(player.getPlayerNameSend());
        // visualize development card of the player
        for(int i=0; i<player.getDevelopmentCardPlayer().size(); i++){
            if(player.getDevelopmentCardPlayer().get(i)!=null){
                try {
                    FileInputStream input = new FileInputStream("src/main/resources/graphics/developmentCard/" + player.getDevelopmentCardPlayer().get(i).getCardID() + ".png");
                    devCardPlayer.get(i).setImage(new Image(input));
                    if(devCardPlayer.get(i).getEffect()==null){
                        devCardPlayer.get(i).setEffect(new DropShadow());
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }else{
                devCardPlayer.get(i).setImage(null);
                devCardPlayer.get(i).setEffect(null);
            }
        }
        // visualize deposit
        for(int i=0; i<player.getDeposit().size(); i++){
            if(player.getDeposit().get(i)!=null){
                deposit.get(i).setImage(resources.get(player.getDeposit().get(i).name().toLowerCase()));
            }else{
                deposit.get(i).setImage(null);
            }
        }
        // visualize strongBox
        coin.setText("X " + player.getStrongBox().get(Resource.COIN));
        stone.setText("X " + player.getStrongBox().get(Resource.STONE));
        shield.setText("X " + player.getStrongBox().get(Resource.SHIELD));
        servant.setText("X " + player.getStrongBox().get(Resource.SERVANT));

        //visualize leaderCard;
        // active
        System.out.println(player.getLeaderCardActive().toString());
        if(player.getLeaderCardActive().size()!=0){
            for(int i=0; i<player.getLeaderCardActive().size(); i++){
                try {
                    FileInputStream input = new FileInputStream("src/main/resources/graphics/leaderCard/" + player.getLeaderCardActive().get(i).getNameCard() + ".png");
                    leaderCardPlayer.get(1-i).setImage(new Image(input));
                    leaderText.get(1-i).setText("ACTIVE");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        //visualize pope favor tiles
        for (int i=0; i<player.getPopeFavorTiles().size(); i++){
            if(player.getPopeFavorTiles().get(i)!=0){
                try {
                    FileInputStream input = new FileInputStream("src/main/resources/graphics/pope/" + popeFavorTiles.get(i).getId() +".png");
                    popeFavorTiles.get(i).setImage(new Image(input));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else{
                popeFavorTiles.get(i).setImage(null);
            }
        }

        // visualzie faith position
        moveFaith(faith0, player.getFaithMarkerPosition(), faithPosition);
        faithPosition = player.getFaithMarkerPosition();



    }

    public void updateTable(DevelopmentCardToClient[][] dev, MarketToClient market){
        devCard.forEach(d -> d.setImage(null));
        //aggiorno la visione delle developmentCard
        for(int i=0; i< dev.length; i++){
            for(int j=0; j<dev[i].length; j++){
                if(dev[i][j]!=null){
                    FileInputStream input = null;
                    try {
                        input = new FileInputStream("src/main/resources/graphics/developmentCard/" + dev[i][j].getCardID() +".png");
                        ImageView tmp = new ImageView(new Image(input));
                        tmp.setFitHeight(231);
                        tmp.setFitWidth(152);
                        developmentGrid.add(tmp, i, j);
                        devCard.add(tmp);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }else{
                    ImageView tmp = new ImageView();
                    tmp.setImage(null);
                    developmentGrid.add(tmp, i, j);
                    devCard.add(tmp);
                }
            }
        }

        // aggiorno la visone del market
        int k=0;
        int z=0;
        for(int i= 0; i<market.getGrid().size(); i++){
            if(i==4 || i==8){
                z++;
                k=0;
            }
            ImageView tmpI = new ImageView(marketMarble.get(market.getGrid().get(i).name().toLowerCase()));
            tmpI.setFitHeight(75);
            tmpI.setFitWidth(75);
            gridMarket.add(tmpI, k, z);
            k++;
        }
        marbleOut.setFitHeight(75);
        marbleOut.setFitWidth(75);
        marbleOut.setImage(marketMarble.get(market.getOutMarble().name().toLowerCase()));
    }

    public void lorenzoFaith(int lorenzoPosition){
        try {
            lorenzoFaith.setImage(new Image(getClass().getResource("/graphics/icons/croce_lorenzo.png").openStream()));
            moveFaith(lorenzoFaith, lorenzoPosition, faithLorenzoPosition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        faithLorenzoPosition = lorenzoPosition;
    }




    public void tabTurnNotActive(boolean b){
        tabTurn.setDisable(b);
    }

    public void marketTurn(ActionEvent actionEvent) {
        gui.changeScene("marketView");
        MarketController controller = (MarketController) gui.getCurrentController();
        int numPlayer = findPlayerIndex();
        controller.drawMarket(turnTmp.getMarket().getGrid(), turnTmp.getMarket().getOutMarble(), turnTmp.getPlayers().get(numPlayer).getLeaderCardActive());
    }

    public void buyDevelopmentTurn(ActionEvent actionEvent) {
        gui.changeScene("buyDevelopmentView");
        BuyDevelopmentController controller = (BuyDevelopmentController) gui.getCurrentController();
        int numPlayer = findPlayerIndex();
        controller.drawDevelopment(turnTmp.getDevelopmentCards(), turnTmp.getPlayers().get(numPlayer).getDeposit(), turnTmp.getPlayers().get(numPlayer).getStrongBox());
    }

    public void activateProductionTurn(ActionEvent actionEvent) {
        gui.changeScene("productionView");
        ActivateProductionController controller = (ActivateProductionController) gui.getCurrentController();
        int numPlayer = findPlayerIndex();
        ArrayList<Image> tmp = new ArrayList<>();
        for(int i=0; i<devCardPlayer.size(); i++){
            if(devCardPlayer.get(i).getImage()!=null){
                tmp.add(devCardPlayer.get(i).getImage());
            }else{
                tmp.add(null);
            }
        }
        controller.drawProduction(tmp, turnTmp.getPlayers().get(numPlayer).getDeposit(), turnTmp.getPlayers().get(numPlayer).getStrongBox());
    }



    public void drawLeaderCard(ArrayList<Image> leaderInHand){
        for(int i=0; i<leaderInHand.size(); i++){
            leaderCardPlayer.get(i).setImage(leaderInHand.get(i));
            leaderText.get(i).setText("IN HAND");
        }
    }

    public void showLeader(ActionEvent actionEvent) {
        leaderPane.setDisable(false);
        leaderPane.setVisible(true);

    }

    public void hideLeader(ActionEvent actionEvent) {
        leaderPane.setVisible(false);
        leaderPane.setDisable(true);
    }

    private int findPlayerIndex(){
        int numPlayer = -1;
        for(int i=0; i<turnTmp.getPlayers().size(); i++){
            if(turnTmp.getPlayers().get(i).getPlayerNameSend().equals(gui.getNamePlayer())){
                numPlayer = i;
            }
        }
        return numPlayer;
    }

    private void moveFaith(ImageView faith, int updateFaithPosition,int  oldPosition){
        if(updateFaithPosition-oldPosition>0){
            for(int k= oldPosition; k<updateFaithPosition; k++){
                if(k<2 || k>=4 && k<9 || k>=11 && k<16 || k>=18 && k<=24){
                    faith.setLayoutX(faith.getLayoutX() + 59);
                }else if(k>=2 && k<4 || k>=16 && k<18){
                    faith.setLayoutY(faith.getLayoutY() - 58);
                }else if(k>=9 && k<11){
                    faith.setLayoutY(faith.getLayoutY() + 58);
                }
            }
        }
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
        marketMarble = gui.getMarble();
        resources = gui.getResources();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deposit = new ArrayList<>(List.of(deposit1, deposit2, deposit3, deposit4, deposit5, deposit6));
        devCardPlayer = new ArrayList<>(List.of(devPlayer0, devPlayer1, devPlayer2));
        leaderCardPlayer = new ArrayList<>(List.of(leaderPlayer0, leaderPlayer1));
        leaderText = new ArrayList<>(List.of(leaderText0, leaderText1));
        popeFavorTiles = new ArrayList<>(List.of(pope0, pope1, pope2));

        faithPosition = 0;
        faithLorenzoPosition = 0;
    }
}
