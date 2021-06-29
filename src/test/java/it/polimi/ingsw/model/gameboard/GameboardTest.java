package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.exceptions.LeaderCardException;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameboardTest {
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