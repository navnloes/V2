package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.ActionField.CheckPointActionField;
import dk.dtu.compute.se.pisd.roborally.model.ActionField.CheckPointCollection;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.Wall;
import dk.dtu.compute.se.pisd.roborally.model.WallCollection;
import org.jetbrains.annotations.NotNull;

public class BoardElementController {

    private Board board;
    private CheckPointCollection checkPointCollection;

    public BoardElementController(@NotNull Board board, CheckPointCollection checkPointCollection) {
        this.board = board;
        this.checkPointCollection = checkPointCollection;

        addAllWalls();
        addAllCheckPoints();

    }

    public void addAllWalls() {
      //  WallCollection.getInstance().addWall(new Wall(0, 0, 0, 1, 0));
      //  WallCollection.getInstance().addWall(new Wall(7, 1, 0, 1, 1));
       // Wall wall1 = new Wall(0,0,1,0,0);
        //Space space1 = new Space(board, 3,3);
        //space1.addWall(wall1);

    }

    public void addAllCheckPoints() {
        checkPointCollection.addActionField(new CheckPointActionField(board, 3, 3, "CheckPoint 1", 0));
        checkPointCollection.addActionField(new CheckPointActionField(board, 0, 7, "CheckPoint 2", 1));
        checkPointCollection.addActionField(new CheckPointActionField(board, 5, 5, "CheckPoint 3", 2));
    }

}
