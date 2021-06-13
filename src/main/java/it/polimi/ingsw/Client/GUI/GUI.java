package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Client.ConnectionToServer;
import it.polimi.ingsw.Client.GUI.ControllerGUI.GUIController;
import it.polimi.ingsw.Events.ServerToClient.NewTurnToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.LeaderCardToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.PlayerInformationToClient;
import it.polimi.ingsw.Model.Market.Marble;
import it.polimi.ingsw.Model.Resource.Resource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class GUI extends Application {
    private String namePlayer;

    private VisitClass visit;
    private ConnectionToServer connectionToServer;

    private Scene currentScene;
    private GUIController currentController;
    private Stage stage;

    //Graphics
    private Map<String, Image> resourcesGraphic = new HashMap<>();
    private Map<String, Image> marblesGraphic = new HashMap<>();
    private Map<String, Image> leaderCardsGraphic = new HashMap<>();
    private Map<String, Image> developmentCardsGraphic = new HashMap<>();

    //Scene
    private Map<String, Scene> scenes = new HashMap<>();
    private Map<String, GUIController> controllers = new HashMap<>();

    //tmp
    private NewTurnToClient lastTurn;
    private ArrayList<Image> leaderInHand;
    private ArrayList<String> playersName = new ArrayList<>();




    public void setLeaderInHand(ArrayList<Image> leaderInHand) {
        this.leaderInHand = leaderInHand;
    }

    public ArrayList<Image> getLeaderInHand() {
        return leaderInHand;
    }

    public NewTurnToClient getLastTurn() {
        return lastTurn;
    }

    public ArrayList<String> getPlayersName() {
        return playersName;
    }

    public void setLastTurn(NewTurnToClient lastTurn) {
        this.lastTurn = lastTurn;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    public void setConnectionToServer(ConnectionToServer connectionToServer) {
        this.connectionToServer = connectionToServer;
        connectionToServer.setGui(this);
        visit = new VisitClass(connectionToServer, this);
    }

    public Map<String, Image> getResourcesGraphic() {
        return resourcesGraphic;
    }

    public Map<String, Image> getMarblesGraphic() {
        return marblesGraphic;
    }

    public Map<String, Image> getLeaderCardsGraphic() {
        return leaderCardsGraphic;
    }

    public Map<String, Image> getDevelopmentCardsGraphic() {
        return developmentCardsGraphic;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public ConnectionToServer getConnectionToServer() {
        return connectionToServer;
    }

    public VisitClass getVisit() {
        return visit;
    }

    public GUIController getCurrentController() {
        return currentController;
    }

    @Override
    public void start(Stage stage) {
        loadGraphics();
        ArrayList<String> nameScene = new ArrayList<String>(List.of("setup", "playerName", "initialResourcesView","initialLeaderView", "leaderCardView",
                "playerView", "newDepositView", "marketView", "buyDevelopmentView", "selectDevSpaceView", "productionView", "lorenzoView"));
        for (String s : nameScene) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + s + ".fxml"));
            try {
                Scene tmpS = new Scene(loader.load());
                scenes.put(s, tmpS);
                GUIController tmpC = loader.getController();
                tmpC.setGUI(this);
                controllers.put(s, tmpC);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.stage = stage;
        this.stage.setTitle("Maestri Del Rinascimento");
        try{
            Image tmpI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/graphics/icons/calamaio.png")));
            this.stage.getIcons().add(tmpI);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        currentScene = scenes.get("setup");
        currentController = controllers.get("setup");
        stage.setResizable(false);
        stage.setScene(currentScene);
        stage.show();
        centerApplication();

    }

    public void centerApplication() {
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        stage.setX((screenSize.getWidth() - currentScene.getWidth()) / 2);
        stage.setY((screenSize.getHeight() - currentScene.getHeight()) / 2);
    }

    public void changeScene(String s){
        System.out.println("cambio scena");
        currentController = controllers.get(s);
        currentScene = scenes.get(s);
        stage.setResizable(false);
        stage.setScene(currentScene);
        //centerApplication();
        stage.show();
    }

    public Map<String, Integer> calculateTotalResources(ArrayList<Resource> deposit, Map<Resource, Integer> strongBox){
        Map<String, Integer> totalResources = new HashMap<>();
        for(Resource r : strongBox.keySet()){
            totalResources.put(r.name().toLowerCase(), strongBox.get(r));
        }
        for(Resource r : deposit){
            if(r!=null){
                totalResources.put(r.name().toLowerCase(), totalResources.get(r.name().toLowerCase()) + 1);
            }
        }
        return totalResources;
    }

    private void loadGraphics(){
        //load marble
        for(Marble m : Marble.values()){
            String input = "/graphics/marble/" + m.name().toLowerCase() + ".png";
            try {
                Image tmpI = new Image(Objects.requireNonNull(getClass().getResourceAsStream(input)));
                marblesGraphic.put(m.name().toLowerCase(), tmpI);
            } catch (NullPointerException e) {
                System.out.println("marble file not found, address " + input);
                e.printStackTrace();
            }
        }
        //load resource
        for(Resource r : Resource.values()){
            String input = "/graphics/resource/" + r.name().toLowerCase() + ".png";
            try{
                Image tmpI = new Image(Objects.requireNonNull(getClass().getResourceAsStream(input)));
                resourcesGraphic.put(r.name().toLowerCase(), tmpI);
            } catch (NullPointerException e) {
                System.out.println("resource file not found, address: " + input );
                e.printStackTrace();
            }
        }
        //load leader cards
        for(int i=1; i<=16; i++){
            String input = "/graphics/leaderCard/leaderCard"+ i + ".png";
            try{
                Image tmpI = new Image(Objects.requireNonNull(getClass().getResourceAsStream(input)));
                leaderCardsGraphic.put("leaderCard"+i, tmpI);
            } catch (NullPointerException e) {
                System.out.println("leader card file not found, address " + input);
                e.printStackTrace();
            }
        }
        //load development cards
        ArrayList<String> color = new ArrayList<>(List.of("B", "G", "P","Y"));
        for(String s : color){
            for(int i=1; i<=3; i++){
                for(int j=1; j<=4; j++){
                    String input = "/graphics/developmentCard/Dev" + s + "_" + i + j + ".png";
                    try{
                        Image tmpI = new Image(Objects.requireNonNull(getClass().getResourceAsStream(input)));
                        developmentCardsGraphic.put("Dev" + s +"_" + i + j, tmpI);
                    }catch (NullPointerException e){
                        System.out.println("development card file not found, address " + input);
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}