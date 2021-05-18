package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Client.ConnectionToServer;
import it.polimi.ingsw.Client.GUI.ControllerGUI.GUIController;
import it.polimi.ingsw.Events.ServerToClient.NewTurnToClient;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.PlayerInformationToClient;
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
        ArrayList<String> nameScene = new ArrayList<String>(List.of("setup", "playerName", "playerView", "newDepositView",
                "marketView", "buyDevelopmentView", "selectDevSpaceView"));
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
            Image tmpI = new Image(new FileInputStream("src/main/resources/graphics/icons/calamaio.png"));
            this.stage.getIcons().add(tmpI);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        currentScene = scenes.get("setup");
        currentController = controllers.get("setup");
        centerApplication();
        stage.setResizable(false);
        stage.setScene(currentScene);
        stage.show();
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
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}