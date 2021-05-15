package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PitTest {

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
    void doAction() {
        Space space = gameController.board.getSpace(3,3);
        PitFieldAction pit = new PitFieldAction();
        space.addFieldAction(pit);
        Player player = gameController.board.getCurrentPlayer();
        Space startSpace = gameController.board.getSpace(5,5);
        player.setStartSpace(startSpace);

        CommandCardField[] card = player.getCards();
        CommandCardField[] program = player.getProgram();

        CommandCard commandCard;

        for (CommandCardField p : program){
            commandCard = new CommandCard(Command.FORWARD);
            p.setCard(commandCard);
            System.out.println(p.getCard().getCommand());
        }

        for (CommandCardField p : card){
            commandCard = new CommandCard(Command.LEFT);
            p.setCard(commandCard);
            System.out.println(p.getCard().getCommand());
        }

        //player is on (3,3)
        player.setSpace(space);

        for (FieldAction f : space.getActions()){
            f.doAction(gameController,space);
        }

        //check if all cards are turned into nullvalues and invisible
        for (CommandCardField c : card){
            Assertions.assertFalse(c.isVisible());
        }
        for (CommandCardField p : program){
            Assertions.assertNull(p.getCard());
        }
        //check if player has rebooted on its startspace (5,5)
        Assertions.assertEquals(player.getSpace(),startSpace);



    }
}