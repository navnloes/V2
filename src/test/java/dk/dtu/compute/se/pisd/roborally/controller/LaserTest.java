package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LaserTest {
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
    void tearDown()  {
        gameController = null;
    }


    @Test
    void doAction() {
        Board board = gameController.board;
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);
        board.setCurrentPlayer(player1);
        gameController.moveCurrentPlayerToSpace(board.getSpace(3, 3));
        board.setCurrentPlayer(player2);
        gameController.moveCurrentPlayerToSpace(board.getSpace(3, 5));

        for (PlayerAction p : player1.getActions()) {
            if (!gameController.wallBlocks(player1, player2.getSpace())){
                p.doAction(board.getGameController(), player1);
            }

        }
        assertEquals(player2.getPenaltySum(),1);
    }
}