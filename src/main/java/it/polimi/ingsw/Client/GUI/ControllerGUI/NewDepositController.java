package it.polimi.ingsw.Client.GUI.ControllerGUI;

import it.polimi.ingsw.Client.GUI.GUI;
import it.polimi.ingsw.Events.ServerToClient.SupportClass.LeaderCardToClient;
import it.polimi.ingsw.Model.Resource.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;


public class NewDepositController implements GUIController, Initializable {
    private GUI gui;

    private boolean isInitial;

    private Map<String, Image> resources;
    private ArrayList<ImageView> depositImg;
    private ArrayList<ImageView> leaderDeposit0;
    private ArrayList<ImageView> leaderDeposit1;
    private ArrayList<ImageView> leaderAdditional;
    private ArrayList<Integer> numDepositClicked = new ArrayList<>();
    private ArrayList<String> resourceClicked = new ArrayList<>();

    @FXML ImageView deposit1;
    @FXML ImageView deposit2;
    @FXML ImageView deposit3;
    @FXML ImageView deposit4;
    @FXML ImageView deposit5;
    @FXML ImageView deposit6;

    //additional deposit leader
    @FXML ImageView leader0;
    @FXML ImageView leader1;

    @FXML ImageView addDeposit00;
    @FXML ImageView addDeposit01;
    @FXML ImageView addDeposit10;
    @FXML ImageView addDeposit11;


    @FXML Label numCoin;
    @FXML Label numStone;
    @FXML Label numShield;
    @FXML Label numServant;

    //tmp
    private Map<String, Integer> marketReceive = new HashMap<>();
    private ArrayList<Resource> depositState = new ArrayList<>();
    private ArrayList<Resource> addDepositType;
    private boolean isAdditional;

    //risorse scartate dal market

    private int getMarketResource(){
        int num =0;
        for(String s : marketReceive.keySet()){
            if(!s.equals("nothing")){
                num = num + marketReceive.get(s);
            }
        }
        return num;
    }


    public void drawDeposit(ArrayList<Resource> depositState, ArrayList<Resource> marketReceived, boolean isAdditional,
                            ArrayList<Resource> addDepositType, ArrayList<Resource> addDepositState, ArrayList<LeaderCardToClient> leaderCards, boolean isInitial){
        this.isInitial = isInitial;
        this.depositState = depositState;
        this.isAdditional = isAdditional;

        if(isAdditional){
            // draw leader
            int i =0;
            for(LeaderCardToClient leader : leaderCards){
                if(leader.getEffect().equals("biggerDeposit")){
                    this.leaderAdditional.get(i).setImage(gui.getLeaderCardsGraphic().get(leader.getNameCard()));
                    i++;
                }
            }

            this.addDepositType = addDepositType;
            depositState.addAll(addDepositState);
            if(addDepositType.size()==1){
                System.out.println(addDepositState);
                leaderDeposit0.forEach(img -> img.setDisable(false));
                depositImg.addAll(leaderDeposit0);
            }else{
                System.out.println(addDepositState);
                leaderDeposit0.forEach(img -> img.setDisable(false));
                leaderDeposit1.forEach(img -> img.setDisable(false));
                depositImg.addAll(leaderDeposit0);
                depositImg.addAll(leaderDeposit1);

            }

        }

        for(int i=0; i<depositState.size(); i++){
            if(depositState.get(i)!=null){
                depositImg.get(i).setImage(resources.get(depositState.get(i).name().toLowerCase()));
            }else{
                depositImg.get(i).setImage(null);
            }
        }

        for(String s : resources.keySet()){
            marketReceive.put(s, 0);
        }
        marketReceive.put("nothing", 6);
        for(Resource r : marketReceived){
            marketReceive.put(r.name().toLowerCase(), marketReceive.get(r.toString().toLowerCase())+1);
        }
        numShield.setText("X " + marketReceive.get("shield"));
        numStone.setText("X " + marketReceive.get("stone"));
        numServant.setText("X " + marketReceive.get("servant"));
        numCoin.setText("X " + marketReceive.get("coin"));

        System.out.println(marketReceive.toString());


    }


