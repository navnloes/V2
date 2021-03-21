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

    public void addAllGears() {
        gearsCollection.addActionField(new GearsActionField(board, 7,6, "Gear 1", Heading.EAST));
        gearsCollection.addActionField(new GearsActionField(board, 6,6, "Gear 2", Heading.WEST));
        gearsCollection.addActionField(new GearsActionField(board, 2,2, "Gear 3", Heading.NORTH));
        gearsCollection.addActionField(new GearsActionField(board, 4,2, "Gear 4", Heading.SOUTH));
    }

}