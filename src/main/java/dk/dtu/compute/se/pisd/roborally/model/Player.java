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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.jetbrains.annotations.NotNull;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;

import java.util.Optional;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Player extends Subject {

    final public static int NO_REGISTERS = 5;
    final public static int NO_CARDS = 8;

    final public Board board;

    private String name;
    private String color;
    private int playerId;
    private int cardIndex = 0;

    private Space space;
    private Heading heading = SOUTH;

    private CommandCardField[] program;
    private CommandCardField[] cards;
    private int checkPointToken;

    private boolean[] checkPointArray = {false, false, false};

    private boolean winner;

    private Space startSpace = null;

    private int life = 3;
    private boolean respawn = false;

    public CommandCardField[] getCards() {
        return cards;
    }

    /**
     * Constructor which assigns a programming field and random command cars
     * @param board
     * @param color of player's robot
     * @param name of player
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


    }

    /**
     * getter to return name
     * @return name of player
     */
    public String getName() {
        return name;
    }

    /**
     * setter to set name
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
     * @return color of player
     */
    public String getColor() {
        return color;
    }

    /**
     * setter that returns color
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
     * @return space that the player's robot is occupying
     */
    public Space getSpace() {
        return space;
    }

    /**
     * setter that places robot on a space
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
     * @return 'Heading' / facing direction
     */
    public Heading getHeading() {
        return heading;
    }

    /**
     * setter that sets the heading of player's robot
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
     * @param i no. that register has
     * @return programming register number i
     */
    public CommandCardField getProgramField(int i) {
        return program[i];
    }

    /**
     * getter to return a specific Command Card
     * @param i no. of Command Card
     * @return Command Card no. i
     */
    public CommandCardField getCardField(int i) {
        return cards[i];
    }

    public void setCardField(int i, CommandCardField commandCardField){
        cards[i] = commandCardField;
    }

    public void addCheckPointToken(){
        checkPointToken  = checkPointToken + 1;
        if (checkPointToken == 3){
            winner = true;
        }
    }


    public int getCardIndex() { return cardIndex; }

    public void setCardIndex(int index) { cardIndex = index; }

    public int getPlayerId() { return playerId; }

    public void setPlayerId(int id) {
        playerId = id;
    }
    /**
     * This method checks which CheckPoint the player has arrived and assigns values for checkPointArray[i]
     * it ensures that the order of the arrived CheckPoints is correct and adds CheckPointTokens to the player
     *
     * @param id int - ID of arrived CheckPoint
     */
    public void arrivedCheckPoint(int id){

        switch (id) {
            case 0:
                if (!checkPointArray[0]){
                    addCheckPointToken();
                    checkPointArray[0] = true;
                }

                break;
            case 1:
                if (checkPointArray[0] && !checkPointArray[1]){
                    addCheckPointToken();
                    checkPointArray[1] = true;
                }

                break;
            case 2:
                if (checkPointArray[1] && !checkPointArray[2]){
                    addCheckPointToken();
                    checkPointArray[2] = true;


                }

                break;
            default:
                System.out.println("Illegal id - CheckPoint ID " + id + " in arrivedCheckPoint(int id)");

        }

    }

    public void createWinner() {
        if (checkPointArray[2]) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(name + " (" + color + ")" +" has won the game");
            alert.setContentText(name + " (" + color + ")" +" has won the game" + "\n Start new game by going to file -> new game");
            Optional<ButtonType> result = alert.showAndWait();

            if (!result.isPresent() || result.get() != ButtonType.OK) {

            }
        }
    }

    public int getCheckPointToken(){
        return checkPointToken;
    }

    public void setCheckPointToken(int i){
        checkPointToken = i;
    }

    public boolean getCheckPointArray(int i) {return checkPointArray[i];}

    public boolean[] getCheckPointArray() {return checkPointArray;}

    public boolean getWinner(){
        return winner;
    }

    public void setStartSpace(Space space){
        startSpace = space;
    }

    public void hit(){
        life--;
        if (life == 0){
            respawn = true;
            life = 3;
        }
    }

    public boolean isRespawn(){
        return respawn;
    }

    public void setRespawn(boolean state){
        respawn = state;
    }

    public Space getStartSpace(){
        return startSpace;
    }

}
