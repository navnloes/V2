package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GearsTest {

    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;

    private GameController gameController;

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

    /**
     * @author S205354
     */

    @Test
    void doActionGearsLeft() {
        Space space = new Space(gameController.board, 3,3);
        GearsFieldAction gearsTest = new GearsFieldAction();
        gearsTest.setDirection(Direction.LEFT);
        space.addFieldAction(gearsTest);
        Player player = gameController.board.getCurrentPlayer();

        player.setSpace(space);

        gearsTest.doAction(gameController, space);

        Assertions.assertEquals(player.getPlayerId(), gameController.board.getSpace(3, 3).getPlayer().getPlayerId(), "Player " + player.getName() + " should beSpace (3,3)!");
        Assertions.assertEquals(Heading.EAST, player.getHeading(), "Player should be heading SOUTH!");
        Assertions.assertNull(gameController.board.getSpace(0, 0).getPlayer(), "Space (0,0) should be empty!");
    }

    @Test
    void doActionGearsRight() {
        Space space = new Space(gameController.board, 3,3);
        GearsFieldAction gearsTest = new GearsFieldAction();
        gearsTest.setDirection(Direction.RIGHT);
        space.addFieldAction(gearsTest);
        Player player = gameController.board.getCurrentPlayer();

        player.setSpace(space);

        gearsTest.doAction(gameController, space);


        Assertions.assertEquals(player.getPlayerId(), gameController.board.getSpace(3, 3).getPlayer().getPlayerId(), "Player " + player.getName() + " should beSpace (3,3)!");
        Assertions.assertEquals(Heading.WEST, player.getHeading(), "Player should be heading SOUTH!");
        Assertions.assertNull(gameController.board.getSpace(0, 0).getPlayer(), "Space (0,0) should be empty!");
    }
}
