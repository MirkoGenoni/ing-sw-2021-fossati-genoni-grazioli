package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.DevelopmentCardToClient;
import it.polimi.ingsw.Model.Resource.Resource;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class BuyDevelopmentController implements GUIController, Initializable {
    private GUI gui;
    
    @FXML GridPane gridDev;
    @FXML Button done;
    @FXML Label coin;
    @FXML Label shield;
    @FXML Label stone;
    @FXML Label servant;
    @FXML Label error;

    private ArrayList<String> devSelected = new ArrayList<>();
    private Map<String, Integer> totalResources;
    private ArrayList<ImageView> devImg = new ArrayList<>();

    //tmp
    private DevelopmentCardToClient[][] gridDevelopment;
    
    public void drawDevelopment(DevelopmentCardToClient[][] dev, ArrayList<Resource> deposit, Map<Resource, Integer> strongBox){
        gridDevelopment = dev;
        for(int i=0; i< dev.length; i++){
            for(int j=0; j<dev[i].length; j++){
                if(dev[i][j]!=null){
                    ImageView tmp = new ImageView(gui.getDevelopmentCardsGraphic().get(dev[i][j].getCardID()));
                    tmp.setId(dev[i][j].getCardID());
                    tmp.setFitHeight(231);
                    tmp.setFitWidth(152);
                    gridDev.add(tmp, i, j);
                    devImg.add(tmp);
                    tmp.setOnMouseClicked(selectAction);
                }
            }
        }
        this.totalResources = gui.calculateTotalResources(deposit, strongBox);

        coin.setText("X " + totalResources.get("coin"));
        stone.setText("X " + totalResources.get("stone"));
        shield.setText("X " + totalResources.get("shield"));
        servant.setText("X " + totalResources.get("servant"));
    }



    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void done(ActionEvent actionEvent) {
        int devLevel = -1;
        int devColor = -1;
        DevelopmentCardToClient developmentSelected = null;
        if(devSelected.size()>1){
            error.setText("you must selected only one development card");
        }else if(devSelected.size()==0){
            error.setText("you haven't selected a card");
        }else if(devSelected.size()==1){
            for(int i=0; i<gridDevelopment.length; i++){
                for(int j=0; j<gridDevelopment[i].length; j++){
                    if(gridDevelopment[i][j]!=null){
                        if(gridDevelopment[i][j].getCardID().equals(devSelected.get(0))){
                            developmentSelected = gridDevelopment[i][j];
                            switch (developmentSelected.getColor()){
                                case "GREEN":
                                    devColor = 0;
                                    break;
                                case "BLUE":
                                    devColor = 1;
                                    break;
                                case "YELLOW":
                                    devColor = 2;
                                    break;
                                case "PURPLE":
                                    devColor = 3;
                                    break;
                            }
                        }
                    }
                }
            }
            clear();
            gui.getConnectionToServer().sendSelectedDevelopmentCard(devColor, developmentSelected.getLevel()-1);



        }
    }

    public void back(ActionEvent actionEvent) {
        gui.changeScene("playerView");
        clear();

    }

    private void clear(){
        totalResources.clear();
        devSelected.clear();
        devImg.forEach(dev -> dev.setImage(null));
        devImg.forEach(dev -> dev.setEffect(null));
        devImg.clear();
    }

    private EventHandler<MouseEvent> selectAction = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            ImageView tmp = (ImageView) mouseEvent.getSource();
            if(tmp.getEffect()==null){
                tmp.setEffect(new DropShadow());
                devSelected.add(tmp.getId());
            }else{
                tmp.setEffect(null);
                devSelected.remove(tmp.getId());
            }
        }
    };
}
