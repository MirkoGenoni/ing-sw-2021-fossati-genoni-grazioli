package it.polimi.ingsw.client.gui.controllergui;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.events.serverToClient.NewTurnToClient;
import it.polimi.ingsw.events.serverToClient.supportclass.DevelopmentCardToClient;
import it.polimi.ingsw.events.serverToClient.supportclass.MarketToClient;
import it.polimi.ingsw.events.serverToClient.supportclass.PlayerInformationToClient;
import it.polimi.ingsw.model.resource.Resource;
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

/**
 * This class represents the controller of the player view scene of the GUI application. Implements GUIController and Initializable interface.
 * This class manage all the information to show to the user.
 * @see GUIController
 * @see Initializable
 *
 * @author Stefano Fossati
 */
public class PlayerViewController implements GUIController, Initializable {
    private GUI gui;

    private int faithLorenzoPosition;
    private int initial = -1;

    private Map<String, Image> marketMarble;
    private Map<String, Image> resources;
    private ArrayList<ImageView> deposit;
    private NewTurnToClient turnTmp;
    private boolean endGame;
    private Map<String, Integer> playersPoints;

    private Map<String, Integer> playersFaithTrackPosition = new HashMap<>();
    private Map<String, ImageView> playerMarkerPosition = new HashMap<>();
    private ArrayList<ImageView> faiths;

    private ArrayList<Image> leaderCardInHand;
    private ArrayList<Button> playerButtons;

    private ArrayList<ImageView> devCard = new ArrayList<>();
    private ArrayList<ImageView> devCardPlayer;
    private ArrayList<ImageView> leaderCardPlayer;
    private ArrayList<ImageView> addDeposit1;
    private ArrayList<ImageView> addDeposit0;
    private ArrayList<Label> leaderText;
    private ArrayList<ImageView> popeFavorTiles;

    // deposit player
    @FXML private ImageView deposit1;
    @FXML private ImageView deposit2;
    @FXML private ImageView deposit3;
    @FXML private ImageView deposit4;
    @FXML private ImageView deposit5;
    @FXML private ImageView deposit6;
    // development of the player
    @FXML private ImageView devPlayer0;
    @FXML private ImageView devPlayer1;
    @FXML private ImageView devPlayer2;
    // strongbox player
    @FXML private Label coin;
    @FXML private Label stone;
    @FXML private Label shield;
    @FXML private Label servant;
    // leader of the player
    @FXML private ImageView leaderPlayer0;
    @FXML private ImageView leaderPlayer1;
    @FXML private Label leaderText0;
    @FXML private Label leaderText1;
    //additional deposit resource
    @FXML private ImageView addDeposit00;
    @FXML private ImageView addDeposit01;
    @FXML private ImageView addDeposit10;
    @FXML private ImageView addDeposit11;

    // pope space
    @FXML private ImageView pope0;
    @FXML private ImageView pope1;
    @FXML private ImageView pope2;
    //faith
    @FXML private ImageView faith0;
    @FXML private ImageView faith1;
    @FXML private ImageView faith2;
    @FXML private ImageView faith3;
    @FXML private ImageView lorenzoFaith;

    //points
    @FXML private Label points;

    // table
    @FXML private GridPane gridMarket;
    @FXML private ImageView marbleOut;
    @FXML private GridPane developmentGrid;
    @FXML private AnchorPane leaderPane;

    //player button
    @FXML private Button player0;
    @FXML private Button player1;
    @FXML private Button player2;
    @FXML private Button player3;
    @FXML private AnchorPane tabTurn;


    /**
     * Updates all the information of all players of the match.
     * @param players The map that contains the information of all players.
     */
    public void updatePlayerBoard(Map<String, PlayerInformationToClient> players){
        turnTmp = gui.getLastTurn();
        leaderCardInHand = new ArrayList<>(gui.getLeaderInHand());
        gui.getLeaderInHand().clear();
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

        //Select the right button
        for(Button b : playerButtons){
            if(b.getText().equals(gui.getNamePlayer())){
                b.setStyle("-fx-background-color: #87553f; -fx-text-fill: white");
            }else{
                b.setStyle("-fx-background-color: white; -fx-text-fill: #87553f");
            }
        }
    }


    /**
     * Updates the information of the game.
     * @param dev The development cards on the table to update.
     * @param market The market structure to update.
     */
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

    /**
     * Updates the stat of Lorenzo in the single player match.
     * @param lorenzoPosition The position of Lorenzo in the faith track.
     */
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

    /**
     * Sets the tab with the turn choice.
     * @param b True to switch off the tub turn, false to switch it on.
     */
    public void tabTurnNotActive(boolean b){
        tabTurn.setDisable(b);
    }


    //------------------------------
    // METHODS FOR GRAPHIC ELEMENTS
    //------------------------------

