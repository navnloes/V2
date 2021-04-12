package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.model.ActionField.*;
import org.jetbrains.annotations.NotNull;

public class BoardElementController {

    private Board board;
    private CheckPointCollection checkPointCollection;
    private ConveyorBeltCollection conveyorBeltCollection;
    private GearsCollection gearsCollection;

    public BoardElementController(@NotNull Board board, CheckPointCollection checkPointCollection, ConveyorBeltCollection conveyorBeltCollection, GearsCollection gearsCollection) {
        this.board = board;
        this.checkPointCollection = checkPointCollection;
        this.conveyorBeltCollection = conveyorBeltCollection;
        this.gearsCollection = gearsCollection;

        addAllWalls();
        addAllCheckPoints();
        addAllConveyorBelts();
        addAllGears();

    }

    public void addAllWalls() {
        WallCollection.getInstance().addWall(new Wall(7, 1, 0, 1, 1));
        WallCollection.getInstance().addWall(new Wall(5, 4, 4, 4, 1));
        WallCollection.getInstance().addWall(new Wall(3, 5, 4, 5, 0));
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
        conveyorBeltCollection.addActionField(new ConveyorBeltActionField(board, 2,4, "ConveyorBelt 4", Heading.WEST));
        conveyorBeltCollection.addActionField(new ConveyorBeltActionField(board, 3,4, "ConveyorBelt 4", Heading.WEST));
    }

    public void addAllGears() {
        gearsCollection.addActionField(new GearsActionField(board, 7,6, "Gear 1", Direction.LEFT));
        gearsCollection.addActionField(new GearsActionField(board, 6,6, "Gear 2", Direction.RIGHT));
        gearsCollection.addActionField(new GearsActionField(board, 2,2, "Gear 3", Direction.LEFT));
        gearsCollection.addActionField(new GearsActionField(board, 4,2, "Gear 4", Direction.RIGHT));
    }

}