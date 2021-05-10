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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public enum Command {

    // This is a very simplistic way of realizing different commands.

    FORWARD("Fwd"),
    RIGHT("Turn Right"),
    LEFT("Turn Left"),
    FAST_FORWARD("Fast Fwd"),

    // XXX Assignment V3
    OPTION_LEFT_RIGHT("Left OR Right", LEFT, RIGHT),
    SPAM("Damage Card");

    final public String displayName;

    final private List<Command> options;

    Command(String displayName, Command... options) {
        this.displayName = displayName;
        this.options = Collections.unmodifiableList(Arrays.asList(options));
    }

    public boolean isInteractive() {
        return !options.isEmpty();
    }

    public List<Command> getOptions() {
        return options;
    }

    public static int getId(Command command){
        int cId = -1;
        switch (command) {
            case FORWARD:
                cId = 0;
                break;
            case RIGHT:
                cId = 1;
                break;
            case LEFT:
                cId = 2;
                break;
            case FAST_FORWARD:
                cId = 3;
                break;
            case OPTION_LEFT_RIGHT:
                cId = 4;
                break;
            case SPAM:
                cId = 5;
                break;
            default:
                System.out.println("Illegal cardType - CardID " + cId + " in updatecardFieldsInDB");
                break;
        }
        return cId;
    }

    public static Command getCommand(int index){
        Command command = null;
        switch (index) {
            case 0:
                command = FORWARD;
                break;
            case 1:
                command = RIGHT;
                break;
            case 2:
                command = LEFT;
                break;
            case 3:
                command = FAST_FORWARD;
                break;
            case 4:
                command = OPTION_LEFT_RIGHT;
                break;
            case 5:
                command = SPAM;
                break;
            default:
                System.out.println("Illegal cardIndex - int index " + index + " in Command.getCommand");
                break;
        }
        return command;
    }

}