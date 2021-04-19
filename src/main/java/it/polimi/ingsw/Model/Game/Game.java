package it.polimi.ingsw.Model.Game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import it.polimi.ingsw.Model.DevelopmentCard.DevelopmentCard;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Model.FaithTrack.FaithTrack;
import it.polimi.ingsw.Model.LeaderCard.*;
import it.polimi.ingsw.Model.Market.Market;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

import static it.polimi.ingsw.Model.DevelopmentCard.CardColor.*;

/**
 * This class represents the game.
 *
 * @author Stefano Fossati
 */

public abstract class Game {
    protected Market marketBoard;
    protected ArrayList<LeaderCard> allLeaderCards;
    protected ArrayList[][] developmentCards;
    protected FaithTrack playersFaithTrack;

    public Game(int numPlayer){
        playersFaithTrack = new FaithTrack(numPlayer);
    }

    public FaithTrack getPlayersFaithTrack() {
        return playersFaithTrack;
    }

    public ArrayList[][] getDevelopmentCards(){
        return developmentCards;
    }

    public Market getMarketBoard() {
        return marketBoard;
    }

    /**
     * Getter of the development card that the player can buy.
     * @return All the development cards available, that the player can buy.
     */
    public DevelopmentCard[][] getDevelopmentCardsAvailable(){
        DevelopmentCard[][] developmentCardsAvailable = new DevelopmentCard[4][3];
        for(int i=0; i<developmentCardsAvailable.length; i++){
            for(int j=0; j<developmentCardsAvailable[i].length; j++){
                developmentCardsAvailable[i][j]  = (DevelopmentCard) developmentCards[i][j].get(0);
            }
        }
        return developmentCardsAvailable;
    }

    /**
     * This method selects the development card that the player want to buy and returns that.
     * @param color The color of the development card that the player want to buy.
     * @param level The level of the development card that the player want to buy.
     * @return The development card that the player has bought.
     */
    public DevelopmentCard buyDevelopmentCard(int color, int level) throws StartGameException {
        if(developmentCards[color][level]!=null && developmentCards[color][level].size()!=0){
            return (DevelopmentCard) developmentCards[color][level].remove(0);
        }else{
            throw new StartGameException("the card selected doesn't exist, Is not available");
        }

    }

    /**
     * This method does the principal actions at the start of the game.
     * @throws StartGameException If the configuration file has problems.
     */
    public void startGame() throws StartGameException {
        marketBoard = new Market();
        leaderCardCreation();       //creates leader cards
        developmentCardCreation();  //creates development cards
    }


    /**
     * This method creates the leader card from the configuration file.
     * @throws StartGameException If the configuration file has problems.
     */

    private void leaderCardCreation() throws StartGameException {
        JsonStreamParser parser;
        allLeaderCards = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        try {
            parser = new JsonStreamParser(new InputStreamReader(
                    new FileInputStream("src/main/java/it/polimi/ingsw/Model/resources/LeaderCards.json"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new StartGameException("Invalid encoding type");
        } catch (FileNotFoundException e) {
            throw new StartGameException("File not found");
        }

        while(parser.hasNext()){
            JsonElement elem = parser.next();
            if(elem.isJsonObject()){
                String name = elem.getAsJsonObject().get("name").getAsString();
                String nameSpecialAbility = elem.getAsJsonObject().get("specialAbility").getAsJsonObject().get("effect").getAsString();
                switch(nameSpecialAbility){
                    case "additionalProduction" :{
                        SpecialAbility tmpSpecialAbility = gson.fromJson(elem.getAsJsonObject().get("specialAbility"), AdditionalProduction.class);
                        allLeaderCards.add(new LeaderCard(name, tmpSpecialAbility));
                        break;
                    }
                    case "biggerDeposit" :{
                        SpecialAbility tmpSpecialAbility = gson.fromJson(elem.getAsJsonObject().get("specialAbility"), BiggerDeposit.class);
                        allLeaderCards.add(new LeaderCard(name, tmpSpecialAbility));
                        break;
                    }
                    case "costLess" :{
                        SpecialAbility tmpSpecialAbility = gson.fromJson(elem.getAsJsonObject().get("specialAbility"), CostLess.class);
                        allLeaderCards.add(new LeaderCard(name, tmpSpecialAbility));
                        break;
                    }
                    case "marketWhiteChange" :{
                        SpecialAbility tmpSpecialAbility = gson.fromJson(elem.getAsJsonObject().get("specialAbility"), MarketWhiteChange.class);
                        allLeaderCards.add(new LeaderCard(name, tmpSpecialAbility));
                        break;
                    }
                }
            }
        }
        Collections.shuffle(allLeaderCards);
    }

    /**
     * This method creates the development cards form the configuration file.
     * Initialises also the structure of the development cards.
     * @throws StartGameException If the configuration file has problems.
     */

    /*
        The bidimensional array is structured in this way:

        [column][row] = [color][level]

                   GREEN    BLUE    YELLOW   PURPLE   <----- color
                +--------+--------+--------+--------+
        level 1 | [0][0] | [1][0] | [2][0] | [3][0] |
                +--------+--------+--------+--------+
        level 2 | [0][1] | [1][1] | [2][1] | [3][1] |
                +--------+--------+--------+--------+
        level 3 | [0][2] | [1][2] | [2][2] | [3][2] |
           |    +--------+--------+--------+--------+
           |
           |---> level


     */
    private void developmentCardCreation() throws StartGameException {
        JsonStreamParser parser;
        int a;
        int b;
        developmentCards = new ArrayList[4][3];

        for(int i=0; i<developmentCards.length; i++){
            for(int j=0; j<developmentCards[i].length; j++){
                developmentCards[i][j] = new ArrayList<DevelopmentCard>();
            }
        }

        Gson gson = new GsonBuilder().create();
        try {
            parser = new JsonStreamParser(new InputStreamReader(
                    new FileInputStream("src/main/java/it/polimi/ingsw/Model/resources/DevelopmentCards.json"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new StartGameException("Invalid encoding type");
        } catch (FileNotFoundException e) {
            throw new StartGameException("File not found");
        }

        while(parser.hasNext()){
            JsonElement elem = parser.next();
            DevelopmentCard dev = gson.fromJson((elem.getAsJsonObject()), DevelopmentCard.class);

            if(dev.getColor().equals(GREEN)){
                a=0;
            }else if(dev.getColor().equals(BLUE)){
                a=1;
            }else if(dev.getColor().equals(YELLOW)){
                a=2;
            }else{
                a=3;
            }

            if(dev.getLevel()==1){
                b=0;
            }else if(dev.getLevel()==2){
                b=1;
            }else{
                b=2;
            }

            developmentCards[a][b].add(dev);
        }

        for(int i=0; i<developmentCards.length; i++){
            for(int j=0; j<developmentCards[i].length; j++){
                Collections.shuffle(developmentCards[i][j]);
            }
        }
    }
}
