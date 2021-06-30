package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.exceptions.LeaderCardException;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
/**
 * This class tests the GameBoard class.
 * @author Mirko Genoni
 */
public class GameboardTest {
    /**
     * This test checks the correct handle of the pope favor tiles inside the gameBoard
     * the other methods are not tested because are only getter method of classes that has been tested
     * in other tests
     */
    @Test
    public void TestGameboard() {
        ArrayList<Integer> popeTiles = new ArrayList<>();
        popeTiles.add(0);
        popeTiles.add(3);
        popeTiles.add(4);

        try {
            Gameboard gameboard = new Gameboard(null);
            gameboard.removePopeFavorTiles(1);
            assertEquals(gameboard.getPopeFavorTilesState(), popeTiles);

            popeTiles.clear();
            popeTiles.add(0);
            popeTiles.add(0);
            popeTiles.add(4);

            gameboard.removePopeFavorTiles(2);
            assertEquals(gameboard.getPopeFavorTilesState(), popeTiles);

            popeTiles.clear();
            popeTiles.add(0);
            popeTiles.add(0);
            popeTiles.add(0);

            gameboard.removePopeFavorTiles(3);
            assertEquals(gameboard.getPopeFavorTilesState(), popeTiles);
        } catch (LeaderCardException e) {}

    }
}