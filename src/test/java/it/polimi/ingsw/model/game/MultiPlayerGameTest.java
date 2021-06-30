package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.exceptions.StartGameException;
import it.polimi.ingsw.model.market.Market;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
/**
 * This class tests the MultiPlayerGame class.
 * These tests test the correct initialization and the behaviour of the MultiPlayerGame
 * @author davide grazioli
 */
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

    /**
     * This test checks the right behaviour of the getDevelopmentCards method returning all the development cards
     */
    @Test
    public void getDevelopmentCards() {
        ArrayList [][] gettedArray = multiPlayerGame.getDevelopmentCards();
        for(int i=0; i<gettedArray.length; i++) {
            for (int j = 0; j < gettedArray[i].length; j++) {
                assertEquals(gettedArray[i][j], multiPlayerGame.developmentCards[i][j]);
            }
        }
    }
    /**
     * This test checks the right behaviour of the getDevelopmentCardsAvailable method returning the available
     * development cards
     */
    @Test
    public void getDevelopmentCardsAvailable() {
       DevelopmentCard[][] gettedAvailable = multiPlayerGame.getDevelopmentCardsAvailable();

       for(int i=0; i<gettedAvailable.length; i++) {
            for (int j = 0; j < gettedAvailable[i].length; j++) {
                assertEquals(gettedAvailable[i][j], multiPlayerGame.getDevelopmentCardsAvailable()[i][j]);
            }
        }

    }
    /**
     * This test checks the right behaviour of the buyDevelopmentCard method verifying that the market
     * releases the correct card and return null if there's not an available card
     */
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
    /**
     * This test checks the right behaviour of the getMarketBoard method.
     */
    @Test
    public void getMarketBoard() {
       Market actualMarket = multiPlayerGame.getMarketBoard();
       assertNotNull(actualMarket);
       assertEquals(actualMarket, multiPlayerGame.marketBoard);
    }

}