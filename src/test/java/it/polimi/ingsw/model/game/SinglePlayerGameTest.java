package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.exceptions.StartGameException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
/**
 * This class tests the SinglePlayerGame class.
 * These tests test the correct initialization and the behaviour of the SinglePlayerGame
 * @author davide grazioli
 */
public class SinglePlayerGameTest {

    Player davidePlayer = new Player("Davide");
    SinglePlayerGame singlePlayerGame = new SinglePlayerGame(davidePlayer);

    @Before
    public void setUp() {
        try {
            singlePlayerGame.startGame();
        } catch (StartGameException e) {
            fail();
        }

        assertNotNull(singlePlayerGame.getDevelopmentCards());
        assertNotNull(singlePlayerGame.getPlayersFaithTrack());
        assertNotNull(singlePlayerGame.getDevelopmentCardsAvailable());
        assertNotNull(singlePlayerGame.getPlayer());
        assertNotNull(singlePlayerGame.getLorenzoIlMagnifico());
    }
    /**
     * This test checks the right behaviour of the getPlayer method returning the player
     */
    @Test
    public void getPlayer() {
        assertEquals("Davide", singlePlayerGame.getPlayer().getName());
    }
    /**
     * This test checks the right behaviour of the getLorenzoIlMagnifico method returning the LorenzoIlMagnifico
     */
    @Test
    public void getLorenzoIlMagnifico() {
        assertEquals(6,singlePlayerGame.getLorenzoIlMagnifico().getSoloActionTokens().size());
    }
    /**
     * This test checks the right behaviour of the getDevelopmentCards method returning all the
     * DevelopmentCards
     */
    @Test
    public void getDevelopmentCards() {
        ArrayList[][] gettedArray = singlePlayerGame.getDevelopmentCards();
        for(int i=0; i<gettedArray.length; i++) {
            for (int j = 0; j < gettedArray[i].length; j++) {
                assertEquals(gettedArray[i][j], singlePlayerGame.developmentCards[i][j]);
            }
        }
    }
    /**
     * This test checks the right behaviour of the getDevelopmentCardsAvailable method returning the
     * available DevelopmentCards
     */
    @Test
    public void getDevelopmentCardsAvailable() {
        DevelopmentCard[][] gettedAvailable = singlePlayerGame.getDevelopmentCardsAvailable();

        for(int i=0; i<gettedAvailable.length; i++) {
            for (int j = 0; j < gettedAvailable[i].length; j++) {
                assertEquals(gettedAvailable[i][j], singlePlayerGame.getDevelopmentCardsAvailable()[i][j]);
            }
        }
    }
    /**
     * This test checks the right behaviour of the buyDevelopmentCard method buying a random DevelopmentCards
     * and testing if it's correct. This test also check the correct thrown of exceptions.
     */
    @Test
    public void buyDevelopmentCard() {
        DevelopmentCard[][] beforeBuyAvailableCards = singlePlayerGame.getDevelopmentCardsAvailable();
        DevelopmentCard[][] afterBuyAvailableCards;
        DevelopmentCard cardBought;

        int color = (int) (Math.random() * 4);
        int level = (int) (Math.random() * 3);

        try {
            cardBought = singlePlayerGame.buyDevelopmentCard(color,level);
            afterBuyAvailableCards = singlePlayerGame.getDevelopmentCardsAvailable();
            assertEquals(cardBought.getCardID(), beforeBuyAvailableCards[color][level].getCardID());
            assertNotEquals(cardBought.getCardID(), afterBuyAvailableCards[color][level].getCardID());
        } catch (StartGameException e) {
            fail();
        }

        try {
            cardBought = singlePlayerGame.buyDevelopmentCard(5,3); //out of bound
        }catch (StartGameException e){
            assertEquals(e.getMessage(), "the card selected doesn't exist, Is not available");
        }
    }

}