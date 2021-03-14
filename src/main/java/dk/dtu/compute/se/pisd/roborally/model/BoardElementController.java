package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.model.ActionField.ActionField;
import dk.dtu.compute.se.pisd.roborally.model.ActionField.CheckPointActionField;
import dk.dtu.compute.se.pisd.roborally.model.ActionField.CheckPointCollection;
import org.jetbrains.annotations.NotNull;

public class BoardElementController {

    private Board board;
    private CheckPointCollection checkPointCollection;


    public BoardElementController(@NotNull Board board){
        this.board = board;
    }

    public void addAllWalls(){
        WallCollection.getInstance().addWall(new Wall(0,0,0,1,0));
        WallCollection.getInstance().addWall(new Wall(7,1,0,1, 1));
    }

    public void addCheckPoints(){
        checkPointCollection.addActionField(new CheckPointActionField(board,3,3,"hej",1));
    }


}
