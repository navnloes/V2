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
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.*;
import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import dk.dtu.compute.se.pisd.roborally.model.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 * @Altered by S205354, S205353
 */
public class SpaceView extends StackPane implements ViewObserver {

    final public static int SPACE_HEIGHT = 75; // 60; // 75;
    final public static int SPACE_WIDTH = 75;  // 60; // 75;
    private static final String BOARDSFOLDER = "boards";
    public final Space space;
    List<Heading> headings;
    Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    public SpaceView(@NotNull Space space) {
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);


        //TODO: finde ud af om dette kan undgås
        if(!space.hasPriorityAntenna()) {
            if ((space.x + space.y) % 2 == 0) {
                this.setStyle("-fx-background-color: white;");
            } else {
                this.setStyle("-fx-background-color: #ff69b4;");
            }
        }


        updatePlayer();

        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }

    private void updatePlayer() {

        this.getChildren().clear();

        //Conveyorbelt
        if (!space.getActions().isEmpty()) {
            FieldAction fieldAction = space.getActions().get(0);
            if (fieldAction instanceof ConveyorBeltFieldAction) {
                Polygon conveyorBelt = new Polygon(0.0, 0.0, 15.0, 40, 30.0, 0.0);
                conveyorBelt.setFill(Color.LIGHTBLUE);
                Heading heading = ((ConveyorBeltFieldAction) fieldAction).getHeading();
                if (heading == Heading.NORTH) {
                    conveyorBelt.setRotate(180);
                } else if (heading == Heading.EAST) {
                    conveyorBelt.setRotate(270);
                } else if (heading == Heading.SOUTH) {
                    conveyorBelt.setRotate(0);
                } else if (heading == Heading.WEST) {
                    conveyorBelt.setRotate(90);
                }

                this.getChildren().add(conveyorBelt);

            }
            else if (fieldAction instanceof DoubleConveyorBeltFieldAction) {
                Polygon conveyorBelt = new Polygon(0.0, 0.0, 20.0, 60, 40.0, 0.0);
                conveyorBelt.setFill(Color.DARKORANGE);
                Heading heading = ((DoubleConveyorBeltFieldAction) fieldAction).getHeading();
                if (heading == Heading.NORTH) {
                    conveyorBelt.setRotate(180);
                } else if (heading == Heading.EAST) {
                    conveyorBelt.setRotate(270);
                } else if (heading == Heading.SOUTH) {
                    conveyorBelt.setRotate(0);
                } else if (heading == Heading.WEST) {
                    conveyorBelt.setRotate(90);
                }

                this.getChildren().add(conveyorBelt);}
            //CheckPoint
                else if (fieldAction instanceof CheckPointFieldAction) {
                gc.setLineWidth(1);
                gc.setStroke(Color.BLUE);
                gc.setFill(Color.GOLD);
                gc.fillOval(12.5, 12.5, 49, 49);
                gc.strokeOval(12.5, 12.5, 50, 50);
                gc.strokeOval(12.5, 12.5, 51, 51);
                Node label = new Label(((CheckPointFieldAction) fieldAction).getCheckPointId() + 1 + "");
                this.getChildren().add(label);
                //Gears
            } else if (fieldAction instanceof GearsFieldAction) {
                Polygon gearPart1 = new Polygon(0.0, 0.0, 25.0, 50, 50.0, 0.0);
                Polygon gearPart2 = new Polygon(0.0, 0.0, 25.0, 50, 50.0, 0.0);
                Polygon gearPart3 = new Polygon(0.0, 0.0, 25.0, 50, 50.0, 0.0);
                gearPart1.setFill(Color.DARKGREY);
                gearPart2.setFill(Color.DARKGREY);
                gearPart3.setFill(Color.DARKGREY);
                gearPart1.setRotate(120);
                gearPart2.setRotate(240);

                if (((GearsFieldAction) fieldAction).getDirection() == Direction.LEFT) {
                    gc.setFill(Color.DARKRED);
                } else {
                    gc.setFill(Color.DARKGREEN);
                }
                gc.fillOval(17.5, 17.5, 40, 40);

                Label label = new Label(((GearsFieldAction) fieldAction).getDirection().toString());

                this.getChildren().add(gearPart1);
                gearPart1.toBack();

                this.getChildren().add(gearPart2);
                gearPart2.toBack();

                this.getChildren().add(gearPart3);
                gearPart3.toBack();

                this.getChildren().add(label);

            } else if (fieldAction instanceof Pit) {
                gc.setLineWidth(1);
                gc.setStroke(Color.BROWN);
                gc.setFill(Color.BLACK);
                gc.fillOval(12.5, 12.5, 49, 49);
                gc.strokeOval(12.5, 12.5, 50, 50);
                gc.strokeOval(12.5, 12.5, 51, 51);
                Node label = new Label("Brad Pitt");
                this.getChildren().add(label);
            }
        }



        //Player Sprite
        Player player = space.getPlayer();
        if (player != null) {
            Polygon arrow = new Polygon(0.0, 0.0,
                    10.0, 20.0,
                    20.0, 0.0);
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90 * player.getHeading().ordinal()) % 360);
            this.getChildren().add(arrow);

            Label label = new Label(Integer.toString(player.getCheckPointToken()));
            this.getChildren().add(label);
            label.toFront();
        }

        //Walls
        headings = space.getWalls();
        drawWalls();
        // drawGears();
        //playerAttackMove(player);

        if (space.hasPriorityAntenna()){
            FileInputStream input = null;
            try {
                ClassLoader classLoader = SpaceView.class.getClassLoader();
                String filename =
                        classLoader.getResource(BOARDSFOLDER).getPath() + "/antennaV2.jpg";


                input = new FileInputStream(filename);
                Image image2 = new Image(input);

                this.setBackground(new Background(new BackgroundImage(image2,  BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(SPACE_WIDTH, SPACE_HEIGHT, false, false, false, false))));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {

            this.getChildren().add(canvas);
            canvas.toBack();

        }
        catch (Exception e) {
            System.out.println("Unable to create multi Canvas");
        }

    }

    /**
     * @author S205354, S205353
     */

    private void drawWalls() {
        if (!headings.isEmpty()) {

            gc.setStroke(Color.DARKGREEN);
            gc.setLineWidth(10);
            gc.setLineCap(StrokeLineCap.ROUND);
            for (Heading wall : headings) {
                if (wall == Heading.SOUTH) {
                    gc.strokeLine(2, SPACE_HEIGHT - 2,
                            SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
                } else if (wall == Heading.NORTH) {
                    gc.strokeLine(2, 2,
                            SPACE_WIDTH - 2, 2);
                } else if (wall == Heading.EAST) {
                    gc.strokeLine(SPACE_WIDTH - 2, 2,
                            SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
                } else {
                    gc.strokeLine(2, 2,
                            2, SPACE_HEIGHT - 2);
                }
            }
        }
    }



    //TODO: Laser hvis muligt.
    private void playerAttackMove(Player player) {

//        if(!(player == null)) {
//
//            String billedSti = "pictures/laser";
//
//            try {
//                BufferedImage laser = ImageIO.read(new File(billedSti));
//                JLabel picLabel = new JLabel(new ImageIcon(laser));
//                JPanel jPanel = new JPanel();
//                jPanel.add(picLabel);
//            } catch (Exception e) {
//            }
//        }


//        if (!(player == null)) {
//            gc.setLineWidth(8);
//            gc.setStroke(Color.DARKRED);
//
//            if (player.getHeading() == Heading.NORTH) {
//                gc.strokeLine(0, 0,
//                        0, 0);
//            } else if (player.getHeading() == Heading.EAST) {
//                gc.strokeLine(2, 2,
//                        SPACE_WIDTH - 2, 2);
//            } else if (player.getHeading() == Heading.SOUTH) {
//                gc.strokeLine(35, 35,
//                        35, 50);
//            } else if (player.getHeading() == Heading.WEST) {
//                gc.strokeLine(SPACE_WIDTH - 2, 2,
//                        SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
//            }
//
//        }
    }

    @Override
    public void updateView(Subject subject) {

        if (subject == this.space) {
            updatePlayer();
        }
    }

}
