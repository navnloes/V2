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
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.model.ActionField.CheckPointActionField;
import dk.dtu.compute.se.pisd.roborally.model.ActionField.CheckPointCollection;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class BoardView extends VBox implements ViewObserver {

    private Board board;
    private Space space;
    private GridPane mainBoardPane;
    private SpaceView[][] spaces;
    private WallView[][] walls;
    private WallView wallView;
    private GameController gameController;

    private PlayersView playersView;

    private Label statusLabel;

    private SpaceEventHandler spaceEventHandler;

    public BoardView(@NotNull GameController gameController) {

        board = gameController.board;
        this.gameController = gameController;

        mainBoardPane = new GridPane();
        playersView = new PlayersView(gameController);
        statusLabel = new Label("<no status>");

        this.getChildren().add(mainBoardPane);
        this.getChildren().add(playersView);
        this.getChildren().add(statusLabel);

        spaces = new SpaceView[board.width][board.height];
        walls = new WallView[board.width][board.height];

        spaceEventHandler = new SpaceEventHandler(gameController);

        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.height; y++) {
                space = board.getSpace(x, y);
                SpaceView spaceView = new SpaceView(space);
                spaces[x][y] = spaceView;
                mainBoardPane.add(spaceView, x, y);
                spaceView.setOnMouseClicked(spaceEventHandler);
            }
        }

        board.attach(this);
        update(board);
    }

    @Override
    public void updateView(Subject subject) {
        if (subject == board) {
            Phase phase = board.getPhase();
            statusLabel.setText(board.getStatusMessage());
            //TODO: hvor den her skal være
            for (Wall wall : WallCollection.getInstance().getMyCollection()){
                if (wall.direction() == 0){
                    horizontalLine(wall.x1(),wall.y1());
                } else {
                    verticalLine(wall.x2(), wall.y2());
                }
            }

            for (CheckPointActionField c : gameController.getCheckPointCollection().getMyCollection()){
                Space space = board.getSpace(c.x(), c.y());
                CheckPointView cpv = new CheckPointView(space,c.id());
                mainBoardPane.add(cpv, c.x(), c.y());
            }
        }
    }

    // XXX this handler and its uses should eventually be deleted! This is just to help test the
    //     behaviour of the game by being able to explicitly move the players on the board!
    private class SpaceEventHandler implements EventHandler<MouseEvent> {

        final public GameController gameController;

        public SpaceEventHandler(@NotNull GameController gameController) {
            this.gameController = gameController;
        }

        @Override
        public void handle(MouseEvent event) {
            Object source = event.getSource();
            if (source instanceof SpaceView) {
                SpaceView spaceView = (SpaceView) source;
                Space space = spaceView.space;
                Board board = space.board;

                if (board == gameController.board) {
                    gameController.moveCurrentPlayerToSpace(space);
                    event.consume();
                }
            }
        }

    }

    //TODO: husk

    public void horizontalLine(int x, int y){
            Space space = board.getSpace(x, y);
            wallView = new WallView(space, Direction.HORIZONTAL);
            walls[x][y] = wallView;
            mainBoardPane.add(wallView, x, y);
    }

    public void verticalLine(int x, int y){
          Space space = board.getSpace(x, y);
          wallView = new WallView(space,Direction.VERTICAL);
          walls[x][y] = wallView;
          mainBoardPane.add(wallView, x, y);
          wallView.setOnMouseClicked(spaceEventHandler);

    }


}
