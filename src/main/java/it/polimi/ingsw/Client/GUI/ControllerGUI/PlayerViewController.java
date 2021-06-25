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

import java.net.URL;
import java.util.*;

public class PlayerViewController implements GUIController, Initializable {
    private GUI gui;

    private int faithPosition;
    private int faithLorenzoPosition;

    private int initial = -1;

    private Map<String, Image> marketMarble;
    private Map<String, Image> resources;
    private ArrayList<ImageView> deposit;
    private NewTurnToClient turnTmp;

    private Map<String, Integer> playersFaithTrackPosition = new HashMap<>();
    private Map<String, ImageView> playerMarkerPosition = new HashMap<>();
    private ArrayList<ImageView> faiths;

    private ArrayList<Button> playerButtons;

    private ArrayList<ImageView> devCard = new ArrayList<>();
    private ArrayList<ImageView> devCardPlayer;
    private ArrayList<ImageView> leaderCardPlayer;
    private ArrayList<ImageView> addDeposit1;
    private ArrayList<ImageView> addDeposit0;
    private ArrayList<Label> leaderText;
    private ArrayList<ImageView> popeFavorTiles;

    @FXML private Tab playerBoardView; //TODO fare così

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
    //additional deposit resource
    @FXML ImageView addDeposit00;
    @FXML ImageView addDeposit01;
    @FXML ImageView addDeposit10;
    @FXML ImageView addDeposit11;

    // pope space
    @FXML ImageView pope0;
    @FXML ImageView pope1;
    @FXML ImageView pope2;
    //faith
    @FXML ImageView faith0;
    @FXML ImageView faith1;
    @FXML ImageView faith2;
    @FXML ImageView faith3;
    @FXML ImageView lorenzoFaith;


    // table
    @FXML GridPane gridMarket;
    @FXML ImageView marbleOut;
    @FXML GridPane developmentGrid;
    @FXML AnchorPane playerBoardPane;
    @FXML AnchorPane leaderPane;
    @FXML Button showLeader;
    @FXML Button hideLeader;


    @FXML Button player0;
    @FXML Button player1;
    @FXML Button player2;
    @FXML Button player3;
    @FXML AnchorPane tabTurn;



    public void updatePlayerBoard(Map<String, PlayerInformationToClient> players){
        turnTmp = gui.getLastTurn();
        if(initial==-1){
            int buttonToHide = 4 - players.size();
            for(int i =0; i<buttonToHide; i++){
                playerButtons.get(3-i).setDisable(true);
                playerButtons.get(3-i).setOpacity(0);
            }

            gui.getPlayersName().add(gui.getNamePlayer());
            playerButtons.get(0).setText(gui.getNamePlayer());
            int z=1;
            int i=0;
            for(String s : players.keySet()){
                if(!s.equals(gui.getNamePlayer())){
                    gui.getPlayersName().add(s);
                    playerButtons.get(z).setText(s);
                    z++;
                }
                playersFaithTrackPosition.put(s, 0);
                playerMarkerPosition.put(s, faiths.get(i));
                i++;
            }
            System.out.println("array: " + gui.getPlayersName());

            initial=0;
        }

        //update all faith marker
        for(String s : players.keySet()){
            moveFaith(playerMarkerPosition.get(s), players.get(s).getFaithMarkerPosition(), playersFaithTrackPosition.get(s));
            playersFaithTrackPosition.put(s, players.get(s).getFaithMarkerPosition());
        }

        //visualize player of the client
        drawPlayerBoard(players.get(gui.getNamePlayer()));










    }

