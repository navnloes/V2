/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.dal.RepositoryAccess;
import dk.dtu.compute.se.pisd.roborally.model.ImpossibleMoveException;
import dk.dtu.compute.se.pisd.roborally.model.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class GameController {

    final public Board board;
    //    CheckPointCollection checkPointCollection;
//    ConveyorBeltCollection conveyorBeltCollection;
//    GearsCollection gearsCollection;
    public boolean won = false;

    public GameController(@NotNull Board board) {
        this.board = board;
        board.setGameController(this);
        if (this.board.getPhase() == Phase.INITIALISATION)
            this.board.setPhase(Phase.PROGRAMMING);
    }

    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space) {


        if (space.getPlayer() == null) {
            Player currentPlayer = board.getCurrentPlayer();
            currentPlayer.setSpace(space);

            board.setCount(board.getCount() + 1);

            int number = board.getPlayerNumber(currentPlayer);
            Player nextPlayer = board.getPlayer((number + 1) % board.getPlayersNumber());
            board.setCurrentPlayer(nextPlayer);
        }
    }

    // XXX: V2

    /**
     * This method begins the PROGRAMMING phase of the game
     * random Command Cards are assigned to all players in the game
     */
    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);

                    if (field.getCard() == null) {
                        field.setCard(generateRandomCommandCard());
                    }

                    field.setVisible(true);
                }

            }
        }

    }

    // XXX: V2

    /**
     * Method generates a random Command Card
     *
     * @return new random CommandCard
     */
    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }

    // XXX: V2

    /**
     * Method ends Programming Phase and sets Phase to Activation
     */
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }

    // XXX: V2

    /**
     * Method makes a specific register visible for all players, in ascending order
     *
     * @param register int for choosing number of register to make visible
     */
    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    // XXX: V2

    /**
     * Method makes all registers on the Command Card Field invisible
     */
    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    // XXX: V2

    /**
     * Method sets stepMode to false
     * All registers are executed
     */
    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    // XXX: V2

    /**
     * StepMode is activated
     * Registers are exectued step-by-step
     */
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    // XXX: V2

    /**
     * Loop that continues to execute all available registers, while game Phase is Activation
     */
    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    // XXX: V2

    /**
     * Method executes specific current player's Command Card of this step
     * (five steps altogether / five registers that can be executed)
     * If Command Card is interactive, game Phase is set to INTERACTION
     * <p>
     * Then, the turn goes on to the next player, whose Command Card is activated
     */
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        if ((board.getPhase() == Phase.ACTIVATION) ||
                (board.getPhase() == Phase.PLAYER_INTERACTION && board.getUserChoice() != null)
                        && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                Command userChoice = board.getUserChoice();
                if (userChoice != null) {
                    board.setUserChoice(null);
                    board.setPhase(Phase.ACTIVATION);
                    executeCommand(currentPlayer, userChoice);
                } else {
                    CommandCard card = currentPlayer.getProgramField(step).getCard();
                    if (card != null) {
                        Command command = card.command;
                        if (command.isInteractive()) {
                            board.setPhase(Phase.PLAYER_INTERACTION);
                            return;
                        }
                        executeCommand(currentPlayer, command);
                    }
                }

                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {
                    for (Player player : board.getPlayers()){
                        Space space = player.getSpace();
                        if (!space.getActions().isEmpty()){
                            for (FieldAction fieldAction : space.getActions()) {
                                fieldAction.doAction(board.getGameController(), space);
                            }
                        }
                    }
                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        board.setCurrentPlayer(board.getPlayer(0));
                    } else {
                        startProgrammingPhase();
                    }
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }

    // XXX: V2

    /**
     * Command of specific CommandCard is executed
     * Robot lasers are activated afterwards
     * @param player  whose turn it is
     * @param command to be executed
     */
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).
            switch (command) {
                case FORWARD:
                    this.moveForward(player);
                    break;
                case RIGHT:
                    this.turnRight(player);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    break;
                case FAST_FORWARD:
                    this.fastForward(player);
                    break;
                default:
                    // DO NOTHING (for now)
            }
            if (!player.getActions().isEmpty()){
                Space space = player.getSpace();
                for (PlayerAction p : player.getActions()) {
                    if (!wallBlocks(player,space))
                        p.doAction(board.getGameController(), player);
                }
            }
        }
    }

    /**
     * When the game is in INTERACTION Phase
     * The phase is set to ACTIVATION
     * and the current player's CommandCard is executed
     * - the turn moves on to the next player, til all steps are executed
     * <p>
     * After all steps are executed, the game Phase returns to PROGRAMMING Phase
     *
     * @param option option of Command
     */
    public void executeCommandOptionAndContinue(@NotNull Command option) {
        assert board.getPhase() == Phase.PLAYER_INTERACTION;
        assert board.getCurrentPlayer() != null;
        board.setUserChoice(option);
        continuePrograms();
    }

    public void moveToSpace(
            @NotNull Player player,
            @NotNull Space space,
            @NotNull Heading heading) throws ImpossibleMoveException {

        Player other = space.getPlayer();
        if (other != null) {
            Space target = board.getNeighbour(space, heading);

            if (target != null) {
                moveToSpace(other, target, heading);
            } else {
                throw new ImpossibleMoveException(player, space, heading);
            }
        }
        if (wallBlocks(player, space)) {
            throw new ImpossibleMoveException(player, space, heading);
        }

        player.setSpace(space);
    }

    /**
     * Method moves player to 'Neighbour' / one space forward
     *
     * @param player to be moved
     */
    public void moveForward(@NotNull Player player) {

        Space current = player.getSpace();
        if (current != null && player.board == current.board) {
            Heading heading = player.getHeading();
            Space target = board.getNeighbour(current, heading);

            if (target != null) {
                try {
                    moveToSpace(player, target, heading);
                } catch (ImpossibleMoveException e) {
                    //
                }
            }
        }
        player.createWinner();


    }

    /**
     * Method moves player two spaces forward
     *
     * @param player to be moved
     */
    public void fastForward(@NotNull Player player) {

        //røv grimt men fungere 5head
        moveForward(player);
        moveForward(player);
    }

    /**
     * Method turns player to the right of the current 'Heading' / facing
     *
     * @param player to be turned
     */
    public void turnRight(@NotNull Player player) {
        Heading heading = player.getHeading();
        player.setHeading(heading.next());
    }

    /**
     * Method turns player to the left of the current 'Heading' / facing
     *
     * @param player to be turned
     */
    public void turnLeft(@NotNull Player player) {
        Heading heading = player.getHeading();
        player.setHeading(heading.prev());
    }

    /**
     * @param source Card, which is picked
     * @param target Card, which is moved to CommandCard Field
     *               -to be executed during ACTIVATION phase
     * @return boolean
     */
    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }

    /**
     * A method called when no corresponding controller operation is implemented yet. This
     * should eventually be removed.
     */

    public void notImplemented() {

    }


    public void onZero() {
        finishProgrammingPhase();
    }

    /**
     * This method checks if the space, that a given player is about to move to, is blocked by a wall
     * The space of the player is compared to the given space
     * @param player Player player who wants to move
     * @param space Space space that player wants to move to
     * @return Boolean true if there is a wall blocking
     */
    public boolean wallBlocks(Player player, Space space) {
        boolean blocks = false;
        Space playerSpace = player.getSpace();
        Heading heading = player.getHeading();
        List<Heading> spaceHeadings = playerSpace.getWalls();
        List<Heading> targetHeadings = space.getWalls();

        for (Heading h : spaceHeadings) {
            if (h == heading) {
                blocks = true;
                break;
            }
        }

        for (Heading t : targetHeadings) {
            switch (t) {
                case SOUTH:
                    if (player.getHeading().equals(Heading.NORTH)) {
                        blocks = true;
                    }
                    break;
                case NORTH:
                    if (player.getHeading().equals(Heading.SOUTH)) {
                        blocks = true;
                    }
                    break;
                case WEST:
                    if (player.getHeading().equals(Heading.EAST)) {
                        blocks = true;
                    }
                    break;
                case EAST:
                    if (player.getHeading() == Heading.WEST) {
                        blocks = true;
                    }
                    break;
                default:
                    System.out.println("Illegal heading - player.getHeading() " + t + " in moveToSpace");
                    break;
            }
        }
        return blocks;
    }

}
