package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Client.ConnectionToServer;
import it.polimi.ingsw.Client.GUI.ControllerGUI.FirstWindowController;
import it.polimi.ingsw.Client.GUI.ControllerGUI.GUIController;
import it.polimi.ingsw.Events.ServerToClient.NewTurnToClient;
import it.polimi.ingsw.Model.Market.Marble;
import it.polimi.ingsw.Model.Resource.Resource;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.*;


/**
 * Represents the GUI principal class.
 * Extends Application abstract classes of javaFX library.
 *
 * @see Application
 *
 * @author Stefano Fossati
 */
public class GUI extends Application {
    private String namePlayer;

    private EventHandlerGUI visit;
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

    //---------------------------
    //  SETTERS
    //---------------------------
    /**
     * Sets the name of the player that uses this GUI.
     * @param namePlayer The name of the player.
     */
    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    /**
     * Sets the connection of the GUI with the server.
     * @param connectionToServer The connection with the server.
     */
    public void setConnectionToServer(ConnectionToServer connectionToServer) {
        this.connectionToServer = connectionToServer;
        visit = new EventHandlerGUI(connectionToServer, this);
        connectionToServer.setVisit(visit);
    }

    /**
     * Sets the pleader card image that the player has in hand.
     * @param leaderInHand The leader card that the player has in hand.
     */
    public void setLeaderInHand(ArrayList<Image> leaderInHand) {
        this.leaderInHand = leaderInHand;
    }

    /**
     * Sets the information about the last turn of the game.
     * @param lastTurn The last turn event of the game.
     */
    public void setLastTurn(NewTurnToClient lastTurn) {
        this.lastTurn = lastTurn;
    }

    //---------------------------
    //  GETTERS
    //---------------------------
    /**
     * Getter of the player name that use the GUI.
     * @return The name of the player.
     */
    public String getNamePlayer() {
        return namePlayer;
    }

    /**
     * Getter of the connection of the GUI with the server.
     * @return The connection with the server.
     */
    public ConnectionToServer getConnectionToServer() {
        return connectionToServer;
    }

    /**
     * Getter of the current controller of the current scene displayed by the GUI.
     * @return The current controller of the scene displayed by the GUI.
     */
    public GUIController getCurrentController() {
        return currentController;
    }

    /**
     * Getter of the leader card image that the player has in the hand.
     * @return The leader card images that the player has in hand.
     */
    public ArrayList<Image> getLeaderInHand() {
        return leaderInHand;
    }

    /**
     * Getter of the last turn information of the match.
     * @return The last turn information of the match.
     */
    public NewTurnToClient getLastTurn() {
        return lastTurn;
    }

    /**
     * Getter of the list of all player name of the match.
     * @return The player names of the match.
     */
    public ArrayList<String> getPlayersName() {
        return playersName;
    }

    //--------------------------------------
    //  GETTERS OF THE GRAPHIC RESOURCES
    //--------------------------------------
    /**
     * Getter of the map of the marble graphics.
     * @return The marble graphics.
     */
    public Map<String, Image> getResourcesGraphic() {
        return resourcesGraphic;
    }

    /**
     * Getter of the map of the resource graphics.
     * @return The resource graphics.
     */
    public Map<String, Image> getMarblesGraphic() {
        return marblesGraphic;
    }

    /**
     * Getter of the map of the leader card graphics.
     * @return The leader card graphics.
     */
    public Map<String, Image> getLeaderCardsGraphic() {
        return leaderCardsGraphic;
    }

    /**
     * Getter of the map of the development card graphics.
     * @return The development card graphics
     */
    public Map<String, Image> getDevelopmentCardsGraphic() {
        return developmentCardsGraphic;
    }



    @Override
    public void start(Stage stage) {
        loadGraphics();
        ArrayList<String> nameScene = new ArrayList<String>(List.of("firstWindow","setup", "playerName", "initialResourcesView","initialLeaderView", "leaderCardView",
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

        this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        currentScene = scenes.get("firstWindow");
        currentController = controllers.get("firstWindow");
        stage.setResizable(false);
        stage.setScene(currentScene);
        stage.show();
        centerApplication();
        FirstWindowController firstWindowController = (FirstWindowController) currentController;
        firstWindowController.showPresentation();

    }

    /**
     * Centers the windows of the game in the center of the principal display of the computer.
     */
    public void centerApplication() {
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        stage.setX((screenSize.getWidth() - currentScene.getWidth()) / 2);
        stage.setY((screenSize.getHeight() - currentScene.getHeight()) / 2);
    }

    /**
     * Change the scene and the controller of the actual view of the GUI.
     * @param scene The name of the scene that the GUI has to display.
     */
    public void changeScene(String scene){
        System.out.println("cambio scena");
        currentController = controllers.get(scene);
        currentScene = scenes.get(scene);
        stage.setResizable(false);
        stage.setScene(currentScene);
        //centerApplication();
        stage.show();
    }

    /**
     * Calculates the total resources that a player has and returns a map.
     * @param deposit The deposit of the resources.
     * @param strongBox The strongbox of the resources.
     * @param additionalDeposit The additional deposit of the player.
     * @return The map with the number of resources that the player have in the deposit and in the strongbox.
     */
    public Map<String, Integer> calculateTotalResources(ArrayList<Resource> deposit, Map<Resource, Integer> strongBox, ArrayList<Resource> additionalDeposit){
        Map<String, Integer> totalResources = new HashMap<>();
        for(Resource r : strongBox.keySet()){
            totalResources.put(r.name().toLowerCase(), strongBox.get(r));
        }
        for(Resource r : deposit){
            if(r!=null){
                totalResources.put(r.name().toLowerCase(), totalResources.get(r.name().toLowerCase()) + 1);
            }
        }

        if(additionalDeposit.size()!=0){
            for(Resource r : additionalDeposit){
                if(r!=null){
                    totalResources.put(r.name().toLowerCase(), totalResources.get(r.name().toLowerCase()) + 1);
                }
            }
        }
        return totalResources;
    }

    /**
     * This method load the graphics that the GUI needed in specified structure save in this class.
     */
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
                        developmentCardsGraphic.put("Dev" + s + "_" + i + j, tmpI);
                    }catch (NullPointerException e){
                        System.out.println("development card file not found, address " + input);
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Main method to start application GUI.
     * @param args The args of the main.
     */
    public static void main(String[] args) {
        launch(args);
    }

}