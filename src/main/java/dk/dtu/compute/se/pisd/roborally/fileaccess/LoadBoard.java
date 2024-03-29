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
package dk.dtu.compute.se.pisd.roborally.fileaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dk.dtu.compute.se.pisd.roborally.controller.ConveyorBeltFieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.DoubleConveyorBeltFieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GearsFieldAction;
import dk.dtu.compute.se.pisd.roborally.model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class LoadBoard {

    private static final String BOARDSFOLDER = "boards";
    private static String DEFAULTBOARD = "defaultboard";
    private static final String JSON_EXT = "json";

    /**
     * This method loads a json board
     * @param boardname String boardname
     * @return Board
     */
    public static Board loadBoard(String boardname) {
        if (boardname == null) {
            boardname = DEFAULTBOARD;
        }

        ClassLoader classLoader = LoadBoard.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(BOARDSFOLDER + "/" + boardname + "." + JSON_EXT);
        if (inputStream == null) {
            // these constants should be defined somewhere
            return createBoard(8, 8, boardname, false);
        }

        // In simple cases, we can create a Gson object with new Gson():
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();

        Board result;
        // FileReader fileReader = null;
        JsonReader reader = null;
        try {
            // fileReader = new FileReader(filename);
            reader = gson.newJsonReader(new InputStreamReader(inputStream));
            BoardTemplate template = gson.fromJson(reader, BoardTemplate.class);

            result = createBoard(template.width, template.height, boardname,true);
            Space space = result.getSpace(template.antennaX, template.antennaY);
            PriorityAntenna priorityAntenna = new PriorityAntenna();
            priorityAntenna.setSpace(space);
            result.addPriorityAntenna(priorityAntenna);

            for (SpaceTemplate spaceTemplate : template.spaces) {
                space = result.getSpace(spaceTemplate.x, spaceTemplate.y);
                if (space != null) {
                    space.getActions().addAll(spaceTemplate.actions);
                    space.getWalls().addAll(spaceTemplate.walls);
                }
            }

            reader.close();
            return result;
        }  catch (IOException e1) {
            if (reader != null) {
                try {
                    reader.close();
                    inputStream = null;
                } catch (IOException e2) {}
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {}
            }
        }
        return null;
    }

    /**
     * This method saves the board
     * @param board Board board
     * @param name String name
     */
    public static void saveBoard(Board board, String name) {
        BoardTemplate template = new BoardTemplate();
        template.width = board.width;
        template.height = board.height;

        for (int i=0; i<board.width; i++) {
            for (int j=0; j<board.height; j++) {
                Space space = board.getSpace(i,j);
                if (!space.getWalls().isEmpty() || !space.getActions().isEmpty()) {
                    SpaceTemplate spaceTemplate = new SpaceTemplate();
                    spaceTemplate.x = space.x;
                    spaceTemplate.y = space.y;
                    spaceTemplate.actions.addAll(space.getActions());
                    //spaceTemplate.walls.addAll(space.getWalls());
                    template.spaces.add(spaceTemplate);
                }
            }
        }

        ClassLoader classLoader = LoadBoard.class.getClassLoader();
        //  this is not very defensive, and will result in a NullPointerException
        //       when the folder "resources" does not exist! But, it does not need
        //       the file "simpleCards.json" to exist!
        String filename =
                classLoader.getResource(BOARDSFOLDER).getPath() + "/" + name + "." + JSON_EXT;

        // In simple cases, we can create a Gson object with new:
        //
        //   Gson gson = new Gson();
        //
        // But, if you need to configure it, it is better to create it from
        // a builder (here, we want to configure the JSON serialisation with
        // a pretty printer):
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>()).
                setPrettyPrinting();
        Gson gson = simpleBuilder.create();

        FileWriter fileWriter = null;
        JsonWriter writer = null;
        try {
            fileWriter = new FileWriter(filename);
            writer = gson.newJsonWriter(fileWriter);
            gson.toJson(template, template.getClass(), writer);
            writer.close();
        } catch (IOException e1) {
            if (writer != null) {
                try {
                    writer.close();
                    fileWriter = null;
                } catch (IOException e2) {}
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e2) {}
            }
        }
    }

    /**
     * This method creates a board
     * @param x int x
     * @param y int y
     * @param boardName String boardName
     * @param json boolean json
     * @return Board
     */
    private static Board createBoard(int x, int y, String boardName, boolean json){
        Board board = new Board(x,y, boardName);

        if(!json) {
            Space space = board.getSpace(5, 5);

            ConveyorBeltFieldAction conveyorBelt = new ConveyorBeltFieldAction();
            conveyorBelt.setHeading(Heading.NORTH);
            space.addFieldAction(conveyorBelt);

            space = board.getSpace(3, 1);
            DoubleConveyorBeltFieldAction doubleConveyorBelt = new DoubleConveyorBeltFieldAction();
            conveyorBelt.setHeading(Heading.NORTH);
            space.addFieldAction(doubleConveyorBelt);

            space = board.getSpace(5, 7);
            GearsFieldAction gearsFieldAction = new GearsFieldAction();
            gearsFieldAction.setDirection(Direction.RIGHT);
            space.addFieldAction(gearsFieldAction);

        /* since we only have 3 checkPoints
                space = board.getSpace(1,1);
        CheckPointFieldAction checkPointFieldAction = new CheckPointFieldAction();
        checkPointFieldAction.setCheckPointId(3);
        space.addFieldAction(checkPointFieldAction);
         */

            /*
            space = board.getSpace(4, 3);
            PriorityAntenna priorityAntenna = new PriorityAntenna();
            priorityAntenna.setSpace(space);
            board.addPriorityAntenna(priorityAntenna);

             */

            space.addWall(Heading.NORTH);

        }

        return board;
    }
}
