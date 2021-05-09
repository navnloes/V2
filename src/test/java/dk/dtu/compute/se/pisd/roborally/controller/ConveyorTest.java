package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConveyorTest {

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
    void doActionConv() {
            Space space = new Space(gameController.board, 3,3);
            ConveyorBeltFieldAction convTest = new ConveyorBeltFieldAction();
        convTest.setHeading(Heading.SOUTH);
        space.addFieldAction(convTest);
        Player player = gameController.board.getCurrentPlayer();

        player.setSpace(space);

        convTest.doAction(gameController, space);


        Assertions.assertEquals(player, gameController.board.getSpace(3, 4).getPlayer(), "Player " + player.getName() + " should beSpace (3,4)!");
        Assertions.assertEquals(Heading.SOUTH, player.getHeading(), "Player should be heading SOUTH!");
        Assertions.assertNull(gameController.board.getSpace(0, 0).getPlayer(), "Space (0,0) should be empty!");
    }

    @Test
    void doActionDoubleConv() {
        Space space = new Space(gameController.board, 3,3);
        DoubleConveyorBeltFieldAction convTest = new DoubleConveyorBeltFieldAction();
        convTest.setHeading(Heading.SOUTH);
        space.addFieldAction(convTest);
        Player player = gameController.board.getCurrentPlayer();

        player.setSpace(space);

        convTest.doAction(gameController, space);

        Assertions.assertEquals(player, gameController.board.getSpace(3, 5).getPlayer(), "Player " + player.getName() + " should beSpace (3,4)!");
        Assertions.assertEquals(Heading.SOUTH, player.getHeading(), "Player should be heading SOUTH!");
        Assertions.assertNull(gameController.board.getSpace(0, 0).getPlayer(), "Space (0,0) should be empty!");
    }
}
