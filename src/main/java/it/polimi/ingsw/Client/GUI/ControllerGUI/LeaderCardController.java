package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.LeaderCardToClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

public class LeaderCardController implements GUIController, Initializable {
    private GUI gui;

    private ArrayList<ImageView> leaderCard;
    private ArrayList<Label> actionLeader;

    private ArrayList<Integer> selectedLeaderToDiscard = new ArrayList<>();
    private ArrayList<Integer> selectedLeaderToActivate = new ArrayList<>();


    @FXML ImageView leader0;
    @FXML ImageView leader1;
    @FXML Label actionLeader0;
    @FXML Label actionLeader1;


    public void drawLeader(ArrayList<LeaderCardToClient> leaderCardAvailable){
        for(int i = 0; i< leaderCardAvailable.size(); i++){
            try {
                FileInputStream input = new FileInputStream("src/main/resources/graphics/leaderCard/" + leaderCardAvailable.get(i).getNameCard() + ".png");
                leaderCard.get(i).setImage(new Image(input));
                leaderCard.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        int i=-1;
                        switch (((ImageView) mouseEvent.getSource()).getId()){
                            case "leader0":
                                i =0;
                                break;
                            case "leader1":
                                i=1;
                                break;
                        }
                        Integer k = i;
                        if(selectedLeaderToActivate.contains(k)){
                            selectedLeaderToActivate.remove(k);
                            selectedLeaderToDiscard.add(k);
                            actionLeader.get(i).setText("DISCARD");
                        }else if(selectedLeaderToDiscard.contains(k)){
                            selectedLeaderToDiscard.remove(k);
                            actionLeader.get(i).setText("");
                            leaderCard.get(i).setEffect(null);
                        }else{
                            selectedLeaderToActivate.add(k);
                            actionLeader.get(i).setText("ACTIVATE");
                            leaderCard.get(i).setEffect(new DropShadow());
                        }
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if(leaderCardAvailable.size()==1){
            leaderCard.get(1).setImage(null);
        }
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leaderCard = new ArrayList<>(List.of(leader0, leader1));
        actionLeader = new ArrayList<>(List.of(actionLeader0, actionLeader1));
    }

    public void done(ActionEvent actionEvent) {
        ArrayList<Image> leaderCardToDraw = new ArrayList<>();
        ArrayList<Integer> sendSelectedLeaderCard = new ArrayList<>();


        for(int i =0; i<leaderCard.size(); i++){
            if(leaderCard.get(i).getImage()!=null){
                Integer k = i;
                if(selectedLeaderToActivate.contains(k)){
                    sendSelectedLeaderCard.add(1);
                }else if(selectedLeaderToDiscard.contains(k)){
                    sendSelectedLeaderCard.add(2);
                }else{
                    sendSelectedLeaderCard.add(0);
                    leaderCardToDraw.add(leaderCard.get(i).getImage());
                }
            }
        }
        leaderCard.forEach(led -> led.setImage(null));
        selectedLeaderToDiscard.clear();
        selectedLeaderToActivate.clear();
        actionLeader.forEach(action -> action.setText(""));
        gui.changeScene("playerView");
        PlayerViewController controller = (PlayerViewController) gui.getCurrentController();
        controller.tabTurnNotActive(true);

        if(leaderCardToDraw.size()>0){
            controller.drawLeaderCard(leaderCardToDraw);
        }


        gui.getConnectionToServer().sendLeaderCardTurn(sendSelectedLeaderCard);
    }
}
