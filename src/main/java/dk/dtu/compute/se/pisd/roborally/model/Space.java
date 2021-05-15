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
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will using an x and y coordinate to determine if af space on the board has a player on it currently
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Space extends Subject {

    public final Board board;

    public final int x;
    public final int y;

    private Player player;
    private boolean hasPriorityAntenna;

    private List<Heading> walls;
    private List<FieldAction> fieldActions;

    /**
     * This is the constructor for Space
     * @param board Board board
     * @param x int x-coordinate
     * @param y int y-coordinate
     */
    public Space(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;

        player = null;
        walls = new ArrayList<Heading>();
        fieldActions = new ArrayList<>();
        hasPriorityAntenna = false;
    }

    /**
     * This getter returns the player on the given space
     * @return Player player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * This method sets the player on the given space
     * @param player Player player
     */
    public void setPlayer(Player player) {
        Player oldPlayer = this.player;
        if (player != oldPlayer &&
                (player == null || board == player.board)) {
            this.player = player;
            if (oldPlayer != null) {
                // this should actually not happen
                oldPlayer.setSpace(null);
            }
            if (player != null) {
                player.setSpace(this);
            }
            notifyChange();
        }
    }

    /**
     * This method notifies the observer of a playerChange on this space
     */
    void playerChanged() {
        // This is a minor hack; since some views that are registered with the space
        // also need to update when some player attributes change, the player can
        // notify the space of these changes by calling this method.
        notifyChange();
    }

    /**
     * This getter returns the fieldActions on this space
     * @return List<FieldAction> fieldActions
     */
    public List<FieldAction> getActions() {
        return fieldActions;
    }

    /**
     * This getter returns the walls on this space
     * @return List<Heading> walls
     */
    public List<Heading> getWalls(){
        return walls;
    }

    /**
     * This method adds a wall to a space
     * @param wall Heading wall
     */
    public void addWall(Heading wall){
        walls.add(wall);
    }

    /**
     * This method adds a fieldAction to this space
     * @param fieldAction FieldAction fieldAction
     */
    public void addFieldAction(FieldAction fieldAction){
        fieldActions.add(fieldAction);
    }

    /**
     * This method tells the space that it has a priorityAntenna
     * @param has boolean has
     */
    public void setPriorityAntenna(boolean has){
        hasPriorityAntenna = has;
    }

    /**
     * This getter returns the boolean hasPriorityAntenna and is used to check if the space has a priorityAntenna
     * @return boolean hasPriorityAntenna
     */
    public boolean hasPriorityAntenna(){
        return hasPriorityAntenna;
    }

}
