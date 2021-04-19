package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameControllerTest {

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

    @Test
    void moveCurrentPlayerToSpace() {
        Board board = gameController.board;
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        gameController.moveCurrentPlayerToSpace(board.getSpace(0, 4));

        Assertions.assertEquals(player1, board.getSpace(0, 4).getPlayer(), "Player " + player1.getName() + " should beSpace (0,4)!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(), "Space (0,0) should be empty!");
        Assertions.assertEquals(player2, board.getCurrentPlayer(), "Current player should be " + player2.getName() +"!");
    }

    @Test
    void moveForward() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.moveForward(current);

        Assertions.assertEquals(current, board.getSpace(0, 1).getPlayer(), "Player " + current.getName() + " should beSpace (0,1)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(), "Player 0 should be heading SOUTH!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(), "Space (0,0) should be empty!");

    }

    @Test
    void fastForward() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.fastForward(current);

        Assertions.assertEquals(current, board.getSpace(0, 2).getPlayer(), "Player " + current.getName() + " should beSpace (0,2)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(), "Player 0 should be heading SOUTH!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(), "Space (0,0) should be empty!");

    }

    @Test
    void turnRight() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.turnRight(current);

        Assertions.assertEquals(current, board.getSpace(0, 0).getPlayer(), "Player " + current.getName() + " should be Space (1,0)!");
        Assertions.assertEquals(Heading.WEST, current.getHeading(), "Player 0 should be heading EAST");
    }

    @Test
    void turnLeft() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.turnLeft(current);

        Assertions.assertEquals(current, board.getSpace(0, 0).getPlayer(), "Player " + current.getName() + " should beSpace (7,0)!");
        Assertions.assertEquals(Heading.EAST, current.getHeading(), "Player 0 should be heading WEST");
    }

    @Test
    void moveToSpace() {

        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        Space playerSpace = new Space(board,0,0);
        Space target = new Space(board, 0,1);
        target.addWall(Heading.NORTH);

        Assertions.assertThrows(ImpossibleMoveException.class,() -> {
            //her er current heading SOUTH
            gameController.moveToSpace(current,target,Heading.SOUTH);
        });
    }

    @Test
    void executeCommand() {
        //Lasertest WIP
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        Player other = board.getPlayer((current.getPlayerId() + 1)%2);
        Space startSpace = new Space(board,0,0);
        other.setStartSpace(startSpace);

        Space space = new Space(board,1,1);

        current.setSpace(space);
        current.setHeading(Heading.SOUTH);

        space = new Space(board,1,3);
        other.setSpace(space);
        other.setHeading(Heading.SOUTH);

        current.addPlayerAction(new LaserPlayerAction());
        for (int i = 0; i < 3; i++){
            for (PlayerAction p : current.getActions()){
                p.doAction(gameController,current);
            }
        }

        //Assertions.assertEquals(startSpace.x,other.getSpace().x);
        //Assertions.assertEquals(startSpace.y,other.getSpace().y);



    }

}