    public void save(MouseEvent mouseEvent) {
        switch (((ImageView) mouseEvent.getSource()).getId()){
            case "deposit1":
                numDepositClicked.add(0);
                break;
            case "deposit2":
                numDepositClicked.add(1);
                break;
            case "deposit3":
                numDepositClicked.add(2);
                break;
            case "deposit4":
                numDepositClicked.add(3);
                break;
            case "deposit5":
                numDepositClicked.add(4);
                break;
            case "deposit6":
                numDepositClicked.add(5);
                break;
            case "stoneImg":
                resourceClicked.add("stone");
                break;
            case "shieldImg":
                resourceClicked.add("shield");
                break;
            case "servantImg":
                resourceClicked.add("servant");
                break;
            case "coinImg":
                resourceClicked.add("coin");
                break;
            case "addDeposit00":
                numDepositClicked.add(6);
                break;
            case "addDeposit01":
                numDepositClicked.add(7);
                break;
            case "addDeposit10":
                numDepositClicked.add(8);
                break;
            case "addDeposit11":
                numDepositClicked.add(9);
                break;
            //TODO reduce code and add comment

        }

        if(numDepositClicked.size()+resourceClicked.size() == 2) {
            if (numDepositClicked.size() == 1) {
                modifyDeposit(resourceClicked.get(0), numDepositClicked.get(0));
            } else if (numDepositClicked.size() == 2) {
                if (numDepositClicked.get(0) == numDepositClicked.get(1)) {
                    modifyDeposit("nothing", numDepositClicked.get(0));
                } else {
                    Resource tmp = depositState.get(numDepositClicked.get(0));
                    depositState.set(numDepositClicked.get(0), depositState.get(numDepositClicked.get(1)));
                    depositState.set(numDepositClicked.get(1), tmp);

                    Image tmpI = depositImg.get(numDepositClicked.get(0)).getImage();
                    depositImg.get(numDepositClicked.get(0)).setImage(depositImg.get(numDepositClicked.get(1)).getImage());
                    depositImg.get(numDepositClicked.get(1)).setImage(tmpI);
                }
            }
            numDepositClicked.clear();
            resourceClicked.clear();
        }
    }

    private void modifyDeposit(String s, int i){
        int num = i;

        //tolgo il materiale dalle risorse ottenute dal market da inserire in deposito
        if(marketReceive.containsKey(s) && num>=0 && num<depositState.size()){
            if(marketReceive.get(s)-1 > -1){
                marketReceive.put(s, marketReceive.get(s) -1);

                //aggiungo il materiale tolto dal deposito ai materiali disponibili
                if (marketReceive.containsKey(s)) {
                    if(depositState.get(num) == null){
                        marketReceive.put("nothing", marketReceive.get("nothing")+1);
                    }else{
                        marketReceive.put(depositState.get(num).toString().toLowerCase(), marketReceive.get(depositState.get(num).toString().toLowerCase()) + 1);
                        depositState.set(num, null);
                    }
                }
                //aggiungo il materiale tolto dai materiali disponibili al deposito
                if (marketReceive.containsKey(s))
                    if(!s.equals("nothing")){
                        depositImg.get(num).setImage(resources.get(s));
                        depositState.set(num, Resource.valueOf(s.toUpperCase()));
                    }else{
                        depositImg.get(num).setImage(null);
                        depositState.set(num, null);
                    }

            }
        }
        numShield.setText("X " + marketReceive.get("shield"));
        numStone.setText("X " + marketReceive.get("stone"));
        numServant.setText("X " + marketReceive.get("servant"));
        numCoin.setText("X " + marketReceive.get("coin"));
    }



    public void done(ActionEvent actionEvent) {
        numDepositClicked.clear();
        resourceClicked.clear();
        leaderAdditional.forEach(leader -> leader.setImage(null));
        leaderDeposit0.forEach(dep -> dep.setImage(null));
        leaderDeposit1.forEach(dep -> dep.setImage(null));
        leaderDeposit0.forEach(img -> img.setDisable(true));
        leaderDeposit1.forEach(img -> img.setDisable(true));
        depositImg = new ArrayList<>(depositImg.subList(0,6));
        gui.changeScene("playerView");
        PlayerViewController controller = (PlayerViewController) gui.getCurrentController();
        controller.tabTurnNotActive(true);
        if(isInitial){
            gui.getConnectionToServer().sendInitialDepositState(depositState);
        }else if(isAdditional){
            ArrayList<Resource> additionalDeposit;
            if(addDepositType.size()==1){
                additionalDeposit = new ArrayList<>(depositState.subList(6, 8)) ;
            }else{
                additionalDeposit = new ArrayList<>(depositState.subList(6,10));
            }
            depositState = new ArrayList<>(depositState.subList(0,6));

            gui.getConnectionToServer().sendNewDepositState(depositState, getMarketResource(), true, additionalDeposit);
        }else{
            gui.getConnectionToServer().sendNewDepositState(depositState, getMarketResource(), false, new ArrayList<>());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        depositImg = new ArrayList<>(List.of(deposit1, deposit2, deposit3, deposit4, deposit5, deposit6));
        leaderDeposit0 = new ArrayList<>(List.of(addDeposit00, addDeposit01));
        leaderDeposit1 = new ArrayList<>(List.of(addDeposit10, addDeposit11));
        leaderAdditional = new ArrayList<>(List.of(leader0, leader1));
        System.out.println("");
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
        resources = gui.getResourcesGraphic();
    }
}