    private void drawPlayerBoard(PlayerInformationToClient player){


        // visualize development card of the player
        for(int i=0; i<player.getDevelopmentCardPlayer().size(); i++){
            if(player.getDevelopmentCardPlayer().get(i)!=null){
                devCardPlayer.get(i).setImage(gui.getDevelopmentCardsGraphic().get(player.getDevelopmentCardPlayer().get(i).getCardID()));
                if(devCardPlayer.get(i).getEffect()==null){
                    devCardPlayer.get(i).setEffect(new DropShadow());
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
        leaderCardPlayer.forEach(lead -> lead.setImage(null));
        leaderText.forEach(text -> text.setText(""));
        System.out.println(player.getLeaderCardActive().toString());
        if(player.getLeaderCardActive().size()!=0){
            int z=0;
            for(int i=0; i<player.getLeaderCardActive().size(); i++){
                leaderCardPlayer.get(1-i).setImage(gui.getLeaderCardsGraphic().get(player.getLeaderCardActive().get(i).getNameCard()));
                leaderText.get(1-i).setText("ACTIVE");
                // stampare risorse nei bigger deposit
                if(player.getLeaderCardActive().get(i).getEffect().equals("biggerDeposit")){
                    ArrayList<Resource> tmpR = new ArrayList<>(player.getAdditionalDeposit().subList(0+2*z, 2+2*z));
                    for(int k=0; k< tmpR.size(); k++){
                        if(i==0){
                            if(tmpR.get(k)!=null){
                                addDeposit1.get(k).setImage(gui.getResourcesGraphic().get(tmpR.get(k).name().toLowerCase()));
                            }else{
                                addDeposit1.get(k).setImage(null);
                            }
                        }else{
                            if(tmpR.get(k)!=null){
                                addDeposit0.get(k).setImage(gui.getResourcesGraphic().get(tmpR.get(k).name().toLowerCase()));
                            }else{
                                addDeposit1.get(k).setImage(null);
                            }
                        }


                    }
                    z++;
                }
            }
        }

        //leader card in hand, only for the player of the client
        if(player.getPlayerNameSend().equals(gui.getNamePlayer())){
            drawLeaderCard(gui.getLeaderInHand());
        }

        //visualize pope favor tiles
        for (int i=0; i<player.getPopeFavorTiles().size(); i++){
            if(player.getPopeFavorTiles().get(i)!=0){
                String input = "/graphics/pope/" + popeFavorTiles.get(i).getId() +".png";
                try{
                    Image tmpI = new Image(Objects.requireNonNull(getClass().getResourceAsStream(input)));
                    popeFavorTiles.get(i).setImage(tmpI);
                }catch (NullPointerException e){
                    System.out.println("pope space file not found, address " + input);
                    e.printStackTrace();
                }

            }else{
                popeFavorTiles.get(i).setImage(null);
            }
        }

        //visualize faith marker
        for(String s: playerMarkerPosition.keySet()){
            if(!s.equals(player.getPlayerNameSend())){
                playerMarkerPosition.get(s).setOpacity(0);
            }
            else{
                playerMarkerPosition.get(s).setOpacity(1);
            }
        }
    }

    public void updateTable(DevelopmentCardToClient[][] dev, MarketToClient market){
        devCard.forEach(d -> d.setImage(null));
        // update development card view
        for(int i=0; i< dev.length; i++){
            for(int j=0; j<dev[i].length; j++){
                if(dev[i][j]!=null){
                    ImageView tmp = new ImageView(gui.getDevelopmentCardsGraphic().get(dev[i][j].getCardID()));
                    tmp.setFitHeight(231);
                    tmp.setFitWidth(152);
                    developmentGrid.add(tmp, i, j);
                    devCard.add(tmp);
                }else{
                    ImageView tmp = new ImageView();
                    tmp.setImage(null);
                    developmentGrid.add(tmp, i, j);
                    devCard.add(tmp);
                }
            }
        }

        // update market view
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
        try{
            lorenzoFaith.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/graphics/icons/croce_lorenzo.png"))));
        }catch (NullPointerException e){
            System.out.println("lorenzo action file not found");
            e.printStackTrace();
        }

        moveFaith(lorenzoFaith, lorenzoPosition, faithLorenzoPosition);
        faithLorenzoPosition = lorenzoPosition;
    }




    public void tabTurnNotActive(boolean b){
        tabTurn.setDisable(b);
    }

    public void marketTurn(ActionEvent actionEvent) {
        gui.changeScene("marketView");
        MarketController controller = (MarketController) gui.getCurrentController();
        controller.drawMarket(turnTmp.getMarket().getGrid(), turnTmp.getMarket().getOutMarble(), turnTmp.getPlayers().get(gui.getNamePlayer()).getLeaderCardActive());
    }

    public void buyDevelopmentTurn(ActionEvent actionEvent) {
        gui.changeScene("buyDevelopmentView");
        BuyDevelopmentController controller = (BuyDevelopmentController) gui.getCurrentController();
        controller.drawDevelopment(turnTmp.getDevelopmentCards(), turnTmp.getPlayers().get(gui.getNamePlayer()).getDeposit(), turnTmp.getPlayers().get(gui.getNamePlayer()).getStrongBox());
    }

    public void activateProductionTurn(ActionEvent actionEvent) {
        gui.changeScene("productionView");
        ActivateProductionController controller = (ActivateProductionController) gui.getCurrentController();
        ArrayList<Image> tmp = new ArrayList<>();
        for(int i=0; i<devCardPlayer.size(); i++){
            if(devCardPlayer.get(i).getImage()!=null){
                tmp.add(devCardPlayer.get(i).getImage());
            }else{
                tmp.add(null);
            }
        }
        controller.drawProduction(tmp, turnTmp.getPlayers().get(gui.getNamePlayer()).getDeposit(), turnTmp.getPlayers().get(gui.getNamePlayer()).getStrongBox(), turnTmp.getPlayers().get(gui.getNamePlayer()).getLeaderCardActive());
    }



    public void drawLeaderCard(ArrayList<Image> leaderInHand){
        if(leaderInHand!=null){
            for(int i=0; i<leaderInHand.size(); i++){
                leaderCardPlayer.get(i).setImage(leaderInHand.get(i));
                leaderText.get(i).setText("IN HAND");
            }
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
        marketMarble = gui.getMarblesGraphic();
        resources = gui.getResourcesGraphic();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deposit = new ArrayList<>(List.of(deposit1, deposit2, deposit3, deposit4, deposit5, deposit6));
        devCardPlayer = new ArrayList<>(List.of(devPlayer0, devPlayer1, devPlayer2));
        leaderCardPlayer = new ArrayList<>(List.of(leaderPlayer0, leaderPlayer1));
        addDeposit0 = new ArrayList<>(List.of(addDeposit00, addDeposit01));
        addDeposit1 = new ArrayList<>(List.of(addDeposit10, addDeposit11));
        leaderText = new ArrayList<>(List.of(leaderText0, leaderText1));
        popeFavorTiles = new ArrayList<>(List.of(pope0, pope1, pope2));

        playerButtons = new ArrayList<>(List.of(player0, player1, player2, player3));
        faiths = new ArrayList<>(List.of(faith0, faith1, faith2, faith3));

        faithPosition = 0;
        faithLorenzoPosition = 0;
    }

    public void player0View(ActionEvent actionEvent) {
        drawPlayerBoard(gui.getLastTurn().getPlayers().get(gui.getPlayersName().get(0)));
        setPlayerButtonColor(actionEvent);
    }

    public void player1View(ActionEvent actionEvent) {
        drawPlayerBoard(gui.getLastTurn().getPlayers().get(gui.getPlayersName().get(1)));
        setPlayerButtonColor(actionEvent);
    }

    public void player2View(ActionEvent actionEvent) {
        drawPlayerBoard(gui.getLastTurn().getPlayers().get(gui.getPlayersName().get(2)));
        setPlayerButtonColor(actionEvent);
    }

    public void player3View(ActionEvent actionEvent) {
        drawPlayerBoard(gui.getLastTurn().getPlayers().get(gui.getPlayersName().get(3)));
        setPlayerButtonColor(actionEvent);
    }

    private void setPlayerButtonColor(ActionEvent actionEvent){
        Button tmp = (Button) actionEvent.getSource();
        for(Button b : playerButtons){
            if(tmp.equals(b)){
                b.setStyle("-fx-background-color: #87553f; -fx-text-fill: white");
            }else{
                b.setStyle("-fx-background-color: white; -fx-text-fill: #87553f");            }
        }
    }
}
