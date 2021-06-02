package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Client.ConnectionToServer;
import it.polimi.ingsw.Client.GUI.ControllerGUI.GUIController;
import it.polimi.ingsw.Events.ServerToClient.NewTurnToClient;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI extends Application {
    private String namePlayer;

    private VisitClass visit;
    private ConnectionToServer connectionToServer;

    private Scene currentScene;
    private GUIController currentController;
    private Stage stage;

    //Graphics
    private Map<String, Image> resources = new HashMap<>();
    private Map<String, Image> marble = new HashMap<>();

    //Scene
    private Map<String, Scene> scenes = new HashMap<>();
    private Map<String, GUIController> controllers = new HashMap<>();

    //tmp
    private NewTurnToClient lastTurn;
    private PlayerInformationToClient lastInformation;

    public void setLastInformation(PlayerInformationToClient lastInformation) {
        this.lastInformation = lastInformation;
    }

    public PlayerInformationToClient getLastInformation() {
        return lastInformation;
    }

    public NewTurnToClient getLastTurn() {
        return lastTurn;
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

    public Map<String, Image> getResources() {
        return resources;
    }

    public Map<String, Image> getMarble() {
        return marble;
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
            Image tmpI = new Image(getClass().getResource("/graphics/icons/calamaio.png").openStream());
            this.stage.getIcons().add(tmpI);
        } catch (IOException e) {
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

    private void loadGraphics(){
        for(Marble m : Marble.values()){
            try {
                InputStream input = getClass().getResource("/graphics/marble/" + m.name().toLowerCase() + ".png").openStream();
                Image tmpI = new Image(input);
                marble.put(m.name().toLowerCase(), tmpI);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(Resource r : Resource.values()){
            try{
                InputStream input = getClass().getResource("/graphics/resource/" + r.name().toLowerCase() + ".png").openStream();
                Image tmpI = new Image(input);
                resources.put(r.name().toLowerCase(), tmpI);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}