package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PriorityAntennaTest {

    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;

    private GameController gameController;

    @BeforeEach
    void setUp() {
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board);
        for (int i = 0; i < 2; i++) {
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
    void getPlayerTurns() {
        Board board = gameController.board;
        Player player0 = board.getPlayer(0);
        Player player1 = board.getPlayer(1);

        Space space = board.getSpace(0,0);
        player0.setSpace(space);
        space = board.getSpace(0,1);
        player1.setSpace(space);

        space = board.getSpace(0,5);
        PriorityAntenna priorityAntenna = new PriorityAntenna();
        priorityAntenna.setSpace(space);

        priorityAntenna.sortPlayerArray(board);
        Player[] playerTurns = priorityAntenna.getPlayerTurns();
        //Metoden printMap() viser her, at player 0 er 5 felter fra antennen
        //samtidig ser vi, at player 1 er 4 felter fra
        priorityAntenna.printMap();

        //nedenstående forløkke viser, at player 1 har første tur, idet denne udskrives først, og at player 0 har anden tur
        for (Player player : playerTurns){
            System.out.println("[" + player.getName() + "]");
        }


    }
}