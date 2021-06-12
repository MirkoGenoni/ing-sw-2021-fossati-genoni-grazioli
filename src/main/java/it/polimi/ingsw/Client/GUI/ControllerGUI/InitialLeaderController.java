package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.LeaderCardToClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InitialLeaderController implements GUIController, Initializable {
    private GUI gui;
    private ArrayList<ImageView> leader;

    private ArrayList<Integer> selectedLeader = new ArrayList<>();


    @FXML ImageView leader0;
    @FXML ImageView leader1;
    @FXML ImageView leader2;
    @FXML ImageView leader3;

    @FXML Button done;

    public void drawLeader(ArrayList<LeaderCardToClient> leaderCard){
        for(int i=0; i<leaderCard.size(); i++){
            try {
                FileInputStream input = new FileInputStream("src/main/resources/graphics/LeaderCard/" + leaderCard.get(i).getNameCard() + ".png");
                leader.get(i).setImage(new Image(input));
                leader.get(i).setOnMouseClicked(action);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leader = new ArrayList<>(List.of(leader0, leader1, leader2, leader3));
    }

    public void done(ActionEvent actionEvent) {
        if(selectedLeader.size()>2){
            //error TODO
        }else if(selectedLeader.size()<2){
            // error TODO
        }else if(selectedLeader.size()==2){
            gui.getConnectionToServer().sendDiscardInitialLeaderCards(selectedLeader.get(0), selectedLeader.get(1));
            done.setDisable(true);
            done.setOpacity(0);
            //wait other player
        }
    }

    private EventHandler<MouseEvent> action = new EventHandler<MouseEvent>(){

        @Override
        public void handle(MouseEvent mouseEvent) {
            Integer i = -1;
            switch (((ImageView) mouseEvent.getSource()).getId()){
                case "leader0":
                    i=0;
                    break;
                case "leader1":
                    i=1;
                    break;
                case "leader2":
                    i=2;
                    break;
                case "leader3":
                    i=3;
                    break;
            }
            int k = i;
            if(!selectedLeader.contains(i)){
                selectedLeader.add(i);
                leader.get(k).setEffect(new DropShadow());
            }else{
                selectedLeader.remove(i);
                leader.get(k).setEffect(null);
            }
        }
    };
}
