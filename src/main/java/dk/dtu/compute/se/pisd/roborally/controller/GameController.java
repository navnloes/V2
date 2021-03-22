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

import dk.dtu.compute.se.pisd.roborally.ImpossibleMoveException;
import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.model.ActionField.CheckPointCollection;
import dk.dtu.compute.se.pisd.roborally.model.ActionField.ConveyorBeltCollection;
import dk.dtu.compute.se.pisd.roborally.model.ActionField.GearsCollection;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {
    /**
     * StopWatch is going to be implemented here in GameController
     */
    final public Board board;
    CheckPointCollection checkPointCollection;
    ConveyorBeltCollection conveyorBeltCollection;
    GearsCollection gearsCollection;
    BoardElementController boardElementController;
    public boolean won = false;

    public GameController(@NotNull Board board) {
        this.board = board;

        if (this.board.getPhase() == Phase.INITIALISATION)
            this.board.setPhase(Phase.PROGRAMMING);

        checkPointCollection = new CheckPointCollection();
        conveyorBeltCollection = new ConveyorBeltCollection();
        gearsCollection = new GearsCollection();
        boardElementController = new BoardElementController(board, checkPointCollection, conveyorBeltCollection, gearsCollection);
    }

    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space)  {


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
                    field.setCard(generateRandomCommandCard());
                    field.setVisible(true);
                }
            }
        }
    }

    // XXX: V2
    /**
     * Method generates a random Command Card
     * @return
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
     *
     * Then, the turn goes on to the next player, whose Command Card is activated
     */
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {
                    Command command = card.command;
                    if (command.isInteractive()) {
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        return;
                    }
                    executeCommand(currentPlayer, command);
                }
                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {
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
     * @param player whose turn it is
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
        }
    }

    //TODO: V3 ExecuteCommandOption
    /**
     * When the game is in INTERACTION Phase
     * The phase is set to ACTIVATION
     * and the current player's CommandCard is executed
     * - the turn moves on to the next player, til all steps are executed
     *
     * After all steps are executed, the game Phase returns to PROGRAMMING Phase
     * @param option option of Command
     */
    public void executeCommandOptionAndContinue(@NotNull Command option){
        Player currentPlayer = board.getCurrentPlayer();
        if (currentPlayer != null &&
                board.getPhase() == Phase.PLAYER_INTERACTION &&
                option != null) {
            board.setPhase(Phase.ACTIVATION);
            executeCommand(currentPlayer,option);

            int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
            if (nextPlayerNumber < board.getPlayersNumber()) {
                board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
            } else {
                int step = board.getStep() + 1;
                if (step < Player.NO_REGISTERS) {
                    makeProgramFieldsVisible(step);
                    board.setStep(step);
                    board.setCurrentPlayer(board.getPlayer(0));
                } else {
                    startProgrammingPhase();
                }
            }
        }
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
        boolean wallBlocks = WallCollection.getInstance().isWallBlocking(player.getSpace().x, player.getSpace().y, space.x, space.y);
        if (wallBlocks){
            throw new ImpossibleMoveException(player, space, heading);
        }

        boolean isConveyerBelt = conveyorBeltCollection.isConveyorBelt(space);
        if (isConveyerBelt){
            space = conveyorBeltCollection.conveyerBeltAction(space);
        }

        boolean isGear = gearsCollection.isGears(space);
        if (isGear){
            player.setHeading(gearsCollection.gearAction(player,space));
        }

        player.setSpace(space);

        boolean isCheckPoint = checkPointCollection.isCheckPoint(space);
        if (isCheckPoint){
            player.arrivedCheckPoint(checkPointCollection.getCheckPointId(space));
        }


    }


    // TODO Assignment V2
    /**
     * Method moves player to 'Neighbour' / one space forward
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
                } catch (ImpossibleMoveException e){
                    //
                }
            }
        }

    }

    // TODO Assignment V2

    /**
     * Method moves player two spaces forward
     * @param player to be moved
     */
    public void fastForward(@NotNull Player player) {

        //røv grimt men fungere 5head
        moveForward(player);
        moveForward(player);
    }

    // TODO Assignment V2

    /**
     * Method turns player to the right of the current 'Heading' / facing
     * @param player to be turned
     */
    public void turnRight(@NotNull Player player) {
        Heading heading = player.getHeading();
        player.setHeading(heading.next());
    }

    // TODO Assignment V2
    /**
     * Method turns player to the left of the current 'Heading' / facing
     * @param player to be turned
     */
    public void turnLeft(@NotNull Player player) {
        Heading heading = player.getHeading();
        player.setHeading(heading.prev());
    }

    /**
     *
     * @param source Card, which is picked
     * @param target Card, which is moved to CommandCard Field
     *               -to be executed during ACTIVATION phase
     * @return
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
    public CheckPointCollection getCheckPointCollection(){
        return checkPointCollection;
    }

    public ConveyorBeltCollection getConveyorBeltCollection() {
        return conveyorBeltCollection;
    }

    public GearsCollection getGearsCollection() {
        return gearsCollection;
    }

}
