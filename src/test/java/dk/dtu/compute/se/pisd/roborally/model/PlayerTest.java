package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;

    private GameController gameController;


    //TODO: hvad er det
    @BeforeEach
    void setUp() {
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board);
        for (int i = 0; i < 6; i++) {
            Player player = new Player(board, null,"Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }
        board.setCurrentPlayer(board.getPlayer(0));
    }

    @AfterEach
    void tearDown() {
        gameController = null;
    }

    @Test
    void addCheckPointToken() {
        Player player = new Player(gameController.board, "blue", "Blue");
        player.addCheckPointToken();
        assertEquals(player.getCheckPointToken(), 1);
    }

    @Test
    void winnerWithThreeTokens() {

        Player player = new Player(gameController.board, "blue", "Blue");

        player.addCheckPointToken();
        player.addCheckPointToken();
        player.addCheckPointToken();

        assertTrue(player.getWinner());

    }

    @Test
    void arrivedDifferentsCheckPoints() {
//        Player player = new Player(gameController.board, "blue", "Blue");
//
//        Space s1 = new Space(gameController.board, 3,3);
//        Space s2 = new Space(gameController.board, 1,6);
//        Space s3 = new Space(gameController.board, 0,8);
//
//        CheckPointActionField c1 = new CheckPointActionField();
//        CheckPointActionField c2 = new CheckPointActionField();
//        CheckPointActionField c3 = new CheckPointActionField();
//
//        CheckPointCollection c = new CheckPointCollection();
//        c.addActionField(c1);
//        c.addActionField(c2);
//        c.addActionField(c3);
//
//
//        player.arrivedCheckPoint(c.getCheckPointId(s1));
//        player.arrivedCheckPoint(c.getCheckPointId(s2));
//        player.arrivedCheckPoint(c.getCheckPointId(s3));
//
//        assertTrue(player.getWinner());

    }

    @Test
    void arrivedSameCheckPoints() {
//        Player player = new Player(gameController.board, "blue", "Blue");
//
//        Space s1 = new Space(gameController.board, 3,3);
//        Space s2 = new Space(gameController.board, 1,6);
//        Space s3 = new Space(gameController.board, 0,8);
//
//        CheckPointActionField c1 = new CheckPointActionField();
//        CheckPointActionField c2 = new CheckPointActionField();
//        CheckPointActionField c3 = new CheckPointActionField();
//
//        CheckPointCollection c = new CheckPointCollection();
//        c.addActionField(c1);
//        c.addActionField(c2);
//        c.addActionField(c3);
//
//
//        player.arrivedCheckPoint(c.getCheckPointId(s1));
//        player.arrivedCheckPoint(c.getCheckPointId(s1));
//        player.arrivedCheckPoint(c.getCheckPointId(s1));
//
//        assertFalse(player.getWinner());

    }


}