package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.ActionField.CheckPointActionField;
import dk.dtu.compute.se.pisd.roborally.model.ActionField.CheckPointCollection;
import dk.dtu.compute.se.pisd.roborally.model.ActionField.ConveyorBeltActionField;
import dk.dtu.compute.se.pisd.roborally.model.ActionField.ConveyorBeltCollection;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Wall;
import dk.dtu.compute.se.pisd.roborally.model.WallCollection;
import org.jetbrains.annotations.NotNull;

public class BoardElementController {

    private Board board;
    private CheckPointCollection checkPointCollection;
    private ConveyorBeltCollection conveyorBeltCollection;

    public BoardElementController(@NotNull Board board, CheckPointCollection checkPointCollection, ConveyorBeltCollection conveyorBeltCollection) {
        this.board = board;
        this.checkPointCollection = checkPointCollection;
        this.conveyorBeltCollection = conveyorBeltCollection;

        addAllWalls();
        addAllCheckPoints();
        addAllConveyorBelts();

    }

    public void addAllWalls() {
        WallCollection.getInstance().addWall(new Wall(0, 0, 0, 1, 0));
        WallCollection.getInstance().addWall(new Wall(7, 1, 0, 1, 1));
    }

    public void addAllCheckPoints() {
        checkPointCollection.addActionField(new CheckPointActionField(board, 3, 3, "CheckPoint 1", 0));
        checkPointCollection.addActionField(new CheckPointActionField(board, 0, 7, "CheckPoint 2", 1));
        checkPointCollection.addActionField(new CheckPointActionField(board, 5, 5, "CheckPoint 3", 2));
    }

    public void addAllConveyorBelts() {
        conveyorBeltCollection.addActionField(new ConveyorBeltActionField(board, 7,7, "ConveyorBelt 1", Heading.NORTH));
        conveyorBeltCollection.addActionField(new ConveyorBeltActionField(board, 6,7, "ConveyorBelt 2", Heading.EAST));
        conveyorBeltCollection.addActionField(new ConveyorBeltActionField(board, 5,7, "ConveyorBelt 3", Heading.SOUTH));
        conveyorBeltCollection.addActionField(new ConveyorBeltActionField(board, 4,7, "ConveyorBelt 4", Heading.WEST));
    }

}