    /**
     * Changes the scene into the market turn scene.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void marketTurn(ActionEvent actionEvent) {
        gui.changeScene("marketView");
        MarketController controller = (MarketController) gui.getCurrentController();
        controller.drawMarket(turnTmp.getMarket().getGrid(), turnTmp.getMarket().getOutMarble(), turnTmp.getPlayers().get(gui.getNamePlayer()).getLeaderCardActive());
    }

    /**
     * Changes the scene into the buy development card scene.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void buyDevelopmentTurn(ActionEvent actionEvent) {
        gui.changeScene("buyDevelopmentView");
        BuyDevelopmentController controller = (BuyDevelopmentController) gui.getCurrentController();
        controller.drawDevelopment(turnTmp.getDevelopmentCards(), turnTmp.getPlayers().get(gui.getNamePlayer()).getDeposit(), turnTmp.getPlayers().get(gui.getNamePlayer()).getStrongBox(),
                turnTmp.getPlayers().get(gui.getNamePlayer()).getAdditionalDeposit(), turnTmp.getPlayers().get(gui.getNamePlayer()).getLeaderCardActive());
    }
    /**
     * Changes the scene into the activate production scene.
     * @param actionEvent The event of the type ActionEvent.
     */
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
        controller.drawProduction(tmp, turnTmp.getPlayers().get(gui.getNamePlayer()).getDeposit(), turnTmp.getPlayers().get(gui.getNamePlayer()).getStrongBox(),
                turnTmp.getPlayers().get(gui.getNamePlayer()).getAdditionalDeposit(), turnTmp.getPlayers().get(gui.getNamePlayer()).getLeaderCardActive());
    }

    /**
     * Displays the leader card of the player.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void showLeader(ActionEvent actionEvent) {
        leaderPane.setDisable(false);
        leaderPane.setVisible(true);
    }

    /**
     * Hides the leader card of the player.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void hideLeader(ActionEvent actionEvent) {
        leaderPane.setVisible(false);
        leaderPane.setDisable(true);
    }

    /**
     * Save the final points of all players.
     * @param playersPoints The map that contains the points of all players.
     */
    public void saveFinalPoints(Map<String, Integer> playersPoints){
        endGame = true;
        this.playersPoints = playersPoints;
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

        faithLorenzoPosition = 0;
        endGame = false;
    }

    /**
     * Updates the scene with the information of another player.
     * @param actionEvent The event of the type ActionEvent.
     */
    public void playerView(ActionEvent actionEvent) {
        for(int i=0; i<gui.getPlayersName().size(); i++){
            if(((Button)actionEvent.getSource()).getId().equals("player" +i)){
                drawPlayerBoard(gui.getLastTurn().getPlayers().get(gui.getPlayersName().get(i)));
            }
        }
        setPlayerButtonColor(actionEvent);
    }

    //------------------------------
    // PRIVATE METHODS
    //------------------------------

    /**
     * Displays all the information of a specific player.
     * @param player The player that were shown the information.
     */
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
                //draw the resources in the bigger deposit leader card
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
            drawLeaderCard(leaderCardInHand);
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

        //print point
        if(endGame){
            points.setVisible(true);
            int point = playersPoints.get(player.getPlayerNameSend());
            points.setText("Points = " + point);
        }
    }

    /**
     * Updates the leader card that the player has in hand.
     * @param leaderInHand The leader card that the player has in hand.
     */
    private void drawLeaderCard(ArrayList<Image> leaderInHand){

        System.out.println("leaderCardin hand "+  leaderInHand);
        System.out.println("leaderCard hand gui "+ gui.getLeaderInHand());
        if(leaderInHand.size()!=0){
            for(int i=0; i<leaderCardPlayer.size(); i++){
                if(leaderCardPlayer.get(i).getImage()!=null){
                    for(int z=0; z<leaderInHand.size(); z++){
                        if(leaderInHand.get(z).equals(leaderCardPlayer.get(i).getImage())){
                            leaderInHand.set(z, null);
                        }
                    }
                }
            }
            int j=0;
            for(int i=0; i<leaderInHand.size(); i++){
                if(leaderInHand.get(i)!=null){
                    leaderCardPlayer.get(j).setImage(leaderInHand.get(i));
                    leaderText.get(i).setText("IN HAND");
                    j++;
                }
            }

        }
        leaderCardInHand.clear();

    }

    /**
     * Sets the color of the graphics element.
     * @param actionEvent The event of the type ActionEvent.
     */
    private void setPlayerButtonColor(ActionEvent actionEvent){
        Button tmp = (Button) actionEvent.getSource();
        for(Button b : playerButtons){
            if(tmp.equals(b)){
                b.setStyle("-fx-background-color: #87553f; -fx-text-fill: white");
            }else{
                b.setStyle("-fx-background-color: white; -fx-text-fill: #87553f");            }
        }
    }

    /**
     * Move the faith marker  position of the player from the old position to the new position.
     * @param faith The faith marker.
     * @param updateFaithPosition The old position.
     * @param oldPosition The new position.
     */
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
}
