package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.CheckPointFieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
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
 void arrivedDifferentCheckPoints() {
        Space space = gameController.board.getSpace(0,0);
        Player player = gameController.board.getPlayer(0);
        player.setSpace(space);

       space = gameController.board.getSpace(3,3);
       CheckPointFieldAction c1 = new CheckPointFieldAction();
       c1.setCheckPointId(0);
       space.addFieldAction(c1);

       player.setSpace(space);
       for (FieldAction fieldAction : space.getActions()){
           fieldAction.doAction(gameController,space);
       }

       space = new Space(gameController.board,6,1);

       CheckPointFieldAction c2 = new CheckPointFieldAction();
       c2.setCheckPointId(1);
       space.addFieldAction(c2);

        player.setSpace(space);
        for (FieldAction fieldAction : space.getActions()){
            fieldAction.doAction(gameController,space);
        }

        space = new Space(gameController.board,8,0);
       CheckPointFieldAction c3 = new CheckPointFieldAction();
       c3.setCheckPointId(2);
       space.addFieldAction(c3);

        player.setSpace(space);
        for (FieldAction fieldAction : space.getActions()){
            fieldAction.doAction(gameController,space);
        }


        assertTrue(player.getWinner());

    }

    @Test
   void arrivedSameCheckPoints() {
        Space space = gameController.board.getSpace(0,0);
        Player player = new Player(gameController.board, "blue", "Blue");
        player.setSpace(space);

        space = gameController.board.getSpace(3,3);
        CheckPointFieldAction c1 = new CheckPointFieldAction();
        c1.setCheckPointId(0);
        space.addFieldAction(c1);

        player.setSpace(space);
        for (FieldAction fieldAction : space.getActions()){
            fieldAction.doAction(gameController,space);
        }

        player.setSpace(space);
        for (FieldAction fieldAction : space.getActions()){
            fieldAction.doAction(gameController,space);
        }

        player.setSpace(space);
        for (FieldAction fieldAction : space.getActions()){
            fieldAction.doAction(gameController,space);
        }


        assertFalse(player.getWinner());

    }


}