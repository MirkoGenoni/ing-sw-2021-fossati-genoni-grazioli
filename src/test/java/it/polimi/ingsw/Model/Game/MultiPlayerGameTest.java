package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.DevelopmentCard.DevelopmentCard;
import it.polimi.ingsw.Model.DevelopmentCard.ProductedMaterials;
import it.polimi.ingsw.Model.Exceptions.DevelopmentCardException;
import it.polimi.ingsw.Model.Exceptions.LeaderCardException;
import it.polimi.ingsw.Model.Exceptions.ResourceException;
import it.polimi.ingsw.Model.Exceptions.StartGameException;
import it.polimi.ingsw.Model.Market.Marble;
import it.polimi.ingsw.Model.Market.Market;
import it.polimi.ingsw.Model.Resource.Resource;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.*;

public class MultiPlayerGameTest {

    MultiPlayerGame multiPlayerGame= new MultiPlayerGame(3);

    @Before
    public void setUp() {
        try {
            multiPlayerGame.addPlayer(new Player("Stefano"));
            multiPlayerGame.addPlayer(new Player("Davide"));
            multiPlayerGame.addPlayer(new Player("Mirko"));
            multiPlayerGame.startGame();
        } catch (StartGameException e) {
            e.printStackTrace();
            fail();
        }
        assertNotNull(multiPlayerGame.getDevelopmentCards());
        assertNotNull(multiPlayerGame.getPlayersFaithTrack());
        assertNotNull(multiPlayerGame.getDevelopmentCardsAvailable());
        assertNotNull(multiPlayerGame.getPlayers());
        assertEquals(multiPlayerGame.getPlayers().length, 3);

        try {
            multiPlayerGame.addPlayer(new Player("UndesideredPlayer"));
        }catch (StartGameException e){
            assertEquals(e.getMessage(), "players all initialized");
        }

    }

    //TODO CONTROLLARE SE OKAY
    /*@Test
    public void startGame() {
        MultiPlayerGame testStartGameMultiPlayerGame = new MultiPlayerGame(3);
        try {
            testStartGameMultiPlayerGame.addPlayer(new Player("Stefano"));
            testStartGameMultiPlayerGame.addPlayer(new Player("Davide"));
            testStartGameMultiPlayerGame.addPlayer(new Player("Mirko"));
            testStartGameMultiPlayerGame.startGame();
        } catch (StartGameException e) {
            fail();
        }

        assertNotNull(testStartGameMultiPlayerGame.getDevelopmentCards());
        assertNotNull(testStartGameMultiPlayerGame.getPlayersFaithTrack());
        assertNotNull(testStartGameMultiPlayerGame.getDevelopmentCardsAvailable());
        assertNotNull(testStartGameMultiPlayerGame.getPlayers());
        assertEquals(testStartGameMultiPlayerGame.getPlayers().length, 3);
    }*/

    @Test
    public void getDevelopmentCards() {
        ArrayList [][] gettedArray = multiPlayerGame.getDevelopmentCards();
        for(int i=0; i<gettedArray.length; i++) {
            for (int j = 0; j < gettedArray[i].length; j++) {
                assertEquals(gettedArray[i][j], multiPlayerGame.developmentCards[i][j]);
            }
        }
    }

    @Test
    public void getDevelopmentCardsAvailable() {
       DevelopmentCard[][] gettedAvailable = multiPlayerGame.getDevelopmentCardsAvailable();

       for(int i=0; i<gettedAvailable.length; i++) {
            for (int j = 0; j < gettedAvailable[i].length; j++) {
                assertEquals(gettedAvailable[i][j], multiPlayerGame.getDevelopmentCardsAvailable()[i][j]);
            }
        }

    }

    @Test
    public void buyDevelopmentCard() {
        DevelopmentCard[][] beforeBuyAvailableCards;
        DevelopmentCard[][] afterBuyAvailableCards;
        DevelopmentCard cardBought;

        int color = (int) (Math.random() * 4);
        int level = (int) (Math.random() * 3);

        for(int i=0; i<3; i++) {
            try {
                beforeBuyAvailableCards = multiPlayerGame.getDevelopmentCardsAvailable();
                cardBought = multiPlayerGame.buyDevelopmentCard(color, level);
                afterBuyAvailableCards = multiPlayerGame.getDevelopmentCardsAvailable();
                assertEquals(cardBought.getCardID(), beforeBuyAvailableCards[color][level].getCardID());
                assertNotEquals(cardBought.getCardID(), afterBuyAvailableCards[color][level].getCardID());
            } catch (StartGameException e) {
                fail();
            }
        }

        try {   //buy the last card
            multiPlayerGame.buyDevelopmentCard(color,level);
        } catch (StartGameException e) {
            fail();
        }

        assertNull(multiPlayerGame.getDevelopmentCardsAvailable()[color][level]); //already bought 4 cards

        try {
            cardBought = multiPlayerGame.buyDevelopmentCard(5,3); //out of bound
        }catch (StartGameException e){
            assertEquals(e.getMessage(), "the card selected doesn't exist, Is not available");
        }
    }

    @Test
    public void getMarketBoard() {
       Market actualMarket = multiPlayerGame.getMarketBoard();
       assertNotNull(actualMarket);
       assertEquals(actualMarket, multiPlayerGame.marketBoard);
    }

}