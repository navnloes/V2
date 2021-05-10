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
package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.PlayerAction;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class Player extends Subject {

    final public static int NO_REGISTERS = 5;
    final public static int NO_CARDS = 8;
    final public static int NO_DECK_SIZE = 40;

    final public Board board;

    private String name;
    private String color;
    private int playerId;
    private int cardIndex = 0;
    private int distance;

    private Space space;
    private Heading heading = SOUTH;

    private CommandCardField[] program;
    private CommandCardField[] cards;
    private Stack<CommandCard> deck;
    private Stack<CommandCard> discardpile;

    private int checkPointToken;

    private boolean[] checkPointArray = {false, false, false};

    private boolean winner;

    private Space startSpace = null;

    private boolean reboot = false;

    public CommandCardField[] getCards() {
        return cards;
    }

    public CommandCardField[] getProgram() {
        return program;
    }

    private List<PlayerAction> playerActions;

    /**
     * Constructor which assigns a programming field and random command cars
     *
     * @param board
     * @param color of player's robot
     * @param name  of player
     */
    public Player(@NotNull Board board, String color, @NotNull String name) {
        this.board = board;
        this.name = name;
        this.color = color;

        this.space = null;

        program = new CommandCardField[NO_REGISTERS];
        for (int i = 0; i < program.length; i++) {
            program[i] = new CommandCardField(this);
        }

        cards = new CommandCardField[NO_CARDS];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new CommandCardField(this);
        }

        checkPointToken = 0;
        winner = false;
        this.playerActions = new ArrayList<>();

        deck = new Stack<>();
        discardpile = new Stack<>();
        for (int i = 0; i < NO_DECK_SIZE; i++) {
            Command[] commands = Command.values();
            // random commands not include penalty command
            int random = (int) (Math.random() * commands.length - 1);
            deck.push(new CommandCard(commands[random]));
        }


    }

    /**
     * getter to return name
     *
     * @return name of player
     */
    public String getName() {
        return name;
    }

    /**
     * setter to set name
     *
     * @param name of player
     */
    public void setName(String name) {
        if (name != null && !name.equals(this.name)) {
            this.name = name;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    /**
     * getter that returns color
     *
     * @return color of player
     */
    public String getColor() {
        return color;
    }

    /**
     * setter that returns color
     *
     * @param color of player
     */
    public void setColor(String color) {
        this.color = color;
        notifyChange();
        if (space != null) {
            space.playerChanged();
        }
    }

    /**
     * getter that returns the player's current space
     *
     * @return space that the player's robot is occupying
     */
    public Space getSpace() {
        return space;
    }

    /**
     * setter that places robot on a space
     *
     * @param space / target space
     */
    public void setSpace(Space space) {
        Space oldSpace = this.space;
        if (space != oldSpace &&
                (space == null || space.board == this.board)) {
            this.space = space;
            if (oldSpace != null) {
                oldSpace.setPlayer(null);
            }
            if (space != null) {
                space.setPlayer(this);
            }
            notifyChange();
        }
    }

    /**
     * getter that returns the heading of player's robot
     *
     * @return 'Heading' / facing direction
     */
    public Heading getHeading() {
        return heading;
    }

    /**
     * setter that sets the heading of player's robot
     *
     * @param heading / target heading direction
     */
    public void setHeading(@NotNull Heading heading) {
        if (heading != this.heading) {
            this.heading = heading;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    /**
     * getter to return a specific programming field register
     *
     * @param i no. that register has
     * @return programming register number i
     */
    public CommandCardField getProgramField(int i) {
        return program[i];
    }

    /**
     * getter to return a specific Command Card
     *
     * @param i no. of Command Card
     * @return Command Card no. i
     */
    public CommandCardField getCardField(int i) {
        return cards[i];
    }

    public void setCardField(int i, CommandCardField commandCardField) {
        cards[i] = commandCardField;
        notifyChange();
    }

    /**
     * This method adds a checkPoint token to the player
     * When a player has all 3 checkPoint tokens, he/she becomes the winner of the game
     */
    public void addCheckPointToken() {
        checkPointToken = checkPointToken + 1;
        notifyChange();
    }

    public void setWinner(boolean state) {
        winner = state;
    }


    /**
     * This method returns the cardindex
     *
     * @return int CardIndex
     */
    public int getCardIndex() {
        return cardIndex;
    }

    /**
     * This method sets the cardIndex to the given int
     *
     * @param index int index
     */
    public void setCardIndex(int index) {
        cardIndex = index;
        notifyChange();
    }

    /**
     * This method returns the playerId of this player
     *
     * @return int playerId
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * This method sets the playerId of this player to the given int
     *
     * @param id
     */
    public void setPlayerId(int id) {
        playerId = id;
        notifyChange();
    }

    /**
     * @author s205353
     * This method crowns a player the winner of the game, when the third value of the checkPoint array is true
     * checkPointArray[2] is true, when the robot has collected all three checkPoints
     */
    public void createWinner() {
        if (winner) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(name + " (" + color + ")" + " has won the game");
            alert.setContentText(name + " (" + color + ")" + " has won the game" + "\n Start new game by going to file -> new game");
            Optional<ButtonType> result = alert.showAndWait();

            if (!result.isPresent() || result.get() != ButtonType.OK) {

            }
        }
    }

    /**
     * This method returns the value of acquirred checkPoint tokens that a player has
     *
     * @return int checKPoint tokens
     */
    public int getCheckPointToken() {
        return checkPointToken;
    }

    /**
     * This method sets the number of checkPoint tokens for a player
     *
     * @param i int i is the number of checkPoint tokens
     */
    public void setCheckPointToken(int i) {
        checkPointToken = i;
        notifyChange();
        if (space != null)
            space.playerChanged();
    }

    public boolean getCheckPointArray(int i) {
        return checkPointArray[i];
    }

    /**
     * This getter returns whether or not, the given player is the winner of the game
     *
     * @return boolean winner
     */
    public boolean getWinner() {
        return winner;
    }

    /**
     * This method sets the player's robot's startSpace to a given field on the map
     *
     * @param space
     */
    public void setStartSpace(Space space) {
        startSpace = space;
        notifyChange();
    }

    /**
     * This method is called when a robot is hit by a laser
     * The robot's life gets updated, and if the robot has lost all of its 3 lives, then it is rebooted
     *
     * @auhtor s205353, s205339, s201192
     */
    public void spamDamage(int damageCards) {
        for (int i = 0; i < damageCards; i++)
            discardpile.add(new CommandCard(Command.SPAM));
        notifyChange();
        if (space != null)
            space.playerChanged();
    }

    /**
     * This method returns the reboot state of the robot in order to determine if the robot should be rebooted
     *
     * @return boolean reboot
     */
    public boolean getReboot() {
        return reboot;
    }

    /**
     * @param state state of reboot - true: robot should be rebooted and false: if robot isn't in reboot mode
     * @auhtor s205353, s205339, s201192
     */
    public void setReboot(boolean state) {
        reboot = state;
        if (reboot) {
            this.setSpace(this.getStartSpace());
            notifyChange();
        }
        notifyChange();
    }

    public Space getStartSpace() {
        return startSpace;
    }

    public boolean[] getCheckPointArray() {
        return checkPointArray;
    }

    public List<PlayerAction> getActions() {
        return playerActions;
    }

    public void addPlayerAction(PlayerAction playerAction) {
        playerActions.add(playerAction);
    }

    public void setCardInvisible(int i) {
        cards[i].setVisible(false);
        notifyChange();
    }

    /**
     * This method sets program cards invisible
     * @param i
     */
    public void setProgramInvisible(int i) {
        program[i].setVisible(false);
        notifyChange();
    }

    /**
     * @param i int distance in number of spaces from priority antenna
     * @author s205353
     */
    public void setDistance(int i) {
        distance = i;
        notifyChange();
    }

    /**
     * @return int distance
     * @author This method returns the player's distance from the priority antenna
     */
    public int getDistance() {
        return distance;
    }

    /**
     * This method returns the top programcard from the deck
     * if the deck has 8 or less cards, the discardpile is shuffled, and these cards are inserted into the deck
     *
     * @return newCard CommandCard
     */
    public CommandCard fetchFromCardDeck() {
        CommandCard newCard = null;
        if (!discardpile.empty() && deck.size() <= NO_CARDS) {
            Collections.shuffle(discardpile);
            deck.addAll(discardpile);
            discardpile.clear();
        }
        newCard = deck.pop();

        return newCard;
    }

    /**
     * This method adds a CommandCard (program card) to the discard pile, when it has been programmed
     *
     * @param card CommandCard
     */
    public void addDiscardCard(CommandCard card) {
        discardpile.push(card);
    }


}
